package com.globallogic.thespaceapp.presentation.launches

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.globallogic.thespaceapp.domain.model.LaunchEntity
import com.globallogic.thespaceapp.domain.usecase.FetchUpcomingLaunchesDataUseCase
import com.globallogic.thespaceapp.utils.Result
import com.globallogic.thespaceapp.utils.State
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LaunchesSharedViewModel @Inject constructor(
    private val fetchUpcomingLaunchesDataUseCase: FetchUpcomingLaunchesDataUseCase
) : ViewModel() {

    private val _upcomingLaunches = MutableLiveData<State<List<LaunchEntity>>>()
    val upcomingLaunches: LiveData<State<List<LaunchEntity>>> = _upcomingLaunches

    init {
        fetchUpcomingLaunchesData()
    }

    fun onRetryClicked() {
        fetchUpcomingLaunchesData()
    }

    fun getLaunchByName(name: String): LaunchEntity? {
        return (upcomingLaunches.value as State.Success<List<LaunchEntity>>).data.findLast {
            it.name == name
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