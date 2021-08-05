package com.globallogic.thespaceapp.presentation.starlink

import android.content.res.Resources
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.globallogic.thespaceapp.R
import com.globallogic.thespaceapp.databinding.FragmentMapStarlinkBinding
import com.globallogic.thespaceapp.di.DefaultDispatcher
import com.globallogic.thespaceapp.di.MainDispatcher
import com.globallogic.thespaceapp.utils.State
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

@AndroidEntryPoint
class StarlinkMapFragment : Fragment(), OnMapReadyCallback {

    private var _binding: FragmentMapStarlinkBinding? = null
    private val binding get() = _binding!!

    private lateinit var googleMap: GoogleMap
    private val viewModel: StarlinkViewModel by viewModels()

    @Inject
    @DefaultDispatcher
    lateinit var defaultDispatcher: CoroutineDispatcher

    @Inject
    @MainDispatcher
    lateinit var mainDispatcher: CoroutineDispatcher

    private lateinit var currentIcon: BitmapDescriptor
    private lateinit var iconSmall: BitmapDescriptor
    private lateinit var iconMedium: BitmapDescriptor

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMapStarlinkBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        iconSmall = BitmapDescriptorFactory.fromResource(R.drawable.sat_icon_20)
        iconMedium = BitmapDescriptorFactory.fromResource(R.drawable.sat_icon_50)

        currentIcon = iconSmall

        val mapFragment = childFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(map: GoogleMap) {
        googleMap = map

        try {
            // Customise the styling of the base map using a JSON object defined
            // in a raw resource file.
            val success = googleMap.setMapStyle(
                MapStyleOptions.loadRawResourceStyle(
                    requireContext(), R.raw.maps_style
                )
            )
            if (!success) {
                Log.e("TAG", "Style parsing failed.")
            }
        } catch (e: Resources.NotFoundException) {
            Log.e("TAG", "Can't find style. Error: ", e)
        }

        val markers = mutableMapOf<String, Marker?>()

        viewModel.markersMap.observe(viewLifecycleOwner) { data ->
            data.forEach { (id, marker) ->
                val m = googleMap.addMarker(
                    MarkerOptions()
                        .position(LatLng(marker!!.latitude, marker.longitude))
                        .title(marker.objectName)
                        .icon(currentIcon)
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
                    currentIcon = if (googleMap.cameraPosition.zoom < 5.0) {
                        iconSmall
                    } else {
                        iconMedium
                    }

                    state.data.forEach {
                        markers[it.id]?.position = LatLng(it.latitude, it.longitude)
                        markers[it.id]?.setIcon(currentIcon)
                    }
                }
            }
        }
    }
}