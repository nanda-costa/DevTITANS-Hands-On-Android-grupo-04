package com.example.plaintext.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.plaintext.data.dao.PreferencesDao
import com.example.plaintext.data.repository.PasswordDBStore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

data class PreferencesViewState(
    val login: String = "",
    val password: String = "",
    val preencher: Boolean = false
)

@HiltViewModel
class PreferencesViewModel @Inject constructor(
    private val preferencesDao: PreferencesDao,
    private val passwordDBStore: PasswordDBStore
) : ViewModel() {

    var preferencesViewState by mutableStateOf(
        PreferencesViewState(
            login = preferencesDao.getLogin(),
            password = preferencesDao.getPassword(),
            preencher = preferencesDao.getAutofill()
        )
    )
        private set

    fun updateLogin(value: String) {
        preferencesViewState = preferencesViewState.copy(login = value)
        preferencesDao.updateLogin(value)
    }

    fun updatePassword(value: String) {
        preferencesViewState = preferencesViewState.copy(password = value)
        preferencesDao.updatePassword(value)
    }

    fun updatePreencher(value: Boolean) {
        preferencesViewState = preferencesViewState.copy(preencher = value)
        preferencesDao.updateAutofill(value)
    }

    fun checkCredentials(
        enteredLogin: String,
        enteredPassword: String
    ): Boolean {
        // 1. Verificar contra a Conta Mestre (Configurações)
        val savedLogin = preferencesDao.getLogin()
        val savedPassword = preferencesDao.getPassword()
        
        if (enteredLogin == savedLogin && enteredPassword == savedPassword && savedLogin.isNotEmpty()) {
            return true
        }

        // 2. Verificar contra as senhas armazenadas na lista
        return try {
            runBlocking {
                val list = passwordDBStore.getList().first()
                list.any { it.login == enteredLogin && it.password == enteredPassword }
            }
        } catch (e: Exception) {
            false
        }
    }
}