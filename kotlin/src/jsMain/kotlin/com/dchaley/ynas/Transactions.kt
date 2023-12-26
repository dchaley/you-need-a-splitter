package com.dchaley.ynas

import com.dchaley.ynas.util.DataState
import com.dchaley.ynas.util.percentOf
import com.dchaley.ynas.util.toUsd
import io.kvision.core.*
import io.kvision.html.*
import io.kvision.panel.gridPanel
import io.kvision.panel.hPanel
import io.kvision.panel.vPanel
import io.kvision.table.*
import ynab.TransactionDetail

fun Container.transactionsList(transactionsState : DataState<List<TransactionDetail>>) {
  val columns = listOf("Date", "Payee", "Category", "Memo", "Amount", "Actions")
  val tableStyling = setOf(TableType.STRIPED, TableType.HOVER)
  val loadingStyling = tableStyling - TableType.HOVER

  when (transactionsState) {
    is DataState.Unloaded -> {
      table(columns, loadingStyling, responsiveType = ResponsiveType.RESPONSIVE) {
        row {
          cell() {
            setAttribute("colspan", columns.size.toString())
            vPanel(alignItems = AlignItems.CENTER, useWrappers = true) {
              h4("no transactions loaded!")
            }
          }
        }
      }

    }
    is DataState.Loading -> {
      table(columns, loadingStyling, responsiveType = ResponsiveType.RESPONSIVE) {
        row {
          cell() {
            setAttribute("colspan", columns.size.toString())
            vPanel(alignItems = AlignItems.CENTER, useWrappers = true) {
              icon("fas fa-spinner fa-xl fa-spin")
              h4("loading transactions…")
            }
          }
        }
      }
    }
    is DataState.Loaded -> {
      val transactions = transactionsState.data
      table(columns, tableStyling, responsiveType = ResponsiveType.RESPONSIVE) {
        for (transaction in transactions) {
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
                    icon("fas fa-note-sticky fa")
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
              hPanel(spacing=2, justify = JustifyContent.SPACEBETWEEN) {
                whiteSpace = WhiteSpace.NOWRAP
                button("", "fas fa-pen-fancy fa-lg", style = ButtonStyle.SECONDARY) {
                  setAttribute("aria-label", "recategorize")
                }
                button("", "fas fa-code-branch fa-lg", style = ButtonStyle.SECONDARY) {
                  setAttribute("aria-label", "split")
                }
                button("", "fas fa-thumbs-up fa-lg", style = ButtonStyle.SECONDARY) {
                  setAttribute("aria-label", "approve")
                }
              }
            }
          }
        }
      }
    }
  }
}
