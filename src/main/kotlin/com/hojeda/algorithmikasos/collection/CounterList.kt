package com.hojeda.algorithmikasos.collection

class CounterList<T> : MutableList<T> {

    companion object {
        fun <T> counterList(): CounterList<T> = CounterList()
    }

    private val delegate = mutableListOf<T>()
    private var blockCount = false
    override val size: Int
        get() = delegate.size

    var reads: Long = 0

    override fun contains(element: T): Boolean {
        return delegate.contains(element)
    }

    override fun containsAll(elements: Collection<T>): Boolean {
        return delegate.containsAll(elements)
    }

    override fun get(index: Int): T {
        reads = reads.inc()
        return delegate[index]
    }

    fun get(index: Int, count: Boolean): T {
        if (count && blockCount.not()) reads = reads.inc()
        return delegate[index]
    }

    override fun indexOf(element: T): Int {
        return delegate.indexOf(element)
    }

    override fun isEmpty(): Boolean {
        return delegate.isEmpty()
    }

    override fun iterator(): MutableIterator<T> =
        delegate.iterator().let {
            object : MutableIterator<T> {

                override fun hasNext(): Boolean = it.hasNext()

                override fun next(): T = it.next().also { if (blockCount.not())reads = reads.inc() }

                override fun remove() = it.remove()
            }
        }

    override fun lastIndexOf(element: T): Int {
        return delegate.lastIndexOf(element)
    }

    override fun listIterator(): MutableListIterator<T> {
        return delegate.listIterator()
    }

    override fun listIterator(index: Int): MutableListIterator<T> {
        return delegate.listIterator(index)
    }

    override fun subList(fromIndex: Int, toIndex: Int): MutableList<T> {
        return delegate.subList(fromIndex, toIndex)
    }

    override fun add(element: T): Boolean {
        return delegate.add(element)
    }

    override fun add(index: Int, element: T) {
        return delegate.add(index, element)
    }

    override fun addAll(index: Int, elements: Collection<T>): Boolean {
        return delegate.addAll(index, elements)
    }

    override fun addAll(elements: Collection<T>): Boolean {
        return delegate.addAll(elements)
    }

    override fun clear() {
        delegate.clear()
    }

    override fun remove(element: T): Boolean {
        return delegate.remove(element)
    }

    override fun removeAll(elements: Collection<T>): Boolean {
        return delegate.removeAll(elements)
    }

    override fun removeAt(index: Int): T {
        return delegate.removeAt(index)
    }

    override fun retainAll(elements: Collection<T>): Boolean {
        return delegate.retainAll(elements)
    }

    override fun set(index: Int, element: T): T {
        return delegate.set(index, element)
    }

    override fun toString(): String {
        return "[${this.joinToString()}]"
    }

    fun blockCount(): CounterList<T> {
        blockCount = true
        return this
    }

    fun enableCount(): CounterList<T> {
        blockCount = false
        return this
    }
}
