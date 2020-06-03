package com.hojeda.algorithmikasos.sorting

import com.hojeda.algorithmikasos.collection.CounterList

object SelecttionSort {

    fun <T> sort(list: CounterList<T>, comparator: Comparator<T>) =
        list.copy().also { copiedList ->
            copiedList.forEachIndexed { outerIndex, outerValue ->
                var lessIndex = outerIndex
                (outerIndex..copiedList.lastIndex).forEach { innerIndex ->
                    if (comparator.compare(copiedList[innerIndex], copiedList.get(lessIndex, false)) == -1) {
                        lessIndex = innerIndex
                    }
                }
                var lessValue = copiedList.get(lessIndex, false)
                copiedList[lessIndex] = copiedList.get(outerIndex, false)
                copiedList[outerIndex] = lessValue
            }

        }
}