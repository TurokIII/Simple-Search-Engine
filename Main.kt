package search

import java.io.File

fun main(args: Array<String>) {
    loadSongs(args[1])
}

fun loadSongs(fileName: String) {
    val songs = readSongs(fileName)
    val wordIndexes = indexSongs(songs)
    getUserInput(songs, wordIndexes)
}

fun readSongs(fileName: String): List<String> {
    val file = File(fileName)
    return file.readLines()
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

fun getQueryType(): String {
    println()
    println("Select a matching strategy: ALL, ANY, NONE")
    return readLine()!!
}

fun searchForSong(songs: List<String>, wordIndexes: MutableMap<String, MutableSet<Int>>) {
    val queryType = getQueryType()
    println()
    println("Enter a song name or artist to search all suitable songs.")
    val songData = readLine()!!
    findSong(songData, songs, wordIndexes, queryType)
}

fun findSong(songData: String, songs: List<String>, wordIndexes: MutableMap<String, MutableSet<Int>>, queryType: String) {
    val words = songData.toUpperCase().split(" ")

    val matches = when (queryType) {
        "ALL" -> findAllWords(words, songs, wordIndexes)
        "ANY" -> findAnyWords(words, songs, wordIndexes)
        "NONE" -> findNoneWords(words, songs, wordIndexes)
        else -> mutableListOf()
    }

    if (matches.isEmpty()) matches.add("No matching songs found")

    printSearchResults(matches)

}

fun findAllWords(
    words: List<String>,
    songs: List<String>,
    wordIndexes: MutableMap<String,MutableSet<Int>>
): MutableList<String> {
    val matches = mutableListOf<String>()
    val lineMatchCount = mutableMapOf<Int, Int>()

    for (word in words) {
        if (wordIndexes.containsKey(word)) {
            for (n in wordIndexes[word]!!) {
                lineMatchCount[n] = lineMatchCount.getOrDefault(n, 0) + 1
            }
        }
    }

    for ((lineNum, matchCount) in lineMatchCount) {
        if (matchCount == words.size) matches.add(songs[lineNum])
    }

    return matches
}

fun findAnyWords(
    words: List<String>,
    songs: List<String>,
    wordIndexes: MutableMap<String, MutableSet<Int>>
): MutableList<String> {
    val matches = mutableListOf<String>()

    for (word in words) {
        if (wordIndexes.containsKey(word)) {
            for (n in wordIndexes[word]!!) {
                matches.add(songs[n])
            }
        }
    }

    return matches
}

fun findNoneWords(
    words: List<String>,
    songs: List<String>,
    wordIndexes: MutableMap<String, MutableSet<Int>>
): MutableList<String> {
    val matches = mutableListOf<String>()
    val linesWithWord = mutableListOf<Int>()

    for (word in words) {
        if (wordIndexes.containsKey(word)) {
            for (n in wordIndexes[word]!!) {
                linesWithWord += wordIndexes[word]!!.map { it.toInt() }
            }
        }
    }

    for (i in songs.indices) {
        if (i !in linesWithWord) matches.add(songs[i])
    }

    return matches
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
