package com.lixoten.flightsearch.di

import android.content.Context
import com.lixoten.flightsearch.data.FlightDatabase
import com.lixoten.flightsearch.data.FlightRepository
import com.lixoten.flightsearch.data.OfflineFlightRepository

interface AppContainer {
    val flightRepository: FlightRepository
}

class AppDataContainer(private val context: Context) : AppContainer {
    override val flightRepository: FlightRepository by lazy {
        OfflineFlightRepository(FlightDatabase.getDatabase(context).flightDao())
    }
}