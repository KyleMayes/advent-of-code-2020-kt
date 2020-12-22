package com.kylemayes.aoc2020.common

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class InputTest {
    @Test
    fun read() {
        assertEquals("foo\nbar\nbaz\n", StringInput("foo\nbar\nbaz\n").read())
        assertEquals("foo\nbar\nbaz\n", ResourceInput("resource.txt").read())
    }

    @Test
    fun readGroups() {
        assertEquals(
            emptyList<List<String>>(),
            StringInput("\n").readGroups(),
        )

        assertEquals(
            listOf(listOf("foo"), listOf("bar", "baz")),
            StringInput("foo\n\nbar\nbaz\n").readGroups(),
        )
    }

    @Test
    fun readLines() {
        assertEquals(
            listOf("foo", "bar", "baz"),
            StringInput("foo\nbar\nbaz\n").readLines(empty = false),
        )

        assertEquals(
            listOf("foo", "", "bar", "", "baz", ""),
            StringInput("foo\n\nbar\n\nbaz\n").readLines(empty = true),
        )
    }
}
