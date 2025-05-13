package com.dchaley.ynas

import com.dchaley.ynas.util.CookieUtil
import com.dchaley.ynas.util.DataState
import com.dchaley.ynas.util.replaceAll
import io.kvision.state.ObservableList
import io.kvision.state.ObservableListWrapper
import io.kvision.state.ObservableValue
import io.kvision.state.observableListOf
import ynab.BudgetSummary
import ynab.Category
import ynab.TransactionDetail

class DataModel {
  private val budgetsObservable = ObservableValue<DataState<ObservableList<BudgetSummary>>>(DataState.Unloaded)
  private val selectedBudgetObservable = ObservableValue<BudgetSummary?>(null)
  private val transactionsStoreObservable =
    ObservableValue<DataState<MutableMap<String, TransactionDetail>>>(DataState.Unloaded)
  private val displayedTransactionsObservable =
    ObservableValue(observableListOf<TransactionDetail>())
  private val categoriesStoreObservable = ObservableValue<DataState<MutableMap<String, Category>>>(DataState.Unloaded)
  private val displayedCategoriesObservable = ObservableValue(observableListOf<Category>())
  private val defaultCategoryIdObservable = ObservableValue<String?>(null)

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

  var categoriesStore: DataState<MutableMap<String, Category>>
    get() = categoriesStoreObservable.value
    set(value) {
      categoriesStoreObservable.value = value
      updateDisplayedCategories()
    }

  var displayedCategories: ObservableList<Category>
    get() = displayedCategoriesObservable.value
    set(value) {
      displayedCategoriesObservable.value = value
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

  var defaultCategoryId: String?
    get() = defaultCategoryIdObservable.value
    set(value) {
      defaultCategoryIdObservable.value = value
    }

  init {
    defaultCategoryIdObservable.subscribe {
      // Save to cookie when value changes
      if (it != null) {
        try {
          CookieUtil.setDefaultCategoryId(it)
        } catch (e: Exception) {
          console.error("Error saving default category to cookie: $e")
        }
      }
    }
  }

  fun updateDisplayedCategories() {
    when (categoriesStore) {
      is DataState.Unloaded -> {
        displayedCategories = observableListOf()
      }

      is DataState.Loading -> {
        displayedCategories = observableListOf()
      }

      is DataState.Loaded -> {
        val categories = (categoriesStore as DataState.Loaded<MutableMap<String, Category>>).data.values
        val sorted = categories.sortedBy { it.name }
        (displayedCategories as ObservableListWrapper).replaceAll(sorted)
      }
    }
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
        (displayedTransactions as ObservableListWrapper).replaceAll(sorted)
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

  fun observeCategoriesStore(): ObservableValue<DataState<MutableMap<String, Category>>> {
    return categoriesStoreObservable
  }

  fun observeDefaultCategoryId(): ObservableValue<String?> {
    return defaultCategoryIdObservable
  }

  /**
   * Loads the default category ID from the cookie.
   * This should be called after categories are loaded.
   */
  fun loadDefaultCategoryFromCookie() {
    try {
      val categoryId = CookieUtil.getDefaultCategoryId()
      if (categoryId != null) {
        defaultCategoryId = categoryId
      }
    } catch (e: Exception) {
      console.error("Error loading default category from cookie: $e")
    }
  }
}
