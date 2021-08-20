package com.masslany.thespaceapp.presentation.launches

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.masslany.thespaceapp.R
import com.masslany.thespaceapp.domain.model.LaunchModel
import com.masslany.thespaceapp.domain.usecase.FetchLaunchesDataUseCase
import com.masslany.thespaceapp.utils.Resource
import com.masslany.thespaceapp.utils.State
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@ExperimentalCoroutinesApi
@HiltViewModel
class LaunchesViewModel @Inject constructor(
    private val fetchLaunchesDataUseCase: FetchLaunchesDataUseCase
) : ViewModel() {

    private val _launches = MutableLiveData<State<List<LaunchAdapterItem>>>()
    val launches: LiveData<State<List<LaunchAdapterItem>>> = _launches

    private var allLaunches: List<LaunchModel> = emptyList()

    private var _query = MutableLiveData("")
    val query: LiveData<String> = _query

    private var _isSearchExpanded = MutableLiveData(false)
    val isSearchExpanded: LiveData<Boolean> = _isSearchExpanded

    init {
        fetchUpcomingLaunchesData(false)
    }

    fun onRetryClicked() {
        fetchUpcomingLaunchesData(true)
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

    fun fetchUpcomingLaunchesData(forceRefresh: Boolean) {

        viewModelScope.launch {
            fetchLaunchesDataUseCase.execute(
                forceRefresh = forceRefresh,
                onFetchSuccess = {},
                onFetchFailed = {}
            ).stateIn(viewModelScope, SharingStarted.Lazily, Resource.Loading)
                .collect { resource ->
                    when (resource) {
                        Resource.Loading -> {
                            _launches.value = State.Loading
                        }
                        is Resource.Success -> {
                            val converted = convertLaunchesToAdapterItems(resource.data)
                            allLaunches = resource.data
                            _launches.value = State.Success(converted)
                        }

                        is Resource.Error -> {
                            _launches.value = State.Error(resource.throwable)
                        }
                    }
                }
        }
    }

    fun onQueryTextChange() {
        if (launches.value is State.Success) {
            val filtered =
                allLaunches.filter { it.name.contains(query.value ?: "", ignoreCase = true) }
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