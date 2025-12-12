package com.revioplus.app.data.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user_session")

@Singleton
class SessionManager @Inject constructor(
    @ApplicationContext private val context: Context
) {

    companion object {
        private val USER_ID_KEY = longPreferencesKey("user_id")
        private val AUTH_TOKEN_KEY = stringPreferencesKey("auth_token")
    }

    // Obtener el ID del usuario actual (null si no está logueado)
    val userId: Flow<Long?> = context.dataStore.data.map{ it[USER_ID_KEY] }

    //Obtener el token actual gaaa
    val authToken: Flow<String?> = context.dataStore.data.map{ it[AUTH_TOKEN_KEY] }

    // Guardar el ID y token al iniciar sesión
    suspend fun saveUserId(id: Long, token:String) {
        context.dataStore.edit { preferences ->
            preferences[USER_ID_KEY] = id
            preferences[AUTH_TOKEN_KEY] = token
        }
    }

    // Borrar sesión (Logout)
    suspend fun clearSession() {
        context.dataStore.edit {
            it.remove(USER_ID_KEY)
            it.remove(AUTH_TOKEN_KEY)
        }
    }
}
