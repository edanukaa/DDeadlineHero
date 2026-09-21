package com.example.ddeadlinehero.ui.settings

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.ddeadlinehero.data.datastore.proto.UserPreferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class SettingsViewModel(
    private val settingsDataStore: SettingsDataStore, //preference data store
    private val protoDataStore: UserPreferencesDataStore //proto data store
) : ViewModel() {


    val notificationsEnabled: Flow<Boolean> =
        settingsDataStore.notificationsEnabled


    fun onNotificationsToggled(enabled: Boolean) {
        viewModelScope.launch {
            settingsDataStore.setNotificationsEnabled(enabled)

            protoDataStore.updateNotifications(enabled)
        }
    }

    class Factory(private val context: Context) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            val preferencesDataStore = SettingsDataStore(context)
            val protoDataStore = UserPreferencesDataStore(context)
            return SettingsViewModel(
                preferencesDataStore,
                protoDataStore
            ) as T
        }
    }
}
