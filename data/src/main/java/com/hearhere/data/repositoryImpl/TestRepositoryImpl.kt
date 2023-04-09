package com.hearhere.data.repositoryImpl

import android.content.Context
import androidx.datastore.preferences.core.edit
import com.hearhere.data.data.local.HearHerePrefKeys
import com.hearhere.data.data.local.TestPref
import com.hearhere.data.data.local.toData
import com.hearhere.data.data.local.toPref
import com.hearhere.data.data.network.ApiHelper
import com.hearhere.data.di.dataStore
import com.hearhere.domain.model.TestModel
import com.hearhere.domain.usecase.repository.TestRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class TestRepositoryImpl @Inject constructor(
    @ApplicationContext private val context : Context,
    private val apiHelper: ApiHelper
    ):
    TestRepository {
    private val testPreferencesFlow: Flow<TestPref> = context.dataStore.data.map { preferences ->
        val token = preferences[HearHerePrefKeys.ACCESS_TOKEN]?:""
        TestPref(token)
    }
    override suspend fun getToken() = testPreferencesFlow.first().toData()

    override suspend fun updateToken(token: String) {
        context.dataStore.edit { preferences ->
            preferences[HearHerePrefKeys.ACCESS_TOKEN] = token
        }
    }
}

fun TestPref.mapToDomain(): TestModel = TestModel(this.value)