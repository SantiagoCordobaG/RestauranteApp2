package com.example.restauranteapp

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.restauranteapp.data.AppDatabase
import com.example.restauranteapp.data.ExamenParametroDAO
import com.example.restauranteapp.model.ExamenParametro
import kotlinx.coroutines.launch

class ExamenParametrosActivity : AppCompatActivity() {

    private lateinit var tvTituloExamen: TextView
    private lateinit var etParametroId: EditText
    private lateinit var etConsecutivo: EditText
    private lateinit var btnGuardarParametro: Button
    private lateinit var btnVolverExamenes: Button
    private lateinit var btnVolverInicio: Button
    private lateinit var linearExamenParametros: LinearLayout

    private lateinit var dao: ExamenParametroDAO

    private var examenId: Int = 0
    private var examenDescripcion: String = ""

    private val cantidadVistasFijas = 6

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_examen_parametros)

        dao = AppDatabase.getDatabase(this).examenParametroDAO()

        examenId = intent.getIntExtra("exa_id", 0)
        examenDescripcion = intent.getStringExtra("exa_descripcion") ?: "Examen"

        tvTituloExamen = findViewById(R.id.tvTituloExamen)
        etParametroId = findViewById(R.id.etParametroId)
        etConsecutivo = findViewById(R.id.etConsecutivo)
        btnGuardarParametro = findViewById(R.id.btnGuardarParametro)
        btnVolverExamenes = findViewById(R.id.btnVolverExamenes)
        btnVolverInicio = findViewById(R.id.btnVolverInicioParametros)
        linearExamenParametros = findViewById(R.id.linearExamenParametros)

        tvTituloExamen.text = "Parámetros de: $examenDescripcion"

        btnGuardarParametro.setOnClickListener {
            guardarParametro()
        }

        btnVolverExamenes.setOnClickListener {
            finish()
        }

        btnVolverInicio.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        cargarParametros()
    }

    private fun guardarParametro() {
        val parametroId = etParametroId.text.toString().toIntOrNull()
        val consecutivo = etConsecutivo.text.toString().toIntOrNull()

        if (examenId == 0) {
            Toast.makeText(this, "No se encontró el examen", Toast.LENGTH_SHORT).show()
            return
        }

        if (parametroId == null || consecutivo == null) {
            Toast.makeText(this, "Complete los campos correctamente", Toast.LENGTH_SHORT).show()
            return
        }

        lifecycleScope.launch {
            dao.insertar(
                ExamenParametro(
                    ep_exa_id = examenId,
                    ep_para_id = parametroId,
                    ep_consecutivo = consecutivo
                )
            )

            runOnUiThread {
                Toast.makeText(
                    this@ExamenParametrosActivity,
                    "Parámetro agregado",
                    Toast.LENGTH_SHORT
                ).show()

                etParametroId.text.clear()
                etConsecutivo.text.clear()
            }

            cargarParametros()
        }
    }

    private fun cargarParametros() {
        lifecycleScope.launch {
            val parametros = dao.obtenerParametrosPorExamen(examenId)

            runOnUiThread {
                if (linearExamenParametros.childCount > cantidadVistasFijas) {
                    linearExamenParametros.removeViews(
                        cantidadVistasFijas,
                        linearExamenParametros.childCount - cantidadVistasFijas
                    )
                }

                parametros.forEach { parametro ->
                    val tv = TextView(this@ExamenParametrosActivity).apply {
                        text = """
                            Parámetro ID: ${parametro.ep_para_id}
                            Consecutivo: ${parametro.ep_consecutivo}
                        """.trimIndent()

                        setTextColor(Color.WHITE)
                        textSize = 15f
                        setPadding(8, 8, 8, 8)
                    }

                    linearExamenParametros.addView(tv)
                }
            }
        }
    }
}