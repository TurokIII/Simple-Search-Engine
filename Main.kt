package search

fun main() {
    getUserInput()
}

fun getUserInput() {
    println("Enter the number of songs:")
    val numberOfSongs = readLine()!!.toInt()
    val songs = mutableListOf<String>()

    println("Enter all songs:")

    repeat(numberOfSongs) {
        songs.add(readLine()!!)
    }

    println()

    println("Enter the number of search queries:")
    val numberOfQueries = readLine()!!.toInt()

    println()

    repeat(numberOfQueries) {
        println("Enter data to search for people:")
        val data = readLine()!!
        println()
        findSong(data, songs)
    }


}

fun findSong(songData: String, songs: MutableList<String>) {
    val matches = mutableListOf<String>()

    for (song in songs) {
        if (song.toLowerCase().contains(songData.toLowerCase())) matches.add(song)
    }

    printSearchResults(matches)

}

fun printSearchResults(matches: MutableList<String>) {
    println("Found songs:")

    if (matches.size > 0) {
        for (song in matches) {
            println(song)
        }
    } else {
        println("No matching songs found.")
    }

    println()
}
