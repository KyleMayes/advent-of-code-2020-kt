package com.kylemayes.aoc2020.day22

import com.kylemayes.aoc2020.common.Input
import com.kylemayes.aoc2020.common.ResourceInput
import com.kylemayes.aoc2020.common.Solution
import com.kylemayes.aoc2020.common.readGroups
import com.kylemayes.aoc2020.common.solve

fun playGameRec(p1: List<Long>, p2: List<Long>, recursion: Boolean): Pair<Boolean, List<Long>> {
    val seen = mutableSetOf<Pair<List<Long>, List<Long>>>()

    val h1 = p1.toMutableList()
    val h2 = p2.toMutableList()
    while (h1.isNotEmpty() && h2.isNotEmpty()) {
        if (!seen.add(Pair(h1.toList(), h2.toList()))) {
            return Pair(true, h1)
        }

        val c1 = h1.removeAt(0)
        val c2 = h2.removeAt(0)
        val winner = if (recursion && h1.size >= c1 && h2.size >= c2) {
            playGameRec(h1.subList(0, c1.toInt()), h2.subList(0, c2.toInt()), recursion).first
        } else {
            c1 > c2
        }

        if (winner) {
            h1.add(c1)
            h1.add(c2)
        } else {
            h2.add(c2)
            h2.add(c1)
        }
    }

    return if (h1.isNotEmpty()) {
        Pair(true, h1)
    } else {
        Pair(false, h2)
    }
}

fun playGame(p1: List<Long>, p2: List<Long>, recursion: Boolean): Long {
    val (_, hand) = playGameRec(p1, p2, recursion)
    return hand.withIndex().map { (i, v) -> v * (hand.size - i) }.sum()
}

typealias ParsedInput = List<List<Long>>

class Day22 : Solution<ParsedInput> {
    override fun parse(input: Input) = input
        .readGroups()
        .map { it.subList(1, it.size).map { l -> l.toLong() }.toMutableList() }
        .toMutableList()

    override fun solvePart1(input: ParsedInput) =
        playGame(input[0], input[1], false)

    override fun solvePart2(input: ParsedInput) =
        playGame(input[0], input[1], true)
}

fun main() = solve({ Day22() }, ResourceInput("day22.txt"))
