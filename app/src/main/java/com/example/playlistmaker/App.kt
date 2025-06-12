package com.example.playlistmaker

import android.app.Application

private const val SHARED_PREFERENCES = "practicum_preferences"
//private const val THEMES_KEY = "themes_key"

class App : Application() {

//    var darkTheme = false
    lateinit var themeSwitcherService: ThemeSwitcherService
    lateinit var searchHistoryService: SearchHistoryService

    override fun onCreate() {
        super.onCreate()

        val sharedPrefs = this.getSharedPreferences(SHARED_PREFERENCES, MODE_PRIVATE)//получ экземпляр SharedPreferences
        themeSwitcherService = ThemeSwitcherService(sharedPrefs)
        searchHistoryService = SearchHistoryService(sharedPrefs)

        val darkThemeEnabled = themeSwitcherService.getDarkThemeEnabled()
        themeSwitcherService.switchTheme(darkThemeEnabled)

    }

}