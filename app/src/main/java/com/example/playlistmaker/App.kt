package com.example.playlistmaker

import android.app.Application
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatDelegate

const val SHARED_PREFERENCES = "practicum_example_preferences"
const val THEMES_KEY = "key_for_edit_text"

class App : Application() {

    var darkTheme = false
    lateinit var sharedPrefs: SharedPreferences
    override fun onCreate() {
        super.onCreate()

        sharedPrefs = getSharedPreferences(SHARED_PREFERENCES, MODE_PRIVATE)
        darkTheme = sharedPrefs.getBoolean(THEMES_KEY, false) //при запуске приложения в поле darkTheme будет значение false, если на устройстве включена светлая тема, и true, если тёмная.
        switchTheme(darkTheme)
    }

    fun switchTheme(darkThemeEnabled: Boolean) {
        darkTheme = darkThemeEnabled //сохраняет текущее состояние темы в переменную класса
        AppCompatDelegate.setDefaultNightMode(
            if (darkThemeEnabled) {
                AppCompatDelegate.MODE_NIGHT_YES
            } else {
                AppCompatDelegate.MODE_NIGHT_NO
            }
        )
        sharedPrefs.edit()
            .putBoolean(THEMES_KEY, darkTheme)
            .apply()
    }
}