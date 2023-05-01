package com.appyael.rickandmortyapp.data.dto

import com.google.gson.annotations.Expose
import com.appyael.rickandmortyapp.domain.model.Character as Character

class CharactersDto(
    @Expose val info: InfoDto,
    @Expose val results: List<CharacterDto>
)

fun CharactersDto.toListCharacters(): List<Character> {
    val resultEntries = results.mapIndexed { _, entries ->
        Character(
            id = entries.id,
            name = entries.name,
            status = entries.status,
            species = entries.species,
            gender = entries.gender,
            origin = entries.origin.name,
            location = entries.location.name,
            image = entries.image,
        )
    }
    return resultEntries
}