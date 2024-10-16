package com.lovelycatv.ga.base

import com.lovelycatv.ga.interfaces.DeepCopyable

/**
 * @author lovelycat
 * @since 2024-10-14 21:23
 * @version 1.0
 */
class Chromosome : DeepCopyable<Chromosome> {
    private val genePieces: MutableList<GenePiece> = mutableListOf()

    fun addGenes(genePieces: Iterable<GenePiece>) {
        addGenes(*genePieces.toList().toTypedArray())
    }

    fun addGenes(vararg genePiece: GenePiece) {
        this.genePieces.addAll(genePiece.toList())
    }

    fun setGene(index: Int, gene: GenePiece) {
        this.genePieces[index] = gene
    }

    fun getGenePieceSize(): Int = this.genePieces.size
    fun getGenes(): List<GenePiece> = this.genePieces.toList()

    infix fun compare(another: Chromosome): Boolean {
        if (this.genePieces.size != another.genePieces.size) {
            return false
        }
        var v = true
        for (i in 0..<this.genePieces.size) {
            val a = this.genePieces[i]
            val b = another.getGenes()[i]
            if (!(a compare b)) {
                v = false
                break
            }
        }
        return v
    }

    override fun deepCopy(): Chromosome {
        val t = Chromosome()
        this.genePieces.forEach {
            t.addGenes(it.deepCopy())
        }
        return t
    }
}