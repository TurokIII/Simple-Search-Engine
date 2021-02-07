package search

fun main() {
    val words = readLine()!!.split(" ")
    val needle = readLine()!!

    for (i in words.indices) {
        if (words[i] == needle) {
            println(i + 1)
            return
        }
    }

    println("Not Found.")
}
