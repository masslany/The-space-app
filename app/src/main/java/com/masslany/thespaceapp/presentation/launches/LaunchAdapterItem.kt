package com.masslany.thespaceapp.presentation.launches

import androidx.annotation.IdRes
import com.masslany.thespaceapp.domain.model.LaunchEntity

data class LaunchAdapterItem(
    @IdRes val type: Int,
    val header: String?,
    val launchEntity: LaunchEntity?
)