package com.example.MVVMSD.repository

import android.content.Context
import com.example.MVVMSD.network.MongoDBApi
import com.example.bbddroomsqlite.DAO.Jugador
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class DataRepository(private val context: Context) {
    private val api = Retrofit.Builder()
        .baseUrl("https://localhost:3000/") // Cambia con tu URL
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(MongoDBApi::class.java)

    suspend fun syncDataToMongoDB() = withContext(Dispatchers.IO) {
        val sharedPref = context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
        val data = Jugador(
            id = sharedPref.getString("id", "") ?: "",
            nombre = sharedPref.getString("nombre", "") ?: "",
            max = sharedPref.getString("max", "") ?: ""
        )
        api.saveDataToMongoDB(data)
    }
}
