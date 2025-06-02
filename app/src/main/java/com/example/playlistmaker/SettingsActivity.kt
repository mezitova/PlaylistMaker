package com.example.playlistmaker

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.google.android.material.appbar.MaterialToolbar

class SettingsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        val navigationBack = findViewById<MaterialToolbar>(R.id.tool_bar)
        //setOnClickListener
        navigationBack.setNavigationOnClickListener {
            finish()
        }

    }
}