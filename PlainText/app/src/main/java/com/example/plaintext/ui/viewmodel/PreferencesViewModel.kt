package com.example.plaintext.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PreferencesViewModel @Inject constructor() : ViewModel() {

    var login by mutableStateOf("")
        private set

    var password by mutableStateOf("")
        private set

    var preencher by mutableStateOf(false)
        private set

    fun updateLogin(value: String) {
        login = value
    }

    fun updatePassword(value: String) {
        password = value
    }

    fun updatePreencher(value: Boolean) {
        preencher = value
    }

    fun checkCredentials(
        enteredLogin: String,
        enteredPassword: String
    ): Boolean {
        return enteredLogin == login &&
                enteredPassword == password &&
                login.isNotEmpty() &&
                password.isNotEmpty()
    }
}