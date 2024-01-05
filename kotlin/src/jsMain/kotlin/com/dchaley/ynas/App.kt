package com.dchaley.ynas

import com.dchaley.ynas.util.DataState
import com.dchaley.ynas.util.replaceAll
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

class DataModel {
    private val budgetsObservable = ObservableValue<DataState<ObservableList<BudgetSummary>>>(DataState.Unloaded)
    private val selectedBudgetObservable = ObservableValue<BudgetSummary?>(null)
    private val transactionsStoreObservable = ObservableValue<DataState<MutableMap<String, TransactionDetail>>>(DataState.Unloaded)
    private val displayedTransactionsObservable = ObservableValue<DataState<ObservableList<TransactionDetail>>>(DataState.Unloaded)

    var budgets: DataState<ObservableList<BudgetSummary>>
        get() = budgetsObservable.value
        set(value) { budgetsObservable.value = value }

    var selectedBudget: BudgetSummary?
        get() = selectedBudgetObservable.value
        set(value) { selectedBudgetObservable.value = value }

    var transactionsStore: DataState<MutableMap<String, TransactionDetail>>
        get() = transactionsStoreObservable.value
        set(value) {
            transactionsStoreObservable.value = value
            updateDisplayedTxns()
        }

    var displayedTransactions: DataState<ObservableList<TransactionDetail>>
        get() = displayedTransactionsObservable.value
        set(value) { displayedTransactionsObservable.value = value }

    fun updateDisplayedTxns() {
        when (transactionsStore) {
            is DataState.Unloaded -> {
                displayedTransactions = DataState.Unloaded
            }

            is DataState.Loading -> {
                displayedTransactions = DataState.Loading
            }

            is DataState.Loaded -> {
                val txns = (transactionsStore as DataState.Loaded<MutableMap<String, TransactionDetail>>).data.values
                // this could sort by anything… but for now, just sort by date
                val sorted = txns.sortedBy { it.date }

                // We don't want to trigger a re-render if the list is already loaded.
                // Setting the value outright (vs replacing contents) triggers a higher-level re-render.
                if (displayedTransactions !is DataState.Loaded<*>) {
                    displayedTransactions = DataState.Loaded(observableListOf(*sorted.toTypedArray()))
                }
                else {
                    val existingList = (displayedTransactions as DataState.Loaded<ObservableList<TransactionDetail>>).data
                    (existingList as ObservableListWrapper).replaceAll(sorted)
                }
            }
        }
    }

    fun updateTransaction(original: TransactionDetail, updated: TransactionDetail) {
        // replace the old object with the updated one
        val storedTransactions = (transactionsStore as DataState.Loaded<MutableMap<String, TransactionDetail>>).data
        storedTransactions[original.id] = updated

        updateDisplayedTxns()
    }

    fun observeBudgets(): ObservableValue<DataState<ObservableList<BudgetSummary>>> {
        return budgetsObservable
    }

    fun observeSelectedBudget(): ObservableValue<BudgetSummary?> {
        return selectedBudgetObservable
    }

    fun observeTransactionsStore(): ObservableValue<DataState<MutableMap<String, TransactionDetail>>> {
        return transactionsStoreObservable
    }

    fun observeDisplayedTransactions(): ObservableValue<DataState<ObservableList<TransactionDetail>>> {
        return displayedTransactionsObservable
    }
}

class App : Application() {
    init {
        require("@fortawesome/fontawesome-free/js/brands.js")
        require("@fortawesome/fontawesome-free/js/solid.js")
        require("@fortawesome/fontawesome-free/js/fontawesome.js")
    }


    override fun start() {
        // Read the variable YNAB_ACCESS_TOKEN from the environment (see config.js in webpack.config.d)
        val env = js("PROCESS_ENV")
        val accessToken = env.YNAB_ACCESS_TOKEN as String

        val ynab = api(accessToken)

        val dataModel = DataModel()

        ynab.budgets.getBudgets().then { response ->
            dataModel.budgets = DataState.Loaded(observableListOf(*response.data.budgets))
        }

        dataModel.observeTransactionsStore().subscribe { _ ->
        }

        fun onApprove(transactionDetail: TransactionDetail) {
            if (dataModel.displayedTransactions !is DataState.Loaded<*>) {
                console.log("onApprove called when transactions not loaded")
                return
            }
            val copied = structuredClone(transactionDetail)
            copied.payee_name += " (approved)"

            dataModel.updateTransaction(transactionDetail, copied)
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

                    div().bind(dataModel.observeSelectedBudget()) { budget ->
                        if (budget != null) {
                            div("Selected budget: ${budget.name}")
                        }
                        else {
                            div().bind(dataModel.observeBudgets()) { state ->
                                budgetSelector(state) { newBudget ->
                                    dataModel.selectedBudget = newBudget
                                    dataModel.displayedTransactions = DataState.Loading
                                    ynab.transactions.getTransactions(newBudget.id, type="unapproved").then { response ->
                                        val pairs = response.data.transactions.map { it.id to it }.toTypedArray()
                                        dataModel.transactionsStore = DataState.Loaded(mutableMapOf(*pairs))
                                    }
                                }
                            }
                        }
                    }

                    div().bind(dataModel.observeDisplayedTransactions()) { state ->
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
