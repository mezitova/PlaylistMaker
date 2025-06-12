package com.example.playlistmaker

import java.util.Locale

data class Track(
    val trackName: String?,
    val artistName: String?,
    val trackTimeMillis: Long,  // Храним миллисекунды
    val artworkUrl100: String?,
    val trackId: String,
) {
    // Добавляем метод форматирования
    fun getFormattedTime(): String {
        return if (trackTimeMillis > 0) {
            val minutes = trackTimeMillis / 1000 / 60
            val seconds = (trackTimeMillis / 1000) % 60
            String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds)
        } else {
            ""
        }
    }
}