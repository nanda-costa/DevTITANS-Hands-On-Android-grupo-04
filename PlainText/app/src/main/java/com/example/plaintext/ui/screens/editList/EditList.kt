package com.example.plaintext.ui.screens.editList

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.plaintext.data.model.PasswordInfo
import com.example.plaintext.ui.screens.Screen
import com.example.plaintext.ui.theme.DarkGray
import com.example.plaintext.ui.theme.LimeGreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditList(
    args: Screen.EditList,
    navigateBack: () -> Unit,
    savePassword: (password: PasswordInfo) -> Unit
) {
    val password = args.password
    val isEditing = password.id != 0 && password.name.isNotEmpty()

    var name by rememberSaveable { mutableStateOf(password.name) }
    var login by rememberSaveable { mutableStateOf(password.login) }
    var senha by rememberSaveable { mutableStateOf(password.password) }
    var notes by rememberSaveable { mutableStateOf(password.notes ?: "") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "PlainText",
                        color = Color.White,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.SemiBold
                    )
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
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Faixa verde de destaque com título dinâmico
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(LimeGreen)
                    .padding(horizontal = 24.dp, vertical = 12.dp)
            ) {
                Text(
                    text = if (isEditing) "Editar senha" else "Adicionar nova senha",
                    color = Color.White,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Formulário com os campos de entrada
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                EditInput(
                    label = "Nome",
                    value = name,
                    onValueChange = { name = it }
                )

                EditInput(
                    label = "Usuário",
                    value = login,
                    onValueChange = { login = it }
                )

                EditInput(
                    label = "Senha",
                    value = senha,
                    onValueChange = { senha = it }
                )

                EditInput(
                    label = "Notas",
                    value = notes,
                    onValueChange = { notes = it },
                    height = 140,
                    singleLine = false
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Botão Salvar estilizado
                Button(
                    onClick = {
                        savePassword(PasswordInfo(password.id, name, login, senha, notes))
                        navigateBack()
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFFFB288),
                        contentColor = Color.Black
                    ),
                    shape = RoundedCornerShape(24.dp),
                    modifier = Modifier
                        .fillMaxWidth(0.45f)
                        .height(44.dp)
                ) {
                    Text(
                        text = "Salvar",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Medium
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))
            }
        }
    }
}

@Composable
fun EditInput(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    height: Int = 60,
    singleLine: Boolean = true
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        singleLine = singleLine,
        modifier = Modifier
            .fillMaxWidth()
            .height(height.dp),
        shape = RoundedCornerShape(4.dp)
    )
}

@Preview(showBackground = true, name = "Adicionar nova senha")
@Composable
fun EditListNewPreview() {
    EditList(
        args = Screen.EditList(PasswordInfo(0, "", "", "", "")),
        navigateBack = {},
        savePassword = {}
    )
}

@Preview(showBackground = true, name = "Editar senha")
@Composable
fun EditListEditPreview() {
    EditList(
        args = Screen.EditList(PasswordInfo(1, "Facebook", "devtitans", "123456", "Notas de teste")),
        navigateBack = {},
        savePassword = {}
    )
}