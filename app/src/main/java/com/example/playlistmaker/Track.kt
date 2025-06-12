package com.example.playlistmaker

import android.content.Intent
import java.io.Serializable
import java.text.SimpleDateFormat
import java.util.Locale

data class Track(
    val trackName: String,
    val artistName: String,
    val trackTimeMillis: Long,  // Храним миллисекунды
    val artworkUrl100: String,
    val trackId: String,

    val collectionName: String?,
    val releaseDate: String?,
    val primaryGenreName: String,
    val country: String
) : Serializable {

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

    val releaseYear: String
        get() = parseReleaseYear(releaseDate)

    fun getCoverArtWork() = artworkUrl100.replaceAfterLast('/', "512x512.jpg")


    private fun parseReleaseYear(releaseDate: String?): String {
        if (releaseDate == null) {
            return ""
        }
        return try {
            val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault())
            val date = inputFormat.parse(releaseDate)
            (date.year + 1900).toString()
        } catch (e: Exception) {
            ""
        }}
}