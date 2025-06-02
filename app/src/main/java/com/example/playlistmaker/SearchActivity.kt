package com.example.playlistmaker

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageView
import com.google.android.material.appbar.MaterialToolbar

class SearchActivity : AppCompatActivity() {

    private lateinit var searchInput: EditText
    private lateinit var searchInputClear: ImageView
    private var searchText: String = "" // Переменная для хранения текста поиска

    companion object {
        private const val SEARCH_TEXT_KEY = "SEARCH_TEXT_KEY" // Ключ для сохранения текста
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        searchInput = findViewById(R.id.searchInput)
        searchInputClear = findViewById(R.id.searchInputClear)

        val navigationBack = findViewById<MaterialToolbar>(R.id.tool_bar)
        navigationBack.setNavigationOnClickListener {
            finish()
        }

        // Настройка атрибутов EditText
        searchInput.apply {
            hint = "Поиск"
            maxLines = 1
            inputType = android.text.InputType.TYPE_CLASS_TEXT
        }

        // Восстановление текста при наличии сохраненного состояния
        if (savedInstanceState != null) {
            searchText = savedInstanceState.getString(SEARCH_TEXT_KEY, "")
            searchInput.setText(searchText)
        }

        // Обработчик кнопки очистки
        searchInputClear.setOnClickListener {
            searchInput.setText("")
            searchText = ""
            val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(searchInput.windowToken, 0)
        }

        // TextWatcher для отслеживания изменений текста
        searchInput.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // заглушка
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                searchText = s?.toString() ?: "" // Сохраняем текущий текст
                searchInputClear.visibility = if (s.isNullOrEmpty()) View.GONE else View.VISIBLE
            }

            override fun afterTextChanged(s: Editable?) {
                // заглушка
            }
        })
    }

    // Сохранение текста перед уничтожением Activity
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(SEARCH_TEXT_KEY, searchText)
    }

    // Восстановление текста
    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        searchText = savedInstanceState.getString(SEARCH_TEXT_KEY, "")
        searchInput.setText(searchText)
    }
}