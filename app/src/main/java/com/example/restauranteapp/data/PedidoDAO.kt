package com.example.restauranteapp.data

import androidx.room.*
import com.example.restauranteapp.model.Pedido

@Dao
interface PedidoDAO {
    @Insert
    suspend fun insertar(pedido: Pedido)

    @Query("SELECT * FROM pedido")
    suspend fun obtenerPedidos(): List<Pedido>

    @Query("SELECT * FROM pedido WHERE id = :id LIMIT 1")
    suspend fun obtenerPedidoPorId(id: Int): Pedido?

    @Update
    suspend fun actualizar(pedido: Pedido)
}
