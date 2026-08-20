package com.example.plaintext.data.model

import android.os.Parcelable
import androidx.compose.runtime.Immutable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Immutable
data class Password(
    val id: Int = 0,
    val name: String,
    val login: String,
    val password: String,
    val notes: String,
)

@Serializable
@Parcelize
data class PasswordInfo(
    val id: Int = 0,
    val name: String = "",
    val login: String = "",
    val password: String = "",
    val notes: String = "",
) : Parcelable {
    fun toPassword(): Password = Password(
        id = id,
        name = name,
        login = login,
        password = password,
        notes = notes
    )
}

fun Password.toPasswordInfo(): PasswordInfo = PasswordInfo(
    id = id,
    name = name,
    login = login,
    password = password,
    notes = notes
)