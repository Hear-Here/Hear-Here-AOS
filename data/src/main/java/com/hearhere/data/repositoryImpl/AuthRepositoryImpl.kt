package com.hearhere.data.repositoryImpl

import android.content.Context
import android.util.Log
import androidx.datastore.preferences.core.edit
import com.hearhere.data.data.local.HearHerePrefKeys
import com.hearhere.data.data.local.TestPref
import com.hearhere.data.data.local.toDomain
import com.hearhere.data.data.network.ApiHelper
import com.hearhere.data.data.network.ApiHelperImpl
import com.hearhere.data.data.network.ParsingHelperImpl
import com.hearhere.data.di.dataStore
import com.hearhere.domain.model.AuthToken
import com.hearhere.domain.repository.AuthRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context,
    private val apiHelper: ApiHelper
) : BaseRepository() ,AuthRepository{

    override suspend fun login(token: String) : Result<AuthToken> {
        Log.d("call post api", "kakao")
        kotlin.runCatching{
            val res = apiHelper.login(token)
            if(res.data!=null){
                Log.d("kakao",res.data.accessToken)
                return Result.success(AuthToken(res.data.accessToken))
            }else{
                Log.d("kakao", " no data...")
            }

        }.onFailure {
            return Result.failure(it)
        }
        return Result.failure(java.lang.Exception("no data received..."))
    }

}