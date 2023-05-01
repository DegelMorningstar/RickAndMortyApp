package com.appyael.rickandmortyapp.ui.locations

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.appyael.rickandmortyapp.domain.listener.LocationsListListener
import com.appyael.rickandmortyapp.domain.model.Location
import com.appyael.rickandmortyapp.domain.usecase.RickAndMortyUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LocationsViewModel @Inject constructor(
    private val rickAndMortyUseCase: RickAndMortyUseCase
):ViewModel(){
    private val _isLoading = MutableLiveData<Boolean>(false)
    val isLoading: LiveData<Boolean> = _isLoading

    private val _showPrev = MutableLiveData<Boolean>(false)
    val showPrev: LiveData<Boolean> = _showPrev

    private val _showNext = MutableLiveData<Boolean>(false)
    val showNext: LiveData<Boolean> = _showNext

    private val _locationsList = MutableLiveData<List<Location>>()
    val locationsList : LiveData<List<Location>> = _locationsList

    private var currentPage = 1

    fun getLocationsList(increase: Boolean){
        _isLoading.value = true
        viewModelScope.launch {
            if (increase) currentPage++ else if (currentPage > 1) currentPage--
            val showPrevious = currentPage > 1
            val showNext = currentPage < 7

            rickAndMortyUseCase.invokeGetLocations(
                currentPage,
                object : LocationsListListener {
                    override fun onSuccess(locationsList: List<Location>) {
                        _locationsList.value = locationsList
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