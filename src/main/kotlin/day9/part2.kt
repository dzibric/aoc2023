package day9

import shared.readInput

fun getPreviousNumberInSequence(sequence: List<Long>, numbers: List<Long> = listOf()): List<Long> {
    val listOfDifferences = getDifferencesList(sequence)
    println(sequence)
    return if (!listOfDifferences.all { it == 0L }) {
        getPreviousNumberInSequence(listOfDifferences, numbers.toMutableList().apply { add(sequence.first) }.toList())
    } else {
        println(listOfDifferences)
        numbers.toMutableList().apply { add(sequence.first) }.toList()
    }
}

fun calculateFinalResult(inputList: List<Long>): Long {
    var result = 0L

    for (i in inputList.indices) {
        if (i % 2 == 0) {
            result += inputList[i]
        } else {
            result -= inputList[i]
        }
    }

    return result
}

fun getSumOfExtrapolatedValues(sequences: List<List<Long>>): Long {
    return sequences.sumOf { calculateFinalResult(getPreviousNumberInSequence(it)) }
}

fun main(args: Array<String>) {
    val sequences = getSequences(readInput(DAY))
    println(getSumOfExtrapolatedValues(sequences))
}