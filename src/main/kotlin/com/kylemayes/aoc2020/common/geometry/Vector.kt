package com.kylemayes.aoc2020.common.geometry

/** A 2D vector. */
data class Vector(val start: Point, val end: Point) {
    val magnitude = start.distanceTo(end)
    val angle = start.angleTo(end)

    override fun toString() = "Vector($start → $end [${"%.2f".format(magnitude)} @ $angle°])"
}
