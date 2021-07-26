package com.globallogic.thespaceapp.presentation.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.globallogic.thespaceapp.databinding.FragmentDashboardBinding

class DashboardFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null
    private val binding get() = _binding!!
    private lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = findNavController()

        binding.cvLaunches.setOnClickListener {
            navController.navigate(
                DashboardFragmentDirections.actionDashboardFragmentToUpcomingLaunchesFragment()
            )
        }

        binding.cvRockets.setOnClickListener {
            navController.navigate(
                DashboardFragmentDirections.actionDashboardFragmentToRocketsFragment()
            )
        }

        binding.cvDragons.setOnClickListener {
            navController.navigate(
                DashboardFragmentDirections.actionDashboardFragmentToDragonsFragment()
            )
        }

        binding.cvRoadster.setOnClickListener {
            navController.navigate(
                DashboardFragmentDirections.actionDashboardFragmentToRoadsterDetailsFragment()
            )
        }

        binding.cvStarlink.setOnClickListener {
            navController.navigate(
                DashboardFragmentDirections.actionDashboardFragmentToStarlinkMapFragment()
            )
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}