package modelo.analizadorSintactico;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;

import modelo.analizadorLexico.AnalizadorLexico;

public abstract class AnalizadorSintactico {
    public static final String EPSILON = "∈";
    public static final String FIN_CADENA = "$";
	public static final String PUNTO = "•";
	
    protected LinkedList<String> simbolosTerminales;
    protected LinkedList<String> simbolosNoTerminales;
    protected AnalizadorLexico analizadorLexico;
    protected LinkedList<String[]> tablaAnalisisSintactico;
    protected LinkedList<LinkedList<String>> reglasGramaticales;
    protected HashMap<String[], HashSet<String>> firstCalculados;
    protected HashMap<String, HashSet<String>> followCalculados;
	protected LinkedList<String[]> tablaAnalisisCadena;
	
    protected AnalizadorSintactico(LinkedList<LinkedList<String>> reglasGramaticales,AnalizadorLexico analizadorLexico) {
    	this.reglasGramaticales=reglasGramaticales;
    	this.analizadorLexico=analizadorLexico;
        this.firstCalculados = new HashMap<String[], HashSet<String>>();
        this.followCalculados = new HashMap<String, HashSet<String>>();
        this.simbolosNoTerminales = new LinkedList<String>();
        this.simbolosTerminales = new LinkedList<String>();
        this.tablaAnalisisSintactico = new LinkedList<String[]>();
        this.tablaAnalisisCadena = new LinkedList<String[]>();
        //Obtenemos los simbolos no terminales de la gramatica
        for (LinkedList<String> reglaGramatical : this.reglasGramaticales) {
            if (!simbolosNoTerminales.contains(reglaGramatical.get(0))) {
                simbolosNoTerminales.add(reglaGramatical.get(0));
            }
        }
        //Ahora obtenemos los simbolos terminales de la gramatica
        for (LinkedList<String>reglaGramatical : this.reglasGramaticales) {
            for (String simbolo : reglaGramatical) {
                if (!simbolosNoTerminales.contains(simbolo)) {
                    if (!simbolo.equals(EPSILON) && !simbolosTerminales.contains(simbolo)) {                    	
                        simbolosTerminales.add(simbolo);
                    }
                }
            }
        }
        simbolosTerminales.add(FIN_CADENA);
    }
    
	protected abstract void crearTablaAnalisisSintactico();
    
    public abstract void imprimeTablaAnalisisSintactico();
    
    public abstract boolean analizaCadena(String cadena);
    
    protected HashSet<String> calculaFollow(String simbolo) {
        if (followCalculados.containsKey(simbolo)) {
            return followCalculados.get(simbolo);
        } else {
            HashSet<String> conjuntoResultante = new HashSet<String>();

            //Verificamos si el simbolo es el no terminal inicial, si si lo es agregamos el fin de cadena
            if (simbolo.equals(reglasGramaticales.get(0).get(0))) {
                conjuntoResultante.add(FIN_CADENA);
            }

            //Buscamos las reglas que tengan al simbolo del lado derecho
            LinkedList<LinkedList<String>> reglasEncontradas = new LinkedList<LinkedList<String>>();
            for (LinkedList<String> reglaGramatical : reglasGramaticales) {
                for (int i = 1; i < reglaGramatical.size(); i++) {
                    if (reglaGramatical.get(i).equals(simbolo)) {
                        reglasEncontradas.add(reglaGramatical);
                    }
                }
            }

            for (LinkedList<String> reglaGramatical : reglasEncontradas) {
                for (int i = 1; i < reglaGramatical.size(); i++) {
                    if (reglaGramatical.get(i).equals(simbolo)) {
                        //Verificamos que el no terminal SIMBOLO no esté al final de la producción de la Regla
                        if ((i + 1) < reglaGramatical.size()) {
                        	LinkedList<String> conjuntoReducido = new LinkedList<String>();
                        	for(int j = i+1;j<reglaGramatical.size();j++) {
                        		conjuntoReducido.add(reglaGramatical.get(j));
                        	}
                        	String[] conjuntoSimbolosReducido = conjuntoReducido.toArray(new String[conjuntoReducido.size()]);
                        	HashSet<String> resultadoFirst = calculaFirst(conjuntoSimbolosReducido);
                        	conjuntoResultante.addAll(resultadoFirst);
                        	conjuntoResultante.remove(EPSILON);
                        	
                            
                            if (firstCalculados.get(conjuntoSimbolosReducido).contains(EPSILON)) {
                                if (!simbolo.equals(reglaGramatical.get(0))) {
                                    conjuntoResultante.addAll(calculaFollow(reglaGramatical.get(0)));
                                }
                            }
                            //Verificamos que el lado izquierdo no sea igual al que estamos analizando
                        } else if (!simbolo.equals(reglaGramatical.get(0))) {
                            conjuntoResultante.addAll(calculaFollow(reglaGramatical.get(0)));
                        }
                    }
                }

            }
            followCalculados.put(simbolo, conjuntoResultante);
            return conjuntoResultante;
        }
    }
    
