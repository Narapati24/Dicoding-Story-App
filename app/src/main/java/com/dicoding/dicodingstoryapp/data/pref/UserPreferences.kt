package com.dicoding.dicodingstoryapp.data.pref

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "session")

class UserPreferences private constructor(private val dataStore: DataStore<Preferences>){
    private val tokenkey = stringPreferencesKey("token")

    fun getTokenUser(): Flow<String?> {
        return dataStore.data.map { preferences ->
            preferences[tokenkey]
        }
    }

    suspend fun saveTokenUser(token: String){
        dataStore.edit { preferences ->
            preferences[tokenkey] = token
        }
    }

    suspend fun deleteTokenUser(){
        dataStore.edit { preferences ->
            preferences.remove(tokenkey)
        }
    }

    companion object{
        @Volatile
        private var INSTANCE: UserPreferences? = null

        fun getInstance(dataStore: DataStore<Preferences>): UserPreferences{
            return INSTANCE ?: synchronized(this){
                val instance = UserPreferences(dataStore)
                INSTANCE = instance
                instance
            }
        }
    }
}