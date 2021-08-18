package com.masslany.thespaceapp.presentation.launches

import androidx.annotation.IdRes
import com.masslany.thespaceapp.domain.model.LaunchModel

data class LaunchAdapterItem(
    @IdRes val type: Int,
    val header: String?,
    val launchModel: LaunchModel?
)