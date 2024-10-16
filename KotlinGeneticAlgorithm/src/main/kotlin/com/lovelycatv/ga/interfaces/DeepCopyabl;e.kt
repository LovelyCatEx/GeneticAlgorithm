package com.lovelycatv.ga.interfaces

/**
 * @author lovelycat
 * @since 2024-10-16 21:32
 * @version 1.0
 */
interface DeepCopyable<T> {
    fun deepCopy(): T
}