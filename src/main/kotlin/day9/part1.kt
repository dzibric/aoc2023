package day9

import shared.readInput

const val DAY = "day9"

fun getDifferencesList(sequence: List<Long>): List<Long> {
    val diffList = mutableListOf<Long>()

    for (i in 1..<sequence.size) {
        val diff = sequence[i] - sequence[i - 1]
        diffList.add(diff)
    }

    return diffList
}

fun getNextNumberInSequence(sequence: List<Long>, difference: Long = 0): Long {
    val listOfDifferences = getDifferencesList(sequence)
    return if (!listOfDifferences.all { it == 0L }) {
        getNextNumberInSequence(listOfDifferences, difference + listOfDifferences.last)
    }
    else {
        difference
    }
}

fun getSequences(input: List<String>) : List<List<Long>> {
    return input.map { row -> row.split(" ").map { it.toLong() } }
}

fun getSumOfLastNumbersInSequences(sequences: List<List<Long>>): Long {
    return sequences.sumOf { getNextNumberInSequence(it) + it.last }
}

fun main(args: Array<String>) {
    val sequences = getSequences(readInput(DAY))
    println(getSumOfLastNumbersInSequences(sequences))
}