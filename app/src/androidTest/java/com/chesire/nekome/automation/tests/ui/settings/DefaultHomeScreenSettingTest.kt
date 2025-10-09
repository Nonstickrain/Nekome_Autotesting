package com.chesire.nekome.automation.tests.ui.settings

import com.chesire.nekome.UITest
import com.chesire.nekome.automation.screens.dialogs.DialogScreen
import com.chesire.nekome.core.resources.R.string
import com.chesire.nekome.automation.screens.mainActivity.MainActivityScreen
import com.chesire.nekome.automation.screens.settings.SettingsScreen
import dagger.hilt.android.testing.HiltAndroidTest
import io.github.kakaocup.compose.node.element.ComposeScreen.Companion.onComposeScreen
import io.github.kakaocup.kakao.common.utilities.getResourceString
import io.qameta.allure.kotlin.junit4.DisplayName
import org.junit.Test

@HiltAndroidTest
class DefaultHomeScreenSettingTest : UITest() {

    @Test
    @DisplayName("Проверка смены языка отображения заголовков")
    fun setDifferentLanguageTest() = before {
        launchActivity()
    }.after {}.run {
        onComposeScreen<MainActivityScreen>(composeTestRule) {
            step("Переключение на таб настроек") {
                settingsTag.performClick()
            }
        }
        onComposeScreen<SettingsScreen>(composeTestRule) {
            step("Тап по настройке начального экрана") {
                flakySafely {
                    getSetting(getResourceString(string.settings_default_home_title)) {
                        performClick()
                    }
                }
            }
        }
        onComposeScreen<DialogScreen>(composeTestRule) {
            step("Выбрать мангу в диалоговом окне") {
                flakySafely {
                   getRatioButton(1) {
                       performClick()
                   }
                }
            }
            step("Нажать OK") {
                flakySafely {
                    okButton.performClick()
                }
            }
        }
        step("Перезагрузка приложения") {
            flakySafely {
                uiDevice.pressHome()
                launchActivity()
            }
        }
        onComposeScreen<MainActivityScreen>(composeTestRule) {
            step("Выбран тэг манги в боттомбаре") {
                mangaTag.assertIsSelected()
            }
        }
    }
}