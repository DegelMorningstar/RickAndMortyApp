package com.appyael.rickandmortyapp.domain.listener

import com.appyael.rickandmortyapp.domain.model.Episode

interface EpisodeListListener {
    fun onSuccess(episodeList: List<Episode>)
    fun onError(msg:String)
}