package com.chesire.nekome.injection

import com.chesire.nekome.core.flags.SeriesType
import com.chesire.nekome.core.flags.Subtype
import com.chesire.nekome.core.models.ImageModel
import com.chesire.nekome.datasource.search.SearchDomain
import com.chesire.nekome.datasource.search.remote.SearchApi
import com.github.michaelbull.result.Ok
import dagger.Module
import dagger.Provides
import dagger.Reusable
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import io.mockk.coEvery
import io.mockk.mockk

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [SearchModule::class]
)
object MockSearchModule {

    private val searchApi: SearchApi = mockk(relaxed = true)

    @Provides
    @Reusable
    fun provideApi(): SearchApi = searchApi

    fun oneItemSearchMock(seriesId: Int, title: String, seriesType: SeriesType) {
        when (seriesType) {
            SeriesType.Manga -> {
                coEvery {
                    searchApi.searchForManga(title)
                } returns Ok(
                    listOf(
                        SearchDomain(
                            id = seriesId,
                            type = seriesType,
                            synopsis = "",
                            canonicalTitle = title,
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

            SeriesType.Anime -> {
                coEvery {
                    searchApi.searchForAnime(title)
                } returns Ok(
                    listOf(
                        SearchDomain(
                            id = seriesId,
                            type = seriesType,
                            synopsis = "",
                            canonicalTitle = title,
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

            SeriesType.Unknown -> {
                coEvery {
                    searchApi.searchForManga(title)
                } returns Ok(emptyList())
                coEvery {
                    searchApi.searchForAnime(title)
                } returns Ok(emptyList())
            }
        }
    }
}
