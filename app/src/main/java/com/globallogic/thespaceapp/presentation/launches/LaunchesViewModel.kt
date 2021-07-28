package com.globallogic.thespaceapp.presentation.launches

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.globallogic.thespaceapp.R
import com.globallogic.thespaceapp.domain.model.LaunchEntity
import com.globallogic.thespaceapp.domain.usecase.FetchUpcomingLaunchesDataUseCase
import com.globallogic.thespaceapp.utils.Result
import com.globallogic.thespaceapp.utils.State
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LaunchesViewModel @Inject constructor(
    private val fetchUpcomingLaunchesDataUseCase: FetchUpcomingLaunchesDataUseCase
) : ViewModel() {

    private val _launches = MutableLiveData<State<List<LaunchAdapterItem>>>()
    val launches: LiveData<State<List<LaunchAdapterItem>>> = _launches

    init {
        fetchUpcomingLaunchesData()
    }

    fun onRetryClicked() {
        fetchUpcomingLaunchesData()
    }

    private fun convertLaunchesToAdapterItems(launchEntities: List<LaunchEntity>): List<LaunchAdapterItem> {
        val upcomingHeader = LaunchAdapterItem(R.id.item_recyclerview_header, "Upcoming", null)
        val pastHeader = LaunchAdapterItem(R.id.item_recyclerview_header, "Past", null)

        val (upcoming, past) = launchEntities.partition {
            it.date >= System.currentTimeMillis() / 1000
        }

        val converted = mutableListOf<LaunchAdapterItem>()

        converted.add(upcomingHeader)
        converted.addAll(
            upcoming.map {
                LaunchAdapterItem(
                    type = R.id.item_recyclerview,
                    header = null,
                    launchEntity = it
                )
            })
        converted.add(pastHeader)
        converted.addAll(
            past.map {
                LaunchAdapterItem(
                    type = R.id.item_recyclerview,
                    header = null,
                    launchEntity = it
                )
            }.reversed() // to sort most recent date
        )

        return converted
    }

    fun fetchUpcomingLaunchesData() {
        _launches.value = State.Loading

        viewModelScope.launch {
            when (val res = fetchUpcomingLaunchesDataUseCase.execute()) {
                is Result.Success -> {
                    val converted = convertLaunchesToAdapterItems(res.data)
                    _launches.value = State.Success(converted)
                }
                is Result.Error<*> -> {
                    _launches.value = State.Error(res.exception)
                }
            }
        }
    }
}