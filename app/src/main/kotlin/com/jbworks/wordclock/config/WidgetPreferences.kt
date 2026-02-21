package com.jbworks.wordclock.config

import android.content.Context
import android.content.Context.MODE_PRIVATE

/**
 * Manages per-widget preferences using SharedPreferences.
 *
 * Each widget instance has its own isolated settings.
 */
class WidgetPreferences(context: Context, private val widgetId: Int) {

    private val prefs = context.getSharedPreferences("widget_prefs_$widgetId", MODE_PRIVATE)

    companion object {
        private const val KEY_SHOW_HASHAAH = "show_hashaah"
        private const val KEY_SHOW_TIME_OF_DAY = "show_time_of_day"
    }

    /**
     * Gets whether to show "השעה" at the beginning.
     */
    fun getShowHashaah(): Boolean {
        return prefs.getBoolean(KEY_SHOW_HASHAAH, false)
    }

    /**
     * Sets whether to show "השעה" at the beginning.
     */
    fun setShowHashaah(show: Boolean) {
        prefs.edit().putBoolean(KEY_SHOW_HASHAAH, show).apply()
    }

    /**
     * Gets whether to show time of day suffix.
     */
    fun getShowTimeOfDay(): Boolean {
        return prefs.getBoolean(KEY_SHOW_TIME_OF_DAY, false)
    }

    /**
     * Sets whether to show time of day suffix.
     */
    fun setShowTimeOfDay(show: Boolean) {
        prefs.edit().putBoolean(KEY_SHOW_TIME_OF_DAY, show).apply()
    }

    /**
     * Clears all preferences for this widget.
     * Call when widget is deleted.
     */
    fun clear() {
        prefs.edit().clear().apply()
    }
}
