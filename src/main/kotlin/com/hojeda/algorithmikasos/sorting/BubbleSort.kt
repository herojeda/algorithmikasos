package com.hojeda.algorithmikasos.sorting

import com.hojeda.algorithmikasos.collection.CounterList

object BubbleSort {

    fun <T> sort(list: CounterList<T>, comparator: Comparator<T>): CounterList<T> =
        list.copy().also { copiedList ->
            var aux: T

            copiedList.forEachIndexed { outerIndex, _ ->
                if (copiedList.lastIndex > outerIndex) {
                    copiedList.forEachIndexed { innerIndex, _ ->
                        if (copiedList.lastIndex > innerIndex) {
                            val e1 = copiedList.get(innerIndex, false)
                            val e2 = copiedList.get(innerIndex.inc(), false)
                            comparator.compare(e1, e2).let {
                                if (it == 1) {
                                    aux = e1
                                    copiedList[innerIndex] = e2
                                    copiedList[innerIndex.inc()] = aux
                                }
                            }
                        }
                    }
                }
            }
        }

    fun <T> optimizedSort(list: CounterList<T>, comparator: Comparator<T>): CounterList<T> =
        list.copy().also { copiedList ->
            var aux: T

            copiedList.forEachIndexed { outerIndex, _ ->
                var isSorted = true
                if (copiedList.lastIndex > outerIndex) {
                    copiedList.forEachIndexed { InnerIndex, _ ->
                        if (copiedList.lastIndex > InnerIndex) {
                            val e1 = copiedList.get(InnerIndex, false)
                            val e2 = copiedList.get(InnerIndex.inc(), false)
                            comparator.compare(e1, e2).let {
                                if (it == 1) {
                                    aux = e1
                                    copiedList[InnerIndex] = e2
                                    copiedList[InnerIndex.inc()] = aux
                                    isSorted = false
                                }
                            }
                        } else if (isSorted) {
                            return copiedList
                        }
                    }
                }
            }
        }

}