package com.example.pruebadeclase

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Modifier

private object ConstantesApp {
    const val NOMBRE_AUTOR = "Fatima Navarro"
    const val NUMERO_LAB = "Lab 2"
    const val MENSAJE_BIENVENIDA = "Bienvenidos a mi programa"
    const val TEXTO_BOTON_INICIAL = "¡Haz clic aquí!"
    const val MENSAJE_TOAST_BIENVENIDA = "¡Hola! Bienvenido al Lab 2"
    const val MENSAJE_TOAST_PRIMEROS_CLICS = "¡Has hecho"
    const val MENSAJE_TOAST_CLICS_ACTIVOS = "¡Sigue así!"
    const val MENSAJE_TOAST_MUCHOS_CLICS = "Ya van"
    const val ETIQUETA_CLICS = "Clics:"

    val COLOR_PRIMARIO = Color(0xFFE91E63)
    val COLOR_FONDO = Color(0xFFF0FFF0)

    val PADDING_PANTALLA = 16.dp
    val ESPACIADO_MEDIO = 24.dp
    val ESPACIADO_GRANDE = 32.dp

    const val CONTADOR_INICIAL = 0
    const val UMBRAL_CLIC_BIENVENIDA = 1
    const val MAXIMO_CLICS_TEMPRANOS = 3
    const val MAXIMO_CLICS_ACTIVOS = 7
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppBienvenida()
        }
    }
}

@Composable
private fun AppBienvenida() {
    Scaffold(modifier = Modifier.fillMaxSize()) { paddingInterno ->
        PantallaBienvenida(modifier = Modifier.padding(paddingInterno))
    }
}

@Composable
private fun PantallaBienvenida(modifier: Modifier = Modifier) {
    val contexto = LocalContext.current
    var contadorClics by remember { mutableIntStateOf(ConstantesApp.CONTADOR_INICIAL) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(ConstantesApp.COLOR_FONDO)
            .padding(ConstantesApp.PADDING_PANTALLA),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        TextoTitulo()

        Spacer(modifier = Modifier.height(ConstantesApp.ESPACIADO_MEDIO))

        TextoBienvenida()

        Spacer(modifier = Modifier.height(ConstantesApp.ESPACIADO_GRANDE))

        BotonInteractivo(
            contadorClics = contadorClics,
            alHacerClic = {
                contadorClics = incrementarContador(contadorClics)
                mostrarRetroalimentacion(contexto, contadorClics)
            }
        )
    }
}

@Composable
private fun TextoTitulo() {
    Text(
        text = construirTextoTitulo(),
        style = MaterialTheme.typography.headlineLarge,
        fontWeight = FontWeight.Bold,
        textAlign = TextAlign.Center,
        color = ConstantesApp.COLOR_PRIMARIO
    )
}

@Composable
private fun TextoBienvenida() {
    Text(
        text = ConstantesApp.MENSAJE_BIENVENIDA,
        style = MaterialTheme.typography.bodyLarge,
        textAlign = TextAlign.Center,
        fontWeight = FontWeight.Bold,
        color = MaterialTheme.colorScheme.onSurface
    )
}

@Composable
private fun BotonInteractivo(
    contadorClics: Int,
    alHacerClic: () -> Unit
) {
    Button(
        onClick = alHacerClic,
        colors = ButtonDefaults.buttonColors(
            containerColor = ConstantesApp.COLOR_PRIMARIO
        )
    ) {
        Text(
            text = obtenerTextoBoton(contadorClics),
            style = MaterialTheme.typography.labelLarge
        )
    }
}

private fun construirTextoTitulo(): String {
    return "${ConstantesApp.NOMBRE_AUTOR} ${ConstantesApp.NUMERO_LAB}"
}

private fun incrementarContador(contadorActual: Int): Int {
    return contadorActual + 1
}

private fun obtenerTextoBoton(contadorClics: Int): String {
    return if (esEstadoInicial(contadorClics)) {
        ConstantesApp.TEXTO_BOTON_INICIAL
    } else {
        "${ConstantesApp.ETIQUETA_CLICS} $contadorClics"
    }
}

private fun mostrarRetroalimentacion(contexto: android.content.Context, contadorClics: Int) {
    val mensaje = generarMensajeRetroalimentacion(contadorClics)
    mostrarToast(contexto, mensaje)
}

private fun generarMensajeRetroalimentacion(contadorClics: Int): String {
    return when {
        esClicBienvenida(contadorClics) -> ConstantesApp.MENSAJE_TOAST_BIENVENIDA
        esClicTemprano(contadorClics) -> "${ConstantesApp.MENSAJE_TOAST_PRIMEROS_CLICS} $contadorClics clics"
        esClicActivo(contadorClics) -> "${ConstantesApp.MENSAJE_TOAST_CLICS_ACTIVOS} $contadorClics clics"
        else -> "${ConstantesApp.MENSAJE_TOAST_MUCHOS_CLICS} $contadorClics clics"
    }
}

private fun mostrarToast(contexto: android.content.Context, mensaje: String) {
    Toast.makeText(contexto, mensaje, Toast.LENGTH_SHORT).show()
}

private fun esEstadoInicial(contadorClics: Int): Boolean {
    return contadorClics == ConstantesApp.CONTADOR_INICIAL
}

private fun esClicBienvenida(contadorClics: Int): Boolean {
    return contadorClics == ConstantesApp.UMBRAL_CLIC_BIENVENIDA
}

private fun esClicTemprano(contadorClics: Int): Boolean {
    return contadorClics in 2..ConstantesApp.MAXIMO_CLICS_TEMPRANOS
}

private fun esClicActivo(contadorClics: Int): Boolean {
    return contadorClics in (ConstantesApp.MAXIMO_CLICS_TEMPRANOS + 1)..ConstantesApp.MAXIMO_CLICS_ACTIVOS
}

@Preview(showBackground = true)
@Composable
private fun PantallaBienvenidaPreview() {
    AppBienvenida()
}