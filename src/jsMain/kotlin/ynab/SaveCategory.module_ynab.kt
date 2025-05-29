@file:Suppress(
  "INTERFACE_WITH_SUPERCLASS",
  "OVERRIDING_FINAL_MEMBER",
  "RETURN_TYPE_MISMATCH_ON_OVERRIDE",
  "CONFLICTING_OVERLOADS"
)
@file:JsModule("ynab")

package ynab

external interface SaveCategory {
  var name: String?
    get() = definedExternally
    set(value) = definedExternally
  var note: String?
    get() = definedExternally
    set(value) = definedExternally
  var category_group_id: String?
    get() = definedExternally
    set(value) = definedExternally
}
