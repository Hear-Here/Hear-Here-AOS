package com.hearhere.presentation.features.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.hearhere.presentation.databinding.FragmentFilterBottomBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PostFilterBottomSheet() : BottomSheetDialogFragment() {

    private lateinit var binding: FragmentFilterBottomBinding
    private val viewModel: PostFilterViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFilterBottomBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView() {
        BottomSheetBehavior.from(view?.parent as View).apply {
            state = BottomSheetBehavior.STATE_EXPANDED
            skipCollapsed = true
        }
        viewModel.initFilterChips()
    }

    companion object {
        fun newInstance(): PostFilterBottomSheet {
            val fragment = PostFilterBottomSheet()
            val bundle = Bundle().apply {}
            fragment.arguments = bundle
            return fragment
        }
    }
}
