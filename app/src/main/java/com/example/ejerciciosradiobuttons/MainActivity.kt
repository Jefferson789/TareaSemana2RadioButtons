package com.example.ejerciciosradiobuttons

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val btnEncuesta = findViewById<Button>(R.id.btnEncuesta)
        val btnTema = findViewById<Button>(R.id.btnTema)
        val btnQuiz = findViewById<Button>(R.id.btnQuiz)
        val btnPropinas = findViewById<Button>(R.id.btnPropinas)
        val btnConversor = findViewById<Button>(R.id.btnConversor)

        btnEncuesta.setOnClickListener {
            val intent = Intent(this, EncuestaActivity::class.java)
            startActivity(intent)
        }

        btnTema.setOnClickListener {
            val intent = Intent(this, TemaActivity::class.java)
            startActivity(intent)
        }

        btnQuiz.setOnClickListener {
            val intent = Intent(this, QuizActivity::class.java)
            startActivity(intent)
        }

        btnPropinas.setOnClickListener {
            val intent = Intent(this, PropinasActivity::class.java)
            startActivity(intent)
        }

        btnConversor.setOnClickListener {
            val intent = Intent(this, ConversorActivity::class.java)
            startActivity(intent)
        }
    }
}