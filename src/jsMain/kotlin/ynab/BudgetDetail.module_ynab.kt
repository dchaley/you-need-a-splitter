@file:Suppress(
  "INTERFACE_WITH_SUPERCLASS",
  "OVERRIDING_FINAL_MEMBER",
  "RETURN_TYPE_MISMATCH_ON_OVERRIDE",
  "CONFLICTING_OVERLOADS"
)
@file:JsModule("ynab")

package ynab

import org.khronos.webgl.*
import org.w3c.dom.*
import org.w3c.dom.events.*
import org.w3c.dom.parsing.*
import org.w3c.dom.svg.*
import org.w3c.dom.url.*
import org.w3c.fetch.*
import org.w3c.files.*
import org.w3c.notifications.*
import org.w3c.performance.*
import org.w3c.workers.*
import org.w3c.xhr.*
import kotlin.js.*

external interface BudgetDetail {
  var id: String
  var name: String
  var last_modified_on: String?
    get() = definedExternally
    set(value) = definedExternally
  var first_month: String?
    get() = definedExternally
    set(value) = definedExternally
  var last_month: String?
    get() = definedExternally
    set(value) = definedExternally
  var date_format: DateFormat?
    get() = definedExternally
    set(value) = definedExternally
  var currency_format: CurrencyFormat?
    get() = definedExternally
    set(value) = definedExternally
  var accounts: Array<Account>?
    get() = definedExternally
    set(value) = definedExternally
  var payees: Array<Payee>?
    get() = definedExternally
    set(value) = definedExternally
  var payee_locations: Array<PayeeLocation>?
    get() = definedExternally
    set(value) = definedExternally
  var category_groups: Array<CategoryGroup>?
    get() = definedExternally
    set(value) = definedExternally
  var categories: Array<Category>?
    get() = definedExternally
    set(value) = definedExternally
  var months: Array<MonthDetail>?
    get() = definedExternally
    set(value) = definedExternally
  var transactions: Array<TransactionSummary>?
    get() = definedExternally
    set(value) = definedExternally
  var subtransactions: Array<SubTransaction>?
    get() = definedExternally
    set(value) = definedExternally
  var scheduled_transactions: Array<ScheduledTransactionSummary>?
    get() = definedExternally
    set(value) = definedExternally
  var scheduled_subtransactions: Array<ScheduledSubTransaction>?
    get() = definedExternally
    set(value) = definedExternally
}
