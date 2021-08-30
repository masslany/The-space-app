package com.masslany.thespaceapp.presentation.rockets

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.RequestManager
import com.masslany.thespaceapp.R
import com.masslany.thespaceapp.databinding.FragmentRocketDetailsBinding
import com.masslany.thespaceapp.utils.Resource
import com.masslany.thespaceapp.utils.makeGone
import com.masslany.thespaceapp.utils.makeVisible
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class RocketDetailsFragment : Fragment() {

    private var _binding: FragmentRocketDetailsBinding? = null
    private val binding get() = _binding!!

    private val args: RocketDetailsFragmentArgs by navArgs()

    private val viewModel: RocketDetailsViewModel by viewModels()

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

        viewModel.fetchRocketById(args.rocketId)
    }

    private fun observeUi() {
        viewModel.rocket.observe(viewLifecycleOwner) { state ->
            when (state) {
                is Resource.Error -> {
                    binding.mlRocketDetails?.makeGone()
                    binding.clContent?.makeGone()
                    binding.errorLayout.errorConstraintLayout.makeVisible()
                    binding.errorLayout.btnRetry.makeVisible()
                    binding.progressIndicator.makeGone()
                }

                Resource.Loading -> {
                    binding.mlRocketDetails?.makeGone()
                    binding.clContent?.makeGone()
                    binding.errorLayout.errorConstraintLayout.makeGone()
                    binding.progressIndicator.makeVisible()
                }

                is Resource.Success -> {
                    binding.errorLayout.errorConstraintLayout.makeGone()
                    binding.mlRocketDetails?.makeVisible()
                    binding.clContent?.makeVisible()
                    binding.progressIndicator.makeGone()

                    val rocketInfo = state.data
                    with(binding) {

                        // Top part
                        tvRocketDetailsHeadline.text = rocketInfo.name
                        tvRocketDetailsType.text = rocketInfo.type.uppercase()
                        tvRocketDetailsDescription.text = rocketInfo.description

                        glide
                            .load(rocketInfo.flickrImages.first())
                            .into(ivRocketDetails)

                        // Rocket size card
                        cardRocketSize.tvHeight.text =
                            getString(R.string.height, rocketInfo.height.meters.toString())
                        cardRocketSize.tvDiameter.text =
                            getString(R.string.diameter, rocketInfo.diameter.meters.toString())
                        cardRocketSize.tvMass.text =
                            getString(R.string.mass, rocketInfo.mass.kg.toString())

                        // First stage card
                        cardFirstStage.tvBurnTime.text =
                            getString(
                                R.string.burn_time,
                                rocketInfo.firstStage.burnTimeSec.toString()
                            )
                        cardFirstStage.tvEngines.text = rocketInfo.firstStage.engines.toString()
                        cardFirstStage.tvFuelAmount.text =
                            getString(
                                R.string.fuel_amount_tones,
                                rocketInfo.firstStage.fuelAmountTons.toString()
                            )
                        cardFirstStage.tvThrustSeaLevel.text =
                            getString(
                                R.string.thrust_sea_level,
                                rocketInfo.firstStage.thrustSeaLevel.kN.toString()
                            )
                        cardFirstStage.tvThrustVacuum.text =
                            getString(
                                R.string.thrust_vacuum,
                                rocketInfo.firstStage.thrustVacuum.kN.toString()
                            )

                        // Second stage card
                        cardSecondStage.tvBurnTime.text =
                            getString(
                                R.string.burn_time,
                                rocketInfo.secondStage.burnTimeSec.toString()
                            )
                        cardSecondStage.tvEngines.text = rocketInfo.secondStage.engines.toString()
                        cardSecondStage.tvFuelAmount.text =
                            getString(
                                R.string.fuel_amount_tones,
                                rocketInfo.secondStage.fuelAmountTons.toString()
                            )
                        cardSecondStage.tvThrust.text =
                            getString(
                                R.string.thrust_vacuum,
                                rocketInfo.secondStage.thrust.kN.toString()
                            )
                        cardSecondStage.tvPayload.text =
                            rocketInfo.secondStage.payloads.option1

                    }
                }
            }
        }

        binding.errorLayout.btnRetry.setOnClickListener {
            viewModel.onRetryClicked(args.rocketId)
        }
    }
}