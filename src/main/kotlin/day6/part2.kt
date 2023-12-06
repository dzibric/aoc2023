package day6
fun calculateAllPossibleDistances(raceLength: Long, currentRecord: Long): Long {
    return LongRange(0, raceLength / 2L).count { number ->
        val distance = number * (raceLength - number)
        distance > currentRecord
    } * 2 - 1L
}
fun main(args: Array<String>) {
    println(calculateAllPossibleDistances( 55826490, 246144110121111L))
}