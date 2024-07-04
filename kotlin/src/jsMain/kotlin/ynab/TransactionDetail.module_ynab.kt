@file:Suppress(
  "INTERFACE_WITH_SUPERCLASS",
  "OVERRIDING_FINAL_MEMBER",
  "RETURN_TYPE_MISMATCH_ON_OVERRIDE",
  "CONFLICTING_OVERLOADS"
)
@file:JsModule("ynab")
@file:JsNonModule

package ynab

external interface TransactionDetail {
  var id: String
  var date: String
  var amount: Number
  var memo: String?
  var cleared: Any
  var approved: Boolean
  var flag_color: Any?
  var account_id: String
  var payee_id: String?
  var category_id: String?
  var transfer_account_id: String?
  var transfer_transaction_id: String?
  var matched_transaction_id: String?
  var import_id: String?
  var import_payee_name: String?
  var import_payee_name_original: String?
  var debt_transaction_type: Any
  var deleted: Boolean
  var account_name: String
  var payee_name: String?
  var category_name: String?
  var subtransactions: Array<SubTransaction>
}
