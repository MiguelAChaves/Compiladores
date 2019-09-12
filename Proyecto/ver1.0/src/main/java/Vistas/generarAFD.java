/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vistas;

import AFN.AFN;
import AFN.Estado;
import static AFN.Estado.cerraduraEpsilon;
import static AFN.Estado.ir_A;
import static AFN.Estado.mover;
import Utilities.Conjunto;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.atomic.AtomicInteger;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Migue
 */
public class generarAFD extends javax.swing.JFrame {

    private LinkedList<AFN> conjuntoAFD;
    private int idProc = 0;
    private MenuPrincipal m = new MenuPrincipal(conjuntoAFD, idProc);
    DefaultTableModel modelo;
    /**
     * Creates new form MostrarAFN
     * @param conjuntoAFD
     * @param m
     */
    public generarAFD(LinkedList<AFN> conjuntoAFD, MenuPrincipal m) {
        initComponents();
        this.setTitle("GenerarAFD");
        this.m = m;
        this.conjuntoAFD = conjuntoAFD;
        /* Se llena el comboBox */
        for(int i=0; i<conjuntoAFD.size(); i++){
            cbAut_AFD.addItem("Autómata " + i);
        }
        
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jButton1 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblAFN = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        cbAut_AFD = new javax.swing.JComboBox<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jButton1.setText("Regresar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        tblAFN.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jScrollPane1.setViewportView(tblAFN);

        jLabel1.setText("Automata a Convertir");

        cbAut_AFD.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbAut_AFDActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(253, 253, 253)
                        .addComponent(jButton1))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 560, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(18, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(26, 26, 26)
                .addComponent(cbAut_AFD, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(186, 186, 186))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(cbAut_AFD, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 376, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 32, Short.MAX_VALUE)
                .addComponent(jButton1)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        this.setVisible(false);
        m.setVisible(true);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void cbAut_AFDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbAut_AFDActionPerformed
        int i = -1;
        if(cbAut_AFD.getSelectedItem()!=null){
            for(int n=0; n<conjuntoAFD.size();n++){
                if(cbAut_AFD.getSelectedItem().equals("Autómata " + n))
                    i = n;
            }
        }
        
        modelo = new DefaultTableModel();
        tblAFN.setModel(modelo);
        modelo.addColumn("Estado");
        for(Character x : conjuntoAFD.get(i).getAlfabeto()){
            modelo.addColumn(x);
        }
        modelo.addColumn("Aceptación");
        modelo.addColumn("Token");
        
        crearAFN(i);
        
    }//GEN-LAST:event_cbAut_AFDActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> cbAut;
    private javax.swing.JComboBox<String> cbAut1;
    private javax.swing.JComboBox<String> cbAut2;
    private javax.swing.JComboBox<String> cbAut3;
    private javax.swing.JComboBox<String> cbAut4;
    private javax.swing.JComboBox<String> cbAut5;
    private javax.swing.JComboBox<String> cbAut6;
    private javax.swing.JComboBox<String> cbAut7;
    private javax.swing.JComboBox<String> cbAut8;
    private javax.swing.JComboBox<String> cbAut_AFD;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblAFN;
    // End of variables declaration//GEN-END:variables

    private void crearAFN(int i) {
        int id=0;
        //Generar Cerradura Epsilon del estado Inicial
        Conjunto S_0 = new Conjunto(id, cerraduraEpsilon(conjuntoAFD.get(i).getEstadoInicial()));
        //Se crea una Cola
        Queue<Conjunto> SConjunto = new LinkedList<Conjunto>();
        ArrayList<Conjunto> TotalConjunto = new ArrayList<Conjunto>();
        //Se ingresa a la cola
        SConjunto.offer(S_0);
        TotalConjunto.add(S_0);
        /* Mientras la cola no este vacia*/
        while(!SConjunto.isEmpty()){
            /* Se saca el elemento de la pila*/
            Conjunto aux = SConjunto.poll();
            System.out.println("------------- Estado " + aux.getId() + "---------------------");
            /* Para cada elemento del alfabeto*/
            for(Character x : conjuntoAFD.get(i).getAlfabeto()){
                int id_temp = -1;
                /* Se hace el ir_a del elemento*/
                HashSet<Estado> auxEdo = new HashSet<Estado>();
                auxEdo = ir_A(aux.getEstados(),x);
                /* Si el Ir_A es vacio, no hay transicion -1*/
                if(auxEdo.isEmpty()){
                    System.out.println(x + "= -1");
                }else{
                    for(Conjunto c : TotalConjunto){
                        if(auxEdo.containsAll(c.getEstados())){
                            id_temp = c.getId();
                        }
                    }
                    if(id_temp != -1){
                        /* Si el conjunto de estados existe, asignar id */
                        System.out.println(x + "= " + id_temp);
                    }else{
                        /* Si no existe crear otro conjunto y agregarlo a la cola */
                        id++;
                        Conjunto S_n = new Conjunto(id, auxEdo);
                        System.out.println(x + "= " + S_n.getId());
                        SConjunto.offer(S_n);
                        TotalConjunto.add(S_n);
                    }
                }
            }
            /* Se verifica si existe un estado final en la pila */
            String token = "-1";
            for(Estado e: aux.getEstados()){
                if(e.isEdoAcep()){
                    token = e.getToken();
                }
            }
            System.out.println("Token = " + token);
        }
    }
}
