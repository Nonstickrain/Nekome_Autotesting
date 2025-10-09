package com.chesire.nekome.automation.screens.seriesCollection.filter

import androidx.compose.ui.test.SemanticsNodeInteractionsProvider
import com.chesire.nekome.app.series.collection.ui.FILTER_ROW_TEST_TAG
import com.chesire.nekome.app.series.collection.ui.FilterTags
import io.github.kakaocup.compose.node.element.ComposeScreen
import io.github.kakaocup.compose.node.element.KNode

class FilterScreen(semanticsProvider: SemanticsNodeInteractionsProvider) :
    ComposeScreen<FilterScreen>(
        semanticsProvider = semanticsProvider,
        viewBuilderAction = {
            useUnmergedTree = true
            hasTestTag(FilterTags.Root)
        }
    ) {

    val okButton: KNode = child {
        hasTestTag(FilterTags.OkButton)
    }

    fun checkBox(position: Int, action: KNode.() -> Unit) {
        onNode {
            hasTestTag(FILTER_ROW_TEST_TAG)
        }.child<KNode> {
            hasTestTag(FilterTags.OptionChecked)
            hasPosition(position)
        }.perform { action() }
    }
}