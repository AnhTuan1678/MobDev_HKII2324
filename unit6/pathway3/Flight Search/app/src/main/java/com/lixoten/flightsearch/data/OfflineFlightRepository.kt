package com.lixoten.flightsearch.data

import com.lixoten.flightsearch.model.Airport
import kotlinx.coroutines.flow.Flow

class OfflineFlightRepository(private val airportDao: FlightDao) : FlightRepository {
    override fun getAllAirportsFlow(): Flow<List<Airport>> {
        return airportDao.getAllAirportsFlow()
    }

    override fun getAllAirportsFlow(query: String): Flow<List<Airport>> {
        return airportDao.getAllAirportsFlow(query)
    }

    override fun getAirportByCodeFlow(code: String): Flow<Airport> {
        return airportDao.getAirportByCodeFlow(code)
    }

    override suspend fun getAllAirports(): List<Airport> {
        return airportDao.getAllAirports()
    }

    override suspend fun getAllAirports(query: String): List<Airport> {
        return airportDao.getAllAirports(query)
    }

    override suspend fun getAirportByCode(code: String): Airport {
        return airportDao.getAirportByCode(code)
    }

    override suspend fun getAirportById(id: Int): Airport {
        return airportDao.getAirportById(id)
    }
}