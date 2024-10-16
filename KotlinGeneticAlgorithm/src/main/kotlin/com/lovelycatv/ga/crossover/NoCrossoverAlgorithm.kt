package com.lovelycatv.ga.crossover

import com.lovelycatv.ga.base.Chromosome

/**
 * @author lovelycat
 * @since 2024-10-14 23:23
 * @version 1.0
 */
class NoCrossoverAlgorithm : CrossoverAlgorithm() {
    override fun crossover(a: Chromosome, b: Chromosome, geneSize: Int): Pair<Chromosome, Chromosome> {
        return a to b
    }
}