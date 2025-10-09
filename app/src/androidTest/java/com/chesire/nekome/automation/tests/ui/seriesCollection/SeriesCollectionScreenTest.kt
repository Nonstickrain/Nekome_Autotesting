package com.chesire.nekome.automation.tests.ui.seriesCollection

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextContains
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performTextInput
import com.chesire.nekome.UITest
import com.chesire.nekome.app.series.collection.ui.SERIES_TITLE_TEST_TAG
import com.chesire.nekome.automation.screens.search.SearchScreen
import com.chesire.nekome.automation.screens.seriesCollection.SeriesCollectionScreen
import com.chesire.nekome.automation.screens.seriesCollection.filter.FilterScreen
import com.chesire.nekome.automation.screens.seriesDetail.SeriesDetailScreen
import com.chesire.nekome.core.flags.SeriesStatus
import com.chesire.nekome.core.flags.SeriesType
import com.chesire.nekome.core.flags.Subtype
import com.chesire.nekome.core.flags.UserSeriesStatus
import com.chesire.nekome.core.models.ImageModel
import com.chesire.nekome.datasource.search.SearchDomain
import com.chesire.nekome.datasource.search.remote.SearchApi
import com.chesire.nekome.datasource.series.SeriesDomain
import com.chesire.nekome.datasource.series.remote.SeriesApi
import com.github.michaelbull.result.Ok
import dagger.hilt.android.testing.HiltAndroidTest
import io.qameta.allure.kotlin.junit4.DisplayName
import org.junit.Test
import io.github.kakaocup.compose.node.element.ComposeScreen.Companion.onComposeScreen
import io.mockk.clearAllMocks
import io.mockk.coEvery
import org.junit.Before
import javax.inject.Inject

@HiltAndroidTest
class SeriesCollectionScreenTest : UITest() {

    @Inject
    lateinit var searchApi: SearchApi

    @Inject
    lateinit var seriesApi: SeriesApi

    @Before
    fun setup() {
        clearAllMocks()
    }

    private val searchAnimeText = "Naruto"
    private val searchAnimeText2 = "Valve"
    private val userId = 0
    private val seriesId = 1
    private val seriesId2 = 2
    private val startingStatus2 = UserSeriesStatus.OnHold
    private val startingStatus = UserSeriesStatus.Current

