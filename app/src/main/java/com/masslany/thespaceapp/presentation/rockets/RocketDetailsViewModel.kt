package com.masslany.thespaceapp.presentation.rockets

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.masslany.thespaceapp.domain.model.RocketEntity
import com.masslany.thespaceapp.domain.usecase.FetchRocketByIdUseCase
import com.masslany.thespaceapp.utils.Result
import com.masslany.thespaceapp.utils.State
import com.masslany.thespaceapp.utils.State.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RocketDetailsViewModel @Inject constructor(
    private val fetchRocketByIdUseCase: FetchRocketByIdUseCase
) : ViewModel() {

    private val _rocket = MutableLiveData<State<RocketEntity>>()
    val rocket: LiveData<State<RocketEntity>> = _rocket

    fun onRetryClicked(id: String) {
        fetchRocketById(id)
    }

    fun fetchRocketById(id: String) {
        _rocket.value = Loading

        viewModelScope.launch {
            when (val result = fetchRocketByIdUseCase.execute(id)) {
                is Result.Success -> {
                    _rocket.value = Success(result.data)
                }
                is Result.Error<*> -> {
                    _rocket.value = Error(result.exception)
                }
            }
        }
    }
}