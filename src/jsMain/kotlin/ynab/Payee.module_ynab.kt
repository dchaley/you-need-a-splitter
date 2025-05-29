@file:Suppress(
  "INTERFACE_WITH_SUPERCLASS",
  "OVERRIDING_FINAL_MEMBER",
  "RETURN_TYPE_MISMATCH_ON_OVERRIDE",
  "CONFLICTING_OVERLOADS"
)
@file:JsModule("ynab")

package ynab

external interface Payee {
  var id: String
  var name: String
  var transfer_account_id: String?
    get() = definedExternally
    set(value) = definedExternally
  var deleted: Boolean
}
