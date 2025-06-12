package com.example.playlistmaker

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.google.android.material.appbar.MaterialToolbar

class Player : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_player)

        setupToolbar()

        //Из Intent извлекается объект Track (сериализованный), переданный из предыдущей активности.
        val track = intent.getSerializableExtra(Track::class.qualifiedName) as Track

        val trackImage = findViewById<ImageView>(R.id.audio_image)
        val trackName = findViewById<TextView>(R.id.audio_name)
        val artistName = findViewById<TextView>(R.id.audio_artist_name)
        val trackTime = findViewById<TextView>(R.id.audio_time)
        val collectionName = findViewById<TextView>(R.id.album_name)
        val collectionNameText = findViewById<TextView>(R.id.album_name_text)
        val releaseDate = findViewById<TextView>(R.id.release_date)
        val primaryGenreName = findViewById<TextView>(R.id.genre_name)
        val country = findViewById<TextView>(R.id.country)


        //Загружаем обложку трека
        Glide.with(this)
            .load(track.getCoverArtWork())
            .transform(RoundedCorners(this.resources.getDimensionPixelSize(R.dimen.corner_radius2)))
            .placeholder(R.drawable.placeholder)
            .into(trackImage)

        trackName.text = track.trackName
        artistName.text = track.artistName
        trackTime.text = track.getFormattedTime()


        releaseDate.text = track.releaseYear
        primaryGenreName.text = track.primaryGenreName
        country.text = track.country

        if (track.collectionName.isNullOrBlank()) {
            collectionNameText.isVisible = false
            collectionName.isVisible = false
        } else {
            collectionName.apply {
                text = track.collectionName
                requestLayout() // Принудительный перерасчёт - при повторном использовании ViewHolder
            }
        }
    }

    private fun setupToolbar() {
        findViewById<MaterialToolbar>(R.id.tool_bar).apply {
            setNavigationOnClickListener { finish() }
        }
    }
}
