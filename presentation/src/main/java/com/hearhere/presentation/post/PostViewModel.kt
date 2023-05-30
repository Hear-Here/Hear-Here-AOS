package com.hearhere.presentation.post

import android.util.Log
import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.hearhere.domain.usecaseImpl.SearchMusicUseCaseImpl
import com.hearhere.domain.usecaseImpl.TestUseCaseImpl
import com.hearhere.presentation.base.BaseItemBinder
import com.hearhere.presentation.base.BaseViewModel
import com.hearhere.presentation.common.util.createRandomId
import com.hearhere.presentation.post.adapter.MusicListItemBinder
import com.hearhere.presentation.test.TestBinder
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class PostViewModel @Inject constructor(
    private val testUseCase: TestUseCaseImpl,
    private val searchMusicUseCase: SearchMusicUseCaseImpl
) : BaseViewModel(){

    private val _list = MutableLiveData<List<BaseItemBinder>>()
    val list get() = _list

    private val _binder = MutableLiveData<List<BaseItemBinder>>()
    val binder get() = _binder

    init {
        setList()
        setToken().also { getToken() }
    }

    fun searchMusic(keyword : String){

        Log.d("/옥채연", keyword)
        viewModelScope.launch {
            kotlin.runCatching{
                val res = searchMusicUseCase.searchMusicBySong(keyword,10)

                val binders = arrayListOf<MusicListItemBinder>()

                res.onSuccess {
                    it.forEach {
                        val binder = MusicListItemBinder()
                        val state = MusicListItemState(
                            it.songId,
                            it.title,
                            it.artist,
                            cover = it.cover,
                            it.pubYear)
                        binder.setMusic(state)
                        binders.add(binder)
                    }.also {
                        binder.postValue(binders)
                    }

                }

                Log.d("/옥채연/search Music by song result",res.toString())
            }.onFailure {
                Log.d("search Music result fail... ",it.toString())
            }

            kotlin.runCatching{
                val res = searchMusicUseCase.searchMusicByArtist(keyword,30)
                Log.d("/옥채연/search Music result",res.toString())
            }.onFailure {
                Log.d("search Music result fail... ",it.toString())
            }
        }

        val tempList = ArrayList<BaseItemBinder>()

    }

    val onClick = View.OnClickListener {
        Log.d("record"," success ")
    }

    private fun setList(){
        val listItems = arrayListOf<TestBinder>()
        for(i in 0 until 10){
            listItems.add(createBinder().apply {
                this.text.set(i.toString())
            })
        }
        _list.postValue(listItems)
    }

    private fun createBinder() : TestBinder{
        return TestBinder(createRandomId(),::onClickItem)
    }

    fun test(){

    }

    fun onClickItem(id: Long){
        //클릭 이벤트 시 binder 에서 호출됨
    }

    private fun getToken(){
        viewModelScope.launch {
            val token = testUseCase.getAccessToken()
            Log.d("token", token.toString())
        }
    }
    private fun setToken(){
        viewModelScope.launch {
            testUseCase.updateAccessToken("new token here")
        }
    }

    data class MusicListItemState(
        val songId : Long,
        val title : String,
        val artist : String,
        val cover : String?="",
        val pubYear : String?
    )
}