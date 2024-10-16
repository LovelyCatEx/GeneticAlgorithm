package com.lovelycatv.ga.crossover

import com.lovelycatv.ga.base.Chromosome

/**
 * @author lovelycat
 * @since 2024-10-14 21:47
 * @version 1.0
 */
abstract class CrossoverAlgorithm {
    fun crossover(parentChromosomes: Pair<Chromosome, Chromosome>): Pair<Chromosome, Chromosome> {
        val a = parentChromosomes.first
        val b = parentChromosomes.second

        if (a.getGenePieceSize() == 0 || b.getGenePieceSize() == 0) {
            throw IllegalStateException("Length of chromosome cannot be zero")
        }

        if (a.getGenePieceSize() != b.getGenePieceSize()) {
            throw IllegalStateException("Length of chromosome a (${a.getGenePieceSize()}) is not equal to length of chromosome b (${b.getGenePieceSize()})")
        }

        return crossover(a, b, a.getGenePieceSize())
    }

    abstract fun crossover(a: Chromosome, b: Chromosome, geneSize: Int): Pair<Chromosome, Chromosome>
}