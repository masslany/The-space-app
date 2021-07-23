package com.globallogic.thespaceapp.presentation.dragons

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.globallogic.thespaceapp.databinding.FragmentDragonsBinding
import com.globallogic.thespaceapp.domain.model.DragonEntity
import com.globallogic.thespaceapp.utils.State
import com.globallogic.thespaceapp.utils.makeGone
import com.globallogic.thespaceapp.utils.makeVisible
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DragonsFragment : Fragment() {

    private var _binding: FragmentDragonsBinding? = null
    private val binding get() = _binding!!

    private val viewModel: DragonsSharedViewModel by activityViewModels()


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

        viewModel.dragons.observe(viewLifecycleOwner) { state ->
            when (state) {
                State.Loading -> {
                    binding.srlDragons.isRefreshing = true
                    binding.errorLayout.errorConstraintLayout.makeGone()
                }
                is State.Success -> {
                    binding.srlDragons.isRefreshing = false
                    binding.errorLayout.errorConstraintLayout.makeGone()

                    fillUi(state.data)
                }
                is State.Error -> {
                    binding.srlDragons.isRefreshing = false
                    binding.errorLayout.errorConstraintLayout.makeVisible()
                }
            }
        }

        binding.srlDragons.setOnRefreshListener {
            viewModel.onRetryClicked()
        }
    }

    private fun fillUi(dragons: List<DragonEntity>) {
        // TODO
    }
}