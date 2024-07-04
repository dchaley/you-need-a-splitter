@file:Suppress(
  "INTERFACE_WITH_SUPERCLASS",
  "OVERRIDING_FINAL_MEMBER",
  "RETURN_TYPE_MISMATCH_ON_OVERRIDE",
  "CONFLICTING_OVERLOADS"
)
@file:JsModule("ynab")
@file:JsNonModule

package ynab

external interface SubTransaction {
  var id: String
  var transaction_id: String
  var amount: Number
  var memo: String?
  var payee_id: String?
  var payee_name: String?
  var category_id: String?
  var category_name: String?
  var transfer_account_id: String?
  var transfer_transaction_id: String?
  var deleted: Boolean
}
