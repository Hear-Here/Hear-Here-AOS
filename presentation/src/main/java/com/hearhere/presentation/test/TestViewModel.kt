package com.hearhere.presentation.test

import android.util.Log
import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.hearhere.domain.usecaseImpl.TestUseCaseImpl
import com.hearhere.presentation.base.BaseItemBinder
import com.hearhere.presentation.base.BaseViewModel
import com.hearhere.presentation.common.util.createRandomId
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TestViewModel @Inject constructor(
    private val testUseCase: TestUseCaseImpl
) : BaseViewModel(){

    private val _list = MutableLiveData<List<BaseItemBinder>>()
    val list get() = _list

    init {
        setList()
        setToken().also { getToken() }
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

    val onClick = View.OnClickListener { Log.d("clicked","hello") }


}