package com.example.bbddroomsqlite.DAO

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(entities = [Jugador::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun jugadorDao(): DAO

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        // Función para obtener la instancia de la base de datos
        // Ahora solo recibe el Contexto
        fun getDatabase(context: Context): AppDatabase {
            // Si ya existe una instancia, devuélvela
            return INSTANCE ?: synchronized(this) {
                // Si no, crea una nueva instancia
                val instance = Room.databaseBuilder(
                    context.applicationContext, // Contexto de la aplicación
                    AppDatabase::class.java,    // Clase de la base de datos
                    "jugadores_database"        // Nombre del archivo de la base de datos
                )
                    .addCallback(RoomDatabaseCallback)
                    .build()
                INSTANCE = instance
                instance
            }
        }

        // Callback como un objeto singleton para evitar recrearlo innecesariamente
        private object RoomDatabaseCallback : RoomDatabase.Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                println("Base de datos creada por primera vez.")
                db.execSQL("INSERT INTO jugadores_table (nombre, max) VALUES ('Jugador1', 5)")
                db.execSQL("INSERT INTO jugadores_table (nombre, max) VALUES ('Jugador2', 10)")
            }
        }
    }
}
