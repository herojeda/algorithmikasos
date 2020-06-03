package com.hojeda.algorithmikasos.sorting

import com.hojeda.algorithmikasos.collection.CounterList
import org.hamcrest.MatcherAssert
import org.hamcrest.Matchers
import org.junit.jupiter.api.Test
import java.math.BigDecimal

class SelectionSortTest {

    companion object {
        private val target = SelecttionSort

        private val longComparator = Comparator<Long> { o1, o2 -> o1.compareTo(o2) }

        private val generateUnorderedList: (Int, Short) -> CounterList<Long> = { digits, size ->
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
        println("Reads -> ${sortedList.reads}")

        sortedList.forEachIndexed { index, value ->
            if (index < sortedList.lastIndex) MatcherAssert.assertThat(
                value,
                Matchers.lessThanOrEqualTo(sortedList[index.inc()])
            )
        }
    }
}