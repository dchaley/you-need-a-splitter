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
import ynab.TransactionDetail
import ynab.api

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

            // TODO: implement transaction approval.
            // Issue the call to ynab budget.
            // When it comes back successfully, insert it into the data model.
            // In the meantime, give it a spinny!

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
