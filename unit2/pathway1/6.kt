fun main() {
    val amanda = Person("Amanda", 33, "play tennis", null)
    val atiqah = Person("Atiqah", 28, "climb", amanda)

    amanda.showProfile()
    atiqah.showProfile()
}

class Person(val name: String, val age: Int, val hobby: String?, val referrer: Person?) {
    fun showProfile() {
        var message =
                if (referrer == null) "Doesn't have a referrer."
                else "Has a referrer named ${referrer.name}, who likes to ${referrer.hobby}."

        println("Name: $name\nAge: $age")
        println("Likes to $hobby. $message\n")
    }
}
