package com.globallogic.thespaceapp.presentation.launches

import android.content.res.Configuration
import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.GridLayoutManager.SpanSizeLookup
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.RequestManager
import com.globallogic.thespaceapp.R
import com.globallogic.thespaceapp.databinding.FragmentLaunchesBinding
import com.globallogic.thespaceapp.utils.State
import com.globallogic.thespaceapp.utils.makeGone
import com.globallogic.thespaceapp.utils.makeVisible
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class LaunchesFragment : Fragment() {

    private var _binding: FragmentLaunchesBinding? = null
    private val binding get() = _binding!!

    private val viewModel: LaunchesViewModel by activityViewModels()

    @Inject
    lateinit var glide: RequestManager

    private var searchView: SearchView? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLaunchesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)

        val launchesAdapter = LaunchesAdapter(glide, onItemClick = {
            findNavController().navigate(
                LaunchesFragmentDirections.actionLaunchesFragmentToLaunchDetailsFragment(
                    it.id
                )
            )
        })
        binding.rvLaunches.adapter = launchesAdapter

        val decor = HeaderItemDecoration(binding.rvLaunches) { itemPosition ->
            launchesAdapter.getItemViewType(itemPosition) == R.id.item_recyclerview_header
        }

        binding.rvLaunches.addItemDecoration(decor)

        when (resources.configuration.orientation) {
            Configuration.ORIENTATION_LANDSCAPE -> {
                binding.rvLaunches.layoutManager = GridLayoutManager(requireContext(), 2).also {
                    it.spanSizeLookup = object : SpanSizeLookup() {
                        override fun getSpanSize(position: Int): Int {
                            return when (launchesAdapter.getItemViewType(position)) {
                                R.id.item_recyclerview -> {
                                    1
                                }
                                R.id.item_recyclerview_header -> {
                                    2
                                }
                                else -> -1
                            }
                        }

                    }
                }
            }
            else -> {
                binding.rvLaunches.layoutManager = LinearLayoutManager(requireContext())
            }
        }
        viewModel.launches.observe(viewLifecycleOwner) { state ->
            when (state) {
                is State.Success<List<LaunchAdapterItem>> -> {
                    launchesAdapter.launches = state.data

                    binding.errorLayout.errorConstraintLayout.makeGone()
                    binding.srlLaunches.isRefreshing = false
                }
                is State.Error -> {
                    Snackbar.make(
                        binding.srlLaunches,
                        state.throwable.message ?: "Error!",
                        Snackbar.LENGTH_LONG
                    ).show()

                    binding.errorLayout.errorConstraintLayout.makeVisible()
                    binding.srlLaunches.isRefreshing = false
                }
                is State.Loading -> {
                    binding.errorLayout.errorConstraintLayout.makeGone()
                    binding.srlLaunches.isRefreshing = true
                }
            }
        }
        binding.srlLaunches.setOnRefreshListener {
            viewModel.fetchUpcomingLaunchesData()
            searchView?.onActionViewCollapsed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.launches_menu, menu)

        val menuItem = menu.findItem(R.id.search_item)
        searchView = menuItem.actionView as SearchView

        searchView?.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean = true

            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.let { text -> viewModel.onQueryTextChange(text) }
                return true
            }

        })
    }
}