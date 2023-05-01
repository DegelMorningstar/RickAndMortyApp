package com.appyael.rickandmortyapp.data.dto

import com.google.gson.annotations.Expose

data class InfoDto(
    @Expose val count: Int,
    @Expose val next: String,
    @Expose val pages: Int,
    @Expose val prev: Any
)