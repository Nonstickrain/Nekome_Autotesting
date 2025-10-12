package com.chesire.nekome.automation.tests.ui.search

import androidx.compose.ui.test.onRoot
import androidx.compose.ui.test.printToLog
import com.chesire.nekome.UITest
import com.chesire.nekome.automation.scenarios.SearchScenario
import com.chesire.nekome.automation.screens.search.SearchScreen
import com.chesire.nekome.core.flags.SeriesType
import com.chesire.nekome.injection.MockSearchModule
import dagger.hilt.android.testing.HiltAndroidTest
import io.github.kakaocup.compose.node.element.ComposeScreen.Companion.onComposeScreen
import io.mockk.clearAllMocks
import io.qameta.allure.kotlin.junit4.DisplayName
import org.junit.Test

@HiltAndroidTest
class SearchScreenTest : UITest() {

    private val animeTitle1 = "Valve"
    private val seriesId = 1

    @Test
    @DisplayName("Проверка работы поискового тега по манге")
    fun mangaTagSearchTest() = before {
        clearAllMocks()
        MockSearchModule.oneItemSearchMock(seriesId, animeTitle1, SeriesType.Manga )
        launchActivity()
    }.after {}.run {
        step("Поиск манги") {
            scenario(SearchScenario(composeTestRule, animeTitle1))
        }
        step("Проверка результата поиска. Отображается только манга") {
            onComposeScreen<SearchScreen>(composeTestRule) {
                flakySafely {
                    getSearchItemTitle(0, composeTestRule) {
                        assertTextContains(animeTitle1)
                    }
                }
            }
        }
    }
}


