package com.kylemayes.aoc2020.common.geometry

import org.apache.commons.lang3.builder.EqualsBuilder
import org.apache.commons.lang3.builder.HashCodeBuilder

/** A 2D ray. */
class Ray(val start: Point, next: Point) {
    val slope = start.slopeTo(next)
    val angle = start.angleTo(next)

    override fun equals(other: Any?) = other is Ray && EqualsBuilder()
        .append(start, other.start)
        .append(slope, other.slope)
        .isEquals

    override fun hashCode() = HashCodeBuilder()
        .append(start)
        .append(slope)
        .toHashCode()

    override fun toString() = "Ray($start → $slope [$angle°])"
}
