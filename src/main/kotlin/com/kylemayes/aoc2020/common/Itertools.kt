package com.kylemayes.aoc2020.common

import java.util.Collections

// ===============================================
// Infinite
// ===============================================

/** Returns an infinite sequence cycling through this sequence. */
fun <T> Sequence<T>.cycle(): Sequence<T> = generateSequence { this }.flatten()

/** Returns an infinite sequence cycling through this iterable. */
fun <T> Iterable<T>.cycle(): Sequence<T> = asSequence().cycle()

/** Returns an infinite sequence of this value. */
fun <T> T.repeat(): Sequence<T> = generateSequence { this }

// ===============================================
// Terminating
// ===============================================

/** Returns the groups containing consecutive values in this iterable with the same key. */
fun <T, U : Comparable<U>> Iterable<T>.groups(mapper: (value: T) -> U): List<Pair<U, List<T>>> {
    val iterator = iterator()
    if (!iterator.hasNext()) {
        return emptyList()
    }

    val groups = mutableListOf<Pair<U, List<T>>>()

    val first = iterator.next()
    var key = mapper(first)
    var group = mutableListOf(first)

    for (value in iterator) {
        val vkey = mapper(value)
        if (vkey != key) {
            groups.add(Pair(key, group))
            group = mutableListOf(value)
            key = vkey
        } else {
            group.add(value)
        }
    }

    if (group.isNotEmpty()) {
        groups.add(Pair(key, group))
    }

    return groups
}

// ===============================================
// Combinatoric
// ===============================================

/** Returns the combinations of the values in this iterable. */
fun <T> Iterable<T>.combinations(choose: Int): Sequence<List<T>> {
    val list = toMutableList()
    if (list.isEmpty() || choose > list.size) {
        return emptySequence()
    }

    val indices = list.indices.take(choose).toMutableList()
    return sequence {
        yield(indices.take(choose).map { list[it] })
        while (true) {
            val i = (0 until choose)
                .reversed()
                .find { indices[it] != it + list.size - choose } ?: break

            indices[i] += 1
            for (j in i + 1 until choose) {
                indices[j] = indices[j - 1] + 1
            }

            yield(indices.map { list[it] })
        }
    }
}

/** Returns the permutations of the values in this iterable. */
@Suppress("NAME_SHADOWING")
fun <T> Iterable<T>.permutations(choose: Int? = null): Sequence<List<T>> {
    val list = toMutableList()
    val choose = choose ?: list.size
    if (list.isEmpty() || choose > list.size) {
        return emptySequence()
    }

    val indices = list.indices.toMutableList()
    val cycles = (list.size downTo (list.size - choose) + 1).toMutableList()
    return sequence {
        yield(indices.take(choose).map { list[it] })
        loop@while (list.size != 0) {
            for (i in (0 until choose).reversed()) {
                cycles[i] -= 1
                if (cycles[i] == 0) {
                    val index = indices[i]
                    (i + 1 until indices.size).map { indices[it - 1] = indices[it] }
                    indices[indices.size - 1] = index
                    cycles[i] = list.size - i
                } else {
                    val j = cycles[i]
                    Collections.swap(indices, i, list.size - j)
                    yield(indices.take(choose).map { list[it] })
                    continue@loop
                }
            }

            break
        }
    }
}

/** Returns the Cartesian product of the iterables in this iterable. */
fun <T> Iterable<Iterable<T>>.product(): Sequence<List<T>> {
    val lists = map { it.toList() }
    if (lists.isEmpty()) {
        return emptySequence()
    }

    val indices = MutableList(lists.size) { 0 }
    return generateSequence(lists.map { it[0] }) {
        indices[indices.size - 1] += 1

        for (i in indices.size - 1 downTo 0) {
            if (indices[i] >= lists[i].size) {
                if (i != 0) {
                    indices[i] = 0
                    indices[i - 1] += 1
                } else {
                    return@generateSequence null
                }
            }
        }

        lists.zip(indices).map { (l, i) -> l[i] }
    }
}

/** Returns the Cartesian product of this iterable with itself. */
fun <T> Iterable<T>.product(repeat: Int): Sequence<List<T>> = this
    .repeat()
    .take(repeat)
    .toList()
    .product()
