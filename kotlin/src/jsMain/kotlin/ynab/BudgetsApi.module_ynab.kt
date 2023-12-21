@file:Suppress("INTERFACE_WITH_SUPERCLASS", "OVERRIDING_FINAL_MEMBER", "RETURN_TYPE_MISMATCH_ON_OVERRIDE", "CONFLICTING_OVERLOADS")

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

external class BudgetsApi(configuration: Any = definedExternally) {
/*
    fun getBudgetByIdRaw(requestParameters: GetBudgetByIdRequest, initOverrides: RequestInit = definedExternally): Promise<ApiResponse<BudgetDetailResponse>>
    fun getBudgetByIdRaw(requestParameters: GetBudgetByIdRequest): Promise<ApiResponse<BudgetDetailResponse>>
    fun getBudgetByIdRaw(requestParameters: GetBudgetByIdRequest, initOverrides: InitOverrideFunction = definedExternally): Promise<ApiResponse<BudgetDetailResponse>>
    fun getBudgetById(budgetId: String, lastKnowledgeOfServer: Number = definedExternally, initOverrides: RequestInit = definedExternally): Promise<BudgetDetailResponse>
    fun getBudgetById(budgetId: String): Promise<BudgetDetailResponse>
    fun getBudgetById(budgetId: String, lastKnowledgeOfServer: Number = definedExternally): Promise<BudgetDetailResponse>
    fun getBudgetById(budgetId: String, lastKnowledgeOfServer: Number = definedExternally, initOverrides: InitOverrideFunction = definedExternally): Promise<BudgetDetailResponse>
    fun getBudgetSettingsByIdRaw(requestParameters: GetBudgetSettingsByIdRequest, initOverrides: RequestInit = definedExternally): Promise<ApiResponse<BudgetSettingsResponse>>
    fun getBudgetSettingsByIdRaw(requestParameters: GetBudgetSettingsByIdRequest): Promise<ApiResponse<BudgetSettingsResponse>>
    fun getBudgetSettingsByIdRaw(requestParameters: GetBudgetSettingsByIdRequest, initOverrides: InitOverrideFunction = definedExternally): Promise<ApiResponse<BudgetSettingsResponse>>
    fun getBudgetSettingsById(budgetId: String, initOverrides: RequestInit = definedExternally): Promise<BudgetSettingsResponse>
    fun getBudgetSettingsById(budgetId: String): Promise<BudgetSettingsResponse>
    fun getBudgetSettingsById(budgetId: String, initOverrides: InitOverrideFunction = definedExternally): Promise<BudgetSettingsResponse>
    fun getBudgets(includeAccounts: Boolean = definedExternally, initOverrides: RequestInit = definedExternally): Promise<BudgetSummaryResponse>
*/
    fun getBudgets(): Promise<BudgetSummaryResponse>
/*
    fun getBudgets(includeAccounts: Boolean = definedExternally): Promise<BudgetSummaryResponse>
    fun getBudgets(includeAccounts: Boolean = definedExternally, initOverrides: InitOverrideFunction = definedExternally): Promise<BudgetSummaryResponse>
*/
}
