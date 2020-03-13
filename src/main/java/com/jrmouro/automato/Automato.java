/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jrmouro.automato;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ronaldo
 */
public class Automato<E, V> implements Initializable {
    
    protected E[] estados = null;
    final protected V[] vocabulario;
    protected E estadoInicial = null;
    
    protected E[][] funcaoPrograma = null;
    final Map<V, Integer> mapV = new HashMap();
    protected Map<E, Integer> mapE = new HashMap();
    protected Map<E, Integer> mapET = new HashMap();
    
    public Automato(V[] vocabulario) {
        this(null, vocabulario, null, null, null);        
    }
    
    public Automato(E[] estados, V[] vocabulario, E[] estadosTerminais, E estadoInicial) {
        this(estados, vocabulario, estadosTerminais, estadoInicial, null);        
    }
    
    public Automato(E[] estados, V[] vocabulario, E[] estadosTerminais, E estadoInicial, E[][] funcaoPrograma) {
        
        this.estados = estados;
        this.vocabulario = vocabulario;
        this.estadoInicial = estadoInicial;
        this.funcaoPrograma = funcaoPrograma;
        
        int ind = 0;
        
        if(this.estados != null)
            for (E e : estados) {
                this.mapE.putIfAbsent(e, ind++);
            }
        
        ind = 0;
        
        if(estadosTerminais != null)
            for (E e : estadosTerminais) {
                this.mapET.putIfAbsent(e, ind++);
            }
        
        ind = 0;
        
        if(this.vocabulario != null)
            for (V v : vocabulario) {
                this.mapV.putIfAbsent(v, ind++);
            }
        
    }
    
    public void setEstadosTerminais(E[] estadosTerminais) {
        
        this.mapET = new HashMap();
        
        int ind = 0;
        
        for (E e : estadosTerminais) {
            this.mapET.putIfAbsent(e, ind++);
        }
        
    }
    
    public void setEstadoInicial(E estadoInicial) {
        this.estadoInicial = estadoInicial;
    }
    
    public void setFuncaoPrograma(E[][] funcaoPrograma) {
        this.funcaoPrograma = funcaoPrograma;
    }
    
    public int success(IParseble<V[]> parse, String str) {
        V[] f = parse.parse(str);
        return this.success(f);
    }
    
    public int success(V[] frase) {
        
        if (this.isInitialized()) {
            
            List<Integer> list = new ArrayList();
            
            for (V v : frase) {
                
                Integer i = this.mapV.get(v);
                
                if (i != null) {
                    
                    list.add(i);
                    
                } else {
                    try {
                        throw new Exception("Palavra não pertencente ao Vocabulário!");
                    } catch (Exception ex) {
                        Logger.getLogger(Automato.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                
            }
            
            int ret = fpe(this.mapE.get(this.estadoInicial), list);
            
            if (ret < 0) {
                
                //return ret;
                return -1;
                
            }
            
            if (this.isEstadoTerminal(ret)) {
                
                //return frase.length;
                return 1;
                
            }
            
            //return -frase.length;
            return 0;
            
        } else {
            
            try {
                throw new Exception("Automato não inicializado!");
            } catch (Exception ex) {
                Logger.getLogger(Automato.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
        
        return Integer.MIN_VALUE;
        
    }
    
    private int fpe(int indEstado, List<Integer> list) {
        
        if (!list.isEmpty()) {
            
            int i = list.get(0);
            
            int fp = fp(indEstado, i);
            
            if (fp < 0) {
                return -list.size();
            }
            
            list.remove(0);
            
            return fpe(fp, list);
            
        }
        
        return indEstado;
        
    }
    
    private int fp(int indEstado, int indPalavra) {
        
        E e = this.funcaoPrograma[indEstado][indPalavra];
        
        Integer i = this.mapE.get(e);
        
        if (i != null) {
            return i;
        }
        
        return -1;
        
    }
    
    private boolean isEstadoTerminal(int ind) {
        E e = this.estados[ind];
        return this.mapET.get(e) != null;
    }
    
    @Override
    public void initialize(Object... o) {
        
        this.funcaoPrograma = null;
        
        if (o != null) {
            
            for (Object object : o) {
                
                E[][] p = (E[][]) object;
                
                if (p.length >= this.estados.length) {
                    if (p.length > 0) {
                        if (p[0].length >= this.vocabulario.length) {
                            if (p[0].length > 0) {
                                this.funcaoPrograma = p;
                            }
                        }
                    }
                }
                
            }
            
        }
        
    }
    
    @Override
    public boolean isInitialized() {
        return  this.estados != null && this.estados.length > 0 &&
                this.funcaoPrograma != null &&
                this.estadoInicial != null &&
                this.vocabulario != null && this.vocabulario.length > 0;
    }
    
    @Override
    public String toString() {
        
        if(this.isInitialized()){
        
            StringBuffer strb = new StringBuffer();

            strb.append("Automato:");

            strb.append("\n -estadoInicial = ").append(estadoInicial.toString());

            strb.append("\n -estados= ");
            for (E e : estados)
                strb.append(e.toString()).append(" ");

            strb.append("\n -estados terminais= ");
            this.mapET.forEach((t, u) -> {
                strb.append(t.toString()).append(" ");
            });


            strb.append("\n -vocabulario= ");
            for (V v : vocabulario)
                strb.append(v.toString()).append(" ");

            strb.append("\n -funcaoPrograma= ");
            if(this.funcaoPrograma != null)
                for (E[] es : funcaoPrograma) {
                    strb.append("\n   ");
                    for (E e : es) {  
                        if(e != null)
                            strb.append(e.toString()).append(" ");   
                        else
                            strb.append("#").append(" "); 
                    }
                }

            return strb.toString();
            
        }else{
            
            try {
                throw new Exception("Automato não inicializado!");
            } catch (Exception ex) {
                Logger.getLogger(Automato.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
        
        return "Automato não inicializado!";
    }
    
}
