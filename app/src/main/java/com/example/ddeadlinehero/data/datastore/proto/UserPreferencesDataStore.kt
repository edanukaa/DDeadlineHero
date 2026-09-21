package com.example.ddeadlinehero.data.datastore.proto

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import com.example.ddeadlinehero.UserPreferences
import kotlinx.coroutines.flow.Flow

// proto datastore

private val Context.userPreferencesDataStore: DataStore<UserPreferences> by dataStore(
    fileName = "user_preferences.pb",
    serializer = UserPreferencesSerializer()
)

class UserPreferencesDataStore(private val context: Context) {

    fun readPreferences(): Flow<UserPreferences> {
        return context.userPreferencesDataStore.data
    }

    suspend fun updateNotifications(enabled: Boolean) {
        context.userPreferencesDataStore.updateData { current ->
            current.toBuilder()
                .setNotificationsEnabled(enabled)
                .build()
        }
    }
}
