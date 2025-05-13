package com.dchaley.ynas.util

import kotlinx.serialization.json.Json
import kotlinx.serialization.encodeToString
import kotlinx.serialization.decodeFromString

/**
 * Utility functions for managing cookies using the document.cookie API.
 * @see https://developer.mozilla.org/en-US/docs/Web/API/Document/cookie
 */
object CookieUtil {
  private const val DEFAULT_CATEGORY_COOKIE_NAME = "defaultCategoryId"
  private const val DEFAULT_PATH = "/"

  /**
   * Encodes a string to Base64
   * @param input The string to encode
   * @return The Base64 encoded string
   */
  private fun encodeToBase64(input: String): String {
    return js("btoa(input)").toString()
  }

  /**
   * Decodes a Base64 string
   * @param input The Base64 encoded string
   * @return The decoded string
   */
  private fun decodeFromBase64(input: String): String {
    return js("atob(input)").toString()
  }

  /**
   * Sets the default category ID map in a cookie with a 1-year expiration date.
   * @param defaultCategoryMap The map of budget ID to default category ID
   */
  fun setDefaultCategoryId(defaultCategoryMap: Map<String, String>) {
    // Calculate expiration date (1 year from now)
    val expirationDate = js("new Date()")
    expirationDate.setFullYear(expirationDate.getFullYear() + 1)

    // Convert map to JSON and encode to Base64
    val jsonString = Json.encodeToString(defaultCategoryMap)
    val encodedValue = encodeToBase64(jsonString)

    // Set the cookie with document.cookie
    val cookieString =
      "$DEFAULT_CATEGORY_COOKIE_NAME=$encodedValue; path=$DEFAULT_PATH; expires=${expirationDate.toUTCString()}"
    js("document.cookie = cookieString")
  }

  /**
   * Gets the default category ID map from the cookie.
   * @return The map of budget ID to default category ID, or empty map if not found
   */
  fun getDefaultCategoryId(): Map<String, String> {
    // Get all cookies
    val cookies = js("document.cookie").toString().split("; ")

    // Find our cookie
    val cookie = cookies.find { it.startsWith("$DEFAULT_CATEGORY_COOKIE_NAME=") }

    // Extract the value
    val encodedValue = cookie?.substringAfter("=")

    // If no cookie found, return empty map
    if (encodedValue == null) {
      return emptyMap()
    }

    return try {
      // Decode from Base64 and parse JSON
      val jsonString = decodeFromBase64(encodedValue)
      Json.decodeFromString<Map<String, String>>(jsonString)
    } catch (e: Exception) {
      console.error("Error decoding default category map: $e")
      emptyMap()
    }
  }

  /**
   * For backward compatibility - gets the default category ID for a specific budget
   * @param budgetId The budget ID
   * @return The default category ID for the budget, or null if not found
   */
  fun getDefaultCategoryIdForBudget(budgetId: String): String? {
    return getDefaultCategoryId()[budgetId]
  }
}
