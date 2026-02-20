package com.jbworks.wordclock.time

import org.junit.Test

/**
 * Test that prints all Hebrew time strings for visual inspection.
 *
 * This test generates output for all 1440 minutes in a day (00:00 to 23:59)
 * with all formatting options enabled.
 */
class HebrewTimeConverterPrintTest {

    @Test
    fun `print all times with all options enabled`() {
        val options = HebrewTimeConverter.TimeFormatOptions(
            showHashaah = true,
            showTimeOfDay = true
        )

        val outputFile = java.io.File("/tmp/hebrew_times_full.txt")
        val writer = outputFile.bufferedWriter()

        writer.write("=" * 80)
        writer.newLine()
        writer.write("Hebrew Time Converter - All Times (All Options Enabled)")
        writer.newLine()
        writer.write("=" * 80)
        writer.newLine()
        writer.newLine()

        for (hour in 0..23) {
            writer.write("--- Hour: ${hour.toString().padStart(2, '0')}:xx ---")
            writer.newLine()
            writer.newLine()

            for (minute in 0..59) {
                val timeString = HebrewTimeConverter.convertTime(hour, minute, options)
                val timeLabel = "${hour.toString().padStart(2, '0')}:${minute.toString().padStart(2, '0')}"
                writer.write("$timeLabel → $timeString")
                writer.newLine()
            }

            writer.newLine()
        }

        writer.write("=" * 80)
        writer.newLine()
        writer.write("Total times printed: ${24 * 60} (1440 minutes)")
        writer.newLine()
        writer.write("=" * 80)
        writer.newLine()

        writer.close()

        println("Output written to: ${outputFile.absolutePath}")
        println("View with: cat ${outputFile.absolutePath}")
    }

    @Test
    fun `print special times only`() {
        val options = HebrewTimeConverter.TimeFormatOptions(
            showHashaah = true,
            showTimeOfDay = true
        )

        println("=" * 80)
        println("Hebrew Time Converter - Special Times Only")
        println("=" * 80)
        println()

        val specialMinutes = listOf(0, 1, 2, 5, 10, 15, 30, 45, 50, 55)

        for (hour in 0..23) {
            println("--- Hour: ${hour.toString().padStart(2, '0')}:xx ---")

            for (minute in specialMinutes) {
                val timeString = HebrewTimeConverter.convertTime(hour, minute, options)
                val timeLabel = "${hour.toString().padStart(2, '0')}:${minute.toString().padStart(2, '0')}"
                println("$timeLabel → $timeString")
            }

            println()
        }

        println("=" * 80)
        println("Total special times printed: ${24 * specialMinutes.size}")
        println("=" * 80)
    }

    @Test
    fun `print comparison with and without options`() {
        val allEnabled = HebrewTimeConverter.TimeFormatOptions(
            showHashaah = true,
            showTimeOfDay = true
        )

        val allDisabled = HebrewTimeConverter.TimeFormatOptions(
            showHashaah = false,
            showTimeOfDay = false
        )

        println("=" * 80)
        println("Hebrew Time Converter - Options Comparison")
        println("=" * 80)
        println()

        val testTimes = listOf(
            0 to 0,   // Midnight
            6 to 0,   // Morning
            6 to 5,   // Five past morning
            7 to 10,  // Ten past morning
            8 to 15,  // Quarter past morning
            9 to 30,  // Half past morning
            10 to 45, // Quarter to
            11 to 50, // Ten to
            11 to 55, // Five to
            12 to 0,  // Noon
            14 to 0,  // Afternoon
            15 to 15, // Afternoon quarter
            18 to 30, // Evening half
            22 to 0,  // Night
            23 to 59  // Last minute
        )

        for ((hour, minute) in testTimes) {
            val timeLabel = "${hour.toString().padStart(2, '0')}:${minute.toString().padStart(2, '0')}"
            val fullVersion = HebrewTimeConverter.convertTime(hour, minute, allEnabled)
            val minimalVersion = HebrewTimeConverter.convertTime(hour, minute, allDisabled)

            println("$timeLabel")
            println("  All enabled:  $fullVersion")
            println("  All disabled: $minimalVersion")
            println()
        }

        println("=" * 80)
    }

    private operator fun String.times(n: Int): String = this.repeat(n)
}
