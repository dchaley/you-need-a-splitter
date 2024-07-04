package com.dchaley.ynas

import io.kvision.core.Container
import io.kvision.core.VerticalAlign
import io.kvision.core.WhiteSpace
import io.kvision.state.ObservableList
import io.kvision.state.bindEach
import io.kvision.table.*
import ynab.Category

fun Container.categoriesTable(
  categories: ObservableList<Category>,
) {
  val columns = listOf("ID", "Name")
  val tableStyling = setOf(TableType.STRIPED, TableType.HOVER)

  table(columns, tableStyling, responsiveType = ResponsiveType.RESPONSIVE)
    .bindEach(categories) { category ->
      row {
        cell {
          verticalAlign = VerticalAlign.MIDDLE
          whiteSpace = WhiteSpace.NOWRAP
          content = category.id
        }
        cell {
          verticalAlign = VerticalAlign.MIDDLE
          whiteSpace = WhiteSpace.NOWRAP
          content = "${category.category_group_name} > ${category.name}"
        }
      }
    }
}
