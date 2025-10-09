package com.chesire.nekome.automation.screens.settings

import androidx.compose.ui.test.SemanticsNodeInteractionsProvider
import com.chesire.nekome.app.settings.config.ui.ConfigTags
import com.chesire.nekome.app.settings.config.ui.SETTING_ELEMENT_TEST_TAG
import io.github.kakaocup.compose.node.element.ComposeScreen
import io.github.kakaocup.compose.node.element.KNode

class SettingsScreen(semanticsProvider: SemanticsNodeInteractionsProvider) :
    ComposeScreen<SettingsScreen>(
        semanticsProvider = semanticsProvider,
        viewBuilderAction = {
            useUnmergedTree = true
            hasTestTag(ConfigTags.Root)
        }
    ) {
    fun getSetting(settingName: String, action: KNode.() -> Unit) {
       onNode {
           hasTestTag(SETTING_ELEMENT_TEST_TAG)
           hasText(settingName)
       }.perform { action() }
    }
}