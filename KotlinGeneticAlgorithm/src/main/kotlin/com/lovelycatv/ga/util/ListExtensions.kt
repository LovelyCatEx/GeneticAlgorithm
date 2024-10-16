package com.lovelycatv.ga.util

/**
 * @author lovelycat
 * @since 2024-10-15 19:32
 * @version 1.0
 */
class ListExtensions {
}

fun <T> List<T>.statistic(): Map<T, Int> {
    val result = mutableMapOf<T, Int>()
    this.forEach {
        if (result.containsKey(it)) {
            result[it] = result[it]!! + 1
        } else {
            result[it] = 1
        }
    }
    return result
}