package com.example.playlistmaker

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ItunesApiClientCreator {
    companion object {
        private const val iTunesBaseUrl = "https://itunes.apple.com"

        fun create(): ItunesApi {
            val retrofit = Retrofit.Builder()
                .baseUrl(iTunesBaseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            return retrofit.create(ItunesApi::class.java)
        }
    }
}