/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jrmouro.automato;

/**
 *
 * @author ronaldo
 * @param <T>
 */
public interface IParseble<T> {
    public T parse(String str);
}
