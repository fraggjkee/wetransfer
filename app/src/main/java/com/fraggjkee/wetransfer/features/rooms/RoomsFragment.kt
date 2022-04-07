package com.fraggjkee.wetransfer.features.rooms

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.fraggjkee.wetransfer.databinding.FragmentRoomsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RoomsFragment : Fragment() {

    private val viewModel: RoomsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return FragmentRoomsBinding.inflate(inflater, container, false)
            .also { setupUi(it) }
            .root
    }

    private fun setupUi(binding: FragmentRoomsBinding) {
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel
    }
}