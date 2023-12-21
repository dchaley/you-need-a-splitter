package com.dchaley.ynas

import io.kvision.*
import io.kvision.core.AlignItems
import io.kvision.form.text.text
import io.kvision.html.button
import io.kvision.html.div
import io.kvision.html.link
import io.kvision.panel.hPanel
import io.kvision.panel.root
import io.kvision.panel.vPanel
import io.kvision.state.bind
import io.kvision.state.bindEach
import io.kvision.state.bindTo
import io.kvision.state.observableState
import io.kvision.utils.auto
import io.kvision.utils.perc
import io.kvision.utils.px
import kotlinx.coroutines.flow.MutableStateFlow
import ynab.BudgetSummary
import ynab.api

class App : Application() {
    override fun start() {
        // Read the variable YNAB_ACCESS_TOKEN from the environment (see config.js in webpack.config.d)
        val env = js("PROCESS_ENV")
        val accessToken = env.YNAB_ACCESS_TOKEN

        val ynab = api(accessToken)

        val budgetSummaries = MutableStateFlow(listOf<BudgetSummary>())
        ynab.budgets.getBudgets().then { response ->
            budgetSummaries.value = response.data.budgets.toList()
        }
        val flow = MutableStateFlow("")
        budgetSummaries.observableState.subscribe  { budgets ->
            flow.value = budgets.joinToString("\n") { it.name }
        }

        root("kvapp") {
            vPanel(alignItems = AlignItems.STRETCH, useWrappers = true) {
                margin = 15.px
                maxWidth = 60.perc
                marginLeft = auto
                marginRight = auto
//                alignSelf = AlignItems.CENTER
                vPanel(alignItems = AlignItems.STRETCH, useWrappers = true) {
                    div("Hello You Need A Splitter!")

                    hPanel(spacing = 5) {
                    }.bindEach(budgetSummaries) { budget ->
                        button(text = budget.name) {
                            onClick {
                                console.log("Clicked on ${budget.name}")
                            }
                        }
                    }
                }
            }
        }
    }
}

fun main() {
    startApplication(
        ::App,
        module.hot,
        BootstrapModule,
        BootstrapCssModule,
        CoreModule
    )
}
