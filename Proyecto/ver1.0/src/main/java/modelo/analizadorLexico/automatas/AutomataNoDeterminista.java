package modelo.analizadorLexico.automatas;

import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Stack;
/**
 * Clase que define un Automata finito no determinista y sus operaciones segun el modelo de Thompson.
 * Este modelo dicta que solo se tendra un estado final, aun asi la clase padre Automata define
 * un conjunto de estados finales. Aunque es posible representar un Automata finito no determinista que no sea de Thompson usando esta clase
 * las operaciones que se apliquen sobre el consideraran un solo estado final o aplicaran el algoritmo para todo el conjunto
 * de estados finales del automata
 * */
public class AutomataNoDeterminista extends Automata{

	/**
	 * Este contructor crea un AutomataNoDeterminista de acuerdo a lo establecido en los parametros
	 * @param HashSet<Estado> estados - Estados del automata
	 * @param LinkedList<Character> alfabeto - Alfabeto valido en el automata
	 * @param HashSet<Estado> estadosFinales - Estados finales
	 * @param Estado estadoInicial - Estado inicial del automata
	 * */
	public AutomataNoDeterminista(HashSet<Estado> estados, LinkedList<Character> alfabeto,HashSet<Estado> estadosFinales, Estado estadoInicial) {
		super(estados,alfabeto,estadosFinales,estadoInicial);
	}

	/**
	 * Este constructor crea un AutomataNoDeterminista Basico de Thompson
	 * 
	 * @param Character ch - Caracter a usar en la transicion del automata
	 * @param LinkedList<Character> alfabeto - Alfabeto valido en el automata
	 * */
	public AutomataNoDeterminista (Character ch,LinkedList<Character> alfabeto){
		super(null,alfabeto,null,null);
		if(!super.getAlfabeto().contains(ch)) {
			super.getAlfabeto().add(ch);
		}
		HashSet<Estado> estados = new HashSet<Estado>();
		Estado estadoFinal = new Estado(false,true,new LinkedList<Transicion>());
		HashSet<Estado> estadosFinales = new HashSet<Estado>();
		estadosFinales.add(estadoFinal);
		LinkedList<Transicion> transicionesEstadoInicial= new LinkedList<Transicion>();
		transicionesEstadoInicial.add(new Transicion(ch,estadoFinal));
		Estado estadoInicial = new Estado(true,false,transicionesEstadoInicial);	
		estados.add(estadoInicial);
		estados.add(estadoFinal);
		
		this.estados = estados;
		this.estadosFinales=estadosFinales;
		this.estadoInicial=estadoInicial;
	}
	
	public AutomataNoDeterminista (Character chInicio,Character chFin,LinkedList<Character> alfabeto){
			super(null,alfabeto,null,null);
			for(char c = chInicio;c<=chFin;c++) {
				if(!super.getAlfabeto().contains(c)) {
					super.getAlfabeto().add(c);
				}
			}
			HashSet<Estado> estados = new HashSet<Estado>();
			Estado estadoFinal = new Estado(false,true,new LinkedList<Transicion>());
			HashSet<Estado> estadosFinales = new HashSet<Estado>();
			estadosFinales.add(estadoFinal);
			LinkedList<Transicion> transicionesEstadoInicial= new LinkedList<Transicion>();
			transicionesEstadoInicial.add(new Transicion(chInicio,chFin,estadoFinal));
			Estado estadoInicial = new Estado(true,false,transicionesEstadoInicial);	
			estados.add(estadoInicial);
			estados.add(estadoFinal);
			this.estados = estados;
			this.estadosFinales=estadosFinales;
			this.estadoInicial=estadoInicial;
	
	}
	
	/**
	 * Aplica la operacion de union (a/b) segun el modelo de Thompson al automata actual
	 * 
	 * @param AutomataNoDeterminista afnb - Automata que se unira a este automata
	 * @return AutomataNoDeterminista - El resultado de unir este automata con el parametro
	 * */
	public AutomataNoDeterminista unirAutomata(AutomataNoDeterminista afnb) {
		Transicion transicionInicialA = new Transicion(this.estadoInicial);
		Transicion transicionInicialB = new Transicion(afnb.getEstadoInicial());
		afnb.getEstadoInicial().setEstadoInicial(false);
		LinkedList<Transicion> transicionesEpsilonEstadosIniciales = new LinkedList<Transicion>();
		transicionesEpsilonEstadosIniciales.add(transicionInicialA);
		transicionesEpsilonEstadosIniciales.add(transicionInicialB);
		Estado estadoInicialNuevo = new Estado(true,false,transicionesEpsilonEstadosIniciales);
		this.estados.add(estadoInicialNuevo);
		this.estadoInicial.setEstadoInicial(false);
		this.estadoInicial = estadoInicialNuevo;
		Estado estadoFinalNuevo = new Estado(false,true,new LinkedList<Transicion>());
		this.estados.add(estadoFinalNuevo);
		for(Estado estado:this.estadosFinales) {
			estado.getTransiciones().add(new Transicion(estadoFinalNuevo)); //Thompson solo tiene un estado final
			estado.setEstadoFinal(false);
		}
		for(Estado estado:afnb.getEstadosFinales()) {
			estado.getTransiciones().add(new Transicion(estadoFinalNuevo));
			estado.setEstadoFinal(false);
		}
		this.estadosFinales = new HashSet<Estado>();
		estadosFinales.add(estadoFinalNuevo);
		for(Character simbolo:afnb.getAlfabeto()) {
			if(!this.alfabeto.contains(simbolo)) {
				this.alfabeto.add(simbolo);
			}
		}
		this.estados.addAll(afnb.getEstados());
		return this;
	}
	
