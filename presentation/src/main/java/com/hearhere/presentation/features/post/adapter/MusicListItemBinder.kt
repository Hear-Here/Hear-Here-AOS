package com.hearhere.presentation.features.post.adapter

import android.graphics.drawable.Drawable
import android.util.Log
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import com.hearhere.presentation.R
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.hearhere.presentation.base.BaseItemBinder
import com.hearhere.presentation.common.util.createRandomId
import com.hearhere.presentation.features.post.PostViewModel

class MusicListItemBinder(
    val onClickItem: (PostViewModel.MusicListItemState) -> Unit
) : BaseItemBinder {
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

    fun onClick() {
        music.value?.let {
            onClickItem(it)
        }
    }
}

@BindingAdapter("cover")
fun setCoverImage(imageview: ImageView, url: String?) {
    Log.d("url -옥", url.toString())
    if (url.isNullOrBlank()) return
    url?.let {
        Glide.with(imageview.context)
            .load(url)
            .centerCrop()
            .placeholder(com.hearhere.presentation.common.R.drawable.outframe)
            .error(
                com.hearhere.presentation.common.R.drawable.outframe
            )
            .listener(object : RequestListener<Drawable> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Drawable>?,
                    isFirstResource: Boolean
                ): Boolean {
                    Log.d("glide-heare", "===$e")
                    return false
                }
                override fun onResourceReady(
                    resource: Drawable?,
                    model: Any?,
                    target: Target<Drawable>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    return false
                }
            })
            .into(imageview)
    }
}
