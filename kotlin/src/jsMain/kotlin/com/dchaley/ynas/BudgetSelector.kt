package com.dchaley.ynas

import com.dchaley.ynas.util.DataState
import io.kvision.core.Container
import io.kvision.form.check.checkBox
import io.kvision.html.*
import io.kvision.panel.hPanel
import io.kvision.panel.vPanel
import io.kvision.state.ObservableList
import io.kvision.state.bindEach
import ynab.BudgetSummary

fun Container.budgetSelector(
  budgetSummaries: DataState<ObservableList<BudgetSummary>>,
  onBudgetSelect: (BudgetSummary, Boolean) -> Unit
) {
  when (budgetSummaries) {
    is DataState.Unloaded, DataState.Loading -> {
      hPanel(spacing = 5) {
        p {
          icon("fas fa-spinner fa-spin")
        }
        div("Loading budgets…")
      }
    }

    is DataState.Loaded -> {
      vPanel(spacing = 5) {
        span("Select a budget:")
        val c = checkBox(true, label = "Only show unapproved transactions")

        hPanel(spacing = 5).bindEach(budgetSummaries.data) { budget ->
          button(text = budget.name) {
            onClick {
              onBudgetSelect(budget, c.value)
            }
          }
        }
      }
    }
  }
}
