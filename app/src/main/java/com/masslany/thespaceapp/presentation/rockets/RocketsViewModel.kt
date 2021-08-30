package com.masslany.thespaceapp.presentation.rockets

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.masslany.thespaceapp.domain.model.RocketModel
import com.masslany.thespaceapp.domain.usecase.FetchRocketsUseCase
import com.masslany.thespaceapp.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RocketsViewModel @Inject constructor(
    private val fetchRocketsUseCase: FetchRocketsUseCase
) : ViewModel() {

    private val _rockets = MutableLiveData<Resource<List<RocketModel>>>()
    val rockets: LiveData<Resource<List<RocketModel>>> = _rockets

    init {
        fetchRockets()
    }

    fun onRetryClicked() {
        fetchRockets()
    }

    private fun fetchRockets() {
        _rockets.value = Resource.Loading
        viewModelScope.launch {
            _rockets.value = fetchRocketsUseCase.execute()
        }
    }
}