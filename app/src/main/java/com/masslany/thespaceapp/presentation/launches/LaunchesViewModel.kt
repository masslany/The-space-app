package com.masslany.thespaceapp.presentation.launches

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.masslany.thespaceapp.R
import com.masslany.thespaceapp.domain.model.LaunchModel
import com.masslany.thespaceapp.domain.usecase.FetchUpcomingLaunchesDataUseCase
import com.masslany.thespaceapp.utils.Result
import com.masslany.thespaceapp.utils.State
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LaunchesViewModel @Inject constructor(
    private val fetchUpcomingLaunchesDataUseCase: FetchUpcomingLaunchesDataUseCase
) : ViewModel() {

    private val _launches = MutableLiveData<State<List<LaunchAdapterItem>>>()
    val launches: LiveData<State<List<LaunchAdapterItem>>> = _launches

    private var allLaunches: List<LaunchModel> = emptyList()

    private var _query = MutableLiveData("")
    val query: LiveData<String> = _query

    private var _isSearchExpanded = MutableLiveData(false)
    val isSearchExpanded: LiveData<Boolean> = _isSearchExpanded

    init {
        fetchUpcomingLaunchesData()
    }

    fun onRetryClicked() {
        fetchUpcomingLaunchesData()
    }

    private fun convertLaunchesToAdapterItems(launchModels: List<LaunchModel>): List<LaunchAdapterItem> {
        val upcomingHeader = LaunchAdapterItem(R.id.item_recyclerview_header, "Upcoming", null)
        val pastHeader = LaunchAdapterItem(R.id.item_recyclerview_header, "Past", null)

        val (upcoming, past) = launchModels.partition {
            it.date >= System.currentTimeMillis() / 1000
        }

        val converted = mutableListOf<LaunchAdapterItem>()

        converted.add(upcomingHeader)
        if (upcoming.isNotEmpty()) {
            converted.addAll(
                upcoming.map {
                    LaunchAdapterItem(
                        type = R.id.item_recyclerview,
                        header = null,
                        launchModel = it
                    )
                })
        } else {
            converted.add(
                LaunchAdapterItem(
                    type = R.id.item_recyclerview_empty,
                    header = null,
                    launchModel = null
                )
            )
        }

        converted.add(pastHeader)
        if (past.isNotEmpty()) {
            converted.addAll(
                past.map {
                    LaunchAdapterItem(
                        type = R.id.item_recyclerview,
                        header = null,
                        launchModel = it
                    )
                }.reversed() // to sort most recent date
            )
        } else {
            converted.add(
                LaunchAdapterItem(
                    type = R.id.item_recyclerview_empty,
                    header = null,
                    launchModel = null
                )
            )
        }

        return converted
    }

    fun fetchUpcomingLaunchesData() {
        _launches.value = State.Loading

        viewModelScope.launch {
            when (val res = fetchUpcomingLaunchesDataUseCase.execute()) {
                is Result.Success -> {
                    val converted = convertLaunchesToAdapterItems(res.data)
                    allLaunches = res.data
                    _launches.value = State.Success(converted)
                }
                is Result.Error<*> -> {
                    _launches.value = State.Error(res.exception)
                }
            }
        }
    }

    fun onQueryTextChange() {
        if (launches.value is State.Success) {
            val filtered = allLaunches.filter { it.name.contains(query.value ?: "", ignoreCase = true) }
            val result = convertLaunchesToAdapterItems(filtered)
            _launches.value = State.Success(result)
        }
    }

    fun setSearchExpanded(expanded: Boolean) {
        _isSearchExpanded.value = expanded
    }

    fun setQuery(query: String) {
        _query.value = query
    }
}