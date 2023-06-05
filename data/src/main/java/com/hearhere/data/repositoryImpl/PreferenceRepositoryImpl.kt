package com.hearhere.data.repositoryImpl

import android.content.Context
import android.util.Log
import androidx.datastore.preferences.core.edit
import com.google.gson.Gson
import com.google.gson.JsonArray
import com.hearhere.data.data.local.HearHerePrefKeys
import com.hearhere.data.di.dataStore
import com.hearhere.domain.model.AuthToken
import com.hearhere.domain.model.Location
import com.hearhere.domain.repository.PreferenceRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json
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

    override suspend fun updateLocation(lat: Double, lng: Double) {
        val location = Gson().toJson(Location(lat,lng))
        context.dataStore.edit { preferences ->
            preferences[HearHerePrefKeys.LOCATION] = location

        }
    }

    override suspend fun getLocation() : Flow<Location> = context.dataStore.data.map { preferences ->
        val location = preferences[HearHerePrefKeys.LOCATION] ?: ""
        Gson().fromJson(location, Location::class.java)
    }

}