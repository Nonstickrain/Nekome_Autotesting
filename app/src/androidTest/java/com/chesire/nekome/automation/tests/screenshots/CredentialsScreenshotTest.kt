package com.chesire.nekome.automation.tests.screenshots

import androidx.compose.ui.test.isDisplayed
import androidx.compose.ui.test.onNodeWithTag
import com.chesire.nekome.ScreenshotTest
import com.chesire.nekome.app.login.credentials.ui.CREDENTIALS_SCREEN_TEST_TAG
import dagger.hilt.android.testing.HiltAndroidTest
import io.qameta.allure.kotlin.junit4.DisplayName
import org.junit.Test

@HiltAndroidTest
class LogInOnCredentialsScreenTest: ScreenshotTest(){

    override val startLoggedIn: Boolean = false

    @Test
    @DisplayName("Проверка состава экрана авторизации")
    fun logInScreenSnapshot() = run{
        step("Снятие скриншот экрана"){
            composeTestRule.waitUntil { composeTestRule.onNodeWithTag(CREDENTIALS_SCREEN_TEST_TAG).isDisplayed() }
            takeScreenshot("LoginScreenScreenshot")
        }
    }
}