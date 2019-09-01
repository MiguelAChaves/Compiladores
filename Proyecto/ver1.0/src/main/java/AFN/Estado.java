/**
 * Clase que representa la abstraccion del objeto estado para un
 * Automata Finito No deterministico (AFN). Para la creación de 
 * los estados se consideraron los parámetros siguientes:
 * @param nextId: Permite el incremento para el conteo del estado.
 * @param id: Identificador de estado.
 * @param edoAcep: Identifica al estado de aceptación
 * @param token: Para el estado final, identificar el token de aceptación
 * @param Transiciones: Conjunto de transiciones del estado.
 * 
 * @author Miguel Angel Chaves
 */
package AFN;

import java.util.HashSet;
import java.util.concurrent.atomic.AtomicInteger;

public class Estado {
    static AtomicInteger nextId = new AtomicInteger();
    private int id;
    boolean edoAcep;
    private String token;
    public HashSet<Transicion> Transiciones;
    
    public Estado(){
        id = nextId.incrementAndGet();
        edoAcep = false;
        token = "-1";
        Transiciones = new HashSet<Transicion>();
        Transiciones.clear();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isEdoAcep() {
        return edoAcep;
    }

    public void setEdoAcep(boolean edoAcep) {
        this.edoAcep = edoAcep;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
   
    
}
