package com.example.restauranteapp.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "detalle_pedido")
data class DetallePedido(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val pedidoId: Int,
    val cantidad: Int,
    val observaciones: String,
    val platoNombre: String,
    val estado: String,
    val precioTotal: Double
)
