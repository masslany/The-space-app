package com.masslany.thespaceapp.presentation.starlink

import androidx.lifecycle.*
import com.masslany.thespaceapp.di.DefaultDispatcher
import com.masslany.thespaceapp.domain.model.StarlinkModel
import com.masslany.thespaceapp.domain.usecase.FetchStarlinksUseCase
import com.masslany.thespaceapp.domain.usecase.GetStarlinkPreferencesUseCase
import com.masslany.thespaceapp.domain.usecase.UpdateStarlinkPreferencesUseCase
import com.masslany.thespaceapp.utils.Resource
import com.masslany.thespaceapp.utils.State
import com.masslany.thespaceapp.utils.State.Loading
import com.masslany.thespaceapp.utils.State.Success
import com.neosensory.tlepredictionengine.TlePredictionEngine
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.math.PI
import kotlin.math.tan

@HiltViewModel
class StarlinkViewModel @Inject constructor(
    private val fetchStarlinksUseCase: FetchStarlinksUseCase,
    getStarlinkPreferencesUseCase: GetStarlinkPreferencesUseCase,
    private val updateStarlinkPreferencesUseCase: UpdateStarlinkPreferencesUseCase,
    @DefaultDispatcher val defaultDispatcher: CoroutineDispatcher
) : ViewModel() {

    private val _starlinks = MutableLiveData<State<List<StarlinkMarker>>>()
    val starlinks: LiveData<State<List<StarlinkMarker>>> = _starlinks

    private val _markersMap = MutableLiveData<Map<String, StarlinkMarker?>>()
    val markersMap: LiveData<Map<String, StarlinkMarker?>> = _markersMap

    val settings = getStarlinkPreferencesUseCase.execute().asLiveData()

    private val starlinkModels: MutableList<StarlinkModel> = mutableListOf()

    init {
        fetchStarlinks(false)
    }

    fun onRetryClicked() {
        fetchStarlinks(true)
    }


    private fun fetchStarlinks(forceRefresh: Boolean) = viewModelScope.launch() {

        fetchStarlinksUseCase.execute(
            forceRefresh = forceRefresh,
            onFetchSuccess = {
                starlinkModels.clear()
            },
            onFetchFailed = {
                _starlinks.value = State.Error(it)
            }
        ).collect { resource ->
            when (resource) {
                Resource.Loading -> {
                    _starlinks.value = Loading
                }
                is Resource.Success -> {
                    starlinkModels.clear()
                    starlinkModels.addAll(resource.data)
                    convertToStarlinkMarkerMap(resource.data)
                }
                is Resource.Error -> {
                    _starlinks.value = State.Error(resource.throwable)
                }
            }
        }


//        when (val result = fetchStarlinksUseCase.execute()) {
//            is Result.Success -> {
//                starlinkModels.clear()
//                starlinkModels.addAll(result.data)
//
//                convertToStarlinkMarkerMap(result.data)
//            }
//            is Result.Error<*> -> {
//                _starlinks.value = Error(result.exception)
//            }
//        }
    }

    private fun convertToStarlinkMarkerMap(data: List<StarlinkModel>) =
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
                        id = starlink.id,
                        objectName = starlink.objectName,
                        launchDate = starlink.launchDate,
                        latitude = predicted[0],
                        longitude = predicted[1],
                        altitude = predicted[2]
                    )
            }
            _markersMap.postValue(tempMap)
        }

    fun predictPosition() = viewModelScope.launch(defaultDispatcher) {
        while (true) {
            calculatePosition()
            delay(2000L)
        }
    }

    fun calculatePosition() {
        val markers = mutableListOf<StarlinkMarker>()
        starlinkModels.forEach { starlink ->
            val predicted = TlePredictionEngine.getSatellitePosition(
                starlink.TLELine1,
                starlink.TLELine2,
                true
            )
            markers.add(
                StarlinkMarker(
                    id = starlink.id,
                    objectName = starlink.objectName,
                    launchDate = starlink.launchDate,
                    latitude = predicted[0],
                    longitude = predicted[1],
                    altitude = predicted[2]
                )
            )
        }
        _starlinks.postValue(Success(markers))
    }

    fun onSliderChanged(value: Float) {
        val newSettings = settings.value?.copy(degrees = value.toDouble()) ?: return

        viewModelScope.launch {
            updateStarlinkPreferencesUseCase.execute(newSettings)
        }
    }

    fun onShowCoverageClicked(showCoverage: Boolean) {
        val newSettings = settings.value?.copy(showCoverage = showCoverage) ?: return

        viewModelScope.launch {
            updateStarlinkPreferencesUseCase.execute(newSettings)
        }
    }

    fun calculateRadius(angle: Double, altitude: Double): Double {
        // Assuming the earth is flat 👀

        val epsilon = degToRad(angle)
        val areaInKm = altitude / tan(epsilon)

        return areaInKm * 1000
    }

    private fun degToRad(degrees: Double): Double {
        return degrees / 180 * PI
    }
}