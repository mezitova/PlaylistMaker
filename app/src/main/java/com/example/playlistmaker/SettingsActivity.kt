package com.example.playlistmaker

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.switchmaterial.SwitchMaterial
import com.google.android.material.textview.MaterialTextView

class SettingsActivity : AppCompatActivity() {
    @SuppressLint("QueryPermissionsNeeded")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        setThemeSwitcher()

        val navigationBack = findViewById<MaterialToolbar>(R.id.tool_bar)
        //setOnClickListener
        navigationBack.setNavigationOnClickListener {
            finish()
        }


        val shareTextView = findViewById<MaterialTextView>(R.id.share_line)
        shareTextView.setOnClickListener {
            val shareIntent = Intent(Intent.ACTION_SEND)
            shareIntent.type = "text/plain"  //«Покажи только приложения, которые умеют работать с простым текстом»
            shareIntent.putExtra(Intent.EXTRA_TEXT, getString(R.string.practicum_link))
            startActivity(shareIntent)
            //Если на устройстве нет мессенджеров
            if (shareIntent.resolveActivity(packageManager) != null) {
                startActivity(shareIntent)
            } else {
                Toast.makeText(this, "Нет приложений для шаринга", Toast.LENGTH_SHORT).show()
            }
        }


        val supportTextView = findViewById<MaterialTextView>(R.id.support_line)
        supportTextView.setOnClickListener {
            val sendIntent = Intent(Intent.ACTION_SENDTO)
                sendIntent.data = Uri.parse("mailto:")
                sendIntent.putExtra(Intent.EXTRA_EMAIL, arrayOf(getString(R.string.support_email)))
                sendIntent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.support_email_subject))
                sendIntent.putExtra(Intent.EXTRA_TEXT, getString(R.string.support_email_text))
            startActivity(sendIntent)
        }


        val agreementTextView = findViewById<MaterialTextView>(R.id.agreement_line)
        agreementTextView.setOnClickListener {
            val openAgreementIntent = Intent(Intent.ACTION_VIEW)
            openAgreementIntent.data = Uri.parse(getString(R.string.user_agreement_link))
            startActivity(openAgreementIntent)
        }


    }
    private fun setThemeSwitcher(){
        val themeSwitcher = findViewById<SwitchMaterial>(R.id.themeSwitcher)
        //applicationContext as App - приведение контекста приложения к нашему кастомному классу App
        //.darkTheme - обращение к переменной darkTheme из класса App, которая хранит текущую тему
        themeSwitcher.isChecked = (applicationContext as App).themeSwitcherService.getDarkThemeEnabled()
        //applicationContext - Экземпляр нашего App


        //App.themeSwitcherService.getDarkThemeEnabled() - если themeSwitcherService статичная, мы сможем к ней обратиться
        //только после создания экземпляра App

        //switcher, checked - ссылка на переключатель и его состояние
        themeSwitcher.setOnCheckedChangeListener { switcher, checked ->
            // приведение контекста приложения к кастомному классу App
            //вызов нашей функции переключения темы с текущим состоянием переключателя
            (applicationContext as App).themeSwitcherService.switchTheme(checked)
        }
    }
}