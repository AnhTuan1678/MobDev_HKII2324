fun main() {
    printWeatherForecast("Ankara", 27, 31, 82)
    printWeatherForecast("Tokyo", 32, 36, 10)
    printWeatherForecast("Cape Town", 59, 64, 2)
    printWeatherForecast("Guatemala City", 50, 55, 7)
}

fun printWeatherForecast(city: String, lowTemp: Int, highTemp: Int, chanceOfRain: Int) {
    println("City: $city")
    println("Low temperature: $lowTemp, High temperature: $highTemp")
    println("Chance of rain: $chanceOfRain%")
    println()
}
