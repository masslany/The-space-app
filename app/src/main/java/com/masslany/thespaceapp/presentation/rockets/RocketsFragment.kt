package com.masslany.thespaceapp.presentation.rockets

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.RequestManager
import com.google.android.material.snackbar.Snackbar
import com.masslany.thespaceapp.R
import com.masslany.thespaceapp.databinding.FragmentRocketsBinding
import com.masslany.thespaceapp.domain.model.RocketModel
import com.masslany.thespaceapp.utils.Resource
import com.masslany.thespaceapp.utils.makeGone
import com.masslany.thespaceapp.utils.makeVisible
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class RocketsFragment : Fragment() {

    private var _binding: FragmentRocketsBinding? = null
    private val binding get() = _binding!!

    private val viewModel: RocketsViewModel by activityViewModels()

    @Inject
    lateinit var glide: RequestManager

    private lateinit var rocketsAdapter: RocketsAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRocketsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()

        setupObservers()

        setupListeners()
    }

    private fun onItemClicked(rocket: RocketModel) {
        findNavController().navigate(
            RocketsFragmentDirections.actionRocketsFragmentToRocketDetailsFragment(
                rocketId = rocket.id
            )
        )
    }

    private fun setupRecyclerView() {
        rocketsAdapter = RocketsAdapter(glide) { rocket ->
            onItemClicked(rocket)
        }
        binding.rvRockets.adapter = rocketsAdapter
        binding.rvRockets.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun setupObservers() {
        viewModel.rockets.observe(viewLifecycleOwner) { state ->
            when (state) {
                Resource.Loading -> {
                    binding.srlLaunches.isRefreshing = true
                    binding.errorLayout.errorConstraintLayout.makeGone()
                }
                is Resource.Success -> {
                    rocketsAdapter.submitList(state.data)
                    binding.rvRockets.makeVisible()

                    binding.srlLaunches.isRefreshing = false
                    binding.errorLayout.errorConstraintLayout.makeGone()
                }
                is Resource.Error -> {
                    val snackbar = Snackbar.make(
                        binding.srlLaunches,
                        state.throwable.message ?: getString(R.string.an_error_occurred),
                        Snackbar.LENGTH_LONG
                    )
                    snackbar.setAction(
                        R.string.retry
                    ) { viewModel.onRetryClicked() }
                    snackbar.show()

                    binding.srlLaunches.isRefreshing = false
                    binding.errorLayout.errorConstraintLayout.makeVisible()
                    binding.rvRockets.makeGone()
                }
            }
        }
    }

    private fun setupListeners() {
        binding.srlLaunches.setOnRefreshListener {
            viewModel.onRetryClicked()
        }
    }
}