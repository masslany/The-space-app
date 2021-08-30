package com.masslany.thespaceapp.presentation.roadster

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.masslany.thespaceapp.domain.model.RoadsterModel
import com.masslany.thespaceapp.domain.usecase.FetchRoadsterDataUseCase
import com.masslany.thespaceapp.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@ExperimentalCoroutinesApi
@HiltViewModel
class RoadsterDetailsViewModel @Inject constructor(
    private val fetchRoadsterDataUseCase: FetchRoadsterDataUseCase
) : ViewModel() {

    private val _roadsterEntity = MutableLiveData<Resource<RoadsterModel>>()
    val roadsterModel: LiveData<Resource<RoadsterModel>> = _roadsterEntity

    init {
        viewModelScope.launch {
            fetchRoadsterData(false)
        }
    }

    fun onRetryClicked() {
        viewModelScope.launch {
            fetchRoadsterData(true)
        }
    }

    private suspend fun fetchRoadsterData(forceRefresh: Boolean) {

        fetchRoadsterDataUseCase.execute(
            forceRefresh = forceRefresh,
            onFetchSuccess = {
            },
            onFetchFailed = {
                _roadsterEntity.value = Resource.Error(it)
            }
        ).stateIn(viewModelScope, SharingStarted.Lazily, Resource.Loading)
            .collect { resource ->
                _roadsterEntity.value = resource
            }
    }
}

