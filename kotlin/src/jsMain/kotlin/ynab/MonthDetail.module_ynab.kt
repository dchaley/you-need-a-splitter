@file:Suppress(
  "INTERFACE_WITH_SUPERCLASS",
  "OVERRIDING_FINAL_MEMBER",
  "RETURN_TYPE_MISMATCH_ON_OVERRIDE",
  "CONFLICTING_OVERLOADS"
)
@file:JsModule("ynab")

package ynab

external interface MonthDetail {
  var month: String
  var note: String?
    get() = definedExternally
    set(value) = definedExternally
  var income: Number
  var budgeted: Number
  var activity: Number
  var to_be_budgeted: Number
  var age_of_money: Number?
    get() = definedExternally
    set(value) = definedExternally
  var deleted: Boolean
  var categories: Array<Category>
}
