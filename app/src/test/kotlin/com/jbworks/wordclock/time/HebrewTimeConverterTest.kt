package com.jbworks.wordclock.time

import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class HebrewTimeConverterTest {

    companion object {
        // Options with showHashaah enabled for most tests
        private val withHashaah = HebrewTimeConverter.TimeFormatOptions(showHashaah = true)
    }

    @Test
    fun `test exact hours - minute 0`() {
        assertEquals("הַשָּׁעָה אַחַת", HebrewTimeConverter.convertTime(1, 0, withHashaah))
        assertEquals("הַשָּׁעָה שְׁתַּיִם", HebrewTimeConverter.convertTime(2, 0, withHashaah))
        assertEquals("הַשָּׁעָה שָׁלוֹשׁ", HebrewTimeConverter.convertTime(3, 0, withHashaah))
        assertEquals("הַשָּׁעָה אַרְבַּע", HebrewTimeConverter.convertTime(4, 0, withHashaah))
        assertEquals("הַשָּׁעָה חָמֵשׁ", HebrewTimeConverter.convertTime(5, 0, withHashaah))
        assertEquals("הַשָּׁעָה שֵׁשׁ", HebrewTimeConverter.convertTime(6, 0, withHashaah))
        assertEquals("הַשָּׁעָה שֶׁבַע", HebrewTimeConverter.convertTime(7, 0, withHashaah))
        assertEquals("הַשָּׁעָה שְׁמוֹנֶה", HebrewTimeConverter.convertTime(8, 0, withHashaah))
        assertEquals("הַשָּׁעָה תֵּשַׁע", HebrewTimeConverter.convertTime(9, 0, withHashaah))
        assertEquals("הַשָּׁעָה עֶשֶׂר", HebrewTimeConverter.convertTime(10, 0, withHashaah))
        assertEquals("הַשָּׁעָה אַחַת־עֶשְׂרֵה", HebrewTimeConverter.convertTime(11, 0, withHashaah))
        assertEquals("הַשָּׁעָה שְׁתֵּים־עֶשְׂרֵה", HebrewTimeConverter.convertTime(12, 0, withHashaah))
    }

    @Test
    fun `test midnight and noon`() {
        // Midnight (00:00) should show as 12
        assertEquals("הַשָּׁעָה שְׁתֵּים־עֶשְׂרֵה", HebrewTimeConverter.convertTime(0, 0, withHashaah))
        // Noon (12:00)
        assertEquals("הַשָּׁעָה שְׁתֵּים־עֶשְׂרֵה", HebrewTimeConverter.convertTime(12, 0, withHashaah))
    }

    @Test
    fun `test PM hours convert to 12-hour format`() {
        assertEquals("הַשָּׁעָה אַחַת", HebrewTimeConverter.convertTime(13, 0, withHashaah))
        assertEquals("הַשָּׁעָה שְׁתַּיִם", HebrewTimeConverter.convertTime(14, 0, withHashaah))
        assertEquals("הַשָּׁעָה שָׁלוֹשׁ", HebrewTimeConverter.convertTime(15, 0, withHashaah))
        assertEquals("הַשָּׁעָה עֶשֶׂר", HebrewTimeConverter.convertTime(22, 0, withHashaah))
        assertEquals("הַשָּׁעָה אַחַת־עֶשְׂרֵה", HebrewTimeConverter.convertTime(23, 0, withHashaah))
    }

    @Test
    fun `test one minute - singular form`() {
        assertEquals("הַשָּׁעָה אַחַת וְדַקָּה אַחַת", HebrewTimeConverter.convertTime(1, 1, withHashaah))
        assertEquals("הַשָּׁעָה שְׁתַּיִם וְדַקָּה אַחַת", HebrewTimeConverter.convertTime(14, 1, withHashaah))
        assertEquals("הַשָּׁעָה שְׁתֵּים־עֶשְׂרֵה וְדַקָּה אַחַת", HebrewTimeConverter.convertTime(12, 1, withHashaah))
    }

    @Test
    fun `test two minutes - dual form`() {
        assertEquals("הַשָּׁעָה אַחַת וּשְׁתֵּי דַּקּוֹת", HebrewTimeConverter.convertTime(1, 2, withHashaah))
        assertEquals("הַשָּׁעָה שָׁלוֹשׁ וּשְׁתֵּי דַּקּוֹת", HebrewTimeConverter.convertTime(3, 2, withHashaah))
        assertEquals("הַשָּׁעָה עֶשֶׂר וּשְׁתֵּי דַּקּוֹת", HebrewTimeConverter.convertTime(22, 2, withHashaah))
    }

    @Test
    fun `test ten past - minute 10`() {
        assertEquals("הַשָּׁעָה אַחַת וַעֲשָׂרָה", HebrewTimeConverter.convertTime(1, 10, withHashaah))
        assertEquals("הַשָּׁעָה שֵׁשׁ וַעֲשָׂרָה", HebrewTimeConverter.convertTime(6, 10, withHashaah))
        assertEquals("הַשָּׁעָה שְׁתֵּים־עֶשְׂרֵה וַעֲשָׂרָה", HebrewTimeConverter.convertTime(12, 10, withHashaah))
        assertEquals("הַשָּׁעָה תֵּשַׁע וַעֲשָׂרָה", HebrewTimeConverter.convertTime(21, 10, withHashaah))
    }

    @Test
    fun `test quarter past - minute 15`() {
        assertEquals("הַשָּׁעָה אַחַת וָרֶבַע", HebrewTimeConverter.convertTime(1, 15, withHashaah))
        assertEquals("הַשָּׁעָה שֵׁשׁ וָרֶבַע", HebrewTimeConverter.convertTime(6, 15, withHashaah))
        assertEquals("הַשָּׁעָה שְׁתֵּים־עֶשְׂרֵה וָרֶבַע", HebrewTimeConverter.convertTime(12, 15, withHashaah))
        assertEquals("הַשָּׁעָה תֵּשַׁע וָרֶבַע", HebrewTimeConverter.convertTime(21, 15, withHashaah))
    }

    @Test
    fun `test half past - minute 30`() {
        assertEquals("הַשָּׁעָה אַחַת וָחֵצִי", HebrewTimeConverter.convertTime(1, 30, withHashaah))
        assertEquals("הַשָּׁעָה אַרְבַּע וָחֵצִי", HebrewTimeConverter.convertTime(4, 30, withHashaah))
        assertEquals("הַשָּׁעָה שְׁתֵּים־עֶשְׂרֵה וָחֵצִי", HebrewTimeConverter.convertTime(12, 30, withHashaah))
        assertEquals("הַשָּׁעָה שֶׁבַע וָחֵצִי", HebrewTimeConverter.convertTime(19, 30, withHashaah))
    }

    @Test
    fun `test quarter to - minute 45`() {
        assertEquals("רֶבַע לִשְׁתַּיִם", HebrewTimeConverter.convertTime(1, 45))
        assertEquals("רֶבַע לְשָׁלוֹשׁ", HebrewTimeConverter.convertTime(2, 45))
        assertEquals("רֶבַע לִשְׁמוֹנֶה", HebrewTimeConverter.convertTime(7, 45))
        assertEquals("רֶבַע לִשְׁתֵּים־עֶשְׂרֵה", HebrewTimeConverter.convertTime(11, 45))

        // 12:45 should wrap to "quarter to one"
        assertEquals("רֶבַע לְאַחַת", HebrewTimeConverter.convertTime(12, 45))
        assertEquals("רֶבַע לְאַחַת", HebrewTimeConverter.convertTime(0, 45))

        // PM hours
        assertEquals("רֶבַע לְשֶׁבַע", HebrewTimeConverter.convertTime(18, 45))
    }

    @Test
    fun `test ten to - minute 50`() {
        assertEquals("עֲשָׂרָה לִשְׁתַּיִם", HebrewTimeConverter.convertTime(1, 50))
        assertEquals("עֲשָׂרָה לְשָׁלוֹשׁ", HebrewTimeConverter.convertTime(2, 50))
        assertEquals("עֲשָׂרָה לִשְׁמוֹנֶה", HebrewTimeConverter.convertTime(7, 50))
        assertEquals("עֲשָׂרָה לִשְׁתֵּים־עֶשְׂרֵה", HebrewTimeConverter.convertTime(11, 50))

        // 12:50 should wrap to "ten to one"
        assertEquals("עֲשָׂרָה לְאַחַת", HebrewTimeConverter.convertTime(12, 50))
        assertEquals("עֲשָׂרָה לְאַחַת", HebrewTimeConverter.convertTime(0, 50))

        // PM hours
        assertEquals("עֲשָׂרָה לְשֶׁבַע", HebrewTimeConverter.convertTime(18, 50))
    }

    @Test
    fun `test five to - minute 55`() {
        assertEquals("חֲמִשָּׁה לִשְׁתַּיִם", HebrewTimeConverter.convertTime(1, 55))
        assertEquals("חֲמִשָּׁה לְשָׁלוֹשׁ", HebrewTimeConverter.convertTime(2, 55))
        assertEquals("חֲמִשָּׁה לִשְׁמוֹנֶה", HebrewTimeConverter.convertTime(7, 55))
        assertEquals("חֲמִשָּׁה לִשְׁתֵּים־עֶשְׂרֵה", HebrewTimeConverter.convertTime(11, 55))

        // 12:55 should wrap to "five to one"
        assertEquals("חֲמִשָּׁה לְאַחַת", HebrewTimeConverter.convertTime(12, 55))
        assertEquals("חֲמִשָּׁה לְאַחַת", HebrewTimeConverter.convertTime(0, 55))

        // PM hours
        assertEquals("חֲמִשָּׁה לְשֶׁבַע", HebrewTimeConverter.convertTime(18, 55))
    }

    @Test
    fun `test five past - minute 5`() {
        assertEquals("הַשָּׁעָה אַחַת וָחֲמִשָּׁה", HebrewTimeConverter.convertTime(1, 5, withHashaah))
        assertEquals("הַשָּׁעָה שֵׁשׁ וָחֲמִשָּׁה", HebrewTimeConverter.convertTime(6, 5, withHashaah))
        assertEquals("הַשָּׁעָה שְׁתֵּים־עֶשְׂרֵה וָחֲמִשָּׁה", HebrewTimeConverter.convertTime(12, 5, withHashaah))
        assertEquals("הַשָּׁעָה תֵּשַׁע וָחֲמִשָּׁה", HebrewTimeConverter.convertTime(21, 5, withHashaah))
    }

    @Test
    fun `test minutes 3-9`() {
        assertEquals("הַשָּׁעָה אַחַת וְשָׁלוֹשׁ דַּקּוֹת", HebrewTimeConverter.convertTime(1, 3, withHashaah))
        assertEquals("הַשָּׁעָה שְׁתַּיִם וְאַרְבַּע דַּקּוֹת", HebrewTimeConverter.convertTime(2, 4, withHashaah))
        // 5 is special case (five past)
        assertEquals("הַשָּׁעָה אַרְבַּע וְשֵׁשׁ דַּקּוֹת", HebrewTimeConverter.convertTime(4, 6, withHashaah))
        assertEquals("הַשָּׁעָה חָמֵשׁ וְשֶׁבַע דַּקּוֹת", HebrewTimeConverter.convertTime(5, 7, withHashaah))
        assertEquals("הַשָּׁעָה שֵׁשׁ וּשְׁמוֹנֶה דַּקּוֹת", HebrewTimeConverter.convertTime(6, 8, withHashaah))
        assertEquals("הַשָּׁעָה שֶׁבַע וְתֵשַׁע דַּקּוֹת", HebrewTimeConverter.convertTime(7, 9, withHashaah))
    }

    @Test
    fun `test minutes 10-19`() {
        // 10 is special case (ten past)
        assertEquals("הַשָּׁעָה שְׁתַּיִם וְאַחַת־עֶשְׂרֵה דַּקּוֹת", HebrewTimeConverter.convertTime(2, 11, withHashaah))
        assertEquals("הַשָּׁעָה שָׁלוֹשׁ וּשְׁתַּיִם־עֶשְׂרֵה דַּקּוֹת", HebrewTimeConverter.convertTime(3, 12, withHashaah))
        assertEquals("הַשָּׁעָה אַרְבַּע וּשְׁלוֹשׁ־עֶשְׂרֵה דַּקּוֹת", HebrewTimeConverter.convertTime(4, 13, withHashaah))
        assertEquals("הַשָּׁעָה חָמֵשׁ וְאַרְבַּע־עֶשְׂרֵה דַּקּוֹת", HebrewTimeConverter.convertTime(5, 14, withHashaah))
        // 15 is special case (quarter)
        assertEquals("הַשָּׁעָה שֵׁשׁ וְשֵׁשׁ־עֶשְׂרֵה דַּקּוֹת", HebrewTimeConverter.convertTime(6, 16, withHashaah))
        assertEquals("הַשָּׁעָה שֶׁבַע וּשְׁבַע־עֶשְׂרֵה דַּקּוֹת", HebrewTimeConverter.convertTime(7, 17, withHashaah))
        assertEquals("הַשָּׁעָה שְׁמוֹנֶה וּשְׁמוֹנֶה־עֶשְׂרֵה דַּקּוֹת", HebrewTimeConverter.convertTime(8, 18, withHashaah))
        assertEquals("הַשָּׁעָה תֵּשַׁע וּתְשַׁע־עֶשְׂרֵה דַּקּוֹת", HebrewTimeConverter.convertTime(9, 19, withHashaah))
    }

    @Test
    fun `test even tens - 20, 30, 40, 50`() {
        assertEquals("הַשָּׁעָה אַחַת וְעֶשְׂרִים", HebrewTimeConverter.convertTime(1, 20, withHashaah))
        // 30 is special case (half)
        assertEquals("הַשָּׁעָה שְׁתַּיִם וְאַרְבָּעִים", HebrewTimeConverter.convertTime(2, 40, withHashaah))
        // 50 is special case (ten to)
    }

    @Test
    fun `test compound minutes - tens plus ones`() {
        // 20s
        assertEquals("הַשָּׁעָה אַחַת וְעֶשְׂרִים וְאַחַת דַּקּוֹת", HebrewTimeConverter.convertTime(1, 21, withHashaah))
        assertEquals("הַשָּׁעָה שְׁתַּיִם וְעֶשְׂרִים וּשְׁתַּיִם דַּקּוֹת", HebrewTimeConverter.convertTime(2, 22, withHashaah))
        assertEquals("הַשָּׁעָה שָׁלוֹשׁ וְעֶשְׂרִים וְתֵשַׁע דַּקּוֹת", HebrewTimeConverter.convertTime(3, 29, withHashaah))

        // 30s
        assertEquals("הַשָּׁעָה אַרְבַּע וּשְׁלוֹשִׁים וְאַחַת דַּקּוֹת", HebrewTimeConverter.convertTime(4, 31, withHashaah))
        assertEquals("הַשָּׁעָה חָמֵשׁ וּשְׁלוֹשִׁים וְחָמֵשׁ דַּקּוֹת", HebrewTimeConverter.convertTime(5, 35, withHashaah))
        assertEquals("הַשָּׁעָה שֵׁשׁ וּשְׁלוֹשִׁים וְתֵשַׁע דַּקּוֹת", HebrewTimeConverter.convertTime(6, 39, withHashaah))

        // 40s
        assertEquals("הַשָּׁעָה שֶׁבַע וְאַרְבָּעִים וְאַחַת דַּקּוֹת", HebrewTimeConverter.convertTime(7, 41, withHashaah))
        assertEquals("הַשָּׁעָה שְׁמוֹנֶה וְאַרְבָּעִים וְאַרְבַּע דַּקּוֹת", HebrewTimeConverter.convertTime(8, 44, withHashaah))
        // 45 is special case
        assertEquals("הַשָּׁעָה תֵּשַׁע וְאַרְבָּעִים וְשֵׁשׁ דַּקּוֹת", HebrewTimeConverter.convertTime(9, 46, withHashaah))

        // 50s (note: 50 and 55 are special cases)
        assertEquals("הַשָּׁעָה עֶשֶׂר וַחֲמִשִּׁים וְאַחַת דַּקּוֹת", HebrewTimeConverter.convertTime(10, 51, withHashaah))
        assertEquals("הַשָּׁעָה אַחַת־עֶשְׂרֵה וַחֲמִשִּׁים וְשָׁלוֹשׁ דַּקּוֹת", HebrewTimeConverter.convertTime(11, 53, withHashaah))
        assertEquals("הַשָּׁעָה שְׁתֵּים־עֶשְׂרֵה וַחֲמִשִּׁים וְתֵשַׁע דַּקּוֹת", HebrewTimeConverter.convertTime(12, 59, withHashaah))
    }

    @Test
    fun `test comprehensive coverage - sample times`() {
        // Morning
        assertEquals("הַשָּׁעָה שֵׁשׁ וְעֶשְׂרִים וְשָׁלוֹשׁ דַּקּוֹת", HebrewTimeConverter.convertTime(6, 23, withHashaah))
        assertEquals("הַשָּׁעָה שֶׁבַע וְאַרְבָּעִים וְשֶׁבַע דַּקּוֹת", HebrewTimeConverter.convertTime(7, 47, withHashaah))

        // Afternoon
        assertEquals("הַשָּׁעָה שְׁתַּיִם וּשְׁלוֹשׁ־עֶשְׂרֵה דַּקּוֹת", HebrewTimeConverter.convertTime(14, 13, withHashaah))
        assertEquals("הַשָּׁעָה שָׁלוֹשׁ וּשְׁלוֹשִׁים וְשֵׁשׁ דַּקּוֹת", HebrewTimeConverter.convertTime(15, 36, withHashaah))

        // Evening
        assertEquals("הַשָּׁעָה תֵּשַׁע וַחֲמִשִּׁים וּשְׁמוֹנֶה דַּקּוֹת", HebrewTimeConverter.convertTime(21, 58, withHashaah))
        assertEquals("הַשָּׁעָה עֶשֶׂר וְעֶשְׂרִים וְאַרְבַּע דַּקּוֹת", HebrewTimeConverter.convertTime(22, 24, withHashaah))
    }

    @Test
    fun `test all special minute cases across different hours`() {
        // Test special minutes (0, 1, 2, 5, 10, 15, 30, 45, 50, 55) with different hours
        for (hour in 0..23) {
            val hour12 = if (hour == 0) 12 else if (hour > 12) hour - 12 else hour
            val hourText = when (hour12) {
                1 -> "אַחַת"
                2 -> "שְׁתַּיִם"
                3 -> "שָׁלוֹשׁ"
                4 -> "אַרְבַּע"
                5 -> "חָמֵשׁ"
                6 -> "שֵׁשׁ"
                7 -> "שֶׁבַע"
                8 -> "שְׁמוֹנֶה"
                9 -> "תֵּשַׁע"
                10 -> "עֶשֶׂר"
                11 -> "אַחַת־עֶשְׂרֵה"
                12 -> "שְׁתֵּים־עֶשְׂרֵה"
                else -> ""
            }

            // Exact hour
            assertEquals("הַשָּׁעָה $hourText", HebrewTimeConverter.convertTime(hour, 0, withHashaah))

            // One minute
            assertEquals("הַשָּׁעָה $hourText וְדַקָּה אַחַת", HebrewTimeConverter.convertTime(hour, 1, withHashaah))

            // Two minutes
            assertEquals("הַשָּׁעָה $hourText וּשְׁתֵּי דַּקּוֹת", HebrewTimeConverter.convertTime(hour, 2, withHashaah))

            // Five past
            assertEquals("הַשָּׁעָה $hourText וָחֲמִשָּׁה", HebrewTimeConverter.convertTime(hour, 5, withHashaah))

            // Ten past
            assertEquals("הַשָּׁעָה $hourText וַעֲשָׂרָה", HebrewTimeConverter.convertTime(hour, 10, withHashaah))

            // Quarter past
            assertEquals("הַשָּׁעָה $hourText וָרֶבַע", HebrewTimeConverter.convertTime(hour, 15, withHashaah))

            // Half past
            assertEquals("הַשָּׁעָה $hourText וָחֵצִי", HebrewTimeConverter.convertTime(hour, 30, withHashaah))
        }
    }

    @Test
    fun `test invalid inputs`() {
        assertFailsWith<IllegalArgumentException> {
            HebrewTimeConverter.convertTime(-1, 0)
        }

        assertFailsWith<IllegalArgumentException> {
            HebrewTimeConverter.convertTime(24, 0)
        }

        assertFailsWith<IllegalArgumentException> {
            HebrewTimeConverter.convertTime(0, -1)
        }

        assertFailsWith<IllegalArgumentException> {
            HebrewTimeConverter.convertTime(0, 60)
        }
    }

    @Test
    fun `test edge case - 23 hours 59 minutes`() {
        assertEquals("הַשָּׁעָה אַחַת־עֶשְׂרֵה וַחֲמִשִּׁים וְתֵשַׁע דַּקּוֹת", HebrewTimeConverter.convertTime(23, 59, withHashaah))
    }

    @Test
    fun `test default options - no prefix`() {
        // Default should have both showHashaah and showTimeOfDay = false
        assertEquals("שְׁתַּיִם", HebrewTimeConverter.convertTime(14, 0))
        assertEquals("שָׁלוֹשׁ וָרֶבַע", HebrewTimeConverter.convertTime(15, 15))
        assertEquals("אַרְבַּע וָחֵצִי", HebrewTimeConverter.convertTime(16, 30))
    }

    @Test
    fun `test without hashaah prefix`() {
        val options = HebrewTimeConverter.TimeFormatOptions(showHashaah = false)
        assertEquals("שְׁתַּיִם", HebrewTimeConverter.convertTime(14, 0, options))
        assertEquals("שָׁלוֹשׁ וָרֶבַע", HebrewTimeConverter.convertTime(15, 15, options))
        assertEquals("אַרְבַּע וָחֵצִי", HebrewTimeConverter.convertTime(16, 30, options))
    }


    @Test
    fun `test with time of day suffix`() {
        val options = HebrewTimeConverter.TimeFormatOptions(showHashaah = true, showTimeOfDay = true)
        assertEquals("הַשָּׁעָה שֶׁבַע בַּבֹּקֶר", HebrewTimeConverter.convertTime(7, 0, options))
        assertEquals("הַשָּׁעָה שְׁתֵּים־עֶשְׂרֵה בַּצָּהֳרַיִם", HebrewTimeConverter.convertTime(12, 0, options))
        assertEquals("הַשָּׁעָה שָׁלוֹשׁ אַחַר־הַצָּהֳרַיִם", HebrewTimeConverter.convertTime(15, 0, options))
        assertEquals("הַשָּׁעָה שֶׁבַע בָּעֶרֶב", HebrewTimeConverter.convertTime(19, 0, options))
        assertEquals("הַשָּׁעָה עֶשֶׂר בַּלַּיְלָה", HebrewTimeConverter.convertTime(22, 0, options))
        assertEquals("הַשָּׁעָה שָׁלוֹשׁ בַּלַּיְלָה", HebrewTimeConverter.convertTime(3, 0, options))
    }

    @Test
    fun `test combined options`() {
        val options = HebrewTimeConverter.TimeFormatOptions(
            showHashaah = false,
            showTimeOfDay = true
        )
        assertEquals("שְׁתַּיִם אַחַר־הַצָּהֳרַיִם", HebrewTimeConverter.convertTime(14, 0, options))
        assertEquals("שָׁלוֹשׁ וָרֶבַע אַחַר־הַצָּהֳרַיִם", HebrewTimeConverter.convertTime(15, 15, options))
        assertEquals("אַרְבַּע וָחֵצִי אַחַר־הַצָּהֳרַיִם", HebrewTimeConverter.convertTime(16, 30, options))
    }
}
