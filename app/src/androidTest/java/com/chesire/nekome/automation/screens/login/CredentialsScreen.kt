package com.chesire.nekome.automator.screens.login

import androidx.compose.ui.test.SemanticsNodeInteractionsProvider
import com.chesire.nekome.app.login.credentials.ui.CREDENTIALS_SCREEN_TEST_TAG
import com.chesire.nekome.app.login.credentials.ui.PASSWORD_INPUT_TEST_TAG
import com.chesire.nekome.app.login.credentials.ui.USERNAME_INPUT_TEST_TAG
import io.github.kakaocup.compose.node.element.ComposeScreen
import io.github.kakaocup.compose.node.element.KNode


class CredentialsScreen(private val semanticsProvider: SemanticsNodeInteractionsProvider) :
    ComposeScreen<CredentialsScreen>(
        semanticsProvider = semanticsProvider,
        viewBuilderAction = {
            useUnmergedTree = true
            hasTestTag(CREDENTIALS_SCREEN_TEST_TAG)
        }
    ) {
    val usernameInput: KNode = child {
        useUnmergedTree = true
        hasTestTag(USERNAME_INPUT_TEST_TAG)
    }
    val passwordInput: KNode = child {
        useUnmergedTree = true
        hasTestTag(PASSWORD_INPUT_TEST_TAG)
    }
}