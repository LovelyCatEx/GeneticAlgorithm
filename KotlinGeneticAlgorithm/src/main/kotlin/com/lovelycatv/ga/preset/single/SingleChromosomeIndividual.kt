package com.lovelycatv.ga.preset.single

import com.lovelycatv.ga.Individual
import com.lovelycatv.ga.crossover.CrossoverAlgorithm

/**
 * @author lovelycat
 * @since 2024-10-14 22:13
 * @version 1.0
 */
class SingleChromosomeIndividual : Individual() {
    override fun defineChromosomesCount(): Int {
        return 1
    }

    override fun deepCopy(): SingleChromosomeIndividual {
        val t = SingleChromosomeIndividual()
        this.getChromosomes().forEach {
            t.addChromosomes(it.deepCopy())
        }
        return t
    }
}