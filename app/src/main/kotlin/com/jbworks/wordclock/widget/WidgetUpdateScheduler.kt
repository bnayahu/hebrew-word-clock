package com.jbworks.wordclock.widget

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import java.util.Calendar
import java.util.concurrent.TimeUnit

/**
 * Manages widget update scheduling using a hybrid approach:
 * - Primary: AlarmManager with exact timing at minute boundaries
 * - Backup: WorkManager periodic updates every 15 minutes
 *
 * This ensures widgets stay current even if exact alarms are missed.
 */
object WidgetUpdateScheduler {

    private const val WORK_NAME = "widget_update_work"
    private const val UPDATE_REQUEST_CODE = 1001

    /**
     * Schedules the next widget update at the start of the next minute.
     */
    fun scheduleNextUpdate(context: Context) {
        scheduleExactAlarm(context)
        scheduleBackupWork(context)
    }

    /**
     * Schedules an exact alarm for the start of the next minute.
     */
    private fun scheduleExactAlarm(context: Context) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

        // Calculate next minute boundary
        val nextMinute = Calendar.getInstance().apply {
            add(Calendar.MINUTE, 1)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }

        val intent = Intent(context, WidgetUpdateReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            UPDATE_REQUEST_CODE,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        // Use setExactAndAllowWhileIdle for precise timing even in Doze mode
        alarmManager.setExactAndAllowWhileIdle(
            AlarmManager.RTC,
            nextMinute.timeInMillis,
            pendingIntent
        )
    }

    /**
     * Schedules periodic backup updates via WorkManager.
     * This serves as a fallback if exact alarms are missed.
     */
    private fun scheduleBackupWork(context: Context) {
        val workRequest = PeriodicWorkRequestBuilder<WidgetUpdateWorker>(
            15, TimeUnit.MINUTES
        ).build()

        WorkManager.getInstance(context).enqueueUniquePeriodicWork(
            WORK_NAME,
            ExistingPeriodicWorkPolicy.KEEP,
            workRequest
        )
    }

    /**
     * Cancels all scheduled updates.
     * Call when the last widget is removed.
     */
    fun cancelUpdates(context: Context) {
        // Cancel alarm
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, WidgetUpdateReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            UPDATE_REQUEST_CODE,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        alarmManager.cancel(pendingIntent)

        // Cancel work
        WorkManager.getInstance(context).cancelUniqueWork(WORK_NAME)
    }
}
