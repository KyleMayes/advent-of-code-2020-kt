package com.kylemayes.aoc2020.common

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class RingTest {
    @Test
    fun query() {
        val zero = Ring(emptyList<Int>())

        assertEquals(0, zero.size)
        assertTrue(zero.isEmpty())

        assertFalse(zero.contains(322))

        assertEquals(emptyList<Int>(), zero.iterator().asSequence().toList())

        assertEquals(null, zero.left(322))
        assertEquals(null, zero.right(322))

        val one = Ring(listOf(1))

        assertEquals(1, one.size)
        assertFalse(one.isEmpty())

        assertTrue(one.contains(1))
        assertFalse(one.contains(322))

        assertEquals(listOf(1), one.iterator().asSequence().toList())

        assertEquals(1, one.left(1))
        assertEquals(1, one.right(1))
        assertEquals(null, one.left(322))
        assertEquals(null, one.right(322))

        val two = Ring(listOf(1, 2))

        assertEquals(2, two.size)
        assertFalse(two.isEmpty())

        assertTrue(two.contains(1))
        assertTrue(two.contains(2))
        assertFalse(two.contains(322))

        assertEquals(listOf(1, 2), two.iterator().asSequence().toList())

        assertEquals(2, two.left(1))
        assertEquals(2, two.right(1))
        assertEquals(1, two.left(2))
        assertEquals(1, two.right(2))
        assertEquals(null, two.left(322))
        assertEquals(null, two.right(322))

        val three = Ring(listOf(1, 2, 3))

        assertEquals(3, three.size)
        assertFalse(three.isEmpty())

        assertTrue(three.contains(1))
        assertTrue(three.contains(2))
        assertTrue(three.contains(3))
        assertFalse(three.contains(322))

        assertEquals(listOf(1, 2, 3), three.iterator().asSequence().toList())

        assertEquals(3, three.left(1))
        assertEquals(2, three.right(1))
        assertEquals(1, three.left(2))
        assertEquals(3, three.right(2))
        assertEquals(2, three.left(3))
        assertEquals(1, three.right(3))
        assertEquals(null, three.left(322))
        assertEquals(null, three.right(322))
    }

    @Test
    fun modification() {
        val ring = Ring<Int>()
        assertEquals("()", ring.toString())

        assertTrue(ring.add(1))
        assertEquals("(1)", ring.toString())
        assertFalse(ring.add(1))

        assertTrue(ring.add(2))
        assertEquals("(1, 2)", ring.toString())
        assertFalse(ring.add(2))

        assertTrue(ring.add(3))
        assertEquals("(1, 2, 3)", ring.toString())
        assertFalse(ring.add(3))

        assertTrue(ring.add(4, left = 1))
        assertEquals("(1, 4, 2, 3)", ring.toString())
        assertFalse(ring.add(4))

        assertTrue(ring.add(5, left = 2))
        assertEquals("(1, 4, 2, 5, 3)", ring.toString())
        assertFalse(ring.add(5))

        assertEquals(3, ring.left(1))
        assertEquals(4, ring.right(1))
        assertEquals(1, ring.left(4))
        assertEquals(2, ring.right(4))
        assertEquals(4, ring.left(2))
        assertEquals(5, ring.right(2))
        assertEquals(2, ring.left(5))
        assertEquals(3, ring.right(5))
        assertEquals(5, ring.left(3))
        assertEquals(1, ring.right(3))

        assertTrue(ring.remove(5))
        assertEquals("(1, 4, 2, 3)", ring.toString())
        assertFalse(ring.remove(5))

        assertTrue(ring.remove(4))
        assertEquals("(1, 2, 3)", ring.toString())
        assertFalse(ring.remove(4))

        assertTrue(ring.remove(1))
        assertEquals("(2, 3)", ring.toString())
        assertFalse(ring.remove(1))

        assertTrue(ring.remove(2))
        assertEquals("(3)", ring.toString())
        assertFalse(ring.remove(2))

        assertTrue(ring.remove(3))
        assertEquals("()", ring.toString())
        assertFalse(ring.remove(3))
    }
}
