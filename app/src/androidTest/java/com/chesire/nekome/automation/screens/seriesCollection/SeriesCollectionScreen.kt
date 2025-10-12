package com.chesire.nekome.automation.screens.seriesCollection

import androidx.compose.ui.test.SemanticsNodeInteractionsProvider
import androidx.compose.ui.test.assertTextContains
import androidx.compose.ui.test.junit4.ComposeTestRule
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onChildAt
import androidx.compose.ui.test.onNodeWithTag
import com.chesire.nekome.app.series.collection.ui.ADD_NEW_BUTTON_TEST_TAG
import com.chesire.nekome.app.series.collection.ui.SERIES_CARD_LIST_TEST_TAG
import com.chesire.nekome.app.series.collection.ui.SERIES_COLLECTION_SCREEN_TEST_TAG
import com.chesire.nekome.app.series.collection.ui.SERIES_TITLE_TEST_TAG
import com.chesire.nekome.app.series.collection.ui.SeriesCollectionTags
import io.github.kakaocup.compose.node.element.ComposeScreen
import io.github.kakaocup.compose.node.element.KNode

class SeriesCollectionScreen(semanticsProvider: SemanticsNodeInteractionsProvider) :
    ComposeScreen<SeriesCollectionScreen>(
        semanticsProvider = semanticsProvider,
        viewBuilderAction = {
            useUnmergedTree = true
            hasTestTag(SERIES_COLLECTION_SCREEN_TEST_TAG)
        }
    ) {
    val addButton: KNode = child {
        hasTestTag(ADD_NEW_BUTTON_TEST_TAG)
    }
    val filterButton: KNode = child {
        hasTestTag(SeriesCollectionTags.MenuFilter)
    }
    val refreshButton: KNode = child {
        hasTestTag(SeriesCollectionTags.MenuRefresh)
    }

    fun getSeriesItemCard(position: Int, composeRule: ComposeTestRule, action: KNode.() -> Unit) {
        composeRule.onNodeWithTag(SERIES_CARD_LIST_TEST_TAG).onChildAt(position).apply {
            onNode { hasTestTag(SeriesCollectionTags.SeriesItem) }.perform { action() }
        }
    }

    fun checkSeriesTitle(position: Int, composeRule: ComposeTestRule, title: String) {
        composeRule.onAllNodesWithTag(SERIES_TITLE_TEST_TAG,true)[position].assertTextContains(title)
    }
}
