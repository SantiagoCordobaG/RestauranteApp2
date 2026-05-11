package com.example.restauranteapp.data

import androidx.room.*
import com.example.restauranteapp.model.ExamenParametro

@Dao
interface ExamenParametroDAO {

    @Insert
    suspend fun insertar(examenParametro: ExamenParametro)

    @Update
    suspend fun actualizar(examenParametro: ExamenParametro)

    @Query("""
        SELECT * FROM examen_parametros
        WHERE ep_exa_id = :examenId
        ORDER BY ep_consecutivo ASC
    """)
    suspend fun obtenerParametrosPorExamen(examenId: Int): List<ExamenParametro>

    @Query("SELECT * FROM examen_parametros WHERE ep_id = :id LIMIT 1")
    suspend fun obtenerParametroPorId(id: Int): ExamenParametro?
}