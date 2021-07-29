package com.globallogic.thespaceapp.presentation.launches

import androidx.annotation.IdRes
import com.globallogic.thespaceapp.domain.model.LaunchEntity

data class LaunchAdapterItem(
    @IdRes val type: Int,
    val header: String?,
    val launchEntity: LaunchEntity?
)