package com.hearhere.presentation.features.main.adapter

import androidx.lifecycle.MutableLiveData
import com.hearhere.presentation.R
import com.hearhere.presentation.base.BaseItemBinder
import com.hearhere.presentation.common.util.createRandomId
import com.hearhere.presentation.features.main.like.MarkerLikeViewModel
import com.hearhere.presentation.features.main.profile.MarkerMyPostingViewModel

class MarkerMyListItemBinder : BaseItemBinder  {
    override var itemId: Long = createRandomId()
    override var itemLayoutId: Int = R.layout.item_marker_my_list


    private val _marker =  MutableLiveData<MarkerMyPostingViewModel.MarkerMyItemState>()
    val marker get() = _marker

    fun setMarker(m : MarkerMyPostingViewModel.MarkerMyItemState){
        _marker.postValue(m)
    }

    override fun areContentsTheSame(oldItem: BaseItemBinder, newItem: BaseItemBinder): Boolean {
        return true
    }
}