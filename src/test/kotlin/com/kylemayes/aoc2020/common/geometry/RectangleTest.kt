package com.kylemayes.aoc2020.common.geometry

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class RectangleTest {
    @Test
    fun invariants() {
        assertThrows<AssertionError> { Rectangle(Point(1, 1), Point(-1, 1)) }
        assertThrows<AssertionError> { Rectangle(Point(1, 1), Point(1, -1)) }
        assertThrows<AssertionError> { Rectangle(Point(1, 1), Point(-1, -1)) }
    }

    @Test
    fun properties() {
        val one = Rectangle(Point(0, 0), Point(0, 0))
        assertEquals(1, one.width)
        assertEquals(1, one.height)
        assertEquals(1, one.area)

        val sixteen = Rectangle(Point(-1, -2), Point(2, 1))
        assertEquals(4, sixteen.width)
        assertEquals(4, sixteen.height)
        assertEquals(16, sixteen.area)
    }

    @Test
    fun contains() {
        val one = Rectangle(Point(0, 0), Point(0, 0))
        assertTrue(one.contains(Point(0, 0)))
        assertFalse(one.contains(Point(1, 0)))
        assertFalse(one.contains(Point(-1, 0)))
        assertFalse(one.contains(Point(0, 1)))
        assertFalse(one.contains(Point(0, -1)))
    }

    @Test
    fun points() {
        assertEquals(
            listOf(Point(0, 0)),
            Rectangle(Point(0, 0), Point(0, 0)).points().toList()
        )

        assertEquals(
            listOf(
                Point(1, 2),
                Point(2, 2),
                Point(3, 2),
                Point(1, 3),
                Point(2, 3),
                Point(3, 3),
                Point(1, 4),
                Point(2, 4),
                Point(3, 4),
            ),
            Rectangle(Point(1, 2), Point(3, 4)).points().toList()
        )
    }

    @Test
    fun stringify() {
        val rectangle = Rectangle(Point(1, 2), Point(3, 4))
        assertEquals("Rectangle((1, 2) → (3, 4))", rectangle.toString())
    }
}
