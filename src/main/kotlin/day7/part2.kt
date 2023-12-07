package day7

import shared.readInput

fun calculateFinalWinningsPart2(input: List<String>): Int {
    val camels = input.map { getCamelFromInputRow(it) }.map { Camel(it.hand, it.hand.getBestPossibleHand(it.hand.cards), it.bid) }
    val sortedCamels = camels.sortedWith { camel1, camel2 ->
        if (camel1.bestHand.isStronger(camel1.hand, camel2.hand, camel2.bestHand)) 1
        else if (camel2.bestHand.isStronger(camel2.hand, camel1.hand, camel1.bestHand)) -1
        else 0
    }
    var sum = 0
    sortedCamels.forEachIndexed { index, camel ->
        camel.print()
        sum += camel.bid * (index + 1)
    }
    return sum
}
fun main(args: Array<String>) {
    println(calculateFinalWinningsPart2(readInput(DAY)))
}