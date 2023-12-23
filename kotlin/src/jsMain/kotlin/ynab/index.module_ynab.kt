@file:Suppress("INTERFACE_WITH_SUPERCLASS", "OVERRIDING_FINAL_MEMBER", "RETURN_TYPE_MISMATCH_ON_OVERRIDE", "CONFLICTING_OVERLOADS")
@file:JsModule("ynab")
@file:JsNonModule

package ynab

import kotlin.js.*

external class api(accessToken: String, endpointUrl: String = definedExternally) {
    // var user: UserApi = definedExternally
    var budgets: BudgetsApi
    // var accounts: AccountsApi = definedExternally
    // var categories: CategoriesApi = definedExternally
    // var months: MonthsApi = definedExternally
    // var payees: PayeesApi = definedExternally
    // var payeeLocations: PayeeLocationsApi = definedExternally
    var transactions: TransactionsApi = definedExternally
    // var scheduledTransactions: ScheduledTransactionsApi = definedExternally
}
