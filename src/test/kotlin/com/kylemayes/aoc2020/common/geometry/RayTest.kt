package com.kylemayes.aoc2020.common.geometry

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class RayTest {
    @Test
    fun stringify() {
        val ray = Ray(Point(0, 0), Point(2, 2))
        Assertions.assertEquals("Ray((0, 0) → (1, 1) [45.0°])", ray.toString())
    }
}
