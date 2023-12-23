@file:Suppress("INTERFACE_WITH_SUPERCLASS", "OVERRIDING_FINAL_MEMBER", "RETURN_TYPE_MISMATCH_ON_OVERRIDE", "CONFLICTING_OVERLOADS")
@file:JsModule("ynab")
@file:JsNonModule

package ynab

import kotlin.js.*

external interface SaveTransaction {
    var account_id: String?
    var date: String?
    var amount: Number?
    var payee_id: String?
    var payee_name: String?
    var category_id: String?
    var memo: String?
    var cleared: Any?
    var approved: Boolean?
    var flag_color: Any?
    var import_id: String?
    var subtransactions: Array<SaveSubTransaction>?
}
