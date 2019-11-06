package modelo.analizadorSintactico.descendentes.descensoRecursivo;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;

import modelo.analizadorLexico.automatas.AutomataDeterminista;
import modelo.analizadorLexico.automatas.AutomataNoDeterminista;
import modelo.analizadorLexico.AnalizadorLexico;
import modelo.analizadorLexico.TokensAnalizadorLexicoExpReg;

public class AnalizadorSintacticoExpReg {
	private AnalizadorLexico analizadorLexico;
	private LinkedList <AutomataNoDeterminista> automatas =new LinkedList<AutomataNoDeterminista>();

	public AnalizadorSintacticoExpReg(String expresionRegular) {
			creaAnalizadorLexicoExpresionesRegulares();
			analizadorLexico.setCadenaAnalizar(expresionRegular);
	}
	
	public AutomataNoDeterminista creaAutomataNoDeterministaPorExpReg() {
		if(E()) {
			return automatas.get(0);
		}
		return null;
	}
	
	private boolean E() {
		if(T()) {
			if(Ep()) {
				return true;
			}
		}
		return false;
	}
	
	private boolean Ep() {
		int token = analizadorLexico.getToken();
		if(token==TokensAnalizadorLexicoExpReg.OR) {
			if(T()) {
				automatas.set(automatas.size()-2,automatas.get(automatas.size()-2).unirAutomata(automatas.get(automatas.size()-1)));
				automatas.remove(automatas.size()-1);
				if(Ep()) {
					return true;
				}
			}
			return false;
		}
		analizadorLexico.regresaToken();
		return true;
	}
	
	private boolean T() {
		if(C()) {
			if(Tp()) {
				return true;
			}
		}
		return false;
	}
	
	private boolean Tp() {
		int token=analizadorLexico.getToken();
		if(token==TokensAnalizadorLexicoExpReg.CONCATENAR) {
			if(C()) {
				automatas.set(automatas.size()-2,automatas.get(automatas.size()-2).concatenarAutomata(automatas.get(automatas.size()-1)));
				automatas.remove(automatas.size()-1);
				if(Tp()) {
					return true;
				}
			}
			return false;
		}
		analizadorLexico.regresaToken();
		return true;
	}
	
	private boolean C() {
		if(F()) {
			if(Cp()) {
				return true;
			}
		}
		return true;
	}
	
	private boolean Cp() {
		int token=analizadorLexico.getToken();
		switch(token){
			case TokensAnalizadorLexicoExpReg.CERRADURA_POSISTIVA:
				automatas.set(automatas.size()-1,automatas.get(automatas.size()-1).cerraduraPositiva());
				if(Cp()) {
					return true;
				}
			case TokensAnalizadorLexicoExpReg.CERRADURA_KLEEN:
				automatas.set(automatas.size()-1,automatas.get(automatas.size()-1).cerraduraKleen());
				if(Cp()) {
					return true;
				}
			case TokensAnalizadorLexicoExpReg.OPCIONAL:
				automatas.set(automatas.size()-1,automatas.get(automatas.size()-1).aplicarOperacionOpcional());
				if(Cp()) {
					return true;
				}
		}
		analizadorLexico.regresaToken();
		return true;
	}
	
