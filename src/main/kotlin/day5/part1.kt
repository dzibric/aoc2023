package day5

import java.io.File
import java.math.BigInteger
import kotlin.math.min

val SEEDS = listOf(
    104847962L,
    3583832,
    1212568077,
    114894281,
    3890048781,
    333451605,
    1520059863,
    217361990,
    310308287,
    12785610,
    3492562455,
    292968049,
    1901414562,
    516150861,
    2474299950,
    152867148,
    3394639029,
    59690410,
    862612782,
    176128197
)

data class Range(val destinationRangeStart: Long, val sourceRangeStart: Long, val rangeLength: Long) {
    fun calculateSeedNumber(seed: Long): Long {
        if (seed in sourceRangeStart..<sourceRangeStart + rangeLength) {
            return destinationRangeStart + (seed - sourceRangeStart)
        }
        return seed
    }

    fun calculateSeedNumberForRange(seedRange: LongRange): LongRange {
        val sourceRangeEnd = sourceRangeStart + rangeLength - 1

        // Check if the seed range intersects with the source range of this map entry
        if (seedRange.endInclusive < sourceRangeStart || seedRange.start > sourceRangeEnd) {
            // No intersection, return the range as is
            return seedRange
        }

        // Find the intersection of the seed range and the source range
        val intersectStart = maxOf(seedRange.start, sourceRangeStart)
        val intersectEnd = minOf(seedRange.endInclusive, sourceRangeEnd)

        // Calculate the new start and end based on the intersection
        val newStart = if (intersectStart == seedRange.start) {
            destinationRangeStart + (intersectStart - sourceRangeStart)
        } else {
            seedRange.start
        }

        val newEnd = if (intersectEnd == seedRange.endInclusive) {
            destinationRangeStart + (intersectEnd - sourceRangeStart)
        } else {
            seedRange.endInclusive
        }

        return LongRange(newStart, newEnd)
    }
}

fun readInput(section: String): List<String> {
    val path = "src/day5.day5.day5.day5.day5.day5.day5.day5.day5.day5.day5.main/kotlin/day5/${section}.txt"
    return File(path).readLines()
}

fun extractStringMapToRange(input: String): Range {
    val regex = """(\d+)\s+(\d+)\s+(\d+)""".toRegex()
    val matchResult = regex.find(input)!!

    val destRangeStart = matchResult.groupValues[1].toLong()
    val sourceRangeStart = matchResult.groupValues[2].toLong()
    val rangeLength = matchResult.groupValues[3].toLong()
    return Range(destRangeStart, sourceRangeStart, rangeLength)
}

fun calculateSeedInSection(seed: Long, section: List<Range>): Long {
    var newSeed = seed
    for (range in section) {
        val originalSeed = newSeed
        newSeed = range.calculateSeedNumber(newSeed)
        if (originalSeed != newSeed) {
            break
        }
    }
    return newSeed
}

fun main(args: Array<String>) {
    val soilMap = readInput("inputSoil").map { extractStringMapToRange(it) }
    val fertilizerMap = readInput("inputFertilizer").map { extractStringMapToRange(it) }
    val waterMap = readInput("inputWater").map { extractStringMapToRange(it) }
    val lightMap = readInput("inputLight").map { extractStringMapToRange(it) }
    val tempMap = readInput("inputTemperature").map { extractStringMapToRange(it) }
    val humidityMap = readInput("inputHumidity").map { extractStringMapToRange(it) }
    val locationMap = readInput("inputLocation").map { extractStringMapToRange(it) }

    val maps = listOf(soilMap, fertilizerMap, waterMap, lightMap, tempMap, humidityMap, locationMap)
    var minSeed = Long.MAX_VALUE
    SEEDS.forEach { seed ->
        var seedLocation = seed
        maps.forEachIndexed { index, map ->
            val sectionName = when (index) {
                0 -> "Soil"
                1 -> "Fertilizer"
                2 -> "Water"
                3 -> "Light"
                4 -> "Temp"
                5 -> "Humid"
                6 -> "Location"
                else -> "Unknown"
            }
            seedLocation = calculateSeedInSection(seedLocation, map)
        }
        if (seedLocation < minSeed) {
            minSeed = seedLocation
        }
    }
    println(minSeed)
}