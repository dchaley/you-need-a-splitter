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

fun Container.transactionsList(
  transactions: ObservableList<TransactionDetail>,
  onApprove: ((TransactionDetail) -> Unit)? = null
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
        if (transaction.category_name == "Split") {
          cell {
            verticalAlign = VerticalAlign.MIDDLE
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
        } else {
          cell(transaction.category_name) {
            verticalAlign = VerticalAlign.MIDDLE
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
            button("", "fas fa-pen-fancy fa-lg", style = ButtonStyle.SECONDARY) {
              setAttribute("aria-label", "recategorize")
            }
            button("", "fas fa-code-branch fa-lg", style = ButtonStyle.SECONDARY) {
              setAttribute("aria-label", "split")
            }
            button("", "fas fa-thumbs-up fa-lg", style = ButtonStyle.SECONDARY) {
              setAttribute("aria-label", "approve")
            }.onClick { onApprove?.invoke(transaction) }
          }
        }
      }
    }
}
