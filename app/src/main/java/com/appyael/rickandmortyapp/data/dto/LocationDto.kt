package com.appyael.rickandmortyapp.data.dto

import com.google.gson.annotations.Expose

data class LocationDto(
    @Expose val name: String,
    @Expose val url: String
)
