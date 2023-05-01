package com.appyael.rickandmortyapp.domain.listener

import com.appyael.rickandmortyapp.domain.model.Location

interface LocationsListListener {
    fun onSuccess(locationsList:List<Location>)
    fun onError(msg:String)
}