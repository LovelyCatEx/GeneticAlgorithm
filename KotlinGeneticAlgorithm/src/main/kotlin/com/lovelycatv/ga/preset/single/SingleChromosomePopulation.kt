package com.lovelycatv.ga.preset.single

import com.lovelycatv.ga.IPopulation
import com.lovelycatv.ga.Population

/**
 * @author lovelycat
 * @since 2024-10-14 22:14
 * @version 1.0
 */
class SingleChromosomePopulation(populationDelegate: IPopulation<SingleChromosomeIndividual>)
    : Population<SingleChromosomeIndividual>(populationDelegate)