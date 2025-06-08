package com.example.playlistmaker
import retrofit2.http.GET
import retrofit2.http.Path

import retrofit2.Call
import retrofit2.http.Query

//библиотека retrofit2 создает за нас экземпляр класса, который имплементит ItunesApi и
//сможет вызвать метод search

//исчерп. -ее описание того, как делать запрос
//нам нужен экземпляр интерф. ItunesApi чтобы вызвать search приняв expression, чтобы вернуть Call<TracksResponse>
interface ItunesApi {
    @GET("/search?entity=song")
    fun search(@Query("term") text: String) : Call<TracksResponse>
}
//Call - вызов с ожиданием ответа типа TracksResponse
//на search висит аннотация  @GET - значит search выполняет запрос получив term
//и возвращает ...
//TracksResponse заворачивается в Call
//search сразу после завершения вернет Call. А TracksResponse вернем в другое время.

//fun search(@Query("term") text: String)
// Добавляет параметр term в URL запроса. Значение будет взято из аргумента text.
// Например, если вызвать search("beatles"), то URL будет /search?entity=song&term=beatles.
