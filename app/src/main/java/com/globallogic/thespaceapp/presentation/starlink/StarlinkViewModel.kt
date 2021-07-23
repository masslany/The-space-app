package com.globallogic.thespaceapp.presentation.starlink

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.globallogic.thespaceapp.di.DefaultDispatcher
import com.globallogic.thespaceapp.domain.model.StarlinkEntity
import com.globallogic.thespaceapp.domain.usecase.FetchStarlinksUseCase
import com.globallogic.thespaceapp.utils.Result
import com.globallogic.thespaceapp.utils.State
import com.globallogic.thespaceapp.utils.State.*
import com.google.android.gms.maps.model.LatLng
import com.neosensory.tlepredictionengine.TlePredictionEngine
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StarlinkViewModel @Inject constructor(
    private val fetchStarlinksUseCase: FetchStarlinksUseCase,
    @DefaultDispatcher val defaultDispatcher: CoroutineDispatcher
) : ViewModel() {

    private val _starlinks = MutableLiveData<State<List<StarlinkMarker>>>()
    val starlinks: LiveData<State<List<StarlinkMarker>>> = _starlinks

    private val _starlinkEntities = MutableLiveData(emptyList<StarlinkEntity>())
    val starlinkEntities: LiveData<List<StarlinkEntity>> = _starlinkEntities

    private val _markersMap = MutableLiveData<Map<String, StarlinkMarker?>>()
    val markersMap: LiveData<Map<String, StarlinkMarker?>> = _markersMap

    init {
        fetchStarlinks()
    }

    fun onRetryClicked() {
        fetchStarlinks()
    }

    private fun fetchStarlinks() {
        _starlinks.value = Loading
        viewModelScope.launch {
            when (val result = fetchStarlinksUseCase.execute()) {
                is Result.Success -> {
                    _starlinkEntities.value = result.data
                    val tempMap = mutableMapOf<String, StarlinkMarker>()

                    result.data.forEach { starlink ->
                        val predicted = TlePredictionEngine.getSatellitePosition(
                            starlink.TLELine1,
                            starlink.TLELine2,
                            true
                        )
                        tempMap[starlink.id] =
                            StarlinkMarker(
                                latLong = LatLng(predicted[0], predicted[1]),
                                id = starlink.id,
                                objectName = starlink.objectName,
                                launchDate = starlink.launchDate,
                            )
                    }
                    _markersMap.value = tempMap
                }
                is Result.Error<*> -> {
                    _starlinks.value = Error(result.exception)
                }
            }
        }
    }

    fun predictPositionOnce(starlinks: List<StarlinkEntity>): List<StarlinkMarker> {
        val markers = mutableListOf<StarlinkMarker>()
        viewModelScope.launch(defaultDispatcher) {

        }
        return markers
    }

    fun predictPosition() = viewModelScope.launch(defaultDispatcher) {
        while (true) {
            val markers = mutableListOf<StarlinkMarker>()
            _starlinkEntities.value?.forEach { starlink ->
                val predicted = TlePredictionEngine.getSatellitePosition(
                    starlink.TLELine1,
                    starlink.TLELine2,
                    true
                )
                markers.add(
                    StarlinkMarker(
                        latLong = LatLng(predicted[0], predicted[1]),
                        id = starlink.id,
                        objectName = starlink.objectName,
                        launchDate = starlink.launchDate,
                    )
                )
            }
            _starlinks.postValue(Success(markers))
            delay(100L)
        }
    }
}