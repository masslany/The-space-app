package com.masslany.thespaceapp.presentation.dragons

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.masslany.thespaceapp.domain.model.DragonModel
import com.masslany.thespaceapp.domain.usecase.FetchDragonsUseCase
import com.masslany.thespaceapp.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DragonsSharedViewModel @Inject constructor(
    private val fetchDragonsUseCase: FetchDragonsUseCase
) : ViewModel() {

    private val _dragons = MutableLiveData<Resource<List<DragonModel>>>()
    val dragons: LiveData<Resource<List<DragonModel>>> = _dragons

    private val _dragon = MutableLiveData<Resource<DragonModel>>()
    val dragon: LiveData<Resource<DragonModel>> = _dragon


    init {
        fetchDragons()
    }

    fun onRetryClicked() {
        fetchDragons()
    }

    private fun fetchDragons() {
        _dragons.value = Resource.Loading

        viewModelScope.launch {
            _dragons.value = fetchDragonsUseCase.execute()
        }
    }

    fun getDragonById(id: String) {
        val dragon = (dragons.value as Resource.Success<List<DragonModel>>).data.findLast {
            it.id == id
        }

        if (dragon != null) {
            _dragon.value = Resource.Success(dragon)
        } else {
            _dragon.value = Resource.Error(NullPointerException())
        }
    }
}