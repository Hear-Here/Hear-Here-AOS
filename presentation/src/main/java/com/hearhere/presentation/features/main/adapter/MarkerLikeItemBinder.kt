package com.hearhere.presentation.features.main.adapter

import androidx.lifecycle.MutableLiveData
import com.hearhere.presentation.R
import com.hearhere.presentation.base.BaseItemBinder
import com.hearhere.presentation.common.util.createRandomId
import com.hearhere.presentation.features.main.like.MarkerLikeViewModel

class MarkerLikeItemBinder : BaseItemBinder {
    override var itemId: Long = createRandomId()
    override var itemLayoutId: Int = R.layout.item_marker_like_list

    private val _marker =  MutableLiveData<MarkerLikeViewModel.MarkerLikeItemState>()
    val marker get() = _marker

    fun setMarker(m : MarkerLikeViewModel.MarkerLikeItemState){
        _marker.postValue(m)
    }

    override fun areContentsTheSame(oldItem: BaseItemBinder, newItem: BaseItemBinder): Boolean {
        return true
    }

}