package modelo.analizadorLexico.automatas;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;

/**
 * Clase que nos sirve para modelar un Automata finito determinista
 * */
public class AutomataDeterminista extends Automata{
	private LinkedList<String[]> representacionTabular;
	
	/**
	 * Este contructor crea un AutomataDeterminista de acuerdo a lo establecido en los parametros
	 * @param HashSet<Estado> estados - Estados del automata
	 * @param LinkedList<Character> alfabeto - Alfabeto valido en el automata
	 * @param HashSet<Estado> estadosFinales - Estados finales
	 * @param Estado estadoInicial - Estado inicial del automata
	 * */
	public AutomataDeterminista(HashSet<Estado> estados, LinkedList<Character> alfabeto,HashSet<Estado> estadosFinales, Estado estadoInicial,LinkedList<String[]> representacionTabular) {
		super(estados,alfabeto,estadosFinales,estadoInicial);
		this.representacionTabular =  representacionTabular;
	}
	
	/**
	 * Este constructor permite crear un Automata determinista a partir de un automata no determinista
	 * @param AutomataNoDeterminista - Automata no determinista a partir del cual se creara un automata determinista
	 * */
	public AutomataDeterminista(AutomataNoDeterminista afn) {
		super(null,afn.getAlfabeto(),null,null);
		this.representacionTabular = new LinkedList<String[]>();
		LinkedList<HashSet<Estado>> conjuntosAnalizar = new LinkedList<HashSet<Estado>>();
		HashSet<Estado> conjuntoInicial = afn.cerraduraEpsilon(afn.getEstadoInicial());
		conjuntosAnalizar.add(conjuntoInicial);
		for(int j = 0;j<conjuntosAnalizar.size();j++) {
			String[] renglon = new String[this.alfabeto.size()+2];
			HashSet<Estado> conjuntoActual = conjuntosAnalizar.get(j);
			for(int i=0;i<this.alfabeto.size();i++) {
				Character simbolo = this.alfabeto.get(i);
				HashSet<Estado> conjuntoResultante = afn.irA(conjuntoActual,simbolo);
				if(!conjuntoResultante.isEmpty()) {
					if(!conjuntosAnalizar.contains(conjuntoResultante)) {
						conjuntosAnalizar.add(conjuntoResultante);
						renglon[i]=String.valueOf(conjuntosAnalizar.indexOf(conjuntoResultante));
					}else {
						renglon[i]=String.valueOf(conjuntosAnalizar.indexOf(conjuntoResultante));
					}
				}else {
					renglon[i]="-1";
				}
			}
			renglon[this.alfabeto.size()] ="-1";
			for(Estado estado:conjuntoActual) {
				if(estado.isEstadoFinal()) {
					renglon[this.alfabeto.size()] = String.valueOf(estado.getToken());
					renglon[this.alfabeto.size()+1] = estado.getClaseLexica();
				}
			}			
			this.representacionTabular.add(renglon);
		}
		
		this.alfabeto = afn.getAlfabeto();
		AutomataDeterminista automata = AutomataDeterminista.convertirTablaAutomata(alfabeto,this.representacionTabular);
		this.estados = automata.getEstados();
		this.estadoInicial = automata.getEstadoInicial();
		this.estadosFinales = automata.getEstadosFinales();
	}
	
	/**
	 * Metodo para convertir la representacion tabular de un AFD a un Objeto AutomataDeterminista
	 * @param LinkedList<Character> - Alfabeto del AFD
	 * @param LinkedList<int[]> - representacion tabular del AFD
	 * */
	public static AutomataDeterminista convertirTablaAutomata(LinkedList<Character> alfabeto,LinkedList<String[]> representacionTabular) {		
		//Primero creamos los estados del AFD y dejamos sus transiciones vacias, creamos un estado por cada renglon de la representacion tabular
		HashMap<Integer,Estado> mapaEstados = new HashMap<Integer,Estado>();
		HashSet<Estado> estados = new HashSet<Estado>();
		HashSet<Estado> estadosFinales = new HashSet<Estado>();
		Estado estadoInicial = null;
		for(int i = 0;i<representacionTabular.size();i++) {
			LinkedList<Transicion> transiciones = new LinkedList<Transicion>();
			Estado estado = new Estado(false,false, transiciones); //Creamos el estado
			if(i==0) { //Es el primer estado de la representacion tabular, lo marcamos como inicial
				estado.setEstadoInicial(true);
				estadoInicial= estado;
			}
			if(Integer.valueOf(representacionTabular.get(i)[alfabeto.size()]) != -1) { //Este renglon tiene token, por lo cual es de aceptacion, lo marcamos como tal y lo agregamos a la lista de estados finales
				estado.setEstadoFinal(true);
				estado.setToken(Integer.valueOf(representacionTabular.get(i)[alfabeto.size()]));
				estado.setClaseLexica(representacionTabular.get(i)[alfabeto.size()+1]);
				estadosFinales.add(estado);
			}
			estados.add(estado);//Agregamos el estado al conjunto de estados del automata
			mapaEstados.put(i,estado);//Lo guardamos en el mapa para que nos ayude a crear las transiciones
		}
		
		for(int i = 0;i<representacionTabular.size();i++) {
			String renglon []= representacionTabular.get(i);
			Estado estado = mapaEstados.get(i);
			for(int j=0;j<alfabeto.size();j++) {
				if(Integer.valueOf(renglon[j])!=-1) {
					estado.getTransiciones().add(new Transicion(alfabeto.get(j),mapaEstados.get(Integer.valueOf(renglon[j]))));//Creamos la transicion
				}
			}
		}
		
		return new AutomataDeterminista(estados,alfabeto,estadosFinales,estadoInicial,representacionTabular);
	}
	
