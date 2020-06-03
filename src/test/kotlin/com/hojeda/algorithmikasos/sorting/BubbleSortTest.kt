package com.hojeda.algorithmikasos.sorting

import com.hojeda.algorithmikasos.collection.CounterList
import org.hamcrest.Matchers.*
import org.hamcrest.MatcherAssert.*
import org.junit.jupiter.api.Test
import java.math.BigDecimal
import java.math.MathContext

class BubbleSortTest {

    companion object {
        val target = BubbleSort

        val longComparator = Comparator<Long> { o1, o2 -> o1.compareTo(o2) }

        val generateUnorderedList: (Int, Short) -> CounterList<Long> = { digits, size ->
            CounterList.counterList<Long>().also { list ->
                (1..size).map {
                    list.add(Math.random().times(BigDecimal.TEN.pow(digits).toDouble()).toLong())
                }
            }.blockCount()
        }
    }

    @Test
    fun `When sorting a list with normal sort the result should be a sorted list`() {
        val givenListSize = 20
        val givenDigits = 3

        val givenUnorderedList = generateUnorderedList(givenDigits, givenListSize.toShort()).enableCount()

        val sortedList = target.sort(
            givenUnorderedList.enableCount(), longComparator
        ).blockCount()

        println("Unordered list -> $givenUnorderedList")
        println("Sorted list -> $sortedList")

        sortedList.forEachIndexed { index, value ->
            if (index < sortedList.lastIndex) assertThat(value, lessThanOrEqualTo(sortedList[index.inc()]))
        }
    }

    @Test
    fun `When sorting a list with normal sort the max reads should be equal to n(pow)2`() {
        val n = BigDecimal.valueOf(20)
        val givenDigits = 3

        val sortedList = target.sort(
            generateUnorderedList(givenDigits, n.toShort()).enableCount(),
            longComparator
        )

        val expectedReads = n.pow(2)

        println("Real reads: ${sortedList.reads}")
        println("Expected reads: $expectedReads")

        assertThat(sortedList.reads, equalTo(expectedReads.toLong()))
    }

    @Test
    fun `When sorting a set of lists with normal sort all reads should be equal to n(pow)2`() {
        val n = BigDecimal.valueOf(30)
        val givenDigits = 3

        val promRange = 1..10000

        val sortedLists = promRange.map {
            target.sort(generateUnorderedList(givenDigits, n.toShort()).enableCount(), longComparator)
        }.toSet()

        val expectedReads = n.pow(2)

        println("Expected reads: $expectedReads")

        sortedLists.forEach { sortedList ->
            assertThat(sortedList.reads, equalTo(expectedReads.toLong()))
        }
    }

    @Test
    fun `When sorting a set of lists with optimized sort the min reads should be less than or equal to (n(pow)2)(percentage)50`() {
        val n = BigDecimal.valueOf(30)
        val givenDigits = 6
        val setRange = 1..10000

        val sortedLists = setRange.map {
            target.optimizedSort(
                generateUnorderedList(givenDigits, n.toShort()).enableCount(),
                Comparator { o1, o2 -> o1.compareTo(o2) }
            ).blockCount()
        }.toSet()

        val minReads = sortedLists.map { it.reads }.minWith(longComparator) ?: 0
        val expectedMinReadsLessThan = n.pow(2).times(BigDecimal.valueOf(0.5))

        println("Real min reads: $minReads")
        println("Expected min reads less than: $expectedMinReadsLessThan")

        assertThat(minReads.toBigDecimal(), lessThan(expectedMinReadsLessThan))
    }

    @Test
    fun `When sorting a set of lists with optimized sort the prom reads should be less than or equal to (n(pow)2)(percentage)86`() {
        val n = BigDecimal.valueOf(30)
        val givenDigits = 6
        val promRange = 1..10000

        val sortedLists = promRange.map {
            target.optimizedSort(
                generateUnorderedList(givenDigits, n.toShort()).enableCount(),
                longComparator
            )
        }.toSet()

        val totalReadsCount = sortedLists.fold(0L) { acc, counterList ->
            acc.plus(counterList.reads)
        }

        val promReads = BigDecimal.valueOf(totalReadsCount)
            .divide(BigDecimal.valueOf(sortedLists.size.toLong()), MathContext.DECIMAL32)

        val expectedPromReadsLessThan = n.pow(2).times(BigDecimal.valueOf(0.86))

        println("Real prom reads: $promReads")
        println("Expected prom less than: $expectedPromReadsLessThan")

        sortedLists.forEach { sortedList ->
            sortedList.forEachIndexed { index, value ->
                if (index < sortedList.lastIndex) assertThat(value, lessThanOrEqualTo(sortedList[index.inc()]))
            }
        }

        assertThat(promReads, lessThan(expectedPromReadsLessThan))
    }

    // Calculate percentiles
}