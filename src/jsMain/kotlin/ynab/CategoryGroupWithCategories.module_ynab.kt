@file:Suppress(
  "INTERFACE_WITH_SUPERCLASS",
  "OVERRIDING_FINAL_MEMBER",
  "RETURN_TYPE_MISMATCH_ON_OVERRIDE",
  "CONFLICTING_OVERLOADS"
)
@file:JsModule("ynab")

package ynab

external interface CategoryGroupWithCategories {
  var id: String
  var name: String
  var hidden: Boolean
  var deleted: Boolean
  var categories: Array<Category>
}
