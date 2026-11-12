package com.example.ejerciciosradiobuttons

import android.content.Context
import android.os.Bundle
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class TemaActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_tema)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.mainTema)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val rgTema = findViewById<RadioGroup>(R.id.rgTema)
        val rbClaro = findViewById<RadioButton>(R.id.rbClaro)
        val rbOscuro = findViewById<RadioButton>(R.id.rbOscuro)
        val rbSistema = findViewById<RadioButton>(R.id.rbSistema)
        val btnAplicarTema = findViewById<Button>(R.id.btnAplicarTema)
        val tvTemaActual = findViewById<TextView>(R.id.tvTemaActual)

        val prefs = getSharedPreferences("configuracion_tema", Context.MODE_PRIVATE)
        val temaGuardado = prefs.getInt("modo_tema", AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)

        when (temaGuardado) {
            AppCompatDelegate.MODE_NIGHT_NO -> rbClaro.isChecked = true
            AppCompatDelegate.MODE_NIGHT_YES -> rbOscuro.isChecked = true
            AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM -> rbSistema.isChecked = true
        }

        mostrarTemaActual(tvTemaActual, temaGuardado)

        btnAplicarTema.setOnClickListener {

            val modoSeleccionado = when (rgTema.checkedRadioButtonId) {
                R.id.rbClaro -> AppCompatDelegate.MODE_NIGHT_NO
                R.id.rbOscuro -> AppCompatDelegate.MODE_NIGHT_YES
                R.id.rbSistema -> AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
                else -> AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
            }

            prefs.edit().putInt("modo_tema", modoSeleccionado).apply()

            AppCompatDelegate.setDefaultNightMode(modoSeleccionado)

            mostrarTemaActual(tvTemaActual, modoSeleccionado)
        }
    }

    private fun mostrarTemaActual(textView: TextView, modo: Int) {
        val texto = when (modo) {
            AppCompatDelegate.MODE_NIGHT_NO -> "Tema actual: Claro"
            AppCompatDelegate.MODE_NIGHT_YES -> "Tema actual: Oscuro"
            AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM -> "Tema actual: Sistema"
            else -> "Tema actual: Sistema"
        }

        textView.text = texto
    }
}