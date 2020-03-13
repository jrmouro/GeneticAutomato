/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jrmouro.automato.genetic;

import com.jrmouro.automato.Automato;
import com.jrmouro.automato.IParseble;
import com.jrmouro.genetic.chromosome.ValidityGenotype;
import com.jrmouro.genetic.integer.IntegerChromosome;
import com.jrmouro.genetic.integer.IntegerCrossover;
import com.jrmouro.genetic.integer.IntegerGeneticAlgorithm;
import com.jrmouro.genetic.integer.IntegerPopulation;
import com.jrmouro.genetic.integer.IntegerStoppingCondition;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ronaldo
 */
public class AutomatoGenetic<E, V> extends Automato<E, V> implements IAutomatoPhenotype<E, V> {

    public int populationSize = 20;
    public int populationReuse = 5;
    public int generationsNumber = 4000;
    public double crossoverRate = .3;
    public double mutationRate = .5;
    public double mutationArityRateGene = .3;
    public int selectionArity = 3;

    public double terminalStateRate = .1;
    public double nullStateProgrammRate = .5;

    private IntegerChromosome fitnessChromosome = null;
    private IntegerPopulation pop = null;
    
    public AutomatoGenetic(V[] vocabulario) {
        super(null, vocabulario, null, null);
    }

    public AutomatoGenetic(E[] estados, V[] vocabulario) {
        super(estados, vocabulario, null, null);
    }
    
    public void run(IParseble<V[]> parse, String[] frasesNeg, String[] frasesPos) {

        run(new AutomatoFitness(this, parse, frasesNeg, frasesPos));

    }

    public void run(AutomatoFitness fitness) {

        if (this.isInitialized()) {

            IntegerGeneticAlgorithm ga = new IntegerGeneticAlgorithm(
                    populationSize,
                    populationReuse,
                    populationSize,
                    new ValidityGenotype<Integer>() {
                @Override
                public boolean isGenotypeValid(List<Integer> representation) {
                    return true;
                }
            },
                    fitness,
                    this.getMinGenotypeSize(), //chromosome size
                    0,
                    Integer.MAX_VALUE - 1,
                    new IntegerStoppingCondition(generationsNumber),
                    new IntegerCrossover(this.getMinGenotypeSize() - 1),
                    crossoverRate,
                    mutationRate,
                    mutationArityRateGene,
                    selectionArity);

            IntegerPopulation pop = ga.run();

            this.fitnessChromosome = (IntegerChromosome) pop.getFittestChromosome();

            this.setPhenotype(this.fitnessChromosome.getGenotype());

        }else{
            
            try {
                throw new Exception("Automato não inicializado!");
            } catch (Exception ex) {
                Logger.getLogger(AutomatoGenetic.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }

    }

    public int getMinGenotypeSize() {
        
        if (this.isInitialized()) {
            return  1 + // estado inicial
                    1 + // qtde estados terminais
                    this.estados.length + // estados terminais
                    (this.estados.length * this.vocabulario.length);// programa
        } else {
            try {
                throw new Exception("AutomatoGenetic não inicializado!");
            } catch (Exception ex) {
                Logger.getLogger(AutomatoGenetic.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        return 0;
    }

    @Override
    public void setPhenotype(List<Integer> genotype) {
        
        if(this.isInitialized()){
            
            if (genotype.size() >= this.getMinGenotypeSize()) {

                this.setEstadoInicial(this.estados[genotype.get(0) % this.estados.length]);

                genotype.remove(0);

                int n = (genotype.get(0) % this.estados.length) + 1;

                genotype.remove(0);

                this.mapET = new HashMap();

                int c = 0;

                for (int i = 0; i < this.estados.length; i++) {

                    int a = genotype.get(0) % (int) ((double) this.estados.length / terminalStateRate);

                    if (a == 0 && c < n) {
                        this.mapET.putIfAbsent(this.estados[i], c++);
                    }

                    genotype.remove(0);
                }

                this.funcaoPrograma = (E[][]) new Object[this.estados.length][this.vocabulario.length];

                for (int i = 0; i < this.estados.length; i++) {

                    for (int j = 0; j < this.vocabulario.length; j++) {

                        int a = (genotype.get(0) % (this.estados.length + (int) ((double) this.estados.length * nullStateProgrammRate)));

                        if (a >= this.estados.length) {
                            this.funcaoPrograma[i][j] = null;
                        } else {
                            this.funcaoPrograma[i][j] = this.estados[a];
                        }

                        genotype.remove(0);

                    }

                }

        } else {
            try {
                throw new Exception("Invalid genotype");
            } catch (Exception ex) {
                Logger.getLogger(AutomatoGenetic.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        }else{
            try {
                throw new Exception("AutomatoGenetic não inicializado!");
            } catch (Exception ex) {
                Logger.getLogger(AutomatoGenetic.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public IntegerChromosome getFitnessChromosome() {
        return fitnessChromosome;
    }

    public IntegerPopulation getPop() {
        return pop;
    }

    @Override
    public String toString() {
        return "AutomatoGenetic:\n" + "  fitnessChromosome= " + fitnessChromosome + "\n" + super.toString();
    }

    @Override
    public boolean isInitialized() {
        return  super.isInitialized() ||
                (   this.estados != null && this.estados.length > 0 &&
                    this.vocabulario != null && this.vocabulario.length > 0 );
    }
    
    

}