    @Test
    @DisplayName("Проверка добавления нового аниме в коллекцию")
    fun addNewSeriesToCollectionTest() = before {
        coEvery {
            searchApi.searchForAnime("Naruto")
        } returns Ok(
            listOf(
                SearchDomain(
                    id = seriesId,
                    type = SeriesType.Anime,
                    synopsis = "",
                    canonicalTitle = searchAnimeText,
                    otherTitles = mapOf("en" to "en"),
                    subtype = Subtype.Manga,
                    posterImage = ImageModel(
                        tiny = ImageModel.ImageData(
                            url = "",
                            width = 0,
                            height = 0
                        ),
                        small = ImageModel.ImageData(
                            url = "",
                            width = 0,
                            height = 0
                        ),
                        medium = ImageModel.ImageData(
                            url = "",
                            width = 0,
                            height = 0
                        ),
                        large = ImageModel.ImageData(
                            url = "",
                            width = 0,
                            height = 0
                        )
                    )
                )
            )
        )
        coEvery {
            seriesApi.addAnime(userId, seriesId, startingStatus)
        } returns Ok(
            SeriesDomain(
                id = seriesId,
                userId = userId,
                title = searchAnimeText,
                subtype = Subtype.Manga,
                slug = "",
                userSeriesStatus = startingStatus,
                otherTitles = mapOf("en" to "en"),
                progress = 1,
                seriesStatus = SeriesStatus.Finished,
                type = SeriesType.Anime,
                rating = 1,
                totalLength = 24,
                startDate = "",
                endDate = "",
                posterImage = ImageModel(
                    tiny = ImageModel.ImageData(
                        url = "",
                        width = 0,
                        height = 0
                    ),
                    small = ImageModel.ImageData(
                        url = "",
                        width = 0,
                        height = 0
                    ),
                    medium = ImageModel.ImageData(
                        url = "",
                        width = 0,
                        height = 0
                    ),
                    large = ImageModel.ImageData(
                        url = "",
                        width = 0,
                        height = 0
                    )
                )
            )
        )
        launchActivity()
    }.after {}.run {
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
                        .performTextInput(searchAnimeText)
                    uiDevice.pressBack()
                }
            }
            step("Инциализация поиска") {
                flakySafely {
                    searchButton.performClick()
                }
            }
            step("Добавление в коллекцию второго элемента ответа поиска") {
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
        coEvery {
            seriesApi.retrieveAnime(userId)
        } returns Ok(
            listOf(
                SeriesDomain(
                    id = seriesId,
                    userId = userId,
                    title = searchAnimeText,
                    subtype = Subtype.Manga,
                    slug = "",
                    userSeriesStatus = startingStatus,
                    otherTitles = mapOf("en" to "en"),
                    progress = 1,
                    seriesStatus = SeriesStatus.Finished,
                    type = SeriesType.Anime,
                    rating = 1,
                    totalLength = 24,
                    startDate = "",
                    endDate = "",
                    posterImage = ImageModel(
                        tiny = ImageModel.ImageData(
                            url = "",
                            width = 0,
                            height = 0
                        ),
                        small = ImageModel.ImageData(
                            url = "",
                            width = 0,
                            height = 0
                        ),
                        medium = ImageModel.ImageData(
                            url = "",
                            width = 0,
                            height = 0
                        ),
                        large = ImageModel.ImageData(
                            url = "",
                            width = 0,
                            height = 0
                        )
                    )
                ),
                SeriesDomain(
                    id = seriesId2,
                    userId = userId,
                    title = searchAnimeText2,
                    subtype = Subtype.Manga,
                    slug = "",
                    userSeriesStatus = startingStatus2,
                    otherTitles = mapOf("en" to "en"),
                    progress = 2,
                    seriesStatus = SeriesStatus.Finished,
                    type = SeriesType.Anime,
                    rating = 1,
                    totalLength = 25,
                    startDate = "",
                    endDate = "",
                    posterImage = ImageModel(
                        tiny = ImageModel.ImageData(
                            url = "",
                            width = 0,
                            height = 0
                        ),
                        small = ImageModel.ImageData(
                            url = "",
                            width = 0,
                            height = 0
                        ),
                        medium = ImageModel.ImageData(
                            url = "",
                            width = 0,
                            height = 0
                        ),
                        large = ImageModel.ImageData(
                            url = "",
                            width = 0,
                            height = 0
                        )
                    )
                )
            )
        )
        coEvery {
            seriesApi.retrieveManga(userId)
        } returns Ok(emptyList())
        launchActivity()
    }.after {}.run {
        step("Тап по кнопке фильтров")
        {
            onComposeScreen<SeriesCollectionScreen>(composeTestRule) {
                flakySafely {
                    filterButton.performClick()
                }
            }
        }
        onComposeScreen<FilterScreen>(composeTestRule) {
            step("Выставить нужные фильтры") {
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
                    )[0].assertTextContains(searchAnimeText2)
                }
            }
        }
        step("Тап по кнопке фильтров")
        {
            onComposeScreen<SeriesCollectionScreen>(composeTestRule) {
                flakySafely {
                    filterButton.performClick()
                }
            }
        }
        onComposeScreen<FilterScreen>(composeTestRule) {
            step("Выставить нужные фильтры") {
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
        step("Проверить отображение экрана под 2 фильтрами") {
            onComposeScreen<SeriesCollectionScreen>(composeTestRule) {
                flakySafely {
                    composeTestRule.onAllNodesWithTag(
                        SERIES_TITLE_TEST_TAG,
                        true
                    )[0].assertTextContains(searchAnimeText)
                }
            }
        }
    }

    @Test
    @DisplayName("Проверка перехода в детали аниме из коллекции")
    fun openSeriesDetailsFromSeriesCollectionScreen() = before {
        coEvery {
            seriesApi.retrieveAnime(userId)
        } returns Ok(
            listOf(
                SeriesDomain(
                    id = seriesId,
                    userId = userId,
                    title = searchAnimeText,
                    subtype = Subtype.Manga,
                    slug = "",
                    userSeriesStatus = startingStatus,
                    otherTitles = mapOf("en" to "en"),
                    progress = 1,
                    seriesStatus = SeriesStatus.Finished,
                    type = SeriesType.Anime,
                    rating = 1,
                    totalLength = 24,
                    startDate = "",
                    endDate = "",
                    posterImage = ImageModel(
                        tiny = ImageModel.ImageData(
                            url = "",
                            width = 0,
                            height = 0
                        ),
                        small = ImageModel.ImageData(
                            url = "",
                            width = 0,
                            height = 0
                        ),
                        medium = ImageModel.ImageData(
                            url = "",
                            width = 0,
                            height = 0
                        ),
                        large = ImageModel.ImageData(
                            url = "",
                            width = 0,
                            height = 0
                        )
                    )
                )
            )
        )
        coEvery {
            seriesApi.retrieveManga(userId)
        } returns Ok(emptyList())
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
                    title.assertTextContains(searchAnimeText)
                }
            }
        }
    }
}