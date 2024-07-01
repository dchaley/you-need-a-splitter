package com.dchaley.ynas

import com.dchaley.ynas.util.DataState
import com.dchaley.ynas.util.replaceAll
import io.kvision.state.ObservableList
import io.kvision.state.ObservableListWrapper
import io.kvision.state.ObservableValue
import io.kvision.state.observableListOf
import ynab.BudgetSummary
import ynab.TransactionDetail

class DataModel {
  private val budgetsObservable = ObservableValue<DataState<ObservableList<BudgetSummary>>>(DataState.Unloaded)
  private val selectedBudgetObservable = ObservableValue<BudgetSummary?>(null)
  private val transactionsStoreObservable =
    ObservableValue<DataState<MutableMap<String, TransactionDetail>>>(DataState.Unloaded)
  private val displayedTransactionsObservable =
    ObservableValue(observableListOf<TransactionDetail>())

  var budgets: DataState<ObservableList<BudgetSummary>>
    get() = budgetsObservable.value
    set(value) {
      budgetsObservable.value = value
    }

  var selectedBudget: BudgetSummary?
    get() = selectedBudgetObservable.value
    set(value) {
      selectedBudgetObservable.value = value
    }

  var transactionsStore: DataState<MutableMap<String, TransactionDetail>>
    get() = transactionsStoreObservable.value
    set(value) {
      transactionsStoreObservable.value = value
      updateDisplayedTxns()
    }

  var displayedTransactions: ObservableList<TransactionDetail>
    get() = displayedTransactionsObservable.value
    set(value) {
      displayedTransactionsObservable.value = value
    }

  fun updateDisplayedTxns() {
    when (transactionsStore) {
      is DataState.Unloaded -> {
        displayedTransactions = observableListOf()
      }

      is DataState.Loading -> {
        displayedTransactions = observableListOf()
      }

      is DataState.Loaded -> {
        val txns = (transactionsStore as DataState.Loaded<MutableMap<String, TransactionDetail>>).data.values
        // this could sort by anything… but for now, just sort by date
        val sorted = txns.sortedBy { it.date }

        // We don't want to trigger a re-render if the list is already loaded.
        // Setting the value outright (vs replacing contents) triggers a higher-level re-render.
        if (displayedTransactions is DataState.Loaded<*>) {
          // The transaction store itself is not changing: it is still loaded.
          // Clients observe the observable list of transactions.
          val existingList = (displayedTransactions as DataState.Loaded<ObservableList<TransactionDetail>>).data
          (existingList as ObservableListWrapper).replaceAll(sorted)
        } else {
          // If we aren't already loaded, just set the value.
          // This will
          displayedTransactions = observableListOf(*sorted.toTypedArray())
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

  fun observeDisplayedTransactions(): ObservableValue<ObservableList<TransactionDetail>> {
    return displayedTransactionsObservable
  }
}
