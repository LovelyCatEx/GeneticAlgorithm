package com.lovelycatv.ga

import com.lovelycatv.ga.base.Chromosome
import com.lovelycatv.ga.base.GenePiece
import com.lovelycatv.ga.util.randomInt
import com.lovelycatv.ga.util.randomUntilNotEquals
import com.lovelycatv.ga.util.statistic
import kotlin.math.ceil
import kotlin.random.Random

/**
 * @author lovelycat
 * @since 2024-10-14 21:30
 * @version 1.0
 */
abstract class Population<I: Individual>(
    private val populationDelegate: IPopulation<I>
) : IPopulation<I> by populationDelegate {
    private val individuals: MutableList<I> = mutableListOf()

    fun initPopulation(initIndividuals: Iterable<I>) {
        if (initIndividuals.map { it.defineChromosomesCount() }.toSet().size != 1) {
            throw IllegalStateException("Chromosomes count of every individual must be the same")
        }
        this.individuals.clear()
        this.individuals.addAll(initIndividuals)
    }

    fun calculateFitness(): List<Pair<I, Double>> {
        return this.individuals.zip(this.individuals.map { calculateFitness(it) })
    }

    fun getElites(count: Int): List<I> {
        return calculateFitness().toMutableList()
            .sortedByDescending { it.second }
            .take(count)
            .map { it.first }
    }

    fun nextGeneration(elitesCount: Int = 1, newGenerationIndividualsCount: Int = this.individuals.size) {
        if (elitesCount > this.individuals.size) {
            throw IllegalStateException("Elites count should be less than the size of current individuals")
        }

        if (elitesCount >= newGenerationIndividualsCount) {
            throw IllegalStateException("Meaningless calculation when the elites count larger than the size of next generation")
        }

        // Selection
        val populationFitness = this.individuals.map { calculateFitness(it) }
        val populationSelectionProbability = populationFitness.map { ceil(it * 100).toInt() }
        val selection = mutableListOf<Int>()
        populationSelectionProbability.forEachIndexed { index, count ->
            for (i in 0..<count) {
                selection.add(index)
            }
        }

        // Elites
        val elites = getElites(elitesCount)

        val tNewGeneration = mutableListOf<I>()
        val parentCount = ceil(newGenerationIndividualsCount / 2.0).toInt()
        for (i in 0..<parentCount) {
            val fatherIndex = randomInt(selection.size)
            var motherIndex = randomInt(selection.size)
            while (individuals[selection[fatherIndex]] compare individuals[selection[motherIndex]]) {
                motherIndex = randomInt(selection.size)
            }
            val (father, mother) = listOf(
                this@Population.individuals[selection[fatherIndex]],
                this@Population.individuals[selection[motherIndex]]
            )

            // Crossover
            val chromosomesOfChildA = mutableListOf<Chromosome>()
            val chromosomesOfChildB = mutableListOf<Chromosome>()
            father.getChromosomes().forEachIndexed { index, chromosome ->
                val chromosomes = chromosome to mother.getChromosomes()[index]
                val crossoverAlgorithm = determineCrossoverAlgorithm(chromosomes)
                val children = crossoverAlgorithm.crossover(chromosomes)
                chromosomesOfChildA.add(children.first)
                chromosomesOfChildB.add(children.second)
            }

            tNewGeneration.add(populationDelegate.buildNewIndividual(chromosomesOfChildA))
            tNewGeneration.add(populationDelegate.buildNewIndividual(chromosomesOfChildB))
        }

        val newGeneration = tNewGeneration.sortedBy { populationDelegate.calculateFitness(it) }.toMutableList().run {
            // Mutation
            this.map {
                it.apply {
                    it.getChromosomes().forEachIndexed { index, chromosome ->
                        if (Random((1..Int.MAX_VALUE).random()).nextDouble() <= populationDelegate.getMutationProbability(chromosome)) {
                            val mutationAlgorithm = populationDelegate.determineMutationAlgorithm(chromosome)
                            val mutatedChromosome = mutationAlgorithm.mutate(chromosome)
                            this.setChromosome(index, mutatedChromosome)
                        }
                    }
                }
            }.toMutableList()
            this.toMutableList()
        }.run {
            this.drop(elitesCount).toMutableList()
        }.run {
            this.addAll(elites)
            this.toMutableList()
        }.run {
            // Force duplicate individuals to mutate
            for (i in 0..<this.size) {
                val individual = this[i]
                for (j in (i + 1)..<this.size) {
                    val tIndividual = this[j]
                    if (individual compare tIndividual) {
                        val newChromosomes = mutableListOf<Chromosome>()
                        tIndividual.getChromosomes().forEach { chromosome ->
                            newChromosomes.add(populationDelegate.determineMutationAlgorithm(chromosome).mutate(chromosome))
                        }
                        this[j] = buildNewIndividual(newChromosomes)
                    }
                }
            }
            this
        }.sortedByDescending { calculateFitness(it) }

        val nextGenerationMap = newGeneration.zip(newGeneration.map { populationDelegate.calculateFitness(it) })
        nextGenerationMap.forEachIndexed { index, (individual, fitness) ->
            println("${index}: $fitness : ${individual.getChromosomes().map { it.getGenes().map { it.getNucleonBases() }}}")
        }

        initPopulation(newGeneration)
    }
}