package modelo.analizadorSintactico.descendentes;

import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Stack;

import modelo.analizadorLexico.AnalizadorLexico;
import modelo.analizadorSintactico.AnalizadorSintactico;

public class AnalizadorLL1 extends AnalizadorSintactico{

    public AnalizadorLL1(LinkedList<LinkedList<String>> reglasGramaticales,AnalizadorLexico analizadorLexico) {
    	super(reglasGramaticales,analizadorLexico);        
        crearTablaAnalisisSintactico();
    }

    @Override
    public void imprimeTablaAnalisisSintactico() {
        System.out.println("  " + Arrays.toString(simbolosTerminales.toArray()));
        for (int i = 0; i < simbolosNoTerminales.size(); i++) {
            System.out.println(simbolosNoTerminales.get(i) + " " + Arrays.toString(tablaAnalisisSintactico.get(i)));
        }
    }

    @Override
    protected void crearTablaAnalisisSintactico() {
    	//Inicializamos todos los valores de la tabla de analisis a -1
        for (int i = 0; i < simbolosNoTerminales.size(); i++) {
            	String[] renglon = new String[simbolosTerminales.size()];
            	Arrays.fill(renglon,"-1");
            	tablaAnalisisSintactico.add(renglon);
        }
        //Llenamos la tabla
        for (int i = 0; i < reglasGramaticales.size(); i++) {
            //Calculamos el first de la regla gramatical actual
            String ladoDerechoRegla[] = new String[reglasGramaticales.get(i).size()-1];
            for(int j = 0;j<ladoDerechoRegla.length;j++) {
                ladoDerechoRegla[j] = reglasGramaticales.get(i).get(j+1);
            }
            HashSet<String> conjuntoResultanteFirst = calculaFirst(ladoDerechoRegla);
            if (conjuntoResultanteFirst.contains(EPSILON)) {
            	HashSet<String> resultadoFollow = calculaFollow(reglasGramaticales.get(i).get(0));
            	System.out.println("Simbolo : "+reglasGramaticales.get(i).get(0)+" Follow : "+Arrays.toString(resultadoFollow.toArray()));
                conjuntoResultanteFirst.addAll(resultadoFollow);
                conjuntoResultanteFirst.remove(EPSILON);
            }
            String ladoDerecho = "";
            for(int s=1; s < reglasGramaticales.get(i).size(); s++){
                ladoDerecho += reglasGramaticales.get(i).get(s);
            }
            for (String simbolo : conjuntoResultanteFirst) {
                tablaAnalisisSintactico.get(simbolosNoTerminales.indexOf(reglasGramaticales.get(i).get(0)))[simbolosTerminales.indexOf(simbolo)] = String.valueOf(i + 1) + "-" + ladoDerecho;
            }
        }
    }

    @Override
    public boolean analizaCadena(String cadena) {
    	System.out.println("Evaluando cadena: "+cadena+"\n");
    	boolean cadenaValida = false;
    	Stack<String> pilaEvaluacion = new Stack<String>();
    	pilaEvaluacion.push(FIN_CADENA);
    	pilaEvaluacion.push(reglasGramaticales.get(0).get(0));
    	analizadorLexico.setCadenaAnalizar(cadena);
    	int token = analizadorLexico.getToken();
    	int indiceSubStringCadena = 0;
    	while(!pilaEvaluacion.isEmpty()){
    		System.out.print("Pila: "+Arrays.toString(pilaEvaluacion.toArray())+" Cadena: " +cadena.substring(indiceSubStringCadena)+"  ");
    		String renglonAnalisisCadena [] = new String[3];
    		renglonAnalisisCadena[0] = Arrays.toString(pilaEvaluacion.toArray());
    		renglonAnalisisCadena[1] = cadena.substring(indiceSubStringCadena);
    		String accion="Error";
    		//Tenemos dos casos, cuando el elemento en la pila es no terminal y cuando es terminal
    		String simboloPila = pilaEvaluacion.peek();
    		if(simbolosNoTerminales.contains(simboloPila)) {
    			//El simbolo de la pila no es terminal
    			String simboloCadena;
    			if(token == AnalizadorLexico.CADENA_TERMINADA) {
    				simboloCadena=FIN_CADENA;
    			}else {
    				simboloCadena=analizadorLexico.getClaseLexicaActual();
    			}
				int numReglaGramatical = Integer.valueOf(tablaAnalisisSintactico.get(simbolosNoTerminales.indexOf(simboloPila))[simbolosTerminales.indexOf(simboloCadena)]);
				accion="Sigue regla "+numReglaGramatical;
				pilaEvaluacion.pop();
				if(numReglaGramatical!=-1) {
					LinkedList<String> reglaGramatical = reglasGramaticales.get(numReglaGramatical);
					for(int i=reglaGramatical.size()-1;i>0;i--) {
						String ladoDerecho = reglaGramatical.get(i);
						if(!ladoDerecho.equals(EPSILON)) {
							pilaEvaluacion.push(ladoDerecho);
						}
					}
				}else {
					cadenaValida=false;
    				break;
				}
    		}else {
    			//El simbolo de la pila es terminal
    			//Verificamos que el simbolo de la pila y la clase lexica coincidan, si coinciden hacemos pop y obtenemos el siguiente token
    			if(simboloPila.equals(analizadorLexico.getClaseLexicaActual())) {
    				pilaEvaluacion.pop();
    				if(!analizadorLexico.getLexemaActual().equals("")) {
	                	indiceSubStringCadena+= analizadorLexico.getLexemaActual().length();
	                }
    				token = analizadorLexico.getToken();
    				accion="POP";
    			}else if(simboloPila.equals(FIN_CADENA)){
    				if(token == AnalizadorLexico.CADENA_TERMINADA) {
    					accion="Aceptar";
    					pilaEvaluacion.pop();
    					cadenaValida=true;
    				}else {
    					cadenaValida=false;
        				break;
    				}
    			}else {
    				cadenaValida=false;
    				break;
    			}
    		}
    		System.out.print(" Accion "+accion+"\n");
    		renglonAnalisisCadena[2] = accion;
    		tablaAnalisisCadena.add(renglonAnalisisCadena);
    	}
    	return cadenaValida;
    }
}