package com.chesire.nekome.automation.scenarios

import androidx.compose.ui.test.junit4.ComposeTestRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performTextInput
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.uiautomator.UiDevice
import com.chesire.nekome.automation.screens.search.SearchScreen
import com.chesire.nekome.automation.screens.seriesCollection.SeriesCollectionScreen
import com.kaspersky.kaspresso.testcases.api.scenario.Scenario
import com.kaspersky.kaspresso.testcases.core.testcontext.TestContext
import io.github.kakaocup.compose.node.element.ComposeScreen.Companion.onComposeScreen

class SearchScenario(
    composeTestRule: ComposeTestRule,
    searchText: String,
    switchToMagaTag: Boolean = true
) : Scenario() {
    private val uiDevice: UiDevice =
        UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())
    override val steps: TestContext<Unit>.() -> Unit = {
        step("Тап по кнопке добавления в коллекцию")
        {
            onComposeScreen<SeriesCollectionScreen>(composeTestRule) {
                flakySafely {
                    addButton.performClick()
                }
            }
        }
        onComposeScreen<SearchScreen>(composeTestRule) {
            step("Ввод названия аниме в поле поиска")
            {
                flakySafely {
                    composeTestRule.onNodeWithText("Enter series title")
                        .performTextInput(searchText)
                    uiDevice.pressBack()
                }
            }
            if (switchToMagaTag) {
                step("Выбор манга тэга") {
                    flakySafely {
                        mangaCheckBox.performClick()
                    }
                }
            }
            step("Инциализация поиска") {
                flakySafely {
                    searchButton.performClick()
                }
            }
        }

    }
}