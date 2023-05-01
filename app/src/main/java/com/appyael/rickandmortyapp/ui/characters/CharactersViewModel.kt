package com.appyael.rickandmortyapp.ui.characters

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.appyael.rickandmortyapp.domain.listener.CharactersListListener
import com.appyael.rickandmortyapp.domain.listener.EpisodeListListener
import com.appyael.rickandmortyapp.domain.model.Episode
import com.appyael.rickandmortyapp.domain.usecase.RickAndMortyUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.appyael.rickandmortyapp.domain.model.Character as Character

@HiltViewModel
class CharactersViewModel @Inject constructor(
    private val rickAndMortyUseCase: RickAndMortyUseCase
):ViewModel() {
    private val _isLoading = MutableLiveData<Boolean>(false)
    val isLoading: LiveData<Boolean> = _isLoading

    private val _showPrev = MutableLiveData<Boolean>(false)
    val showPrev: LiveData<Boolean> = _showPrev

    private val _showNext = MutableLiveData<Boolean>(false)
    val showNext: LiveData<Boolean> = _showNext

    private val _charactersList = MutableLiveData<List<Character>>()
    val charactersList : LiveData<List<Character>> = _charactersList

    private var currentPage = 1

    fun getCharactersList(increase: Boolean){
        _isLoading.value = true
        viewModelScope.launch {
            if (increase) currentPage++ else if (currentPage > 1) currentPage--
            val showPrevious = currentPage > 1
            val showNext = currentPage < 42

            rickAndMortyUseCase.invokeGetCharacters(
                currentPage,
                object : CharactersListListener {
                    override fun onSuccess(charactersList: List<Character>) {
                        _charactersList.value = charactersList
                        _showPrev.value = showPrevious
                        _showNext.value = showNext
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