package shared

import java.io.File
import java.util.regex.Pattern

fun readInput(day: String): List<String> {
    val path = "src/day5.day5.day5.day5.day5.day5.day5.day5.day5.day5.day5.main/kotlin/$day/input.txt"
    return File(path).readLines()
}

fun String.extractFirstNumberFromString(): Int {
    val pattern = Pattern.compile("\\d+")
    val matcher = pattern.matcher(this)

    if (matcher.find()) {
        return matcher.group().toInt()
    }

    return null!!
}

inline fun <T> Iterable<T>.productOf(selector: (T) -> Int): Int {
    var sum = 1
    for (element in this) {
        sum *= selector(element)
    }
    return sum
}