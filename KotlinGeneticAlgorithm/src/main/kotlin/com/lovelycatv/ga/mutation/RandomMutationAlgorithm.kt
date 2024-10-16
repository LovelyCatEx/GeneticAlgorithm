package com.lovelycatv.ga.mutation

import com.lovelycatv.ga.base.Chromosome

/**
 * @author lovelycat
 * @since 2024-10-14 23:27
 * @version 1.0
 */
class RandomMutationAlgorithm(val generateMutateValue: () -> Double) : MutationAlgorithm {
    override fun mutate(chromosome: Chromosome): Chromosome {
        val tChromosome = chromosome.deepCopy()

        val mutateGeneIndex = (0..<tChromosome.getGenePieceSize()).random()
        val mutateGene = tChromosome.getGenes()[mutateGeneIndex]

        val mutateIndex = (0..<mutateGene.getNucleonBases().size).random()
        mutateGene.setNucleonBase(mutateIndex, generateMutateValue())

        tChromosome.setGene(mutateGeneIndex, mutateGene)

        return tChromosome
    }
}