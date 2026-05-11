package com.example.restauranteapp

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.restauranteapp.data.AppDatabase
import com.example.restauranteapp.model.DetallePedido
import kotlinx.coroutines.launch
import androidx.core.widget.addTextChangedListener

class DetallePedidoActivity : AppCompatActivity() {

    private lateinit var spPlatos: Spinner
    private lateinit var etCantidad: EditText
    private lateinit var etObservaciones: EditText
    private lateinit var spEstado: Spinner
    private lateinit var tvPrecioTotal: TextView
    private lateinit var tvInfoPedido: TextView
    private lateinit var btnAgregarDetalle: Button
    private lateinit var linearDetalles: LinearLayout

    private var pedidoId: Int = 0
    private val db by lazy { AppDatabase.getDatabase(this) }
    private var listaPlatos = listOf<String>()
    private var preciosPlatos = mapOf<String, Double>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalle_pedido)

        // Enlaces con los elementos del XML
        spPlatos = findViewById(R.id.spPlatos)
        etCantidad = findViewById(R.id.etCantidad)
        etObservaciones = findViewById(R.id.etObservaciones)
        spEstado = findViewById(R.id.spEstadoDetalle)
        tvPrecioTotal = findViewById(R.id.tvPrecioTotal)
        tvInfoPedido = findViewById(R.id.tvInfoPedido)
        btnAgregarDetalle = findViewById(R.id.btnAgregarDetalle)
        linearDetalles = findViewById(R.id.linearDetalles)

        pedidoId = intent.getIntExtra("pedidoId", 0)

        // 🔹 Nuevo paso: mostrar información del pedido actual
        mostrarInfoPedido()

        cargarPlatos()
        cargarDetalles()

        btnAgregarDetalle.setOnClickListener { guardarDetalle() }

        spPlatos.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: android.view.View?, position: Int, id: Long) {
                calcularPrecioTotal()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        etCantidad.addTextChangedListener {
            calcularPrecioTotal()
        }
    }

    private fun mostrarInfoPedido() {
        lifecycleScope.launch {
            val pedido = db.pedidoDAO().obtenerPedidoPorId(pedidoId)
            pedido?.let {
                runOnUiThread {
                    tvInfoPedido.text = "🪑 Mesa: ${it.mesa}  |  📅 Fecha: ${it.fecha}"
                }
            }
        }
    }

    private fun cargarPlatos() {
        lifecycleScope.launch {
            val platos = db.platoDAO().obtenerPlatos()
            listaPlatos = platos.map { it.nombre }
            preciosPlatos = platos.associate { it.nombre to it.precio }

            runOnUiThread {
                val adaptador = ArrayAdapter(this@DetallePedidoActivity, android.R.layout.simple_spinner_item, listaPlatos)
                adaptador.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                spPlatos.adapter = adaptador
            }
        }
    }

    private fun calcularPrecioTotal() {
        val cantidad = etCantidad.text.toString().toIntOrNull() ?: 0
        val platoSeleccionado = spPlatos.selectedItem?.toString() ?: return
        val precioUnitario = preciosPlatos[platoSeleccionado] ?: 0.0
        val total = cantidad * precioUnitario
        tvPrecioTotal.text = "💲 Total: %.2f".format(total)
    }

    private fun guardarDetalle() {
        val platoSeleccionado = spPlatos.selectedItem?.toString() ?: ""
        val cantidad = etCantidad.text.toString().toIntOrNull()
        val observaciones = etObservaciones.text.toString()
        val estado = spEstado.selectedItem.toString()
        val precioUnitario = preciosPlatos[platoSeleccionado] ?: 0.0
        val total = (cantidad ?: 0) * precioUnitario

        if (cantidad == null || cantidad <= 0) {
            Toast.makeText(this, "Ingrese una cantidad válida", Toast.LENGTH_SHORT).show()
            return
        }

        val detalle = DetallePedido(
            pedidoId = pedidoId,
            cantidad = cantidad,
            observaciones = observaciones,
            platoNombre = platoSeleccionado,
            estado = estado,
            precioTotal = total
        )

        lifecycleScope.launch {
            db.detallePedidoDAO().insertar(detalle)
            runOnUiThread {
                Toast.makeText(this@DetallePedidoActivity, "Detalle agregado ✅", Toast.LENGTH_SHORT).show()
                etCantidad.text.clear()
                etObservaciones.text.clear()
                spEstado.setSelection(0)
                cargarDetalles()
            }
        }
    }

    private fun cargarDetalles() {
        lifecycleScope.launch {
            val detalles = db.detallePedidoDAO().obtenerDetallesPorPedido(pedidoId)
            runOnUiThread {
                linearDetalles.removeAllViews()
                detalles.forEach { detalle ->
                    val tv = TextView(this@DetallePedidoActivity).apply {
                        text = "🍽️ ${detalle.platoNombre} - Cant: ${detalle.cantidad} - ${detalle.estado}\nObs: ${detalle.observaciones}\n💰 Total: $${detalle.precioTotal}"
                        setPadding(0, 12, 0, 12)
                        setTextColor(resources.getColor(android.R.color.white))
                    }
                    linearDetalles.addView(tv)
                }
            }
        }
    }
}
