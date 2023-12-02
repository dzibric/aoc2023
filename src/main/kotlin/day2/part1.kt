package day2

import shared.extractFirstNumberFromString
import shared.readInput
import java.util.regex.Pattern

const val DAY = "day2"

const val MAX_BLUES = 14
const val MAX_GREENS = 13
const val MAX_REDS = 12
const val BLUE = "blue"
const val RED = "red"
const val GREEN = "green"

val COLORS = listOf(BLUE, GREEN, RED)
data class Dice(val number: Int, val color: String)
data class Set(val dices: List<Dice>)

class Game(val number: Int, val sets: List<Set>)

fun getDicesFromSet(input: String): List<Dice> {
    val pattern = Pattern.compile("\\b(\\d+)\\s+(${COLORS.joinToString("|")})\\b")
    val matcher = pattern.matcher(input)
    val colors = mutableListOf<Dice>()

    while (matcher.find()) {
        val number = matcher.group(1).toInt()
        val item = matcher.group(2)
        colors.add(Dice(number, item))
    }

    return colors
}

fun createGame(input: String): Game {
    val gameNumber = input.extractFirstNumberFromString()
    val allSetsString = input.substring(input.indexOf(':')).trim()
    val setsStringList = allSetsString.split(";")
    val sets = setsStringList.map { Set(getDicesFromSet(it)) }

    return Game(gameNumber, sets)
}

fun extractGames(input: List<String>): List<Game> {
    return input.map { createGame(it) }
}

fun isGameImpossible(game: Game): Boolean {
    return game.sets.any { set ->
        (set.dices.find { it.color == BLUE }?.number ?: 0) > MAX_BLUES ||
                (set.dices.find { it.color == RED }?.number ?: 0) > MAX_REDS ||
                (set.dices.find { it.color == GREEN }?.number ?: 0) > MAX_GREENS
    }
}

fun calculateImpossibleGames(games: List<Game>): Int {
    return games.sumOf { if (!isGameImpossible(it)) {
        it.number
    } else 0 }
}

fun main(args: Array<String>) {
    val input = readInput(DAY)
    val games = extractGames(input)
    println(calculateImpossibleGames(games))
}