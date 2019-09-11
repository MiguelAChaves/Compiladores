/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AFD;

import java.util.ArrayList;
import java.util.HashSet;

/**
 *
 * @author Migue
 */
public class AFD {
    public HashSet<Character> Alfabeto;
    public ArrayList<Estado> Estados;
    public int numFilas, numColumn;
    
    public AFD(HashSet<Character> Alfabeto){
        this.Alfabeto = new HashSet<Character>();
        this.Alfabeto.clear();
        this.Alfabeto.addAll(Alfabeto);
    }
}
