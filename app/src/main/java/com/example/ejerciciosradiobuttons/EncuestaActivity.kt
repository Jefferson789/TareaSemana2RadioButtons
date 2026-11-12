package com.example.ejerciciosradiobuttons

import android.os.Bundle
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class EncuestaActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_encuesta)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.mainEncuesta)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val rgCalificacion = findViewById<RadioGroup>(R.id.rgCalificacion)
        val btnMostrarResumen = findViewById<Button>(R.id.btnMostrarResumen)
        val tvResultadoEncuesta = findViewById<TextView>(R.id.tvResultadoEncuesta)

        btnMostrarResumen.setOnClickListener {

            val idSeleccionado = rgCalificacion.checkedRadioButtonId

            if (idSeleccionado == -1) {
                Toast.makeText(
                    this,
                    "Por favor selecciona una calificación",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                val radioButtonSeleccionado = findViewById<RadioButton>(idSeleccionado)
                val calificacion = radioButtonSeleccionado.text.toString()

                val mensaje = when (calificacion) {
                    "Excelente" -> "Seleccionaste Excelente. El servicio fue muy satisfactorio."
                    "Bueno" -> "Seleccionaste Bueno. El servicio fue satisfactorio."
                    "Regular" -> "Seleccionaste Regular. El servicio puede mejorar."
                    "Malo" -> "Seleccionaste Malo. El servicio necesita mejoras."
                    else -> "Calificación seleccionada: $calificacion"
                }

                tvResultadoEncuesta.text = mensaje
            }
        }
    }
}