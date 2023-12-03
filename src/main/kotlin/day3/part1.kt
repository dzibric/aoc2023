package day3

import shared.readInput
import java.awt.Point

const val DAY = "day3"

data class Scheme(val input: List<String>)

data class NumberCoordinates(val x: Int, val y: IntRange)

data class Number(val schemeCoordinates: NumberCoordinates, val number: Int)

fun isSymbol(char: Char): Boolean {
    return char.isLetterOrDigit() || char == '.'
}
fun extractNumbersFromSchemeRow(row: Int, string: String): List<Number> {
    val regex = Regex("\\d+")
    val matches = regex.findAll(string)

    val result = mutableListOf<Number>()

    for (match in matches) {
        val startIndex = match.range.first
        val endIndex = match.range.last
        result.add(Number(NumberCoordinates(row, IntRange(startIndex, endIndex)), match.value.toInt()))
    }

    return result
}

fun isAdjacentToSymbol(number: Number, scheme: Scheme): Boolean {
    return number.schemeCoordinates.y.any {
        isPointAdjacentToSymbol(Point(number.schemeCoordinates.x, it), scheme)
    }
}

fun isPointAdjacentToSymbol(point: Point, scheme: Scheme): Boolean {
    val directions = listOf(
        Pair(1, 0), Pair(-1, 0), Pair(0, 1), Pair(0, -1),
        Pair(-1, -1), Pair(-1, 1), Pair(1, 1), Pair(1, -1)
    )

    for ((dx, dy) in directions) {
        val newX = point.x + dx
        val newY = point.y + dy

        if (newX in 0..<scheme.input.size && newY in 0..<scheme.input[0].length) {
            if (!isSymbol(scheme.input[newX][newY])) {
                return true
            }
        }
    }

    return false
}

fun extractNumbersFromScheme(scheme: Scheme): List<Number> {
    return scheme.input.flatMapIndexed { index: Int, s: String -> extractNumbersFromSchemeRow(index, s) }
}

fun getSumOfAllNumbersWithNoAdjacentSymbol(numbers: List<Number>, scheme: Scheme): Int {
    return numbers.sumOf { if (isAdjacentToSymbol(it, scheme)) it.number else 0 }
}

fun main(args: Array<String>) {
    val scheme = Scheme(readInput(DAY))
    println(getSumOfAllNumbersWithNoAdjacentSymbol(extractNumbersFromScheme(scheme), scheme))
}