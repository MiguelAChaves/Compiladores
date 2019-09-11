/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AFD;

/**
 *
 * @author Migue
 */
public class Estado {
    public int id;
    public int[] corrAlfabeto_Estado;
    public String tokenAcep;

    public Estado(int id, int[] corrAlfabeto_Estado, String tokenAcep) {
        this.id = id;
        this.corrAlfabeto_Estado = corrAlfabeto_Estado;
        this.tokenAcep = tokenAcep;
    }
}
