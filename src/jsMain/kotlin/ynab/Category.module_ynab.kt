@file:Suppress(
  "INTERFACE_WITH_SUPERCLASS",
  "OVERRIDING_FINAL_MEMBER",
  "RETURN_TYPE_MISMATCH_ON_OVERRIDE",
  "CONFLICTING_OVERLOADS"
)
@file:JsModule("ynab")

package ynab

external interface Category {
  var id: String
  var category_group_id: String
  var category_group_name: String?
    get() = definedExternally
    set(value) = definedExternally
  var name: String
  var hidden: Boolean
  var original_category_group_id: String?
    get() = definedExternally
    set(value) = definedExternally
  var note: String?
    get() = definedExternally
    set(value) = definedExternally
  var budgeted: Number
  var activity: Number
  var balance: Number
  var goal_type: CategoryGoalTypeEnum?
    get() = definedExternally
    set(value) = definedExternally
  var goal_needs_whole_amount: Boolean?
    get() = definedExternally
    set(value) = definedExternally
  var goal_day: Number?
    get() = definedExternally
    set(value) = definedExternally
  var goal_cadence: Number?
    get() = definedExternally
    set(value) = definedExternally
  var goal_cadence_frequency: Number?
    get() = definedExternally
    set(value) = definedExternally
  var goal_creation_month: String?
    get() = definedExternally
    set(value) = definedExternally
  var goal_target: Number?
    get() = definedExternally
    set(value) = definedExternally
  var goal_target_month: String?
    get() = definedExternally
    set(value) = definedExternally
  var goal_percentage_complete: Number?
    get() = definedExternally
    set(value) = definedExternally
  var goal_months_to_budget: Number?
    get() = definedExternally
    set(value) = definedExternally
  var goal_under_funded: Number?
    get() = definedExternally
    set(value) = definedExternally
  var goal_overall_funded: Number?
    get() = definedExternally
    set(value) = definedExternally
  var goal_overall_left: Number?
    get() = definedExternally
    set(value) = definedExternally
  var deleted: Boolean
}

external object CategoryGoalTypeEnum {
  var Tb: String /* "TB" */
  var Tbd: String /* "TBD" */
  var Mf: String /* "MF" */
  var Need: String /* "NEED" */
  var Debt: String /* "DEBT" */
}

external fun instanceOfCategory(value: Any?): Boolean

external fun CategoryFromJSON(json: Any): Category

external fun CategoryFromJSONTyped(json: Any, ignoreDiscriminator: Boolean): Category

external fun CategoryToJSON(value: Category? = definedExternally): Any
