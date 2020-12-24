package com.kylemayes.aoc2020.day23

import com.kylemayes.aoc2020.common.Input
import com.kylemayes.aoc2020.common.Progress
import com.kylemayes.aoc2020.common.ResourceInput
import com.kylemayes.aoc2020.common.Ring
import com.kylemayes.aoc2020.common.Solution
import com.kylemayes.aoc2020.common.readLines
import com.kylemayes.aoc2020.common.solve

fun play(cups: List<Int>, moves: Int): Ring<Int> {
    val ring = Ring(cups)

    val progress = Progress(moves)
    var current = cups[0]
    for (move in 0 until moves) {
        val a = ring.right(current)!!
        val b = ring.right(a)!!
        val c = ring.right(b)!!

        ring.remove(a)
        ring.remove(b)
        ring.remove(c)

        val bottom = (current - 1 downTo 1).asSequence()
        val top = (cups.size downTo current + 1).asSequence()
        for (destination in bottom + top) {
            if (ring.contains(destination)) {
                ring.add(c, left = destination)
                ring.add(b, left = destination)
                ring.add(a, left = destination)
                break
            }
        }

        current = ring.right(current)!!
        progress.track(move)
    }

    return ring
}

typealias ParsedInput = List<Int>

class Day23 : Solution<ParsedInput> {
    override fun parse(input: Input) = input
        .readLines()[0]
        .toCharArray()
        .map { it.toString().toInt() }

    override fun solvePart1(input: ParsedInput): Long {
        val cups = play(input, 100)
        return cups.sequence(start = cups.right(1))
            .take(cups.size - 1)
            .joinToString("") { it.toString() }
            .toLong()
    }

    override fun solvePart2(input: ParsedInput): Long {
        val cups = play(input + (input.maxOrNull()!! + 1..1000000), 10000000)
        val a = cups.right(1)!!
        val b = cups.right(a)!!
        return a.toLong() * b.toLong()
    }
}

fun main() = solve({ Day23() }, ResourceInput("day23.txt"))
