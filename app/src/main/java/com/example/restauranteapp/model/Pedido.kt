package com.example.restauranteapp.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "pedido")
data class Pedido(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val fecha: String,
    val mesa: Int,
    val estado: String
)
