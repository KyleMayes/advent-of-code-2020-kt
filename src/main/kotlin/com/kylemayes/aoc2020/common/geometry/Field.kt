package com.kylemayes.aoc2020.common.geometry

import kotlin.math.max
import kotlin.math.min

/** A sparse, infinite 2D grid. */
typealias Field<T> = HashMap<Point, T>

/** Returns a field containing the supplied entries. */
fun <T> fieldOf(vararg pairs: Pair<Point, T>) = Field(mutableMapOf(*pairs))

/** Returns a field containing the supplied entries. */
fun <T> Map<Point, T>.toField() = Field(this)

/** Returns this field as a tile (if non-empty). */
@Suppress("NAME_SHADOWING")
fun <T> Field<T>.toTile(bounds: Rectangle? = null): Tile<T?>? {
    val bounds = bounds ?: bounds() ?: return null
    return Tile(bounds.width, bounds.height) { this[it + bounds.topLeft] }
}

/** Returns this field as a tile (if non-empty). */
@Suppress("NAME_SHADOWING")
fun <T> Field<T>.toTile(bounds: Rectangle? = null, default: (point: Point) -> T): Tile<T>? {
    val bounds = bounds ?: bounds() ?: return null
    return Tile(bounds.width, bounds.height) { this[it + bounds.topLeft] ?: default(it) }
}

/** Returns the bounds of this field (if non-empty). */
fun <T> Field<T>.bounds(): Rectangle? {
    if (isEmpty()) {
        return null
    }

    var minX = 0
    var maxX = 0
    var minY = 0
    var maxY = 0

    for ((x, y) in keys) {
        minX = min(minX, x)
        maxX = max(maxX, x)
        minY = min(minY, y)
        maxY = max(maxY, y)
    }

    return Rectangle(Point(minX, minY), Point(maxX, maxY))
}

/** Returns this field as a string where each row is a line and each value is a character. */
fun <T> Field<T>.render(
    default: Char = ' ',
    bounds: Rectangle? = null,
    render: (value: T) -> Char,
): String = toTile(bounds)?.render { it?.let { render(it) } ?: default } ?: ""
