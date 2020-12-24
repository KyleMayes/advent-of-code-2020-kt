package com.kylemayes.aoc2020.common

/** A circular linked list. */
class Ring<E> : Collection<E> {
    private class Node<E>(
        val element: E,
        var left: Node<E>? = null,
        var right: Node<E>? = null,
    )

    private var start: Node<E>? = null
    private val nodes: MutableMap<E, Node<E>> = mutableMapOf()

    constructor()

    constructor(other: Ring<E>) {
        start = other.start
        nodes.putAll(other.nodes)
    }

    constructor(elements: Iterable<E>) {
        val list = elements.map { Node(it) }
        start = list.getOrNull(0)
        for ((index, node) in list.withIndex()) {
            node.left = list.getMod(index - 1)
            node.right = list.getMod(index + 1)
            nodes[node.element] = node
        }
    }

    // ===========================================
    // Query Operations
    // ===========================================

    override val size get() = nodes.size

    override fun isEmpty() = nodes.isEmpty()

    override fun contains(element: E) = nodes.containsKey(element)
    override fun containsAll(elements: Collection<E>) = elements.all { contains(it) }

    override fun iterator(): Iterator<E> = sequence().asIterable().iterator()

    @Suppress("NAME_SHADOWING")
    fun sequence(start: E? = null, right: Boolean = true) = if (this.start != null) {
        sequence {
            val start = nodes[start] ?: this@Ring.start!!
            yield(start.element)
            var next = start.right
            while (next !== start) {
                yield(next!!.element)
                next = if (right) { next.right } else { next.left }
            }
        }
    } else {
        emptySequence()
    }

    fun left(element: E): E? = nodes[element]?.left?.element
    fun right(element: E): E? = nodes[element]?.right?.element

    // ===========================================
    // Modification Operations
    // ===========================================

    @Suppress("NAME_SHADOWING")
    fun add(element: E, left: E? = null): Boolean {
        if (start == null) {
            start = Node(element)
            start!!.left = start
            start!!.right = start
            nodes[element] = start!!
            return true
        }

        if (nodes.containsKey(element)) {
            return false
        }

        val left = nodes[left] ?: start!!.left!!
        val right = left.right!!

        val node = Node(element, left, right)
        left.right = node
        right.left = node
        nodes[element] = node

        return true
    }

    fun remove(element: E): Boolean {
        val node = nodes.remove(element) ?: return false

        if (node.left !== node) {
            node.left!!.right = node.right
            node.right!!.left = node.left
            if (node === start) {
                start = node.right
            }
        } else {
            start = null
        }

        return true
    }

    fun clear() {
        start = null
        nodes.clear()
    }

    // ===========================================
    // Other
    // ===========================================

    override fun toString() = "(${sequence().joinToString()})"
}
