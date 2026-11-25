package com.example.app_movil_tecnoventas.Vista

import android.app.Activity
import android.content.Intent
import android.widget.Button
import android.widget.Toast
import com.example.app_movil_tecnoventas.R

object BottomNavHelper {

    fun configurarBarra(activity: Activity, pantallaActual: String) {
        val btnInicio = activity.findViewById<Button>(R.id.navInicio)
        val btnCatalogo = activity.findViewById<Button>(R.id.navCatalogo)
        val btnBolsa = activity.findViewById<Button>(R.id.navBolsa)
        val btnPerfil = activity.findViewById<Button>(R.id.navPerfil)

        btnInicio?.setOnClickListener {
            if (pantallaActual != "INICIO") {
                activity.startActivity(Intent(activity, MainActivity::class.java))
                activity.finish()
            }
        }

        btnCatalogo?.setOnClickListener {
            if (pantallaActual != "CATALOGO") {
                activity.startActivity(Intent(activity, ComprarActivity::class.java))
                activity.finish()
            }
        }

        btnBolsa?.setOnClickListener {
            abrirModuloEquipo(
                activity,
                "com.example.app_movil_tecnoventas.Vista.BolsaActivity",
                "Módulo de Bolsa (Ángel David) pendiente de integrar"
            )
        }

        btnPerfil?.setOnClickListener {
            abrirModuloEquipo(
                activity,
                "com.example.app_movil_tecnoventas.Vista.PerfilActivity",
                "Módulo de Perfil (Oscar) pendiente de integrar"
            )
        }
    }

    fun abrirModuloEquipo(activity: Activity, className: String, mensajePendiente: String) {
        try {
            val clazz = Class.forName(className)
            activity.startActivity(Intent(activity, clazz))
        } catch (e: ClassNotFoundException) {
            Toast.makeText(activity, mensajePendiente, Toast.LENGTH_SHORT).show()
        }
    }
}