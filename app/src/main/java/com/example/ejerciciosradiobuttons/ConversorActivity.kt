package com.example.ejerciciosradiobuttons

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.util.Locale

class ConversorActivity : AppCompatActivity() {

    private val unidadesLongitud = arrayOf("Metros", "Kilómetros", "Millas")
    private val unidadesPeso = arrayOf("Kilogramos", "Gramos", "Libras")
    private val unidadesTemperatura = arrayOf("Celsius", "Fahrenheit", "Kelvin")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_conversor)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.mainConversor)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val rgTipoConversion = findViewById<RadioGroup>(R.id.rgTipoConversion)
        val layoutCampos = findViewById<LinearLayout>(R.id.layoutCampos)
        val tvDescripcionTipo = findViewById<TextView>(R.id.tvDescripcionTipo)
        val etValor = findViewById<EditText>(R.id.etValor)
        val rgUnidadOrigen = findViewById<RadioGroup>(R.id.rgUnidadOrigen)
        val rgUnidadDestino = findViewById<RadioGroup>(R.id.rgUnidadDestino)
        val btnConvertir = findViewById<Button>(R.id.btnConvertir)
        val btnLimpiarConversor = findViewById<Button>(R.id.btnLimpiarConversor)
        val tvResultadoConversor = findViewById<TextView>(R.id.tvResultadoConversor)

        val origen1 = findViewById<RadioButton>(R.id.rbOrigen1)
        val origen2 = findViewById<RadioButton>(R.id.rbOrigen2)
        val origen3 = findViewById<RadioButton>(R.id.rbOrigen3)
        val destino1 = findViewById<RadioButton>(R.id.rbDestino1)
        val destino2 = findViewById<RadioButton>(R.id.rbDestino2)
        val destino3 = findViewById<RadioButton>(R.id.rbDestino3)

        rgTipoConversion.setOnCheckedChangeListener { _, checkedId ->

            val unidades = when (checkedId) {
                R.id.rbLongitud -> unidadesLongitud
                R.id.rbPeso -> unidadesPeso
                R.id.rbTemperatura -> unidadesTemperatura
                else -> null
            }

            if (unidades == null) {
                layoutCampos.visibility = View.GONE
                return@setOnCheckedChangeListener
            }

            layoutCampos.visibility = View.VISIBLE

            origen1.text = unidades[0]
            origen2.text = unidades[1]
            origen3.text = unidades[2]
            destino1.text = unidades[0]
            destino2.text = unidades[1]
            destino3.text = unidades[2]

            tvDescripcionTipo.text = when (checkedId) {
                R.id.rbLongitud -> "Conversión de longitud: metros, kilómetros y millas."
                R.id.rbPeso -> "Conversión de peso: kilogramos, gramos y libras."
                else -> "Conversión de temperatura: Celsius, Fahrenheit y Kelvin."
            }

            etValor.hint = when (checkedId) {
                R.id.rbLongitud -> "Valor de longitud"
                R.id.rbPeso -> "Valor de peso"
                else -> "Valor de temperatura"
            }

            etValor.text.clear()
            rgUnidadOrigen.clearCheck()
            rgUnidadDestino.clearCheck()
            tvResultadoConversor.text = "Ingresa el valor y elige las unidades."
        }

        btnConvertir.setOnClickListener {

            val tipoId = rgTipoConversion.checkedRadioButtonId

            if (tipoId == -1) {
                Toast.makeText(this, "Selecciona el tipo de conversión", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val textoValor = etValor.text.toString().trim()

            if (textoValor.isEmpty()) {
                Toast.makeText(this, "Ingresa un valor a convertir", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val valor = textoValor.toDoubleOrNull()

            if (valor == null) {
                Toast.makeText(this, "Ingresa un número válido", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val idOrigen = rgUnidadOrigen.checkedRadioButtonId
            val idDestino = rgUnidadDestino.checkedRadioButtonId

            if (idOrigen == -1 || idDestino == -1) {
                Toast.makeText(this, "Selecciona la unidad de origen y destino", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val indiceOrigen = indiceUnidad(idOrigen)
            val indiceDestino = indiceUnidad(idDestino)

            val unidades = when (tipoId) {
                R.id.rbLongitud -> unidadesLongitud
                R.id.rbPeso -> unidadesPeso
                else -> unidadesTemperatura
            }

            val nombreOrigen = unidades[indiceOrigen]
            val nombreDestino = unidades[indiceDestino]

            val resultado = when (tipoId) {
                R.id.rbLongitud -> convertirLongitud(valor, indiceOrigen, indiceDestino)
                R.id.rbPeso -> convertirPeso(valor, indiceOrigen, indiceDestino)
                else -> convertirTemperatura(valor, indiceOrigen, indiceDestino)
            }

            val texto = StringBuilder()
            texto.append("Tipo: ").append(nombreTipo(tipoId)).append("\n")
            texto.append(formatear(valor)).append(" ").append(nombreOrigen)
            texto.append(" = ").append(formatear(resultado)).append(" ").append(nombreDestino)

            tvResultadoConversor.text = texto.toString()
        }

        btnLimpiarConversor.setOnClickListener {
            rgTipoConversion.clearCheck()
            rgUnidadOrigen.clearCheck()
            rgUnidadDestino.clearCheck()
            etValor.text.clear()
            layoutCampos.visibility = View.GONE
            tvResultadoConversor.text = "Selecciona un tipo de conversión para comenzar."
        }
    }

    private fun nombreTipo(tipoId: Int): String {
        return when (tipoId) {
            R.id.rbLongitud -> "Longitud"
            R.id.rbPeso -> "Peso"
            else -> "Temperatura"
        }
    }

    private fun indiceUnidad(id: Int): Int {
        return when (id) {
            R.id.rbOrigen1, R.id.rbDestino1 -> 0
            R.id.rbOrigen2, R.id.rbDestino2 -> 1
            else -> 2
        }
    }

    // Longitud: todo pasa primero a metros
    private fun convertirLongitud(valor: Double, origen: Int, destino: Int): Double {
        val enMetros = when (origen) {
            0 -> valor                  // metros
            1 -> valor * 1000.0         // kilómetros
            else -> valor * 1609.344    // millas
        }

        return when (destino) {
            0 -> enMetros
            1 -> enMetros / 1000.0
            else -> enMetros / 1609.344
        }
    }

    // Peso: todo pasa primero a kilogramos
    private fun convertirPeso(valor: Double, origen: Int, destino: Int): Double {
        val enKilogramos = when (origen) {
            0 -> valor                  // kilogramos
            1 -> valor / 1000.0         // gramos
            else -> valor * 0.45359237  // libras
        }

        return when (destino) {
            0 -> enKilogramos
            1 -> enKilogramos * 1000.0
            else -> enKilogramos / 0.45359237
        }
    }

    // Temperatura: todo pasa primero a Celsius
    private fun convertirTemperatura(valor: Double, origen: Int, destino: Int): Double {
        val enCelsius = when (origen) {
            0 -> valor                      // Celsius
            1 -> (valor - 32.0) * 5.0 / 9.0 // Fahrenheit
            else -> valor - 273.15          // Kelvin
        }

        return when (destino) {
            0 -> enCelsius
            1 -> enCelsius * 9.0 / 5.0 + 32.0
            else -> enCelsius + 273.15
        }
    }

    private fun formatear(valor: Double): String {
        return String.format(Locale.US, "%.2f", valor)
    }
}