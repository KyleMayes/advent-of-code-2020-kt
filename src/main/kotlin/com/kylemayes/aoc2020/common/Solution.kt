package com.kylemayes.aoc2020.common

import com.github.ajalt.mordant.TermColors
import kotlin.system.measureTimeMillis

/** A solution to an Advent of Code problem. */
interface Solution<I> {
    /** Parses the input for this solution. */
    fun parse(input: Input): I

    /** Solves part 1 of the problem. */
    fun solvePart1(input: I): Any
    /** Solves part 2 of the problem. */
    fun solvePart2(input: I): Any
}

/** Runs this solution and prints the results. */
fun <I> solve(create: () -> Solution<I>, vararg inputs: Input) {
    val t = TermColors()
    for (input in inputs) {
        val full = input.toString().replace("\n", "\\n")
        val header = if (full.length > 50) {
            full.substring(0, 47) + "..."
        } else {
            full
        }

        val spacer = "─".repeat(header.length + 2)
        println(t.bold(t.cyan("┌$spacer┐")))
        println(t.bold(t.cyan("| $header |")))
        println(t.bold(t.cyan("└$spacer┘")))

        val solution = create()
        for ((i, solve) in listOf(solution::solvePart1, solution::solvePart2).withIndex()) {
            val result: Any
            val millis = measureTimeMillis { result = solve(solution.parse(input)) }
            print(t.bold(t.blue("Part ${i + 1}: ")))
            print(t.bold(t.yellow("$result ")))
            println(t.bold(t.blue("(${millis / 1000.0}s)")))
        }
    }
}
