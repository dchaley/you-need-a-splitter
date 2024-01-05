package com.dchaley.ynas

import com.dchaley.ynas.util.DataState
import io.kvision.core.Container
import io.kvision.html.button
import io.kvision.html.div
import io.kvision.html.icon
import io.kvision.html.label
import io.kvision.panel.hPanel
import io.kvision.state.ObservableList
import io.kvision.state.bindEach
import ynab.BudgetSummary

fun Container.budgetSelector(budgetSummaries: DataState<ObservableList<BudgetSummary>>, onBudgetSelect: (BudgetSummary) -> Unit) {
  when (budgetSummaries) {
    is DataState.Unloaded, DataState.Loading -> {
      hPanel(spacing = 5) {
        div {
          icon("fas fa-spinner fa-spin")
        }
        div("Loading budgets…")
      }
    }
    is DataState.Loaded -> {
      label("Select a budget:")
      hPanel(spacing = 5) {
      }.bindEach(budgetSummaries.data) { budget ->
        button(text = budget.name) {
          onClick {
            onBudgetSelect(budget)
          }
        }
      }
    }
  }
}
