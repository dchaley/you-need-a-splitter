@file:Suppress("INTERFACE_WITH_SUPERCLASS", "OVERRIDING_FINAL_MEMBER", "RETURN_TYPE_MISMATCH_ON_OVERRIDE", "CONFLICTING_OVERLOADS")
@file:JsModule("ynab")
@file:JsNonModule

package ynab

import kotlin.js.*
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

external interface CreateTransactionRequest {
    var budgetId: String
    var data: PostTransactionsWrapper
}

external interface DeleteTransactionRequest {
    var budgetId: String
    var transactionId: String
}

external interface GetTransactionByIdRequest {
    var budgetId: String
    var transactionId: String
}

external interface GetTransactionsRequest {
    var budgetId: String
    var sinceDate: String?
    var type: Any
    var lastKnowledgeOfServer: Number?
}

external interface GetTransactionsByAccountRequest {
    var budgetId: String
    var accountId: String
    var sinceDate: String?
    var type: Any
    var lastKnowledgeOfServer: Number?
}

external interface GetTransactionsByCategoryRequest {
    var budgetId: String
    var categoryId: String
    var sinceDate: String?
    var type: Any
    var lastKnowledgeOfServer: Number?
}

external interface GetTransactionsByPayeeRequest {
    var budgetId: String
    var payeeId: String
    var sinceDate: String?
    var type: Any
    var lastKnowledgeOfServer: Number?
}

external interface ImportTransactionsRequest {
    var budgetId: String
}

external interface UpdateTransactionRequest {
    var budgetId: String
    var transactionId: String
    var data: PutTransactionWrapper
}

external interface UpdateTransactionsRequest {
    var budgetId: String
    var data: PatchTransactionsWrapper
}

