package com.example.ecommerce.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.ecommerce.model.dataclasses.LoginData
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class StoreLoggedInUserDetails(private val context: Context) {
    // to make sure there is only one instance
    companion object {
        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore("user")
        val USER_DETAILS = stringPreferencesKey("user_data")
    }

    // to get the email
    val getLoggedInUserDetails: Flow<String?> = context.dataStore.data
        .map { preferences ->
            preferences[USER_DETAILS] ?: ""
        }

    // to save the email
    suspend fun saveLoginDetails(loginData: LoginData) {
        val gson = Gson()
        val loginDataJson = gson.toJson(loginData)

        context.dataStore.edit { preferences ->
            preferences[USER_DETAILS] = loginDataJson
        }
    }
}