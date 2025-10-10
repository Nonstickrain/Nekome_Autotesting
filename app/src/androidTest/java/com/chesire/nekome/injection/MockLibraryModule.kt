package com.chesire.nekome.injection

import com.chesire.nekome.core.flags.SeriesStatus
import com.chesire.nekome.core.flags.SeriesType
import com.chesire.nekome.core.flags.Subtype
import com.chesire.nekome.core.flags.UserSeriesStatus
import com.chesire.nekome.core.models.ImageModel
import com.chesire.nekome.datasource.series.SeriesDomain
import com.chesire.nekome.datasource.series.remote.SeriesApi
import com.chesire.nekome.helpers.SeriesMockData
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
    replaces = [LibraryModule::class]
)
object MockLibraryModule {

    private val seriesApi: SeriesApi = mockk(relaxed = true)

    @Provides
    @Reusable
    fun provideApi(): SeriesApi = seriesApi

    fun addOneAnimeItemMock(
        userId: Int,
        seriesId: Int,
        startingStatus: UserSeriesStatus,
        animeTitle: String
    ) {
        coEvery {
            seriesApi.addAnime(userId, seriesId, startingStatus)
        } returns Ok(
            SeriesDomain(
                id = seriesId,
                userId = userId,
                title = animeTitle,
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
    }

    fun retrieveTwoAnimeItemsMock(
        userId: Int,
        firstItem: SeriesMockData,
        secondItem: SeriesMockData
    ) {
        coEvery {
            seriesApi.retrieveAnime(userId)
        } returns Ok(
            listOf(
                SeriesDomain(
                    id = firstItem.id,
                    userId = userId,
                    title = firstItem.title,
                    subtype = Subtype.Manga,
                    slug = "",
                    userSeriesStatus = firstItem.userSeriesStatus,
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
                    id = secondItem.id,
                    userId = userId,
                    title = secondItem.title,
                    subtype = Subtype.Manga,
                    slug = "",
                    userSeriesStatus = secondItem.userSeriesStatus,
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
    }

    fun retrieveOneAnimeItemMock(userId: Int, firstItem: SeriesMockData) {
        coEvery {
            seriesApi.retrieveAnime(userId)
        } returns Ok(
            listOf(
                SeriesDomain(
                    id = firstItem.id,
                    userId = userId,
                    title = firstItem.title,
                    subtype = Subtype.Manga,
                    slug = "",
                    userSeriesStatus = firstItem.userSeriesStatus,
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
    }
}