package com.example.restauranteapp.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "examen_parametros")
data class ExamenParametro(
    @PrimaryKey(autoGenerate = true)
    val ep_id: Int = 0,

    val ep_exa_id: Int,
    val ep_para_id: Int,
    val ep_consecutivo: Int
)