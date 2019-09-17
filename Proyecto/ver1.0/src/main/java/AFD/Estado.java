/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AFD;

import AFN.Transicion;
import java.util.LinkedList;

/**
 *
 * @author Migue
 */
public class Estado {
    public int id;
    private LinkedList<Transicion> transiciones;
    public String tokenAcep;
    
    public Estado(){
        this.id = -1;
        transiciones = new LinkedList<> ();
        tokenAcep = "";
    }

    public Estado(int id, LinkedList<Transicion> transiciones, String tokenAcep) {
        this.id = id;
        this.transiciones = transiciones;
        this.tokenAcep = tokenAcep;
    }
}
