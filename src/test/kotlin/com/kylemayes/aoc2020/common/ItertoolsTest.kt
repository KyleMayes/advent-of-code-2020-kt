package com.kylemayes.aoc2020.common

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class ItertoolsTest {
    @Test
    fun cycle() {
        val one = listOf(1).cycle()
        assertEquals(1.repeat().take(10).toList(), one.take(10).toList())

        val three = listOf(1, 2, 3).cycle()
        assertEquals(listOf(1, 2, 3, 1, 2, 3, 1, 2, 3, 1), three.take(10).toList())
    }

    @Test
    fun repeat() {
        assertEquals(listOf(1, 1, 1), 1.repeat().take(3).toList())
    }

    @Test
    fun groups() {
        assertEquals(
            listOf<Pair<Boolean, List<Int>>>(),
            emptyList<Int>().groups { it % 2 == 0 },
        )

        assertEquals(
            listOf(Pair(false, listOf(1))),
            listOf(1).groups { it % 2 == 0 },
        )

        assertEquals(
            listOf(Pair(true, listOf(2))),
            listOf(2).groups { it % 2 == 0 },
        )

        assertEquals(
            listOf(
                Pair(false, listOf(1)),
                Pair(true, listOf(2)),
                Pair(false, listOf(3)),
                Pair(true, listOf(4)),
            ),
            listOf(1, 2, 3, 4).groups { it % 2 == 0 },
        )

        assertEquals(
            listOf(
                Pair(false, listOf(1, 3)),
                Pair(true, listOf(2, 4)),
                Pair(false, listOf(5, 7)),
                Pair(true, listOf(6, 8)),
            ),
            listOf(1, 3, 2, 4, 5, 7, 6, 8).groups { it % 2 == 0 },
        )
    }

    @Test
    fun combinations() {
        assertEquals(
            listOf("0", "1", "2", "3"),
            "0123".asIterable()
                .combinations(1)
                .map { it.joinToString("") }
                .toList()
        )

        assertEquals(
            listOf("01", "02", "03", "12", "13", "23"),
            "0123".asIterable()
                .combinations(2)
                .map { it.joinToString("") }
                .toList()
        )

        assertEquals(
            listOf("012", "013", "023", "123"),
            "0123".asIterable()
                .combinations(3)
                .map { it.joinToString("") }
                .toList()
        )
    }

    @Test
    fun permutations() {
        assertEquals(
            listOf("0", "1", "2"),
            "012".asIterable()
                .permutations(1)
                .map { it.joinToString("") }
                .toList()
        )

        assertEquals(
            listOf("01", "02", "10", "12", "20", "21"),
            "012".asIterable()
                .permutations(2)
                .map { it.joinToString("") }
                .toList()
        )

        assertEquals(
            listOf("012", "021", "102", "120", "201", "210"),
            "012".asIterable()
                .permutations(3)
                .map { it.joinToString("") }
                .toList()
        )

        assertEquals(
            listOf("012", "021", "102", "120", "201", "210"),
            "012".asIterable()
                .permutations()
                .map { it.joinToString("") }
                .toList()
        )
    }

    @Test
    fun product() {
        assertEquals(
            emptyList<String>(),
            "01".asIterable()
                .product(0)
                .map { it.joinToString("") }
                .toList(),
        )

        assertEquals(
            listOf("0", "1"),
            "01".asIterable()
                .product(1)
                .map { it.joinToString("") }
                .toList(),
        )

        assertEquals(
            listOf("00", "01", "10", "11"),
            "01".asIterable()
                .product(2)
                .map { it.joinToString("") }
                .toList(),
        )

        assertEquals(
            listOf("000", "001", "010", "011", "100", "101", "110", "111"),
            "01".asIterable()
                .product(3)
                .map { it.joinToString("") }
                .toList(),
        )

        assertEquals(
            listOf("0A", "0B", "1A", "1B", "2A", "2B", "3A", "3B"),
            listOf("0123".asIterable(), "AB".asIterable())
                .product()
                .map { it.joinToString("") }
                .toList()
        )
    }
}
