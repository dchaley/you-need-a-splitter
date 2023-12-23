package com.dchaley.ynas

import io.kvision.core.Container
import io.kvision.html.*
import io.kvision.panel.hPanel
import io.kvision.state.bind
import io.kvision.state.bindEach
import kotlinx.coroutines.flow.StateFlow
import ynab.BudgetSummary

fun Container.budgetSelector(budgetSummaries: StateFlow<List<BudgetSummary>>, onBudgetSelect: (BudgetSummary) -> Unit) {

  div().bind(budgetSummaries) { budgets ->
    if (budgets.isEmpty()) {
      hPanel(spacing = 5) {
        div {
          icon("fas fa-spinner fa-spin")
        }
        div("Loading budgets…")
      }
    }
    else {
      label("Select a budget:")
      hPanel(spacing = 5) {
      }.bindEach(budgetSummaries) { budget ->
        button(text = budget.name) {
          onClick {
            onBudgetSelect(budget)
          }
        }
      }
    }
  }
}
