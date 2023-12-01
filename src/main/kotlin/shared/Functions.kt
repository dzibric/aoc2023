package shared

import java.io.File

fun readInput(day: String): List<String> {
    val path = "src/main/kotlin/$day/input.txt"
    return File(path).readLines()
}