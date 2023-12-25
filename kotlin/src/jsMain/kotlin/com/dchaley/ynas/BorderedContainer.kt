package com.dchaley.ynas

import io.kvision.core.Background
import io.kvision.core.Color
import io.kvision.core.Container
import io.kvision.html.div
import io.kvision.utils.px

fun Container.borderedContainer(content: Container.() -> Unit) {
  div {
    borderRadius = 6.px
    background = Background(Color("#000000"))
    padding = 3.px
    div {
      borderRadius = 6.px
      background = Background(Color("#ffffff"))

      content()
    }
  }
}

