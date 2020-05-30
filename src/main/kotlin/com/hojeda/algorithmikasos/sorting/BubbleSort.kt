package com.hojeda.algorithmikasos.sorting

import com.hojeda.algorithmikasos.collection.CounterList

object BubbleSort {

    fun <T> sort(list: CounterList<T>, comparator: Comparator<T>): CounterList<T> {

        val listRange = 0 until list.size
        var aux: T

        list.forEach { i ->
            var isSorted = true
            list.forEachIndexed { index, t ->
                if (list.lastIndex > index) {
                    var e1 = list.get(index, false)
                    var e2 = list.get(index.inc(), false)
                    comparator.compare(e1, e2).let {
                        if (it == 1) {
                            aux = e1
                            list[index] = e2
                            list[index.inc()] = aux
                            isSorted = false
                        }
                    }
                } else if (isSorted) {
                    return list
                }
            }
        }

        return list
    }

}