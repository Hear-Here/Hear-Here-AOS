package com.hearhere.presentation.features.main

import android.content.res.ColorStateList
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.hearhere.presentation.databinding.FragmentMarkerdetailBottomBinding
import com.hearhere.presentation.util.screenHeight
import kotlinx.coroutines.launch


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
        initView()

        val uri = Uri.parse("https://cdnimg.melon.co.kr/cm/album/images/022/08/448/2208448_500.jpg")
        binding.recordIv.setImageCover(uri)


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
            Toast.makeText(requireContext(), it.isLike.toString(), Toast.LENGTH_SHORT).show()
        })
    }


    companion object {
        fun newInstance(postId: Int): MarkerDetailBottomSheet {
            val fragment = MarkerDetailBottomSheet()
            val POST_ID = "postId"

            val bundle = Bundle().apply {
                putInt(POST_ID, postId)
            }
            fragment.arguments = bundle
            return fragment
        }
    }
}