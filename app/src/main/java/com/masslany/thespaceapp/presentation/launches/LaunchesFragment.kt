package com.masslany.thespaceapp.presentation.launches

import android.content.res.Configuration
import android.os.Bundle
import android.view.*
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.RequestManager
import com.google.android.material.snackbar.Snackbar
import com.masslany.thespaceapp.R
import com.masslany.thespaceapp.databinding.FragmentLaunchesBinding
import com.masslany.thespaceapp.utils.State
import com.masslany.thespaceapp.utils.makeGone
import com.masslany.thespaceapp.utils.makeVisible
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject


@AndroidEntryPoint
@ExperimentalCoroutinesApi
class LaunchesFragment : Fragment() {

    private var _binding: FragmentLaunchesBinding? = null
    private val binding get() = _binding!!

    private val viewModel: LaunchesViewModel by viewModels()

    @Inject
    lateinit var glide: RequestManager

    private var searchView: SearchView? = null
    private lateinit var launchesAdapter: LaunchesAdapter

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

        setupRecyclerView()

        setupObservers()

        setupListeners()
    }



    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.launches_menu, menu)

        val menuItem = menu.findItem(R.id.search_item)
        searchView = menuItem.actionView as SearchView
        searchView?.setOnSearchClickListener {
            viewModel.setSearchExpanded(true)
        }

        searchView?.setOnCloseListener {
            viewModel.setSearchExpanded(false)
            true
        }

        viewModel.isSearchExpanded.observe(viewLifecycleOwner) { expanded ->
            if (expanded) {
                searchView?.onActionViewExpanded()
                searchView?.setQuery(viewModel.query.value, false)
            } else {
                searchView?.onActionViewCollapsed()
            }
        }

        searchView?.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let {
                    viewModel.setQuery(query)
                    viewModel.onQueryTextChange()
                    searchView?.clearFocus()
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.let { text ->
                    viewModel.setQuery(text)
                    viewModel.onQueryTextChange()
                }
                return true
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        hideKeyboard()
    }

    private fun hideKeyboard() {
        val inputMethodManager =
            ContextCompat.getSystemService(requireContext(), InputMethodManager::class.java)!!
        inputMethodManager.hideSoftInputFromWindow(requireActivity().currentFocus?.windowToken, 0)
    }

    private fun setupRecyclerView() {
        launchesAdapter = LaunchesAdapter(glide, onItemClick = {
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
                    it.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
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
    }

    private fun setupObservers() {
        viewModel.launches.observe(viewLifecycleOwner) { state ->
            when (state) {
                is State.Success<List<LaunchAdapterItem>> -> {
                    launchesAdapter.submitList(state.data)

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
    }

    private fun setupListeners() {
        binding.srlLaunches.setOnRefreshListener {
            viewModel.fetchUpcomingLaunchesData(false)
            searchView?.onActionViewCollapsed()
            viewModel.setSearchExpanded(false)
            viewModel.setQuery("")
        }
    }
}