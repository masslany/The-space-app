package com.globallogic.thespaceapp.presentation.launches

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.globallogic.thespaceapp.domain.model.LaunchEntity
import com.globallogic.thespaceapp.domain.usecase.FetchLaunchByIdUseCase
import com.globallogic.thespaceapp.domain.usecase.FetchUpcomingLaunchesDataUseCase
import com.globallogic.thespaceapp.domain.usecase.GetLaunchNotificationStateUseCase
import com.globallogic.thespaceapp.domain.usecase.ToggleLaunchNotificationUseCase
import com.globallogic.thespaceapp.utils.Result
import com.globallogic.thespaceapp.utils.State
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LaunchesSharedViewModel @Inject constructor(
    private val fetchUpcomingLaunchesDataUseCase: FetchUpcomingLaunchesDataUseCase,
    private val fetchLaunchByIdUseCase: FetchLaunchByIdUseCase,
    private val toggleLaunchNotificationUseCase: ToggleLaunchNotificationUseCase,
    private val getLaunchNotificationStateUseCase: GetLaunchNotificationStateUseCase,
) : ViewModel() {

    private val _upcomingLaunches = MutableLiveData<State<List<LaunchEntity>>>()
    val upcomingLaunches: LiveData<State<List<LaunchEntity>>> = _upcomingLaunches

    private val _launch = MutableLiveData<State<LaunchEntity>>()
    val launch: LiveData<State<LaunchEntity>> = _launch

    private val _notificationState = MutableLiveData<Boolean>()
    val notificationState: LiveData<Boolean> = _notificationState

    init {
        fetchUpcomingLaunchesData()
    }

    fun onRetryClicked() {
        fetchUpcomingLaunchesData()
    }

    fun onNotificationToggleClicked(launchEntity: LaunchEntity) {
        viewModelScope.launch {
            toggleLaunchNotificationUseCase.execute(launchEntity)
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

    fun getLaunchById(id: String) {
        _launch.value = State.Loading

        viewModelScope.launch {
            when (val res = fetchLaunchByIdUseCase.execute(id)) {
                is Result.Success -> {
                    _launch.value = State.Success(res.data)
                }
                is Result.Error<*> -> {
                    _launch.value = State.Error(res.exception)
                }
            }
        }
    }

    fun fetchUpcomingLaunchesData() {
        _upcomingLaunches.value = State.Loading

        viewModelScope.launch {
            when (val res = fetchUpcomingLaunchesDataUseCase.execute()) {
                is Result.Success -> {
                    _upcomingLaunches.value = State.Success(res.data)
                }
                is Result.Error<*> -> {
                    _upcomingLaunches.value = State.Error(res.exception)
                }
            }
        }
    }
}