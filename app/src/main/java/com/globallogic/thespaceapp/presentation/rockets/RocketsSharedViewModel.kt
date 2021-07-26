package com.globallogic.thespaceapp.presentation.rockets

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.globallogic.thespaceapp.domain.model.RocketEntity
import com.globallogic.thespaceapp.domain.usecase.FetchRocketsUseCase
import com.globallogic.thespaceapp.utils.Result
import com.globallogic.thespaceapp.utils.State
import com.globallogic.thespaceapp.utils.State.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.lang.NullPointerException
import javax.inject.Inject

@HiltViewModel
class RocketsSharedViewModel @Inject constructor(
    private val fetchRocketsUseCase: FetchRocketsUseCase
) : ViewModel() {

    private val _rockets = MutableLiveData<State<List<RocketEntity>>>()
    val rockets: LiveData<State<List<RocketEntity>>> = _rockets

    private val _rocket = MutableLiveData<State<RocketEntity>>()
    val rocket: LiveData<State<RocketEntity>> = _rocket

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
                is Result.Error<*> -> {
                    _rockets.value = Error(result.exception)
                }
            }
        }
    }

    fun getRocketById(rocketId: String) {
        val rocket = (rockets.value as Success).data.findLast {
            it.id == rocketId
        }

        if(rocket != null) {
            _rocket.value = Success(rocket)
        } else {
            _rocket.value = Error(NullPointerException())
        }
    }
}