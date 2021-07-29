package com.globallogic.thespaceapp.presentation.roadster

import LinePagerIndicatorDecoration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
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

        val roadsterImagesAdapter = RoadsterImagesAdapter(glide)
        binding.rvRoadsterImages.adapter = roadsterImagesAdapter
        binding.rvRoadsterImages.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

        val snapHelper = PagerSnapHelper()
        snapHelper.attachToRecyclerView(binding.rvRoadsterImages)
        binding.rvRoadsterImages.addItemDecoration(LinePagerIndicatorDecoration())

        viewModel.roadsterEntity.observe(viewLifecycleOwner) { state ->
            when (state) {
                is Error -> {
                    with(binding) {
                        lottieLoading.makeGone()
                        errorLayout.errorConstraintLayout.makeVisible()
                        errorLayout.btnRetry.makeVisible()
                        mlContent?.makeGone()
                        clContent?.makeGone()
                    }
                }
                Loading -> {
                    with(binding) {
                        errorLayout.errorConstraintLayout.makeGone()

                        mlContent?.makeGone()
                        clContent?.makeGone()


                        lottieLoading.makeVisible()
                    }
                }
                is Success -> {
                    with(binding) {

                        clContent?.makeVisible()
                        mlContent?.makeVisible()

                        lottieLoading.makeGone()

                        errorLayout.errorConstraintLayout.makeGone()

                        tvName.text = state.data.name
                        tvLaunchDate.text = state.data.launchDate
                        tvSpeed.text = getString(R.string.speed_per_hour, state.data.speed)
                        tvEarthDistance.text =
                            getString(R.string.distance, state.data.distanceFromEarth)
                        tvMarsDistance.text =
                            getString(R.string.distance, state.data.distanceFromMars)
                        tvDescription.text = state.data.description

                        // TODO: fill recyclerview with data
                        roadsterImagesAdapter.images = state.data.images
                    }
                }
            }
        }
        binding.errorLayout.btnRetry.setOnClickListener {
            viewModel.onRetryClicked()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}