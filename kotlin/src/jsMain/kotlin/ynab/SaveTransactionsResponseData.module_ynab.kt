@file:Suppress(
  "INTERFACE_WITH_SUPERCLASS",
  "OVERRIDING_FINAL_MEMBER",
  "RETURN_TYPE_MISMATCH_ON_OVERRIDE",
  "CONFLICTING_OVERLOADS"
)
@file:JsModule("ynab")
@file:JsNonModule

package ynab

external interface SaveTransactionsResponseData {
  var transaction_ids: Array<String>
  var transaction: TransactionDetail?
  var transactions: Array<TransactionDetail>?
  var duplicate_import_ids: Array<String>?
  var server_knowledge: Number
}
