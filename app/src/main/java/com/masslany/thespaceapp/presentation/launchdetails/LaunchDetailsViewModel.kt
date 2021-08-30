package com.masslany.thespaceapp.presentation.launchdetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.masslany.thespaceapp.domain.model.LaunchModel
import com.masslany.thespaceapp.domain.usecase.FetchLaunchByIdUseCase
import com.masslany.thespaceapp.domain.usecase.GetLaunchNotificationStateUseCase
import com.masslany.thespaceapp.domain.usecase.ToggleLaunchNotificationUseCase
import com.masslany.thespaceapp.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LaunchDetailsViewModel @Inject constructor(
    private val fetchLaunchByIdUseCase: FetchLaunchByIdUseCase,
    private val toggleLaunchNotificationUseCase: ToggleLaunchNotificationUseCase,
    private val getLaunchNotificationStateUseCase: GetLaunchNotificationStateUseCase,
) : ViewModel() {

    private val _launch = MutableLiveData<Resource<LaunchModel>>()
    val launch: LiveData<Resource<LaunchModel>> = _launch

    private val _notificationState = MutableLiveData<Boolean>()
    val notificationState: LiveData<Boolean> = _notificationState

    private val _shouldShowNotificationToggle = MutableLiveData(false)
    val shouldShowNotificationToggle: LiveData<Boolean> = _shouldShowNotificationToggle


    fun onRetryClicked(id: String) {
        getLaunchById(id)
    }

    fun onNotificationToggleClicked() {
        viewModelScope.launch {
            if (launch.value is Resource.Success) {
                launch.value?.let {
                    val entity = (it as Resource.Success).data
                    toggleLaunchNotificationUseCase.execute(entity)
                }
            }
        }
    }

    fun fetchNotificationState(launchModel: LaunchModel) {
        viewModelScope.launch {
            val result = getLaunchNotificationStateUseCase.execute(launchModel)
            result.collect { state ->
                _notificationState.value = state
            }
        }
    }

    private fun shouldShowNotificationToggle(launchModel: LaunchModel): Boolean {
        return launchModel.date > System.currentTimeMillis() / 1000
    }

    fun getLaunchById(id: String) {
        _launch.value = Resource.Loading

        viewModelScope.launch {
            when (val res = fetchLaunchByIdUseCase.execute(id)) {
                is Resource.Success -> {
                    _shouldShowNotificationToggle.value = shouldShowNotificationToggle(res.data)
                    _launch.value = Resource.Success(res.data)
                }
                is Resource.Error -> {
                    _launch.value = Resource.Error(res.throwable)
                }
            }
        }
    }
}