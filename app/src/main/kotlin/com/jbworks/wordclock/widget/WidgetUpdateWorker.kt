package com.jbworks.wordclock.widget

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters

/**
 * WorkManager Worker that provides backup periodic updates for widgets.
 *
 * This runs every 15 minutes as a fallback in case exact alarms are missed.
 * It also re-schedules the exact alarm to ensure minute-boundary updates continue.
 */
class WidgetUpdateWorker(
    private val context: Context,
    workerParams: WorkerParameters
) : Worker(context, workerParams) {

    override fun doWork(): Result {
        // Update all widgets
        WordClockWidget.updateAllWidgets(context)

        // Ensure exact alarm is scheduled for next minute
        WidgetUpdateScheduler.scheduleNextUpdate(context)

        return Result.success()
    }
}
