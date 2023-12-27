package com.dchaley.ynas

import com.dchaley.ynas.util.DataState
import io.kvision.*
import io.kvision.core.AlignItems
import io.kvision.html.div
import io.kvision.html.h1
import io.kvision.html.header
import io.kvision.panel.root
import io.kvision.panel.vPanel
import io.kvision.state.ObservableList
import io.kvision.state.ObservableValue
import io.kvision.state.bind
import io.kvision.state.observableListOf
import io.kvision.utils.auto
import io.kvision.utils.perc
import io.kvision.utils.px
import js.core.structuredClone
import ynab.BudgetSummary
import ynab.TransactionDetail
import ynab.api

typealias transactionState = DataState<ObservableList<TransactionDetail>>

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

        val budgetSummaries = observableListOf<BudgetSummary>()
        ynab.budgets.getBudgets().then { response ->
            budgetSummaries.addAll(response.data.budgets)
        }
        val selectedBudget = ObservableValue<BudgetSummary?>(null)

        val loadedTransactions = ObservableValue<transactionState>(DataState.Unloaded)

        fun onApprove(transactionDetail: TransactionDetail) {
            if (loadedTransactions.value !is DataState.Loaded<*>) {
                console.log("onApprove called when transactions not loaded")
                return
            }
            val list = (loadedTransactions.value as DataState.Loaded<ObservableList<TransactionDetail>>).data
            val index = list.indexOf(transactionDetail)
            val copied = structuredClone(transactionDetail)
            copied.payee_name += " (approved)"
            list[index] = copied
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
                                    loadedTransactions.value = DataState.Loaded(observableListOf(*response.data.transactions))
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
