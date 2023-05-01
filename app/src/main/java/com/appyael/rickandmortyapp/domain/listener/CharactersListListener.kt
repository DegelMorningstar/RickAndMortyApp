package com.appyael.rickandmortyapp.domain.listener

import com.appyael.rickandmortyapp.domain.model.Character as Character

interface CharactersListListener {
    fun onSuccess(charactersList:List<Character>)
    fun onError(msg:String)
}