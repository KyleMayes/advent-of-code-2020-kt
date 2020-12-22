package com.kylemayes.aoc2020.common.geometry

import org.apache.commons.lang3.builder.EqualsBuilder
import org.apache.commons.lang3.builder.HashCodeBuilder

/** A dense, finite 2D grid. */
class Tile<T> : Cloneable {
    val bounds: Rectangle

    private val cells: MutableList<T>

    constructor(width: Int, height: Int, cells: List<T>) {
        assert(width > 0 && height > 0)
        assert(cells.size == width * height)
        bounds = Rectangle(Point(0, 0), Point(width - 1, height - 1))
        this.cells = cells.toMutableList()
    }

    constructor(width: Int, height: Int, cell: (point: Point) -> T) : this(
        width,
        height,
        MutableList(width * height) { cell(Point(it % width, it / width)) }
    )

    /** Returns this tile as a field. */
    fun toField(): Field<T> = HashMap(entries().associate { it })

    // ===========================================
    // Read / Write
    // ===========================================

    fun getOrNull(point: Point): T? {
        return cells.getOrNull((point.y * bounds.width) + point.x)
    }

    operator fun get(point: Point): T {
        assert(bounds.contains(point)) { "$point outside of tile bounds ($bounds)." }
        return getOrNull(point)!!
    }

    operator fun set(point: Point, value: T) {
        assert(bounds.contains(point)) { "$point outside of tile bounds ($bounds)." }
        cells[(point.y * bounds.width) + point.x] = value
    }

    /** Returns a sequence of the entries in this tile. */
    fun entries(): Sequence<Pair<Point, T>> =
        bounds.points().map { it to this[it] }

    /** Returns this tile as a string where each row is a line and each value is a character. */
    fun render(render: (value: T) -> Char): String =
        rows().joinToString("\n") { it.joinToString("") { v -> render(v).toString() } }

    // ===========================================
    // Vectors
    // ===========================================

    /** Returns the values in a row in this tile. */
    fun row(y: Int): List<T> {
        assert(y >= 0 && y < bounds.height) { "Row $y outside of tile bounds ($bounds)." }
        val start = y * bounds.width
        return cells.slice(start until start + bounds.width)
    }

    /** Returns the values in a column in this tile. */
    fun column(x: Int): List<T> {
        assert(x >= 0 && x < bounds.width) { "Column $x outside of tile bounds ($bounds)." }
        return (0 until bounds.height).map { cells[(it * bounds.width) + x] }
    }

    /** Returns the rows in this tile. */
    fun rows(): List<List<T>> = cells.chunked(bounds.width)

    /** Returns the columns in this tile. */
    fun columns(): List<List<T>> = List(bounds.width) { column(it) }

    // ===========================================
    // Transforms
    // ===========================================

    /** Merges this tile and the supplied tile into a new tile horizontally. */
    fun mergeX(right: Tile<T>): Tile<T> {
        assert(bounds.height == right.bounds.height) { "Tile heights are not equal." }
        val width = bounds.width + right.bounds.width
        val cells = mutableListOf<T>()
        rows().zip(right.rows()).forEach { cells.addAll(it.first); cells.addAll(it.second) }
        return Tile(width, bounds.height, cells)
    }

    /** Merges this tile and the supplied tile into a new tile vertically. */
    fun mergeY(bottom: Tile<T>): Tile<T> {
        assert(bounds.width == bottom.bounds.width) { "Tile widths are not equal." }
        val height = bounds.height + bottom.bounds.height
        return Tile(bounds.width, height, cells + bottom.cells)
    }

    /** Flips this tile into a new tile horizontally. */
    fun flipX() = Tile(bounds.width, bounds.height, rows().map { it.reversed() }.flatten())

    /** Flips this tile into a new tile vertically. */
    fun flipY() = Tile(bounds.width, bounds.height, rows().reversed().flatten())

    /** Rotates this tile into a new tile clockwise. */
    @Suppress("NAME_SHADOWING")
    fun rotateCW(degrees: Int): Tile<T> {
        val degrees = ((degrees % 360) + 360) % 360
        assert(degrees % 90 == 0) { "Rotation angle is not a multiple of 90° ($degrees°)" }

        if (degrees == 0) {
            return clone()
        }

        val (width, height) = if (degrees == 180) {
            Pair(bounds.width, bounds.height)
        } else {
            Pair(bounds.height, bounds.width)
        }

        val transform = when (degrees) {
            90 -> { p: Point -> Point(p.y, width - p.x - 1) }
            180 -> { p: Point -> Point(width - p.x - 1, height - p.y - 1) }
            270 -> { p: Point -> Point(p.y, p.x) }
            else -> throw IllegalStateException("Unreachable.")
        }

        return Tile(width, height) { this[transform(it)] }
    }

    /** Returns a part of this tile as a new tile. */
    fun crop(crop: Rectangle): Tile<T> {
        assert(bounds.contains(crop.topLeft) && bounds.contains(crop.bottomRight))
        assert(crop.width > 0 && crop.height > 0)
        return Tile(crop.width, crop.height) { this[it + crop.topLeft] }
    }

    // ===========================================
    // Other
    // ===========================================

    override fun clone() = Tile(bounds.width, bounds.height, cells)

    override fun equals(other: Any?) = other is Tile<*> && EqualsBuilder()
        .append(bounds, other.bounds)
        .append(cells, other.cells)
        .isEquals

    override fun hashCode() = HashCodeBuilder()
        .append(bounds)
        .append(cells)
        .toHashCode()

    override fun toString() = rows().joinToString { it.joinToString() }
}

/** Returns these strings as a tile of characters where each string is a row. */
fun List<String>.toTile(): Tile<Char> {
    assert(size > 0 && this[0].isNotEmpty())

    val width = this[0].length
    val height = size
    assert(all { it.length == width })

    return Tile(width, height) { this[it.y][it.x] }
}

/** Returns this string as a tile of characters where each line is a row. */
fun String.toTile(): Tile<Char> = split("\n").toTile()
