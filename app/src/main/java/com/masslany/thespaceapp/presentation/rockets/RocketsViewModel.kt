package com.masslany.thespaceapp.presentation.rockets

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.masslany.thespaceapp.domain.model.RocketModel
import com.masslany.thespaceapp.domain.usecase.FetchRocketsUseCase
import com.masslany.thespaceapp.utils.Result
import com.masslany.thespaceapp.utils.State
import com.masslany.thespaceapp.utils.State.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RocketsViewModel @Inject constructor(
    private val fetchRocketsUseCase: FetchRocketsUseCase
) : ViewModel() {

    private val _rockets = MutableLiveData<State<List<RocketModel>>>()
    val rockets: LiveData<State<List<RocketModel>>> = _rockets

    init {
        fetchRockets()
    }

    fun onRetryClicked() {
        fetchRockets()
    }

    private fun fetchRockets() {
        _rockets.value = Loading
        viewModelScope.launch {
            when (val result = fetchRocketsUseCase.execute()) {
                is Result.Success -> {
                    _rockets.value = Success(result.data)
                }
                is Result.Error -> {
                    _rockets.value = Error(result.exception)
                }
            }
        }
    }
}