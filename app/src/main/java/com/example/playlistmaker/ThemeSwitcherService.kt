package com.example.playlistmaker

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatDelegate

private const val THEMES_KEY = "themes_key"

class ThemeSwitcherService(
    private val sharedPrefs: SharedPreferences
) {

    fun switchTheme(darkThemeEnabled: Boolean) {
        val darkTheme = darkThemeEnabled //сохраняет текущее состояние темы в переменную класса
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

    fun getDarkThemeEnabled(): Boolean {
        return sharedPrefs.getBoolean(
            THEMES_KEY,
            false
        ) //при запуске приложения в поле darkTheme будет значение false, если на устройстве включена светлая тема, и true, если тёмная.
    }
}