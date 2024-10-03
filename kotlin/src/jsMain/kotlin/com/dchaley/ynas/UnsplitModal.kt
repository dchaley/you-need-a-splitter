package com.dchaley.ynas

import com.dchaley.ynas.util.toUsd
import io.kvision.form.select.selectInput
import io.kvision.html.*
import io.kvision.modal.Dialog
import io.kvision.panel.hPanel
import io.kvision.panel.vPanel
import io.kvision.state.ObservableValue
import io.kvision.state.bind
import io.kvision.state.bindTo
import io.kvision.utils.em
import ynab.Category
import ynab.TransactionDetail


fun unsplitModal(
  transaction: TransactionDetail,
  categories: Map<String, Category>,
): Dialog<String?> {
  val selectedCategory = ObservableValue<String?>(null)

  return Dialog("Unsplit transaction") {
    vPanel {
      p("Combine these categories:")
      ul {
        transaction.subtransactions.forEach {
          li("${it.category_name}: ${it.amount.toUsd()}")
        }
      }

      p("into this category:")
      selectInput(
        options = categories.values.map { it.id to "${it.category_group_name} > ${it.name}" },
      ).bindTo(selectedCategory)

      hPanel(spacing = 5) {
        marginTop = 1.em

        button("Unsplit", style = ButtonStyle.PRIMARY)
          .bind(selectedCategory) {
            disabled = it == null
          }.onClick {
            setResult(selectedCategory.value)
          }

        button("Cancel", style = ButtonStyle.WARNING)
          .onClick {
            setResult(null)
          }
      }
    }
  }
}
