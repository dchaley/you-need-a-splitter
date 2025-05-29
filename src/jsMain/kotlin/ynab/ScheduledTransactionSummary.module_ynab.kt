@file:Suppress(
  "INTERFACE_WITH_SUPERCLASS",
  "OVERRIDING_FINAL_MEMBER",
  "RETURN_TYPE_MISMATCH_ON_OVERRIDE",
  "CONFLICTING_OVERLOADS"
)
@file:JsModule("ynab")

package ynab

external interface ScheduledTransactionSummary {
  var id: String
  var date_first: String
  var date_next: String
  var frequency: Any
  var amount: Number
  var memo: String?
    get() = definedExternally
    set(value) = definedExternally
  var flag_color: Any?
    get() = definedExternally
    set(value) = definedExternally
  var flag_name: String?
    get() = definedExternally
    set(value) = definedExternally
  var account_id: String
  var payee_id: String?
    get() = definedExternally
    set(value) = definedExternally
  var category_id: String?
    get() = definedExternally
    set(value) = definedExternally
  var transfer_account_id: String?
    get() = definedExternally
    set(value) = definedExternally
  var deleted: Boolean
}
