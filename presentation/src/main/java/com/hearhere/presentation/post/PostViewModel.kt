package com.hearhere.presentation.post

import android.util.Log
import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.hearhere.domain.usecaseImpl.SearchMusicUseCaseImpl
import com.hearhere.domain.usecaseImpl.TestUseCaseImpl
import com.hearhere.presentation.base.BaseItemBinder
import com.hearhere.presentation.base.BaseViewModel
import com.hearhere.presentation.post.adapter.MusicListItemBinder
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PostViewModel @Inject constructor(
    private val testUseCase: TestUseCaseImpl,
    private val searchMusicUseCase: SearchMusicUseCaseImpl
) : BaseViewModel() {

    private val _list = MutableLiveData<List<BaseItemBinder>>()
    val list get() = _list

    private val _titleBinder = MutableLiveData<List<BaseItemBinder>>()
    val titleBinder get() = _titleBinder

    private val _singerBinder = MutableLiveData<List<BaseItemBinder>>()
    val singerBinder get() = _singerBinder

    private val _selectedMusic = MutableLiveData<MusicListItemState?>(null)
    val selectedMusic get() = _selectedMusic

    fun searchMusic(keyword: String) {
        if (keyword.isNullOrBlank()) return
        Log.d("/옥채연", keyword)
        viewModelScope.launch {
            _loading.postValue(true)
            kotlin.runCatching {
                val res = searchMusicUseCase.searchMusicBySong(keyword, 30)

                val binders = arrayListOf<MusicListItemBinder>()

                res.onSuccess {
                    it.forEach {
                        val binder = MusicListItemBinder(::onClickItem)
                        val state = MusicListItemState(
                            it.songId,
                            it.title,
                            it.artist,
                            cover = it.cover,
                            it.pubYear
                        )
                        binder.setMusic(state)
                        binders.add(binder)
                    }.also {
                        titleBinder.postValue(binders)
                    }
                }

                Log.d("/옥채연/search Music by song result", res.toString())
            }.onFailure {
                Log.d("search Music result fail... ", it.toString())
            }

            kotlin.runCatching {
                val res = searchMusicUseCase.searchMusicByArtist(keyword, 30)

                val binders = arrayListOf<MusicListItemBinder>()

                res.onSuccess {
                    it.forEach {
                        val binder = MusicListItemBinder(::onClickItem)
                        val state = MusicListItemState(
                            it.songId,
                            it.title,
                            it.artist,
                            cover = it.cover,
                            it.pubYear
                        )

                        binder.setMusic(state)
                        binders.add(binder)
                    }.also {
                        singerBinder.postValue(binders)
                    }
                }

                Log.d("/옥채연/search Music result", res.toString())
            }.onFailure {
                Log.d("search Music result fail... ", it.toString())
            }
            _loading.postValue(false)
        }
    }

    val onClick = View.OnClickListener {
        Log.d("record", " success ")
    }

    fun test() {
    }

    fun onClickItem(state: MusicListItemState) {
        // 클릭 이벤트 시 binder 에서 호출됨
        Log.d("OK music state", state.toString())
        _selectedMusic.postValue(state)
    }

    data class MusicListItemState(
        val songId: Long,
        val title: String,
        val artist: String,
        val cover: String? = "",
        val pubYear: String?
    )
}
