package com.example.plaintext.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.plaintext.data.dao.PreferencesDao
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PreferencesViewModel @Inject constructor(
    private val preferencesDao: PreferencesDao
) : ViewModel() {

    var login by mutableStateOf(preferencesDao.getLogin())
        private set

    var password by mutableStateOf(preferencesDao.getPassword())
        private set

    var preencher by mutableStateOf(preferencesDao.getAutofill())
        private set

    fun updateLogin(value: String) {
        login = value
        preferencesDao.updateLogin(value)
    }

    fun updatePassword(value: String) {
        password = value
        preferencesDao.updatePassword(value)
    }

    fun updatePreencher(value: Boolean) {
        preencher = value
        preferencesDao.updateAutofill(value)
    }

    fun checkCredentials(
        enteredLogin: String,
        enteredPassword: String
    ): Boolean {
        val savedLogin = preferencesDao.getLogin()
        val savedPassword = preferencesDao.getPassword()
        return enteredLogin == savedLogin &&
                enteredPassword == savedPassword &&
                savedLogin.isNotEmpty() &&
                savedPassword.isNotEmpty()
    }
}