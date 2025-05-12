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
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
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

      val req = js("{transaction: {}}")
      req.transaction.approved = true

      ynab.transactions.updateTransaction(
        dataModel.selectedBudget!!.id, transactionDetail.id, req
      ).then { response ->
        dataModel.updateTransaction(transactionDetail, response.data.transaction)
      }.catch { error ->
        console.error("Error updating transaction: $error")
      }
    }

    fun onUnapprove(transactionDetail: TransactionDetail) {
      if (dataModel.transactionsStore !is DataState.Loaded<*>) {
        console.log("onUnapprove called when transactions not loaded")
        return
      }

      val req = js("{transaction: {}}")
      req.transaction.approved = false

      ynab.transactions.updateTransaction(
        dataModel.selectedBudget!!.id, transactionDetail.id, req
      ).then { response ->
        dataModel.updateTransaction(transactionDetail, response.data.transaction)
      }.catch { error ->
        console.error("Error updating transaction: $error")
      }
    }

    fun finishSplit(transactionDetail: TransactionDetail, splitResult: SplitResult) {
      if (dataModel.transactionsStore !is DataState.Loaded<*>) {
        console.log("finishSplit called when transactions not loaded")
        return
      }

      val req = js("{transaction: {}}")
      // Change the category to "Split"
      req.transaction.category_name = "Split"

      // Calculate the remaining amount
      val remainingAmount = transactionDetail.amount.toInt() - splitResult.splitAmount

      // Create the subtransactions array with the original transaction and the new split
      val subtransaction1 = js("{}")
      subtransaction1.amount = remainingAmount
      subtransaction1.category_id = transactionDetail.category_id
      subtransaction1.deleted = false

      val subtransaction2 = js("{}")
      subtransaction2.amount = splitResult.splitAmount
      subtransaction2.category_id = splitResult.categoryId
      subtransaction1.deleted = false

      val subtransactions = js("[]")
      subtransactions.push(subtransaction1)
      subtransactions.push(subtransaction2)

      req.transaction.subtransactions = subtransactions

      ynab.transactions.updateTransaction(
        dataModel.selectedBudget!!.id, transactionDetail.id, req
      ).then { response ->
        dataModel.updateTransaction(transactionDetail, response.data.transaction)
      }.catch { error ->
        console.error("Error updating transaction: $error")
      }
    }

    fun onSplit(transactionDetail: TransactionDetail) {
      if (dataModel.transactionsStore !is DataState.Loaded<*>) {
        console.log("onSplit called when transactions not loaded")
        return
      }
      if (dataModel.categoriesStore !is DataState.Loaded<*>) {
        console.log("onSplit called when categories not loaded")
        return
      }
      val categories = (dataModel.categoriesStore as DataState.Loaded).data

      GlobalScope.launch {
        val splitResult = splitModal(transactionDetail, categories).getResult()

        if (splitResult == null) {
          return@launch
        } else {
          finishSplit(transactionDetail, splitResult)
        }
      }
    }

    fun onSelectBudget(budget: BudgetSummary, onlyUnapproved: Boolean) {
      dataModel.selectedBudget = budget

      // Load the transactions for the selected budget.
      dataModel.transactionsStore = DataState.Loading
      val txnType = if (onlyUnapproved) "unapproved" else ""
      ynab.transactions.getTransactions(budget.id, type = txnType).then { response ->
        val pairs = response.data.transactions.map { it.id to it }.toTypedArray()
        dataModel.transactionsStore = DataState.Loaded(mutableMapOf(*pairs))
      }

      // Load the categories for the selected budget.
      dataModel.categoriesStore = DataState.Loading
      ynab.categories.getCategories(budget.id).then { response ->
        val pairs = response.data.category_groups.flatMap { it.categories.toList() }.map { it.id to it }.toTypedArray()
        dataModel.categoriesStore = DataState.Loaded(mutableMapOf(*pairs))
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
                    transactionsTable(
                      dataModel.displayedTransactions,
                      onApprove = ::onApprove,
                      onUnapprove = ::onUnapprove,
                      onSplit = ::onSplit,
                    )
                  }
                }
              }
            }
          }

          // This part of the UI shows the categories for the selected budget.
          div().bind(dataModel.observeCategoriesStore()) { state ->
            borderedContainer {
              padding = 2.em
              vPanel(alignItems = AlignItems.CENTER, useWrappers = true) {
                padding = 2.em
                when (state) {
                  is DataState.Unloaded -> {
                    h4("no categories loaded!")
                  }

                  is DataState.Loading -> {
                    p {
                      icon("fas fa-spinner fa-xl fa-spin")
                    }
                    h4("loading categories…")
                  }

                  is DataState.Loaded -> {
                    categoriesTable(dataModel.displayedCategories)
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
