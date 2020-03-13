/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jrmouro.automato.genetic;

import java.util.List;

/**
 *
 * @author ronaldo
 */
public interface IAutomatoPhenotype<E,V> {
    public void setPhenotype(List<Integer> genotype);
}
