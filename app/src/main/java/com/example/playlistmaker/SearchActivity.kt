package com.example.playlistmaker

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.appbar.MaterialToolbar
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.Locale

class SearchActivity : AppCompatActivity() {

    private lateinit var searchInputEditText: EditText
    private lateinit var searchInputClear: ImageView
    private var searchText: String = "" // Переменная для хранения текста поиска
    private lateinit var recyclerView: RecyclerView
    private lateinit var trackAdapter: TrackAdapter

    //-------------------------------------<Спринт 11
    //нам нужен экземпляр интерф. ITunesApi чтобы вызвать search
    private val iTunesService = ItunesApiClientCreator.create()
    private val tracks = ArrayList<Track>()

    private lateinit var placeholderMessage: TextView
    private lateinit var placeHolderImage: ImageView
    private lateinit var refreshButton: Button
    //--------------------------------------Спринт 11>

    //ст.
    companion object {
        private const val SEARCH_TEXT_KEY = "SEARCH_TEXT_KEY" // Ключ для сохранения текста
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        searchInputEditText = findViewById(R.id.search_input)
        searchInputClear = findViewById(R.id.search_input_clear)
        recyclerView = findViewById(R.id.recycler_track_list)
        //----------------------------------<Спринт 11
        placeholderMessage = findViewById(R.id.placeHolderMessage)
        placeHolderImage = findViewById(R.id.placeHolderImage)
        refreshButton = findViewById(R.id.refreshButton)
        //----------------------------------Спринт 11>


        //trackAdapter = TrackAdapter(MockTracks.getMockTracks())
        trackAdapter = TrackAdapter(tracks)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = trackAdapter


        val navigationBack = findViewById<MaterialToolbar>(R.id.tool_bar)
        navigationBack.setNavigationOnClickListener {
            finish()
        }

        // Настройка атрибутов EditText
        searchInputEditText.apply {
            hint = context.getString(R.string.search)
            maxLines = 1
            inputType = android.text.InputType.TYPE_CLASS_TEXT
        }

        //--------------------------------- <Спринт 11

        searchInputEditText.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                if (searchInputEditText.text.isNotEmpty()) {
                    performSearch(searchInputEditText.text.toString())
                }
                true
            }
            false
        }

        refreshButton.setOnClickListener {
            if (searchText.isNotEmpty()) {
                performSearch(searchText)
            }
        }


        //------------------------------------ Спринт 11>

        // Восстановление текста при наличии сохраненного состояния
        if (savedInstanceState != null) {
            searchText = savedInstanceState.getString(SEARCH_TEXT_KEY, "")
            searchInputEditText.setText(searchText)
        }

        // Обработчик кнопки очистки
        searchInputClear.setOnClickListener {
            clearSearchResults()
        }

        // TextWatcher для отслеживания изменений текста
        searchInputEditText.addTextChangedListener(object : TextWatcher {
            //Срабатывает перед изменением текста
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // заглушка
            }

            //Срабатывает во время изменения текста.
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                searchText = s?.toString() ?: "" // Сохраняем текущий текст
                searchInputClear.visibility = if (s.isNullOrEmpty()) View.GONE else View.VISIBLE
            }

            override fun afterTextChanged(s: Editable?) {
                // заглушка
            }
        })
    }



    //-------------------------------<Спринт 11
    private fun performSearch(term: String) {
        showLoading()
        iTunesService.search(term).enqueue(object : Callback<TracksResponse> {
            override fun onResponse(call: Call<TracksResponse>, response: Response<TracksResponse>) {
                if (response.isSuccessful) {
                    val tracksResponse = response.body()
                    if (tracksResponse?.results != null) {
                        val trackList = tracksResponse.results.map { apiTrack ->
                            Track(
                                trackName = apiTrack.trackName ?: "",
                                artistName = apiTrack.artistName ?: "",
                                trackTimeMillis = apiTrack.trackTimeMillis ?: 0,  // Передаём миллисекунды
                                artworkUrl100 = apiTrack.artworkUrl100 ?: ""
                            )
                        }
                        showSearchResults(trackList)
                    } else {
                        showEmptyResults()
                    }
                } else {
                    showError()
                }
            }

            override fun onFailure(call: Call<TracksResponse>, t: Throwable) {
                showError()
            }
        })
    }

    private fun formatTrackTime(millis: Long): String {
        return if (millis > 0) {
            val minutes = millis / 1000 / 60
            val seconds = millis / 1000 % 60
            String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds)
        } else {
            ""
        }
    }

    private fun showLoading() {
        recyclerView.visibility = View.GONE
        placeholderMessage.visibility = View.GONE
        placeHolderImage.visibility = View.GONE
        refreshButton.visibility = View.GONE
    }

    private fun showSearchResults(results: List<Track>) {
        if (results.isEmpty()) {
            showEmptyResults()
            return
        }

        tracks.clear()
        tracks.addAll(results)
        trackAdapter.notifyDataSetChanged()

        recyclerView.visibility = View.VISIBLE
        placeholderMessage.visibility = View.GONE
        placeHolderImage.visibility = View.GONE
        refreshButton.visibility = View.GONE
    }

    private fun showEmptyResults() {
        recyclerView.visibility = View.GONE
        placeholderMessage.visibility = View.VISIBLE
        placeHolderImage.visibility = View.VISIBLE
        refreshButton.visibility = View.GONE

        placeholderMessage.text = getString(R.string.nothing_found)
        placeHolderImage.setImageResource(R.drawable.nothing_found)
    }

    private fun showError() {
        recyclerView.visibility = View.GONE
        placeholderMessage.visibility = View.VISIBLE
        placeHolderImage.visibility = View.VISIBLE
        refreshButton.visibility = View.VISIBLE

        placeholderMessage.text = getString(R.string.no_internet)
        placeHolderImage.setImageResource(R.drawable.no_internet)
    }

    private fun clearSearchResults() {
        searchInputEditText.setText("")
        searchText = ""

        // Очищаем список треков, скрываем RecyclerView, cкрываем все возможные плейсхолдеры
        tracks.clear()
        trackAdapter.notifyDataSetChanged()
        recyclerView.visibility = View.GONE

        placeholderMessage.visibility = View.GONE
        placeHolderImage.visibility = View.GONE
        refreshButton.visibility = View.GONE


        // Скрываем клавиатуру
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(searchInputEditText.windowToken, 0)
        //   searchInputClear.visibility = View.GONE
    }



    //-------------------------------Спринт 11>

    // Сохранение текста перед уничтожением Activity
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState) //Сохраняет состояние всех View с id(например, текст в EditText, если у него есть android:id).
        outState.putString(SEARCH_TEXT_KEY, searchText) //ключ-значение
    }

    // Восстановление текста, Вызывается, когда Activity воссоздается (например, после поворота экрана)
    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState) //восстанавливает состояние системных View (если у них есть android:id)
        searchText = savedInstanceState.getString(SEARCH_TEXT_KEY, "") //Извлекает сохраненный текст по ключу SEARCH_TEXT_KEY (если нет - использует пустую строку "")
        searchInputEditText.setText(searchText)
    }
}