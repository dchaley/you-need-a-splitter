@file:Suppress(
  "INTERFACE_WITH_SUPERCLASS",
  "OVERRIDING_FINAL_MEMBER",
  "RETURN_TYPE_MISMATCH_ON_OVERRIDE",
  "CONFLICTING_OVERLOADS"
)
@file:JsModule("ynab")

package ynab

external interface TransactionSummary {
  var id: String
  var date: String
  var amount: Number
  var memo: String?
    get() = definedExternally
    set(value) = definedExternally
  var cleared: Any
  var approved: Boolean
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
  var transfer_transaction_id: String?
    get() = definedExternally
    set(value) = definedExternally
  var matched_transaction_id: String?
    get() = definedExternally
    set(value) = definedExternally
  var import_id: String?
    get() = definedExternally
    set(value) = definedExternally
  var import_payee_name: String?
    get() = definedExternally
    set(value) = definedExternally
  var import_payee_name_original: String?
    get() = definedExternally
    set(value) = definedExternally
  var debt_transaction_type: TransactionSummaryDebtTransactionTypeEnum?
    get() = definedExternally
    set(value) = definedExternally
  var deleted: Boolean
}

external object TransactionSummaryDebtTransactionTypeEnum {
  var Payment: String /* "payment" */
  var Refund: String /* "refund" */
  var Fee: String /* "fee" */
  var Interest: String /* "interest" */
  var Escrow: String /* "escrow" */
  var BalanceAdjustment: String /* "balanceAdjustment" */
  var Credit: String /* "credit" */
  var Charge: String /* "charge" */
}

external fun instanceOfTransactionSummary(value: Any?): Boolean

external fun TransactionSummaryFromJSON(json: Any): TransactionSummary

external fun TransactionSummaryFromJSONTyped(json: Any, ignoreDiscriminator: Boolean): TransactionSummary

external fun TransactionSummaryToJSON(value: TransactionSummary? = definedExternally): Any
