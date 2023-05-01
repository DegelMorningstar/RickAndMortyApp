package com.appyael.rickandmortyapp.data.dto

import com.appyael.rickandmortyapp.domain.model.Character
import com.google.gson.annotations.Expose

data class CharacterDto(
    @Expose val id: Int,
    @Expose val name: String,
    @Expose val status: String,
    @Expose val species: String,
    @Expose val gender: String,
    @Expose val type: String,
    @Expose val origin: OriginDto,
    @Expose val location: LocationDto,
    @Expose val image: String,
    @Expose val episode: List<String>,
    @Expose val url: String,
    @Expose val created: String,
)

fun CharacterDto.toCharacter(): Character {
    return Character(
        id = this.id,
        name = this.name,
        status = this.status,
        species = this.species,
        gender = this.gender,
        origin = this.origin.name,
        location = this.location.name,
        image = this.image,
    )
}