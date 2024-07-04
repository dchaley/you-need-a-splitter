@file:Suppress(
  "INTERFACE_WITH_SUPERCLASS",
  "OVERRIDING_FINAL_MEMBER",
  "RETURN_TYPE_MISMATCH_ON_OVERRIDE",
  "CONFLICTING_OVERLOADS"
)
@file:JsModule("ynab")
@file:JsNonModule

package ynab

external class BudgetSummary {
  var id: String
  var name: String
  var last_modified_on: String?
  var first_month: String?
  var last_month: String?
  var date_format: DateFormat?
  var currency_format: CurrencyFormat?
  // var accounts: Array<Account>?
}
