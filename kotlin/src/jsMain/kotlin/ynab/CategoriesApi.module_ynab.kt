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

external interface GetCategoriesRequest {
  var budgetId: String
  var lastKnowledgeOfServer: Number?
    get() = definedExternally
    set(value) = definedExternally
}

external interface GetCategoryByIdRequest {
  var budgetId: String
  var categoryId: String
}

external interface GetMonthCategoryByIdRequest {
  var budgetId: String
  var month: String
  var categoryId: String
}

external interface UpdateCategoryRequest {
  var budgetId: String
  var categoryId: String
  var data: PatchCategoryWrapper
}

external interface UpdateMonthCategoryRequest {
  var budgetId: String
  var month: String
  var categoryId: String
  var data: PatchMonthCategoryWrapper
}

external open class CategoriesApi(configuration: Configuration = definedExternally) : BaseAPI {
  open fun getCategoriesRaw(
    requestParameters: GetCategoriesRequest,
    initOverrides: RequestInit = definedExternally,
  ): Promise<ApiResponse<CategoriesResponse>>

  open fun getCategoriesRaw(requestParameters: GetCategoriesRequest): Promise<ApiResponse<CategoriesResponse>>
  open fun getCategoriesRaw(
    requestParameters: GetCategoriesRequest,
    initOverrides: InitOverrideFunction = definedExternally,
  ): Promise<ApiResponse<CategoriesResponse>>

  open fun getCategories(
    budgetId: String,
    lastKnowledgeOfServer: Number = definedExternally,
    initOverrides: RequestInit = definedExternally,
  ): Promise<CategoriesResponse>

  open fun getCategories(budgetId: String): Promise<CategoriesResponse>
  open fun getCategories(
    budgetId: String,
    lastKnowledgeOfServer: Number = definedExternally,
  ): Promise<CategoriesResponse>

  open fun getCategories(
    budgetId: String,
    lastKnowledgeOfServer: Number = definedExternally,
    initOverrides: InitOverrideFunction = definedExternally,
  ): Promise<CategoriesResponse>

  open fun getCategoryByIdRaw(
    requestParameters: GetCategoryByIdRequest,
    initOverrides: RequestInit = definedExternally,
  ): Promise<ApiResponse<CategoryResponse>>

  open fun getCategoryByIdRaw(requestParameters: GetCategoryByIdRequest): Promise<ApiResponse<CategoryResponse>>
  open fun getCategoryByIdRaw(
    requestParameters: GetCategoryByIdRequest,
    initOverrides: InitOverrideFunction = definedExternally,
  ): Promise<ApiResponse<CategoryResponse>>

  open fun getCategoryById(
    budgetId: String,
    categoryId: String,
    initOverrides: RequestInit = definedExternally,
  ): Promise<CategoryResponse>

  open fun getCategoryById(budgetId: String, categoryId: String): Promise<CategoryResponse>
  open fun getCategoryById(
    budgetId: String,
    categoryId: String,
    initOverrides: InitOverrideFunction = definedExternally,
  ): Promise<CategoryResponse>

  open fun getMonthCategoryByIdRaw(
    requestParameters: GetMonthCategoryByIdRequest,
    initOverrides: RequestInit = definedExternally,
  ): Promise<ApiResponse<CategoryResponse>>

  open fun getMonthCategoryByIdRaw(requestParameters: GetMonthCategoryByIdRequest): Promise<ApiResponse<CategoryResponse>>
  open fun getMonthCategoryByIdRaw(
    requestParameters: GetMonthCategoryByIdRequest,
    initOverrides: InitOverrideFunction = definedExternally,
  ): Promise<ApiResponse<CategoryResponse>>

  open fun getMonthCategoryById(
    budgetId: String,
    month: String,
    categoryId: String,
    initOverrides: RequestInit = definedExternally,
  ): Promise<CategoryResponse>

  open fun getMonthCategoryById(budgetId: String, month: String, categoryId: String): Promise<CategoryResponse>
  open fun getMonthCategoryById(
    budgetId: String,
    month: String,
    categoryId: String,
    initOverrides: InitOverrideFunction = definedExternally,
  ): Promise<CategoryResponse>

  open fun updateCategoryRaw(
    requestParameters: UpdateCategoryRequest,
    initOverrides: RequestInit = definedExternally,
  ): Promise<ApiResponse<SaveCategoryResponse>>

  open fun updateCategoryRaw(requestParameters: UpdateCategoryRequest): Promise<ApiResponse<SaveCategoryResponse>>
  open fun updateCategoryRaw(
    requestParameters: UpdateCategoryRequest,
    initOverrides: InitOverrideFunction = definedExternally,
  ): Promise<ApiResponse<SaveCategoryResponse>>

  open fun updateCategory(
    budgetId: String,
    categoryId: String,
    data: PatchCategoryWrapper,
    initOverrides: RequestInit = definedExternally,
  ): Promise<SaveCategoryResponse>

  open fun updateCategory(
    budgetId: String,
    categoryId: String,
    data: PatchCategoryWrapper,
  ): Promise<SaveCategoryResponse>

  open fun updateCategory(
    budgetId: String,
    categoryId: String,
    data: PatchCategoryWrapper,
    initOverrides: InitOverrideFunction = definedExternally,
  ): Promise<SaveCategoryResponse>

  open fun updateMonthCategoryRaw(
    requestParameters: UpdateMonthCategoryRequest,
    initOverrides: RequestInit = definedExternally,
  ): Promise<ApiResponse<SaveCategoryResponse>>

  open fun updateMonthCategoryRaw(requestParameters: UpdateMonthCategoryRequest): Promise<ApiResponse<SaveCategoryResponse>>
  open fun updateMonthCategoryRaw(
    requestParameters: UpdateMonthCategoryRequest,
    initOverrides: InitOverrideFunction = definedExternally,
  ): Promise<ApiResponse<SaveCategoryResponse>>

  open fun updateMonthCategory(
    budgetId: String,
    month: String,
    categoryId: String,
    data: PatchMonthCategoryWrapper,
    initOverrides: RequestInit = definedExternally,
  ): Promise<SaveCategoryResponse>

  open fun updateMonthCategory(
    budgetId: String,
    month: String,
    categoryId: String,
    data: PatchMonthCategoryWrapper,
  ): Promise<SaveCategoryResponse>

  open fun updateMonthCategory(
    budgetId: String,
    month: String,
    categoryId: String,
    data: PatchMonthCategoryWrapper,
    initOverrides: InitOverrideFunction = definedExternally,
  ): Promise<SaveCategoryResponse>
}
