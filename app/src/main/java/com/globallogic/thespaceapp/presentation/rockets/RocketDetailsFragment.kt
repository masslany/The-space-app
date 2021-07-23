package com.globallogic.thespaceapp.presentation.rockets

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.RequestManager
import com.globallogic.thespaceapp.databinding.FragmentRocketDetailsBinding
import com.globallogic.thespaceapp.utils.State
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class RocketDetailsFragment : Fragment() {

    private var _binding: FragmentRocketDetailsBinding? = null
    private val binding get() = _binding!!

    private val args: RocketDetailsFragmentArgs by navArgs()

    private val viewModel: RocketsSharedViewModel by activityViewModels()

    @Inject
    lateinit var glide: RequestManager

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRocketDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeUi()

        viewModel.getRocketById(args.rocketId)
    }

    private fun observeUi() {
        viewModel.rocket.observe(viewLifecycleOwner) { state ->
            when (state) {
                is State.Error -> {
                }
                State.Loading -> {
                }
                is State.Success -> {
                    val rocketInfo = state.data
                    with(binding) {

                        // Top part
                        tvRocketDetailsHeadline.text = rocketInfo.name
                        tvRocketDetailsType.text = rocketInfo.type.uppercase()
                        Log.d("TAG", "observeUi: ${rocketInfo.description}")
                        tvRocketDetailsDescription.text = rocketInfo.description

                        glide
                            .load(rocketInfo.flickrImages.first())
                            .into(ivRocketDetails)

                        // Rocket size card
                        cardRocketSize.tvHeight.text = rocketInfo.height.meters.toString()
                        cardRocketSize.tvDiameter.text = rocketInfo.diameter.meters.toString()
                        cardRocketSize.tvMass.text = rocketInfo.mass.kg.toString()

                    }
                }
            }
        }
    }
}