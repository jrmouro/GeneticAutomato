/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jrmouro.automato.genetic;

import com.jrmouro.automato.IParseble;
import org.junit.jupiter.api.Test;

/**
 *
 * @author ronaldo
 */
public class AutomatoGeneticIntegerStateTest {
    
    IParseble parse = new IParseble<String[]>() {
        @Override
        public String[] parse(String str) {
            String[] ret = new String[str.length()];
            for (int i = 0; i < str.length(); i++) {
                ret[i] = String.valueOf(str.charAt(i));
            }
            return ret;
        }
    };
    
    String[] vocab = {"a", "b"};
    
    /**
     * Test of setPhenotype method, of class AutomatoGeneticIntegerState.
     */
    @Test
    public void testSetPhenotype() {
        
        System.out.println("run");
        
        String[] frasesPos = {
            "a",
            "aa",
            "aaa",
            "abbba",
            "abababa",            
        };
        
        
        String[] frasesNeg = {
            "b",
            "bbb",
            "bbba",  
            "abbbb"            
        };
        
        AutomatoGeneticIntegerState instance = new AutomatoGeneticIntegerState<String>(vocab, 4);
        
        instance.run(this.parse, frasesNeg, frasesPos);
        
        System.out.print("\n" + instance + "\n\n");
        
        
        System.out.println("FrasesPos:");
        for (String f : frasesPos) {            
            System.out.print("  "+ f + ": ");
            System.out.println(instance.success(parse, f));             
        }
        
        System.out.println("\nFrasesNeg:");
        for (String f : frasesNeg) {            
            System.out.print("  "+ f + ": ");
            System.out.println(instance.success(parse, f));            
        }
        
        
        
    }
    
}
