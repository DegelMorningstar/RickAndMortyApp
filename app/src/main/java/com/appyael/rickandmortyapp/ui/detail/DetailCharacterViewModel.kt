package com.appyael.rickandmortyapp.ui.detail

import androidx.lifecycle.*
import com.appyael.rickandmortyapp.domain.listener.CharacterListener
import com.appyael.rickandmortyapp.domain.model.Character
import com.appyael.rickandmortyapp.domain.model.Location
import com.appyael.rickandmortyapp.domain.usecase.RickAndMortyUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailCharacterViewModel @Inject constructor(
    private val rickAndMortyUseCase: RickAndMortyUseCase,
    private val savedStateHandle: SavedStateHandle
):ViewModel() {
    private val _isLoading = MutableLiveData<Boolean>(false)
    val isLoading: LiveData<Boolean> = _isLoading

    private val _character = MutableLiveData<Character>()
    val character : LiveData<Character> = _character

    fun getCharacter(){
        _isLoading.value = true
        savedStateHandle.get<Int>("id")?.let { characterId ->
            viewModelScope.launch {
                rickAndMortyUseCase.invokeGetCharacter(
                    characterId,
                    object : CharacterListener{
                        override fun onSuccess(character: Character) {
                            _character.value = character
                            _isLoading.value = false
                        }

                        override fun onError(msg: String) {
                            _isLoading.value = false
                        }

                    }
                )
            }
        }
    }

}