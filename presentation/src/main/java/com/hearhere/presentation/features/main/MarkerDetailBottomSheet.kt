package com.hearhere.presentation.features.main

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.hearhere.presentation.databinding.FragmentMarkerdetailBottomBinding
import com.hearhere.presentation.util.screenHeight
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MarkerDetailBottomSheet : BottomSheetDialogFragment() {

    private lateinit var binding: FragmentMarkerdetailBottomBinding
    private val viewModel: MarkerDetailViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMarkerdetailBottomBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.root.visibility = View.INVISIBLE
        initView()
    }

    override fun onDestroy() {
        viewModel.sendLikeState()
        super.onDestroy()
    }

    private fun initView() {
        BottomSheetBehavior.from(view?.parent as View).apply {
            state = BottomSheetBehavior.STATE_EXPANDED
            skipCollapsed = true
        }

        binding.markerBottomLayout.apply {
            layoutParams = layoutParams.apply {
                height = (screenHeight * 0.85).toInt()
            }
        }

        viewModel.uiState.observe(this, Observer {
            Toast.makeText(requireContext(), it.isLiked.toString(), Toast.LENGTH_SHORT).show()
        })
        viewModel.loading.observe(this, Observer {
            if(it == true) binding.root.visibility = View.INVISIBLE
            else binding.root.visibility = View.VISIBLE
        })
    }


    companion object {
        fun newInstance(postId: Long): MarkerDetailBottomSheet {
            val fragment = MarkerDetailBottomSheet()
            val POST_ID = "postId"

            val bundle = Bundle().apply {
                putLong(POST_ID, postId)
            }
            fragment.arguments = bundle
            return fragment
        }
    }
}