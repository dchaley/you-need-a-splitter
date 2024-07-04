@file:Suppress(
  "INTERFACE_WITH_SUPERCLASS",
  "OVERRIDING_FINAL_MEMBER",
  "RETURN_TYPE_MISMATCH_ON_OVERRIDE",
  "CONFLICTING_OVERLOADS"
)
@file:JsModule("ynab")

package ynab

external interface DebtData {
  @nativeGetter
  operator fun get(key: String): Number?

  @nativeSetter
  operator fun set(key: String, value: Number)
}

external interface Account {
  var id: String
  var name: String
  var type: Any
  var on_budget: Boolean
  var closed: Boolean
  var note: String?
    get() = definedExternally
    set(value) = definedExternally
  var balance: Number
  var cleared_balance: Number
  var uncleared_balance: Number
  var transfer_payee_id: String?
  var direct_import_linked: Boolean?
    get() = definedExternally
    set(value) = definedExternally
  var direct_import_in_error: Boolean?
    get() = definedExternally
    set(value) = definedExternally
  var last_reconciled_at: String?
    get() = definedExternally
    set(value) = definedExternally
  var debt_original_balance: Number?
    get() = definedExternally
    set(value) = definedExternally
  var debt_interest_rates: DebtData?
    get() = definedExternally
    set(value) = definedExternally
  var debt_minimum_payments: DebtData?
    get() = definedExternally
    set(value) = definedExternally
  var debt_escrow_amounts: DebtData?
    get() = definedExternally
    set(value) = definedExternally
  var deleted: Boolean
}
