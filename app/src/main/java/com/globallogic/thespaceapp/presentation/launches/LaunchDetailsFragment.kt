package com.globallogic.thespaceapp.presentation.launches

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.RequestManager
import com.globallogic.thespaceapp.R
import com.globallogic.thespaceapp.databinding.FragmentLaunchDetailsBinding
import com.globallogic.thespaceapp.domain.model.LaunchEntity
import com.globallogic.thespaceapp.utils.makeVisible
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class LaunchDetailsFragment : Fragment() {

    private var _binding: FragmentLaunchDetailsBinding? = null
    private val binding get() = _binding!!

    private val args: LaunchDetailsFragmentArgs by navArgs()

    private val viewModel: LaunchesSharedViewModel by activityViewModels()

    @Inject
    lateinit var glide: RequestManager

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLaunchDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val launchEntity = viewModel.getLaunchByName(args.launchName)
        launchEntity?.let {
            fillUi(launchEntity)
        } ?: run {
            throw RuntimeException("LaunchEntity is null in LaunchDetailsFragment!")
        }

    }

    private fun fillUi(launchEntity: LaunchEntity) {
        binding.tvLaunchDetailsHeadline.text = launchEntity.name
        binding.tvLaunchDetailsDate.text = launchEntity.date

//        TODO: Implement countdown
//        binding.tvLaunchDetailsCountdown.text =

        launchEntity.details?.let { details ->
            binding.tvLaunchDetailsDescription.text = details
            binding.cvLaunchDetailsDescription.makeVisible()
        }

        launchEntity.rocketId?.let { rocketId ->
            binding.cardRocketInfo.cvRocket.makeVisible()
//            TODO: Fill with rocket info
//            binding.cardRocketInfo.tvRocketName.text = rocketId
        }

        launchEntity.launchpadId?.let { launchpadId ->
            binding.cardLaunchpadInfo.cvLaunchpad.makeVisible()
//            TODO: Fill with launchpad info
//            binding.cardLaunchpadInfo.tvLaunchpadName.text = launchpadId
        }

        if (launchEntity.payloadsIds.isNotEmpty()) {
            binding.cardPayloadInfo.cvPayload.makeVisible()
//            TODO: Fill with payloads info
//            binding.cardPayloadInfo.tvPayloadName.text = launchEntity.payloadsIds.first()
        }

        glide
            .load(launchEntity.image)
            .placeholder(R.drawable.ic_launch_placeholder)
            .into(binding.ivLaunchDetails)
    }
}