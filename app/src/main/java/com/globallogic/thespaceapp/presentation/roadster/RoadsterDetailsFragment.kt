package com.globallogic.thespaceapp.presentation.roadster

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bumptech.glide.RequestManager
import com.globallogic.thespaceapp.databinding.FragmentRoadsterDetailsBinding
import com.globallogic.thespaceapp.utils.makeGone
import com.globallogic.thespaceapp.utils.makeVisible
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class RoadsterDetailsFragment : Fragment() {

    private var _binding: FragmentRoadsterDetailsBinding? = null
    private val binding get() = _binding!!

    private val viewModel: RoadsterDetailsViewModel by viewModels()

    @Inject
    lateinit var glide: RequestManager

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRoadsterDetailsBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.roadsterEntity.observe(viewLifecycleOwner) { state ->
            when (state) {
                is RoadsterDetailsViewModel.RoadsterDetailsState.Error -> {
                    with(binding) {
                        shimmerViewContainer.makeGone()
                        shimmerViewContainer.stopShimmer()
                        tvError.makeVisible()
                        btnRetry.makeVisible()
                    }
                }
                RoadsterDetailsViewModel.RoadsterDetailsState.Loading -> {
                    with(binding) {
                        ivRoadster.makeGone()
                        nestedScrollView.makeGone()
                        detailsLayout.makeGone()

                        binding.tvError.makeGone()
                        binding.btnRetry.makeGone()

                        shimmerViewContainer.makeVisible()
                        shimmerViewContainer.startShimmer()
                    }
                }
                is RoadsterDetailsViewModel.RoadsterDetailsState.Success -> {
                    with(binding) {
                        ivRoadster.makeVisible()
                        nestedScrollView.makeVisible()
                        detailsLayout.makeVisible()

                        shimmerViewContainer.makeGone()
                        shimmerViewContainer.stopShimmer()

                        binding.tvError.makeGone()
                        binding.btnRetry.makeGone()

                        tvName.text = state.data.name
                        tvLaunchDate.text = state.data.launchDate
                        tvSpeed.text = state.data.speed
                        tvEarthDistance.text = state.data.distanceFromEarth
                        tvMarsDistance.text = state.data.distanceFromMars
                        tvDescription.text = state.data.description
                        glide.load(state.data.image).into(ivRoadster)
                    }
                }
            }
        }

        binding.btnRetry.setOnClickListener {
            viewModel.onRetryClicked()
        }
    }

    override fun onPause() {
        super.onPause()
        binding.shimmerViewContainer.stopShimmer()
    }

    override fun onStop() {
        super.onStop()
        binding.shimmerViewContainer.stopShimmer()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding.shimmerViewContainer.stopShimmer()
        _binding = null
    }
}