	/**
	 * Nota: Este metodo NO esta implementado pero no se usa actualmente, al convertir de AFN a AFD esta tabla se genera automaticamente 
	 * 
	 * Metodo para convertir un AutomataDeterminista a su representacion Tabular
	 * @param AutomataDeterminista Automata determinista al cual se le obtendra una representacion tabular en base a sus transiciones y estados
	 * */
	private static LinkedList<int[]> generarRepresentacionTabularAutomata(AutomataDeterminista afd){
		//TODO - Agregar logica para transformar objeto AFD a su representacion tabular
		return null;
	}
	
	/**
	 * Metodo para guardar el AFD en representacion tabular a un archivo de texto
	 * @param AutomataDeterminista - Automata a guardar
	 * @param String - Ruta donde se guardara el archivo
	 * */
	public static void escribirAutomataArchivo(AutomataDeterminista afd,String rutaArchivo,String separador) throws IOException{
	    BufferedWriter writer = new BufferedWriter(new FileWriter(rutaArchivo));
	    //En la primera linea del archivo escribimos el separador que vamos a utilizar para despues poder leer el archivo sin problemas
	    writer.write(separador+"\n");
	    //Despues esribimos el alfabeto
	    String cadenaAlfabeto = "";
	    for(Character simbolo:afd.getAlfabeto()) {
	    	cadenaAlfabeto+=simbolo+separador;
		}
	    cadenaAlfabeto=cadenaAlfabeto.substring(0,cadenaAlfabeto.length()-1);
	    writer.write(cadenaAlfabeto+"\n");
	    //Finalmente escribimos la representacion tabular del automata
	    for(String[] renglon:afd.getRepresentacionTabular()) {
	    	String lineaAFD = "";
	    	for(String transicion:renglon) {
	    		lineaAFD+=transicion+separador;
	    	}
	    	lineaAFD=lineaAFD.substring(0,lineaAFD.length()-1);
	    	writer.write(lineaAFD+"\n");
	    }
	    //Cerramos el archivo
	    writer.close();
	}
	
	/**
	 * Metodo para leer el AFD de un archivo de texto generado por este programa
	 * @param String - Ruta donde se encuentra el archivo del AFD
	 * @return AutomataDeterminista - Automata determinista creado a partir del archivo
	 * */
	public static AutomataDeterminista leerAutomataArchivo(String rutaArchivo) throws IOException{
            LinkedList<Character> alfabeto = new LinkedList<Character>();
            LinkedList<String[]> representacionTabular = new LinkedList<String[]>();
            FileReader fileReader = new FileReader(rutaArchivo);
            BufferedReader buferedReader = new BufferedReader(fileReader);
            int linea = 0;
            String cadenaLeida;
            String separador = "";
        while((cadenaLeida = buferedReader.readLine())!=null) {
        	if(linea == 0) {
        		separador = cadenaLeida;
        	}else if (linea == 1) {
        		String simbolosAlfabeto[] = cadenaLeida.split(separador);
        		for(String simboloAlfabeto:simbolosAlfabeto) {
        			alfabeto.add(simboloAlfabeto.toCharArray()[0]);
        		}
        	}else {
        		String transicionesAutomata[] = cadenaLeida.split(separador);
        		String[] renglon = new String[alfabeto.size()+2];
        		int i = 0;
        		for(String transicionAutomata:transicionesAutomata) {
        			renglon[i] = transicionAutomata;
        			i++;
        		}
        		representacionTabular.add(renglon);
        	}
        	linea++;
        }
        buferedReader.close();
        return AutomataDeterminista.convertirTablaAutomata(alfabeto, representacionTabular);
	}
	
	public LinkedList<String[]> getRepresentacionTabular(){
		return representacionTabular;
	}
	
	public void analizaCadena(String cadena) {
		Estado estadoActual=this.getEstadoInicial();
		int indice=0,recuerda=0,estadoAceptacionPasado=-1;
		String lexema="";
		while(indice<cadena.length()) {
			if(obtenerTransicion(estadoActual,cadena.charAt(indice))!=null) {
				estadoActual=obtenerTransicion(estadoActual,cadena.charAt(indice));
				lexema+=cadena.charAt(indice);
				indice++;
				if(estadoActual.isEstadoFinal()) {
					recuerda=indice;
					estadoAceptacionPasado=estadoActual.getToken();
				}
			}else if(estadoAceptacionPasado==-1) {
				indice++;
				estadoActual=this.getEstadoInicial();
			}else {
				indice=recuerda;
				estadoActual=this.getEstadoInicial();
				estadoAceptacionPasado=-1;
				lexema="";
			}
		}
		if(estadoAceptacionPasado!=-1) {
			System.out.println("La cadena '"+cadena+"' es correcta");
		}else {
			System.out.println("La cadena '"+cadena+"' tiene un error sintactico en el caracter "+cadena.charAt(indice-1)+" en la posicion "+(indice-1));
		}
	}
	
	public static Estado obtenerTransicion(Estado estado, Character simbolo) {
		for(Transicion transicion:estado.getTransiciones()) {
			for(char c = transicion.getCaracterDeTransicionInicial().charValue();c<=transicion.getCaracterDeTransicionFinal().charValue();c++) {
				if(simbolo.charValue() == c) {
					return transicion.getEstadoSiguiente();
				}
			}
		}
		return null;
	}
}
