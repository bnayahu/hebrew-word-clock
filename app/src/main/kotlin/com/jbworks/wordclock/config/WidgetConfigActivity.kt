package com.jbworks.wordclock.config

import android.app.Activity
import android.appwidget.AppWidgetManager
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.switchmaterial.SwitchMaterial
import com.jbworks.wordclock.R
import com.jbworks.wordclock.theme.ThemeManager
import com.jbworks.wordclock.time.HebrewTimeConverter
import com.jbworks.wordclock.widget.WordClockWidget
import java.util.Calendar

/**
 * Configuration activity for the Hebrew Word Clock widget.
 *
 * Allows users to select theme colors and font size with a live preview.
 */
class WidgetConfigActivity : AppCompatActivity() {

    private var appWidgetId = AppWidgetManager.INVALID_APPWIDGET_ID

    private lateinit var previewContainer: android.view.View
    private lateinit var previewText: TextView
    private lateinit var showHashaahSwitch: SwitchMaterial
    private lateinit var showDiacriticsSwitch: SwitchMaterial
    private lateinit var showTimeOfDaySwitch: SwitchMaterial
    private lateinit var useSpecialQuartersSwitch: SwitchMaterial
    private lateinit var saveButton: Button
    private lateinit var cancelButton: Button

    private lateinit var prefs: WidgetPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Set result to CANCELED initially
        setResult(Activity.RESULT_CANCELED)

        setContentView(R.layout.activity_configuration)

        // Get widget ID from intent
        appWidgetId = intent?.extras?.getInt(
            AppWidgetManager.EXTRA_APPWIDGET_ID,
            AppWidgetManager.INVALID_APPWIDGET_ID
        ) ?: AppWidgetManager.INVALID_APPWIDGET_ID

        // If invalid widget ID, finish immediately
        if (appWidgetId == AppWidgetManager.INVALID_APPWIDGET_ID) {
            finish()
            return
        }

        prefs = WidgetPreferences(this, appWidgetId)

        // Initialize views
        previewContainer = findViewById(R.id.preview_container)
        previewText = findViewById(R.id.preview_text)
        showHashaahSwitch = findViewById(R.id.show_hashaah_switch)
        showDiacriticsSwitch = findViewById(R.id.show_diacritics_switch)
        showTimeOfDaySwitch = findViewById(R.id.show_time_of_day_switch)
        useSpecialQuartersSwitch = findViewById(R.id.use_special_quarters_switch)
        saveButton = findViewById(R.id.save_button)
        cancelButton = findViewById(R.id.cancel_button)

        // Load saved preferences
        loadPreferences()

        setupSwitches()
        setupButtons()

        // Update preview with current time
        updatePreview()
    }

    private fun loadPreferences() {
        showHashaahSwitch.isChecked = prefs.getShowHashaah()
        showDiacriticsSwitch.isChecked = prefs.getShowDiacritics()
        showTimeOfDaySwitch.isChecked = prefs.getShowTimeOfDay()
        useSpecialQuartersSwitch.isChecked = prefs.getUseSpecialQuarters()
    }

    private fun setupSwitches() {
        val updateListener = { _: Any -> updatePreview() }

        showHashaahSwitch.setOnCheckedChangeListener { _, _ -> updateListener(Unit) }
        showDiacriticsSwitch.setOnCheckedChangeListener { _, _ -> updateListener(Unit) }
        showTimeOfDaySwitch.setOnCheckedChangeListener { _, _ -> updateListener(Unit) }
        useSpecialQuartersSwitch.setOnCheckedChangeListener { _, _ -> updateListener(Unit) }
    }

    private fun setupButtons() {
        saveButton.setOnClickListener {
            saveConfiguration()
        }

        cancelButton.setOnClickListener {
            finish()
        }
    }

    private fun updatePreview() {
        // Get current time
        val calendar = Calendar.getInstance()
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE)

        // Get format options from switches
        val options = HebrewTimeConverter.TimeFormatOptions(
            showHashaah = showHashaahSwitch.isChecked,
            showDiacritics = showDiacriticsSwitch.isChecked,
            showTimeOfDay = showTimeOfDaySwitch.isChecked,
            useSpecialQuarters = useSpecialQuartersSwitch.isChecked
        )

        // Convert time with options
        val hebrewTime = HebrewTimeConverter.convertTime(hour, minute, options)
        previewText.text = hebrewTime

        // Get colors based on system dark mode
        val colors = ThemeManager.getThemeColors(this)

        // Apply colors to preview
        previewText.setTextColor(colors.textColor)
        previewContainer.setBackgroundColor(colors.backgroundColor)
    }

    private fun saveConfiguration() {
        // Save preferences
        prefs.setShowHashaah(showHashaahSwitch.isChecked)
        prefs.setShowDiacritics(showDiacriticsSwitch.isChecked)
        prefs.setShowTimeOfDay(showTimeOfDaySwitch.isChecked)
        prefs.setUseSpecialQuarters(useSpecialQuartersSwitch.isChecked)

        // Update widget immediately
        val appWidgetManager = AppWidgetManager.getInstance(this)
        WordClockWidget.updateAppWidget(this, appWidgetManager, appWidgetId)

        // Set result and finish
        val resultValue = Intent().apply {
            putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId)
        }
        setResult(Activity.RESULT_OK, resultValue)
        finish()
    }
}
