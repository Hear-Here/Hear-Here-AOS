package com.hearhere.presentation.post.adapter

import android.graphics.drawable.Drawable
import android.util.Log
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import com.hearhere.presentation.R
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.hearhere.presentation.base.BaseItemBinder
import com.hearhere.presentation.common.util.createRandomId
import com.hearhere.presentation.post.PostViewModel

class MusicListItemBinder(): BaseItemBinder {
    override var itemId: Long = createRandomId()
    override var itemLayoutId: Int = R.layout.item_music_list

    private val _music = MutableLiveData<PostViewModel.MusicListItemState>()

    val music get() = _music

    val coverImage = ObservableField<String>("")

    fun setMusic(m: PostViewModel.MusicListItemState) {
        _music.postValue(m)
        coverImage.set(m.cover)
    }

    override fun areContentsTheSame(oldItem: BaseItemBinder, newItem: BaseItemBinder): Boolean {
        return true
    }

}

@BindingAdapter("cover")
fun setCoverImage(imageview: ImageView, url: String?){
    Log.d("url -옥",url.toString())
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
