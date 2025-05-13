package com.dchaley.ynas.util

/**
 * Utility functions for managing cookies using the document.cookie API.
 * @see https://developer.mozilla.org/en-US/docs/Web/API/Document/cookie
 */
object CookieUtil {
  private const val DEFAULT_CATEGORY_COOKIE_NAME = "defaultCategoryId"
  private const val DEFAULT_PATH = "/"

  /**
   * Sets the default category ID in a cookie with a 1-year expiration date.
   * @param categoryId The category ID to store
   */
  fun setDefaultCategoryId(categoryId: String) {
    // Calculate expiration date (1 year from now)
    val expirationDate = js("new Date()")
    expirationDate.setFullYear(expirationDate.getFullYear() + 1)

    // Set the cookie with document.cookie
    val cookieString =
      "$DEFAULT_CATEGORY_COOKIE_NAME=$categoryId; path=$DEFAULT_PATH; expires=${expirationDate.toUTCString()}"
    js("document.cookie = cookieString")
  }

  /**
   * Gets the default category ID from the cookie.
   * @return The cookie value, or null if not found
   */
  fun getDefaultCategoryId(): String? {
    // Get all cookies
    val cookies = js("document.cookie").toString().split("; ")

    // Find our cookie
    val cookie = cookies.find { it.startsWith("$DEFAULT_CATEGORY_COOKIE_NAME=") }

    // Extract and return the value
    return cookie?.substringAfter("=")
  }
}
