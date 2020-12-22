package com.kylemayes.aoc2020.common.geometry

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class TileTest {
    @Test
    fun invariants() {
        assertThrows<AssertionError> { Tile(0, 0) { ' ' } }
        assertThrows<AssertionError> { Tile(1, 0) { ' ' } }
        assertThrows<AssertionError> { Tile(0, 1) { ' ' } }

        assertThrows<AssertionError> { Tile(0, 0, emptyList<Char>()) }
        assertThrows<AssertionError> { Tile(1, 0, emptyList<Char>()) }
        assertThrows<AssertionError> { Tile(0, 1, emptyList<Char>()) }
        assertThrows<AssertionError> { Tile(1, 1, emptyList<Char>()) }
    }

    @Test
    fun toField() {
        assertEquals(
            fieldOf(
                Point(0, 0) to 'a',
                Point(1, 0) to 'b',
                Point(0, 1) to 'c',
                Point(1, 1) to 'd',
            ),
            "ab\ncd".toTile().toField()
        )
    }

    @Test
    fun read() {
        val one = "a".toTile()
        val six = "abc\ndef".toTile()

        assertThrows<AssertionError> { one[Point(0, 1)] }
        assertThrows<AssertionError> { one[Point(1, 0)] }
        assertThrows<AssertionError> { one[Point(1, 1)] }
        assertThrows<AssertionError> { one[Point(0, -1)] }
        assertThrows<AssertionError> { one[Point(-1, 0)] }
        assertThrows<AssertionError> { one[Point(-1, -1)] }

        assertEquals(Rectangle(Point(0, 0), Point(0, 0)), one.bounds)
        assertEquals('a', one[Point(0, 0)])

        assertThrows<AssertionError> { six[Point(0, 2)] }
        assertThrows<AssertionError> { six[Point(3, 0)] }
        assertThrows<AssertionError> { six[Point(3, 2)] }
        assertThrows<AssertionError> { six[Point(0, -1)] }
        assertThrows<AssertionError> { six[Point(-1, 0)] }
        assertThrows<AssertionError> { six[Point(-1, -1)] }

        assertEquals(Rectangle(Point(0, 0), Point(2, 1)), six.bounds)
        assertEquals('a', six[Point(0, 0)])
        assertEquals('b', six[Point(1, 0)])
        assertEquals('c', six[Point(2, 0)])
        assertEquals('d', six[Point(0, 1)])
        assertEquals('e', six[Point(1, 1)])
        assertEquals('f', six[Point(2, 1)])

        assertEquals(
            listOf(Pair(Point(0, 0), 'a')),
            one.entries().toList(),
        )

        assertEquals(
            listOf(
                Pair(Point(0, 0), 'a'),
                Pair(Point(1, 0), 'b'),
                Pair(Point(2, 0), 'c'),
                Pair(Point(0, 1), 'd'),
                Pair(Point(1, 1), 'e'),
                Pair(Point(2, 1), 'f'),
            ),
            six.entries().toList(),
        )

        assertEquals("a", one.render { it })
        assertEquals("abc\ndef", six.render { it })
    }

    @Test
    fun write() {
        val one = "a".toTile()
        val six = "abc\ndef".toTile()

        assertThrows<AssertionError> { one[Point(0, 1)] = '?' }
        assertThrows<AssertionError> { one[Point(1, 0)] = '?' }
        assertThrows<AssertionError> { one[Point(1, 1)] = '?' }
        assertThrows<AssertionError> { one[Point(0, -1)] = '?' }
        assertThrows<AssertionError> { one[Point(-1, 0)] = '?' }
        assertThrows<AssertionError> { one[Point(-1, -1)] = '?' }

        one[Point(0, 0)] = 'b'

        assertEquals("b", one.render { it })

        assertThrows<AssertionError> { six[Point(0, 2)] = '?' }
        assertThrows<AssertionError> { six[Point(3, 0)] = '?' }
        assertThrows<AssertionError> { six[Point(3, 2)] = '?' }
        assertThrows<AssertionError> { six[Point(0, -1)] = '?' }
        assertThrows<AssertionError> { six[Point(-1, 0)] = '?' }
        assertThrows<AssertionError> { six[Point(-1, -1)] = '?' }

        six[Point(1, 0)] = '2'
        six[Point(1, 1)] = '5'

        assertEquals("a2c\nd5f", six.render { it })
    }

    @Test
    fun vectors() {
        val tile = "ab\ncd\nef".toTile()

        assertThrows<AssertionError> { tile.row(-1) }
        assertThrows<AssertionError> { tile.row(3) }

        assertEquals(listOf('a', 'b'), tile.row(0))
        assertEquals(listOf('c', 'd'), tile.row(1))
        assertEquals(listOf('e', 'f'), tile.row(2))

        assertThrows<AssertionError> { tile.column(-1) }
        assertThrows<AssertionError> { tile.column(2) }

        assertEquals(listOf('a', 'c', 'e'), tile.column(0))
        assertEquals(listOf('b', 'd', 'f'), tile.column(1))

        assertEquals(
            listOf(listOf('a', 'b'), listOf('c', 'd'), listOf('e', 'f')),
            tile.rows(),
        )

        assertEquals(
            listOf(listOf('a', 'c', 'e'), listOf('b', 'd', 'f')),
            tile.columns(),
        )
    }

    @Test
    fun transforms() {
        val a = "ab\ncd\nef".toTile()
        val b = "abc\ndef".toTile()
        val c = "123\n456\n789".toTile()

        assertThrows<AssertionError> { a.mergeX(b) }

        assertEquals("ab123\ncd456\nef789", a.mergeX(c).render { it })
        assertEquals("123ab\n456cd\n789ef", c.mergeX(a).render { it })

        assertThrows<AssertionError> { b.mergeY(a) }

        assertEquals("abc\ndef\n123\n456\n789", b.mergeY(c).render { it })
        assertEquals("123\n456\n789\nabc\ndef", c.mergeY(b).render { it })

        assertEquals("ba\ndc\nfe", a.flipX().render { it })
        assertEquals("cba\nfed", b.flipX().render { it })

        assertEquals("ef\ncd\nab", a.flipY().render { it })
        assertEquals("def\nabc", b.flipY().render { it })

        assertThrows<AssertionError> { a.rotateCW(45) }
        assertThrows<AssertionError> { a.rotateCW(135) }

        assertEquals(a, a.rotateCW(0))
        assertEquals(b, b.rotateCW(0))

        assertEquals("eca\nfdb", a.rotateCW(90).render { it })
        assertEquals("da\neb\nfc", b.rotateCW(90).render { it })

        assertEquals("fe\ndc\nba", a.rotateCW(180).render { it })
        assertEquals("fed\ncba", b.rotateCW(180).render { it })

        assertEquals("ace\nbdf", a.rotateCW(270).render { it })
        assertEquals("ad\nbe\ncf", b.rotateCW(270).render { it })

        assertEquals(a, a.rotateCW(360))
        assertEquals(b, b.rotateCW(360))

        assertEquals("ab\ncd\nef", a.crop(Point(0, 0).rectangleTo(Point(1, 2))).render { it })
        assertEquals("a\nc\ne", a.crop(Point(0, 0).rectangleTo(Point(0, 2))).render { it })
        assertEquals("b\nd\nf", a.crop(Point(1, 0).rectangleTo(Point(1, 2))).render { it })
        assertEquals("ab\ncd", a.crop(Point(0, 0).rectangleTo(Point(1, 1))).render { it })
        assertEquals("ab", a.crop(Point(0, 0).rectangleTo(Point(1, 0))).render { it })
        assertEquals("cd\nef", a.crop(Point(0, 1).rectangleTo(Point(1, 2))).render { it })
        assertEquals("ef", a.crop(Point(0, 2).rectangleTo(Point(1, 2))).render { it })
    }

    @Test
    fun toTile() {
        assertThrows<AssertionError> { listOf<String>().toTile() }
        assertThrows<AssertionError> { "".toTile() }
        assertThrows<AssertionError> { "\n".toTile() }
        assertThrows<AssertionError> { "a\nbc".toTile() }

        val one = "a".toTile()
        assertEquals(Rectangle(Point(0, 0), Point(0, 0)), one.bounds)
        assertEquals('a', one[Point(0, 0)])

        val six = "abc\ndef".toTile()
        assertEquals(Rectangle(Point(0, 0), Point(2, 1)), six.bounds)
        assertEquals('a', six[Point(0, 0)])
        assertEquals('b', six[Point(1, 0)])
        assertEquals('c', six[Point(2, 0)])
        assertEquals('d', six[Point(0, 1)])
        assertEquals('e', six[Point(1, 1)])
        assertEquals('f', six[Point(2, 1)])
    }
}