	/**
	 * Aplica la operacion de concatenacion (a o b) segun el modelo de Thompson al automata actual
	 * 
	 * @param AutomataNoDeterminista afnb - Automata que se concatenara a este automata
	 * @return AutomataNoDeterminista - El resultado de concatenar el parametro con este automata
	 * */
	public AutomataNoDeterminista concatenarAutomata(AutomataNoDeterminista afnb) {
		for(Estado estado:this.estadosFinales) {
			estado.getTransiciones().addAll(afnb.getEstadoInicial().getTransiciones());
			estado.setEstadoFinal(false);
		}
		for(Estado estado:afnb.getEstados()) {
			if(!estado.isEstadoInicial()) {
				this.estados.add(estado);
			}
		}
		afnb.getEstadoInicial().setEstadoInicial(false);
		
		this.estadosFinales=afnb.getEstadosFinales();
		
		for(Character simbolo:afnb.getAlfabeto()) {
			if(!this.alfabeto.contains(simbolo)) {
				this.alfabeto.add(simbolo);
			}
		}
		return this;
	}
	
	/**
	 * Aplica la operacion opcional (a?) segun el modelo de Thompson al automata actual
	 * 
	 * @return AutomataNoDeterminista - El resultado de aplicar la operacion opcional a este automata
	 * */
	public AutomataNoDeterminista aplicarOperacionOpcional() {
		LinkedList<Transicion> transicionesEpsilonNuevoInicial = new LinkedList<Transicion>(); 
		transicionesEpsilonNuevoInicial.add(new Transicion(this.estadoInicial));
		Estado estadoInicialNuevo = new Estado(true,false,transicionesEpsilonNuevoInicial);
		this.estadoInicial.setEstadoInicial(false);
		this.estadoInicial = estadoInicialNuevo;
		this.estados.add(estadoInicialNuevo);
		Estado estadoFinalNuevo = new Estado(false,true,new LinkedList<Transicion>());
		
		for(Estado estado:this.estadosFinales) {
			estado.getTransiciones().add(new Transicion(estadoFinalNuevo));
			estado.setEstadoFinal(false);
		}
		this.estados.add(estadoFinalNuevo);
		HashSet<Estado> estadosFinales = new HashSet<Estado>();
		estadosFinales.add(estadoFinalNuevo);
		this.estadosFinales = estadosFinales;
		
		for(Estado estado:this.estadosFinales) {
			this.estadoInicial.getTransiciones().add(new Transicion(estado));
		}
		
		return this;
	}
	/**
	 * Aplica la operacion cerradura de Kleen (a*) segun el modelo de Thompson al automata actual
	 * 
	 * @return AutomataNoDeterminista - El resultado de aplicar la operacion cerradura de Kleen a este automata
	 * */
	public AutomataNoDeterminista cerraduraKleen() {
		LinkedList<Transicion> transicionesEpsilonNuevoInicial = new LinkedList<Transicion>(); 
		transicionesEpsilonNuevoInicial.add(new Transicion(this.estadoInicial));
		Estado estadoInicialNuevo = new Estado(true,false,transicionesEpsilonNuevoInicial);
		for(Estado estado:this.estadosFinales) {
			estado.getTransiciones().add(new Transicion(this.estadoInicial));
		}
		this.estados.add(estadoInicialNuevo);
		this.estadoInicial.setEstadoInicial(false);
		this.estadoInicial = estadoInicialNuevo;
		Estado estadoFinalNuevo = new Estado(false,true,new LinkedList<Transicion>());
		
		for(Estado estado:this.estadosFinales) {
			estado.getTransiciones().add(new Transicion(estadoFinalNuevo));
			estado.setEstadoFinal(false);
		}
		HashSet<Estado> estadosFinales = new HashSet<Estado>();
		estadosFinales.add(estadoFinalNuevo);
		this.estados.add(estadoFinalNuevo);
		this.estadosFinales = estadosFinales;
		
		for(Estado estado:this.estadosFinales) {
			this.estadoInicial.getTransiciones().add(new Transicion(estado));
		}
		
		return this;
	}
	
