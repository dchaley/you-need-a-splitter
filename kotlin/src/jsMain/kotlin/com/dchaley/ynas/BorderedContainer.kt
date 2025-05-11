package com.dchaley.ynas

import io.kvision.core.Background
import io.kvision.core.Color
import io.kvision.core.Container
import io.kvision.core.CssSize
import io.kvision.html.div
import io.kvision.utils.px

fun Container.borderedContainer(radius: CssSize = 6.px, size: CssSize = 3.px, content: Container.() -> Unit) {
  div {
    borderRadius = radius
    background = Background(Color("#000000"))
    padding = size
    div {
      borderRadius = radius
      background = Background(Color("#ffffff"))

      content()
    }
  }
}

