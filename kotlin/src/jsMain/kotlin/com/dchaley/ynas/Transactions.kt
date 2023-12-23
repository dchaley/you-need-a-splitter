package com.dchaley.ynas

import io.kvision.core.Container
import io.kvision.html.*
import ynab.TransactionDetail

fun Container.transactionsList(transactions: List<TransactionDetail>) {
  table {
    thead {
      tr {
        th("Date")
        th("Payee")
        th("Category")
        th("Memo")
        th("Amount")
      }
    }
    tbody {
      for (transaction in transactions) {
        tr {
          td(transaction.date)
          td(transaction.payee_name)
          td(transaction.category_name)
          td(transaction.memo)
          td(transaction.amount.toString())
        }
      }
    }
  }
}
