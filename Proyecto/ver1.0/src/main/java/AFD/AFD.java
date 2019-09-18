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
    public ArrayList<String> Estados;

    public AFD() {
        this.Alfabeto = new HashSet<>();
        this.Estados = new ArrayList<>();
    }

    public HashSet<Character> getAlfabeto() {
        return Alfabeto;
    }

    public void setAlfabeto(HashSet<Character> Alfabeto) {
        this.Alfabeto = Alfabeto;
    }

    public ArrayList<String> getEstados() {
        return Estados;
    }

    public void setEstados(ArrayList<String> Estados) {
        this.Estados = Estados;
    }
    
    public void addEstado(String e){
        this.Estados.add(e);
    }

}
