package com.example.bbddroomsqlite.DAO

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface DAO {

    // @Query: Consulta SQL personalizada
    // Obtiene todos los jugadores ordenados por puntuación máxima descendente
    @Query("SELECT * FROM jugadores ORDER BY max DESC")
    fun getAllJugadores(): Flow<List<Jugador>>

    // @Query: Consulta para obtener un jugador por su ID
    @Query("SELECT * FROM jugadores WHERE id = :id")
    suspend fun getJugadorById(id: Long): Jugador?

    // @Query: Consulta para obtener el jugador con la puntuación máxima
    @Query("SELECT * FROM jugadores ORDER BY max DESC LIMIT 1")
    suspend fun getJugadorRecord(): Jugador?

    // @Query: Consulta para buscar jugadores por nombre (LIKE para búsquedas parciales)
    @Query("SELECT * FROM jugadores WHERE nombre LIKE :nombreBuscado ORDER BY max DESC")
    suspend fun getJugadoresPorNombre(nombreBuscado: String): List<Jugador>

    // @Insert: Inserta uno o más jugadores
    // onConflict = OnConflictStrategy.IGNORE: Ignora si hay conflictos (por ejemplo, ID duplicado)
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertarJugador(jugador: Jugador)

    // @Update: Actualiza un jugador existente
    @Update
    suspend fun actualizarJugador(jugador: Jugador)

    // @Delete: Elimina un jugador
    @Delete
    suspend fun borrarJugador(jugador: Jugador)

    // @Query: Consulta para borrar jugadores por nombre (útil para limpiar o pruebas)
    @Query("DELETE FROM jugadores WHERE nombre = :nombre")
    suspend fun borrarJugadoresPorNombre(nombre: String)

    // @Query: Consulta para borrar todos los jugadores (útil para pruebas)
    @Query("DELETE FROM jugadores")
    suspend fun borrarTodosLosJugadores()
}