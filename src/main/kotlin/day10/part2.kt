package day10

import shared.readInput
import java.awt.Point
import kotlin.math.max
import kotlin.math.min

fun isPointInsideLoop(point: Point, loop: List<Point>): Boolean {
    var count = 0
    var j = loop.size - 1
    for (i in loop.indices) {
        val pi = loop[i]
        val pj = loop[j]

        // Check if the point is a vertex of the loop.
        if (point == pi) return false

        // Check if the point is on an edge of the loop.
        if (pi.y > pj.y) {
            if (isOnSegment(pj, point, pi)) return false
        } else {
            if (isOnSegment(pi, point, pj)) return false
        }

        // Count intersections for the ray-casting.
        if ((pi.y > point.y) != (pj.y > point.y) &&
            (point.x < (pj.x - pi.x) * (point.y - pi.y) / (pj.y - pi.y) + pi.x)) {
            count++
        }
        j = i
    }
    return count % 2 != 0
}

fun isOnSegment(p1: Point, q: Point, p2: Point): Boolean {
    return q.x <= max(p1.x, p2.x) && q.x >= min(p1.x, p2.x) &&
            q.y <= max(p1.y, p2.y) && q.y >= min(p1.y, p2.y)
}

fun countDotsInsideLoop(map: List<String>, loop: List<Point>): Int {
    var count = 0
    for (rowIndex in map.indices) {
        for (colIndex in map[rowIndex].indices) {
            if (isPointInsideLoop(Point(rowIndex, colIndex), loop)) {
                println("$rowIndex, $colIndex")
                count++
            }
        }
    }
    return count
}
fun getListOfPointsFormingPolygon(map: List<String>, pipe: Pipe?, pipes: List<Pipe> = emptyList()): List<Pipe> {
    if (pipe == null) return pipes
    val nextPipe = nextPipe(pipe, map)
    val newPipes = pipes.toMutableList().apply { add(pipe) }
    if (nextPipe?.sign == 'S') return newPipes
    return getListOfPointsFormingPolygon(map, nextPipe, newPipes)
}
fun main(args: Array<String>) {
    val map = readInput(DAY)
    val startPipe = map.withIndex().flatMap { (y, row) ->
        row.withIndex().mapNotNull { (x, char) ->
            if (char == 'S') Pipe('S', y, x) else null
        }
    }.first
    val listOfPoints = getListOfPointsFormingPolygon(map, startPipe).map { Point(it.x, it.y) }
    println(countDotsInsideLoop(map, listOfPoints))
}