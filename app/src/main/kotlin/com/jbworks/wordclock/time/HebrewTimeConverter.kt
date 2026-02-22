package com.jbworks.wordclock.time

/**
 * Converts numeric time (hour, minute) to flowing Hebrew text with nikud (diacritics).
 *
 * Examples:
 * - 14:00 → "הַשָּׁעָה שְׁתַּיִם"
 * - 14:15 → "הַשָּׁעָה שְׁתַּיִם וָרֶבַע"
 * - 14:32 → "הַשָּׁעָה שְׁתַּיִם וּשְׁלֹשִׁים וּשְׁתַּיִם דַּקּוֹת"
 */
object HebrewTimeConverter {

    data class TimeFormatOptions(
        val showHashaah: Boolean = false,
        val showTimeOfDay: Boolean = false
    )

    // Hours use feminine forms in Hebrew
    private val hebrewHours = mapOf(
        1 to "אַחַת",
        2 to "שְׁתַּיִם",
        3 to "שָׁלוֹשׁ",
        4 to "אַרְבַּע",
        5 to "חָמֵשׁ",
        6 to "שֵׁשׁ",
        7 to "שֶׁבַע",
        8 to "שְׁמוֹנֶה",
        9 to "תֵּשַׁע",
        10 to "עֶשֶׂר",
        11 to "אַחַת־עֶשְׂרֵה",
        12 to "שְׁתֵּים־עֶשְׂרֵה"
    )

    // Basic numbers 1-19 for minutes (absolute forms)
    private val hebrewOnes = mapOf(
        1 to "אַחַת",
        2 to "שְׁתַּיִם",
        3 to "שָׁלוֹשׁ",
        4 to "אַרְבַּע",
        5 to "חָמֵשׁ",
        6 to "שֵׁשׁ",
        7 to "שֶׁבַע",
        8 to "שְׁמוֹנֶה",
        9 to "תֵּשַׁע"
    )

    // Numbers with vav prefix (for use in construct with vav conjunctive)
    private val hebrewOnesWithVav = mapOf(
        1 to "וְאַחַת",
        2 to "וּשְׁתַּיִם",
        3 to "וְשָׁלוֹשׁ",
        4 to "וְאַרְבַּע",
        5 to "וְחָמֵשׁ",
        6 to "וְשֵׁשׁ",
        7 to "וְשֶׁבַע",
        8 to "וּשְׁמוֹנֶה",
        9 to "וְתֵשַׁע"
    )

    private val hebrewTeens = mapOf(
        10 to "עֶשֶׂר",
        11 to "אַחַת־עֶשְׂרֵה",
        12 to "שְׁתֵּים־עֶשְׂרֵה",
        13 to "שְׁלוֹשׁ־עֶשְׂרֵה",
        14 to "אַרְבַּע־עֶשְׂרֵה",
        15 to "חֲמֵשׁ־עֶשְׂרֵה",
        16 to "שֵׁשׁ־עֶשְׂרֵה",
        17 to "שְׁבַע־עֶשְׂרֵה",
        18 to "שְׁמוֹנֶה־עֶשְׂרֵה",
        19 to "תְּשַׁע־עֶשְׂרֵה"
    )

    // Teens with vav prefix (for use in construct)
    private val hebrewTeensWithVav = mapOf(
        10 to "וְעֶשֶׂר",
        11 to "וְאַחַת־עֶשְׂרֵה",
        12 to "וּשְׁתַּיִם־עֶשְׂרֵה",
        13 to "וּשְׁלוֹשׁ־עֶשְׂרֵה",
        14 to "וְאַרְבַּע־עֶשְׂרֵה",
        15 to "וְחָמֵשׁ־עֶשְׂרֵה",
        16 to "וְשֵׁשׁ־עֶשְׂרֵה",
        17 to "וּשְׁבַע־עֶשְׂרֵה",
        18 to "וּשְׁמוֹנֶה־עֶשְׂרֵה",
        19 to "וּתְשַׁע־עֶשְׂרֵה"
    )

    private val hebrewTens = mapOf(
        20 to "עֶשְׂרִים",
        30 to "שְׁלוֹשִׁים",
        40 to "אַרְבָּעִים",
        50 to "חֲמִשִּׁים"
    )

