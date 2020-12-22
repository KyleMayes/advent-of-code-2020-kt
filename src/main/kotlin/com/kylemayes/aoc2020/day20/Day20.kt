package com.kylemayes.aoc2020.day20

import com.kylemayes.aoc2020.common.Input
import com.kylemayes.aoc2020.common.ResourceInput
import com.kylemayes.aoc2020.common.Solution
import com.kylemayes.aoc2020.common.geometry.Field
import com.kylemayes.aoc2020.common.geometry.Point
import com.kylemayes.aoc2020.common.geometry.Tile
import com.kylemayes.aoc2020.common.geometry.toField
import com.kylemayes.aoc2020.common.geometry.toTile
import com.kylemayes.aoc2020.common.readGroups
import com.kylemayes.aoc2020.common.solve
import java.util.Stack
import kotlin.math.roundToInt
import kotlin.math.sqrt

val seaMonster = listOf(
    "                  # ",
    "#    ##    ##    ###",
    " #  #  #  #  #  #   ",
).toTile()
    .entries()
    .filter { it.second == '#' }
    .map { it.first - Point(0, 1) }
    .toList()

data class Image(val id: Long, val tile: Tile<Char>) {
    val left = tile.column(0)
    val right = tile.column(tile.bounds.width - 1)
    val top = tile.row(0)
    val bottom = tile.row(tile.bounds.height - 1)
    val edges = setOf(left, right, top, bottom)

    fun matches(other: Image): Boolean =
        edges.any { other.edges.contains(it) }
}

typealias ParsedInput = Map<Long, Image>

class Day20 : Solution<ParsedInput> {
    private val candidates = mutableListOf<Image>()
    private val neighbors = mutableMapOf<Long, MutableSet<Long>>()
    private val corners = mutableSetOf<Long>()
    private val edges = mutableSetOf<Long>()

    override fun parse(input: Input) = input
        .readGroups()
        .associate {
            val match = Regex("""Tile (\d+):""").find(it[0])!!
            val id = match.groupValues[1].toLong()
            val tile = it.slice(1 until it.size).toTile()
            id to Image(id, tile)
        }

    override fun solvePart1(input: ParsedInput): Long {
        for (image in input.values) {
            candidates.add(image.copy())
            candidates.add(image.copy(tile = image.tile.flipX()))
            candidates.add(image.copy(tile = image.tile.flipY()))

            val r90 = image.tile.rotateCW(90)
            candidates.add(image.copy(tile = r90))
            candidates.add(image.copy(tile = r90.flipX()))
            candidates.add(image.copy(tile = r90.flipY()))

            val r180 = image.tile.rotateCW(180)
            candidates.add(image.copy(tile = r180))
            candidates.add(image.copy(tile = r180.flipX()))
            candidates.add(image.copy(tile = r180.flipY()))

            val r270 = image.tile.rotateCW(270)
            candidates.add(image.copy(tile = r270))
            candidates.add(image.copy(tile = r270.flipX()))
            candidates.add(image.copy(tile = r270.flipY()))
        }

        for (a in input.values) {
            for (b in candidates.filter { it.id != a.id }) {
                if (a.matches(b)) {
                    neighbors.computeIfAbsent(a.id) { mutableSetOf() }.add(b.id)
                }
            }
        }

        for ((id, neighbors) in neighbors) {
            if (neighbors.size == 2) {
                corners.add(id)
            } else if (neighbors.size == 3) {
                edges.add(id)
            }
        }

        return corners.reduce { acc, id -> acc * id }
    }

    override fun solvePart2(input: ParsedInput): Long {
        val dimensions = sqrt(input.size.toDouble()).roundToInt()

        val stack = Stack<Field<Image>>()

        val first = corners.minOrNull()!!
        for (image in candidates.filter { it.id == first }) {
            val field = Field<Image>()
            field[Point(0, 0)] = image
            stack.push(field)
        }

        var result: Field<Image>? = null
        while (stack.isNotEmpty()) {
            val field = stack.pop()
            if (field.size == input.size) {
                result = field
                break
            }

            val cx = (field.size - 1) % dimensions
            val cy = (field.size - 1) / dimensions
            val (nx, ny) = if (cx + 1 < dimensions) {
                Point(cx + 1, cy)
            } else {
                Point(0, cy + 1)
            }

            val left = field[Point(nx - 1, ny)]
            val top = field[Point(nx, ny - 1)]

            val used = field.values.map { it.id }.toSet()
            val neighbors = listOfNotNull(left, top)
                .map { neighbors[it.id]!! }
                .reduce { acc, ids -> acc.intersect(ids).toMutableSet() }

            val candidates = candidates.filter {
                !used.contains(it.id) && neighbors.contains(it.id)
            }

            for (candidate in candidates) {
                val horizontal = left == null || left.right == candidate.left
                val vertical = top == null || top.bottom == candidate.top
                if (horizontal && vertical) {
                    val clone = Field(field)
                    clone[Point(nx, ny)] = candidate
                    stack.push(clone)
                }
            }
        }

        val image = result!!
            .mapValues {
                val b = it.value.tile.bounds
                it.value.tile.crop(Point(1, 1).rectangleTo(b.bottomRight - Point(1, 1)))
            }
            .toField()
            .toTile { throw IllegalStateException("Unreachable!") }!!
            .rows()
            .map { it.reduce { acc, t -> acc.mergeX(t) } }
            .reduce { acc, t -> acc.mergeY(t) }

        for (flipped in listOf(image, image.flipX(), image.flipY())) {
            for (rotated in listOf(0, 90, 180, 270).map { flipped.rotateCW(it) }) {
                var monsters = 0

                for ((x, y) in rotated.bounds.points()) {
                    val points = seaMonster.map { Point(x + it.x, y + it.y) }
                    if (points.all { rotated.getOrNull(it) == '#' }) {
                        points.forEach { rotated[it] = 'O' }
                        monsters += 1
                    }
                }

                if (monsters != 0) {
                    return rotated.entries().count { it.second == '#' }.toLong()
                }
            }
        }

        return 0
    }
}

fun main() = solve({ Day20() }, ResourceInput("day20.txt"))
