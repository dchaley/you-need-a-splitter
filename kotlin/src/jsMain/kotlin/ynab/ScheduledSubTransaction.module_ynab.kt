@file:Suppress(
  "INTERFACE_WITH_SUPERCLASS",
  "OVERRIDING_FINAL_MEMBER",
  "RETURN_TYPE_MISMATCH_ON_OVERRIDE",
  "CONFLICTING_OVERLOADS"
)
@file:JsModule("ynab")

package ynab

external interface ScheduledSubTransaction {
  var id: String
  var scheduled_transaction_id: String
  var amount: Number
  var memo: String?
    get() = definedExternally
    set(value) = definedExternally
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
