package com.kylemayes.aoc2020.common.geometry

import org.apache.commons.math3.util.ArithmeticUtils
import kotlin.math.abs
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.max
import kotlin.math.min
import kotlin.math.pow
import kotlin.math.roundToInt
import kotlin.math.sin
import kotlin.math.sqrt

/** A 2D point. */
data class Point(val x: Int, val y: Int) : Comparable<Point> {
    /** Returns the Euclidean distance between this point and the supplied point. */
    fun distanceTo(other: Point): Double =
        sqrt((other.x - x).toDouble().pow(2) + (other.y - y).toDouble().pow(2))

    /** Returns the Manhattan distance between this point and the supplied point. */
    fun manhattanDistanceTo(other: Point): Int =
        abs(other.x - x) + abs(other.y - y)

    /** Returns the angle of the line segment from this point to the supplied point. */
    fun angleTo(other: Point): Double {
        val (dx, dy) = other - this
        val radians = atan2(dy.toDouble(), dx.toDouble())
        return (Math.toDegrees(radians) + 360.0) % 360.0
    }

    /** Returns the slope of the line segment from this point to the supplied point. */
    fun slopeTo(other: Point): Point {
        val (dx, dy) = other - this
        val gcd = ArithmeticUtils.gcd(dx, dy)
        return Point(dx / gcd, dy / gcd)
    }

    /** Returns the ray starting from this point and passing through the supplied point. */
    fun rayTo(other: Point): Ray =
        Ray(this, other)

    /** Returns the vector from this point to the supplied point. */
    fun vectorTo(other: Point): Vector =
        Vector(this, other)

    /** Returns the rectangle with this point and the supplied point as corners. */
    fun rectangleTo(other: Point): Rectangle {
        val topLeft = Point(min(x, other.x), min(y, other.y))
        val bottomRight = Point(max(x, other.x), max(y, other.y))
        return Rectangle(topLeft, bottomRight)
    }

    /** Returns a copy of this point rotated counter-clockwise around the supplied point. */
    fun rotateCCW(center: Point, degrees: Int): Point {
        val radians = Math.toRadians(degrees.toDouble())
        val cos = cos(radians)
        val sin = sin(radians)
        val rx = cos * (x - center.x) + sin * (y - center.y) + center.x
        val ry = cos * (y - center.y) - sin * (x - center.x) + center.y
        return Point(rx.roundToInt(), ry.roundToInt())
    }

    /** Returns the points neighboring this point. */
    fun neighbors(diagonal: Boolean = true): List<Point> {
        val neighbors = mutableListOf<Point>()

        for (dy in -1..1) {
            for (dx in -1..1) {
                if ((dx != 0 || dy != 0) && (diagonal || ((dx != 0) xor (dy != 0)))) {
                    neighbors.add(Point(x + dx, y + dy))
                }
            }
        }

        return neighbors
    }

    operator fun plus(other: Point): Point = Point(x + other.x, y + other.y)
    operator fun minus(other: Point): Point = Point(x - other.x, y - other.y)

    override fun compareTo(other: Point) = compareValuesBy(this, other, { x }, { y })

    override fun toString() = "($x, $y)"
}
