package com.example.bbddroomsqlite.DAO

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "jugadores")
data class Jugador(
    @PrimaryKey(autoGenerate = true)
    val id: String = "",
    val nombre: String = "",
    val max: String = ""
)