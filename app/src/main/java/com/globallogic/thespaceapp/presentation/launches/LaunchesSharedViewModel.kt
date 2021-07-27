package com.globallogic.thespaceapp.presentation.launches

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.globallogic.thespaceapp.domain.model.LaunchEntity
import com.globallogic.thespaceapp.domain.usecase.AddLaunchRemainderUseCase
import com.globallogic.thespaceapp.domain.usecase.FetchLaunchByIdUseCase
import com.globallogic.thespaceapp.domain.usecase.FetchUpcomingLaunchesDataUseCase
import com.globallogic.thespaceapp.utils.Result
import com.globallogic.thespaceapp.utils.State
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LaunchesSharedViewModel @Inject constructor(
    private val fetchUpcomingLaunchesDataUseCase: FetchUpcomingLaunchesDataUseCase,
    private val fetchLaunchByIdUseCase: FetchLaunchByIdUseCase,
    private val addLaunchRemainderUseCase: AddLaunchRemainderUseCase
) : ViewModel() {

    private val _upcomingLaunches = MutableLiveData<State<List<LaunchEntity>>>()
    val upcomingLaunches: LiveData<State<List<LaunchEntity>>> = _upcomingLaunches

    private val _launch = MutableLiveData<State<LaunchEntity>>()
    val launch: LiveData<State<LaunchEntity>> = _launch

    init {
        fetchUpcomingLaunchesData()
    }

    fun onRetryClicked() {
        fetchUpcomingLaunchesData()
    }

    fun onRemainderClicked(launchEntity: LaunchEntity) {
        addLaunchRemainderUseCase.execute(launchEntity)
    }

    fun getLaunchById(id: String) {
        Log.e("TAG", id  )
        if (upcomingLaunches.value == State.Loading) {
            // Should only happen when launching from deeplink
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
        } else {
            val foundLaunch =
                (upcomingLaunches.value as State.Success<List<LaunchEntity>>).data.findLast {
                    it.id == id
                }
            if (foundLaunch != null) {
                _launch.value = State.Success(foundLaunch)
            } else {
                _launch.value = State.Error(NullPointerException())
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