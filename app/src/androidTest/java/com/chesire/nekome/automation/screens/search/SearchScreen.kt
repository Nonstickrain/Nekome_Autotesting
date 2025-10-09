package com.chesire.nekome.automation.screens.search

import androidx.compose.ui.test.SemanticsNodeInteractionsProvider
import androidx.compose.ui.test.junit4.ComposeTestRule
import androidx.compose.ui.test.onChildAt
import androidx.compose.ui.test.onNodeWithTag
import com.chesire.nekome.app.search.search.ui.ADD_SERIES_BUTTON_TEST_TAG
import com.chesire.nekome.app.search.search.ui.SEARCH_BUTTON_TEST_TAG
import com.chesire.nekome.app.search.search.ui.SEARCH_ITEM_TITLE_TEST_TAG
import com.chesire.nekome.app.search.search.ui.SEARCH_SCREEN_TEST_TAG
import com.chesire.nekome.app.search.search.ui.SERIES_LIST_TEST_TAG
import com.chesire.nekome.app.search.search.ui.SearchTags
import io.github.kakaocup.compose.node.element.ComposeScreen
import io.github.kakaocup.compose.node.element.KNode

class SearchScreen(semanticsProvider: SemanticsNodeInteractionsProvider) :
    ComposeScreen<SearchScreen>(
        semanticsProvider = semanticsProvider,
        viewBuilderAction = {
            useUnmergedTree = true
            hasTestTag(SEARCH_SCREEN_TEST_TAG)
        }
    ) {

    val searchButton: KNode = child {
        hasTestTag(SEARCH_BUTTON_TEST_TAG)
    }
    val mangaCheckBox: KNode = child {
        hasTestTag(SearchTags.Manga)
    }

    fun getSearchItemAddButton(position: Int, composeRule: ComposeTestRule, action: KNode.() -> Unit) {
        composeRule.onNodeWithTag(SERIES_LIST_TEST_TAG).onChildAt(position).apply {
            onNode { hasTestTag(ADD_SERIES_BUTTON_TEST_TAG) }.perform { action() }
        }
    }

    fun getSearchItemTitle(position: Int, composeRule: ComposeTestRule, action: KNode.() -> Unit) {
        composeRule.onNodeWithTag(SERIES_LIST_TEST_TAG).onChildAt(position).apply {
            onNode { hasTestTag(SEARCH_ITEM_TITLE_TEST_TAG) }.perform { action() }
        }
    }
}

