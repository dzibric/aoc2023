package day2

import shared.productOf
import shared.readInput

fun getMinDicesFromGame(game: Game): Set {
    val minReds = game.sets.maxOf { set -> set.dices.find { it.color == RED }?.number ?: 0 }
    val minBlues = game.sets.maxOf { set -> set.dices.find { it.color == BLUE }?.number ?: 0 }
    val minGreens = game.sets.maxOf { set -> set.dices.find { it.color == GREEN }?.number ?: 0 }
    return Set(listOf(Dice(minBlues, BLUE), Dice(minGreens, GREEN), Dice(minReds, RED)))
}

fun calculatePowerOfSet(set: Set): Int {
    return set.dices.productOf { it.number }
}

fun calculateSumOfAllPowers(gameMinDices: List<Set>): Int {
    return gameMinDices.sumOf { calculatePowerOfSet(it) }
}

fun main(args: Array<String>) {
    val input = readInput(DAY)
    val games = extractGames(input)
    println(calculateSumOfAllPowers(games.map { getMinDicesFromGame(it) }))
}