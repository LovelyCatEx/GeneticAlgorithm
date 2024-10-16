import com.lovelycatv.ga.IPopulation
import com.lovelycatv.ga.base.Chromosome
import com.lovelycatv.ga.base.GenePiece
import com.lovelycatv.ga.crossover.CrossoverAlgorithm
import com.lovelycatv.ga.crossover.SinglePointCrossover
import com.lovelycatv.ga.crossover.TwoPointCrossover
import com.lovelycatv.ga.mutation.MutationAlgorithm
import com.lovelycatv.ga.mutation.RandomMutationAlgorithm
import com.lovelycatv.ga.preset.single.SingleChromosomeIndividual
import com.lovelycatv.ga.preset.single.SingleChromosomePopulation
import org.junit.jupiter.api.Test
import kotlin.math.abs
import kotlin.random.Random

/**
 * @author lovelycat
 * @since 2024-10-14 22:17
 * @version 1.0
 */
class SingleChromosomePopulationTest {
    @Test
    fun test() {
        val populationIndividuals = mutableListOf<SingleChromosomeIndividual>()

        for (i in 1..10) {
            val individual = SingleChromosomeIndividual().apply {
                val chromosome = Chromosome().apply {
                    for (j in 1..9) {
                        this.addGenes(
                            GenePiece().apply {
                                this.addNucleonBases(Random(i * j).nextInt(256).toDouble())
                            }
                        )
                    }
                }
                this.addChromosomes(chromosome)
            }
            populationIndividuals.add(individual)
        }

        println("Initial population:")
        populationIndividuals.forEach {
            println(it.getChromosomes()[0].getGenes().map { it.getNucleonBases() })
        }

        val fxFitness = fun (rgbColor: List<Double>, target: List<Int>): Double {
            val r = rgbColor[0]
            val g = rgbColor[1]
            val b = rgbColor[2]

            val tR = target[0]
            val tG = target[1]
            val tB = target[2]

            return (((255 - abs(tR - r)) + (255 - abs(tG - g)) + (255 - abs(tB - b))) / (3 * 255))
        }

        val population = SingleChromosomePopulation(object : IPopulation<SingleChromosomeIndividual> {
            override fun calculateFitness(individual: SingleChromosomeIndividual): Double {
                val c1 = individual.getChromosomes()[0]
                val a = c1.getGenes().take(3).map { it.getNucleonBases()[0] }
                val b = c1.getGenes().drop(3).take(3).map { it.getNucleonBases()[0] }
                val c = c1.getGenes().drop(6).map { it.getNucleonBases()[0] }
                val p1 = fxFitness(a, listOf(255, 0, 0))
                val p2 = fxFitness(b, listOf(0, 255, 0))
                val p3 = fxFitness(c, listOf(0, 0, 255))
                return (p1 + p2 + p3) / 3
            }

            override fun buildNewIndividual(chromosomes: List<Chromosome>): SingleChromosomeIndividual {
                return SingleChromosomeIndividual().apply {
                    chromosomes.forEach {
                        this.addChromosomes(it.deepCopy())
                    }
                }
            }

            override fun determineCrossoverAlgorithm(chromosomes: Pair<Chromosome, Chromosome>): CrossoverAlgorithm {
                return listOf(
                    SinglePointCrossover(),
                    TwoPointCrossover()
                ).random()
            }

            override fun determineMutationAlgorithm(chromosome: Chromosome): MutationAlgorithm {
                return listOf(
                    RandomMutationAlgorithm {
                        Random((0..Int.MAX_VALUE).random()).nextInt(256).toDouble()
                    }
                ).random()
            }

            override fun getMutationProbability(chromosome: Chromosome): Double {
                return 0.01
            }

        })

        population.initPopulation(populationIndividuals)

        val generationsCount = 100
        for (i in 1..generationsCount) {
            println("========== $i ==========")
            population.nextGeneration()
        }
    }
}