package search

import java.io.File

fun main(args: Array<String>) {
    val fileName = args[1]
    println(fileName)
    val file = File(fileName)
    val songs = file.readLines()
    val wordIndexes = indexSongs(songs)

    getUserInput(songs, wordIndexes)
}

fun indexSongs(songs: List<String>): MutableMap<String, MutableSet<Int>> {
    val wordIndexes = mutableMapOf<String, MutableSet<Int>>()

    for (i in songs.indices) {
        val line = songs[i].replace(",", " ")
        val words = line.split(" ")
        for (word in words) {
            val wordUpper = word.toUpperCase()
            if (wordIndexes.containsKey(wordUpper)) {
                wordIndexes[wordUpper]?.add(i)
            } else {
                wordIndexes[wordUpper] = mutableSetOf(i)
            }
        }
    }

    return wordIndexes
}

fun getUserInput(songs: List<String>, wordIndexes: MutableMap<String, MutableSet<Int>>) {
    while(true) {
        printMenu()
        val input = readLine()!!

        when (input) {
            "1" -> searchForSong(songs, wordIndexes)
            "2" -> printAllSongs(songs)
            "0" -> break
            else -> println("\nIncorrect option! Try again")
        }

    }
}

fun searchForSong(songs: List<String>, wordIndexes: MutableMap<String, MutableSet<Int>>) {
    println()
    println("Enter a song name or artist to search all suitable songs.")
    val songData = readLine()!!
    findSong(songData, songs, wordIndexes)
}

fun findSong(songData: String, songs: List<String>, wordIndexes: MutableMap<String, MutableSet<Int>>) {
    val songDataUpper = songData.toUpperCase()
    val matches = mutableListOf<String>()

    if (wordIndexes.containsKey(songDataUpper)) {
        for (n in wordIndexes[songDataUpper]!!) {
            matches.add(songs[n])
        }
    } else {
        matches.add("No matching songs found")
    }

    printSearchResults(matches)

}

fun printSearchResults(matches: List<String>) {
    println("Found songs:")

    if (matches.isNotEmpty()) {
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
