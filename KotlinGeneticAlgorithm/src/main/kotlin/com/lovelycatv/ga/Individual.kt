package com.lovelycatv.ga

import com.lovelycatv.ga.base.Chromosome
import com.lovelycatv.ga.interfaces.DeepCopyable

/**
 * @author lovelycat
 * @since 2024-10-14 21:22
 * @version 1.0
 */
abstract class Individual : DeepCopyable<Individual> {
    private val chromosomes: MutableList<Chromosome> = mutableListOf()

    fun addChromosomes(chromosomes: Iterable<Chromosome>) {
        this.chromosomes.addAll(chromosomes.toList())
    }

    fun addChromosomes(vararg chromosomes: Chromosome) {
        this.chromosomes.addAll(chromosomes.toList())
    }

    fun getChromosomes(): List<Chromosome> = this.chromosomes.toList()

    fun setChromosome(index: Int, chromosome: Chromosome) {
        this.chromosomes[index] = chromosome
    }

    abstract fun defineChromosomesCount(): Int

    infix fun compare(another: Individual): Boolean {
        if (this.chromosomes.size != another.getChromosomes().size) {
            return false
        }
        var v = true
        for (i in 0..<this.chromosomes.size) {
            val a = this.chromosomes[i]
            val b = another.getChromosomes()[i]
            if (!(a compare b)) {
                v = false
                break
            }
        }
        return v
    }
}