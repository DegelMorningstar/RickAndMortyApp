package com.appyael.rickandmortyapp.data.dto

import com.appyael.rickandmortyapp.domain.model.Location
import com.google.gson.annotations.Expose

data class LocationsDto(
    @Expose val info: InfoDto,
    @Expose val results: List<LocationXDto>
)

data class LocationXDto(
    @Expose val id:Int,
    @Expose val name:String,
    @Expose val type:String,
    @Expose val dimension:String,
    @Expose val residents:List<String>,
    @Expose val url:String,
    @Expose val created:String
)

fun LocationsDto.toListLocations(): List<Location> {
    val resultEntries = results.mapIndexed { _, entries ->
        Location(
            name = entries.name,
            type = entries.type,
            dimension = entries.dimension
        )
    }
    return resultEntries
}
