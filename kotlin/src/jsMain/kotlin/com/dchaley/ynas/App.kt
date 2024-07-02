package com.dchaley.ynas

import com.dchaley.ynas.util.DataState
import io.kvision.*
import io.kvision.core.AlignItems
import io.kvision.html.*
import io.kvision.panel.root
import io.kvision.panel.vPanel
import io.kvision.state.bind
import io.kvision.state.observableListOf
import io.kvision.utils.auto
import io.kvision.utils.em
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


  override fun start() {
    // Read the variable YNAB_ACCESS_TOKEN from the environment (see config.js in webpack.config.d)
    val env = js("PROCESS_ENV")
    val accessToken = env.YNAB_ACCESS_TOKEN as String

    // The YNAB API client.
    val ynab = api(accessToken)

    val dataModel = DataModel()

    ynab.budgets.getBudgets().then { response ->
      dataModel.budgets = DataState.Loaded(observableListOf(*response.data.budgets))
    }

    dataModel.observeTransactionsStore().subscribe { _ ->
    }

    fun onApprove(transactionDetail: TransactionDetail) {
      if (dataModel.transactionsStore !is DataState.Loaded<*>) {
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

    fun onUnapprove(transactionDetail: TransactionDetail) {
      if (dataModel.transactionsStore !is DataState.Loaded<*>) {
        console.log("onUnapprove called when transactions not loaded")
        return
      }
      val copied = structuredClone(transactionDetail)
      copied.payee_name += " (unapproved)"

      // TODO: implement transaction unapproval.
      // Issue the call to ynab budget.
      // When it comes back successfully, insert it into the data model.
      // In the meantime, give it a spinny!

      dataModel.updateTransaction(transactionDetail, copied)
    }

    fun onSelectBudget(budget: BudgetSummary, onlyUnapproved: Boolean) {
      dataModel.selectedBudget = budget
      dataModel.transactionsStore = DataState.Loading
      val txnType = if (onlyUnapproved) "unapproved" else ""
      ynab.transactions.getTransactions(budget.id, type = txnType).then { response ->
        val pairs = response.data.transactions.map { it.id to it }.toTypedArray()
        dataModel.transactionsStore = DataState.Loaded(mutableMapOf(*pairs))
      }
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

          // This part of the UI shows the selected budget or a selector.
          div().bind(dataModel.observeSelectedBudget()) { budget ->
            if (budget != null) {
              div("Selected budget: ${budget.name}")
            } else {
              div().bind(dataModel.observeBudgets()) { state ->
                budgetSelector(state, ::onSelectBudget)
              }
            }
          }

          // This part of the UI shows the transactions for the selected budget.
          div().bind(dataModel.observeTransactionsStore()) { state ->
            borderedContainer {
              padding = 2.em
              vPanel(alignItems = AlignItems.CENTER, useWrappers = true) {
                padding = 2.em
                when (state) {
                  is DataState.Unloaded -> {
                    h4("no transactions loaded!")
                  }

                  is DataState.Loading -> {
                    p {
                      icon("fas fa-spinner fa-xl fa-spin")
                    }
                    h4("loading transactions…")
                  }

                  is DataState.Loaded -> {
                    transactionsList(
                      dataModel.displayedTransactions,
                      onApprove = ::onApprove,
                      onUnapprove = ::onUnapprove,
                    )
                  }
                }
              }
            }
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
