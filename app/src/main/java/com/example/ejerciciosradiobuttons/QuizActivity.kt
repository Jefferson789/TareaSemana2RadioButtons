package com.example.ejerciciosradiobuttons

import android.os.Bundle
import android.widget.Button
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class QuizActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_quiz)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.mainQuiz)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val rgPregunta1 = findViewById<RadioGroup>(R.id.rgPregunta1)
        val rgPregunta2 = findViewById<RadioGroup>(R.id.rgPregunta2)
        val rgPregunta3 = findViewById<RadioGroup>(R.id.rgPregunta3)
        val btnCalcularPuntaje = findViewById<Button>(R.id.btnCalcularPuntaje)
        val btnReiniciarQuiz = findViewById<Button>(R.id.btnReiniciarQuiz)
        val tvResultadoQuiz = findViewById<TextView>(R.id.tvResultadoQuiz)

        btnCalcularPuntaje.setOnClickListener {

            // Validamos que las tres preguntas estén respondidas
            if (rgPregunta1.checkedRadioButtonId == -1 ||
                rgPregunta2.checkedRadioButtonId == -1 ||
                rgPregunta3.checkedRadioButtonId == -1
            ) {
                Toast.makeText(
                    this,
                    "Responde todas las preguntas antes de calcular",
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }

            var puntaje = 0

            val acierto1 = rgPregunta1.checkedRadioButtonId == R.id.rbP1Kotlin
            val acierto2 = rgPregunta2.checkedRadioButtonId == R.id.rbP2RadioButton
            val acierto3 = rgPregunta3.checkedRadioButtonId == R.id.rbP3Xml

            if (acierto1) puntaje++
            if (acierto2) puntaje++
            if (acierto3) puntaje++

            val mensajeFinal = when (puntaje) {
                3 -> "Excelente, respondiste todo correctamente."
                2 -> "Muy bien, solo fallaste una."
                1 -> "Puedes mejorar, repasa los conceptos."
                else -> "Revisa los temas e inténtalo de nuevo."
            }

            val resultado = StringBuilder()
            resultado.append("Puntaje final: $puntaje de 3\n\n")
            resultado.append("Pregunta 1: ").append(if (acierto1) "Correcta" else "Incorrecta (Kotlin)").append("\n")
            resultado.append("Pregunta 2: ").append(if (acierto2) "Correcta" else "Incorrecta (RadioButton)").append("\n")
            resultado.append("Pregunta 3: ").append(if (acierto3) "Correcta" else "Incorrecta (XML de layout)").append("\n\n")
            resultado.append(mensajeFinal)

            tvResultadoQuiz.text = resultado.toString()
        }

        btnReiniciarQuiz.setOnClickListener {
            rgPregunta1.clearCheck()
            rgPregunta2.clearCheck()
            rgPregunta3.clearCheck()
            tvResultadoQuiz.text = "Aquí aparecerá tu puntaje final."
        }
    }
}