	public boolean F() {
		int token=analizadorLexico.getToken();
		switch(token){
			case TokensAnalizadorLexicoExpReg.PARENTESIS_I:
				if(E()) {
					token=analizadorLexico.getToken();
					if(token == TokensAnalizadorLexicoExpReg.PARENTESIS_D) {
						return true;
					}
				}
				return false;
			case TokensAnalizadorLexicoExpReg.SIMBOLO:
				/*if(analizadorLexico.getLexemaActual().startsWith("\\\\")) {
					if(analizadorLexico.getLexemaActual().length() == 3) {
						AutomataNoDeterminista nuevoAutomata =new AutomataNoDeterminista('\\',null);
						nuevoAutomata = nuevoAutomata.concatenarAutomata(new AutomataNoDeterminista(analizadorLexico.getLexemaActual().charAt(2),null)); //Caso donde se desea incluir el caracter de escape dentro del nuavo analizador lexico es decir \ algo
						automatas.add(nuevoAutomata);
					}else {
						AutomataNoDeterminista nuevoAutomata =new AutomataNoDeterminista(analizadorLexico.getLexemaActual().charAt(1),null); //Caso donde se desea agregar \ unicamente
						automatas.add(nuevoAutomata);
					}
					
				}else*/
				if(analizadorLexico.getLexemaActual().startsWith("\\")) {
					AutomataNoDeterminista nuevoAutomata =new AutomataNoDeterminista(analizadorLexico.getLexemaActual().charAt(1),null);
					automatas.add(nuevoAutomata);
				}else{
					AutomataNoDeterminista nuevoAutomata =new AutomataNoDeterminista(analizadorLexico.getLexemaActual().charAt(0),null);
					automatas.add(nuevoAutomata);
				}
				return true;
			case TokensAnalizadorLexicoExpReg.CORCHETE_I:
				token = analizadorLexico.getToken();
				if(token == TokensAnalizadorLexicoExpReg.SIMBOLO) {
					Character caracterInicialNuevoAutomata = analizadorLexico.getLexemaActual().charAt(0);
					token = analizadorLexico.getToken();
					if(token == TokensAnalizadorLexicoExpReg.COMA) {
						token = analizadorLexico.getToken();
						if(token == TokensAnalizadorLexicoExpReg.SIMBOLO) {
							Character caracterFinalNuevoAutomata = analizadorLexico.getLexemaActual().charAt(0);
							token = analizadorLexico.getToken();
							if(token == TokensAnalizadorLexicoExpReg.CORCHETE_D){
								AutomataNoDeterminista nuevoAutomata =new AutomataNoDeterminista(caracterInicialNuevoAutomata,caracterFinalNuevoAutomata,null);
								automatas.add(nuevoAutomata);
								return true;
							}
						}
					}
				}
				return false;
		}
		return false;
	}
	
	public static void escribirExpresionesRegularesArchivo(LinkedList<String[]> expresionesRegulares,String rutaArchivo,String separador) throws IOException{
		BufferedWriter writer = new BufferedWriter(new FileWriter(rutaArchivo));
	    //En la primera linea del archivo escribimos el separador que vamos a utilizar para despues poder leer el archivo sin problemas
	    writer.write(separador+"\n");
	    //Ahora escribimos la expresion regular al archivo
	    for(String [] expresionRegular:expresionesRegulares) {
	    	String lineaArchivo = expresionRegular[0] + separador + expresionRegular[1]+ separador+ expresionRegular[2];
	    	writer.write(lineaArchivo+"\n");
	    }
	    //Cerramos el archivo
	    writer.close();
	}
	
	public static AutomataDeterminista crearAFDAnalizadorLexicoConExpRegArchivo(String rutaArchivo) throws IOException{
		LinkedList<AutomataNoDeterminista> automatasLeidosArchivo = new LinkedList<AutomataNoDeterminista>();
		FileReader fileReader = new FileReader(rutaArchivo);
        BufferedReader buferedReader = new BufferedReader(fileReader);
        int linea = 0;
        String cadenaLeida;
        String separador = "";
        while((cadenaLeida = buferedReader.readLine())!=null) {
        	if(linea == 0) {
        		separador = cadenaLeida;
        	}else {
        		String expresionRegular []= cadenaLeida.split(separador);
        		AnalizadorSintacticoExpReg analizadorSintactico = new AnalizadorSintacticoExpReg(expresionRegular[0]);
        		AutomataNoDeterminista AFNExpresionRegular = analizadorSintactico.creaAutomataNoDeterministaPorExpReg();
        		AFNExpresionRegular.setTokenAutomata(Integer.valueOf(expresionRegular[1]));
        		AFNExpresionRegular.setClaseLexicaAutomata(expresionRegular[2]);
        		automatasLeidosArchivo.add(AFNExpresionRegular);
        	}
        	linea++;
        }
        buferedReader.close();
        return new AutomataDeterminista(AutomataNoDeterminista.unirAutomatasAnalizadorLexico(automatasLeidosArchivo));
	}
	
