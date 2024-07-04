enum class Daypart {
    MORNING,
    AFTERNOON,
    EVENING
}

data class Event(
        val title: String,
        val description: String? = null,
        val daypart: Daypart,
        var durationInMinutes: Int // minutes
)

fun main() {
    val events: MutableList<Event> =
            mutableListOf(
                    Event(
                            title = "Wake up",
                            description = "Time to get up",
                            daypart = Daypart.MORNING,
                            durationInMinutes = 0
                    ),
                    Event(
                            title = "Eat breakfast",
                            daypart = Daypart.MORNING,
                            durationInMinutes = 15
                    ),
                    Event(
                            title = "Learn about Kotlin",
                            daypart = Daypart.AFTERNOON,
                            durationInMinutes = 30
                    ),
                    Event(
                            title = "Practice Compose",
                            daypart = Daypart.AFTERNOON,
                            durationInMinutes = 60
                    ),
                    Event(
                            title = "Watch latest DevBytes video",
                            daypart = Daypart.AFTERNOON,
                            durationInMinutes = 10
                    ),
                    Event(
                            title = "Check out latest Android Jetpack library",
                            daypart = Daypart.EVENING,
                            durationInMinutes = 45
                    )
            )

    printNumberOfShortEvents(events)
    printEventsByDaypart(events)
    printLastEvent(events)
}

fun printNumberOfShortEvents(events: List<Event>) {
    var numbet = events.filter { it.durationInMinutes <= 60 }.size
    println("Number of short events: $numbet")
}

fun printEventsByDaypart(events: List<Event>) {
    val eventsByDaypart = events.groupBy { it.daypart }
    eventsByDaypart.forEach { (daypart, events) -> println("$daypart: ${events.size}") }
}

fun printLastEvent(events: List<Event>) {
    println("Last event of the day: ${events.last().title}")
}

val Event.durationOfEvent: String
    get() = if (this.durationInMinutes < 60) "short" else "long"