external class TransactionsApi(configuration: Any = definedExternally) {
    // fun createTransaction(budgetId: String, data: PostTransactionsWrapper, initOverrides: RequestInit = definedExternally): Promise<SaveTransactionsResponse>
    fun createTransaction(budgetId: String, data: PostTransactionsWrapper): Promise<SaveTransactionsResponse>
    // fun createTransaction(budgetId: String, data: PostTransactionsWrapper, initOverrides: InitOverrideFunction = definedExternally): Promise<SaveTransactionsResponse>
    // fun deleteTransaction(budgetId: String, transactionId: String, initOverrides: RequestInit = definedExternally): Promise<TransactionResponse>
    fun deleteTransaction(budgetId: String, transactionId: String): Promise<TransactionResponse>
    // fun deleteTransaction(budgetId: String, transactionId: String, initOverrides: InitOverrideFunction = definedExternally): Promise<TransactionResponse>
    // fun getTransactionById(budgetId: String, transactionId: String, initOverrides: RequestInit = definedExternally): Promise<TransactionResponse>
    fun getTransactionById(budgetId: String, transactionId: String): Promise<TransactionResponse>
    // fun getTransactionById(budgetId: String, transactionId: String, initOverrides: InitOverrideFunction = definedExternally): Promise<TransactionResponse>
    // fun getTransactions(budgetId: String, sinceDate: String = definedExternally, type: Any = definedExternally, lastKnowledgeOfServer: Number = definedExternally, initOverrides: RequestInit = definedExternally): Promise<TransactionsResponse>
    fun getTransactions(budgetId: String): Promise<TransactionsResponse>
    fun getTransactions(budgetId: String, sinceDate: String = definedExternally): Promise<TransactionsResponse>
    fun getTransactions(budgetId: String, sinceDate: String = definedExternally, type: Any = definedExternally): Promise<TransactionsResponse>
    fun getTransactions(budgetId: String, sinceDate: String = definedExternally, type: Any = definedExternally, lastKnowledgeOfServer: Number = definedExternally): Promise<TransactionsResponse>
    // fun getTransactions(budgetId: String, sinceDate: String = definedExternally, type: Any = definedExternally, lastKnowledgeOfServer: Number = definedExternally, initOverrides: InitOverrideFunction = definedExternally): Promise<TransactionsResponse>
    // fun getTransactionsByAccount(budgetId: String, accountId: String, sinceDate: String = definedExternally, type: Any = definedExternally, lastKnowledgeOfServer: Number = definedExternally, initOverrides: RequestInit = definedExternally): Promise<TransactionsResponse>
    fun getTransactionsByAccount(budgetId: String, accountId: String): Promise<TransactionsResponse>
    fun getTransactionsByAccount(budgetId: String, accountId: String, sinceDate: String = definedExternally): Promise<TransactionsResponse>
    fun getTransactionsByAccount(budgetId: String, accountId: String, sinceDate: String = definedExternally, type: Any = definedExternally): Promise<TransactionsResponse>
    fun getTransactionsByAccount(budgetId: String, accountId: String, sinceDate: String = definedExternally, type: Any = definedExternally, lastKnowledgeOfServer: Number = definedExternally): Promise<TransactionsResponse>
    // fun getTransactionsByAccount(budgetId: String, accountId: String, sinceDate: String = definedExternally, type: Any = definedExternally, lastKnowledgeOfServer: Number = definedExternally, initOverrides: InitOverrideFunction = definedExternally): Promise<TransactionsResponse>
    // fun getTransactionsByCategory(budgetId: String, categoryId: String, sinceDate: String = definedExternally, type: Any = definedExternally, lastKnowledgeOfServer: Number = definedExternally, initOverrides: RequestInit = definedExternally): Promise<HybridTransactionsResponse>
    fun getTransactionsByCategory(budgetId: String, categoryId: String): Promise<HybridTransactionsResponse>
    fun getTransactionsByCategory(budgetId: String, categoryId: String, sinceDate: String = definedExternally): Promise<HybridTransactionsResponse>
    fun getTransactionsByCategory(budgetId: String, categoryId: String, sinceDate: String = definedExternally, type: Any = definedExternally): Promise<HybridTransactionsResponse>
    fun getTransactionsByCategory(budgetId: String, categoryId: String, sinceDate: String = definedExternally, type: Any = definedExternally, lastKnowledgeOfServer: Number = definedExternally): Promise<HybridTransactionsResponse>
    // fun getTransactionsByCategory(budgetId: String, categoryId: String, sinceDate: String = definedExternally, type: Any = definedExternally, lastKnowledgeOfServer: Number = definedExternally, initOverrides: InitOverrideFunction = definedExternally): Promise<HybridTransactionsResponse>
    // fun getTransactionsByPayee(budgetId: String, payeeId: String, sinceDate: String = definedExternally, type: Any = definedExternally, lastKnowledgeOfServer: Number = definedExternally, initOverrides: RequestInit = definedExternally): Promise<HybridTransactionsResponse>
    fun getTransactionsByPayee(budgetId: String, payeeId: String): Promise<HybridTransactionsResponse>
    fun getTransactionsByPayee(budgetId: String, payeeId: String, sinceDate: String = definedExternally): Promise<HybridTransactionsResponse>
    fun getTransactionsByPayee(budgetId: String, payeeId: String, sinceDate: String = definedExternally, type: Any = definedExternally): Promise<HybridTransactionsResponse>
    fun getTransactionsByPayee(budgetId: String, payeeId: String, sinceDate: String = definedExternally, type: Any = definedExternally, lastKnowledgeOfServer: Number = definedExternally): Promise<HybridTransactionsResponse>
    // fun getTransactionsByPayee(budgetId: String, payeeId: String, sinceDate: String = definedExternally, type: Any = definedExternally, lastKnowledgeOfServer: Number = definedExternally, initOverrides: InitOverrideFunction = definedExternally): Promise<HybridTransactionsResponse>
    // fun importTransactions(budgetId: String, initOverrides: RequestInit = definedExternally): Promise<TransactionsImportResponse>
    fun importTransactions(budgetId: String): Promise<TransactionsImportResponse>
    // fun importTransactions(budgetId: String, initOverrides: InitOverrideFunction = definedExternally): Promise<TransactionsImportResponse>
    // fun updateTransaction(budgetId: String, transactionId: String, data: PutTransactionWrapper, initOverrides: RequestInit = definedExternally): Promise<TransactionResponse>
    fun updateTransaction(budgetId: String, transactionId: String, data: PutTransactionWrapper): Promise<TransactionResponse>
    // fun updateTransaction(budgetId: String, transactionId: String, data: PutTransactionWrapper, initOverrides: InitOverrideFunction = definedExternally): Promise<TransactionResponse>
    // fun updateTransactions(budgetId: String, data: PatchTransactionsWrapper, initOverrides: RequestInit = definedExternally): Promise<SaveTransactionsResponse>
    fun updateTransactions(budgetId: String, data: PatchTransactionsWrapper): Promise<SaveTransactionsResponse>
    // fun updateTransactions(budgetId: String, data: PatchTransactionsWrapper, initOverrides: InitOverrideFunction = definedExternally): Promise<SaveTransactionsResponse>
}

external object GetTransactionsTypeEnum {
    var Uncategorized: String /* "uncategorized" */
    var Unapproved: String /* "unapproved" */
}

external object GetTransactionsByAccountTypeEnum {
    var Uncategorized: String /* "uncategorized" */
    var Unapproved: String /* "unapproved" */
}

external object GetTransactionsByCategoryTypeEnum {
    var Uncategorized: String /* "uncategorized" */
    var Unapproved: String /* "unapproved" */
}

external object GetTransactionsByPayeeTypeEnum {
    var Uncategorized: String /* "uncategorized" */
    var Unapproved: String /* "unapproved" */
}
