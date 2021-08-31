package com.masslany.thespaceapp.presentation.dragons

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth
import com.masslany.thespaceapp.data.remote.response.dragons.HeatShield
import com.masslany.thespaceapp.data.remote.response.dragons.PayloadInfo
import com.masslany.thespaceapp.data.remote.response.dragons.Thruster
import com.masslany.thespaceapp.domain.model.DragonModel
import com.masslany.thespaceapp.domain.repository.DragonsRepository
import com.masslany.thespaceapp.domain.usecase.FetchDragonsUseCase
import com.masslany.thespaceapp.utils.MainCoroutineRule
import com.masslany.thespaceapp.utils.Resource
import com.masslany.thespaceapp.utils.getOrAwaitValue
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.io.IOException

@ExperimentalCoroutinesApi
internal class DragonsSharedViewModelTest {
    @Rule
    @JvmField
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Rule
    @JvmField
    var coroutineRule = MainCoroutineRule()

    private lateinit var viewModel: DragonsSharedViewModel
    private val repository: DragonsRepository = mockk()

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
    }

    @Test
    fun emptyResponseShouldReturnSuccessWithEmptyList() {
        // Given
        val data = emptyList<DragonModel>()
        coEvery { repository.fetchDragonsData() } returns Resource.Success(data)
        viewModel = DragonsSharedViewModel(
            FetchDragonsUseCase(repository)
        )

        // Then
        val result = viewModel.dragons.getOrAwaitValue()
        Truth.assertThat(result).isInstanceOf(Resource.Success::class.java)
        Truth.assertThat((result as Resource.Success).data).isEqualTo(data)
    }

    @Test
    fun validResponseShouldReturnSuccessWithItems() {
        // Given
        val data = listOf(
            createDragonModel(name = "Dragon 1"),
            createDragonModel(name = "Dragon 2"),
            createDragonModel(name = "Dragon 3")
        )
        coEvery { repository.fetchDragonsData() } returns Resource.Success(data)
        viewModel = DragonsSharedViewModel(
            FetchDragonsUseCase(repository)
        )

        // Then
        val result = viewModel.dragons.getOrAwaitValue()
        Truth.assertThat(result).isInstanceOf(Resource.Success::class.java)
        Truth.assertThat((result as Resource.Success).data).isEqualTo(data)
    }

    @Test
    fun errorResponseShouldReturnErrorWithException() {
        val exception = IOException()
        coEvery { repository.fetchDragonsData() } returns Resource.Error(exception)
        viewModel = DragonsSharedViewModel(
            FetchDragonsUseCase(repository)
        )

        // Then
        val result = viewModel.dragons.getOrAwaitValue()
        Truth.assertThat(result).isInstanceOf(Resource.Error::class.java)
        Truth.assertThat((result as Resource.Error).throwable).isEqualTo(exception)
    }

    private fun createDragonModel(
        name: String = "Dragon #1",
        active: Boolean = true,
        crewCapacity: Int = 0,
        description: String = "",
        diameter: Double = 99.0,
        dryMass: Int = 1222,
        firstFlight: String = "12.10.2019",
        flickrImages: List<String> = emptyList(),
        id: String = "id1",
        wikipedia: String = "https://en.wikipedia.org/wiki/SpaceX_Dragon",
        heightWTrunk: Double = 33.2,
        payloadInfo: PayloadInfo = PayloadInfo(0, 0, 0, 0),
        heatShield: HeatShield = HeatShield("", "", 0.0, 0),
        thrusters: List<Thruster> = emptyList(),
    ) = DragonModel(
        name = name,
        active = active,
        crewCapacity = crewCapacity,
        description = description,
        diameter = diameter,
        dryMass = dryMass,
        firstFlight = firstFlight,
        flickrImages = flickrImages,
        id = id,
        wikipedia = wikipedia,
        heightWTrunk = heightWTrunk,
        payloadInfo = payloadInfo,
        heatShield = heatShield,
        thrusters = thrusters
    )
}