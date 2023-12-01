package day1

import shared.readInput

enum class Number(val value: Int, val alias: String) {
    ONE(1, "one"),
    TWO(2, "two"),
    THREE(3, "three"),
    FOUR(4, "four"),
    FIVE(5, "five"),
    SIX(6, "six"),
    SEVEN(7, "seven"),
    EIGHT(8, "eight"),
    NINE(9, "nine");

    companion object {
        fun getNumberFromValue(value: Int): Number {
            return entries.find { it.value == value }!!
        }
    }
}

fun sumOfFirstAndLastDigitDay2(string: String): Int {
    val numberAliases = Number.entries.toTypedArray()
    val allNumbersAndTheirIndex = mutableListOf<Pair<Int, Number>>()
    numberAliases.forEach { number ->
        val firstIndex = string.indexOf(number.alias)
        val lastIndex = string.lastIndexOf(number.alias)

        if (firstIndex != -1) {
            allNumbersAndTheirIndex.add(Pair(firstIndex, number))
        }
        if (lastIndex != -1) {
            allNumbersAndTheirIndex.add(Pair(lastIndex, number))
        }
    }
    val firstDigit = string.firstOrNull { it.isDigit() }
    val lastDigit = string.lastOrNull { it.isDigit() }

    firstDigit?.let { digit ->
        allNumbersAndTheirIndex.add(Pair(string.indexOfFirst { it.isDigit() }, Number.getNumberFromValue(digit.digitToInt()))) }
    lastDigit?.let { digit ->
        allNumbersAndTheirIndex.add(Pair(string.indexOfLast { it.isDigit() }, Number.getNumberFromValue(digit.digitToInt())))
    }
    val firstNumber = allNumbersAndTheirIndex.minBy { it.first }
    val lastNumber = allNumbersAndTheirIndex.maxBy { it.first }
    return (firstNumber.second.value.toString() + lastNumber.second.value.toString()).toInt()
}


fun main(args: Array<String>) {
    println(readInput(DAY).sumOf { sumOfFirstAndLastDigitDay2(it) })
}