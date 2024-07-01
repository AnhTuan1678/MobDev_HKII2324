fun main() {
    val Steps = 4000
    val caloriesBurned = pedometerStepsToCalories(Steps)
    println("Walking $Steps steps burns $caloriesBurned calories")
}

fun pedometerStepsToCalories(numberOfStepS: Int): Double {
    val caloriesBurnedForEachStep = 0.04
    val totalCaloriesBurned = numberOfStepS * caloriesBurnedForEachStep
    return totalCaloriesBurned
}
