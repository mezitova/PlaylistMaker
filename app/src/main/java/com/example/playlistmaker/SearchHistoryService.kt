package com.example.playlistmaker

import android.content.SharedPreferences
import com.google.gson.Gson
import kotlin.apply
import kotlin.collections.remove

private const val HISTORY_KEY = "history_key"
private const val MAX_HISTORY_SIZE = 10

class SearchHistoryService(private val sharedPrefs: SharedPreferences) {
    // запись
    fun addTrackToHistory(trackToAdd: Track) {
        var currentTracks = getTrackHistory()
        var newTracks =
            currentTracks.filter { trackItem -> trackItem.trackId != trackToAdd.trackId }
                .toMutableList()
        newTracks.add(0, trackToAdd)


        if (newTracks.size > MAX_HISTORY_SIZE) {
            newTracks.removeAt(newTracks.size - 1)
        }

        val json = Gson().toJson(newTracks)
        sharedPrefs.edit()
            .putString(HISTORY_KEY, json)
            .apply()
    }

    //чтение
    fun getTrackHistory(): Array<Track> {
        val json = sharedPrefs.getString(HISTORY_KEY, null) ?: return emptyArray()
        return Gson().fromJson(json, Array<Track>::class.java)
    }

    fun clearHistory() {
        sharedPrefs.edit()
            .remove(HISTORY_KEY)
            .apply()
    }

}