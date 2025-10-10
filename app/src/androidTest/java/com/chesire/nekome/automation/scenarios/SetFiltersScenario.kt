package com.chesire.nekome.automation.scenarios

import androidx.compose.ui.test.junit4.ComposeTestRule
import com.chesire.nekome.automation.screens.seriesCollection.SeriesCollectionScreen
import com.chesire.nekome.automation.screens.seriesCollection.filter.FilterScreen
import com.kaspersky.kaspresso.testcases.api.scenario.Scenario
import com.kaspersky.kaspresso.testcases.core.testcontext.TestContext
import io.github.kakaocup.compose.node.element.ComposeScreen.Companion.onComposeScreen

class SetFiltersScenario(composeTestRule: ComposeTestRule) : Scenario() {
    override val steps: TestContext<Unit>.() -> Unit = {
        step("Тап по кнопке фильтров")
        {
            onComposeScreen<SeriesCollectionScreen>(composeTestRule) {
                flakySafely {
                    filterButton.performClick()
                }
            }
        }
        onComposeScreen<FilterScreen>(composeTestRule) {
            step("Выставить фильтры Current и On Hold") {
                flakySafely(5000) {
                    checkBox(2) { performClick() }
                    checkBox(0) { performClick() }
                }
            }
            step("Принять натсройки фильтра") {
                flakySafely {
                    okButton.performClick()
                }
            }
        }
    }
}