package com.example.ejerciciosradiobuttons

import android.app.Application
import android.content.Context
import androidx.appcompat.app.AppCompatDelegate

class AppTema : Application() {

    override fun onCreate() {
        super.onCreate()

        val prefs = getSharedPreferences("configuracion_tema", Context.MODE_PRIVATE)

        val temaGuardado = prefs.getInt(
            "modo_tema",
            AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
        )

        AppCompatDelegate.setDefaultNightMode(temaGuardado)
    }
}