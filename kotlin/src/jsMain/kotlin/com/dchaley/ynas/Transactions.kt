package com.dchaley.ynas

import com.dchaley.ynas.util.DataState
import com.dchaley.ynas.util.toUsd
import io.kvision.core.AlignItems
import io.kvision.core.Container
import io.kvision.core.JustifyContent
import io.kvision.core.WhiteSpace
import io.kvision.html.ButtonStyle
import io.kvision.html.button
import io.kvision.html.h4
import io.kvision.html.icon
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
              whiteSpace = WhiteSpace.NOWRAP
              content = transaction.date
            }
            cell(transaction.payee_name)
            cell(transaction.category_name)
            cell(transaction.memo)
            cell(transaction.amount.toUsd())
            cell {
              hPanel(spacing=2, justify = JustifyContent.SPACEBETWEEN) {
                whiteSpace = WhiteSpace.NOWRAP
                button("", "fas fa-code-branch fa-lg", style = ButtonStyle.SECONDARY) {
                  setAttribute("alt", "split")
                }
                button("", "fas fa-thumbs-up fa-lg", style = ButtonStyle.SECONDARY) {
                  setAttribute("alt", "approve")
                }
              }
            }
          }
        }
      }
    }
  }
}
