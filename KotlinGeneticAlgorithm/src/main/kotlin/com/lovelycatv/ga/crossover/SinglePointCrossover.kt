package com.lovelycatv.ga.crossover

import com.lovelycatv.ga.base.Chromosome

/**
 * @author lovelycat
 * @since 2024-10-14 21:49
 * @version 1.0
 */
class SinglePointCrossover : CrossoverAlgorithm() {
    override fun crossover(a: Chromosome, b: Chromosome, geneSize: Int): Pair<Chromosome, Chromosome> {
        val selectedIndex = (1..<geneSize).random()

        val offspringAGenes = a.getGenes().take(selectedIndex) + b.getGenes().drop(selectedIndex)
        val offspringBGenes = b.getGenes().take(selectedIndex) + a.getGenes().drop(selectedIndex)

        val offspringA = Chromosome().apply {
            addGenes(offspringAGenes)
        }
        val offspringB = Chromosome().apply {
            addGenes(offspringBGenes)
        }

        return Pair(offspringA, offspringB)
    }
}