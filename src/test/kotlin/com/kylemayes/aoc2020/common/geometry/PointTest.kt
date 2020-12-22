package com.kylemayes.aoc2020.common.geometry

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import kotlin.math.sqrt

class PointTest {
    @Test
    fun distanceTo() {
        assertEquals(0.0, Point(0, 0).distanceTo(Point(0, 0)))

        assertEquals(1.0, Point(0, 0).distanceTo(Point(1, 0)))
        assertEquals(1.0, Point(0, 0).distanceTo(Point(-1, 0)))
        assertEquals(1.0, Point(0, 0).distanceTo(Point(0, 1)))
        assertEquals(1.0, Point(0, 0).distanceTo(Point(0, -1)))

        assertEquals(sqrt(2.0), Point(0, 0).distanceTo(Point(1, 1)))
        assertEquals(sqrt(2.0), Point(0, 0).distanceTo(Point(-1, 1)))
        assertEquals(sqrt(2.0), Point(0, 0).distanceTo(Point(1, -1)))
        assertEquals(sqrt(2.0), Point(0, 0).distanceTo(Point(-1, -1)))
    }

    @Test
    fun manhattanDistanceTo() {
        assertEquals(0, Point(0, 0).manhattanDistanceTo(Point(0, 0)))

        assertEquals(1, Point(0, 0).manhattanDistanceTo(Point(1, 0)))
        assertEquals(1, Point(0, 0).manhattanDistanceTo(Point(-1, 0)))
        assertEquals(1, Point(0, 0).manhattanDistanceTo(Point(0, 1)))
        assertEquals(1, Point(0, 0).manhattanDistanceTo(Point(0, -1)))

        assertEquals(2, Point(0, 0).manhattanDistanceTo(Point(1, 1)))
        assertEquals(2, Point(0, 0).manhattanDistanceTo(Point(-1, 1)))
        assertEquals(2, Point(0, 0).manhattanDistanceTo(Point(1, -1)))
        assertEquals(2, Point(0, 0).manhattanDistanceTo(Point(-1, -1)))
    }

    @Test
    fun angleTo() {
        assertEquals(0.0, Point(0, 0).angleTo(Point(1, 0)))
        assertEquals(90.0, Point(0, 0).angleTo(Point(0, 1)))
        assertEquals(180.0, Point(0, 0).angleTo(Point(-1, 0)))
        assertEquals(270.0, Point(0, 0).angleTo(Point(0, -1)))

        assertEquals(45.0, Point(0, 0).angleTo(Point(1, 1)))
        assertEquals(135.0, Point(0, 0).angleTo(Point(-1, 1)))
        assertEquals(225.0, Point(0, 0).angleTo(Point(-1, -1)))
        assertEquals(315.0, Point(0, 0).angleTo(Point(1, -1)))
    }

    @Test
    fun slopeTo() {
        assertEquals(Point(1, 0), Point(0, 0).slopeTo(Point(100, 0)))
        assertEquals(Point(-1, 0), Point(0, 0).slopeTo(Point(-100, 0)))

        assertEquals(Point(0, 1), Point(0, 0).slopeTo(Point(0, 100)))
        assertEquals(Point(0, -1), Point(0, 0).slopeTo(Point(0, -100)))

        assertEquals(Point(1, 1), Point(0, 0).slopeTo(Point(100, 100)))
        assertEquals(Point(-1, 1), Point(0, 0).slopeTo(Point(-100, 100)))
        assertEquals(Point(1, -1), Point(0, 0).slopeTo(Point(100, -100)))
        assertEquals(Point(-1, -1), Point(0, 0).slopeTo(Point(-100, -100)))

        assertEquals(Point(4, 17), Point(0, 0).slopeTo(Point(16, 68)))
    }

    @Test
    fun rayTo() {
        val ray = Point(1, 2).rayTo(Point(3, 4))
        assertEquals(Point(1, 2), ray.start)
        assertEquals(Point(1, 1), ray.slope)
        assertEquals(45.0, ray.angle)
    }

    @Test
    fun vectorTo() {
        val vector = Point(1, 2).vectorTo(Point(3, 4))
        assertEquals(Point(1, 2), vector.start)
        assertEquals(Point(3, 4), vector.end)
        assertEquals(sqrt(8.0), vector.magnitude)
        assertEquals(45.0, vector.angle)
    }

    @Test
    fun rectangleTo() {
        val a = Point(-1, -2).rectangleTo(Point(2, 1))
        assertEquals(Point(-1, -2), a.topLeft)
        assertEquals(Point(2, 1), a.bottomRight)

        val b = Point(1, -2).rectangleTo(Point(-2, 1))
        assertEquals(Point(-2, -2), b.topLeft)
        assertEquals(Point(1, 1), b.bottomRight)

        val c = Point(-1, 2).rectangleTo(Point(2, -1))
        assertEquals(Point(-1, -1), c.topLeft)
        assertEquals(Point(2, 2), c.bottomRight)

        val d = Point(1, 2).rectangleTo(Point(-2, -1))
        assertEquals(Point(-2, -1), d.topLeft)
        assertEquals(Point(1, 2), d.bottomRight)
    }

    @Test
    fun rotate() {
        assertEquals(Point(1, 2), Point(1, 2).rotateCCW(Point(0, 0), 0))
        assertEquals(Point(2, -1), Point(1, 2).rotateCCW(Point(0, 0), 90))
        assertEquals(Point(-1, -2), Point(1, 2).rotateCCW(Point(0, 0), 180))
        assertEquals(Point(-2, 1), Point(1, 2).rotateCCW(Point(0, 0), 270))
        assertEquals(Point(1, 2), Point(1, 2).rotateCCW(Point(0, 0), 360))

        assertEquals(Point(3, 4), Point(3, 4).rotateCCW(Point(1, 2), 0))
        assertEquals(Point(3, 0), Point(3, 4).rotateCCW(Point(1, 2), 90))
        assertEquals(Point(-1, 0), Point(3, 4).rotateCCW(Point(1, 2), 180))
        assertEquals(Point(-1, 4), Point(3, 4).rotateCCW(Point(1, 2), 270))
        assertEquals(Point(3, 4), Point(3, 4).rotateCCW(Point(1, 2), 360))
    }

    @Test
    fun neighbors() {
        assertEquals(
            listOf(
                Point(1, 1),
                Point(0, 2),
                Point(2, 2),
                Point(1, 3),
            ),
            Point(1, 2).neighbors(false),
        )

        assertEquals(
            listOf(
                Point(0, 1),
                Point(1, 1),
                Point(2, 1),
                Point(0, 2),
                Point(2, 2),
                Point(0, 3),
                Point(1, 3),
                Point(2, 3),
            ),
            Point(1, 2).neighbors(true),
        )
    }

    @Test
    fun arithmetic() {
        assertEquals(Point(3, 6), Point(1, 2) + Point(2, 4))
        assertEquals(Point(-1, -2), Point(1, 2) - Point(2, 4))
    }

    @Test
    fun stringify() {
        val point = Point(1, 2)
        assertEquals("(1, 2)", point.toString())
    }
}
