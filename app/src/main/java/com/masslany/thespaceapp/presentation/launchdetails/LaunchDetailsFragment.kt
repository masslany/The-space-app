package com.masslany.thespaceapp.presentation.launchdetails

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.core.content.res.ResourcesCompat
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.RequestManager
import com.google.android.material.snackbar.Snackbar
import com.masslany.thespaceapp.R
import com.masslany.thespaceapp.databinding.FragmentLaunchDetailsBinding
import com.masslany.thespaceapp.di.DefaultDispatcher
import com.masslany.thespaceapp.domain.model.LaunchModel
import com.masslany.thespaceapp.utils.*
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import javax.inject.Inject

@AndroidEntryPoint
class LaunchDetailsFragment : Fragment() {

    private var _binding: FragmentLaunchDetailsBinding? = null
    private val binding get() = _binding!!

    private val args: LaunchDetailsFragmentArgs by navArgs()

    private val viewModel: LaunchDetailsViewModel by viewModels()

    @Inject
    @DefaultDispatcher
    lateinit var defaultDispatcher: CoroutineDispatcher

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
        setHasOptionsMenu(true)

        viewModel.launch.observe(viewLifecycleOwner) { state ->
            when (state) {
                is State.Success -> {
                    binding.errorLayout.errorConstraintLayout.makeGone()
                    binding.mlLaunchDetails?.makeVisible()
                    binding.clContent?.makeVisible()
                    binding.progressIndicator.makeGone()
                    fillUi(state.data)
                }
                State.Loading -> {
                    binding.errorLayout.errorConstraintLayout.makeGone()
                    binding.clContent?.makeGone()
                    binding.mlLaunchDetails?.makeGone()
                    binding.progressIndicator.makeVisible()
                }
                is State.Error -> {
                    binding.errorLayout.errorConstraintLayout.makeVisible()
                    binding.errorLayout.btnRetry.makeVisible()
                    binding.mlLaunchDetails?.makeGone()
                    binding.clContent?.makeGone()
                    binding.progressIndicator.makeGone()

                    binding.errorLayout.btnRetry.setOnClickListener {
                        viewModel.onRetryClicked(args.launchId)
                    }
                }
            }
        }

        viewModel.getLaunchById(args.launchId)
    }

    private fun fillUi(launchModel: LaunchModel) {
        viewModel.fetchNotificationState(launchModel)
        activity?.invalidateOptionsMenu()

        binding.tvLaunchDetailsHeadline.text = launchModel.name
        binding.tvLaunchDetailsHeadline.isSelected = true // to enable marquee
        binding.tvLaunchDetailsDate.text = launchModel.date.toDateSting()

        // Countdown
        lifecycleScope.launch(defaultDispatcher) {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                while (true) {
                    withContext(Dispatchers.Main) {
                        binding.tvLaunchDetailsCountdown.text =
                            launchModel.date.toCountdownString()
                    }
                    delay(1000)
                }
            }
        }

        launchModel.details?.let { details ->
            binding.tvLaunchDetailsDescription.text = details
            binding.cvLaunchDetailsDescription.makeVisible()
        }


        launchModel.article?.let { uri ->
            binding.tvArticleLabel.enable()
            binding.tvArticleLabel.setOnClickListener {
                startActivity(Intent(Intent.ACTION_VIEW, uri))
            }
        }
        launchModel.webcast?.let { uri ->
            binding.ivWebcast.enable()
            binding.ivWebcast.setOnClickListener {
                startActivity(Intent(Intent.ACTION_VIEW, uri))
            }
        }
        launchModel.wikipedia?.let { uri ->
            binding.ivWikipedia.enable()
            binding.ivWikipedia.setOnClickListener {
                startActivity(Intent(Intent.ACTION_VIEW, uri))
            }
        }


        launchModel.rocketId?.let { rocketId ->
            binding.cardRocketInfo.cvRocket.makeVisible()

            binding.cardRocketInfo.cvRocket.setOnClickListener {
                findNavController().navigate("spaceapp://rocketDetails/${rocketId}".toUri())
            }
        }

        launchModel.launchpadId?.let { launchpadId ->
            binding.cardLaunchpadInfo.cvLaunchpad.makeVisible()

            // TODO: Navigate to LaunchpadDetailsFragment
            binding.cardLaunchpadInfo.cvLaunchpad.setOnClickListener {
                Snackbar.make(
                    requireContext(),
                    binding.cardLaunchpadInfo.cvLaunchpad,
                    getString(R.string.not_implemented),
                    Snackbar.LENGTH_SHORT
                ).show()
            }
        }

        if (launchModel.payloadsIds.isNotEmpty()) {
            binding.cardPayloadInfo.cvPayload.makeVisible()

            // TODO: Navigate to LaunchpadDetailsFragment
            binding.cardPayloadInfo.cvPayload.setOnClickListener {
                Snackbar.make(
                    requireContext(),
                    binding.cardPayloadInfo.cvPayload,
                    getString(R.string.not_implemented),
                    Snackbar.LENGTH_SHORT
                ).show()
            }
        }

        glide
            .load(launchModel.image)
            .placeholder(R.drawable.ic_launch_placeholder)
            .into(binding.ivLaunchDetails)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_notification -> {
                viewModel.onNotificationToggleClicked()
                true
            }
            else -> {
                super.onOptionsItemSelected(item)
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.launch_details_menu, menu)

        viewModel.notificationState.observe(viewLifecycleOwner) { state ->
            when (state) {
                true -> {
                    menu.findItem(R.id.action_notification)?.icon = ResourcesCompat.getDrawable(
                        resources, R.drawable.ic_outline_notifications_off_24, null
                    )
                }
                false -> {
                    menu.findItem(R.id.action_notification)?.icon = ResourcesCompat.getDrawable(
                        resources, R.drawable.ic_outline_notifications_none_24, null
                    )
                }
            }
        }

        viewModel.shouldShowNotificationToggle.observe(viewLifecycleOwner) { should ->
            when (should) {
                true -> {
                    setMenuVisibility(true)
                }
                false -> {
                    setMenuVisibility(false)
                }
            }
        }
    }
}