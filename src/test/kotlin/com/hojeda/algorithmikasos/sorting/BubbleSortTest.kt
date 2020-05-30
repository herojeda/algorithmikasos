package com.hojeda.algorithmikasos.sorting

import com.hojeda.algorithmikasos.collection.CounterList
import org.hamcrest.Matchers.*
import org.hamcrest.MatcherAssert.*
import org.junit.jupiter.api.Test
import java.math.BigDecimal
import java.math.MathContext

class BubbleSortTest {

    @Test
    fun `When sorting a list the max reads should be less or equal to n^2`() {
        val target = BubbleSort
        val givenRange = 1..20
        val givenRangeSize = givenRange.toList().size

        val givenUnorderedList = CounterList.counterList<Long>().also { list ->
            givenRange.map {
                list.add(Math.random().times(100).toLong())
            }
        }.blockCount()

        println("Unordered list -> $givenUnorderedList")

        val sortedList = target.sort(
            givenUnorderedList.enableCount(),
            Comparator { o1, o2 -> o1.compareTo(o2) }
        ).blockCount()

        println("Sorted list -> $sortedList, with reads: ${sortedList.reads}")

        val maxReads = givenRangeSize.toBigDecimal().pow(2).longValueExact()

        assertThat(sortedList.reads, lessThanOrEqualTo(maxReads))
    }

    @Test
    fun `test`() {
        val target = BubbleSort
        val givenRange = 1..20
        val readsComparator = Comparator<CounterList<Long>> { o1, o2 -> o1.reads.compareTo(o2.reads) }
        val givenRangeSize = givenRange.toList().size

        val generateUnorderedList: () -> CounterList<Long> = {
            CounterList.counterList<Long>().also { list ->
                givenRange.map {
                    list.add(Math.random().times(100).toLong())
                }
            }.blockCount()
        }

        val promRange = 1..10000

        val sortedLists = promRange.map {
            target.sort(
                generateUnorderedList().enableCount(), Comparator { o1, o2 -> o1.compareTo(o2) }
            ).blockCount()
        }

        val totalReadsCount = sortedLists.fold(0L) { acc, counterList ->
            acc.plus(counterList.reads)
        }

        val promReads = BigDecimal.valueOf(totalReadsCount)
            .divide(BigDecimal.valueOf(sortedLists.size.toLong()), MathContext.DECIMAL32)

        val maxReads = sortedLists.maxWith(readsComparator)?.reads ?: 0
        val minReads = sortedLists.minWith(readsComparator)?.reads ?: 0
        val maxLimitReads = givenRangeSize.toBigDecimal().pow(2).longValueExact()

        println("El máximo de lecturas posible es: $maxLimitReads")
        println("El promedio de las lecturas es: $promReads")
        println("La cantidad máxima de lecturas ha sido: $maxReads")
        println("La cantidad mínima de lecturas ha sido: $minReads")
    }
}