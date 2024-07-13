package com.example.dessertclicker.ui

import androidx.lifecycle.ViewModel
import com.example.dessertclicker.data.Datasource.dessertList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class DessertViewModel: ViewModel() {
    private val _dessertUiState = MutableStateFlow(DessertUiState())
    val dessertUiState = _dessertUiState.asStateFlow()

    fun onDessertClicked() {
        _dessertUiState.update { uiState ->
            val sold =  uiState.dessertsSold + 1
            uiState.copy(
                revenue = uiState.revenue + uiState.currentDessertPrice,
                dessertsSold = sold,
                currentDessertIndex = determineDessert(sold),
                currentDessertImageId = dessertList[uiState.currentDessertIndex].imageId,
                currentDessertPrice = dessertList[uiState.currentDessertIndex].price,
            )
        }
    }

    private fun determineDessert(dessertsSold: Int): Int{
        var dessert = 0
        for(i in dessertList.indices){
            if(dessertsSold >= dessertList[i].startProductionAmount){
                dessert = i
            }else{
                break
            }
        }
        return  dessert
    }
}