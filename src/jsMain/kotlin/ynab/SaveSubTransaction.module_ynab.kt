@file:Suppress(
  "INTERFACE_WITH_SUPERCLASS",
  "OVERRIDING_FINAL_MEMBER",
  "RETURN_TYPE_MISMATCH_ON_OVERRIDE",
  "CONFLICTING_OVERLOADS"
)
@file:JsModule("ynab")
@file:JsNonModule

package ynab

external interface SaveSubTransaction {
  var amount: Number
  var payee_id: String?
  var payee_name: String?
  var category_id: String?
  var memo: String?
}
