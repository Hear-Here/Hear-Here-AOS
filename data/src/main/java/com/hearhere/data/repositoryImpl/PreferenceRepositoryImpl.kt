package com.hearhere.data.repositoryImpl

import android.content.Context
import androidx.datastore.preferences.core.edit
import com.hearhere.data.data.local.HearHerePrefKeys
import com.hearhere.data.di.dataStore
import com.hearhere.domain.model.AuthToken
import com.hearhere.domain.repository.PreferenceRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking
import javax.inject.Inject


class PreferenceRepositoryImpl @Inject constructor(
    @ApplicationContext val context : Context) : PreferenceRepository {

    private val authPreferencesFlow: Flow<AuthToken> = context.dataStore.data.map { preferences ->
        val token = preferences[HearHerePrefKeys.ACCESS_TOKEN] ?: ""
        AuthToken(token)
    }

    override suspend fun getAuthToken() = authPreferencesFlow.first()

    override suspend fun updateToken(token: String) {
        context.dataStore.edit { preferences ->
            preferences[HearHerePrefKeys.ACCESS_TOKEN] = token
        }
    }

}