/**
 * Mc Compiler                              |v1.0
 * @author Miguel Angel Chaves
 * 
 * @desc: Compilador para la asignatura de Compiladores 20-1.
 * 
 * --- LOG DE VERSIONES ---
 * v1.0     | Creación de Automatas AFN
 *          | Operaciones: Union, Concatenación, Cerraduras
 *          | Conversión de AFN a AFD
 */
package Utilities;
import Vistas.*;
import AFN.AFN;
import java.util.LinkedList;
public class main {
    public static void main(String[] args) {
        System.out.println("-------------------------------------------------");
        System.out.println("    Mc. Compiler                    |   v1.0     ");
        System.out.println("-------------------------------------------------");
        //Leer AFN en archivos
        LinkedList<AFN> ConjuntoAFN = new LinkedList<> ();
        LinkedList<AFN> ConjuntoAFN_AFD = new LinkedList<> ();
        //Invocar al MenuPrincipal
        MenuPrincipal m = new MenuPrincipal(ConjuntoAFN, ConjuntoAFN_AFD);
        m.setVisible(true);
    }
}
