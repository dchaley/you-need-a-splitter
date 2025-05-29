package com.dchaley.ynas.util

import kotlin.math.roundToInt

fun Number.percentOf(another: Number): String {
  val percent = (this.toDouble() / another.toDouble()) * 100.0
  return "${percent.roundToInt()}%"
}
