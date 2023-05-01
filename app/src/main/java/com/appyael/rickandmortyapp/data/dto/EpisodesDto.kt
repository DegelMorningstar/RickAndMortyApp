package com.appyael.rickandmortyapp.data.dto

import com.appyael.rickandmortyapp.domain.model.Episode
import com.google.gson.annotations.Expose

data class EpisodesDto(
    @Expose val info: InfoDto,
    @Expose val results: List<EpisodeDto>
)

fun EpisodesDto.toListEpisodes(): List<Episode>{
    val result = results.mapIndexed { _, entries ->
        Episode(
            name = entries.name,
            airDate = entries.air_date,
            episode = entries.episode
        )
    }
    return result
}
