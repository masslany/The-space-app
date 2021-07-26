package com.globallogic.thespaceapp.presentation.dragons

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.globallogic.thespaceapp.domain.model.DragonEntity
import com.globallogic.thespaceapp.domain.usecase.FetchDragonsUseCase
import com.globallogic.thespaceapp.utils.Result
import com.globallogic.thespaceapp.utils.State
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DragonsSharedViewModel @Inject constructor(
    private val fetchDragonsUseCase: FetchDragonsUseCase
) : ViewModel() {

    private val _dragons = MutableLiveData<State<List<DragonEntity>>>()
    val dragons: LiveData<State<List<DragonEntity>>> = _dragons

    init {
        fetchDragons()
    }

    fun onRetryClicked() {
        fetchDragons()
    }

    private fun fetchDragons() {
        _dragons.value = State.Loading

        viewModelScope.launch {
            when (val res = fetchDragonsUseCase.execute()) {
                is Result.Success -> {
                    _dragons.value = State.Success(res.data)
                }
                is Result.Error<*> -> {
                    _dragons.value = State.Error(res.exception)
                }
            }
        }
    }

    fun getDragonById(id: String): DragonEntity? {
        Log.e("TAG", dragons.value.toString())
        return (dragons.value as State.Success<List<DragonEntity>>).data.findLast {
            it.id == id
        }
    }
}