	private void creaAnalizadorLexicoExpresionesRegulares() {
		LinkedList<AutomataNoDeterminista> listaAutomatas = new LinkedList<AutomataNoDeterminista>();
		
		//Operadores de las Expresiones regulares
		
		AutomataNoDeterminista OR = new AutomataNoDeterminista('|', null);
		OR.setTokenAutomata(TokensAnalizadorLexicoExpReg.OR);
		listaAutomatas.add(OR);
		
		AutomataNoDeterminista CONCATENAR = new AutomataNoDeterminista('&', null);
		CONCATENAR.setTokenAutomata(TokensAnalizadorLexicoExpReg.CONCATENAR);
		listaAutomatas.add(CONCATENAR);
		
		AutomataNoDeterminista CERR_POS = new AutomataNoDeterminista('+', null);
		CERR_POS.setTokenAutomata(TokensAnalizadorLexicoExpReg.CERRADURA_POSISTIVA);
		listaAutomatas.add(CERR_POS);
		
		AutomataNoDeterminista CERR_KLEEN = new AutomataNoDeterminista('*', null);
		CERR_KLEEN.setTokenAutomata(TokensAnalizadorLexicoExpReg.CERRADURA_KLEEN);
		listaAutomatas.add(CERR_KLEEN);
		
		AutomataNoDeterminista OPC = new AutomataNoDeterminista('?', null);
		OPC.setTokenAutomata(TokensAnalizadorLexicoExpReg.OPCIONAL);
		listaAutomatas.add(OPC);
		
		AutomataNoDeterminista PAR_I = new AutomataNoDeterminista('(', null);
		PAR_I.setTokenAutomata(TokensAnalizadorLexicoExpReg.PARENTESIS_I);
		listaAutomatas.add(PAR_I);
		
		AutomataNoDeterminista PAR_D = new AutomataNoDeterminista(')', null);
		PAR_D.setTokenAutomata(TokensAnalizadorLexicoExpReg.PARENTESIS_D);
		listaAutomatas.add(PAR_D);
		
		AutomataNoDeterminista COR_I = new AutomataNoDeterminista('[', null);
		COR_I.setTokenAutomata(TokensAnalizadorLexicoExpReg.CORCHETE_I);
		listaAutomatas.add(COR_I);
		
		AutomataNoDeterminista COR_D = new AutomataNoDeterminista(']', null);
		COR_D.setTokenAutomata(TokensAnalizadorLexicoExpReg.CORCHETE_D);
		listaAutomatas.add(COR_D);

		AutomataNoDeterminista COMA = new AutomataNoDeterminista(',', null);
		COMA.setTokenAutomata(TokensAnalizadorLexicoExpReg.COMA);
		listaAutomatas.add(COMA);
		
		//Simbolos de los operadores de las expresiones regulares
		
		AutomataNoDeterminista SIMB10 = new AutomataNoDeterminista('\\', null);
		SIMB10 = SIMB10.concatenarAutomata(new AutomataNoDeterminista('|', null));
		SIMB10.setTokenAutomata(TokensAnalizadorLexicoExpReg.SIMBOLO);
		listaAutomatas.add(SIMB10);
		
		AutomataNoDeterminista SIMB11 = new AutomataNoDeterminista('\\', null);
		SIMB11 = SIMB11.concatenarAutomata(new AutomataNoDeterminista('&', null));
		SIMB11.setTokenAutomata(TokensAnalizadorLexicoExpReg.SIMBOLO);
		listaAutomatas.add(SIMB11);
		
		AutomataNoDeterminista SIMB4 = new AutomataNoDeterminista('\\', null);
		SIMB4 = SIMB4.concatenarAutomata(new AutomataNoDeterminista('+', null));
		SIMB4.setTokenAutomata(TokensAnalizadorLexicoExpReg.SIMBOLO);
		listaAutomatas.add(SIMB4);
		
		AutomataNoDeterminista SIMB6 = new AutomataNoDeterminista('\\', null);
		SIMB6 = SIMB6.concatenarAutomata(new AutomataNoDeterminista('*', null));
		SIMB6.setTokenAutomata(TokensAnalizadorLexicoExpReg.SIMBOLO);
		listaAutomatas.add(SIMB6);
		
		AutomataNoDeterminista SIMB12 = new AutomataNoDeterminista('\\', null);
		SIMB12 = SIMB12.concatenarAutomata(new AutomataNoDeterminista('?', null));
		SIMB12.setTokenAutomata(TokensAnalizadorLexicoExpReg.SIMBOLO);
		listaAutomatas.add(SIMB12);
		
		AutomataNoDeterminista SIMB7 = new AutomataNoDeterminista('\\', null);
		SIMB7 = SIMB7.concatenarAutomata(new AutomataNoDeterminista('(', null));
		SIMB7.setTokenAutomata(TokensAnalizadorLexicoExpReg.SIMBOLO);
		listaAutomatas.add(SIMB7);
		
		AutomataNoDeterminista SIMB8 = new AutomataNoDeterminista('\\', null);
		SIMB8 = SIMB8.concatenarAutomata(new AutomataNoDeterminista(')', null));
		SIMB8.setTokenAutomata(TokensAnalizadorLexicoExpReg.SIMBOLO);
		listaAutomatas.add(SIMB8);
		
		AutomataNoDeterminista SIMB17 = new AutomataNoDeterminista('\\', null);
		SIMB17 = SIMB17.concatenarAutomata(new AutomataNoDeterminista('[', null));
		SIMB17.setTokenAutomata(TokensAnalizadorLexicoExpReg.SIMBOLO);
		listaAutomatas.add(SIMB17);
		
		AutomataNoDeterminista SIMB18 = new AutomataNoDeterminista('\\', null);
		SIMB18 = SIMB18.concatenarAutomata(new AutomataNoDeterminista(']', null));
		SIMB18.setTokenAutomata(TokensAnalizadorLexicoExpReg.SIMBOLO);
		listaAutomatas.add(SIMB18);
		
		AutomataNoDeterminista SIMB16 = new AutomataNoDeterminista('\\', null);
		SIMB16 = SIMB16.concatenarAutomata(new AutomataNoDeterminista(',', null));
		SIMB16.setTokenAutomata(TokensAnalizadorLexicoExpReg.SIMBOLO);
		listaAutomatas.add(SIMB16);
		
		AutomataNoDeterminista SIMB32 = new AutomataNoDeterminista('\\', null);
		SIMB32 = SIMB32.concatenarAutomata(new AutomataNoDeterminista('\\', null));
		SIMB32.setTokenAutomata(TokensAnalizadorLexicoExpReg.SIMBOLO);
		listaAutomatas.add(SIMB32);

		AutomataNoDeterminista SIMB41 = new AutomataNoDeterminista('\\', null);
		SIMB41 = SIMB41.concatenarAutomata(new AutomataNoDeterminista('\\', null));
		SIMB41 = SIMB41.concatenarAutomata(new AutomataNoDeterminista(' ', null));
		SIMB41.setTokenAutomata(TokensAnalizadorLexicoExpReg.SIMBOLO);
		listaAutomatas.add(SIMB41);
		
		//Simbolos UNICODE
		
		AutomataNoDeterminista SIMB1 = new AutomataNoDeterminista('A','Z', null);
		SIMB1.setTokenAutomata(80);
		listaAutomatas.add(SIMB1);
		
		AutomataNoDeterminista SIMB2 = new AutomataNoDeterminista('a','z', null);
		SIMB2.setTokenAutomata(TokensAnalizadorLexicoExpReg.SIMBOLO);
		listaAutomatas.add(SIMB2);
		
		AutomataNoDeterminista SIMB3 = new AutomataNoDeterminista('0','9', null);
		SIMB3.setTokenAutomata(TokensAnalizadorLexicoExpReg.SIMBOLO);
		listaAutomatas.add(SIMB3);
		
		AutomataNoDeterminista SIMB9 = new AutomataNoDeterminista('/', null);
		SIMB9.setTokenAutomata(TokensAnalizadorLexicoExpReg.SIMBOLO);
		listaAutomatas.add(SIMB9);
		
		AutomataNoDeterminista SIMB5 = new AutomataNoDeterminista('-', null);
		SIMB5.setTokenAutomata(TokensAnalizadorLexicoExpReg.SIMBOLO);
		listaAutomatas.add(SIMB5);
		
		AutomataNoDeterminista SIMB19 = new AutomataNoDeterminista('.', null);
		SIMB19.setTokenAutomata(TokensAnalizadorLexicoExpReg.SIMBOLO);
		listaAutomatas.add(SIMB19);
		
		AutomataNoDeterminista SIMB22 = new AutomataNoDeterminista('^', null);
		SIMB22.setTokenAutomata(TokensAnalizadorLexicoExpReg.SIMBOLO);
		listaAutomatas.add(SIMB22);
		
		AutomataNoDeterminista SIMB23 = new AutomataNoDeterminista('_', null);
		SIMB23.setTokenAutomata(TokensAnalizadorLexicoExpReg.SIMBOLO);
		listaAutomatas.add(SIMB23);
		
		AutomataNoDeterminista SIMB24 = new AutomataNoDeterminista('}', null);
		SIMB24.setTokenAutomata(TokensAnalizadorLexicoExpReg.SIMBOLO);
		listaAutomatas.add(SIMB24);
		
		AutomataNoDeterminista SIMB25 = new AutomataNoDeterminista('{', null);
		SIMB25.setTokenAutomata(TokensAnalizadorLexicoExpReg.SIMBOLO);
		listaAutomatas.add(SIMB25);
		
		AutomataNoDeterminista SIMB26 = new AutomataNoDeterminista('@', null);
		SIMB26.setTokenAutomata(TokensAnalizadorLexicoExpReg.SIMBOLO);
		listaAutomatas.add(SIMB26);
		
		AutomataNoDeterminista SIMB27 = new AutomataNoDeterminista('$', null);
		SIMB27.setTokenAutomata(TokensAnalizadorLexicoExpReg.SIMBOLO);
		listaAutomatas.add(SIMB27);
		
		AutomataNoDeterminista SIMB28 = new AutomataNoDeterminista('#', null);
		SIMB28.setTokenAutomata(TokensAnalizadorLexicoExpReg.SIMBOLO);
		listaAutomatas.add(SIMB28);
		
		AutomataNoDeterminista SIMB29 = new AutomataNoDeterminista(' ', null);
		SIMB29.setTokenAutomata(TokensAnalizadorLexicoExpReg.SIMBOLO);
		listaAutomatas.add(SIMB29);
		
		AutomataNoDeterminista SIMB30 = new AutomataNoDeterminista('∈', null);
		SIMB30.setTokenAutomata(TokensAnalizadorLexicoExpReg.SIMBOLO);
		listaAutomatas.add(SIMB30);
		
		AutomataNoDeterminista SIMB31 = new AutomataNoDeterminista('>', null);
		SIMB31.setTokenAutomata(TokensAnalizadorLexicoExpReg.SIMBOLO);
		listaAutomatas.add(SIMB31);
		
		AutomataNoDeterminista SIMB34 = new AutomataNoDeterminista(';', null);
		SIMB34.setTokenAutomata(TokensAnalizadorLexicoExpReg.SIMBOLO);
		listaAutomatas.add(SIMB34);
		AutomataNoDeterminista SIMB33 = new AutomataNoDeterminista('\'', null);
		SIMB33.setTokenAutomata(TokensAnalizadorLexicoExpReg.SIMBOLO);
		listaAutomatas.add(SIMB33);
		
		AutomataNoDeterminista SIMB35 = new AutomataNoDeterminista('>', null);
		SIMB35.setTokenAutomata(TokensAnalizadorLexicoExpReg.SIMBOLO);
		listaAutomatas.add(SIMB35);
		AutomataNoDeterminista SIMB36 = new AutomataNoDeterminista('%', null);
		SIMB36.setTokenAutomata(TokensAnalizadorLexicoExpReg.SIMBOLO);
		listaAutomatas.add(SIMB36);
		AutomataNoDeterminista SIMB37 = new AutomataNoDeterminista('~', null);
		SIMB37.setTokenAutomata(TokensAnalizadorLexicoExpReg.SIMBOLO);
		listaAutomatas.add(SIMB37);
		AutomataNoDeterminista SIMB38 = new AutomataNoDeterminista('`', null);
		SIMB38.setTokenAutomata(TokensAnalizadorLexicoExpReg.SIMBOLO);
		listaAutomatas.add(SIMB38);
		
		AutomataNoDeterminista SIMB39 = new AutomataNoDeterminista('=', null);
		SIMB39.setTokenAutomata(TokensAnalizadorLexicoExpReg.SIMBOLO);
		listaAutomatas.add(SIMB39);
		AutomataNoDeterminista SIMB40 = new AutomataNoDeterminista('!', null);
		SIMB40.setTokenAutomata(TokensAnalizadorLexicoExpReg.SIMBOLO);
		listaAutomatas.add(SIMB40);

		analizadorLexico = new AnalizadorLexico(listaAutomatas);
	}
}