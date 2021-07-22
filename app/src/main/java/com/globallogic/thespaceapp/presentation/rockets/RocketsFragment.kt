package com.globallogic.thespaceapp.presentation.rockets

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.RequestManager
import com.globallogic.thespaceapp.databinding.FragmentRocketsBinding
import com.globallogic.thespaceapp.utils.State
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class RocketsFragment : Fragment() {

    private var _binding: FragmentRocketsBinding? = null
    private val binding get() = _binding!!

    private val viewModel: RocketsSharedViewModel by activityViewModels()

    @Inject
    lateinit var glide: RequestManager

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

        val adapter = RocketsAdapter(glide)
        binding.rvRockets.adapter = adapter
        binding.rvRockets.layoutManager = LinearLayoutManager(requireContext())

        viewModel.rockets.observe(viewLifecycleOwner) { state ->
            println(state)
            when (state) {
                State.Loading -> {
                    binding.srlLaunches.isRefreshing = true
                }
                is State.Success -> {
                    adapter.rockets = state.data
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
            }
        }

        binding.srlLaunches.setOnRefreshListener {
            viewModel.onRetryClicked()
        }
    }
}