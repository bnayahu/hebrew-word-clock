package com.jbworks.wordclock.widget

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.widget.RemoteViews
import com.jbworks.wordclock.R
import com.jbworks.wordclock.config.WidgetConfigActivity
import com.jbworks.wordclock.config.WidgetPreferences
import com.jbworks.wordclock.theme.ThemeManager
import com.jbworks.wordclock.time.HebrewTimeConverter
import java.util.Calendar

/**
 * Main AppWidgetProvider for the Hebrew Word Clock widget.
 *
 * Handles widget lifecycle events and updates the display with Hebrew time text.
 */
class WordClockWidget : AppWidgetProvider() {

    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {
        // Update all widget instances
        for (appWidgetId in appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId)
        }

        // Schedule next minute update
        WidgetUpdateScheduler.scheduleNextUpdate(context)
    }

    override fun onEnabled(context: Context) {
        // First widget added - start periodic updates
        WidgetUpdateScheduler.scheduleNextUpdate(context)
    }

    override fun onDisabled(context: Context) {
        // Last widget removed - cancel all updates
        WidgetUpdateScheduler.cancelUpdates(context)
    }

    override fun onDeleted(context: Context, appWidgetIds: IntArray) {
        // Clean up preferences for deleted widgets
        for (appWidgetId in appWidgetIds) {
            val prefs = WidgetPreferences(context, appWidgetId)
            prefs.clear()
        }
    }

    companion object {
        /**
         * Updates a single widget instance with the current Hebrew time.
         */
        fun updateAppWidget(
            context: Context,
            appWidgetManager: AppWidgetManager,
            appWidgetId: Int
        ) {
            val prefs = WidgetPreferences(context, appWidgetId)

            // Get current time
            val calendar = Calendar.getInstance()
            val hour = calendar.get(Calendar.HOUR_OF_DAY)
            val minute = calendar.get(Calendar.MINUTE)

            // Get format options from preferences
            val options = HebrewTimeConverter.TimeFormatOptions(
                showHashaah = prefs.getShowHashaah(),
                showDiacritics = prefs.getShowDiacritics(),
                showTimeOfDay = prefs.getShowTimeOfDay(),
                useSpecialQuarters = prefs.getUseSpecialQuarters()
            )

            // Convert to Hebrew text with options
            val hebrewTime = HebrewTimeConverter.convertTime(hour, minute, options)

            // Get colors based on system dark mode
            val colors = ThemeManager.getThemeColors(context)

            // Build RemoteViews
            val views = RemoteViews(context.packageName, R.layout.widget_layout)
            views.setTextViewText(R.id.time_text, hebrewTime)
            views.setTextColor(R.id.time_text, colors.textColor)
            views.setInt(R.id.widget_container, "setBackgroundColor", colors.backgroundColor)

            // Set click intent to open configuration
            val configIntent = Intent(context, WidgetConfigActivity::class.java).apply {
                putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId)
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
            }
            val pendingIntent = PendingIntent.getActivity(
                context,
                appWidgetId,
                configIntent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )
            views.setOnClickPendingIntent(R.id.widget_container, pendingIntent)

            // Update widget
            appWidgetManager.updateAppWidget(appWidgetId, views)
        }

        /**
         * Updates all widget instances.
         */
        fun updateAllWidgets(context: Context) {
            val appWidgetManager = AppWidgetManager.getInstance(context)
            val appWidgetIds = appWidgetManager.getAppWidgetIds(
                android.content.ComponentName(context, WordClockWidget::class.java)
            )
            for (appWidgetId in appWidgetIds) {
                updateAppWidget(context, appWidgetManager, appWidgetId)
            }
        }
    }
}
