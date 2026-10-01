package com.example.firebasefroga1

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.firebase.firestore.FirebaseFirestore

class IkasleActivity : ComponentActivity() {

    private val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            IkaslePantaila(db)
        }
    }
}

data class Ikaslea(
    val nan: String = "",
    val izena: String = "",
    val abizena: String = ""
)

@Composable
fun IkaslePantaila(db: FirebaseFirestore) {

    val context = LocalContext.current

    // Mantener los valores de los campos
    var nan by rememberSaveable { mutableStateOf("") }
    var izena by rememberSaveable { mutableStateOf("") }
    var abizena by rememberSaveable { mutableStateOf("") }
    var datuak by rememberSaveable { mutableStateOf("") }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        item {
            Text(
                text = "IKASLEAK",
                fontSize = 24.sp
            )

            Spacer(modifier = Modifier.height(20.dp))

            // NAN
            OutlinedTextField(
                value = nan,
                onValueChange = { nan = it },
                label = { Text("NAN") },
                modifier = Modifier.fillMaxWidth()
            )

            // IZENA
            OutlinedTextField(
                value = izena,
                onValueChange = { izena = it },
                label = { Text("Izena") },
                modifier = Modifier.fillMaxWidth()
            )

            // ABIZENA
            OutlinedTextField(
                value = abizena,
                onValueChange = { abizena = it },
                label = { Text("Abizena") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = datuak,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(20.dp))

            // GORDE
            Button(
                onClick = {
                    val ikaslea = Ikaslea(
                        nan = nan,
                        izena = izena,
                        abizena = abizena
                    )

                    db.collection("ikasleak")
                        .add(ikaslea)
                        .addOnSuccessListener {
                            Toast.makeText(
                                context,
                                "Datuak gorde dira",
                                Toast.LENGTH_SHORT
                            ).show()

                            // LIMPIAR LOS TEXTFIELD
                            nan = ""
                            izena = ""
                            abizena = ""
                        }
                        .addOnFailureListener {
                            Toast.makeText(
                                context,
                                "Datuak ez dira gorde",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                }
            ) {
                Text("Gorde")
            }

            Spacer(modifier = Modifier.height(5.dp))

            // EGUNERATU
            Button(
                onClick = {
                    if (nan.isBlank()) {
                        Toast.makeText(
                            context,
                            "Sartu NAN-a",
                            Toast.LENGTH_SHORT
                        ).show()

                    } else {
                        db.collection("ikasleak")
                            .whereEqualTo("nan", nan)
                            .get()
                            .addOnSuccessListener { dokumentuak ->

                                if (dokumentuak.isEmpty) {
                                    Toast.makeText(
                                        context,
                                        "Ez da ikaslea aurkitu",
                                        Toast.LENGTH_SHORT
                                    ).show()

                                } else {
                                    for (document in dokumentuak) {
                                        db.collection("ikasleak")
                                            .document(document.id)
                                            .update(
                                                mapOf(
                                                    "izena" to izena,
                                                    "abizena" to abizena
                                                )
                                            )
                                            .addOnSuccessListener {
                                                Toast.makeText(
                                                    context,
                                                    "Ikaslea eguneratu da",
                                                    Toast.LENGTH_SHORT
                                                ).show()

                                                // LIMPIAR LOS TEXTFIELD
                                                nan = ""
                                                izena = ""
                                                abizena = ""
                                            }
                                            .addOnFailureListener {
                                                Toast.makeText(
                                                    context,
                                                    "Ikaslea ez da eguneratu",
                                                    Toast.LENGTH_SHORT
                                                ).show()
                                            }
                                    }
                                }
                            }
                            .addOnFailureListener {
                                Toast.makeText(
                                    context,
                                    "Errorea ikaslea bilatzean",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                    }
                }
            ) {
                Text("Eguneratu")
            }
            Spacer(modifier = Modifier.height(5.dp))

            // EZABATU
            Button(
                onClick = {
                    if (nan.isBlank()) {
                        Toast.makeText(
                            context,
                            "Sartu NAN-a",
                            Toast.LENGTH_SHORT
                        ).show()

                    } else {
                        db.collection("ikasleak")
                            .whereEqualTo("nan", nan)
                            .get()
                            .addOnSuccessListener { dokumentuak ->
                                if (dokumentuak.isEmpty) {
                                    Toast.makeText(
                                        context,
                                        "Ez da ikaslea aurkitu",
                                        Toast.LENGTH_SHORT
                                    ).show()

                                } else {
                                    val batch = db.batch()
                                    for (document in dokumentuak) {
                                        batch.delete(document.reference)
                                    }

                                    batch.commit()
                                        .addOnSuccessListener {

                                            Toast.makeText(
                                                context,
                                                "Ikaslea ezabatu da",
                                                Toast.LENGTH_SHORT
                                            ).show()

                                            // LIMPIAR LOS TEXTFIELD
                                            nan = ""
                                            izena = ""
                                            abizena = ""
                                        }
                                        .addOnFailureListener {

                                            Toast.makeText(
                                                context,
                                                "Ikaslea ez da ezabatu",
                                                Toast.LENGTH_SHORT
                                            ).show()
                                        }
                                }
                            }
                            .addOnFailureListener {

                                Toast.makeText(
                                    context,
                                    "Errorea ikaslea bilatzean",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                    }
                }
            ) {
                Text("Ezabatu")
            }

            Spacer(modifier = Modifier.height(5.dp))

            // ERAKUTSI
            Button(
                onClick = {
                    db.collection("ikasleak")
                        .get()
                        .addOnSuccessListener { ikasleak ->

                            datuak = ikasleak.documents.joinToString(
                                separator = "\n\n"
                            ) { ikaslea ->

                                """
                                NAN: ${ikaslea.getString("nan") ?: ""}
                                Izena: ${ikaslea.getString("izena") ?: ""}
                                Abizena: ${ikaslea.getString("abizena") ?: ""}
                                """.trimIndent()
                            }
                            if (ikasleak.isEmpty) {
                                datuak = "Ez dago ikaslerik"
                            }
                            // LIMPIAR LOS TEXTFIELD
                            nan = ""
                            izena = ""
                            abizena = ""
                        }
                        .addOnFailureListener { e ->

                            datuak = "Errorea: ${e.message}"
                        }
                }
            ) {
                Text("Erakutsi")
            }

            Spacer(modifier = Modifier.height(5.dp))

            // ITZULI
            Button(
                onClick = {
                    // LIMPIAR LOS TEXTFIELD
                    nan = ""
                    izena = ""
                    abizena = ""

                    val intent = Intent(
                        context,
                        HasieraActivity::class.java
                    )

                    context.startActivity(intent)
                }
            ) {
                Text("Itzuli")
            }
        }
    }
}