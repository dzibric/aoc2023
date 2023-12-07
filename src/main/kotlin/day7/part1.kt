package day7

import shared.indexOfFirstAfter
import shared.readInput
import java.lang.Exception

const val DAY = "day7"

enum class Card(val label: Char) {
    JACK('J'),
    TWO('2'),
    THREE('3'),
    FOUR('4'),
    FIVE('5'),
    SIX('6'),
    SEVEN('7'),
    EIGHT('8'),
    NINE('9'),
    TEN('T'),
    QUEEN('Q'),
    KING('K'),
    ACE('A');

    companion object {
        fun getCardByLabel(label: Char) = entries.find { it.label == label }!!
    }
}

data class Hand(val cards: List<Card>) {
    fun getHandStrength(): Int {
        return when {
            hasFiveOfKind() -> 7
            hasPoker() -> 6
            hasFullHouse() -> 5
            hasThreeOfAKind() -> 4
            hasTwoPairs() -> 3
            cards.distinct().size == cards.size - 1 -> 2
            else -> 1
        }
    }

    fun print() {
        cards.forEach { print(" ${it.label}") }
    }

    fun isStronger(initialHand: Hand, initialSecondHand: Hand, secondHand: Hand): Boolean {
        val thisHandStrength = getHandStrength()
        val otherHangStrength = secondHand.getHandStrength()
        return if (thisHandStrength > otherHangStrength) {
            true
        } else if (thisHandStrength == otherHangStrength) {
            initialHand.isHighCardStronger(initialSecondHand)
        } else {
            false
        }
    }

    fun getBestPossibleHand(cards: List<Card>, jackIndex: Int = 0): Hand {
        if (!cards.any { it == Card.JACK }) return Hand(cards)

        val newJackIndex = cards.indexOfFirstAfter(jackIndex - 1) { it == Card.JACK }
        if (newJackIndex == -1) return Hand(cards)

        val bestCard = Card.entries.toTypedArray().maxBy {
            val replacedCards = cards.toMutableList()
            replacedCards[newJackIndex] = it
            getBestPossibleHand(replacedCards, newJackIndex + 1).getHandStrength()
        }

        val newHand = cards.toMutableList()
        newHand[newJackIndex] = bestCard
        return getBestPossibleHand(newHand, newJackIndex + 1)
    }

    private fun isHighCardStronger(hand: Hand, cardIndex: Int = 0): Boolean {
        if (cardIndex > 4) return false
        return try {
            if (cards[cardIndex] > hand.cards[cardIndex]) true
            else if (cards[cardIndex]< hand.cards[cardIndex]) false
            else isHighCardStronger(hand, cardIndex + 1)
        } catch (e: Exception) {
            false
        }
    }

    private fun hasTwoPairs(): Boolean {
        val elementGroups = cards.groupBy { it }
        return elementGroups.values.count { it.size == 2 } == 2
    }

    private fun hasThreeOfAKind(): Boolean {
        val elementGroups = cards.groupBy { it }
        return elementGroups.values.any { it.size == 3 }
    }

    private fun hasFullHouse(): Boolean {
        val elementGroups = cards.groupBy { it }
        val tripleCount = elementGroups.values.any { it.size == 3 }
        val pairCount = elementGroups.values.any { it.size == 2 }
        return tripleCount && pairCount
    }

    private fun hasPoker(): Boolean {
        val elementGroups = cards.groupBy { it }
        return elementGroups.values.any { it.size == 4 }
    }

    private fun hasFiveOfKind(): Boolean {
        val elementGroups = cards.groupBy { it }
        return elementGroups.values.any { it.size == 5 }
    }
}

data class Camel(val hand: Hand, val bestHand: Hand, val bid: Int) {
    fun print() {
        println(hand.cards.map { it.label } + " " + bid)
        println(bestHand.cards.map { it.label } + " " + bid)
        println()
    }
}

fun getCamelFromInputRow(input: String): Camel {
    val cardLabels = input.split(" ")[0]
    val bid = input.split(" ")[1].toInt()
    val hand = Hand(cardLabels.map { Card.getCardByLabel(it) })
    return Camel(hand, hand.getBestPossibleHand(hand.cards), bid)
}

fun calculateFinalWinnings(input: List<String>): Int {
    val camels = input.map { getCamelFromInputRow(it) }
    val sortedCamels = camels.sortedWith { camel1, camel2 ->
        if (camel1.hand.isStronger(camel1.hand, camel2.hand, camel2.hand)) 1 else -1
    }
    var sum = 0
    sortedCamels.forEachIndexed { index, camel ->
        sum += camel.bid * (index + 1)
    }
    return sum
}

fun main(args: Array<String>) {
    println(calculateFinalWinnings(readInput(DAY)))
}