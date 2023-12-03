package day3

import shared.productOf
import shared.readInput
import java.awt.Point

private const val GEAR_NUMBER_CONDITION = 2

data class NumberGear(val number: Number, val gearPoint: Point)

fun isAdjacentToGear(number: Number, scheme: Scheme): Pair<Boolean, Point> {
    val isAdjacentGear = number.schemeCoordinates.y.find {
        isPointAdjacentToGear(Point(number.schemeCoordinates.x, it), scheme).first
    }
    if (isAdjacentGear != null) {
        return Pair(true, isPointAdjacentToGear(Point(number.schemeCoordinates.x, isAdjacentGear), scheme).second)
    }
    return Pair(false, Point(0, 0))
}

fun isPointAdjacentToGear(point: Point, scheme: Scheme): Pair<Boolean, Point> {
    val directions = listOf(
        Pair(1, 0), Pair(-1, 0), Pair(0, 1), Pair(0, -1),
        Pair(-1, -1), Pair(-1, 1), Pair(1, 1), Pair(1, -1)
    )

    for ((dx, dy) in directions) {
        val newX = point.x + dx
        val newY = point.y + dy

        if (newX in 0..<scheme.input.size && newY in 0..<scheme.input[0].length) {
            if (scheme.input[newX][newY] == '*') {
                return Pair(true, Point(newX, newY))
            }
        }
    }

    return Pair(false, Point(0, 0))
}

fun getAllNumberGears(numbers: List<Number>, scheme: Scheme): List<NumberGear> {
    return numbers.mapNotNull { number ->
        val isAdjacent = isAdjacentToGear(number, scheme)
        if (isAdjacent.first) {
            NumberGear(number, isAdjacent.second)
        } else {
            null
        }
    }
}

fun getSumOfAllCorrectGears(numberGears: List<NumberGear>): Int {
    val correctGears = numberGears.mapNotNull { numberGear ->
        if (numberGears.count { gear -> gear.gearPoint == numberGear.gearPoint } == GEAR_NUMBER_CONDITION) {
            numberGear
        } else {
            null
        }
    }
    val gearGroups = correctGears.groupBy { it.gearPoint }
    val products = gearGroups.map { group -> group.value.productOf { it.number.number } }
    return products.sumOf { it }
}

fun main(args: Array<String>) {
    val scheme = Scheme(readInput(DAY))
    val numberGears = getAllNumberGears(extractNumbersFromScheme(scheme), scheme)
    println(getSumOfAllCorrectGears(numberGears))
}