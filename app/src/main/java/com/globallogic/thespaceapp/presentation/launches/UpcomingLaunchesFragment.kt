package com.globallogic.thespaceapp.presentation.launches

import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.RequestManager
import com.globallogic.thespaceapp.databinding.FragmentLaunchesBinding
import com.globallogic.thespaceapp.domain.model.LaunchEntity
import com.globallogic.thespaceapp.utils.State
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class UpcomingLaunchesFragment : Fragment() {

    private var _binding: FragmentLaunchesBinding? = null
    private val binding get() = _binding!!

    private val viewModel: LaunchesViewModel by activityViewModels()

    @Inject
    lateinit var glide: RequestManager

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLaunchesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val launchesAdapter = LaunchesAdapter(glide, onItemClick = {
            findNavController().navigate(
                UpcomingLaunchesFragmentDirections.actionUpcomingLaunchesFragmentToLaunchDetailsFragment(
                    it.id
                )
            )
        })
        binding.rvLaunches.adapter = launchesAdapter

        when (resources.configuration.orientation) {
            Configuration.ORIENTATION_LANDSCAPE -> {
                binding.rvLaunches.layoutManager = GridLayoutManager(requireContext(), 2)
            }
            else -> {
                binding.rvLaunches.layoutManager = LinearLayoutManager(requireContext())
            }
        }
        viewModel.upcomingLaunches.observe(viewLifecycleOwner) { state ->
            when (state) {
                is State.Success<List<LaunchEntity>> -> {
                    launchesAdapter.launches = state.data
                    launchesAdapter.notifyDataSetChanged()
                    binding.srlLaunches.isRefreshing = false
                }
                is State.Error -> {
                    Snackbar.make(
                        binding.srlLaunches,
                        state.throwable.message ?: "Error!",
                        Snackbar.LENGTH_LONG
                    ).show()
                    binding.srlLaunches.isRefreshing = false
                }
                is State.Loading -> {
                    binding.srlLaunches.isRefreshing = true
                }
            }
        }
        binding.srlLaunches.setOnRefreshListener {
            viewModel.fetchUpcomingLaunchesData()
        }
    }
}