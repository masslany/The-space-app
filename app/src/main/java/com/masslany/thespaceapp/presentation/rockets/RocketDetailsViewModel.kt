package com.masslany.thespaceapp.presentation.rockets

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.masslany.thespaceapp.domain.model.RocketModel
import com.masslany.thespaceapp.domain.usecase.FetchRocketByIdUseCase
import com.masslany.thespaceapp.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RocketDetailsViewModel @Inject constructor(
    private val fetchRocketByIdUseCase: FetchRocketByIdUseCase
) : ViewModel() {

    private val _rocket = MutableLiveData<Resource<RocketModel>>()
    val rocket: LiveData<Resource<RocketModel>> = _rocket

    fun onRetryClicked(id: String) {
        fetchRocketById(id)
    }

    fun fetchRocketById(id: String) {
        _rocket.value = Resource.Loading

        viewModelScope.launch {
            _rocket.value = fetchRocketByIdUseCase.execute(id)
        }
    }
}