package com.example.restauranteapp.data

import androidx.room.*
import com.example.restauranteapp.model.DetallePedido

@Dao
interface DetallePedidoDAO {
    @Insert
    suspend fun insertar(detalle: DetallePedido)

    @Query("SELECT * FROM detalle_pedido WHERE pedidoId = :pedidoId")
    suspend fun obtenerDetallesPorPedido(pedidoId: Int): List<DetallePedido>

    @Update
    suspend fun actualizar(detalle: DetallePedido)
}
