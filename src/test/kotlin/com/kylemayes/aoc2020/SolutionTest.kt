package com.kylemayes.aoc2020

import com.kylemayes.aoc2020.common.ResourceInput
import com.kylemayes.aoc2020.common.Solution
import com.kylemayes.aoc2020.day20.Day20
import com.kylemayes.aoc2020.day21.Day21
import com.kylemayes.aoc2020.day22.Day22
import com.kylemayes.aoc2020.day23.Day23
import com.kylemayes.aoc2020.day24.Day24
import com.kylemayes.aoc2020.day25.Day25
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
        Arguments.of("day21.txt", Day21(), 2162, "lmzg,cxk,bsqh,bdvmx,cpbzbx,drbm,cfnt,kqprv"),
        Arguments.of("day22.txt", Day22(), 33925L, 33441L),
        Arguments.of("day23.txt", Day23(), 39564287L, 404431096944L),
        Arguments.of("day24.txt", Day24(), 232, 3519),
        Arguments.of("day25.txt", Day25(), 19414467L, 0),
    )

    @ParameterizedTest
    @MethodSource("solutionsParameters")
    fun <I> solutions(resource: String, solution: Solution<I>, part1: Any, part2: Any) {
        val input = ResourceInput(resource)
        assertEquals(part1, solution.solvePart1(solution.parse(input)))
        assertEquals(part2, solution.solvePart2(solution.parse(input)))
    }
}
