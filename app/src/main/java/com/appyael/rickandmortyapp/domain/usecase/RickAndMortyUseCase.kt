package com.appyael.rickandmortyapp.domain.usecase

import com.appyael.rickandmortyapp.data.RickAndMortyService
import com.appyael.rickandmortyapp.domain.listener.CharacterListener
import com.appyael.rickandmortyapp.domain.listener.CharactersListListener
import com.appyael.rickandmortyapp.domain.listener.EpisodeListListener
import com.appyael.rickandmortyapp.domain.listener.LocationsListListener
import javax.inject.Inject

class RickAndMortyUseCase @Inject constructor(
    private val rickAndMortyService: RickAndMortyService
) {

    suspend fun invokeGetEpisodes(
        page:Int,
        listener: EpisodeListListener
    ){
        rickAndMortyService.getEpisodes(
            page,
            listener
        )
    }

    suspend fun invokeGetCharacters(
        page:Int,
        listener:CharactersListListener
    ){
        rickAndMortyService.getCharacters(
            page,
            listener
        )
    }

    suspend fun invokeGetLocations(
        page: Int,
        listener: LocationsListListener
    ){
        rickAndMortyService.getLocations(
            page,
            listener
        )
    }

    suspend fun invokeGetCharacter(
        id:Int,
        listener: CharacterListener
    ){
        rickAndMortyService.getCharacter(
            id,
            listener
        )
    }

}