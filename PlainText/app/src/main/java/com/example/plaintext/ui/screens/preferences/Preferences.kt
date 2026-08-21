package com.example.plaintext.ui.screens.preferences

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.plaintext.ui.theme.DarkGray
import com.example.plaintext.ui.viewmodel.PreferencesViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Preferences(
    preferencesViewModel: PreferencesViewModel = hiltViewModel(),
    onBack: () -> Unit
) {
    var showLoginDialog by remember { mutableStateOf(false) }
    var showPasswordDialog by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Configurações", color = Color.White) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Voltar",
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = DarkGray
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
        ) {
            Text(
                text = "Geral",
                color = Color(0xFF99CC00),
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(16.dp)
            )

            SettingsItem(
                title = "Setar Login",
                subtitle = "Login para entrar no sistema",
                onClick = { showLoginDialog = true }
            )

            HorizontalDivider()

            SettingsItem(
                title = "Setar Senha",
                subtitle = "Senha para entrar no sistema",
                onClick = { showPasswordDialog = true }
            )

            HorizontalDivider()

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(text = "Preencher login", fontWeight = FontWeight.Bold)
                    Text(text = "Preencher login na tela inicial", fontSize = 12.sp, color = Color.Gray)
                }
                Checkbox(
                    checked = preferencesViewModel.preferencesViewState.preencher,
                    onCheckedChange = { preferencesViewModel.updatePreencher(it) }
                )
            }

            HorizontalDivider()
        }
    }

    if (showLoginDialog) {
        var tempLogin by remember { mutableStateOf(preferencesViewModel.preferencesViewState.login) }
        AlertDialog(
            onDismissRequest = { showLoginDialog = false },
            title = { Text("Setar Login") },
            text = {
                Column {
                    Text("Digite o login", fontSize = 12.sp)
                    OutlinedTextField(
                        value = tempLogin,
                        onValueChange = { tempLogin = it },
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            },
            confirmButton = {
                TextButton(onClick = {
                    preferencesViewModel.updateLogin(tempLogin)
                    showLoginDialog = false
                }) { Text("OK") }
            },
            dismissButton = {
                TextButton(onClick = { showLoginDialog = false }) { Text("CANCEL") }
            }
        )
    }

    if (showPasswordDialog) {
        var tempPassword by remember { mutableStateOf(preferencesViewModel.preferencesViewState.password) }
        AlertDialog(
            onDismissRequest = { showPasswordDialog = false },
            title = { Text("Setar Senha") },
            text = {
                Column {
                    Text("Digite a senha", fontSize = 12.sp)
                    OutlinedTextField(
                        value = tempPassword,
                        onValueChange = { tempPassword = it },
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            },
            confirmButton = {
                TextButton(onClick = {
                    preferencesViewModel.updatePassword(tempPassword)
                    showPasswordDialog = false
                }) { Text("OK") }
            },
            dismissButton = {
                TextButton(onClick = { showPasswordDialog = false }) { Text("CANCEL") }
            }
        )
    }
}

@Composable
fun SettingsItem(title: String, subtitle: String, onClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(16.dp)
    ) {
        Text(text = title, fontWeight = FontWeight.Bold)
        Text(text = subtitle, fontSize = 12.sp, color = Color.Gray)
    }
}