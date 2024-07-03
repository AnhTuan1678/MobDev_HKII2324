class Song(val title: String, val artist: String, val publishedYear: Int) {
    var countPlay: Int = 0

    var isFamous = false
        get() = countPlay >= 1000
        private set

    fun printInfo() {
        println("$title by $artist, was released in $publishedYear.")
    }

    fun play() {
        countPlay++
    }
}

fun main() {
    val song = Song("Love Story", "Taylor Swift", 2008)
    song.printInfo()
    println("The song is famous: ${song.isFamous}")
    repeat(1001) { song.play() }
    println("The song is famous: ${song.isFamous}")
}
