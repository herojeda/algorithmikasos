package com.hojeda.algorithmikasos.collection

import com.hojeda.algorithmikasos.collection.CounterList.Companion.counterList
import org.hamcrest.MatcherAssert.*
import org.hamcrest.Matchers.*
import org.junit.jupiter.api.Test


class CounterListTest {

    @Test
    fun `When execute forEach should count iterations`() {
        val expectedReadCount = 5L
        val target = counterList<Long>().also { list ->
            (1..expectedReadCount).forEach { list.add(it) }
        }

        target.forEach(::println)

        assertThat(target.reads, equalTo(expectedReadCount))
    }

    @Test
    fun `When execute iterate over indexes should count iterations`() {
        val givenRange = 1..5L
        val target = counterList<Long>().also { list ->
            givenRange.forEach { list.add(it) }
        }

        givenRange.forEach {
            target[it.toInt().dec()].let(::println)
        }

        assertThat(target.reads, equalTo(givenRange.last()))
    }
}