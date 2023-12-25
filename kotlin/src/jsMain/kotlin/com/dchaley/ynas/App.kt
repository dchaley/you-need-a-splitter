package com.dchaley.ynas

import com.dchaley.ynas.util.DataState
import io.kvision.*
import io.kvision.core.AlignItems
import io.kvision.html.*
import io.kvision.panel.root
import io.kvision.panel.vPanel
import io.kvision.state.bind
import io.kvision.utils.auto
import io.kvision.utils.perc
import io.kvision.utils.px
import kotlinx.coroutines.flow.*
import ynab.BudgetSummary
import ynab.TransactionDetail
import ynab.api

typealias transactionState = DataState<List<TransactionDetail>>

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

        val budgetSummaries = MutableStateFlow(listOf<BudgetSummary>())
        ynab.budgets.getBudgets().then { response ->
            budgetSummaries.value = response.data.budgets.toList()
        }
        val selectedBudget = MutableStateFlow<BudgetSummary?>(null)

        val loadedTransactions = MutableStateFlow<transactionState>(DataState.Unloaded)

        root("kvapp") {
            vPanel(alignItems = AlignItems.STRETCH, useWrappers = true) {
                margin = 15.px
                maxWidth = 60.perc
                marginLeft = auto
                marginRight = auto
                vPanel(alignItems = AlignItems.STRETCH, useWrappers = true) {
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
                                    loadedTransactions.value = DataState.Loaded(response.data.transactions.toList())
                                }
                            }
                        }
                    }

                    div().bind(loadedTransactions) { state ->
                        borderedContainer { transactionsList(state) }
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
