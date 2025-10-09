package com.chesire.nekome.automation.screens.seriesDetail

import androidx.compose.ui.test.SemanticsNodeInteractionsProvider
import com.chesire.nekome.app.series.item.ui.SERIES_DETAIL_SCREEN_TEST_TAG
import com.chesire.nekome.app.series.item.ui.SERIES_DETAIL_TITLE_TEST_TAG
import io.github.kakaocup.compose.node.element.ComposeScreen
import io.github.kakaocup.compose.node.element.KNode

class SeriesDetailScreen(semanticsProvider: SemanticsNodeInteractionsProvider) :
    ComposeScreen<SeriesDetailScreen>(
        semanticsProvider = semanticsProvider,
        viewBuilderAction = {
            useUnmergedTree = true
            hasTestTag(SERIES_DETAIL_SCREEN_TEST_TAG)
        }
    ) {

        val title: KNode = child {
            hasTestTag(SERIES_DETAIL_TITLE_TEST_TAG)
        }
}
