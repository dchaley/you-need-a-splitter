package com.dchaley.ynas

import io.kvision.core.AlignContent
import io.kvision.form.select.select
import io.kvision.html.ButtonStyle
import io.kvision.html.button
import io.kvision.html.div
import io.kvision.html.p
import io.kvision.modal.Dialog
import io.kvision.panel.hPanel
import io.kvision.panel.vPanel
import io.kvision.state.ObservableValue
import io.kvision.state.bind
import io.kvision.state.bindTo
import io.kvision.utils.em
import ynab.Category
import ynab.TransactionDetail

/**
 * Result of the category selection modal
 */
data class CategoryResult(
  val categoryId: String,
)

/**
 * Creates a modal dialog for selecting a category for a transaction
 *
 * @param transaction The transaction to edit
 * @param categories Map of category IDs to Category objects
 * @return A Dialog that will return a CategoryResult when confirmed, or null if canceled
 */
fun categoryModal(
  transaction: TransactionDetail,
  categories: Map<String, Category>,
): Dialog<CategoryResult?> {
  val selectedCategoryId = ObservableValue(transaction.category_id)
  val category = categories[transaction.category_id]

  return Dialog(
    "Change Category for ${transaction.payee_name ?: "Transaction"}"
  ) {
    vPanel(spacing = 6) {
      div {
        p("Select a category:")
        select(
          options = categories.values.map { it.id to it.renderCategory() },
        ).bindTo(selectedCategoryId)
      }

      div {
        alignContent = AlignContent.CENTER

        div().bind(selectedCategoryId) { categoryId ->
          val selectedCategory = categories[categoryId]
          if (selectedCategory != null) {
            p("New category: ${selectedCategory.renderCategory()}")
          }
        }
      }

      hPanel(spacing = 5) {
        marginTop = 1.em

        button("Save", style = ButtonStyle.PRIMARY) {
          disabled = selectedCategoryId.value == null
        }.onClick {
          setResult(CategoryResult(selectedCategoryId.value!!))
        }

        button("Cancel", style = ButtonStyle.WARNING).onClick {
          setResult(null)
        }
      }
    }
  }
}
