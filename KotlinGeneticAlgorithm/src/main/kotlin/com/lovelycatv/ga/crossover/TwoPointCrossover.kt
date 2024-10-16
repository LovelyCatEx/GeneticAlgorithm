package com.lovelycatv.ga.crossover

import com.lovelycatv.ga.base.Chromosome

/**
 * @author lovelycat
 * @since 2024-10-14 22:02
 * @version 1.0
 */
class TwoPointCrossover : CrossoverAlgorithm() {
    override fun crossover(a: Chromosome, b: Chromosome, geneSize: Int): Pair<Chromosome, Chromosome> {
        // Ensure there are at least 2 genes for crossover
        if (geneSize < 2) {
            throw IllegalArgumentException("Gene size must be at least 2 for two-point crossover.")
        }

        // Randomly select two crossover points, ensuring selectedIndex1 < selectedIndex2
        val selectedIndex1 = (0..<geneSize - 1).random()  // last valid index for selectedIndex1 is geneSize - 2
        val selectedIndex2 = (selectedIndex1 + 1..<geneSize).random()  // selectedIndex2 must be greater than selectedIndex1

        // Offspring A's genes:
        // [0..selectedIndex1) from A, [selectedIndex1..selectedIndex2) from B, [selectedIndex2..geneSize) from A
        val offspringAGenes = a.getGenes().take(selectedIndex1) +
                b.getGenes().subList(selectedIndex1, selectedIndex2) +
                a.getGenes().drop(selectedIndex2)

        // Offspring B's genes:
        // [0..selectedIndex1) from B, [selectedIndex1..selectedIndex2) from A, [selectedIndex2..geneSize) from B
        val offspringBGenes = b.getGenes().take(selectedIndex1) +
                a.getGenes().subList(selectedIndex1, selectedIndex2) +
                b.getGenes().drop(selectedIndex2)

        // Create new chromosomes for the offspring
        val offspringA = Chromosome().apply {
            addGenes(offspringAGenes)
        }
        val offspringB = Chromosome().apply {
            addGenes(offspringBGenes)
        }

        // Return the offspring pair
        return Pair(offspringA, offspringB)
    }

}