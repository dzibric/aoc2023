package day8

import shared.leastCommonMultiple
import shared.readInput
import java.lang.IllegalStateException

private const val INSTRUCTIONS =
    "LLRLRRRLRRRLRRLLRRRLLRRLLRLRLRRRLRRRLLRRRLLRRRLRRLRRLRLRRLLRRRLRRRLLRRRLRRLLLRRLRLLLRLRRRLRLRLLLRRLRRLLLRRRLLRRRLRLRLLRRLRLRRRLRLRLLRLRRLRRRLRRLRLRRRLRLRRLRRLRLRRLLRLRLRRLRLLRRLRRLRLRRLLRLRLLRRLLRLLLRRLRLRRRLRRRLRRRLRLRLRRRLLLRLRRLRLRRRLRRRLRRRLRLRRRLRRRLRRRLRRRR"
tailrec fun findPathToZ(nodes: HashMap<String, Node>, currentInstructionIndex: Int = 0, node: Node?, path: Long = 0): Long {
    if (node == null) throw IllegalStateException("Node is null")
    println("Visiting: ${node.name}, Path Length: $path")

    if (node.name.endsWith("Z")) return path

    val instructionIndex = currentInstructionIndex % INSTRUCTIONS.length
    return when (INSTRUCTIONS[instructionIndex]) {
        'R' -> findPathToZ(nodes, instructionIndex + 1, nodes[node.rightChild], path + 1)

        'L' -> findPathToZ(nodes, instructionIndex + 1, nodes[node.leftChild], path + 1)

        else -> throw IllegalStateException("Invalid instruction")
    }
}

fun main(args: Array<String>) {
    val nodes = extractNodesToMap(readInput(DAY))
    val nodesEndingWithA = HashMap(nodes.filter { it.key.endsWith("A") })
    val paths = nodesEndingWithA.map { findPathToZ(nodes, 0, it.value) }
    println(paths.reduce { acc, i -> leastCommonMultiple(acc, i) })
}