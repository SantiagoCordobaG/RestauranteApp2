package com.example.restauranteapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnExamen = findViewById<Button>(R.id.btnExamenes)

        btnExamen.setOnClickListener {
            val intent = Intent(this, ExamenActivity::class.java)
            startActivity(intent)
        }

        val btnPedido = findViewById<Button>(R.id.btnPedido)

        btnPedido.setOnClickListener {
            val intent = Intent(this, PedidoActivity::class.java)
            startActivity(intent)
        }
    }
}