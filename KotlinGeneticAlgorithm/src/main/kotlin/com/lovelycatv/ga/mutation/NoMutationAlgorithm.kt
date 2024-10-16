package com.lovelycatv.ga.mutation

import com.lovelycatv.ga.base.Chromosome

/**
 * @author lovelycat
 * @since 2024-10-14 23:24
 * @version 1.0
 */
class NoMutationAlgorithm : MutationAlgorithm {
    override fun mutate(chromosome: Chromosome): Chromosome {
        return chromosome
    }
}