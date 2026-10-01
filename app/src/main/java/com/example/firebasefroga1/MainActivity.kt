package com.example.firebasefroga1

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.example.firebasefroga1.ui.theme.FireBaseFroga1Theme
import com.google.firebase.auth.FirebaseAuth

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            FireBaseFroga1Theme {
                LoginScreen()
            }
        }
    }
}

@Composable
fun LoginScreen() {

    val context = LocalContext.current
    val auth = FirebaseAuth.getInstance()

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 50.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        // Campo Email
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = {
                Text("Email")
            }
        )

        // Campo Contraseña
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = {
                Text("Pasaitza")
            },
            visualTransformation = PasswordVisualTransformation()
        )

        // Botón Sartu
        Button(
            onClick = {
                kudeatuAuthentication(
                    email,
                    password,
                    auth,
                    context
                )
            }
        ) {
            Text("Sartu")
        }

        Button(
            onClick = {

                val intent = Intent(
                    context,
                    RegisterActivity::class.java
                )

                context.startActivity(intent)
            }
        ) {
            Text("Register")
        }
    }
}

fun kudeatuAuthentication(
    email: String,
    password: String,
    auth: FirebaseAuth,
    context: Context
) {
    auth.signInWithEmailAndPassword(email, password)
        .addOnCompleteListener { task ->

            if (task.isSuccessful) {

                Toast.makeText(
                    context,
                    "Login zuzena",
                    Toast.LENGTH_SHORT
                ).show()

                val intent = Intent(context, HasieraActivity::class.java)
                context.startActivity(intent)

            } else {

                Toast.makeText(
                    context,
                    "Errorea: ${task.exception?.message}",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
}