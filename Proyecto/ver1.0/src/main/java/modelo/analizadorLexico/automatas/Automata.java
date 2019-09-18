package modelo.analizadorLexico.automatas;

import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedList;

/**
 * Esta clase abstracta nos sirve para definir las operaciones y atributos principales para cualquier tipo de automata
 * */
public abstract class Automata {
	
	protected LinkedList<Character> alfabeto;
	protected HashSet<Estado> estados;
	protected HashSet<Estado> estadosFinales;
	protected Estado estadoInicial;
	
	/**
	 * Este contructor crea un Automata de acuerdo a lo establecido en los parametros
	 * @param HashSet<Estado> estados - Estados del automata
	 * @param LinkedList<Character> alfabeto - Alfabeto valido en el automata
	 * @param HashSet<Estado> estadosFinales - Estados finales
	 * @param Estado estadoInicial - Estado inicial del automata
	 * */
	public Automata(HashSet<Estado> estados, LinkedList<Character> alfabeto,HashSet<Estado> estadosFinales, Estado estadoInicial) {
		super();
		this.estados = estados;
		if(alfabeto == null) {
			this.alfabeto = new LinkedList<Character>();
		}else {
			this.alfabeto=alfabeto;
		}
		this.estadosFinales = estadosFinales;
		this.estadoInicial = estadoInicial;
	}
	
	public HashSet<Estado> getEstados() {
		return estados;
	}
	
	public LinkedList<Character> getAlfabeto() {
		return alfabeto;
	}
	
	public HashSet<Estado> getEstadosFinales() {
		return estadosFinales;
	}
	
	public Estado getEstadoInicial() {
		return estadoInicial;
	}
	
}	
