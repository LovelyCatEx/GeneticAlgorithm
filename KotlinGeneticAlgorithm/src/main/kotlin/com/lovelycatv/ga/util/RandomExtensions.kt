package com.lovelycatv.ga.util

import kotlin.random.Random

/**
 * @author lovelycat
 * @since 2024-10-15 19:04
 * @version 1.0
 */
class RandomExtensions {
}

fun randomInt(max: Int = -1): Int {
    return Random((0..Int.MAX_VALUE).random()).run {
        if (max == -1)
            nextInt()
        else
            nextInt(max)
    }
}

fun <T> randomUntilNotEquals(notEquals: T, fx: () -> T): T {
    val generated = fx()
    return if (generated == notEquals) {
        randomUntilNotEquals(notEquals, fx)
    } else {
        generated
    }
}