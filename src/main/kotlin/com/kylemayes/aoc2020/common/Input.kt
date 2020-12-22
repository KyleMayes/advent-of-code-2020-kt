package com.kylemayes.aoc2020.common

import java.nio.charset.StandardCharsets

/** A solution input. */
interface Input {
    /** Returns this input. */
    fun read(): String
}

/** A solution input loaded from a string. */
data class StringInput(private val string: String) : Input {
    override fun read() = string
}

/** A solution input loaded from a resource. */
data class ResourceInput(private val resource: String) : Input {
    override fun read() = javaClass
        .classLoader
        .getResource(resource)!!
        .readText(StandardCharsets.UTF_8)
        .replace(Regex("\\r\\n?"), "\n")
}

/** Reads this input as a list of groups of consecutive non-empty lines. */
fun Input.readGroups(): List<List<String>> = readLines(true)
    .groups { it.isEmpty() }
    .filter { (empty, _) -> !empty }
    .map { (_, chunk) -> chunk }

/** Reads this input as a list of (by default non-empty) lines. */
fun Input.readLines(empty: Boolean = false): List<String> = read()
    .split("\n")
    .map { it.trim() }
    .filter { empty || it.isNotEmpty() }
