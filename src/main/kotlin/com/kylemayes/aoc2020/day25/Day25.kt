package com.kylemayes.aoc2020.day25

import com.kylemayes.aoc2020.common.Input
import com.kylemayes.aoc2020.common.ResourceInput
import com.kylemayes.aoc2020.common.Solution
import com.kylemayes.aoc2020.common.readLines
import com.kylemayes.aoc2020.common.solve

typealias ParsedInput = List<Long>

class Day25 : Solution<ParsedInput> {
    override fun parse(input: Input) = input
        .readLines()
        .map { it.toLong() }

    override fun solvePart1(input: ParsedInput): Any {
        val cardPK = input[0]
        val doorPK = input[1]

        var value = 1L
        var loop = 0L
        for (size in 1L..Long.MAX_VALUE) {
            value *= 7L
            value %= 20201227L
            if (value == cardPK) {
                loop = size
                break
            }
        }

        var key = 1L
        for (size in 1L..loop) {
            key *= doorPK
            key %= 20201227L
        }

        return key
    }

    override fun solvePart2(input: ParsedInput): Any {
        return 0
    }
}

fun main() = solve({ Day25() }, ResourceInput("day25.txt"))
