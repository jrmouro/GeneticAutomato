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
public class AutomatoGeneticTest {

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

    String[] estados = {"1", "2", "3"};

    String[][] prog = {
        {"2", null},
        {"2", "3"},
        {"2", "3"}
    };

    String[] vocab = {"a", "b"};

    String[] term = {"1"};

    /**
     * Test of run method, of class AutomatoGenetic.
     */
    @Test
    public void testRun() {
        
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
            "abbbb",
            
        };
        
        AutomatoGenetic instance = new AutomatoGenetic(estados, vocab);
        
        instance.run(this.parse, frasesNeg, frasesPos);
        
        System.out.print("\n" + instance + "\n\n");
        
        
        System.out.println("FrasesPos:");
        for (String f : frasesPos) {            
            System.out.print("  "+ f + ": ");
            System.out.println(instance.success(parse, f));             
        }
        
        System.out.println("FrasesNeg:");
        for (String f : frasesNeg) {            
            System.out.print("  "+ f + ": ");
            System.out.println(instance.success(parse, f));            
        }
        
        
        
    }

}
