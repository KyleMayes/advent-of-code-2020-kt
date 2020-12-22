package com.kylemayes.aoc2020.day21

import com.kylemayes.aoc2020.common.Input
import com.kylemayes.aoc2020.common.ResourceInput
import com.kylemayes.aoc2020.common.Solution
import com.kylemayes.aoc2020.common.readLines
import com.kylemayes.aoc2020.common.solve

data class Food(
    val ingredients: List<String>,
    val allergens: List<String>,
)

typealias ParsedInput = List<Food>

class Day21 : Solution<ParsedInput> {
    private val allergenic = mutableMapOf<String, String>()

    override fun parse(input: Input) = input
        .readLines()
        .map {
            val (left, right) = it.split(" (contains ")
            val ingredients = left.split(" ")
            val allergens = right.replace(")", "").split(", ")
            Food(ingredients, allergens)
        }

    override fun solvePart1(input: ParsedInput): Int {
        val candidates = mutableMapOf<String, MutableSet<String>>()
        for ((ingredients, allergens) in input) {
            for (allergen in allergens) {
                candidates.merge(allergen, ingredients.toMutableSet()) { a, b ->
                    a.intersect(b).toMutableSet()
                }
            }
        }

        val sketchy = candidates.entries.flatMap { it.value }.toSet()
        val safe = input.flatMap { it.ingredients }.filter { !sketchy.contains(it) }

        while (allergenic.size < candidates.size) {
            for ((allergen, ingredients) in candidates) {
                if (ingredients.size == 1) {
                    val ingredient = ingredients.first()
                    allergenic[allergen] = ingredient
                    for ((_, ins) in candidates) {
                        ins.remove(ingredient)
                    }
                }
            }
        }

        return safe.size
    }

    override fun solvePart2(input: ParsedInput) = allergenic
        .entries
        .sortedBy { it.key }
        .joinToString(",") { it.value }
}

fun main() = solve({ Day21() }, ResourceInput("day21.txt"))
