package com.dchaley.ynas.util

fun Number.percentOf(another: Number): String {
  val percent = (this.toDouble() / another.toDouble()) * 100.0
  return "${percent.toInt()}%"
}
