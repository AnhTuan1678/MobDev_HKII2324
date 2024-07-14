package com.lixoten.flightsearch.data

import androidx.room.*
import com.lixoten.flightsearch.model.Airport
import com.lixoten.flightsearch.model.Favorite
import kotlinx.coroutines.flow.Flow

@Dao
interface FlightDao {
    @Query(
        """
        Select * from airport 
        ORDER BY id ASC 
        """
    )
    fun getAllAirportsFlow(): Flow<List<Airport>>
    @Query(
        """
        Select * from airport 
        ORDER BY id ASC 
        """
    )
    suspend fun getAllAirports(): List<Airport>
    @Query(
        """
    Select * from airport
    WHERE iata_code = :query OR name LIKE '%' || :query || '%'        
    ORDER BY name ASC
        """
    )
    fun getAllAirportsFlow(query: String): Flow<List<Airport>>
    @Query(
        """
    Select * from airport
    WHERE iata_code = :query OR name LIKE '%' || :query || '%'        
    ORDER BY name ASC
        """
    )
    suspend fun getAllAirports(query: String): List<Airport>
    @Query(
        """
    Select * from airport
    WHERE iata_code = :code
        """
    )
    suspend fun getAirportByCode(code: String): Airport
    @Query(
        """
    Select * from airport
    WHERE iata_code = :code
        """
    )
    fun getAirportByCodeFlow(code: String): Flow<Airport>
    @Query(
        """
    Select * from airport
    WHERE id = :id
        """
    )
    suspend fun getAirportById(id: Int): Airport
}