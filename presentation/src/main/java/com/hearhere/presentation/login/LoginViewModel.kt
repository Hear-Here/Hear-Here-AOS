package com.hearhere.presentation.login

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.hearhere.domain.model.ApiResponse
import com.hearhere.domain.usecaseImpl.LoginUseCaseImpl
import com.hearhere.domain.usecaseImpl.SearchMusicUseCaseImpl
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.user.UserApiClient
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCaseImpl,
    private val searchMusicUseCase: SearchMusicUseCaseImpl,
    application: Application
) : AndroidViewModel(application){

    private val TAG = "Kakao_Auth"
    private val context = application.applicationContext

    private val _loginState = MutableLiveData<Boolean?>(null)
    val loginState get() = _loginState


    private val callback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
        if (error != null) {
            _loginState.postValue(false)
            Log.d(TAG, "카카오계정으로 로그인 실패", error)
        } else if (token != null) {
            Log.d(TAG, "카카오계정으로 로그인 성공 ${token}")
            requestUserInfo()
        }
    }

    private fun requestUserInfo(){
        UserApiClient.instance.accessTokenInfo { _, _ ->
            UserApiClient.instance.me { user, _ ->
                val email= user?.kakaoAccount?.email?:""
                val id = user?.id ?:-1
                Log.d("kakao_nick",email)
                Log.d("kakao_id",id.toString())
                sendUserInfo(id,email)
            }
        }
    }

    fun kakaoLogin() {
        if (UserApiClient.instance.isKakaoTalkLoginAvailable(context)) {
            UserApiClient.instance.loginWithKakaoTalk(context) { token, error ->
                if (error != null) {
                    Log.e(TAG, "카카오톡으로 로그인 실패", error)
                    _loginState.postValue(false)
                    if (error is ClientError && error.reason == ClientErrorCause.Cancelled) {
                        return@loginWithKakaoTalk
                    }
                    UserApiClient.instance.loginWithKakaoAccount(context, callback = callback)
                } else if (token != null) {
                    Log.i(TAG, "카카오톡으로 로그인 성공 ${token.accessToken}")
                    requestUserInfo()
                }
            }

        } else {
            UserApiClient.instance.loginWithKakaoAccount(context, callback = callback)
        }
    }

    private fun sendUserInfo(id: Long, email: String = "") {
        viewModelScope.launch {
            val res = loginUseCase.login(id, email)
            Log.d("kakao-hear",res.toString())
            when(res){
                is ApiResponse.Success->{
                    loginUseCase.updateToken( res.data!!.accessToken)
                    _loginState.postValue(true)
                }
                else->{
                    Log.d("login error", res.message.toString())
                }
            }
        }
    }

}