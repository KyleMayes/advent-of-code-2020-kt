package com.kylemayes.aoc2020.common.geometry

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class FieldTest {
    @Test
    fun toTile() {
        val field = fieldOf(
            Point(-1, -1) to 'a',
            Point(1, -1) to 'b',
            Point(0, 0) to 'c',
            Point(-1, 1) to 'd',
            Point(1, 1) to 'e',
        )

        assertEquals(
            "a b\n c \nd e",
            field.toTile()!!.render { it ?: ' ' },
        )

        assertEquals(
            "c \n e",
            field.toTile(Point(0, 0).rectangleTo(Point(1, 1)))!!.render { it ?: ' ' },
        )

        assertEquals(
            "a.b\n.c.\nd.e",
            field.toTile { '.' }!!.render { it },
        )

        assertEquals(
            "c.\n.e",
            field.toTile(Point(0, 0).rectangleTo(Point(1, 1))) { '.' }!!.render { it },
        )
    }

    @Test
    fun render() {
        val field = fieldOf(
            Point(-1, -1) to 'a',
            Point(1, -1) to 'b',
            Point(0, 0) to 'c',
            Point(-1, 1) to 'd',
            Point(1, 1) to 'e',
        )

        assertEquals(
            "a b\n c \nd e",
            field.render { it },
        )

        assertEquals(
            "a.b\n.c.\nd.e",
            field.render('.') { it },
        )

        assertEquals(
            "c.\n.e",
            field.render('.', Point(0, 0).rectangleTo(Point(1, 1))) { it },
        )
    }
}
