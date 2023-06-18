package com.hearhere.presentation.features.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import com.google.android.gms.maps.model.LatLng
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.hearhere.presentation.databinding.DialogMarkerCreateBinding
import com.hearhere.presentation.databinding.FragmentMarkerdetailBottomBinding
import com.hearhere.presentation.util.screenHeight


class MarkerCreateDialog : BottomSheetDialogFragment() {
    private lateinit var binding: DialogMarkerCreateBinding
    private var listener: OnClickDialog? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DialogMarkerCreateBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        binding.view = this
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
                height = (screenHeight * 0.25).toInt()
            }
        }

    }

    fun onClickPositive() {
        listener?.onClickPositive()
    }

    fun onClickNegative() {
        listener?.onClickNegative()
    }

    interface OnClickDialog {
        fun onClickPositive()
        fun onClickNegative()
    }

    companion object {
        fun newInstance(listener: OnClickDialog): MarkerCreateDialog {
            val fr = MarkerCreateDialog()
            fr.listener = listener
            return fr
        }
    }

}