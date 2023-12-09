package day8

import shared.readInput
import java.lang.IllegalStateException

const val DAY = "day8"

private const val INSTRUCTIONS =
    "LLRLRRRLRRRLRRLLRRRLLRRLLRLRLRRRLRRRLLRRRLLRRRLRRLRRLRLRRLLRRRLRRRLLRRRLRRLLLRRLRLLLRLRRRLRLRLLLRRLRRLLLRRRLLRRRLRLRLLRRLRLRRRLRLRLLRLRRLRRRLRRLRLRRRLRLRRLRRLRLRRLLRLRLRRLRLLRRLRRLRLRRLLRLRLLRRLLRLLLRRLRLRRRLRRRLRRRLRLRLRRRLLLRLRRLRLRRRLRRRLRRRLRLRRRLRRRLRRRLRRRR"

class Node(val name: String, var leftChild: String, var rightChild: String) {
    fun print(instruction: Char) {
        println("$instruction -> $name->($leftChild, $rightChild)")
    }
}

fun extractNodesToMap(pairs: List<String>): HashMap<String, Node> {
    val nodes = hashMapOf<String, Node>()

    pairs.forEach { pair ->
        val (parent, children) = pair.split(" = ")
        val (left, right) = children.trim('(', ')').split(", ").map { it.trim() }

        nodes[parent] = Node(parent, left, right)
    }

    return nodes
}


fun findPathToZZZ(nodes: HashMap<String, Node>, currentInstructionIndex: Int = 0, node: Node?, path: Int = 0): Int {
    if (node == null) throw IllegalStateException("Node is null")
    println("Visiting: ${node.name}, Path Length: $path")

    if (node.name == "ZZZ") return path

    val instructionIndex = currentInstructionIndex % INSTRUCTIONS.length
    return when (INSTRUCTIONS[instructionIndex]) {
        'R' -> findPathToZZZ(nodes, instructionIndex + 1, nodes[node.rightChild], path + 1)

        'L' -> findPathToZZZ(nodes, instructionIndex + 1, nodes[node.leftChild], path + 1)

        else -> throw IllegalStateException("Invalid instruction")
    }
}

fun main(args: Array<String>) {
    val nodes = extractNodesToMap(readInput(DAY))

    println(findPathToZZZ(nodes, node = nodes["AAA"]!!))
}