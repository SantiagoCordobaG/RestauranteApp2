package com.example.restauranteapp

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.restauranteapp.data.AppDatabase
import com.example.restauranteapp.data.ExamenDAO
import com.example.restauranteapp.model.Examen
import kotlinx.coroutines.launch

class ExamenActivity : AppCompatActivity() {

    private lateinit var etDescripcion: EditText
    private lateinit var etValor: EditText
    private lateinit var spTipo: Spinner
    private lateinit var spEstado: Spinner
    private lateinit var btnGuardar: Button
    private lateinit var btnVolverInicio: Button
    private lateinit var linearExamenes: LinearLayout

    private lateinit var dao: ExamenDAO

    private var examenActualId: Int? = null

    private val cantidadVistasFijas = 8

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_examen)

        dao = AppDatabase.getDatabase(this).examenDAO()

        etDescripcion = findViewById(R.id.etDescripcionExamen)
        etValor = findViewById(R.id.etValorExamen)
        spTipo = findViewById(R.id.spTipoExamen)
        spEstado = findViewById(R.id.spEstadoExamen)
        btnGuardar = findViewById(R.id.btnGuardarExamen)
        btnVolverInicio = findViewById(R.id.btnVolverInicio)
        linearExamenes = findViewById(R.id.linearExamenes)

        btnGuardar.setOnClickListener {
            guardarOActualizarExamen()
        }

        btnVolverInicio.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        cargarExamenes()
    }

    private fun guardarOActualizarExamen() {
        val descripcion = etDescripcion.text.toString().trim()
        val valor = etValor.text.toString().toIntOrNull()
        val tipo = spTipo.selectedItem.toString()
        val estado = spEstado.selectedItem.toString()

        if (descripcion.isEmpty() || valor == null) {
            Toast.makeText(this, "Complete los campos correctamente", Toast.LENGTH_SHORT).show()
            return
        }

        lifecycleScope.launch {
            if (examenActualId == null) {
                dao.insertar(
                    Examen(
                        exa_descripcion = descripcion,
                        exa_valor = valor,
                        exa_tipo = tipo,
                        exa_estado = estado
                    )
                )

                runOnUiThread {
                    Toast.makeText(this@ExamenActivity, "Examen guardado", Toast.LENGTH_SHORT).show()
                }
            } else {
                dao.actualizar(
                    Examen(
                        exa_id = examenActualId!!,
                        exa_descripcion = descripcion,
                        exa_valor = valor,
                        exa_tipo = tipo,
                        exa_estado = estado
                    )
                )

                runOnUiThread {
                    Toast.makeText(this@ExamenActivity, "Examen actualizado", Toast.LENGTH_SHORT).show()
                    btnGuardar.text = "Guardar Examen"
                }

                examenActualId = null
            }

            limpiarCampos()
            cargarExamenes()
        }
    }

    private fun cargarExamenes() {
        lifecycleScope.launch {
            val examenes = dao.obtenerExamenes()

            runOnUiThread {
                if (linearExamenes.childCount > cantidadVistasFijas) {
                    linearExamenes.removeViews(
                        cantidadVistasFijas,
                        linearExamenes.childCount - cantidadVistasFijas
                    )
                }

                examenes.forEach { examen ->
                    val fila = LinearLayout(this@ExamenActivity).apply {
                        orientation = LinearLayout.VERTICAL
                        setPadding(8, 8, 8, 8)
                    }

                    val tv = TextView(this@ExamenActivity).apply {
                        text = """
                            ${examen.exa_descripcion}
                            Valor: $${examen.exa_valor}
                            Tipo: ${examen.exa_tipo}
                            Estado: ${examen.exa_estado}
                        """.trimIndent()

                        setTextColor(Color.WHITE)
                        textSize = 15f
                    }

                    val filaBotones = LinearLayout(this@ExamenActivity).apply {
                        orientation = LinearLayout.HORIZONTAL
                    }

                    val btnEditar = Button(this@ExamenActivity).apply {
                        text = "Editar"
                        layoutParams = LinearLayout.LayoutParams(
                            0,
                            LinearLayout.LayoutParams.WRAP_CONTENT,
                            1f
                        )

                        setOnClickListener {
                            cargarExamenEnCampos(examen)
                        }
                    }

                    filaBotones.addView(btnEditar)

                    fila.addView(tv)
                    fila.addView(filaBotones)

                    linearExamenes.addView(fila)
                }
            }
        }
    }

    private fun cargarExamenEnCampos(examen: Examen) {
        examenActualId = examen.exa_id

        etDescripcion.setText(examen.exa_descripcion)
        etValor.setText(examen.exa_valor.toString())

        spTipo.setSelection(
            when (examen.exa_tipo) {
                "Laboratorio" -> 0
                "Imagenología" -> 1
                "Clínico" -> 2
                "Otro" -> 3
                else -> 0
            }
        )

        spEstado.setSelection(
            when (examen.exa_estado) {
                "Activo" -> 0
                "Inactivo" -> 1
                else -> 0
            }
        )

        btnGuardar.text = "Actualizar Examen"
    }

    private fun limpiarCampos() {
        etDescripcion.text.clear()
        etValor.text.clear()
        spTipo.setSelection(0)
        spEstado.setSelection(0)
        examenActualId = null
    }
}