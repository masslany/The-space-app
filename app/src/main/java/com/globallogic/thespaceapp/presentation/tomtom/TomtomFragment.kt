package com.globallogic.thespaceapp.presentation.tomtom

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.globallogic.thespaceapp.R
import com.globallogic.thespaceapp.databinding.FragmentTomtomBinding
import com.globallogic.thespaceapp.presentation.starlink.StarlinkMarker
import com.globallogic.thespaceapp.presentation.starlink.StarlinkViewModel
import com.globallogic.thespaceapp.utils.State
import com.tomtom.online.sdk.common.location.LatLng
import com.tomtom.online.sdk.map.*
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TomtomFragment : Fragment(), OnMapReadyCallback {

    private var _binding: FragmentTomtomBinding? = null
    private val binding get() = _binding!!
    private lateinit var mapFragment: MapFragment

    private val viewModel: StarlinkViewModel by viewModels()

    private lateinit var currentIcon: Icon
    private lateinit var iconSmall: Icon
    private lateinit var iconMedium: Icon


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTomtomBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        iconSmall = Icon.Factory.fromResources(requireContext(), R.drawable.sat_icon_20)
        iconMedium = Icon.Factory.fromResources(requireContext(), R.drawable.sat_icon_50)

        mapFragment = childFragmentManager.findFragmentById(R.id.map_fragment) as MapFragment
        mapFragment.getAsyncMap(this)

    }

    override fun onMapReady(tomtomMap: TomtomMap) {
        tomtomMap.uiSettings.setStyleUrl("asset://styles/night.json")
        viewModel.predictPosition()

        viewModel.starlinks.observe(viewLifecycleOwner) { state ->
            when (state) {
                is State.Error -> {
                }
                State.Loading -> {
                }

                is State.Success -> {
                    currentIcon = if (tomtomMap.zoomLevel < 5.0) {
                        iconSmall
                    } else {
                        iconMedium
                    }

                    tomtomMap.removeMarkers()
                    prepareMarkers(tomtomMap, state.data)
                }
            }
        }
    }

    private fun prepareMarkers(map: TomtomMap, starlinkMarkers: List<StarlinkMarker>) {
        starlinkMarkers.forEach {
            val latitude = it.latLong.latitude
            val longitude = it.latLong.longitude
            map.addMarker(
                MarkerBuilder(
                    LatLng(
                        latitude,
                        longitude
                    )
                ).icon(currentIcon)
            )
        }
    }
}