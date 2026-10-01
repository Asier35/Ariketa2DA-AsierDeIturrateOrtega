package com.example.firebasefroga1

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.google.firebase.auth.FirebaseAuth

class HasieraActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {

            val usuario = FirebaseAuth.getInstance().currentUser
            val email = usuario?.email ?: "Ez dago emailik"

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(30.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Text(
                    text = "Ongi etorri $email"
                )
                Button(
                    onClick = {
                        val intent = Intent(
                            this@HasieraActivity,
                            IkasleActivity::class.java
                        )

                        startActivity(intent)
                        finish()
                    }
                ) {
                    Text("IKASLEAK")
                }


                Button(
                    onClick = {
                        val intent = Intent(
                            this@HasieraActivity,
                            MainActivity::class.java
                        )

                        startActivity(intent)
                        finish()
                    }
                ) {
                    Text("ITZULI")
                }
            }
        }
    }
}