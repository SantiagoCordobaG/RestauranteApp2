package com.example.restauranteapp.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.restauranteapp.model.Examen
import com.example.restauranteapp.model.Plato
import com.example.restauranteapp.model.Pedido
import com.example.restauranteapp.model.DetallePedido
import com.example.restauranteapp.model.ExamenParametro

@Database(
    entities = [Examen::class, ExamenParametro::class, Plato::class,Pedido::class, DetallePedido::class],
    version = 5,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun examenDAO(): ExamenDAO

    abstract fun examenParametroDAO(): ExamenParametroDAO

    abstract fun platoDAO(): PlatoDAO
    abstract fun pedidoDAO(): PedidoDAO
    abstract fun detallePedidoDAO(): DetallePedidoDAO

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "restaurante_db"
                )
                    .fallbackToDestructiveMigration()
                    .build()

                INSTANCE = instance
                instance
            }
        }
    }
}