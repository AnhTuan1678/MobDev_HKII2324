package com.example.amphibians.data


//import com.example.amphibians.network.AmphibiansApi.retrofitService


interface AmphibianRepository {
    suspend fun getAmphibians(): List<Amphibian>
}
