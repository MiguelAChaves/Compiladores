package modelo.analizadorSintactico.descendentes.descensoRecursivo;

import modelo.analizadorLexico.AnalizadorLexico;
import modelo.analizadorLexico.automatas.AutomataNoDeterminista;

import java.util.LinkedList;

public class AnalizadorSintacticoGramaticas {

    private LinkedList<LinkedList<String>> gramatica;
    private LinkedList<String> regla;
    private String reglaActual;
    private AnalizadorLexico analizadorLexico;
    private int contador=0;
   
    public AnalizadorSintacticoGramaticas(String cadenaAnalizar) {
    	creaAnalizadorLexicoGramaticas();
    	analizadorLexico.setCadenaAnalizar(cadenaAnalizar);
        gramatica = new LinkedList<LinkedList<String>>();
        regla = new LinkedList<String>();
        regla.add("");
        reglaActual = "";
    }

    public LinkedList<LinkedList<String>> generaGramatica() {
        if(G()){
            return gramatica;
        }
        return null;
    }

    private boolean G() {
        if (listaReglas()) {
            return true;
        }
        return false;
    }

    private boolean listaReglas() {
        int tok = 0;

        if (regla()) {
            tok=analizadorLexico.getToken();
            //Token del ;=10
            if (tok == 10) {
                regla.add("");
                if (listaReglasP()) {

                    return true;
                }
            }
        }
        return false;
    }

    private boolean listaReglasP() {
        int tok = 0,recuerda=contador;
        //recordar el estado del scanner antes de proceder
        if (regla()) {
            tok=analizadorLexico.getToken();
            //token del ;: 10
            if (tok == 10) {
                regla.add("");
                if (listaReglasP()) {
                    return true;
                }
            }
            return false;
        }
        this.contador=recuerda;
        //regresar el scanner al estado original
        return true;
    }

    private boolean regla() {
        int tok = 0;
        if (ladoIzquierdo()) {
            tok=analizadorLexico.getToken();
            //token del '-''>': 20
            if (tok == 20) {
                if (listaLadosDerechos()) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean ladoIzquierdo() {
        int tok = analizadorLexico.getToken();
        //token del simbolo:40
        if (tok == 40) {
            reglaActual = analizadorLexico.getLexemaActual().replaceAll(" ","");//Este replace puede causar problemas, si se incluyera una gramatica que involucra al espacio como simbolo terminal
            regla.set(regla.size() - 1, reglaActual);
            return true;
        }
        return false;
    }

    private boolean listaLadosDerechos() {
        int tok = 0;
        if (ladoDerecho()) {
            tok=analizadorLexico.getToken();
            //token del OR(|):30
            //Se agrega un nuevo renglón que correspondería a un lado Abajo de la regla actual 
            gramatica.add((LinkedList<String>) regla.clone());
            regla.clear();
            if (tok == 30) {
                regla.add(reglaActual);
                if (listaLadosDerechos()) {
                    return true;
                }
            }
            analizadorLexico.regresaToken();
            this.contador--;
            return true;
        }
        return false;
    }

    private boolean ladoDerecho() {
        if (listaSimbolos()) {
            return true;
        }
        return true;
    }

    private boolean listaSimbolos() {
        int tok = analizadorLexico.getToken(),recuerda;
        //Token del Simbolo:40
        if (tok == 40) {
            //recordar el estado actual del scanner
            recuerda=this.contador;
            regla.add(analizadorLexico.getLexemaActual().replaceAll(" ",""));//Este replace puede causar problemas, si se incluyera una gramatica que involucra al espacio como simbolo terminal
            if (listaSimbolos()) {
                return true;
            }
            analizadorLexico.regresaToken();
            this.contador=recuerda;
            return true;
        }
        return false;
    }
    
    private void creaAnalizadorLexicoGramaticas() {
    	LinkedList<AutomataNoDeterminista> automatas = new LinkedList<AutomataNoDeterminista>();

    	AutomataNoDeterminista a1 = new AnalizadorSintacticoExpReg(";").creaAutomataNoDeterministaPorExpReg();
    	a1.setTokenAutomata(10);
    	a1.setClaseLexicaAutomata("PUNTOYCOMA");
    	automatas.add(a1);
    	AutomataNoDeterminista a2 = new AnalizadorSintacticoExpReg("-&>").creaAutomataNoDeterministaPorExpReg();
    	a2.setTokenAutomata(20);
    	a2.setClaseLexicaAutomata("FLECHA");
    	automatas.add(a2);
    	AutomataNoDeterminista a3 = new AnalizadorSintacticoExpReg("\\|").creaAutomataNoDeterministaPorExpReg();
    	a3.setTokenAutomata(30);
    	a3.setClaseLexicaAutomata("OR");
    	automatas.add(a3); 
    	AutomataNoDeterminista a4 = new AnalizadorSintacticoExpReg(" *&([A,Z]|[a,z]|\\+|-|\\(|\\)|[0,9]|\\[|\\]|\\*|\\&|\\?|\\,|/|.|@|_|#|{|}|∈|'|^|%|!|=|\\\\)+& +").creaAutomataNoDeterministaPorExpReg();
    	a4.setTokenAutomata(40);
    	a4.setClaseLexicaAutomata("SIMBOLO");
    	automatas.add(a4);
    	this.analizadorLexico = new AnalizadorLexico(automatas);
    }
}
