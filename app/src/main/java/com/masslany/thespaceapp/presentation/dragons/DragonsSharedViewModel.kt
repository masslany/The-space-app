package com.masslany.thespaceapp.presentation.dragons

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.masslany.thespaceapp.domain.model.DragonEntity
import com.masslany.thespaceapp.domain.usecase.FetchDragonsUseCase
import com.masslany.thespaceapp.utils.Result
import com.masslany.thespaceapp.utils.State
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DragonsSharedViewModel @Inject constructor(
    private val fetchDragonsUseCase: FetchDragonsUseCase
) : ViewModel() {

    private val _dragons = MutableLiveData<State<List<DragonEntity>>>()
    val dragons: LiveData<State<List<DragonEntity>>> = _dragons

    private val _dragon = MutableLiveData<State<DragonEntity>>()
    val dragon: LiveData<State<DragonEntity>> = _dragon


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

    fun getDragonById(id: String) {
        val dragon = (dragons.value as State.Success<List<DragonEntity>>).data.findLast {
            it.id == id
        }

        if (dragon != null) {
            _dragon.value = State.Success(dragon)
        } else {
            _dragon.value = State.Error(NullPointerException())
        }
    }
}