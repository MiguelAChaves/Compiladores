/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utilities;

import AFN.Estado;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.concurrent.atomic.AtomicInteger;

/**
 *
 * @author Migue
 */
public class Conjunto {
    private int id;
    HashSet<Estado> Estados;
    
    public Conjunto(int id, HashSet<Estado> cargaEstados){
        this.id = id;
        Estados = cargaEstados;
    }
    public int getId() {
        return id;
    }

    public HashSet<Estado> getEstados() {
        return Estados;
    }
    
}
