package com.example.MVVMSD.network

import com.example.bbddroomsqlite.DAO.Jugador
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.Response

interface MongoDBApi {
    @POST("api/save-data")
    suspend fun saveDataToMongoDB(@Body data: Jugador): Response<String>
}
