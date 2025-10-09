package com.chesire.nekome.automation.screens.dialogs

import androidx.compose.ui.test.SemanticsNodeInteractionsProvider
import com.chesire.nekome.core.compose.composables.DialogTags
import io.github.kakaocup.compose.node.element.ComposeScreen
import io.github.kakaocup.compose.node.element.KNode

class DialogScreen(semanticsProvider: SemanticsNodeInteractionsProvider) :
    ComposeScreen<DialogScreen>(
        semanticsProvider = semanticsProvider,
        viewBuilderAction = {
            useUnmergedTree = true
            hasTestTag(DialogTags.Root)
        }
    ) {
    val okButton: KNode = child {
        hasTestTag(DialogTags.OkButton)
    }

    fun getRatioButton(position: Int, action: KNode.()->Unit) {
        onNode {
            hasTestTag(DialogTags.OptionRadio)
            hasPosition(position)
        }perform{ action() }
    }
}