package com.jbworks.wordclock.widget

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

/**
 * BroadcastReceiver that handles AlarmManager triggers for widget updates.
 *
 * This receiver is triggered at the start of each minute to update all widgets
 * with the current time in Hebrew.
 */
class WidgetUpdateReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        // Update all widgets
        WordClockWidget.updateAllWidgets(context)

        // Schedule next update
        WidgetUpdateScheduler.scheduleNextUpdate(context)
    }
}
