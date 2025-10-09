package com.chesire.nekome.automation.tests.ui.search

import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.onRoot
import androidx.compose.ui.test.performTextInput
import androidx.compose.ui.test.printToLog
import com.chesire.nekome.UITest
import com.chesire.nekome.automation.screens.search.SearchScreen
import com.chesire.nekome.automation.screens.seriesCollection.SeriesCollectionScreen
import com.chesire.nekome.core.flags.SeriesType
import com.chesire.nekome.core.flags.Subtype
import com.chesire.nekome.core.models.ImageModel
import com.chesire.nekome.datasource.search.SearchDomain
import com.chesire.nekome.datasource.search.remote.SearchApi
import com.github.michaelbull.result.Ok
import dagger.hilt.android.testing.HiltAndroidTest
import io.github.kakaocup.compose.node.element.ComposeScreen.Companion.onComposeScreen
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.qameta.allure.kotlin.junit4.DisplayName
import org.junit.Before
import org.junit.Test
import javax.inject.Inject

@HiltAndroidTest
class SearchScreenTest : UITest() {
    @Inject
    lateinit var searchApi: SearchApi

    @Before
    fun setup() {
        clearAllMocks()
        coEvery {
            searchApi.searchForManga("Valve")
        } returns Ok(
            listOf(
                SearchDomain(
                    id = seriesId,
                    type = SeriesType.Manga,
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
    }

    private val searchAnimeText = "Valve"
    private val seriesId = 1


    @Test
    @DisplayName("Проверка работы поискового тега по манге")
    fun mangaTagSearchTest() = before {
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
            step("Выбор манга тэга") {
                flakySafely {
                    mangaCheckBox.performClick()
                }
            }
            step("Инциализация поиска") {
                flakySafely {
                    searchButton.performClick()
                }
            }
            step("Проверка результата поиска. Отображается только манга") {
                flakySafely {
                    composeTestRule.onRoot(useUnmergedTree = true)
                        .printToLog("Дерево SeriesCollectionScreen")
                    getSearchItemTitle(0, composeTestRule) {
                        assertTextContains(searchAnimeText)
                    }
                }
            }
        }
    }
}

