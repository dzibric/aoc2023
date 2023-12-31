package shared

import java.io.File
import java.util.regex.Pattern

fun readInput(day: String): List<String> {
    val path = "src/main/kotlin/$day/input.txt"
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

fun List<Any>.findIndexOfElementAfterIndex(element: Any, startIndex: Int): Int {
    for (index in startIndex + 1..< size) {
        if (this[index] == element) {
            return index
        }
    }
    return -1
}

inline fun <T> List<T>.indexOfFirstAfter(afterIndex: Int, predicate: (T) -> Boolean): Int {
    for (index in afterIndex + 1..< size) {
        if (predicate(this[index])) {
            return index
        }
    }
    return -1
}

inline fun <T> Iterable<T>.productOf(selector: (T) -> Int): Int {
    var sum = 1
    for (element in this) {
        sum *= selector(element)
    }
    return sum
}

fun leastCommonMultiple(a: Int, b: Int): Int {
    return a * b / greatestCommonDivisor(a, b)
}

fun leastCommonMultiple(a: Long, b: Long): Long {
    return a * b / greatestCommonDivisor(a, b)
}

tailrec fun greatestCommonDivisor(a: Int, b: Int): Int {
    return if (b == 0) a
    else greatestCommonDivisor(b, a % b)
}

tailrec fun greatestCommonDivisor(a: Long, b: Long): Long {
    return if (b == 0L) a
    else greatestCommonDivisor(b, a % b)
}