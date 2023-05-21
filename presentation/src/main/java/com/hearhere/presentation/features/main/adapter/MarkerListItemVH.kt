package com.hearhere.presentation.features.main.adapter

import android.content.Context
import android.graphics.drawable.Drawable
import android.net.Uri
import androidx.core.content.ContextCompat
import androidx.databinding.ViewDataBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.hearhere.presentation.base.BaseItemBinder
import com.hearhere.presentation.base.BaseViewHolder
import com.hearhere.presentation.common.R
import com.hearhere.presentation.databinding.ItemMarkerListBinding

class MarkerListItemVH(private val binding: ViewDataBinding) : BaseViewHolder(binding) {

    override fun bind(binder: BaseItemBinder) {
        super.bind(binder)
        binder as MarkerListItemBinder
        binding as ItemMarkerListBinding
    }
}