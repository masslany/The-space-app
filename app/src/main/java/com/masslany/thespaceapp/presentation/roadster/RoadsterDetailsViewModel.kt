package com.masslany.thespaceapp.presentation.roadster

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.masslany.thespaceapp.domain.model.RoadsterModel
import com.masslany.thespaceapp.domain.usecase.FetchRoadsterDataUseCase
import com.masslany.thespaceapp.utils.Result
import com.masslany.thespaceapp.utils.State
import com.masslany.thespaceapp.utils.State.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RoadsterDetailsViewModel @Inject constructor(
    private val fetchRoadsterDataUseCase: FetchRoadsterDataUseCase
) : ViewModel() {


    private val _roadsterEntity = MutableLiveData<State<RoadsterModel>>()
    val roadsterModel: LiveData<State<RoadsterModel>> = _roadsterEntity

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
            is Result.Success<RoadsterModel> -> {
                _roadsterEntity.value = Success(response.data)
            }
            is Result.Error<*> -> {
                _roadsterEntity.value = Error(response.exception)
            }
        }
    }
}

