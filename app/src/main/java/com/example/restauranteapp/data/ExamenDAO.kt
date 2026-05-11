package com.example.restauranteapp.data

import androidx.room.*
import com.example.restauranteapp.model.Examen

@Dao
interface ExamenDAO {

    @Insert
    suspend fun insertar(examen: Examen)

    @Update
    suspend fun actualizar(examen: Examen)

    @Query("SELECT * FROM examen ORDER BY exa_id DESC")
    suspend fun obtenerExamenes(): List<Examen>

    @Query("SELECT * FROM examen WHERE exa_id = :id LIMIT 1")
    suspend fun obtenerExamenPorId(id: Int): Examen?
}