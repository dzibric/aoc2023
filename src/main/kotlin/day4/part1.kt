package day4

import shared.readInput
import kotlin.math.pow

const val DAY = "day4"

data class Card(val number: Int, val winningNumbers: List<Int>, val receivedNumbers: List<Int>) {
    fun getWinners(): Int = winningNumbers.count { receivedNumbers.contains(it) }
    fun getWinningWorth(): Int {
        val winners = getWinners()
        if (winners == 0) return 0
        return 2.toDouble().pow(winners - 1).toInt()
    }
}

fun extractDataToCard(input: String): Card {
    val regex = """Card\s+(\d+):\s+([\d\s]+)\s+\|\s+([\d\s]+)""".toRegex()
    val matchResult = regex.find(input)!!

    val cardNumber = matchResult.groupValues[1].toInt()
    val winningNumbers = matchResult.groupValues[2].split(" ").filter { it.isNotBlank() }.map { it.toInt() }
    val receivedNumbers = matchResult.groupValues[3].split(" ").filter { it.isNotBlank() }.map { it.toInt() }
    return Card(cardNumber, winningNumbers, receivedNumbers)
}

fun extractInputToCards(input: List<String>) = input.map { extractDataToCard(it) }

fun main(args: Array<String>) {
    println(extractInputToCards(readInput(DAY)).sumOf { it.getWinningWorth() })
}