	/**
	 * Aplica la operacion cerradura positiva (a+) segun el modelo de Thompson al automata actual
	 * 
	 * @return AutomataNoDeterminista - El resultado de aplicar la operacion cerradura positiva a este automata
	 * */
	public AutomataNoDeterminista cerraduraPositiva() {
		LinkedList<Transicion> transicionesEpsilonNuevoInicial = new LinkedList<Transicion>(); 
		transicionesEpsilonNuevoInicial.add(new Transicion(this.estadoInicial));
		Estado estadoInicialNuevo = new Estado(true,false,transicionesEpsilonNuevoInicial);
		for(Estado estado:this.estadosFinales) {
			estado.getTransiciones().add(new Transicion(this.estadoInicial));
		}
		this.estados.add(estadoInicialNuevo);
		this.estadoInicial.setEstadoInicial(false);
		this.estadoInicial = estadoInicialNuevo;
		Estado estadoFinalNuevo = new Estado(false,true,new LinkedList<Transicion>());
		
		for(Estado estado:this.estadosFinales) {
			estado.getTransiciones().add(new Transicion(estadoFinalNuevo));
			estado.setEstadoFinal(false);
		}
		HashSet<Estado> estadosFinales = new HashSet<Estado>();
		estadosFinales.add(estadoFinalNuevo);
		this.estados.add(estadoFinalNuevo);
		this.estadosFinales = estadosFinales;
		
		return this;
	}
	
	/**
	 * Efectua la operacion cerradura epsilon al estado dado
	 * @param long id - ID del estado al que se le aplicara la operacion
	 * @return HashSet<Estado> - Conjunto de estados resultante de la cerradura
	 * */
	public HashSet<Estado> cerraduraEpsilon(long id){
		HashSet<Estado> resultadoCerraduraEpsilon = null;
		for(Estado estado:this.estados) {
			if(estado.getId() == id) {
				resultadoCerraduraEpsilon = cerraduraEpsilon(estado);
				break;
			}
		}
		return resultadoCerraduraEpsilon;
	}
	
	/**
	 * Efectua la operacion cerradura epsilon al estado dado
	 * @param Estado - Estado al que se le aplicara la operacion
	 * @return HashSet<Estado> - Conjunto de estados resultante de la cerradura
	 * */
	public  HashSet<Estado> cerraduraEpsilon(Estado estado){
		HashSet<Estado> resultadoCerraduraEpsilon = new HashSet<Estado>();
		Stack<Estado> pilaEstados = new Stack<Estado>();
		pilaEstados.push(estado);
		while(!pilaEstados.isEmpty()) {
			Estado estadoActual = pilaEstados.pop();
			if(!resultadoCerraduraEpsilon.contains(estadoActual)) {
				resultadoCerraduraEpsilon.add(estadoActual);
				for(Transicion transicion:estadoActual.getTransiciones()) {
					if(transicion.isEpsilon()) {
						pilaEstados.push(transicion.getEstadoSiguiente());
					}
				}
			}
		}
		return resultadoCerraduraEpsilon;
	}
	
	/**
	 * Efectua la operacion mover al conjunto de estados dado
	 * @param HashSet<Estado> - Conjunto de estados al que se les aplicara la operacion mover
	 * @param Character - Simbolo con el que se evaluara si es posible la transicion
	 * @return HashSet<Estado> - Conjunto de estados resultante de la operacion mover
	 * */
	public  HashSet<Estado> mover(HashSet<Estado> conjuntoEstados,Character simbolo){
		HashSet<Estado> conjuntoEstadosAlcanzables = new HashSet<Estado>();
		for(Estado estado:conjuntoEstados) {
			for(Transicion transicion:estado.getTransiciones()) {
				if(!transicion.isEpsilon()) {
					ciclochar:
					for(char c = transicion.getCaracterDeTransicionInicial().charValue();c<=transicion.getCaracterDeTransicionFinal().charValue();c++) {
						if(simbolo.charValue() == c) {
							conjuntoEstadosAlcanzables.add(transicion.getEstadoSiguiente());
							break ciclochar;
						}
					}
				}
			}
		}
		return conjuntoEstadosAlcanzables;
	}
	/**
	 * Efectua la operacion ir a al conjunto de estados dado
	 * @param HashSet<Estado> - Conjunto de estados al que se les aplicara la operacion ir a
	 * @param Character - Simbolo con el que se evaluara 
	 * @return HashSet<Estado> - Conjunto de estados resultante de la operacion ir a
	 * */
	public  HashSet<Estado> irA(HashSet<Estado> conjuntoEstados,Character simbolo){
		HashSet<Estado> conjuntoEstadosAlcanzables = new HashSet<Estado>();
		HashSet<Estado> resultadoMover = mover(conjuntoEstados, simbolo);
		for(Estado estado:resultadoMover) {
			for(Estado estadoCerrEpsilon:cerraduraEpsilon(estado)) {
				conjuntoEstadosAlcanzables.add(estadoCerrEpsilon);
			}
			
		}
		return conjuntoEstadosAlcanzables;
	}
	
