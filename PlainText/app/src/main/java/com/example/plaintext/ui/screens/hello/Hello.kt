package com.example.plaintext.ui.screens.hello

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import com.example.plaintext.ui.screens.Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Hello_screen(args: Screen.Hello) {
    Scaffold { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Olá, ${args.name ?: "usuário"}!",
                fontSize = 20.sp
            )
        }
    }
}
