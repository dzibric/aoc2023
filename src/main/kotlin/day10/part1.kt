package day10

import shared.readInput

const val DAY = "day10"

class Pipe(val sign: Char, val x: Int, val y: Int, val prevPipe: Pipe? = null)

fun nextPipe(pipe: Pipe, map: List<String>): Pipe? {
    val topPipe = if (pipe.x > 0) map[pipe.x - 1][pipe.y] else ' '
    val botPipe = if (pipe.x < map.size - 1) map[pipe.x + 1][pipe.y] else ' '
    val leftPipe = if (pipe.y > 0) map[pipe.x][pipe.y - 1] else ' '
    val rightPipe = if (pipe.y < map[0].length - 1) map[pipe.x][pipe.y + 1] else ' '

    val canConnectTop = topPipe == '|' || topPipe == 'F' || topPipe == '7' || topPipe == 'S'
    val canConnectRight = rightPipe == '-' || rightPipe == '7' || rightPipe == 'J' || rightPipe == 'S'
    val canConnectLeft = leftPipe == '-' || leftPipe == 'L' || leftPipe == 'F' || leftPipe == 'S'
    val canConnectBot = botPipe == '|' || botPipe == 'L' || botPipe == 'J' || botPipe == 'S'

    return when {
        (pipe.sign == '|' || pipe.sign == 'J' || pipe.sign == 'L' || pipe.sign == 'S') && canConnectTop && (pipe.prevPipe?.x != pipe.x - 1 || pipe.prevPipe?.y != pipe.y) -> {
            Pipe(topPipe, pipe.x - 1, pipe.y, pipe)
        }
        (pipe.sign == '-' || pipe.sign == 'J' || pipe.sign == '7' || pipe.sign == 'S') && canConnectLeft && (pipe.prevPipe?.x != pipe.x ||  pipe.prevPipe?.y != pipe.y - 1) -> {
            Pipe(leftPipe, pipe.x, pipe.y - 1, pipe)
        }
        (pipe.sign == '-' || pipe.sign == 'F' || pipe.sign == 'L' || pipe.sign == 'S') && canConnectRight && (pipe.prevPipe?.x != pipe.x ||  pipe.prevPipe?.y != pipe.y + 1) -> {
            Pipe(rightPipe, pipe.x, pipe.y + 1, pipe)
        }
        (pipe.sign == '|' || pipe.sign == 'F' || pipe.sign == '7' || pipe.sign == 'S') && canConnectBot && (pipe.prevPipe?.x != pipe.x + 1 ||  pipe.prevPipe?.y != pipe.y) -> {
            Pipe(botPipe, pipe.x + 1, pipe.y, pipe)
        }
        else -> null
    }
}

fun getNextPipesUntilStart(map: List<String>, pipe: Pipe?, level: Int = 0): Int {
    if (pipe == null) return level
    println(pipe.sign)
    val nextPipe = nextPipe(pipe, map)
    if (nextPipe?.sign == 'S') return level
    return getNextPipesUntilStart(map, nextPipe, level + 1)
}

fun main(args: Array<String>) {
    val map = readInput(DAY)
    val startPipe = map.withIndex().flatMap { (y, row) ->
        row.withIndex().mapNotNull { (x, char) ->
            if (char == 'S') Pipe('S', y, x) else null
        }
    }.first
    println(getNextPipesUntilStart(map, startPipe) / 2 + 1)
}