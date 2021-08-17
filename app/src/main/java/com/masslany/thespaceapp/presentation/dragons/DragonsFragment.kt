package com.masslany.thespaceapp.presentation.dragons

import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.RequestManager
import com.google.android.material.snackbar.Snackbar
import com.masslany.thespaceapp.R
import com.masslany.thespaceapp.databinding.FragmentDragonsBinding
import com.masslany.thespaceapp.utils.State
import com.masslany.thespaceapp.utils.makeGone
import com.masslany.thespaceapp.utils.makeVisible
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class DragonsFragment : Fragment() {

    private var _binding: FragmentDragonsBinding? = null
    private val binding get() = _binding!!

    private val viewModel: DragonsSharedViewModel by activityViewModels()

    @Inject
    lateinit var glide: RequestManager

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDragonsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val dragonsAdapter = DragonsAdapter(glide) { dragon ->
            findNavController().navigate(
                DragonsFragmentDirections.actionDragonsFragmentToDragonDetailsFragment(dragon.id)
            )
        }
        binding.rvDragons.adapter = dragonsAdapter

        when (resources.configuration.orientation) {
            Configuration.ORIENTATION_LANDSCAPE -> {
                binding.rvDragons.layoutManager = GridLayoutManager(requireContext(), 2)
            }
            else -> {
                binding.rvDragons.layoutManager = LinearLayoutManager(requireContext())
            }
        }

        viewModel.dragons.observe(viewLifecycleOwner) { state ->
            when (state) {
                State.Loading -> {
                    binding.srlDragons.isRefreshing = true
                    binding.errorLayout.errorConstraintLayout.makeGone()
                }
                is State.Success -> {
                    binding.srlDragons.isRefreshing = false
                    binding.rvDragons.makeVisible()
                    binding.errorLayout.errorConstraintLayout.makeGone()
                    dragonsAdapter.dragons = state.data
                }
                is State.Error -> {
                    val snackbar = Snackbar.make(
                        binding.srlDragons,
                        state.throwable.message ?: getString(R.string.an_error_occurred),
                        Snackbar.LENGTH_LONG
                    )
                    snackbar.setAction(
                        R.string.retry
                    ) { viewModel.onRetryClicked() }
                    snackbar.show()

                    binding.srlDragons.isRefreshing = false
                    binding.rvDragons.makeGone()
                    binding.errorLayout.errorConstraintLayout.makeVisible()
                }
            }
        }

        binding.srlDragons.setOnRefreshListener {
            viewModel.onRetryClicked()
        }
    }
}