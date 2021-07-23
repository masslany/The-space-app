package com.globallogic.thespaceapp.presentation.dragons

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.globallogic.thespaceapp.databinding.FragmentDragonsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DragonsFragment : Fragment() {

    private var _binding: FragmentDragonsBinding? = null
    private val binding get() = _binding!!


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

    }
}