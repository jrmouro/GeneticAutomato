/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jrmouro.automato.genetic;

import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ronaldo
 * @param <V>
 */
public class AutomatoGeneticIntegerState<V> extends AutomatoGenetic<Integer,V>{

    public int stateMaxNumber = 10;
    public int stateMinNumber = 1;
    
    public AutomatoGeneticIntegerState(V[] vocabulario) {
        super(vocabulario);        
    }
    
    public AutomatoGeneticIntegerState(V[] vocabulario, int stateMaxNumber) {
        super(vocabulario);
        this.stateMaxNumber = stateMaxNumber;       
    }
    
    public AutomatoGeneticIntegerState(V[] vocabulario, int stateMinNumber, int stateMaxNumber) {
        super(vocabulario);
        this.stateMaxNumber = stateMaxNumber;       
    }

    @Override
    public int getMinGenotypeSize() {
        return  1 + // estado inicial
                1 + // número de estados
                this.stateMaxNumber +
                (this.stateMaxNumber * this.vocabulario.length);
    }
    
    

    @Override
    public void setPhenotype(List<Integer> genotype) {
        
        if(this.isInitialized()){
        
            if(genotype.size() >= this.getMinGenotypeSize()){
            
                this.setEstadoInicial(new Integer(0));

                int n = (genotype.get(0) % this.stateMaxNumber) + 1;
                
                if( n < this.stateMinNumber)
                    n = this.stateMinNumber;
                
                if(n > 0){              

                    this.estados = new Integer[n];
                    this.estados[0] = this.estadoInicial;            

                    this.mapE = new HashMap();
                    this.mapE.putIfAbsent(this.estados[0] , 0);


                    for (int i = 1; i < n; i++) {
                        this.estados[i] = new Integer(i);
                        this.mapE.putIfAbsent(this.estados[i] , i);
                    }

                    genotype.remove(0);

                    n = (genotype.get(0) % this.estados.length) + 1;

                    genotype.remove(0);

                    this.mapET = new HashMap();

                    int c = 0;

                    for (int i = 0; i < this.estados.length; i++) {

                        int a = genotype.get(0) % (int)((double)this.estados.length / terminalStateRate);

                        if(a == 0 && c < n){
                            this.mapET.putIfAbsent(this.estados[i], c++);
                        }

                        genotype.remove(0);
                    }

                    this.funcaoPrograma = new Integer[this.estados.length][this.vocabulario.length];

                    for (int i = 0; i < this.estados.length; i++) {

                        for (int j = 0; j < this.vocabulario.length; j++) {

                            int a = (genotype.get(0) % (this.estados.length + (int)((double)this.estados.length * nullStateProgrammRate)));

                            if(a >= this.estados.length)
                                this.funcaoPrograma[i][j] = null;
                            else
                                this.funcaoPrograma[i][j] = this.estados[a];

                            genotype.remove(0);

                        }

                    }
                
                }else{
                    try {
                        throw new Exception("Invalid states number");
                    } catch (Exception ex) {
                        Logger.getLogger(AutomatoGeneticIntegerState.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }


            }else{
                try {
                    throw new Exception("Invalid genotype");
                } catch (Exception ex) {
                    Logger.getLogger(AutomatoGenetic.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }else{
            try {
                throw new Exception("AutomatoGeneticIntegerState não inicializado!");
            } catch (Exception ex) {
                Logger.getLogger(AutomatoGeneticIntegerState.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public boolean isInitialized() {
        return  super.isInitialized() || 
                (this.vocabulario != null && this.vocabulario.length > 0); 
    }
       
   
}
