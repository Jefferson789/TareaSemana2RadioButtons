package com.example.ejerciciosradiobuttons

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.util.Locale

class PropinasActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_propinas)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.mainPropinas)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val etMonto = findViewById<EditText>(R.id.etMonto)
        val rgPorcentaje = findViewById<RadioGroup>(R.id.rgPorcentaje)
        val btnCalcularPropina = findViewById<Button>(R.id.btnCalcularPropina)
        val btnLimpiarPropinas = findViewById<Button>(R.id.btnLimpiarPropinas)
        val tvResultadoPropinas = findViewById<TextView>(R.id.tvResultadoPropinas)

        btnCalcularPropina.setOnClickListener {

            val textoMonto = etMonto.text.toString().trim()

            // Validamos que se haya ingresado un monto
            if (textoMonto.isEmpty()) {
                Toast.makeText(this, "Ingresa el monto de la cuenta", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val monto = textoMonto.toDoubleOrNull()

            if (monto == null || monto <= 0) {
                Toast.makeText(this, "Ingresa un monto válido mayor a 0", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Validamos que se haya elegido un porcentaje
            val idSeleccionado = rgPorcentaje.checkedRadioButtonId

            if (idSeleccionado == -1) {
                Toast.makeText(this, "Selecciona un porcentaje de propina", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val porcentaje = when (idSeleccionado) {
                R.id.rbDiez -> 10
                R.id.rbQuince -> 15
                R.id.rbVeinte -> 20
                else -> 0
            }

            val propina = monto * porcentaje / 100.0
            val total = monto + propina

            val resultado = StringBuilder()
            resultado.append("Monto de la cuenta: ").append(formatear(monto)).append("\n")
            resultado.append("Porcentaje seleccionado: ").append(porcentaje).append("%\n")
            resultado.append("Propina: ").append(formatear(propina)).append("\n")
            resultado.append("Total a pagar: ").append(formatear(total))

            tvResultadoPropinas.text = resultado.toString()
        }

        btnLimpiarPropinas.setOnClickListener {
            etMonto.text.clear()
            rgPorcentaje.clearCheck()
            tvResultadoPropinas.text = "Aquí aparecerá el cálculo de la propina."
        }
    }

    private fun formatear(valor: Double): String {
        return String.format(Locale.US, "%.2f", valor)
    }
}