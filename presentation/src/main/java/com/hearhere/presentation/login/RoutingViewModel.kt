package com.hearhere.presentation.login

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.hearhere.domain.repository.PreferenceRepository
import com.hearhere.presentation.base.BaseViewModel
import com.hearhere.presentation.util.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RoutingViewModel @Inject constructor(
   private val preferenceRepository: PreferenceRepository
) : BaseViewModel(){

    val navigateToMainEvent = SingleLiveEvent<Any>()
    val navigateToLoginEvent = SingleLiveEvent<Any>()
    val navigateToOnBoardEvent = SingleLiveEvent<Any>()


    fun checkToken(){
        viewModelScope.launch {
            val token = preferenceRepository.getAuthToken()
            Log.d("pref token",token.accessToken)
            if(token.accessToken.isNullOrBlank()){
                navigateToLoginEvent.call()
            }else navigateToMainEvent.call()
        }
    }

}