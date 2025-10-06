package com.chesire.nekome

import android.graphics.Bitmap
import android.view.View
import java.io.File
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

abstract class ScreenshotTest: UITest() {

    /**
     * Папка для сохранения скриншотов
     */
    open val screenshotFolder: String by lazy {
        val dir = File(
            composeTestRule.activity.getExternalFilesDir(null),
            "screenshots/${this::class.simpleName}"
        )
        if (!dir.exists()) dir.mkdirs()
        dir.absolutePath
    }

    /**
     * Сохраняет скриншот всего экрана
     */
    protected fun takeScreenshot(name: String) {
        val bitmap = captureComposeBitmap()
        saveBitmap(bitmap, name)
    }

    private fun captureComposeBitmap(): Bitmap {
        val view: View = composeTestRule.activity.window.decorView.rootView
        val bitmap = Bitmap.createBitmap(view.width, view.height, Bitmap.Config.ARGB_8888)
        val canvas = android.graphics.Canvas(bitmap)
        view.draw(canvas)
        return bitmap
    }

    private fun saveBitmap(bitmap: Bitmap, name: String) {
        val timestamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US).format(Date())
        val file = File(screenshotFolder, "${name}_$timestamp.png")
        FileOutputStream(file).use { out ->
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, out)
        }
    }
}