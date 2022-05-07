package com.pomc.view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import static javax.swing.JOptionPane.getRootFrame;
import static javax.swing.JOptionPane.showMessageDialog;

public class SheetView {

    public static JPanel cambio() {
        // el panel con barras de scroll automáticas
        JPanel frame = new JPanel(new BorderLayout());
        JScrollPane scrollPane = new JScrollPane();


        // se añade el panel de scroll a la ventana


        //TABLE
        JTable table;
        DefaultTableModel model;
        //add the table to the frame
        String[] columnNames = { "ID", "Un dato", "Otro dato" };
        // creo un modelo de datos, sin datos por eso 'null' y establezco los
        // nombres de columna
        model = new DefaultTableModel(null, columnNames);
        // creo la tabla con el modelo de datos creado
        table = new JTable(model);

        // código del botón
        JButton btnAadirLnea = new JButton("Meter contenido");
        btnAadirLnea.addActionListener(new ActionListener() {
           public void actionPerformed(ActionEvent arg0) {

               // aquí se añaden datos a la tabla
               for (int i = 0; i < 100; i++) {

                   // creo un vector con una fila
                   Object[] aux = {i, i * 34, Math.random()};

                   // añado la fila al modelo
                   model.addRow(aux);

               }
           }
       });
        scrollPane.setViewportView(table);
btnAadirLnea.setBounds(10, 249, 267, 23);
            // pongo el botón en la ventana
        frame.add(BorderLayout.CENTER,scrollPane);
        frame.add(BorderLayout.SOUTH,btnAadirLnea);
        return frame;
    }

    public static void updateMenu(JMenuBar mb) {
        JMenu m = mb.getMenu(0);
        JMenuItem saveB = new JMenuItem("Save");
        JMenuItem saveAsB = new JMenuItem("Save As...");
        JMenuItem exportB = new JMenuItem("Export...");
        JMenuItem propertiesB = new JMenuItem("Properties");
        JMenuItem closeB = new JMenuItem("Close");
        m.add(saveB,2);
        m.add(saveAsB,3);
        m.add(exportB,4);
        m.add(propertiesB,5);
        m.add(closeB,6);

        closeB.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                MainMenu.frame.removeAll();
                try {
                    MainMenu.main(new String[] {""});
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

    }

}
