package com.chesire.nekome

import androidx.compose.ui.test.junit4.createEmptyComposeRule
import androidx.test.core.app.ActivityScenario
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.uiautomator.UiDevice
import com.adevinta.android.barista.rule.cleardata.ClearDatabaseRule
import com.adevinta.android.barista.rule.cleardata.ClearPreferencesRule
import com.chesire.nekome.core.preferences.ApplicationPreferences
import com.chesire.nekome.core.preferences.SeriesPreferences
import com.chesire.nekome.database.dao.SeriesDao
import com.chesire.nekome.database.dao.UserDao
import com.chesire.nekome.datasource.auth.local.AuthProvider
import com.chesire.nekome.helpers.createTestUser
import com.chesire.nekome.helpers.login
import com.chesire.nekome.helpers.logout
import com.chesire.nekome.helpers.reset
import com.chesire.nekome.ui.MainActivity
import com.kaspersky.kaspresso.testcases.api.testcase.TestCase
import dagger.hilt.android.testing.HiltAndroidRule
import javax.inject.Inject
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.runner.RunWith

/**
 * Provides a base class to use for all UI tests.
 */
@RunWith(AndroidJUnit4::class)
abstract class UITest : TestCase() {

    @Suppress("LeakingThis")
    @get:Rule(order = 0)
    val hilt = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val clearDatabase = ClearDatabaseRule()

    @get:Rule(order = 2)
    val clearPreferences = ClearPreferencesRule()

    @get:Rule
    val composeTestRule = createEmptyComposeRule()

    @Inject
    lateinit var authProvider: AuthProvider

    @Inject
    lateinit var series: SeriesDao

    @Inject
    lateinit var user: UserDao

    @Inject
    lateinit var applicationPreferences: ApplicationPreferences

    @Inject
    lateinit var seriesPreferences: SeriesPreferences


    /**
     * Flag for if the test should start with a logged in user.
     * Defaults to `true`, override to force the user to be logged out.
     */
    open val startLoggedIn: Boolean = true
    open val uiDevice: UiDevice = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())
    /**
     * Initial setup method.
*/
    @Before
    open fun setUp() {
        hilt.inject()

        runBlocking {
            applicationPreferences.reset()
            seriesPreferences.reset()
        }

        if (startLoggedIn) {
            authProvider.login()
            user.createTestUser()
        } else {
            authProvider.logout()
        }
    }
    /**
     * Launches the [Activity] using the [ActivityScenario].
     */
    protected fun launchActivity() {
        ActivityScenario.launch(MainActivity::class.java)
        // Not the nicest solution, but it keeps compose views a bit happier when they launch.
        Thread.sleep(200)
    }
}
