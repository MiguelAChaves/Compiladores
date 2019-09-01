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
    
    public AFN(Character s){
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
}
