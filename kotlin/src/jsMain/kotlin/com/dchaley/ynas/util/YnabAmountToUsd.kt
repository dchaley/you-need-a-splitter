package com.dchaley.ynas.util

import kotlin.math.absoluteValue

// Extend the number class with a toUsd which converts the number from millicents to dollars.
fun Number.toUsd(): String {
  // This goes through JS's toFixed method.
  // It seems that String.format is not available in Kotlin/JS
  val dollarStr = (this.toDouble().absoluteValue / 1000.0).asDynamic().toFixed(2)

  return if (this.toInt() >= 0) {
    "\$$dollarStr"
  }
  else {
    "-\$$dollarStr"
  }
}
