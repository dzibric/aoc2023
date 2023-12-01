package day1

import shared.readInput

const val DAY = "day1"

fun sumOfFirstAndLastDigit(string: String) =
    (string.first { it.isDigit() }.toString() + string.last{ it.isDigit() }.toString()).toInt()

fun main(args: Array<String>) {
    println(readInput(DAY).sumOf { sumOfFirstAndLastDigit(it) })
}