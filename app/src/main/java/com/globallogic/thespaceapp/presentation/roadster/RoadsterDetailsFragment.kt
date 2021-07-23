package com.globallogic.thespaceapp.presentation.roadster

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bumptech.glide.RequestManager
import com.globallogic.thespaceapp.R
import com.globallogic.thespaceapp.databinding.FragmentRoadsterDetailsBinding
import com.globallogic.thespaceapp.utils.State.*
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
                is Error -> {
                    with(binding) {
                        lottieLoading.makeGone()

                        tvError.makeVisible()
                        btnRetry.makeVisible()
                        errorLayout.makeVisible()
                    }
                }
                Loading -> {
                    with(binding) {
                        ivRoadster.makeGone()
                        nestedScrollView.makeGone()
                        detailsLayout.makeGone()

                        binding.tvError.makeGone()
                        binding.btnRetry.makeGone()
                        errorLayout.makeGone()


                        lottieLoading.makeVisible()
                    }
                }
                is Success -> {
                    with(binding) {
                        ivRoadster.makeVisible()
                        nestedScrollView.makeVisible()
                        detailsLayout.makeVisible()

                        lottieLoading.makeGone()

                        tvError.makeGone()
                        btnRetry.makeGone()
                        errorLayout.makeGone()

                        tvName.text = state.data.name
                        tvLaunchDate.text = state.data.launchDate
                        tvSpeed.text = getString(R.string.speed_per_hour, state.data.speed)
                        tvEarthDistance.text =
                            getString(R.string.distance, state.data.distanceFromEarth)
                        tvMarsDistance.text =
                            getString(R.string.distance, state.data.distanceFromMars)
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}