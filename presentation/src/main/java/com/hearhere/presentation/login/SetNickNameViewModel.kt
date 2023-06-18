package com.hearhere.presentation.login

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.hearhere.domain.model.ApiResponse
import com.hearhere.domain.usecaseImpl.LoginUseCaseImpl
import com.hearhere.presentation.base.BaseViewModel
import com.hearhere.presentation.common.component.BasicTextField
import com.hearhere.presentation.util.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SetNickNameViewModel @Inject constructor(
    private val authUseCase: LoginUseCaseImpl
) : BaseViewModel(){

    private val _nickname = MutableLiveData<NickNameState>(NickNameState(""))
    val nickname get() = _nickname

    private val _navigationToMain = SingleLiveEvent<Any>()
    val navigationToMain get() = _navigationToMain



    init {
       viewModelScope.launch {
           val token =  authUseCase.getToken()
           Log.d("pref",token.accessToken)
       }
    }

    val nicknameHandler = object : BasicTextField.OnTextChanged{
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            _nickname.postValue(nickname.value?.copy(s.toString()))
        }

    }

    fun setNickName(){
        viewModelScope.launch {
            val res = authUseCase.setNickName(nickname.value?.nickname.toString())
            _navigationToMain.call()
            if(res is ApiResponse.Success){

            }else{
                Log.d("hyom nickname error",res.message.toString())
            }
        }
    }

    data class NickNameState(val nickname : String){
        val isEnabled =  !nickname.isNullOrBlank()
    }

}