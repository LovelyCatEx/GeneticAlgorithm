package com.lovelycatv.ga

import com.lovelycatv.ga.base.Chromosome
import com.lovelycatv.ga.crossover.CrossoverAlgorithm
import com.lovelycatv.ga.mutation.MutationAlgorithm

/**
 * @author lovelycat
 * @since 2024-10-14 22:14
 * @version 1.0
 */
interface IPopulation<I: Individual> {
    fun calculateFitness(individual: I): Double

    fun buildNewIndividual(chromosomes: List<Chromosome>): I

    fun determineCrossoverAlgorithm(chromosomes: Pair<Chromosome, Chromosome>): CrossoverAlgorithm

    fun determineMutationAlgorithm(chromosome: Chromosome): MutationAlgorithm

    fun getMutationProbability(chromosome: Chromosome): Double

}