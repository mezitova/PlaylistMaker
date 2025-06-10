package com.example.playlistmaker

import android.app.Application

private const val SHARED_PREFERENCES = "practicum_preferences"
//private const val THEMES_KEY = "themes_key"

class App : Application() {

//    var darkTheme = false
    lateinit var themeSwitcherService: ThemeSwitcherService

    override fun onCreate() {
        super.onCreate()

        val sharedPrefs = this.getSharedPreferences(SHARED_PREFERENCES, MODE_PRIVATE)//получ экземпляр SharedPreferences
        themeSwitcherService = ThemeSwitcherService(sharedPrefs)

        val darkThemeEnabled = themeSwitcherService.getDarkThemeEnabled()
        themeSwitcherService.switchTheme(darkThemeEnabled)
//        val darkTheme = sharedPrefs.getBoolean(THEMES_KEY, false) //при запуске приложения в поле darkTheme будет значение false, если на устройстве включена светлая тема, и true, если тёмная.
//        switchTheme(darkTheme)
    }

//    fun switchTheme(darkThemeEnabled: Boolean) {
//        darkTheme = darkThemeEnabled //сохраняет текущее состояние темы в переменную класса
//        AppCompatDelegate.setDefaultNightMode(
//            if (darkThemeEnabled) {
//                AppCompatDelegate.MODE_NIGHT_YES
//            } else {
//                AppCompatDelegate.MODE_NIGHT_NO
//            }
//        )
//        sharedPrefs.edit()
//            .putBoolean(THEMES_KEY, darkTheme)
//            .apply()
//    }
}