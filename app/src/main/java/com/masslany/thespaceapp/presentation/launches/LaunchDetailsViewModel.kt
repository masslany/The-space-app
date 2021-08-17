package com.masslany.thespaceapp.presentation.launches

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.masslany.thespaceapp.domain.model.LaunchEntity
import com.masslany.thespaceapp.domain.usecase.FetchLaunchByIdUseCase
import com.masslany.thespaceapp.domain.usecase.GetLaunchNotificationStateUseCase
import com.masslany.thespaceapp.domain.usecase.ToggleLaunchNotificationUseCase
import com.masslany.thespaceapp.utils.Result
import com.masslany.thespaceapp.utils.State
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

    private val _launch = MutableLiveData<State<LaunchEntity>>()
    val launch: LiveData<State<LaunchEntity>> = _launch

    private val _notificationState = MutableLiveData<Boolean>()
    val notificationState: LiveData<Boolean> = _notificationState

    private val _shouldShowNotificationToggle = MutableLiveData(false)
    val shouldShowNotificationToggle: LiveData<Boolean> = _shouldShowNotificationToggle


    fun onRetryClicked(id: String) {
        getLaunchById(id)
    }

    fun onNotificationToggleClicked() {
        viewModelScope.launch {
            if (launch.value is State.Success) {
                launch.value?.let {
                    val entity = (it as State.Success).data
                    toggleLaunchNotificationUseCase.execute(entity)
                }
            }
        }
    }

    fun fetchNotificationState(launchEntity: LaunchEntity) {
        viewModelScope.launch {
            val result = getLaunchNotificationStateUseCase.execute(launchEntity)
            result.collect { state ->
                _notificationState.value = state
            }
        }
    }

    private fun shouldShowNotificationToggle(launchEntity: LaunchEntity): Boolean {
        return launchEntity.date > System.currentTimeMillis() / 1000
    }

    fun getLaunchById(id: String) {
        _launch.value = State.Loading

        viewModelScope.launch {
            when (val res = fetchLaunchByIdUseCase.execute(id)) {
                is Result.Success -> {
                    _shouldShowNotificationToggle.value = shouldShowNotificationToggle(res.data)
                    _launch.value = State.Success(res.data)
                }
                is Result.Error<*> -> {
                    _launch.value = State.Error(res.exception)
                }
            }
        }
    }
}