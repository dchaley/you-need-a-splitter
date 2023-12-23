@file:Suppress("INTERFACE_WITH_SUPERCLASS", "OVERRIDING_FINAL_MEMBER", "RETURN_TYPE_MISMATCH_ON_OVERRIDE", "CONFLICTING_OVERLOADS")
@file:JsModule("ynab")
@file:JsNonModule

package ynab

import kotlin.js.*

external interface HybridTransactionsResponse {
    var data: HybridTransactionsResponseData
}
