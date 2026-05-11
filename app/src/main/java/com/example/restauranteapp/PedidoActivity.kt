package com.example.restauranteapp

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.restauranteapp.data.AppDatabase
import com.example.restauranteapp.model.Pedido
import kotlinx.coroutines.launch

class PedidoActivity : AppCompatActivity() {
    private lateinit var etFecha: EditText
    private lateinit var etMesa: EditText
    private lateinit var spEstado: Spinner
    private lateinit var btnAgregar: Button
    private lateinit var linearPedidos: LinearLayout

    private val dao by lazy { AppDatabase.getDatabase(this).pedidoDAO() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pedido)

        etFecha = findViewById(R.id.etFecha)
        etMesa = findViewById(R.id.etMesa)
        spEstado = findViewById(R.id.spEstadoPedido)
        btnAgregar = findViewById(R.id.btnAgregarPedido)
        linearPedidos = findViewById(R.id.linearPedidos)

        btnAgregar.setOnClickListener { guardarPedido() }
        cargarPedidos()
    }

    private fun guardarPedido() {
        val fecha = etFecha.text.toString()
        val mesa = etMesa.text.toString().toIntOrNull()
        val estado = spEstado.selectedItem.toString()

        if (fecha.isNotEmpty() && mesa != null) {
            lifecycleScope.launch {
                dao.insertar(Pedido(fecha = fecha, mesa = mesa, estado = estado))
                runOnUiThread {
                    Toast.makeText(this@PedidoActivity, "Pedido agregado", Toast.LENGTH_SHORT).show()
                    etFecha.text.clear()
                    etMesa.text.clear()
                    spEstado.setSelection(0)
                    cargarPedidos()
                }
            }
        } else {
            Toast.makeText(this, "Complete los campos correctamente", Toast.LENGTH_SHORT).show()
        }
    }

    private fun cargarPedidos() {
        lifecycleScope.launch {
            val pedidos = dao.obtenerPedidos()
            runOnUiThread {
                linearPedidos.removeAllViews()
                pedidos.forEachIndexed { index, pedido ->
                    val btnPedido = Button(this@PedidoActivity).apply {
                        text = "Pedido ${index + 1}"
                        setOnClickListener {
                            val intent = Intent(this@PedidoActivity, DetallePedidoActivity::class.java)
                            intent.putExtra("pedidoId", pedido.id)
                            startActivity(intent)
                        }
                    }
                    linearPedidos.addView(btnPedido)
                }
            }
        }
    }
}
