package com.example.ddeadlinehero.data.datastore.proto

import androidx.datastore.core.Serializer
import com.example.ddeadlinehero.UserPreferences
import java.io.InputStream
import java.io.OutputStream

class UserPreferencesSerializer : Serializer<UserPreferences> {

    override val defaultValue: UserPreferences
        get() = UserPreferences.getDefaultInstance()

    override suspend fun readFrom(input: InputStream): UserPreferences {
        return UserPreferences.parseFrom(input)
    }

    override suspend fun writeTo(
        t: UserPreferences,
        output: OutputStream
    ) {
        t.writeTo(output)
    }
}
