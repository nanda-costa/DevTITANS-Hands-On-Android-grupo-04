package com.example.plaintext.ui.screens.editList

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.plaintext.R
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
    var name by rememberSaveable { mutableStateOf(password.name) }
    var login by rememberSaveable { mutableStateOf(password.login) }
    var senha by rememberSaveable { mutableStateOf(password.password) }
    var notes by rememberSaveable { mutableStateOf(password.notes ?: "") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("PlainText", color = Color.White) },
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
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(LimeGreen)
                    .padding(16.dp)
            ) {
                Text(
                    text = "Senha",
                    color = Color.White,
                    fontSize = 20.sp
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                EditField(icon = Icons.Default.Star, value = name, onValueChange = { name = it })
                EditField(icon = Icons.Default.Person, value = login, onValueChange = { login = it })
                EditField(icon = Icons.Default.Lock, value = senha, onValueChange = { senha = it })
                EditField(icon = Icons.Default.Info, value = notes, onValueChange = { notes = it }, isNotes = true)

                Spacer(modifier = Modifier.height(24.dp))

                Button(
                    onClick = {
                        savePassword(PasswordInfo(password.id, name, login, senha, notes))
                        navigateBack()
                    },
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = DarkGray,
                        contentColor = Color.White
                    )
                ) {
                    Text("SALVAR")
                }
            }
        }
    }
}

@Composable
fun EditField(
    icon: ImageVector,
    value: String,
    onValueChange: (String) -> Unit,
    isNotes: Boolean = false
) {
    Row(verticalAlignment = Alignment.Top) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            modifier = Modifier.size(32.dp),
            tint = Color.Gray
        )
        Spacer(modifier = Modifier.width(16.dp))
        TextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = Color.Transparent,
                focusedContainerColor = Color.Transparent
            ),
            label = if (isNotes) { { Text("Notas") } } else null,
            singleLine = !isNotes,
            minLines = if (isNotes) 3 else 1
        )
    }
}


@Composable
fun EditInput(
    textInputLabel: String,
    textInputState: MutableState<String> = mutableStateOf(""),
    textInputHeight: Int = 60
) {
    val padding: Int = 30

    var textState by rememberSaveable { textInputState }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(textInputHeight.dp)
            .padding(horizontal = padding.dp),
        horizontalArrangement = Arrangement.Center,
    ) {
        OutlinedTextField(
            value = textState,
            onValueChange = { textState = it },
            label = { Text(textInputLabel) },
            modifier = Modifier
                .height(textInputHeight.dp)
                .fillMaxWidth()
        )

    }
    Spacer(modifier = Modifier.height(10.dp))
}

@Preview(showBackground = true)
@Composable
fun EditListPreview() {
    EditList(
        Screen.EditList(PasswordInfo(1, "Nome", "Usuário", "Senha", "Notas")),
        navigateBack = {},
        savePassword = {}
    )
}

@Preview(showBackground = true)
@Composable
fun EditInputPreview() {
    EditInput("Nome")
}