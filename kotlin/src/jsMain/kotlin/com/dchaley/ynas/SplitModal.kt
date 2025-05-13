package com.dchaley.ynas

import com.dchaley.ynas.util.toUsd
import io.kvision.core.AlignContent
import io.kvision.form.select.select
import io.kvision.form.text.textInput
import io.kvision.html.*
import io.kvision.modal.Dialog
import io.kvision.panel.hPanel
import io.kvision.panel.tab
import io.kvision.panel.tabPanel
import io.kvision.panel.vPanel
import io.kvision.state.ObservableValue
import io.kvision.state.bind
import io.kvision.state.bindTo
import io.kvision.utils.em
import io.kvision.utils.px
import ynab.Category
import ynab.TransactionDetail
import kotlin.math.roundToInt


data class SplitResult(
  val categoryId: String,
  val splitAmount: Int,
)

fun splitModal(
  transaction: TransactionDetail,
  categories: Map<String, Category>,
  defaultCategoryId: String? = null
): Dialog<SplitResult?> {
  val splitCategoryId = ObservableValue<String?>(defaultCategoryId)

  val txnAmount = transaction.amount.toInt()
  val category = categories[transaction.category_id]

  val splitAmountStr = ObservableValue("0")
  val splitPercentStr = ObservableValue("0")
  val splitAmount = ObservableValue(0)
  val remainingAmountStr = ObservableValue((txnAmount / 1000).toString())
  val remainingAmount = ObservableValue(txnAmount)

  val setRemainingAmount = fun(newRemainingAmount: Int) {
    if (newRemainingAmount % 10 != 0) {
      throw IllegalArgumentException("Remaining amount must be a multiple of 10 not $newRemainingAmount")
    }
    if (newRemainingAmount != remainingAmount.value) {
      remainingAmount.value = newRemainingAmount
    }

    val newRemainingStr = (0.001 * newRemainingAmount).toString()
    if (newRemainingStr != remainingAmountStr.value) {
      remainingAmountStr.value = newRemainingStr
    }
  }

  val setSplitAmount = fun(newSplitAmount: Int) {
    if (newSplitAmount % 10 != 0) {
      throw IllegalArgumentException("Split amount must be a multiple of 10 not $newSplitAmount")
    }
    if (newSplitAmount != splitAmount.value) {
      splitAmount.value = newSplitAmount
    }

    val newSplitStr = (0.001 * newSplitAmount).toString()
    if (newSplitStr != splitAmountStr.value) {
      splitAmountStr.value = newSplitStr
    }
  }

  val setSplitPercent = fun(newSplitAmount: Int) {
    val newSplitPercentStr = ((100.0 * newSplitAmount) / txnAmount).toString()
    if (newSplitPercentStr != splitPercentStr.value) {
      splitPercentStr.value = newSplitPercentStr
    }
  }

  val dollarsStrToYnabThousands = fun(dollarsStr: String): Int? {
    val amountDbl = dollarsStr.toDoubleOrNull()
    // Discard sub-cent precision from input
    return amountDbl?.times(1000)?.toInt()?.let { it / 10 * 10 }
  }

  val updateFromSplitAmount = fun(newSplitAmountStr: String) {
    val newSplitAmount = dollarsStrToYnabThousands(newSplitAmountStr)
    if (newSplitAmount == null) {
      return
    }

    splitAmount.value = newSplitAmount
    setSplitPercent(newSplitAmount)
    setRemainingAmount(txnAmount - splitAmount.value)
  }

  val updateFromSplitPercent = fun(newSplitPercentStr: String) {
    val percent = newSplitPercentStr.toDoubleOrNull()
    if (percent == null) {
      return
    }

    // Remove sub-cent precision from result
    val newSplitAmount = (0.01 * percent * txnAmount).roundToInt() / 10 * 10
    setSplitAmount(newSplitAmount)
    setRemainingAmount(txnAmount - splitAmount.value)
  }

  val updateFromRemainingAmount = fun(newRemainingAmountStr: String) {
    val newRemainingAmount = dollarsStrToYnabThousands(newRemainingAmountStr)
    if (newRemainingAmount == null) {
      return
    }

    remainingAmount.value = newRemainingAmount
    val newSplitAmount = txnAmount - newRemainingAmount
    setSplitAmount(newSplitAmount)
    setSplitPercent(newSplitAmount)
  }

  return Dialog(
    "Split ${transaction.amount.toUsd()} (${category?.renderCategory()})"
  ) {
    addBeforeDisposeHook(splitAmountStr.subscribe {
      updateFromSplitAmount(it)
    })

    addBeforeDisposeHook(splitPercentStr.subscribe {
      updateFromSplitPercent(it)
    })

    addBeforeDisposeHook(remainingAmountStr.subscribe {
      updateFromRemainingAmount(it)
    })

    vPanel(spacing = 6) {
      div {
        p("Into:")
        select(
          options = categories.values.map { it.id to it.renderCategory() },
        ).bindTo(splitCategoryId)
      }
      div {
        alignContent = AlignContent.CENTER

        bind(splitCategoryId) {
          if (it == null) {
            return@bind
          }
          borderedContainer(3.px, size = 2.px) {
            tabPanel {
              tab("Percent", "fa-solid fa-percent") {
                textInput {
                  placeholder = "% split"
                }
                  .bindTo(splitPercentStr)
              }
              tab("Amount", "fa-solid fa-dollar-sign") {
                textInput {
                  placeholder = "$ split"
                }.bindTo(splitAmountStr)
              }
              tab("Leftover", "fa-solid fa-calculator") {
                textInput {
                  placeholder = "$ split"
                }.bindTo(remainingAmountStr)
              }
            }

          }
        }
      }

      div {
        bind(remainingAmount) { remainingAmount ->
          bind(splitCategoryId) { splitCategoryId ->
            val splitCategory = categories[splitCategoryId]
            bind(splitAmount) { splitAmt ->
              ul {
                li {
                  content = "${category?.renderCategory()}: ${remainingAmount.toUsd()}"
                }

                if (splitCategory != null) {
                  li {
                    content = "${splitCategory.renderCategory()}: ${splitAmt.toUsd()}"
                  }
                }
              }
            }
          }
        }
      }

      hPanel(spacing = 5) {
        marginTop = 1.em

        button("Split", style = ButtonStyle.PRIMARY).bind(splitCategoryId) {
          disabled = it == null
        }.onClick {
          setResult(SplitResult(splitCategoryId.value!!, splitAmount.value))
        }

        button("Cancel", style = ButtonStyle.WARNING).onClick {
          setResult(null)
        }
      }
    }
  }
}
