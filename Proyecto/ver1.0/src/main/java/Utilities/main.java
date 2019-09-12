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
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.LinkedList;
public class main {
    
    static File f = new File("data.obj");
    
    public static void main(String[] args) {
        System.out.println("-------------------------------------------------");
        System.out.println("    Mc. Compiler                    |   v1.0     ");
        System.out.println("-------------------------------------------------");
        
        LinkedList<AFN> ConjuntoAFN = new LinkedList<> ();
        LinkedList<AFN> ConjuntoAFN_AFD = new LinkedList<> ();
        //Leer AFN en archivo
        ConjuntoAFN_AFD = leerArchivo();
        //Invocar al MenuPrincipal
        MenuPrincipal m = new MenuPrincipal(ConjuntoAFN, ConjuntoAFN_AFD);
        m.setVisible(true);
    }

    private static LinkedList<AFN> leerArchivo(){
        LinkedList<AFN> aux = new LinkedList<> ();
        try{
            if (f.createNewFile()) {
                System.out.println("File created: " + f.getName());
            } else {
                System.out.println("File already exists.");
            }
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(f));
            Object obj;
            while((obj = ois.readObject()) instanceof AFN) {
                aux.add((AFN)obj);
            }
        }catch (EOFException e) {
            return aux;
        }catch (Exception e) {
            e.printStackTrace(); // otras excepciones que no son fin de archivo
        }
        return aux;
    }
}
