/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jrmouro.automato;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author ronaldo
 */
public class AutomatoTest {

    /**
     * Test of success method, of class Automato.
     */
    @Test
    public void testRun() {
        
        System.out.println("run");

        String[] estados = {"1", "2", "3"};

        String[][] prog = {
            {"2", null},
            {"2", "3"},
            {"2", "3"}
        };

        String[] vocab = {"a", "b"};

        String[] term = {"1"};

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

        Automato<String, String> instance = new Automato(estados, vocab, term, "1", prog);
        int expResult = 1;
        int result = instance.success(parse, "abab");
        
        System.out.print("\n" + instance + "\n\n");

        //assertEquals(expResult, result);
    }

}
