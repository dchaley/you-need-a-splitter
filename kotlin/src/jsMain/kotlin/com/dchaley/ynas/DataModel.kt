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
  private val defaultCategoryMapObservable = ObservableValue<Map<String, String>>(emptyMap())
  private val defaultSplitPercentageMapObservable = ObservableValue<Map<String, String>>(emptyMap())

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

  /**
   * Gets the default category ID for the currently selected budget.
   * @return The default category ID, or null if not found or no budget selected
   */
  var defaultCategoryId: String?
    get() {
      val budgetId = selectedBudget?.id ?: return null
      return defaultCategoryMapObservable.value[budgetId]
    }
    set(value) {
      val budgetId = selectedBudget?.id ?: ""
      if (value == "") {
        // Remove the current budget's entry from the map
        defaultCategoryMapObservable.value = defaultCategoryMapObservable.value.filter { it.key != budgetId }
      } else {
        // Otherwise, update the map with the new value
        val newMap = defaultCategoryMapObservable.value.toMutableMap()
        newMap[budgetId] = value ?: ""
        defaultCategoryMapObservable.value = newMap.toMap()
      }
    }

  /**
   * Gets the default split percentage for the currently selected budget.
   * @return The default split percentage string, or null if not found or no budget selected
   */
  var defaultSplitPercentage: String?
    get() {
      val budgetId = selectedBudget?.id ?: return null
      return defaultSplitPercentageMapObservable.value[budgetId]
    }
    set(value) {
      val budgetId = selectedBudget?.id ?: ""
      if (value == "") {
        // Remove the current budget's entry from the map
        defaultSplitPercentageMapObservable.value = defaultSplitPercentageMapObservable.value.filter { it.key != budgetId }
      } else {
        // Otherwise, update the map with the new value
        val newMap = defaultSplitPercentageMapObservable.value.toMutableMap()
        newMap[budgetId] = value ?: ""
        defaultSplitPercentageMapObservable.value = newMap.toMap()
      }
    }

  init {
    defaultCategoryMapObservable.subscribe {
      // Save to cookie when value changes
      if (it.isNotEmpty()) {
        try {
          CookieUtil.setDefaultCategoryId(it)
        } catch (e: Exception) {
          console.error("Error saving default category map to cookie: $e")
        }
      }
    }

    defaultSplitPercentageMapObservable.subscribe {
      // Save to cookie when value changes
      if (it.isNotEmpty()) {
        try {
          CookieUtil.setDefaultSplitPercentage(it)
        } catch (e: Exception) {
          console.error("Error saving default split percentage map to cookie: $e")
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
    // Create a derived observable that updates when either the map or selected budget changes
    val derivedObservable = ObservableValue(defaultCategoryId)

    // Update the derived observable when the map changes
    defaultCategoryMapObservable.subscribe {
      derivedObservable.value = it[selectedBudget?.id]
    }

    // Update the derived observable when the selected budget changes
    selectedBudgetObservable.subscribe {
      derivedObservable.value = defaultCategoryMapObservable.value[it?.id]
    }

    derivedObservable.subscribe {
      if (defaultCategoryId != it) {
        defaultCategoryId = it
      }
    }

    return derivedObservable
  }

  /**
   * Loads the default category map from the cookie.
   * This should be called after categories are loaded.
   */
  fun loadDefaultCategoryFromCookie() {
    try {
      val categoryMap = CookieUtil.getDefaultCategoryId()
      if (categoryMap.isNotEmpty()) {
        defaultCategoryMapObservable.value = categoryMap
      }
    } catch (e: Exception) {
      console.error("Error loading default category map from cookie: $e")
    }
  }

  /**
   * Loads the default split percentage map from the cookie.
   * This should be called after categories are loaded.
   */
  fun loadDefaultSplitPercentageFromCookie() {
    try {
      val splitPercentageMap = CookieUtil.getDefaultSplitPercentage()
      if (splitPercentageMap.isNotEmpty()) {
        defaultSplitPercentageMapObservable.value = splitPercentageMap
      }
    } catch (e: Exception) {
      console.error("Error loading default split percentage map from cookie: $e")
    }
  }

  /**
   * Observes the default split percentage for the currently selected budget.
   * @return An observable value that updates when either the map or selected budget changes
   */
  fun observeDefaultSplitPercentage(): ObservableValue<String?> {
    // Create a derived observable that updates when either the map or selected budget changes
    val derivedObservable = ObservableValue(defaultSplitPercentage)

    // Update the derived observable when the map changes
    defaultSplitPercentageMapObservable.subscribe {
      derivedObservable.value = it[selectedBudget?.id]
    }

    // Update the derived observable when the selected budget changes
    selectedBudgetObservable.subscribe {
      derivedObservable.value = defaultSplitPercentageMapObservable.value[it?.id]
    }

    derivedObservable.subscribe {
      if (defaultSplitPercentage != it) {
        defaultSplitPercentage = it
      }
    }

    return derivedObservable
  }
}
