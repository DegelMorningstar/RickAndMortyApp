package com.appyael.rickandmortyapp.domain.listener

import com.appyael.rickandmortyapp.domain.model.Character

interface CharacterListener {
    fun onSuccess(character: Character)
    fun onError(msg:String)
}