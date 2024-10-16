package com.lovelycatv.ga.mutation

import com.lovelycatv.ga.base.Chromosome

/**
 * @author lovelycat
 * @since 2024-10-14 23:14
 * @version 1.0
 */
interface MutationAlgorithm {
    fun mutate(chromosome: Chromosome): Chromosome
}