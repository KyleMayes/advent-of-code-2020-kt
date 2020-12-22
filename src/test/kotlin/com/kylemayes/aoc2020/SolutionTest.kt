package com.kylemayes.aoc2020

import com.kylemayes.aoc2020.common.ResourceInput
import com.kylemayes.aoc2020.common.Solution
import com.kylemayes.aoc2020.day20.Day20
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.util.stream.Stream

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class SolutionTest {
    fun solutionsParameters(): Stream<Arguments> = Stream.of(
        Arguments.of("day20.txt", Day20(), 32287787075651L, 1939L),
    )

    @ParameterizedTest
    @MethodSource("solutionsParameters")
    fun <I> solutions(resource: String, solution: Solution<I>, part1: Any, part2: Any) {
        val input = ResourceInput(resource)
        assertEquals(part1, solution.solvePart1(solution.parse(input)))
        assertEquals(part2, solution.solvePart2(solution.parse(input)))
    }
}