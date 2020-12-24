package com.kylemayes.aoc2020.common

/** Indexes into this list with a index modulo the size of this list. */
fun <T> List<T>.getMod(index: Int): T =
    this[Math.floorMod(index, size)]
