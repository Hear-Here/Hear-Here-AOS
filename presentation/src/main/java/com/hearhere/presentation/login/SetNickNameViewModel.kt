package com.hearhere.presentation.login

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.hearhere.domain.usecaseImpl.LoginUseCaseImpl
import com.hearhere.presentation.base.BaseViewModel
import com.hearhere.presentation.common.component.BasicTextField
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SetNickNameViewModel @Inject constructor(
    private val useCaseImpl: LoginUseCaseImpl
) : BaseViewModel(){

    private val _nickname = MutableLiveData<NickNameState>(NickNameState(""))
    val nickname get() = _nickname



    init {
       viewModelScope.launch {
           val token =  useCaseImpl.getToken()
           Log.d("pref",token.accessToken)
       }
    }

    val nicknameHandler = object : BasicTextField.OnTextChanged{
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            _nickname.postValue(nickname.value?.copy(s.toString()))
        }

    }

    data class NickNameState(val nickname : String){
        val isEnabled = true// !nickname.isNullOrBlank()
    }

}