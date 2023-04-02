package com.hearhere.presentation.test

import androidx.lifecycle.MutableLiveData
import com.hearhere.presentation.base.BaseItemBinder
import com.hearhere.presentation.base.BaseViewModel
import com.hearhere.presentation.common.util.createRandomId


class TestViewModel : BaseViewModel(){

    private val _list = MutableLiveData<List<BaseItemBinder>>()
    val list get() = _list

    init {
        setList()
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

    fun onClickItem(id: Long){
        //클릭 이벤트 시 binder 에서 호출됨
    }
}