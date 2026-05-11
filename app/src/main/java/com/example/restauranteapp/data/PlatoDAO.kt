package com.example.restauranteapp.data

import androidx.room.*
import com.example.restauranteapp.model.Plato

@Dao
interface PlatoDAO {

    @Insert
    suspend fun insertar(plato: Plato)

    @Update
    suspend fun actualizar(plato: Plato)

    @Query("SELECT * FROM platos ORDER BY id DESC")
    suspend fun obtenerPlatos(): List<Plato>

    @Query("SELECT * FROM platos WHERE id = :id LIMIT 1")
    suspend fun obtenerPlatoPorId(id: Int): Plato?
}