package com.jbworks.wordclock.theme

import android.content.Context
import android.content.res.Configuration
import android.os.Build
import com.jbworks.wordclock.R

/**
 * Manages theme colors including Material You dynamic colors.
 *
 * Automatically selects light or dark theme based on system settings.
 */
object ThemeManager {

    data class ColorTheme(
        val textColor: Int,
        val backgroundColor: Int
    )

    /**
     * Gets the color theme based on system dark mode setting.
     */
    fun getThemeColors(context: Context): ColorTheme {
        val isDarkMode = (context.resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) == Configuration.UI_MODE_NIGHT_YES
        return if (isDarkMode) {
            getDynamicDarkColors(context)
        } else {
            getDynamicLightColors(context)
        }
    }

    /**
     * Gets Material You dynamic light theme colors.
     * Falls back to default colors on Android 11 and below.
     */
    private fun getDynamicLightColors(context: Context): ColorTheme {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            ColorTheme(
                textColor = context.getColor(android.R.color.system_accent1_600),
                backgroundColor = context.getColor(android.R.color.system_neutral1_50)
            )
        } else {
            ColorTheme(
                textColor = context.getColor(R.color.default_text),
                backgroundColor = context.getColor(R.color.default_background)
            )
        }
    }

    /**
     * Gets Material You dynamic dark theme colors.
     * Falls back to dynamic light theme on Android 11 and below.
     */
    private fun getDynamicDarkColors(context: Context): ColorTheme {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            ColorTheme(
                textColor = context.getColor(android.R.color.system_accent1_200),
                backgroundColor = context.getColor(android.R.color.system_neutral1_900)
            )
        } else {
            getDynamicLightColors(context)
        }
    }
}