    // Tens with vav prefix (for use in construct)
    private val hebrewTensWithVav = mapOf(
        20 to "וְעֶשְׂרִים",
        30 to "וּשְׁלוֹשִׁים",
        40 to "וְאַרְבָּעִים",
        50 to "וַחֲמִשִּׁים"
    )

    /**
     * Converts time to Hebrew text.
     *
     * @param hour Hour in 24-hour format (0-23)
     * @param minute Minute (0-59)
     * @param options Formatting options
     * @return Hebrew text representation of the time
     */
    fun convertTime(hour: Int, minute: Int, options: TimeFormatOptions = TimeFormatOptions()): String {
        require(hour in 0..23) { "Hour must be between 0 and 23" }
        require(minute in 0..59) { "Minute must be between 0 and 59" }

        // Convert to 12-hour format
        val hour12 = when (hour) {
            0 -> 12
            in 1..12 -> hour
            else -> hour - 12
        }

        val hourText = hebrewHours[hour12]!!
        val prefix = if (options.showHashaah) "הַשָּׁעָה " else ""

        val timeText = when (minute) {
            0 -> "${prefix}${hourText}"
            1 -> "${prefix}${hourText} וְדַקָּה אַחַת"
            2 -> "${prefix}${hourText} וּשְׁתֵּי דַּקּוֹת"
            5 -> "${prefix}${hourText} וָחֲמִשָּׁה"
            10 -> "${prefix}${hourText} וַעֲשָׂרָה"
            15 -> "${prefix}${hourText} וָרֶבַע"
            20 -> "${prefix}${hourText} ${convertMinutesWithVav(minute)}"
            30 -> "${prefix}${hourText} וָחֵצִי"
            40 -> "${prefix}${hourText} ${convertMinutesWithVav(minute)}"
            45 -> {
                val nextHour = if (hour12 == 12) 1 else hour12 + 1
                val lamed = if (nextHour == 2 || nextHour == 8 || nextHour == 12) "לִ" else "לְ"
                "רֶבַע ${lamed}${hebrewHours[nextHour]}"
            }
            50 -> {
                val nextHour = if (hour12 == 12) 1 else hour12 + 1
                val lamed = if (nextHour == 2 || nextHour == 8 || nextHour == 12) "לִ" else "לְ"
                "עֲשָׂרָה ${lamed}${hebrewHours[nextHour]}"
            }
            55 -> {
                val nextHour = if (hour12 == 12) 1 else hour12 + 1
                val lamed = if (nextHour == 2 || nextHour == 8 || nextHour == 12) "לִ" else "לְ"
                "חֲמִשָּׁה ${lamed}${hebrewHours[nextHour]}"
            }
            else -> "${prefix}${hourText} ${convertMinutesWithVav(minute)} דַּקּוֹת"
        }

        return if (options.showTimeOfDay) {
            val timeOfDaySuffix = getTimeOfDaySuffix(hour)
            "$timeText $timeOfDaySuffix"
        } else {
            timeText
        }
    }

    /**
     * Gets the time of day suffix based on the hour.
     */
    private fun getTimeOfDaySuffix(hour: Int): String {
        return when (hour) {
            in 6..11 -> "בַּבֹּקֶר"
            in 12..13 -> "בַּצָּהֳרַיִם"
            in 14..17 -> "אַחַר־הַצָּהֳרַיִם"
            in 18..20 -> "בָּעֶרֶב"
            else -> "בַּלַּיְלָה"
        }
    }

    /**
     * Converts a minute value (3-59, excluding special cases) to Hebrew text with vav prefix.
     *
     * @param minute Minute value (3-59)
     * @return Hebrew text for the minute value with vav prefix
     */
    private fun convertMinutesWithVav(minute: Int): String {
        return when (minute) {
            in 3..9 -> hebrewOnesWithVav[minute]!!
            in 10..19 -> hebrewTeensWithVav[minute]!!
            in 20..59 -> {
                val tens = (minute / 10) * 10
                val ones = minute % 10
                if (ones == 0) {
                    hebrewTensWithVav[tens]!!
                } else {
                    "${hebrewTensWithVav[tens]} ${hebrewOnesWithVav[ones]}"
                }
            }
            else -> throw IllegalArgumentException("Unexpected minute value: $minute")
        }
    }

}
