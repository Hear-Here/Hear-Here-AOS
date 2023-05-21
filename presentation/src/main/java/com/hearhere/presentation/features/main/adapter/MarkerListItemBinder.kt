package com.hearhere.presentation.features.main.adapter

import android.content.Context
import android.graphics.drawable.Drawable
import android.net.Uri
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.hearhere.presentation.R
import com.hearhere.presentation.base.BaseItemBinder
import com.hearhere.presentation.common.util.createRandomId
import com.hearhere.presentation.databinding.ItemMarkerListBinding
import com.hearhere.presentation.features.main.MarkerListViewModel

class MarkerListItemBinder() : BaseItemBinder {
    override var itemId: Long = createRandomId()
    override var itemLayoutId: Int = R.layout.item_marker_list

    private val _marker =  MutableLiveData<MarkerListViewModel.MarkerListItemState>()
    val marker get() = _marker

    fun setMarker(m: MarkerListViewModel.MarkerListItemState){
        _marker.postValue(m)
    }

    override fun areContentsTheSame(oldItem: BaseItemBinder, newItem: BaseItemBinder): Boolean {
        return true
    }

}

@BindingAdapter("coverImage")
fun setCoverImage(imageview: ImageView, url: String?){
    if(url.isNullOrBlank()) return
    url?.let {
        Glide.with(imageview)
            .load(it)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .fitCenter()
            .placeholder(com.hearhere.presentation.common.R.drawable.outframe)
            .error(
                com.hearhere.presentation.common.R.drawable.outframe)
            .into(object : CustomTarget<Drawable>() {
                override fun onResourceReady(
                    resource: Drawable,
                    transition: Transition<in Drawable>?
                ) {
                    imageview.setImageDrawable(resource)
                }
                override fun onLoadCleared(placeholder: Drawable?) {}
            })
    }
}
