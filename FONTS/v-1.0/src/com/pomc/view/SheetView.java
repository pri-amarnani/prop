package com.pomc.view;

import javax.sql.RowSetReader;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import static javax.swing.JOptionPane.showMessageDialog;

public class SheetView {

    public static JPanel cambio(int numfil,int numcol) {
        // el panel con barras de scroll automáticas
        JPanel frame = new JPanel(new BorderLayout());


        //TABLE
        JTable table=new JTable(numfil,numcol);
        DefaultTableModel model;

        ArrayList<String> headers= new ArrayList<>(numfil);
        for (int i=1; i<=numfil;i++){
            headers.add(""+i);
        }
        ListModel l= new AbstractListModel() {
            @Override
            public int getSize() {
                return headers.size();
            }

            @Override
            public Object getElementAt(int index) {
                return headers.get(index);
            }
        };
        JList rowHeader= new JList(l);

        rowHeader.setFixedCellWidth(50);
        rowHeader.setFixedCellHeight(table.getRowHeight());
        Color c= new Color(70,130,180);
        rowHeader.setBackground(c);
        rowHeader.setBorder(BorderFactory.createLineBorder(Color.WHITE));
        rowHeader.setForeground(Color.WHITE);

        table.getTableHeader().setBorder(BorderFactory.createLineBorder(Color.WHITE));
        table.getTableHeader().setBackground(c);
        table.getTableHeader().setForeground(Color.WHITE);
        rowHeader.setCellRenderer(getRenderer());

        model = (DefaultTableModel) (table.getModel());



       table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        JScrollPane scrollPane = new JScrollPane(
                table,
                ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS
        );
            // pongo el botón en la ventana

        scrollPane.setRowHeaderView(rowHeader);
        frame.add(BorderLayout.CENTER,scrollPane);
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

    public static String convertToNumberingScheme(int number) {
        var letters  = "";
        do {
            number -= 1;
            char letra = (char) (65 + (number % 26));
            letters = letra + letters;
            number = (number / 26); // quick `floor`
        } while(number > 0);

        return letters;
    }

    private static ListCellRenderer<? super String> getRenderer() {
        return new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list,
                                                          Object value, int index, boolean isSelected, boolean cellHasFocus) {
                JLabel listCellRendererComponent = (JLabel) super
                        .getListCellRendererComponent(list, value, index, isSelected,
                                cellHasFocus);
                listCellRendererComponent.setBorder(BorderFactory.createMatteBorder(0,
                        0, 1, 0, Color.white));
                return listCellRendererComponent;
            }
        };
    }

}