	/**
	 * Pone el token al estado final del automata, este metodo considera que el automata fue
	 * construido usando el metodo de Thompson, por lo cual va a considerar un solo estado final, aun asi 
	 * pondra el token a todos los estados finales del automata si este no fuera construido segun el 
	 * modelo de Thompson 
	 * 
	 * @param int token - Token que se asignara al estado final del automata
	 * */
	public void setTokenAutomata(int token) {
		for(Estado estadoFinal:this.estadosFinales) {
			estadoFinal.setToken(token);
		}
	}
	
	/**
	 * Pone la clase lexica al estado final del automata, este metodo considera que el automata fue
	 * construido usando el metodo de Thompson, por lo cual va a considerar un solo estado final, aun asi 
	 * pondra la clase lexica a todos los estados finales del automata si este no fuera construido segun el 
	 * modelo de Thompson 
	 * 
	 * @param String claseLexica - Clase lexica que se asignara al estado final del automata
	 * */
	public void setClaseLexicaAutomata(String claseLexica) {
		for(Estado estadoFinal:this.estadosFinales) {
			estadoFinal.setClaseLexica(claseLexica);
		}
	}
	
	/**
	 * Genera las transiciones epsilon necesarias crear un AFN para un analizador lexico es decir, 
	 * generara un nuevo estado inicial, y creara transiciones epsilon a los estados iniciales de cada uno de los 
	 * AutomatasNoDeterministas que se envien como parametro
	 * 
	 * Es importante recalcar que este automata en la mayoria de los casos NO cumplira con las especificaciones del modelo de Thompson
	 * por lo cual, cualquier operacion efectuada sobre este puede ser incorrecta
	 * 
	 * Asi mismo, se rrecuerda que si se llamara a la operacion setTokenAutomata de el AutomataNoDeterminista que genera este metodo 
	 * todos los estados finales de este automata tendrian el mismo token
	 * 
	 * Por ultimo, cabe decir que los tokens de cada automata de entrada seran respetados en su respectivo estado final
	 * 
	 * @param LinkedList<AutomataNoDeterminista> automatas - Lista con los automatas a los que se les aplicara la union especial para el analizador lexico
	 * @return AutomataNoDeterminista - Nuevo automata no determinista resultado de la union especial para analziador lexico de los automatas enviados como parametro
	 * */
	public static AutomataNoDeterminista unirAutomatasAnalizadorLexico(LinkedList<AutomataNoDeterminista> automatas) {
		 
		LinkedList<Transicion> transiciones = new LinkedList<Transicion>();
		HashSet<Estado> estados = new HashSet<Estado>();
		HashSet<Estado> estadosFinales = new HashSet<Estado>();
		LinkedList<Character> alfabeto = new LinkedList<Character>();
		
		for(AutomataNoDeterminista afn:automatas) {
			Transicion transicion = new Transicion(afn.getEstadoInicial());
			afn.getEstadoInicial().setEstadoInicial(false);
			estados.addAll(afn.getEstados());
			estadosFinales.addAll(afn.getEstadosFinales());
			transiciones.add(transicion);
			for(Character simbolo:afn.getAlfabeto()) {
				if(!alfabeto.contains(simbolo)) {
					alfabeto.add(simbolo);
				}
			}
		}
		Estado nuevoEstadoInicial = new Estado(true,false,transiciones);
		estados.add(nuevoEstadoInicial);
		return new AutomataNoDeterminista(estados, alfabeto, estadosFinales, nuevoEstadoInicial);
	}
	
	public boolean analizaCadena(String cadena) {
		HashSet<Estado> s=this.cerraduraEpsilon(this.getEstadoInicial());
		Iterator<Estado> s2;
		int i=0;
		while(i<cadena.length()) {
			s=this.irA(s,cadena.charAt(i));
			if(s.isEmpty()) {
				return false;
			}
			i++;
		}
		s2=s.iterator();
		do {
			if(s2.next().isEstadoFinal()) {
				return true;
			}
		}while(s2.hasNext());
		return false;
	}
}

