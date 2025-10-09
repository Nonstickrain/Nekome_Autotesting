package com.chesire.nekome.automation.screens.mainActivity

import androidx.compose.ui.test.SemanticsNodeInteractionsProvider
import com.chesire.nekome.ui.MainActivityTags
import io.github.kakaocup.compose.node.element.ComposeScreen
import io.github.kakaocup.compose.node.element.KNode

class MainActivityScreen(semanticsProvider: SemanticsNodeInteractionsProvider) :
    ComposeScreen<MainActivityScreen>(
        semanticsProvider = semanticsProvider,
        viewBuilderAction = {
            useUnmergedTree = true
            hasTestTag(MainActivityTags.Root)
        }
    ) {
    val settingsTag: KNode = child {
        hasTestTag(MainActivityTags.Settings)
    }
    val mangaTag: KNode = child {
        hasTestTag(MainActivityTags.Manga)
    }
}