    protected HashSet<String> calculaFirst(String[] conjuntoSimbolos) {
        if (firstCalculados.containsKey(conjuntoSimbolos)) {
            return firstCalculados.get(conjuntoSimbolos);
        } else {
            HashSet<String> conjuntoResultante = new HashSet<String>();
            if (simbolosTerminales.contains(conjuntoSimbolos[0])) {
                conjuntoResultante.add(conjuntoSimbolos[0]);
            } else if (conjuntoSimbolos[0].equals(EPSILON)) {
                conjuntoResultante.add(EPSILON);
            } else {
                //Buscamos las reglas que tengan al simbolo no terminal del lado izquierdo
                LinkedList<LinkedList<String>> reglasEncontradas = new LinkedList<LinkedList<String>>();
                for (LinkedList<String> reglaGramaticalEncontrada : reglasGramaticales) {
                    if (conjuntoSimbolos[0].equals(reglaGramaticalEncontrada.get(0))) {
                        reglasEncontradas.add(reglaGramaticalEncontrada);
                    }
                }
                for (LinkedList<String>reglaGramaticalEncontrada : reglasEncontradas) {
                	String conjuntoSimbolosNuevo[] = new String[reglaGramaticalEncontrada.size()-1];
                	for(int i = 0;i<conjuntoSimbolosNuevo.length;i++) {
                		conjuntoSimbolosNuevo[i] = reglaGramaticalEncontrada.get(i+1);
                	}
                    conjuntoResultante.addAll(calculaFirst(conjuntoSimbolosNuevo));
                    
                }
                if(conjuntoResultante.contains(EPSILON) && conjuntoSimbolos.length!=1){
                	conjuntoResultante.remove(EPSILON);
                	String conjuntoSimbolosNuevo[] = new String[conjuntoSimbolos.length-1];
                	for(int i = 0;i<conjuntoSimbolosNuevo.length;i++) {
                		conjuntoSimbolosNuevo[i] = conjuntoSimbolos[i+1];
                	}
                	conjuntoResultante.addAll(calculaFirst(conjuntoSimbolosNuevo));
                	
                }
            }
            firstCalculados.put(conjuntoSimbolos, conjuntoResultante);
            return conjuntoResultante;
        }
    }
    
    
    public LinkedList<String[]> getTablaAnalisisSintactico(){
    	return this.tablaAnalisisSintactico;
    }

	public LinkedList<String> getSimbolosTerminales() {
		return simbolosTerminales;
	}

	public LinkedList<String> getSimbolosNoTerminales() {
		return simbolosNoTerminales;
	}
    
	public LinkedList<String[]> getTablaAnalisisCadena() {
		return tablaAnalisisCadena;
	}
    
    
}
