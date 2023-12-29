package com.dchaley.ynas

import com.dchaley.ynas.util.DataState
import io.kvision.*
import io.kvision.core.AlignItems
import io.kvision.html.div
import io.kvision.html.h1
import io.kvision.html.header
import io.kvision.panel.root
import io.kvision.panel.vPanel
import io.kvision.state.*
import io.kvision.utils.auto
import io.kvision.utils.perc
import io.kvision.utils.px
import js.core.structuredClone
import ynab.BudgetSummary
import ynab.TransactionDetail
import ynab.api

class App : Application() {
    init {
        require("@fortawesome/fontawesome-free/js/brands.js")
        require("@fortawesome/fontawesome-free/js/solid.js")
        require("@fortawesome/fontawesome-free/js/fontawesome.js")
    }

    // define an extension function on ObservableListWrapper that clears and adds all atomically
    fun <T> ObservableListWrapper<T>.replaceAll(newItems: List<T>) : Boolean {
        if (this.isEmpty() && newItems.isEmpty()) {
            return true
        }
        this.mutableList.clear()
        return this.addAll(newItems)
    }

    override fun start() {
        // Read the variable YNAB_ACCESS_TOKEN from the environment (see config.js in webpack.config.d)
        val env = js("PROCESS_ENV")
        val accessToken = env.YNAB_ACCESS_TOKEN as String

        val ynab = api(accessToken)

        val budgetSummaries = observableListOf<BudgetSummary>()
        ynab.budgets.getBudgets().then { response ->
            budgetSummaries.addAll(response.data.budgets)
        }
        val selectedBudget = ObservableValue<BudgetSummary?>(null)

        val transactionsStore = ObservableValue<DataState<MutableMap<String, TransactionDetail>>>(DataState.Unloaded)

        val loadedTransactions = ObservableValue<DataState<ObservableList<TransactionDetail>>>(DataState.Unloaded)

        fun updater(state: DataState<MutableMap<String, TransactionDetail>>) {
            when (state) {
                is DataState.Unloaded -> {
                    loadedTransactions.value = DataState.Unloaded
                }

                is DataState.Loading -> {
                    loadedTransactions.value = DataState.Loading
                }

                is DataState.Loaded -> {
                    val txns = state.data.values
                    // this could sort by anything… but for now, just sort by date
                    val sorted = txns.sortedBy { it.date }

                    // We don't want to trigger a re-render if the list is already loaded.
                    // Setting the value outright (vs replacing contents) triggers a higher-level re-render.
                    if (loadedTransactions.value !is DataState.Loaded<*>) {
                        loadedTransactions.value = DataState.Loaded(observableListOf(*sorted.toTypedArray()))
                    }
                    else {
                        val existingList = (loadedTransactions.value as DataState.Loaded<ObservableList<TransactionDetail>>).data
                        (existingList as ObservableListWrapper).replaceAll(sorted)
                    }
                }
            }
        }

        transactionsStore.subscribe { state ->
            updater(state)
        }

        fun updateTransaction(original: TransactionDetail, updated: TransactionDetail) {
            val storedTransactions = (transactionsStore.value as DataState.Loaded<MutableMap<String, TransactionDetail>>).data

            // replace the old object with the updated one
            storedTransactions[original.id] = updated

            updater(transactionsStore.value)
        }

        fun onApprove(transactionDetail: TransactionDetail) {
            if (loadedTransactions.value !is DataState.Loaded<*>) {
                console.log("onApprove called when transactions not loaded")
                return
            }
            val copied = structuredClone(transactionDetail)
            copied.payee_name += " (approved)"

            updateTransaction(transactionDetail, copied)
        }

        root("kvapp") {
            vPanel(alignItems = AlignItems.STRETCH, useWrappers = true) {
                margin = 15.px
                maxWidth = 75.perc
                marginLeft = auto
                marginRight = auto
                vPanel(spacing = 5, alignItems = AlignItems.STRETCH, useWrappers = true) {
                    header {
                        h1("You Need a Splitter!")
                    }

                    div().bind(selectedBudget) {budget ->
                        if (budget != null) {
                            div("Selected budget: ${budget.name}")
                        }
                        else {
                            budgetSelector(budgetSummaries) { newBudget ->
                                selectedBudget.value = newBudget
                                loadedTransactions.value = DataState.Loading
                                ynab.transactions.getTransactions(newBudget.id, type="unapproved").then { response ->
                                    val pairs = response.data.transactions.map { it.id to it }.toTypedArray()
                                    transactionsStore.value = DataState.Loaded(mutableMapOf(*pairs))
                                }
                            }
                        }
                    }

                    div().bind(loadedTransactions) { state ->
                        borderedContainer { transactionsList(state, onApprove = ::onApprove) }
                    }
                }
            }
        }
    }
}

fun main() {
    startApplication(
        ::App,
        module.hot,
        BootstrapModule,
        CoreModule
    )
}
