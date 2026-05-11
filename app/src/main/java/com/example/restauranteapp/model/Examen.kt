package com.example.restauranteapp.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "examen")
data class Examen(
    @PrimaryKey(autoGenerate = true)
    val exa_id: Int = 0,

    val exa_descripcion: String,
    val exa_valor: Int,
    val exa_tipo: String,
    val exa_estado: String
)