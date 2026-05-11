package com.example.restauranteapp.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "platos")
data class Plato(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val nombre: String,
    val precio: Double,
    val estado: String,
    val categoria: String
)