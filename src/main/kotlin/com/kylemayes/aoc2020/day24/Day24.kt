package com.kylemayes.aoc2020.day24

import com.kylemayes.aoc2020.common.Input
import com.kylemayes.aoc2020.common.ResourceInput
import com.kylemayes.aoc2020.common.Solution
import com.kylemayes.aoc2020.common.geometry.HexDirection
import com.kylemayes.aoc2020.common.geometry.HexPoint
import com.kylemayes.aoc2020.common.readLines
import com.kylemayes.aoc2020.common.solve
import java.lang.Exception

fun trace(input: ParsedInput): MutableSet<HexPoint> {
    val black = mutableSetOf<HexPoint>()

    for (line in input) {
        val point = line.fold(HexPoint(0, 0, 0)) { acc, d -> acc + d.delta }
        if (black.contains(point)) {
            black.remove(point)
        } else {
            black.add(point)
        }
    }

    return black
}

typealias ParsedInput = List<List<HexDirection>>

class Day24 : Solution<ParsedInput> {
    override fun parse(input: Input) = input
        .readLines()
        .map { it.toUpperCase() }
        .map {
            val directions = mutableListOf<HexDirection>()

            var index = 0
            while (index < it.length) {
                index += try {
                    directions.add(HexDirection.valueOf(it.substring(index, index + 2)))
                    2
                } catch (e: Exception) {
                    directions.add(HexDirection.valueOf(it.substring(index, index + 1)))
                    1
                }
            }

            directions
        }

    override fun solvePart1(input: ParsedInput) =
        trace(input).size

    override fun solvePart2(input: ParsedInput): Int {
        var black = trace(input)

        for (day in 1..100) {
            val destination = HashSet(black)

            for (point in black + black.flatMap { it.neighbors() }.toSet()) {
                val neighbors = point.neighbors().count { black.contains(it) }
                if (black.contains(point)) {
                    if (neighbors == 0 || neighbors > 2) {
                        destination.remove(point)
                    }
                } else if (neighbors == 2) {
                    destination.add(point)
                }
            }

            black = destination
        }

        return black.size
    }
}

fun main() = solve({ Day24() }, ResourceInput("day24.txt"))
