@file:Suppress(
  "INTERFACE_WITH_SUPERCLASS",
  "OVERRIDING_FINAL_MEMBER",
  "RETURN_TYPE_MISMATCH_ON_OVERRIDE",
  "CONFLICTING_OVERLOADS"
)
@file:JsModule("ynab")

package ynab

import org.w3c.fetch.RequestInit
import kotlin.js.Promise

external interface GetBudgetByIdRequest {
  var budgetId: String
  var lastKnowledgeOfServer: Number?
    get() = definedExternally
    set(value) = definedExternally
}

external interface GetBudgetSettingsByIdRequest {
  var budgetId: String
}

external interface GetBudgetsRequest {
  var includeAccounts: Boolean?
    get() = definedExternally
    set(value) = definedExternally
}

external open class BudgetsApi(configuration: Configuration = definedExternally) : BaseAPI {
  open fun getBudgetByIdRaw(
    requestParameters: GetBudgetByIdRequest,
    initOverrides: RequestInit = definedExternally,
  ): Promise<ApiResponse<BudgetDetailResponse>>

  open fun getBudgetByIdRaw(requestParameters: GetBudgetByIdRequest): Promise<ApiResponse<BudgetDetailResponse>>
  open fun getBudgetByIdRaw(
    requestParameters: GetBudgetByIdRequest,
    initOverrides: InitOverrideFunction = definedExternally,
  ): Promise<ApiResponse<BudgetDetailResponse>>

  open fun getBudgetById(
    budgetId: String,
    lastKnowledgeOfServer: Number = definedExternally,
    initOverrides: RequestInit = definedExternally,
  ): Promise<BudgetDetailResponse>

  open fun getBudgetById(budgetId: String): Promise<BudgetDetailResponse>
  open fun getBudgetById(
    budgetId: String,
    lastKnowledgeOfServer: Number = definedExternally,
  ): Promise<BudgetDetailResponse>

  open fun getBudgetById(
    budgetId: String,
    lastKnowledgeOfServer: Number = definedExternally,
    initOverrides: InitOverrideFunction = definedExternally,
  ): Promise<BudgetDetailResponse>

  open fun getBudgetSettingsByIdRaw(
    requestParameters: GetBudgetSettingsByIdRequest,
    initOverrides: RequestInit = definedExternally,
  ): Promise<ApiResponse<BudgetSettingsResponse>>

  open fun getBudgetSettingsByIdRaw(requestParameters: GetBudgetSettingsByIdRequest): Promise<ApiResponse<BudgetSettingsResponse>>
  open fun getBudgetSettingsByIdRaw(
    requestParameters: GetBudgetSettingsByIdRequest,
    initOverrides: InitOverrideFunction = definedExternally,
  ): Promise<ApiResponse<BudgetSettingsResponse>>

  open fun getBudgetSettingsById(
    budgetId: String,
    initOverrides: RequestInit = definedExternally,
  ): Promise<BudgetSettingsResponse>

  open fun getBudgetSettingsById(budgetId: String): Promise<BudgetSettingsResponse>
  open fun getBudgetSettingsById(
    budgetId: String,
    initOverrides: InitOverrideFunction = definedExternally,
  ): Promise<BudgetSettingsResponse>

  open fun getBudgetsRaw(
    requestParameters: GetBudgetsRequest,
    initOverrides: RequestInit = definedExternally,
  ): Promise<ApiResponse<BudgetSummaryResponse>>

  open fun getBudgetsRaw(requestParameters: GetBudgetsRequest): Promise<ApiResponse<BudgetSummaryResponse>>
  open fun getBudgetsRaw(
    requestParameters: GetBudgetsRequest,
    initOverrides: InitOverrideFunction = definedExternally,
  ): Promise<ApiResponse<BudgetSummaryResponse>>

  open fun getBudgets(
    includeAccounts: Boolean = definedExternally,
    initOverrides: RequestInit = definedExternally,
  ): Promise<BudgetSummaryResponse>

  open fun getBudgets(): Promise<BudgetSummaryResponse>
  open fun getBudgets(includeAccounts: Boolean = definedExternally): Promise<BudgetSummaryResponse>
  open fun getBudgets(
    includeAccounts: Boolean = definedExternally,
    initOverrides: InitOverrideFunction = definedExternally,
  ): Promise<BudgetSummaryResponse>
}
