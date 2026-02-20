# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.kts.

# Keep widget provider
-keep class com.jbworks.wordclock.widget.WordClockWidget { *; }

# Keep time converter
-keep class com.jbworks.wordclock.time.HebrewTimeConverter { *; }

# Keep configuration activity
-keep class com.jbworks.wordclock.config.WidgetConfigActivity { *; }

# Keep data classes used for configuration
-keep class com.jbworks.wordclock.time.HebrewTimeConverter$TimeFormatOptions { *; }

# Keep WorkManager worker
-keep class com.jbworks.wordclock.widget.WidgetUpdateWorker { *; }

# Keep broadcast receivers
-keep class com.jbworks.wordclock.widget.WidgetUpdateReceiver { *; }

# AndroidX and Material Components
-keep class com.google.android.material.** { *; }
-dontwarn com.google.android.material.**
-keep class androidx.** { *; }
-dontwarn androidx.**

# Keep WorkManager
-keep class * extends androidx.work.Worker
-keep class * extends androidx.work.ListenableWorker { *; }

# Kotlin
-keep class kotlin.** { *; }
-keep class kotlinx.** { *; }
-dontwarn kotlin.**
-dontwarn kotlinx.**
