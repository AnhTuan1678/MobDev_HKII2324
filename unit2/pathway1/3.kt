fun main() {
    val child = 5
    val adult = 28
    val senior = 87

    val isMonday = true

    println("The movie ticket price for a person aged $child is \$${ticketPrice(child, isMonday)}.")
    println("The movie ticket price for a person aged $adult is \$${ticketPrice(adult, isMonday)}.")
    println(
            "The movie ticket price for a person aged $senior is \$${ticketPrice(senior, isMonday)}."
    )
}

fun ticketPrice(age: Int, isMonday: Boolean): Int {
    // Fill in the code.
    when {
        age < 13 -> return 15
        age in 13..60 -> return if (isMonday) 25 else 30
        else -> return 20
    }
}
