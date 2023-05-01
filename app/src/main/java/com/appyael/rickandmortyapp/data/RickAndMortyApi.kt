package com.appyael.rickandmortyapp.data

import com.appyael.rickandmortyapp.data.dto.CharacterDto
import com.appyael.rickandmortyapp.data.dto.CharactersDto
import com.appyael.rickandmortyapp.data.dto.EpisodesDto
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RickAndMortyApi {

    @GET("character/")
    fun getCharacters(
        @Query("page") page: Int
    ): Call<ResponseBody>

    @GET("character/{id}")
    fun getCharacter(
        @Path("id") id: Int
    ): Call<ResponseBody>

    @GET("episode/")
    fun getEpisodes(
        @Query("page") page: Int
    ): Call<ResponseBody>

    @GET("location/")
    fun getLocations(
        @Query("page") page: Int
    ): Call<ResponseBody>
}