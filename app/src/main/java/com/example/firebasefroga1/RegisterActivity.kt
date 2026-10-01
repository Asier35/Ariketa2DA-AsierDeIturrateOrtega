package com.example.firebasefroga1

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class RegisterActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            RegisterPantaila()
        }
    }
}

@Composable
fun RegisterPantaila() {

    val context = LocalContext.current

    // Variables para los TextField
    var izena by rememberSaveable { mutableStateOf("") }
    var pasahitza by rememberSaveable { mutableStateOf("") }
    var erabiltzaile by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center
    ) {

        Text(
            text = "REGISTER",
            fontSize = 24.sp
        )

        Spacer(modifier = Modifier.height(20.dp))

        // IZENA
        OutlinedTextField(
            value = izena,
            onValueChange = { izena = it },
            label = { Text("Izena") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(10.dp))

        // PASAHITZA
        OutlinedTextField(
            value = pasahitza,
            onValueChange = { pasahitza = it },
            label = { Text("Pasahitza") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(10.dp))

        // ERABILTZAILE
        OutlinedTextField(
            value = erabiltzaile,
            onValueChange = { erabiltzaile = it },
            label = { Text("Erabiltzaile") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(10.dp))

        // PASSWORD
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(20.dp))

        // GORDE
        Button(
            onClick = {

                Toast.makeText(
                    context,
                    "Datuak gorde dira",
                    Toast.LENGTH_SHORT
                ).show()

                // Limpiar los campos
                izena = ""
                pasahitza = ""
                erabiltzaile = ""
                password = ""
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Gorde")
        }

        Spacer(modifier = Modifier.height(10.dp))

        // ITZULI
        Button(
            onClick = {

                val intent = Intent(
                    context,
                    MainActivity::class.java
                )

                context.startActivity(intent)
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Itzuli")
        }
    }
}