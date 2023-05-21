package com.hearhere.presentation.features.main.adapter

import androidx.lifecycle.MutableLiveData
import com.hearhere.presentation.R
import com.hearhere.presentation.base.BaseItemBinder
import com.hearhere.presentation.common.util.createRandomId
import com.hearhere.presentation.features.main.like.MarkerLikeViewModel
import com.hearhere.presentation.features.main.profile.MarkerMyPostingViewModel

class MarkerMyListItemBinder(
    private val onClick:(Int)->Unit?,
    private val onClickMenu : (Int ,String) -> Unit?

) : BaseItemBinder  {
    override var itemId: Long = createRandomId()
    override var itemLayoutId: Int = R.layout.item_marker_my_list


    private val _marker =  MutableLiveData<MarkerMyPostingViewModel.MarkerMyItemState>()
    val marker get() = _marker

    fun setMarker(m : MarkerMyPostingViewModel.MarkerMyItemState){
        _marker.postValue(m)
    }

    fun onClickItem(){
        if(marker.value!=null){
            onClick(marker.value!!.postId)
        }
    }

    fun onClickItemMenu(){
        if(marker.value!=null){
            onClickMenu(marker.value!!.postId , marker.value!!.title)
        }
    }


    override fun areContentsTheSame(oldItem: BaseItemBinder, newItem: BaseItemBinder): Boolean {
        return true
    }
}