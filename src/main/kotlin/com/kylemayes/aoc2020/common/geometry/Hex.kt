package com.kylemayes.aoc2020.common.geometry

/** A direction on a hexagonal grid. */
enum class HexDirection(val delta: HexPoint) {
    E(HexPoint(1, -1, 0)),
    SE(HexPoint(0, -1, 1)),
    SW(HexPoint(-1, 0, 1)),
    W(HexPoint(-1, 1, 0)),
    NW(HexPoint(0, 1, -1)),
    NE(HexPoint(1, 0, -1)),
}

/** A point on a hexagonal grid. */
data class HexPoint(val x: Int, val y: Int, val z: Int) : Comparable<HexPoint> {
    /** Returns the points neighboring this point. */
    fun neighbors() = HexDirection.values().map { this + it.delta }

    operator fun plus(other: HexPoint): HexPoint =
        HexPoint(x + other.x, y + other.y, z + other.z)
    operator fun minus(other: HexPoint): HexPoint =
        HexPoint(x - other.x, y - other.y, z - other.z)

    override fun compareTo(other: HexPoint) =
        compareValuesBy(this, other, { x }, { y }, { z })

    override fun toString() = "($x, $y, $z)"
}
