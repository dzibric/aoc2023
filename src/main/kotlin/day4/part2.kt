package day4

import shared.readInput
fun calculateCopy(card: Card, cards: List<Card>): Int {
    val copiedCards = card.getWinners()
    val elementsToAdd = (card.number + 1..< card.number + 1 + copiedCards).toList()
    val cardsToAdd = elementsToAdd.mapNotNull { element -> cards.find { element == it.number } }
    var sum = 1
    cardsToAdd.forEach {
        sum += calculateCopy(it, cards)
    }
    return sum
}

fun main(args: Array<String>) {
    val cards = extractInputToCards(readInput(DAY))
    println(extractInputToCards(readInput(DAY)).sumOf { calculateCopy(it, cards) })
}