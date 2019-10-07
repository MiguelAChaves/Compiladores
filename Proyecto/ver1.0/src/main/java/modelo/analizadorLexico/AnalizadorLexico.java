package modelo.analizadorLexico;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.Stack;

import modelo.analizadorLexico.automatas.AutomataDeterminista;
import modelo.analizadorLexico.automatas.AutomataNoDeterminista;
import modelo.analizadorLexico.automatas.Estado;
import modelo.analizadorLexico.automatas.Transicion;


public class AnalizadorLexico implements Serializable{
	
	public static final int ERROR = -1;
	public static final int CADENA_TERMINADA = 0;
	
	private AutomataDeterminista automataAnalizadorLexico;
	private Stack<Integer> indicesAceptacionPasados = new Stack<Integer>();
	private String cadenaAnalizar;
	private String lexemaActual;
	private int indiceActual;
	private int tokenActual;
	private String claseLexicaActual;
	
	public AnalizadorLexico (LinkedList<AutomataNoDeterminista> automatasParaAnalizador) {
		automataAnalizadorLexico = new AutomataDeterminista(AutomataNoDeterminista.unirAutomatasAnalizadorLexico(automatasParaAnalizador));
	}
	public AnalizadorLexico(AutomataDeterminista automata) {
		automataAnalizadorLexico = automata;
	}
	
	public String getCadenaAnalizar() {
		return cadenaAnalizar;
	}
	public void setCadenaAnalizar(String cadenaAnalizar) {
		indicesAceptacionPasados.clear();
		this.cadenaAnalizar = cadenaAnalizar;
	}
	
	public String getLexemaActual() {
		return lexemaActual;
	}

	public void regresaToken() {
		indiceActual = indicesAceptacionPasados.pop();
	}
	
	public int getToken() {
		if(indicesAceptacionPasados.isEmpty()) {
			indiceActual = indicesAceptacionPasados.push(0);
		}else {
			indiceActual = indicesAceptacionPasados.peek();
		}
		if(indiceActual>=cadenaAnalizar.length()) {
			claseLexicaActual = "";
			lexemaActual="";
			return CADENA_TERMINADA;
		}else {
			Estado estadoActual=automataAnalizadorLexico.getEstadoInicial();
			tokenActual = ERROR;
			claseLexicaActual = "";
			lexemaActual="";
			while(indiceActual<cadenaAnalizar.length()) {
				if(AutomataDeterminista.obtenerTransicion(estadoActual,cadenaAnalizar.charAt(indiceActual))!=null) {
					//Seguimos la transicion
					estadoActual=AutomataDeterminista.obtenerTransicion(estadoActual,cadenaAnalizar.charAt(indiceActual));
					lexemaActual+=cadenaAnalizar.charAt(indiceActual);
					if(estadoActual.isEstadoFinal()) {
						tokenActual=estadoActual.getToken();
						claseLexicaActual=estadoActual.getClaseLexica();
						indiceActual++;
					}else {
						indiceActual++;
						tokenActual=ERROR;
					}
				}else{
					//Ya no hay transicion
					break;
				}
			}
			
			if(tokenActual != ERROR) {
				indicesAceptacionPasados.push(indiceActual);
				return tokenActual;
			}else if(indiceActual>=cadenaAnalizar.length()){
				return CADENA_TERMINADA;
			}else {
				return ERROR;
			}
		}

	}
	
	public String getClaseLexicaActual() {
		return this.claseLexicaActual;
	}
	
	
}