package com.jbworks.wordclock.widget

import android.appwidget.AppWidgetManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

/**
 * BroadcastReceiver that handles device boot completion.
 *
 * After a device reboot, all alarms are cleared. This receiver re-schedules
 * widget updates if any widgets are installed.
 */
class BootReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == Intent.ACTION_BOOT_COMPLETED) {
            // Check if any widgets are installed
            val appWidgetManager = AppWidgetManager.getInstance(context)
            val appWidgetIds = appWidgetManager.getAppWidgetIds(
                android.content.ComponentName(context, WordClockWidget::class.java)
            )

            if (appWidgetIds.isNotEmpty()) {
                // Re-schedule updates
                WidgetUpdateScheduler.scheduleNextUpdate(context)

                // Update widgets immediately
                WordClockWidget.updateAllWidgets(context)
            }
        }
    }
}
