package com.globallogic.thespaceapp.presentation.tomtom

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.globallogic.thespaceapp.R
import com.globallogic.thespaceapp.databinding.FragmentTomtomBinding
import com.globallogic.thespaceapp.presentation.starlink.StarlinkViewModel
import com.globallogic.thespaceapp.utils.State
import com.tomtom.online.sdk.common.location.LatLng
import com.tomtom.online.sdk.map.*
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TomtomFragment : Fragment(), OnMapReadyCallback, TomtomMapCallback.OnMarkerClickListener {

    private var _binding: FragmentTomtomBinding? = null
    private val binding get() = _binding!!
    private lateinit var mapFragment: MapFragment

    private val viewModel: StarlinkViewModel by viewModels()

    private lateinit var currentIcon: Icon
    private lateinit var iconSmall: Icon
    private lateinit var iconMedium: Icon
    private lateinit var iconLarge: Icon


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

        iconSmall = Icon.Factory.fromResources(requireContext(), R.drawable.sat_icon_50)
        iconMedium = Icon.Factory.fromResources(requireContext(), R.drawable.sat_icon_50)
        iconLarge = Icon.Factory.fromResources(requireContext(), R.drawable.sat_icon_100)

        currentIcon = iconLarge

        mapFragment = childFragmentManager.findFragmentById(R.id.map_fragment) as MapFragment
        mapFragment.getAsyncMap(this)

    }

    override fun onMapReady(tomtomMap: TomtomMap) {
        tomtomMap.uiSettings.setStyleUrl("asset://styles/night.json")
        tomtomMap.markerSettings.setMarkersClustering(true, 40, 6)
        tomtomMap.addOnMarkerClickListener(this);

        val markers = mutableMapOf<String, Marker>()

        viewModel.markersMap.observe(viewLifecycleOwner) { data ->
            data.forEach { (id, marker) ->
                val latitude = marker!!.latitude
                val longitude = marker.longitude
                val m = tomtomMap.addMarker(
                    MarkerBuilder(
                        LatLng(
                            latitude,
                            longitude
                        )
                    )
                        .icon(currentIcon)
                        .markerBalloon(SimpleMarkerBalloon(marker.objectName))
                        .iconAnchor(MarkerAnchor.Bottom)
                        .decal(false)
                        .shouldCluster(true)
                )
                markers[id] = m
            }

            viewModel.predictPosition()
        }

        viewModel.starlinks.observe(viewLifecycleOwner) { state ->
            when (state) {
                is State.Error -> {
                }
                State.Loading -> {
                }

                is State.Success -> {
                    println(tomtomMap.zoomLevel.toString())
//                    currentIcon = if (tomtomMap.zoomLevel < 5.0) {
//                        iconMedium
//                    } else {
//                        iconLarge
//                    }
                    state.data.forEach {
                        val marker = markers[it.id]!!
                        tomtomMap.markerSettings.moveMarker(
                            marker,
                            LatLng(
                                it.latitude,
                                it.longitude
                            )
                        )
//                        tomtomMap.markerSettings.updateMarkerIcon(
//                            marker,
//                            currentIcon
//                        )
                    }
                }
            }
        }
    }

    override fun onMarkerClick(marker: Marker) {}
}