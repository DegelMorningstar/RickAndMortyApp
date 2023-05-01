package com.appyael.rickandmortyapp.ui.episodes

import android.os.Handler
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.appyael.rickandmortyapp.domain.listener.EpisodeListListener
import com.appyael.rickandmortyapp.domain.model.Episode
import com.appyael.rickandmortyapp.domain.usecase.RickAndMortyUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EpisodesViewModel @Inject constructor(
    private val rickAndMortyUseCase: RickAndMortyUseCase
):ViewModel() {

    private val _isLoading = MutableLiveData<Boolean>(false)
    val isLoading: LiveData<Boolean> = _isLoading

    private val _showPrev = MutableLiveData<Boolean>(false)
    val showPrev: LiveData<Boolean> = _showPrev

    private val _showNext = MutableLiveData<Boolean>(false)
    val showNext: LiveData<Boolean> = _showNext

    private val _episodesList = MutableLiveData<List<Episode>>()
    val episodeList : LiveData<List<Episode>> = _episodesList

    private var currentPage = 1

    fun getEpisodesList(increase: Boolean){
        _isLoading.value = true
        viewModelScope.launch {
            if (increase) currentPage++ else if (currentPage > 1) currentPage--
            val showPrevious = currentPage > 1
            val showNext = currentPage < 3
            rickAndMortyUseCase.invokeGetEpisodes(
                currentPage,
                object : EpisodeListListener{
                    override fun onSuccess(episodeList: List<Episode>) {
                        _episodesList.value = episodeList
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