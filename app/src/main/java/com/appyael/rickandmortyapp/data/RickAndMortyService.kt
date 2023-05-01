package com.appyael.rickandmortyapp.data

import android.util.Log
import com.appyael.rickandmortyapp.data.di.IoDispatcher
import com.appyael.rickandmortyapp.data.dto.*
import com.appyael.rickandmortyapp.domain.listener.CharacterListener
import com.appyael.rickandmortyapp.domain.listener.CharactersListListener
import com.appyael.rickandmortyapp.domain.listener.EpisodeListListener
import com.appyael.rickandmortyapp.domain.listener.LocationsListListener
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class RickAndMortyService @Inject constructor(
    private val rickAndMortyApi: RickAndMortyApi,
    private val gson:Gson,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) {

    suspend fun getEpisodes(page: Int,listener: EpisodeListListener) {
        withContext(ioDispatcher){
            val response = rickAndMortyApi.getEpisodes(page)
            response.enqueue(object : Callback<ResponseBody>{
                override fun onResponse(
                    call: Call<ResponseBody>,
                    response: Response<ResponseBody>
                ) {
                    if(response.isSuccessful){
                        val json = response.body()?.string() ?: ""
                        val episodesDto = gson.fromJson(json, EpisodesDto::class.java)
                        listener.onSuccess(episodesDto.toListEpisodes())
                    }else{
                        listener.onError("Hubo un error")
                    }
                }

                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    listener.onError(t.message.toString())
                }

            })
        }
    }

    suspend fun getCharacters(page: Int,listener: CharactersListListener) {
        withContext(ioDispatcher){
            val response = rickAndMortyApi.getCharacters(page)
            response.enqueue(object : Callback<ResponseBody>{
                override fun onResponse(
                    call: Call<ResponseBody>,
                    response: Response<ResponseBody>
                ) {
                    if(response.isSuccessful){
                        val json = response.body()?.string() ?: ""
                        Log.d("******************json",json)
                        val charactersDto = gson.fromJson(json, CharactersDto::class.java)
                        listener.onSuccess(charactersDto.toListCharacters())
                    }else{
                        listener.onError("Hubo un error")
                    }
                }

                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    listener.onError(t.message.toString())
                }

            })
        }
    }

    suspend fun getCharacter(id: Int,listener: CharacterListener) {
        withContext(ioDispatcher){
            val response = rickAndMortyApi.getCharacter(id)
            response.enqueue(object : Callback<ResponseBody>{
                override fun onResponse(
                    call: Call<ResponseBody>,
                    response: Response<ResponseBody>
                ) {
                    if(response.isSuccessful){
                        val json = response.body()?.string() ?: ""
                        Log.d("******************json",json)
                        val characterDto = gson.fromJson(json, CharacterDto::class.java)
                        listener.onSuccess(characterDto.toCharacter())
                    }else{
                        listener.onError("Hubo un error")
                    }
                }

                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    listener.onError(t.message.toString())
                }

            })
        }
    }

    suspend fun getLocations(page: Int,listener: LocationsListListener) {
        withContext(ioDispatcher){
            val response = rickAndMortyApi.getLocations(page)
            response.enqueue(object : Callback<ResponseBody>{
                override fun onResponse(
                    call: Call<ResponseBody>,
                    response: Response<ResponseBody>
                ) {
                    if(response.isSuccessful){
                        val json = response.body()?.string() ?: ""
                        Log.d("******************json",json)
                        val locationsDto = gson.fromJson(json, LocationsDto::class.java)
                        listener.onSuccess(locationsDto.toListLocations())
                    }else{
                        listener.onError("Hubo un error")
                    }
                }

                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    listener.onError(t.message.toString())
                }

            })
        }
    }
}