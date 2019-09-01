/**
 * Clase que representa la abstraccion del objeto transicion para 
 * un Automata Finito No deterministico (AFN). Para la creación de 
 * las transiciones se considero lo siguiente:
 * @param edoSiguiente: Asignación al estado.
 * @param minSimbolo: Simbolo inicial
 * @param maxSimbolo: Simbolo final
 *
 * @author Miguel Angel Chaves
 */
package AFN;

public class Transicion {
    private final Estado edoSiguiente;
    char minSimbolo;
    char maxSimbolo;
    
    public Transicion(char s, Estado e){
        minSimbolo = s;
        maxSimbolo = s;
        edoSiguiente = e;
    }

    public Estado getEdoSiguiente() {
        return edoSiguiente;
    }

    public char getMinSimbolo() {
        return minSimbolo;
    }

    public char getMaxSimbolo() {
        return maxSimbolo;
    }
    
    
    
}
