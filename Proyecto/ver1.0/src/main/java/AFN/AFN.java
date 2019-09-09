/**
 * Clase que representa la abstraccion del objeto Automata Finito No 
 * deterministico (AFN). Para la creación del AFN consideraron los parámetros 
 * siguientes:
 * @param Alfabeto: Permite el incremento para el conteo del estado.
 * @param Estados: Identificador de estado.
 * @param EstadosAceptación: Identifica al estado de aceptación
 * @param EstadoInicial: Inicio del automata.
 * 
 * @author Miguel Angel Chaves
 */
package AFN;

import java.util.ArrayList;
import java.util.HashSet;

public class AFN {
    
    /**
     * Establecimiento de constantes
     * @param Epsilon: Caracter vacio.
     */
    static final char Epsilon = 0x00;
   
    
    HashSet<Estado> Estados;
    HashSet<Character> Alfabeto;
    Estado EstadoInicial;
    HashSet<Estado> EstadosAceptacion;
    
    public AFN(){
        Estados = new HashSet<>();
        Estados.clear();
        Alfabeto = new HashSet<>();
        Alfabeto.clear();
        EstadosAceptacion = new HashSet<>();
        EstadosAceptacion.clear();
    }
    
    public AFN(char s){
        EstadoInicial = new Estado();
        Estado EstadoFinal = new Estado();
        EstadoFinal.edoAcep = true;
        Estados = new HashSet<>();
        Alfabeto = new HashSet<>();
        EstadosAceptacion = new HashSet<>();
        EstadoInicial.setTransición(s, EstadoFinal);
        Alfabeto.add(s);
        Estados.add(EstadoInicial);
        Estados.add(EstadoFinal);
        EstadosAceptacion.add(EstadoFinal);
    }
    
    public void setConcat(AFN AFN2){
        for(Estado e: this.EstadosAceptacion){
            for(Transicion t: AFN2.EstadoInicial.Transiciones){
                e.setTransición(t.minSimbolo, t.getEdoSiguiente());
                AFN2.Estados.remove(AFN2.EstadoInicial);
            }
            e.setEdoAcep(false);
            EstadosAceptacion.remove(e);
            AFN2.Estados.remove(e);
        }
        this.Estados.addAll(AFN2.Estados);
        this.Alfabeto.addAll(AFN2.Alfabeto);
        this.EstadosAceptacion.addAll(AFN2.EstadosAceptacion);
        
    }

    @Override
    public String toString() {
        ArrayList<String> t = new ArrayList<>();
        Estados.forEach((var) -> {
            var.Transiciones.forEach((var2) -> {
                t.add("q" + var.getId() + " --> " + var2);
            });
        });
        return "----------------------------------------------\n"
                + "Detalles del Automata\n"
                + "----------------------------------------------\n"
                + "Alfabeto: " + Alfabeto.toString() + "\n"
                + "----------------------------------------------\n"
                + "Estados: " + Estados.toString() +  "\n"
                + "----------------------------------------------\n"
                + "Estado inicial: " + EstadoInicial.toString() + "\n"
                + "----------------------------------------------\n"
                + "Estados de aceptación: " + EstadosAceptacion.toString() + "\n" 
                + "----------------------------------------------\n"
                + "Transiciones\n" + t + "\n"
                + "----------------------------------------------\n";
    }
    
}
