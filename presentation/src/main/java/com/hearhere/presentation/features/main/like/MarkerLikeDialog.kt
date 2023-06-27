package com.hearhere.presentation.features.main.like

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.hearhere.presentation.databinding.DialogLikeMenuBinding
import com.hearhere.presentation.util.screenHeight

class MarkerLikeDialog : BottomSheetDialogFragment() {
    private val POST_ID = "postId"
    private val TITLE = "title"

    private lateinit var binding: DialogLikeMenuBinding

    private val viewModel: MarkerLikeViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DialogLikeMenuBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
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

        binding.markerBottomLayout.apply {
            layoutParams = layoutParams.apply {
                height = (screenHeight * 0.2).toInt()
            }
        }

        binding.deleteBtn.setOnClickListener {
            arguments?.getLong(POST_ID)?.let { id ->
                viewModel.onClickItemDelete(id)
            }
        }

        binding.copyBtn.setOnClickListener {
            arguments?.getString(TITLE)?.let { title ->
                viewModel.onClickItemCopy(title)
            }
        }
    }

    companion object {
        fun newInstance(postId: Long, title: String): MarkerLikeDialog {
            val fragment = MarkerLikeDialog()
            val POST_ID = "postId"
            val TITLE = "title"

            val bundle = Bundle().apply {
                putLong(POST_ID, postId)
                putString(TITLE, title)
            }
            fragment.arguments = bundle
            return fragment
        }
    }
}
