package modelo.analizadorLexico.automatas;

import java.util.LinkedList;

/**
 * Clase que nos sirve para modelar un estado de un automata
 * */
public class Estado {
	private static long UUID_ESTADOS = 0L;
	
	private boolean estadoInicial;
	private boolean estadoFinal;
	private LinkedList<Transicion> transiciones;
	private int token;
	private String claseLexica;
	private long id;
	
	/**
	 * Crea un nuevo estado para un automata, el id se asigna de manera automatica por el programa
	 * 
	 * @param boolean estadoInicial - Indica si el estado es un estado inicial
	 * @param boolean estadoFinal - Indica si el estado es un estado de aceptacion o final
	 * @LinkedList<Transicion> transiciones - Lista con las transiciones que presenta el estado
	 * */
	public Estado(boolean estadoInicial, boolean estadoFinal, LinkedList<Transicion> transiciones) {
		super();
		this.estadoInicial = estadoInicial;
		this.estadoFinal = estadoFinal;
		this.transiciones = transiciones;
		this.token=-1;
		this.id = UUID_ESTADOS;
		UUID_ESTADOS++;
	}
	
	public boolean isEstadoInicial() {
		return estadoInicial;
	}
	
	public void setEstadoInicial(boolean estadoInicial) {
		this.estadoInicial = estadoInicial;
	}

	public void setEstadoFinal(boolean estadoFinal) {
		this.estadoFinal = estadoFinal;
	}

	public boolean isEstadoFinal() {
		return estadoFinal;
	}
	public LinkedList<Transicion> getTransiciones() {
		return transiciones;
	}
	
	public long getId() {
		return id;
	}
	
	public int getToken() {
		return token;
	}

	public void setToken(int token) {
		this.token = token;
	}
	
	public String getClaseLexica() {
		return this.claseLexica;
	}
	
	public void setClaseLexica(String claseLexica){
		this.claseLexica=claseLexica;
	}
}
