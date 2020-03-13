/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jrmouro.automato.genetic;

import com.jrmouro.automato.Automato;
import com.jrmouro.automato.IParseble;
import com.jrmouro.genetic.chromosome.ChromosomeAbstract;
import com.jrmouro.genetic.fitnessfunction.FitnessFunction;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ronaldo
 */
public class AutomatoFitness<E,V> implements FitnessFunction<Integer>{
    
    final AutomatoGenetic<E,V> automato; 
    final IParseble<V[]> parse;
    final List<V[]> frasesPos = new ArrayList();
    final List<V[]> frasesNeg = new ArrayList();

    public AutomatoFitness(AutomatoGenetic<E,V> automato, IParseble<V[]> parse, String[] frasesNeg, String[] frasesPos) {
        this.automato = automato;
        this.parse = parse;
        
        if(frasesPos != null)
            for (String frase : frasesPos) {
                this.frasesPos.add(parse.parse(frase));
            } 
        
        if(frasesNeg != null)
            for (String frase : frasesNeg) {
                this.frasesNeg.add(parse.parse(frase));
            } 
    }   
    

    @Override
    public double fitness(ChromosomeAbstract<Integer> chromosome) {
        
        double ret = 0;        
           
        automato.setPhenotype(chromosome.getGenotype());
        
        for (V[] frase : this.frasesPos) {
            ret += ((double)automato.success(frase))/this.frasesPos.size();
        }
        
        for (V[] frase : this.frasesNeg) {
            ret -= ((double)automato.success(frase))/this.frasesNeg.size();
        }
        
        return ret / 2.0;
        
    }
    
}
