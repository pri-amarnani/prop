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

    public static JPanel cambio(boolean isNew) {

        // el panel con barras de scroll automáticas
        JPanel frame = new JPanel(new BorderLayout());
        String[] sheets_names = PresentationController.getSheets();
        JTabbedPane sheets = new JTabbedPane();
        for (int i = 0; i < sheets_names.length; i++) {
            int numfil=PresentationController.getSheetRows(i);
            int numcol= PresentationController.getSheetCols(i);
            //TABLE
            JTable table = new JTable(numfil,numcol );
            DefaultTableModel model;

            ArrayList<String> headers = new ArrayList<>(numfil);
            for (int j = 1; j <= numfil; j++) {
                headers.add("" + j);
            }
            ListModel l = new AbstractListModel() {
                @Override
                public int getSize() {
                    return headers.size();
                }

                @Override
                public Object getElementAt(int index) {
                    return headers.get(index);
                }
            };
            JList rowHeader = new JList(l);

            rowHeader.setFixedCellWidth(50);
            rowHeader.setFixedCellHeight(table.getRowHeight());
            Color c = new Color(70, 130, 180);
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


            //new sheet button


            sheets.addTab(sheets_names[i], null, scrollPane);

        }

    JButton newsheet = new JButton("+");
            newsheet.setBorderPainted(false);
            newsheet.setFocusPainted(false);
            newsheet.setContentAreaFilled(false);
            newsheet.setBackground(frame.getBackground());
            Font f= new Font("Comic Sans",Font.BOLD,10);
            newsheet.setFont(f);
            sheets.setSize(5,5);
           frame.add(BorderLayout.PAGE_END, newsheet);
            JPanel panelboton= new JPanel();
            panelboton.add(newsheet);
            sheets.add("extra",null);
            sheets.setTabComponentAt(sheets.getTabCount()-1,panelboton);
            sheets.setEnabledAt(sheets.getTabCount()-1,false);

        frame.add(BorderLayout.CENTER, sheets);


                newsheet.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        int aux = PresentationController.getNumberofSheets()+1;
                        JTextField newsheet_title = new JTextField("sheet " + aux);
                        JTextField nrows = new JTextField("25");
                        JTextField ncolumns = new JTextField("25");
                        Object[] fields = {
                                //"Insert a title for the new document",title,
                                "Insert a title for the new sheet", newsheet_title,
                                "Insert the number of rows in the new sheet", nrows,
                                "Insert the number of columns in the new sheet", ncolumns,
                        };

                        int newsheetpop = JOptionPane.showConfirmDialog(
                                null,
                                fields,
                                "New sheet",
                                JOptionPane.OK_CANCEL_OPTION,
                                JOptionPane.PLAIN_MESSAGE,
                                null

                        );
                        if (newsheetpop == JOptionPane.OK_OPTION) {
                            //PresentationController.newSheet(newsheet_title.getText(), Integer.parseInt(nrows.getText()), Integer.parseInt(nrows.getText()));
                            sheets.insertTab(newsheet_title.getText(),null,addSheet(newsheet_title.getText(),Integer.parseInt(nrows.getText()), Integer.parseInt(ncolumns.getText())),null,sheets.getTabCount()-1);
                            //frame.repaint();
                            frame.setVisible(true);
                            sheets.setSelectedIndex(sheets.getTabCount()-2);
                        }
                    }

                });

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
        JMenu block= new JMenu("Block");
        JMenuItem create= new JMenuItem("Create");
        JMenuItem create_and_func= new JMenuItem("Create & Apply function");
        JMenuItem check= new JMenuItem(("Check current block"));
        block.add(create);
        block.add(create_and_func);
        block.add(check);

        /*JMenu sheets= new JMenu("Change sheet");
        JMenuItem changesheets= new JRadioButtonMenuItem();
        sheets.add(changesheets);
        mb.add(sheets,2);*/
        mb.add(block,1);
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

        create.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PresentationController.createBlock(2,1,2,2,0);
            }
        });

    }

    public static JScrollPane addSheet(String title, int numfil,int numcol){
        PresentationController.newSheet(title,numfil,numcol);
        String[] sheets_names = PresentationController.getSheets();

            //TABLE
            JTable table = new JTable(numfil,numcol );
            DefaultTableModel model;

            ArrayList<String> headers = new ArrayList<>(numfil);
            for (int j = 1; j <= numfil; j++) {
                headers.add("" + j);
            }
            ListModel l = new AbstractListModel() {
                @Override
                public int getSize() {
                    return headers.size();
                }

                @Override
                public Object getElementAt(int index) {
                    return headers.get(index);
                }
            };
            JList rowHeader = new JList(l);

            rowHeader.setFixedCellWidth(50);
            rowHeader.setFixedCellHeight(table.getRowHeight());
            Color c = new Color(70, 130, 180);
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


            //new sheet button

            return scrollPane;

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
