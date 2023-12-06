package day6

import shared.productOf

val LENGTHS = listOf(7, 15, 30)
val RECORDS = listOf(9, 40, 200)

fun calculateAllPossibleDistances(raceLength: Int, currentRecord: Int): Int {
    return IntRange(0, raceLength).count { number ->
        val distance = number * (raceLength - number)
        distance > currentRecord
    }
}

fun getFinalProductOfAllPossibleWins(lengths: List<Int>, records: List<Int>): Int {
    val possibleWins = lengths.mapIndexed { index, length -> calculateAllPossibleDistances(length, records[index]) }
    return possibleWins.productOf { it }
}

fun main(args: Array<String>) {
    println(getFinalProductOfAllPossibleWins(LENGTHS, RECORDS))
}