package modelo.analizadorLexico.automatas;
/**
 * Clase que nos sirve para modelar una transicon de un estado a otro para un automata
 * */
public class Transicion {

	private Character caracterDeTransicionInicial;
	private Character caracterDeTransicionFinal;
	private Estado estadoSiguiente;
	private boolean epsilon;
	
	/**
	 * Este contructor nos ayuda a crear una transicion simple con un caracter a otro estado
	 * @param Character caracterDeTransicion - Caracter con el que se hara la transicion 
	 * @param Estado estadoSiguiente - Estado al que se llegara con la transcion
	 * */
	public Transicion(Character caracterDeTransicion, Estado estadoSiguiente) {
		super();
		this.epsilon = false;
		this.caracterDeTransicionInicial = caracterDeTransicion;
		this.caracterDeTransicionFinal = caracterDeTransicion;
		this.estadoSiguiente = estadoSiguiente;
	}
	
	/**
	 * Este contructor nos ayuda a crear una transicion y establecer un rango de caracteres en el cual la transicion es valida
	 * @param Character caracterDeTransicionInicial - Caracter de menor valor segun UTF-8 o ASCII con el que se hara la transicion 
	 * @param Character caracterDeTransicionInicial - Caracter de mayor valor segun UTF-8 o ASCII con el que se hara la transicion
	 * @param Estado estadoSiguiente - Estado al que se llegara con la transcion
	 * */
	public Transicion(Character caracterDeTransicionInicial,Character caracterDeTransicionFinal, Estado estadoSiguiente) {
		super();
		this.epsilon = false;
		this.caracterDeTransicionInicial = caracterDeTransicionInicial;
		this.caracterDeTransicionFinal = caracterDeTransicionFinal;
		this.estadoSiguiente = estadoSiguiente;
	}
	
	/**
	 * Este contructor nos ayuda a crear una transicion epsilon a otro estado
	 * @param Estado estadoSiguiente - Estado al que se llegara con la transcion
	 * */
	public Transicion(Estado estadoSiguiente) {
		super();
		this.epsilon = true;
		this.estadoSiguiente = estadoSiguiente;
	}
	
	public Character getCaracterDeTransicionInicial() {
		return caracterDeTransicionInicial;
	}

	public Character getCaracterDeTransicionFinal() {
		return caracterDeTransicionFinal;
	}
	
	public Estado getEstadoSiguiente() {
		return estadoSiguiente;
	}
	
	public boolean isEpsilon() {
		return epsilon;
	}
}