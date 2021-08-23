package com.masslany.thespaceapp.presentation.launches

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.google.common.truth.Truth.assertThat
import com.masslany.thespaceapp.data.local.cache.entities.toLaunchEntity
import com.masslany.thespaceapp.domain.model.LaunchModel
import com.masslany.thespaceapp.domain.repository.LaunchesRepository
import com.masslany.thespaceapp.domain.usecase.FetchLaunchesDataUseCase
import com.masslany.thespaceapp.utils.MainCoroutineRule
import com.masslany.thespaceapp.utils.Resource
import com.masslany.thespaceapp.utils.State
import com.masslany.thespaceapp.utils.getOrAwaitValue
import io.mockk.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.util.*


@ExperimentalCoroutinesApi
class LaunchesViewModelTest {

    @Rule
    @JvmField
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Rule
    @JvmField
    var coroutineRule = MainCoroutineRule()

    private lateinit var viewModel: LaunchesViewModel
    private val repository: LaunchesRepository = mockk()
    private val list = arrayListOf<State<List<LaunchAdapterItem>>>()


    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        coEvery { repository.getCachedLaunches() } returns flowOf(emptyList())
        coEvery { repository.fetchLaunchesData() } returns Resource.Success(emptyList())
        coEvery { repository.saveFetchedLaunches(emptyList()) } returns Unit
        viewModel = LaunchesViewModel(
            FetchLaunchesDataUseCase(repository)
        )

        //create mockk object
        val observer = mockk<Observer<State<List<LaunchAdapterItem>>>>()
        //create slot
        val slot = slot<State<List<LaunchAdapterItem>>>()
        //create list to store values

        //capture value on every call
        every { observer.onChanged(capture(slot)) } answers {
            //store captured value
            list.add(slot.captured)
        }
        //start observing
        viewModel.launches.observeForever(observer)
    }

    @Test
    fun emptyResponseShouldContainsFourItems() = runBlockingTest {
        viewModel.fetchUpcomingLaunchesData(true)

        assertThat(viewModel.launches.getOrAwaitValue()).isInstanceOf(State.Success::class.java)
        assertThat((list.last() as State.Success).data.size).isEqualTo(4)
    }

    @Test
    fun successResponseShouldContainItem() = runBlockingTest {
        val item = LaunchModel(
            id = UUID.randomUUID().toString(), "Test", null, 123, null,
            null, null, emptyList(), emptyList(), emptyList(),
            null, null, null
        )
        coEvery { repository.fetchLaunchesData() } returns Resource.Success(listOf(item))
        coEvery { repository.saveFetchedLaunches(listOf(toLaunchEntity(item))) } returns Unit

        viewModel.fetchUpcomingLaunchesData(true)

        assertThat(viewModel.launches.getOrAwaitValue()).isInstanceOf(State.Success::class.java)
        println(list)
        assertThat((list.last() as State.Success).data.size).isEqualTo(5)
        assertThat((list.last() as State.Success).data).contains(item.name)
    }
}