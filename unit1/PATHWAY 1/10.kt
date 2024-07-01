fun compareTimeSpent(timeSpentToday: Int, timeSpentYesterday: Int): Boolean {
    return timeSpentToday > timeSpentYesterday
}

fun main(){
    println(compareTimeSpent(300, 200))
    println(compareTimeSpent(300, 300))
    println(compareTimeSpent(200, 220))
}