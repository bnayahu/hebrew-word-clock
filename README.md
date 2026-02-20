# Hebrew Word Clock - שעון מילים עברי

An Android home screen widget that displays the current time as flowing Hebrew text.

## Features

- **Hebrew Time Display**: Shows time in natural Hebrew language with full diacritics (nikud) (e.g., "הַשָּׁעָה שְׁתַּיִם וּשְׁלוֹשִׁים וּשְׁתַּיִם דַּקּוֹת")
- **Natural Hebrew Expressions**: Uses special time forms (וָחֲמִשָּׁה, וָעֶשְׂרָה, וָרֶבַע, וָחֵצִי, רֶבַע לְ, עֲשָׂרָה לְ, חֲמִשָּׁה לְ for :05, :10, :15, :30, :45, :50, :55)
- **Minute Precision**: Updates every minute with all 60 possible minute values
- **Material You Integration**: Automatic color themes that adapt to your Android 12+ system theme (light/dark mode)
- **Auto-Scaling Text**: Font size automatically adjusts to fit the widget dimensions
- **Compact Layout**: Optimized for 1 row × 4 columns (minimum size)
- **Customizable Display Options**:
  - Toggle "השעה" prefix (show/hide "The hour is...")
  - Add time-of-day context (בַּבֹּקֶר, בַּצָּהֳרַיִם, אַחַר־הַצָּהֳרַיִם, בָּעֶרֶב, בַּלַּיְלָה)
- **Battery Efficient**: Uses hybrid update mechanism (AlarmManager + WorkManager)
- **RTL Support**: Proper right-to-left layout for Hebrew text

## Requirements

- Android 12+ (API 31+)
- Supports Android 12, 13, and 14

## Installation

1. Clone the repository
2. Open the project in Android Studio
3. Build and run on your device or emulator
4. Long-press on your home screen → Widgets → Hebrew Word Clock
5. Configure your preferred display options

## Configuration Options

When adding the widget, you can customize the following settings:

- **Show 'השעה'**: Display the word "הַשָּׁעָה" (the hour) at the beginning of the time
- **Time of Day**: Add contextual suffixes based on the hour:
  - 6:00-11:59 → "בַּבֹּקֶר" (in the morning)
  - 12:00-13:59 → "בַּצָּהֳרַיִם" (at noon)
  - 14:00-17:59 → "אַחַר־הַצָּהֳרַיִם" (in the afternoon)
  - 18:00-20:59 → "בָּעֶרֶב" (in the evening)
  - 21:00-5:59 → "בַּלַּיְלָה" (at night)

All settings have a live preview that updates in real-time.

### Built-in Features (Always Enabled)

- **Diacritics (Nikud)**: Full Hebrew vowel points for accurate pronunciation
- **Special Time Forms**: Natural Hebrew expressions for common times:
  - 5 minutes past → "וָחֲמִשָּׁה" (and five)
  - 10 minutes past → "וָעֶשְׂרָה" (and ten)
  - 15 minutes past → "וָרֶבַע" (and a quarter)
  - 30 minutes past → "וָחֵצִי" (and a half)
  - 45 minutes past → "רֶבַע לְ" (quarter to)
  - 50 minutes past → "עֲשָׂרָה לְ" (ten to)
  - 55 minutes past → "חֲמִשָּׁה לְ" (five to)

## Technical Details

### Architecture

- **HebrewTimeConverter**: Core logic for converting numeric time to Hebrew text with formatting options
- **WordClockWidget**: AppWidgetProvider managing widget lifecycle
- **WidgetUpdateScheduler**: Hybrid update mechanism (AlarmManager + WorkManager)
- **WidgetConfigActivity**: Configuration UI with live preview and Material Design switches
- **ThemeManager**: Automatic Material You dynamic color support based on system dark mode
- **WidgetPreferences**: Per-widget settings storage using SharedPreferences

### Update Mechanism

The widget uses a hybrid approach for updates:
- **Primary**: AlarmManager with exact timing at minute boundaries
- **Backup**: WorkManager periodic updates every 15 minutes
- **Boot Recovery**: Reschedules updates after device reboot

### Hebrew Number System

- Hours use feminine forms (אַחַת, שְׁתַּיִם, שָׁלשׁ...)
- Special cases for exact hours, quarter past, half past, and quarter to
- Singular, dual, and plural forms handled correctly
- 12-hour format with AM/PM conversion
- Full diacritics (nikud) for accurate pronunciation
- Optional time-of-day context for clarity

## Testing

Run unit tests:
```bash
./gradlew test
```

Tests cover:
- All 1440 time combinations (24 hours × 60 minutes)
- Edge cases and invalid inputs
- All formatting options (with/without prefix and time-of-day suffix)
- Special time forms (quarters, halves, and special minute values)

## License

MIT License - Feel free to use and modify.

## Author

JBWorks - 2026
