package com.example.playlistmaker

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners


class TrackAdapter(
    val parentActivity: AppCompatActivity,
    var tracks: List<Track>, // -------------------------------------
    private val searchHistoryService: SearchHistoryService
) : RecyclerView.Adapter<TrackAdapter.TrackViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrackViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.track_item, parent, false)
        return TrackViewHolder(view)
    }

    override fun onBindViewHolder(holder: TrackViewHolder, position: Int) {
        holder.bind(tracks[position])
        holder.itemView.setOnClickListener {
            searchHistoryService.addTrackToHistory(tracks[position])
            playTrack(tracks[position])
        }
    }

    override fun getItemCount(): Int = tracks.size


    private fun playTrack(track: Track) {
        val playerIntent = Intent(parentActivity, Player::class.java).putExtra(Track::class.qualifiedName, track)
        parentActivity.startActivity(playerIntent)
    }

    class TrackViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val trackName: TextView = itemView.findViewById(R.id.track_name)
        private val artistName: TextView = itemView.findViewById(R.id.artist_name)
        private val trackTime: TextView = itemView.findViewById(R.id.track_time)
        private val artwork: ImageView = itemView.findViewById(R.id.artwork)

        fun bind(track: Track) {
            trackName.text = track.trackName

            artistName.apply {
                text = track.artistName
                requestLayout() // Принудительный перерасчёт - магия для решения бага в Android. * без которой трек тайм полетит вправо при повторном использовании ViewHolder
            }

            trackTime.text = track.getFormattedTime() // Форматируем здесь

            Glide.with(itemView.context)
                .load(track.artworkUrl100)
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.placeholder) // Показываем плейсхолдер при ошибке загрузки
                .transform(RoundedCorners(itemView.resources.getDimensionPixelSize(R.dimen.corner_radius)))
                .centerCrop()
                .into(artwork)
        }
    }
}

//* Почему требуется сброс текста?
//Механизм переиспользования ViewHolder
//RecyclerView для оптимизации использует одни и те же ViewHolder для разных позиций списка. Если не очищать данные перед их обновлением, могут оставаться артефакты от предыдущего элемента.
//
//Проблема с ellipsize и layout_weight
//При комбинации android:ellipsize="end" и layout_weight, TextView может "застрять" в состоянии обрезанного текста, даже если новый текст короче. Это баг в Android.

