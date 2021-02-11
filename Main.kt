package search

import java.io.File

fun main(args: Array<String>) {
    val fileName = args[1]
    println(fileName)
    val file = File(fileName)
    val songs = file.readLines()

    getUserInput(songs)
}

fun addSongs(): MutableList<String> {
    println("Enter the number of songs:")
    val numberOfSongs = readLine()!!.toInt()
    val songs = mutableListOf<String>()

    println("Enter all songs:")

    repeat(numberOfSongs) {
        songs.add(readLine()!!)
    }

    return songs
}

fun getUserInput(songs: List<String>) {
    while(true) {
        printMenu()
        val input = readLine()!!

        when (input) {
            "1" -> searchForSong(songs)
            "2" -> printAllSongs(songs)
            "0" -> break
            else -> println("\nIncorrect option! Try again")
        }

    }
}

fun searchForSong(songs: List<String>) {
    println()
    println("Enter a song name or artist to search all suitable songs.")
    val songData = readLine()!!
    findSong(songData, songs)
}

fun findSong(songData: String, songs: List<String>) {

    val matches = mutableListOf<String>()

    for (song in songs) {
        if (song.toLowerCase().contains(songData.toLowerCase())) matches.add(song)
    }

    printSearchResults(matches)

}

fun printSearchResults(matches: List<String>) {
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

fun printAllSongs(songs: List<String>) {
    println("=== List of songs ===")
    for (song in songs) {
        println(song)
    }
}

fun printMenu() {
    println()
    println("=== Menu ===")
    println("1. Find a song")
    println("2. Print all songs")
    println("0. Exit")
}
