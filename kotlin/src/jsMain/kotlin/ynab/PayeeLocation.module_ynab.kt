@file:Suppress(
  "INTERFACE_WITH_SUPERCLASS",
  "OVERRIDING_FINAL_MEMBER",
  "RETURN_TYPE_MISMATCH_ON_OVERRIDE",
  "CONFLICTING_OVERLOADS"
)
@file:JsModule("ynab")

package ynab

external interface PayeeLocation {
  var id: String
  var payee_id: String
  var latitude: String
  var longitude: String
  var deleted: Boolean
}
