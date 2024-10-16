package com.lovelycatv.ga.base

import com.lovelycatv.ga.interfaces.DeepCopyable

/**
 * @author lovelycat
 * @since 2024-10-14 21:24
 * @version 1.0
 */
class GenePiece : DeepCopyable<GenePiece> {
    private val nucleonBases: MutableList<Double> = mutableListOf()

    fun addNucleonBases(vararg nucleonBases: Double) {
        this.nucleonBases.addAll(nucleonBases.toList())
    }

    fun setNucleonBase(index: Int, newValue: Double) {
        this.nucleonBases[index] = newValue
    }

    fun getNucleonBases(): List<Double> = this.nucleonBases

    infix fun compare(another: GenePiece): Boolean {
        if (this.nucleonBases.size != another.nucleonBases.size) {
            return false
        }
        var v = true
        for (i in 0..<this.nucleonBases.size) {
            val a = this.nucleonBases[i]
            val b = another.getNucleonBases()[i]
            if (a != b) {
                v = false
                break
            }
        }
        return v
    }

    override fun deepCopy(): GenePiece {
        val t = GenePiece()
        this.nucleonBases.forEach {
            t.addNucleonBases(it)
        }
        return t
    }
}