package com.dchaley.ynas

import com.dchaley.ynas.util.percentOf
import com.dchaley.ynas.util.toUsd
import io.kvision.core.*
import io.kvision.html.*
import io.kvision.panel.gridPanel
import io.kvision.panel.hPanel
import io.kvision.state.ObservableList
import io.kvision.state.bindEach
import io.kvision.table.*
import ynab.TransactionDetail

fun equalTester(a: TransactionDetail, b: TransactionDetail): Boolean {
  // We need this if we update txn fields directly.
  // If we only ever update by replacing the object, this is unnecessary.
  // If we had fixed fields (vs mutable), there'd be no way to update the fields directly.

  // Make sure every field is equal.
  val x = a.id == b.id &&
          a.date == b.date &&
          a.amount == b.amount &&
          a.memo == b.memo &&
          a.cleared == b.cleared &&
          a.approved == b.approved &&
          a.flag_color == b.flag_color &&
          a.account_id == b.account_id &&
          a.payee_id == b.payee_id &&
          a.category_id == b.category_id &&
          a.transfer_account_id == b.transfer_account_id &&
          a.transfer_transaction_id == b.transfer_transaction_id &&
          a.matched_transaction_id == b.matched_transaction_id &&
          a.import_id == b.import_id &&
          a.import_payee_name == b.import_payee_name &&
          a.import_payee_name_original == b.import_payee_name_original &&
          a.debt_transaction_type == b.debt_transaction_type &&
          a.deleted == b.deleted &&
          a.account_name == b.account_name &&
          a.payee_name == b.payee_name &&
          a.category_name == b.category_name &&
          a.subtransactions.contentEquals(b.subtransactions)

  return x
}

fun Container.transactionsTable(
  transactions: ObservableList<TransactionDetail>,
  onApprove: ((TransactionDetail) -> Unit)? = null,
  onUnapprove: ((TransactionDetail) -> Unit)? = null,
  onSplit: ((TransactionDetail) -> Unit)? = null,
) {
  val columns = listOf("Date", "Payee", "Category", "Memo", "Amount", "Actions")
  val tableStyling = setOf(TableType.STRIPED, TableType.HOVER)

  table(columns, tableStyling, responsiveType = ResponsiveType.RESPONSIVE)
    .bindEach(transactions, equalizer = ::equalTester) { transaction ->
      row {
        cell {
          verticalAlign = VerticalAlign.MIDDLE
          whiteSpace = WhiteSpace.NOWRAP
          content = transaction.date
        }
        cell(transaction.payee_name) {
          verticalAlign = VerticalAlign.MIDDLE
        }
        cell {
          verticalAlign = VerticalAlign.MIDDLE
          if (transaction.category_name == "Split") {
            small {
              gridPanel(columnGap = 3) {
                transaction.subtransactions.forEachIndexed { index, subTransaction ->
                  val row = index + 1
                  add(Div(subTransaction.category_name), 1, row)
                  add(Div(subTransaction.amount.toUsd()), 2, row)
                  add(Div(subTransaction.amount.percentOf(transaction.amount)), 3, row)
                }
              }
            }
          }
        }
        cell {
          verticalAlign = VerticalAlign.MIDDLE
          if (transaction.memo.orEmpty().isNotEmpty()) {
            link("", "#") {
              icon = "fas fa-note-sticky fa"
              setAttribute("aria-label", "view memo")
              enableTooltip(TooltipOptions(transaction.memo))
            }
          } else {
            content = ""
          }
        }
        cell(transaction.amount.toUsd()) {
          verticalAlign = VerticalAlign.MIDDLE
        }
        cell {
          verticalAlign = VerticalAlign.MIDDLE
          hPanel(spacing = 2, justify = JustifyContent.SPACEBETWEEN) {
            whiteSpace = WhiteSpace.NOWRAP
            button("", "fas fa-pencil fa-lg", style = ButtonStyle.SECONDARY) {
              setAttribute("aria-label", "recategorize")
            }
            if (transaction.category_name != "Split") {
              button("", style = ButtonStyle.SECONDARY) {
                div {
                  useSnabbdomDistinctKey()
                  icon("fas fa-code-branch fa-lg")
                }
                setAttribute("aria-label", "split")
              }.onClick { onSplit?.invoke(transaction) }
            } else {
              button("", style = ButtonStyle.SECONDARY) {
                setAttribute("hidden", "true")
                div {
                  useSnabbdomDistinctKey()
                  span(className = "fa-layers fa-fw") {
                    i(className = "fas fa-code-branch") {
                      setAttribute("data-fa-transform", "shrink-2")
                    }
                    icon("fas fa-slash")
                  }
                }
              }

            }
            if (transaction.approved) {
              button("", style = ButtonStyle.OUTLINESECONDARY) {
                div {
                  useSnabbdomDistinctKey()
                  icon("fas fa-thumbs-down fa-lg")
                }
                setAttribute("aria-label", "unapprove")
              }.onClick { onUnapprove?.invoke(transaction) }
            } else {
              button("", style = ButtonStyle.SECONDARY) {
                div {
                  useSnabbdomDistinctKey()
                  icon("fas fa-thumbs-up fa-lg")
                }
                setAttribute("aria-label", "approve")
              }.onClick { onApprove?.invoke(transaction) }
            }
          }
        }
      }
    }
}
