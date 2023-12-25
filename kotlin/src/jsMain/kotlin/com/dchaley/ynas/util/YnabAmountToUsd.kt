package com.dchaley.ynas.util

import io.kvision.utils.Intl

// Extend the number class with a toUsd which converts the number from millicents to dollars.
fun Number.toUsd(): String {
  // This goes through JS's toFixed method.
  // It seems that String.format is not available in Kotlin/JS
  val dollars = this.toDouble() / 1000.0
  return Intl.NumberFormat("en-US", js("{style: 'currency', currency: 'USD'}")).format(dollars)
}
