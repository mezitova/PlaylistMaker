package com.example.playlistmaker

import java.util.Locale

data class Track(
    val trackName: String,
    val artistName: String,
    val trackTimeMillis: Long,  // Храним миллисекунды
    val artworkUrl100: String
) {
    // Добавляем метод форматирования
    fun getFormattedTime(): String {
        val minutes = trackTimeMillis / 1000 / 60
        val seconds = (trackTimeMillis / 1000) % 60
        return String.format("%02d:%02d", minutes, seconds)
    }
}