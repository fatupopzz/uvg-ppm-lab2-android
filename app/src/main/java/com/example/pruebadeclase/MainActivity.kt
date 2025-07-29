package com.example.pruebadeclase

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.background
import androidx.compose.material3.ButtonDefaults


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                TwoTextsScreen(
                    mainMessage = "Fatima Navarro Lab 2",
                    secondaryMessage = "Bienvenidos a mi programa",
                    modifier = Modifier.padding(innerPadding)
                )
            }
        }
    }
}

@Composable
fun TwoTextsScreen(
    mainMessage: String,
    secondaryMessage: String,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    var clickCount by remember { mutableIntStateOf(0) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFFF0FFF0))
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Título principal
        Text(
            text = mainMessage,
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            color = Color(0xFFE91E63),
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Mensaje de bienvenida
        Text(
            text = secondaryMessage,
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface
        )

        Spacer(modifier = Modifier.height(32.dp))

        // Botón interactivo
        Button(
            onClick = {
                clickCount++
                showToastMessage(context, clickCount)
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFFE91E63)
            )
        ) {
            Text(
                text = getButtonText(clickCount),
                style = MaterialTheme.typography.labelLarge

            )
        }
    }
}

// Función para generar el texto del botón
fun getButtonText(clickCount: Int): String {
    return if (clickCount == 0) {
        "¡Haz clic aquí!"
    } else {
        "Clics: $clickCount"
    }
}

// Función para mostrar el Toast
fun showToastMessage(context: android.content.Context, clickCount: Int) {
    val message = when {
        clickCount == 1 -> "¡Hola Fatima! Bienvenida al Lab 2"
        clickCount <= 3 -> "¡Has hecho $clickCount clics"
        clickCount <= 7 -> "¡Sigue así! $clickCount clics"
        else -> "Ya van $clickCount clics"
    }
    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
}

@Preview(showBackground = true)
@Composable
fun TwoTextsScreenPreview() {
    TwoTextsScreen(
        mainMessage = "Fatima Navarro Lab 2",
        secondaryMessage = "Bienvenidos a mi programa"
    )
}
