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
    static final char Epsilon = 'ε';
   
    
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
    
    public AFN(Estado s){
        Estados = new HashSet<>();
        Estados.clear();
        Alfabeto = new HashSet<>();
        Alfabeto.clear();
        EstadosAceptacion = new HashSet<>();
        EstadosAceptacion.clear();
        EstadoInicial = s;
        Estados.add(EstadoInicial);
    }
    
    public void setEstadoInicial(Estado EstadoInicial) {
        this.EstadoInicial = EstadoInicial;
    }

    public HashSet<Character> getAlfabeto() {
        return Alfabeto;
    }

    public HashSet<Estado> getEstados() {
        return Estados;
    }

    public Estado getEstadoInicial() {
        return EstadoInicial;
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
    
    public static AFN setCPositiva(AFN AFN_1){
        AFN auxAFN = new AFN();
        /*Agregamos el alfabeto*/
        auxAFN.Alfabeto.addAll(AFN_1.Alfabeto);
        /* Creamos el estado inicial*/
        Estado nuevoInicio = new Estado();
        /* Creamos el estado final*/
        Estado nuevoFinal = new Estado();
        nuevoFinal.setEdoAcep(true);
        /* Creamos las 2 transiciones para los 2 automatas*/
        nuevoInicio.setTransición(Epsilon, AFN_1.EstadoInicial);
        /* Agregamos el estado a la nueva lista de Estados*/
        auxAFN.setEstadoInicial(nuevoInicio);
        auxAFN.Estados.add(nuevoInicio);
        /* Agregar transicion entre los estados inicial final anteriores*/
        /* Agregar los estados y transiciones en AFN1*/
        for(Estado e: AFN_1.Estados){
            if(e.isEdoAcep()){
                e.setEdoAcep(false);
                e.setTransición(Epsilon, nuevoFinal);
                e.setTransición(Epsilon, AFN_1.EstadoInicial);
            }
            auxAFN.Estados.add(e);
        }
        /* Agregamos el estado final */
        auxAFN.Estados.add(nuevoFinal);
        auxAFN.EstadosAceptacion.add(nuevoFinal);
        
        return auxAFN;
    }
    
    public static AFN setCKleene(AFN AFN_1){
        AFN auxAFN = new AFN();
        /*Agregamos el alfabeto*/
        auxAFN.Alfabeto.addAll(AFN_1.Alfabeto);
        /* Creamos el estado inicial*/
        Estado nuevoInicio = new Estado();
        /* Creamos el estado final*/
        Estado nuevoFinal = new Estado();
        nuevoFinal.setEdoAcep(true);
        /* Creamos las 2 transiciones para los 2 automatas*/
        nuevoInicio.setTransición(Epsilon, AFN_1.EstadoInicial);
        nuevoInicio.setTransición(Epsilon, nuevoFinal);
        /* Agregamos el estado a la nueva lista de Estados*/
        auxAFN.setEstadoInicial(nuevoInicio);
        auxAFN.Estados.add(nuevoInicio);
        /* Agregar transicion entre los estados inicial final anteriores*/
        /* Agregar los estados y transiciones en AFN1*/
        for(Estado e: AFN_1.Estados){
            if(e.isEdoAcep()){
                e.setEdoAcep(false);
                e.setTransición(Epsilon, nuevoFinal);
                e.setTransición(Epsilon, AFN_1.EstadoInicial);
            }
            auxAFN.Estados.add(e);
        }
        /* Agregamos el estado final */
        auxAFN.Estados.add(nuevoFinal);
        auxAFN.EstadosAceptacion.add(nuevoFinal);
        
        return auxAFN;
    }
    
    public static AFN setCOpcional(AFN AFN_1){
        AFN auxAFN = new AFN();
        /*Agregamos el alfabeto*/
        auxAFN.Alfabeto.addAll(AFN_1.Alfabeto);
        /* Creamos el estado inicial*/
        Estado nuevoInicio = new Estado();
        /* Creamos el estado final*/
        Estado nuevoFinal = new Estado();
        nuevoFinal.setEdoAcep(true);
        /* Creamos las 2 transiciones para los 2 automatas*/
        nuevoInicio.setTransición(Epsilon, AFN_1.EstadoInicial);
        nuevoInicio.setTransición(Epsilon, nuevoFinal);
        /* Agregamos el estado a la nueva lista de Estados*/
        auxAFN.setEstadoInicial(nuevoInicio);
        auxAFN.Estados.add(nuevoInicio);
        /* Agregar transicion entre los estados inicial final anteriores*/
        /* Agregar los estados y transiciones en AFN1*/
        for(Estado e: AFN_1.Estados){
            if(e.isEdoAcep()){
                e.setEdoAcep(false);
                e.setTransición(Epsilon, nuevoFinal);
            }
            auxAFN.Estados.add(e);
        }
        /* Agregamos el estado final */
        auxAFN.Estados.add(nuevoFinal);
        auxAFN.EstadosAceptacion.add(nuevoFinal);
        
        return auxAFN;
    }
    
    public static AFN setUnion(AFN afn1, AFN afn2) {
        AFN auxAFN = new AFN();
        
        /*Agregamos el alfabeto*/
        auxAFN.Alfabeto.addAll(afn1.Alfabeto);
        auxAFN.Alfabeto.addAll(afn2.Alfabeto);
        
        /* Creamos el estado inicial*/
        Estado nuevoInicio = new Estado();
        /* Creamos el estado final*/
        Estado nuevoFinal = new Estado();
        nuevoFinal.setEdoAcep(true);
        /* Creamos las 2 transiciones para los 2 automatas*/
        nuevoInicio.setTransición(Epsilon, afn1.EstadoInicial);
        nuevoInicio.setTransición(Epsilon, afn2.EstadoInicial);
        
        /* Agregamos el estado a la nueva lista de Estados*/
        auxAFN.setEstadoInicial(nuevoInicio);
        auxAFN.Estados.add(nuevoInicio);
        
        /* Agregar los estados y transiciones en AFN1*/
        for(Estado e: afn1.Estados){
            if(e.isEdoAcep()){
                e.setEdoAcep(false);
                e.setTransición(Epsilon, nuevoFinal);
            }
            auxAFN.Estados.add(e);
        }
        
        /* Agregar los estados y transiciones en AFN2*/
        for(Estado e: afn2.Estados){
            if(e.isEdoAcep()){
                e.setEdoAcep(false);
                e.setTransición(Epsilon, nuevoFinal);
            }
            auxAFN.Estados.add(e);
        }
       
        /* Agregamos el estado final */
        auxAFN.Estados.add(nuevoFinal);
        auxAFN.EstadosAceptacion.add(nuevoFinal);
        
        return auxAFN;
    }

    public void setEspecial(AFN AFN2, String token){
        this.EstadoInicial.setTransición(Epsilon, AFN2.EstadoInicial);
        for(Estado e: AFN2.Estados){
            if(e.isEdoAcep()){
                e.setToken(token);
                this.EstadosAceptacion.add(e);
            }
            this.Estados.add(e);
        }
        
        this.Alfabeto.addAll(AFN2.Alfabeto);
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
