package com.globallogic.thespaceapp.presentation.starlinks

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.globallogic.thespaceapp.domain.model.StarlinkEntity
import com.globallogic.thespaceapp.domain.usecase.FetchStarlinksUseCase
import com.globallogic.thespaceapp.utils.Result
import com.globallogic.thespaceapp.utils.State
import com.globallogic.thespaceapp.utils.State.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StarlinksViewModel @Inject constructor(
    private val fetchStarlinksUseCase: FetchStarlinksUseCase
) : ViewModel() {

    private val _starlinks = MutableLiveData<State<List<StarlinkEntity>>>()
    val starlinks: LiveData<State<List<StarlinkEntity>>> = _starlinks

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
                    _starlinks.value = Success(result.data)
                }
                is Result.Error<*> -> {
                    _starlinks.value = Error(result.exception)
                }
            }
        }
    }
}