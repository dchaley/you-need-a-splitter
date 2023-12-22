package com.dchaley.ynas

import io.kvision.core.Container
import io.kvision.html.customTag

fun Container.Spinner() {
  io.kvision.require("@material/web/progress/circular-progress.js")

  customTag("md-circular-progress") {
    setAttribute("indeterminate", "1")
  }
}
