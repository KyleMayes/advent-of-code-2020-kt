package com.kylemayes.aoc2020.common

import kotlin.math.floor

/** A progress tracker for a loop with a known number of iterations. */
class Progress(private val iterations: Int) {
    private var started = false
    private var period = floor(iterations.toDouble() / 100.0).toInt()

    fun track(iteration: Int) {
        if (!started) {
            print("Working... 0%")
            started = true
            return
        }

        if (iteration + 1 == iterations) {
            print("\r              \r")
            return
        }

        if (iteration % period == 0) {
            val periods = iteration / period
            print("\rWorking... $periods%")
        }
    }
}
