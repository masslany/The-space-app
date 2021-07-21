package com.globallogic.thespaceapp.presentation.roadster

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.globallogic.thespaceapp.domain.model.RoadsterEntity
import com.globallogic.thespaceapp.domain.usecase.FetchRoadsterDataUseCase
import com.globallogic.thespaceapp.presentation.roadster.RoadsterDetailsViewModel.RoadsterDetailsState.*
import com.globallogic.thespaceapp.utils.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RoadsterDetailsViewModel @Inject constructor(
    private val fetchRoadsterDataUseCase: FetchRoadsterDataUseCase
) : ViewModel() {


    private val _roadsterEntity = MutableLiveData<RoadsterDetailsState>()
    val roadsterEntity: LiveData<RoadsterDetailsState> = _roadsterEntity

    init {
        viewModelScope.launch {
            fetchRoadsterData()
        }
    }

    fun onRetryClicked() {
        viewModelScope.launch {
            fetchRoadsterData()
        }
    }

    private suspend fun fetchRoadsterData() {
        _roadsterEntity.value = Loading

        when (val response = fetchRoadsterDataUseCase.execute()) {
            is Result.Success<RoadsterEntity> -> {
                _roadsterEntity.value = Success(response.data)
            }
            is Result.Error<*> -> {
                _roadsterEntity.value = Error(response.exception)
            }
        }
    }

    sealed class RoadsterDetailsState {
        data class Success(val data: RoadsterEntity) : RoadsterDetailsState()
        data class Error(val exception: Exception) : RoadsterDetailsState()
        object Loading : RoadsterDetailsState()
    }
}

