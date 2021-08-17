package com.masslany.thespaceapp.presentation.dragons

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.RequestManager
import com.masslany.thespaceapp.R
import com.masslany.thespaceapp.databinding.FragmentDragonDetailsBinding
import com.masslany.thespaceapp.utils.State
import com.masslany.thespaceapp.utils.makeGone
import com.masslany.thespaceapp.utils.makeVisible
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class DragonDetailsFragment : Fragment() {

    private var _binding: FragmentDragonDetailsBinding? = null
    private val binding get() = _binding!!
    private val args: DragonDetailsFragmentArgs by navArgs()

    private val viewModel: DragonsSharedViewModel by activityViewModels()

    @Inject
    lateinit var glide: RequestManager

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDragonDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeUi()

        viewModel.getDragonById(args.dragonId)
    }

    private fun observeUi() {
        viewModel.dragon.observe(viewLifecycleOwner) { state ->
            when (state) {
                is State.Error -> {
                    binding.errorLayout.errorConstraintLayout.makeVisible()
                }
                State.Loading -> {

                }
                is State.Success -> {
                    binding.errorLayout.errorConstraintLayout.makeGone()

                    val dragon = state.data

                    with(binding) {
                        tvDragonDetailsHeadline.text = dragon.name
                        tvDragonDetailsDescription.text = dragon.description
                        glide.load(dragon.flickrImages.first()).into(ivDragonDetails)

                        // Basic info card
                        cardInfo.tvFirstFlight.text = dragon.firstFlight
                        cardInfo.tvActive.text = dragon.active.toString()
                        cardInfo.tvCrewCapacity.text = dragon.crewCapacity.toString()

                        // Size info card
                        cardSize.tvHeight.text =
                            getString(R.string.height, dragon.heightWTrunk.toString())
                        cardSize.tvDiameter.text =
                            getString(R.string.diameter, dragon.diameter.toString())
                        cardSize.tvMass.text =
                            getString(R.string.mass, dragon.dryMass.toString())

                        // Heat shield info card
                        cardHeatShield.tvMaterial.text = dragon.heatShield.material
                        cardHeatShield.tvSize.text =
                            getString(R.string.size_meters, dragon.heatShield.sizeMeters.toString())
                        cardHeatShield.tvTemperature.text =
                            getString(
                                R.string.temp_degrees,
                                dragon.heatShield.tempDegrees.toString()
                            )
                        cardHeatShield.tvDevPartner.text = dragon.heatShield.devPartner

                        // Payload info card
                        cardPayload.tvLaunchMass.text =
                            getString(R.string.mass, dragon.payloadInfo.launchMass.toString())
                        cardPayload.tvLaunchVolume.text = getString(
                            R.string.volume_cubic_meters,
                            dragon.payloadInfo.launchVolume.toString()
                        )
                        cardPayload.tvReturnMass.text =
                            getString(R.string.mass, dragon.payloadInfo.returnMass.toString())
                        cardPayload.tvReturnVolume.text = getString(
                            R.string.volume_cubic_meters, dragon.payloadInfo.returnVolume.toString()
                        )
                    }
                }

            }
        }
    }
}