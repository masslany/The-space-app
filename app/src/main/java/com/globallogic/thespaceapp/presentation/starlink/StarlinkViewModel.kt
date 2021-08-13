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

    private val _markersMap = MutableLiveData<Map<String, StarlinkMarker?>>()
    val markersMap: LiveData<Map<String, StarlinkMarker?>> = _markersMap

    private val starlinkEntities: MutableList<StarlinkEntity> = mutableListOf()

    init {
        fetchStarlinks()
    }

    fun onRetryClicked() {
        fetchStarlinks()
    }

    private fun fetchStarlinks() = viewModelScope.launch() {
        _starlinks.value = Loading

        when (val result = fetchStarlinksUseCase.execute()) {
            is Result.Success -> {
                starlinkEntities.clear()
                starlinkEntities.addAll(result.data)

                convertToStarlinkMarkerMap(result.data)
            }
            is Result.Error<*> -> {
                _starlinks.value = Error(result.exception)
            }
        }
    }


    private fun convertToStarlinkMarkerMap(data: List<StarlinkEntity>) =
        viewModelScope.launch(defaultDispatcher) {
            val tempMap = mutableMapOf<String, StarlinkMarker>()

            data.forEach { starlink ->
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
            _markersMap.postValue(tempMap)
        }

    fun predictPosition() = viewModelScope.launch(defaultDispatcher) {
        while (true) {
            val markers = mutableListOf<StarlinkMarker>()
            starlinkEntities.forEach { starlink ->
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
            delay(300L)
        }
    }
}