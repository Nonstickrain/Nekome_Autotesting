package com.chesire.nekome.automation.tests.ui.seriesCollection

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextContains
import androidx.compose.ui.test.onAllNodesWithTag
import com.chesire.nekome.UITest
import com.chesire.nekome.app.series.collection.ui.SERIES_TITLE_TEST_TAG
import com.chesire.nekome.automation.scenarios.SearchScenario
import com.chesire.nekome.automation.scenarios.SetFiltersScenario
import com.chesire.nekome.automation.screens.search.SearchScreen
import com.chesire.nekome.automation.screens.seriesCollection.SeriesCollectionScreen
import com.chesire.nekome.automation.screens.seriesDetail.SeriesDetailScreen
import com.chesire.nekome.core.flags.SeriesType
import com.chesire.nekome.core.flags.UserSeriesStatus
import com.chesire.nekome.datasource.series.remote.SeriesApi
import com.chesire.nekome.helpers.SeriesMockData
import com.chesire.nekome.injection.MockLibraryModule
import com.chesire.nekome.injection.MockSearchModule
import dagger.hilt.android.testing.HiltAndroidTest
import io.qameta.allure.kotlin.junit4.DisplayName
import org.junit.Test
import io.github.kakaocup.compose.node.element.ComposeScreen.Companion.onComposeScreen
import io.mockk.clearAllMocks
import org.junit.Before
import javax.inject.Inject

@HiltAndroidTest
class SeriesCollectionScreenTest : UITest() {

    @Inject
    lateinit var seriesApi: SeriesApi

   @Before
    fun setup() {
        clearAllMocks()
    }

    private val userId = 0
    private val firstAnime = SeriesMockData(id = 1, title = "Naruto", userSeriesStatus = UserSeriesStatus.Current)
    private val secondAnime = SeriesMockData(id = 2, title = "Valve", userSeriesStatus = UserSeriesStatus.OnHold)

    @Test
    @DisplayName("Проверка добавления нового аниме в коллекцию")
    fun addNewSeriesToCollectionTest() = before {
        MockSearchModule.oneItemSearchMock(firstAnime.id, firstAnime.title, SeriesType.Anime)
        MockLibraryModule.addOneAnimeItemMock(userId, firstAnime.id, firstAnime.userSeriesStatus,firstAnime.title)
        launchActivity()
    }.after {}.run {
        step("Поиск аниме по названию") {
            scenario(SearchScenario(composeTestRule, firstAnime.title, false))
        }
        onComposeScreen<SearchScreen>(composeTestRule) {
            step("Добавление в коллекцию аниме из ответа поиска") {
                flakySafely {
                    getSearchItemAddButton(0, composeTestRule) {
                        performClick()
                    }
                }
            }
            step("Возврат на предыдущий экран") {
                flakySafely {
                    uiDevice.pressBack()
                }
            }
        }
        step("Проверка наличия добавленного аниме") {
            onComposeScreen<SeriesCollectionScreen>(composeTestRule) {
                flakySafely {
                    composeTestRule.onAllNodesWithTag(
                        SERIES_TITLE_TEST_TAG,
                        true
                    )[0].assertIsDisplayed()
                }
            }
        }
    }

    @Test
    @DisplayName("Проверка работа фильтра на экране коллекции")
    fun checkFilterOnSeriesCollectionScreenTest() = before {
        MockLibraryModule.retrieveTwoAnimeItemsMock(userId, firstAnime, secondAnime)
        launchActivity()
    }.after {}.run {
        step("Выставить фильтры") {
            flakySafely {
                scenario(SetFiltersScenario(composeTestRule))
            }
        }
        step("Обновить список")
        {
            onComposeScreen<SeriesCollectionScreen>(composeTestRule) {
                flakySafely {
                    refreshButton.performClick()
                }
            }
        }
        step("Проверить отображение экрана под 2 фильтрами") {
            onComposeScreen<SeriesCollectionScreen>(composeTestRule) {
                flakySafely {
                    composeTestRule.onAllNodesWithTag(
                        SERIES_TITLE_TEST_TAG,
                        true
                    )[0].assertTextContains(secondAnime.title)
                }
            }
        }
        step("Вернуть старые фильтры") {
            flakySafely {
                scenario(SetFiltersScenario(composeTestRule))
            }
        }
        step("Проверить отображение экрана под 2 фильтрами") {
            onComposeScreen<SeriesCollectionScreen>(composeTestRule) {
                flakySafely {
                    composeTestRule.onAllNodesWithTag(
                        SERIES_TITLE_TEST_TAG,
                        true
                    )[0].assertTextContains(firstAnime.title)
                }
            }
        }
    }

    @Test
    @DisplayName("Проверка перехода в детали аниме из коллекции")
    fun openSeriesDetailsFromSeriesCollectionScreen() = before {
        MockLibraryModule.retrieveOneAnimeItemMock(userId, firstAnime)
        launchActivity()
    }.after {}.run {
        onComposeScreen<SeriesCollectionScreen>(composeTestRule) {
            step("Обновить список коллекции")
            {
                flakySafely {
                    refreshButton.performClick()
                }
            }
            step("Переход в детали аниме") {
                flakySafely {
                    getSeriesItemCard(0, composeTestRule) {
                        performClick()
                    }
                }
            }
        }
        onComposeScreen<SeriesDetailScreen>(composeTestRule) {
            step("Проверка открытия карточки нужной карточки аниме") {
                flakySafely {
                    title.assertTextContains(firstAnime.title)
                }
            }
        }
    }
}