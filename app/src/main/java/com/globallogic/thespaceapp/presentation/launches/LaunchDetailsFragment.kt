package com.globallogic.thespaceapp.presentation.launches

import android.app.NotificationManager
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.RequestManager
import com.globallogic.thespaceapp.R
import com.globallogic.thespaceapp.databinding.FragmentLaunchDetailsBinding
import com.globallogic.thespaceapp.di.DefaultDispatcher
import com.globallogic.thespaceapp.domain.model.LaunchEntity
import com.globallogic.thespaceapp.presentation.notification.SpaceNotificationManager
import com.globallogic.thespaceapp.utils.enable
import com.globallogic.thespaceapp.utils.makeVisible
import com.globallogic.thespaceapp.utils.toCountdownString
import com.globallogic.thespaceapp.utils.toDateSting
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import javax.inject.Inject

@AndroidEntryPoint
class LaunchDetailsFragment : Fragment() {

    private var _binding: FragmentLaunchDetailsBinding? = null
    private val binding get() = _binding!!

    private val args: LaunchDetailsFragmentArgs by navArgs()

    private val viewModel: LaunchesSharedViewModel by activityViewModels()

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

        val launchEntity = viewModel.getLaunchByName(args.launchName)

        if (launchEntity != null) {
            fillUi(launchEntity)
        } else {
            throw RuntimeException("LaunchEntity is null in LaunchDetailsFragment!")
        }
    }

    private fun fillUi(launchEntity: LaunchEntity) {

        binding.btnFavourite.setOnClickListener {
//            viewModel.onRemainderClicked()
            val notification =
                SpaceNotificationManager(requireContext()).createNotification(launchEntity)

            val notificationManager =
                ContextCompat.getSystemService(
                    requireContext(),
                    NotificationManager::class.java
                ) as NotificationManager

            val intent = Intent()
            notificationManager.notify(launchEntity.name, 0, notification)
        }



        binding.tvLaunchDetailsHeadline.text = launchEntity.name
        binding.tvLaunchDetailsDate.text = launchEntity.date.toDateSting()

        // Countdown
        lifecycleScope.launch(defaultDispatcher) {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                while (true) {
                    withContext(Dispatchers.Main) {
                        binding.tvLaunchDetailsCountdown.text =
                            launchEntity.date.toCountdownString()
                    }
                    delay(1000)
                }
            }
        }

//        TODO: Do we need launchEntity.cores ?

        launchEntity.details?.let { details ->
            binding.tvLaunchDetailsDescription.text = details
            binding.cvLaunchDetailsDescription.makeVisible()
        }


        launchEntity.article?.let { uri ->
            binding.tvArticleLabel.enable()
            binding.tvArticleLabel.setOnClickListener {
                startActivity(Intent(Intent.ACTION_VIEW, uri))
            }
        }
        launchEntity.webcast?.let { uri ->
            binding.ivWebcast.enable()
            binding.ivWebcast.setOnClickListener {
                startActivity(Intent(Intent.ACTION_VIEW, uri))
            }
        }
        launchEntity.wikipedia?.let { uri ->
            binding.ivWikipedia.enable()
            binding.ivWikipedia.setOnClickListener {
                startActivity(Intent(Intent.ACTION_VIEW, uri))
            }
        }


        launchEntity.rocketId?.let { rocketId ->
            binding.cardRocketInfo.cvRocket.makeVisible()
            // TODO: Navigate to RocketDetailsFragment
        }

        launchEntity.launchpadId?.let { launchpadId ->
            binding.cardLaunchpadInfo.cvLaunchpad.makeVisible()
            // TODO: Navigate to LaunchpadDetailsFragment
        }

        if (launchEntity.payloadsIds.isNotEmpty()) {
            binding.cardPayloadInfo.cvPayload.makeVisible()
            // TODO: Navigate to PayloadDetailsFragment
        }

        glide
            .load(launchEntity.image)
            .placeholder(R.drawable.ic_launch_placeholder)
            .into(binding.ivLaunchDetails)
    }
}