package com.pomc.view;

import javax.sql.RowSetReader;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import static javax.swing.JOptionPane.showMessageDialog;
import static javax.swing.JOptionPane.showOptionDialog;

public class SheetView {
    static JTabbedPane sheets = new JTabbedPane();
    public static JPanel cambio(boolean isNew) {

        // el panel con barras de scroll automáticas
        JPanel frame = new JPanel(new BorderLayout());
        String[] sheets_names = PresentationController.getSheets();

        for (int i = 0; i < sheets_names.length; i++) {
            int numfil=PresentationController.getSheetRows(i,sheets_names[i]);
            int numcol= PresentationController.getSheetCols(i,sheets_names[i]);
            //TABLE
            JTable table = new JTable();
            DefaultTableModel model=new DefaultTableModel(numfil,numcol);
            table.setModel(model);
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
                        boolean ok = false;
                        while (!ok) {
                            int aux = PresentationController.getNumberofSheets() + 1;
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
                            if (newsheetpop == JOptionPane.OK_OPTION && nrows.getText()!=null &&ncolumns.getText() !=null) {
                                Integer numfil = Integer.parseInt(nrows.getText());
                                Integer numcol = Integer.parseInt(ncolumns.getText());
                                if (numfil > 0 && numcol > 0) {
                                    sheets.insertTab(newsheet_title.getText(), null, addSheet(newsheet_title.getText(), numfil, numcol), null, sheets.getTabCount() - 1);
                                    frame.setVisible(true);
                                    sheets.setSelectedIndex(sheets.getTabCount() - 2);
                                    ok=true;
                                }
                            }

                            if (newsheetpop == JOptionPane.CANCEL_OPTION) ok = true;

                            if (!ok)
                                showMessageDialog(null, "Invalid values. Couldn't create sheet.\nTry again", "Error!", JOptionPane.ERROR_MESSAGE);

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
        JMenu sheet= new JMenu(("Sheet"));
        JMenuItem change_name= new JMenuItem("Change name");
        JMenu rows= new JMenu("Rows...");
        JMenuItem rowsInsert= new JMenuItem("Insert");
        JMenuItem rowsDelete= new JMenuItem("Delete");
        rows.add(rowsInsert);
        rows.add(rowsDelete);
        JMenu cols= new JMenu("Columns...");
        JMenuItem colsInsert= new JMenuItem("Insert");
        JMenuItem colsDelete= new JMenuItem("Delete");
        cols.add(colsInsert);
        cols.add(colsDelete);
        JMenuItem delete= new JMenuItem("Delete current sheet");
        sheet.add(change_name);
        sheet.add(rows);
        sheet.add(cols);
        sheet.add(delete);

        /*JMenu sheets= new JMenu("Change sheet");
        JMenuItem changesheets= new JRadioButtonMenuItem();
        sheets.add(changesheets);
        mb.add(sheets,2);*/
        mb.add(block,1);
        mb.add(sheet,2);
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
                //PresentationController.createBlock(2,1,2,2,0);
            }
        });

        change_name.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String newname = (String) JOptionPane.showInputDialog(
                        null,
                        "New Name",
                        "Insert a new title for this sheet",
                        JOptionPane.PLAIN_MESSAGE,
                        null,
                        null,
                        currentSheetName()

                );
                PresentationController.changeSheetName(currentSheetName(),newname);
                sheets.setTitleAt(sheets.getSelectedIndex(),newname);
            }
        });

        rowsInsert.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SpinnerNumberModel snm= new SpinnerNumberModel(1,1,100000,1);
                SpinnerNumberModel snm2= new SpinnerNumberModel(getCurrentTable().getModel().getRowCount(),1,getCurrentTable().getModel().getRowCount(),1);
                JSpinner jsp=new JSpinner(snm);
                JSpinner jsp2=new JSpinner(snm2);
                //FALTA PREGUNTAR ON
                Object [] spinners= {
                        "Insert the number of rows to add",jsp,
                        "Insert the position where you want to add the rows",jsp2
                };
                int addR = JOptionPane.showConfirmDialog(
                        null,
                        spinners,
                        "Add rows",
                        JOptionPane.OK_CANCEL_OPTION,
                        JOptionPane.PLAIN_MESSAGE,
                        null

                );
                if (addR==JOptionPane.OK_OPTION) {
                    PresentationController.addRow(currentSheetName(), (Integer) jsp.getValue(), (Integer) jsp2.getValue());
                    DefaultTableModel tmodel = (DefaultTableModel) getCurrentTable().getModel();
                    //  tmodel.setRowCount(0);
                    ArrayList<Object> newRow = new ArrayList<Object>();
                    //fill with empty data this row
                    for (int j = 0; j < tmodel.getColumnCount(); j++) {
                        newRow.add("");
                    }
                    for (int k = 0; k < (Integer) jsp.getValue(); k++) {
                        tmodel.insertRow((Integer) jsp2.getValue()-1, newRow.toArray());
                    }
                    updateHeaders();
                }
            }
        });

        colsInsert.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SpinnerNumberModel snm= new SpinnerNumberModel(1,1,100000,1);
                SpinnerNumberModel snm2= new SpinnerNumberModel(getCurrentTable().getModel().getColumnCount(),1,getCurrentTable().getModel().getColumnCount(),1);
                JSpinner jsp=new JSpinner(snm);
                JSpinner jsp2=new JSpinner(snm2);
                //FALTA PREGUNTAR ON
                Object [] spinners= {
                        "Insert the number of columns to add",jsp,
                        "Insert the position where you want to add the columns",jsp2
                };
                int addC = JOptionPane.showConfirmDialog(
                        null,
                        spinners,
                        "Add columns",
                        JOptionPane.OK_CANCEL_OPTION,
                        JOptionPane.PLAIN_MESSAGE,
                        null

                );
                if (addC==JOptionPane.OK_OPTION) {
                    PresentationController.addCols(currentSheetName(), (Integer) jsp.getValue(), (Integer) jsp2.getValue());
                    DefaultTableModel tmodel = (DefaultTableModel) getCurrentTable().getModel();

                    for (int k = 0; k < (Integer) jsp.getValue(); k++) {
                        TableColumn col= new TableColumn(tmodel.getColumnCount());
                        getCurrentTable().addColumn(col);
                        int a=(Integer)jsp2.getValue();
                        tmodel.addColumn( getCurrentTable().getColumnModel().getColumn(a).getHeaderValue());//NO SE ACTUALIZA BIEN SI NO SE AÑADE AL FINAL
                        getCurrentTable().moveColumn(getCurrentTable().getColumnCount()-1,a);
                    }

                }
            }
        });

        rowsDelete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SpinnerNumberModel snm= new SpinnerNumberModel(1,1,getCurrentTable().getModel().getRowCount(),1);
                SpinnerNumberModel snm2= new SpinnerNumberModel(1,1,getCurrentTable().getModel().getRowCount(),1);
                JSpinner jsp=new JSpinner(snm);
                JSpinner jsp2=new JSpinner(snm2);
                //FALTA PREGUNTAR ON
                Object [] spinners= {
                        "Insert the number of rows to delete",jsp,
                        "Insert the position from where you want to delete the rows",jsp2
                };
                int DelR = JOptionPane.showConfirmDialog(
                        null,
                        spinners,
                        "Delete rows",
                        JOptionPane.OK_CANCEL_OPTION,
                        JOptionPane.PLAIN_MESSAGE,
                        null

                );
                if (DelR==JOptionPane.OK_OPTION) {
                    PresentationController.delRow(currentSheetName(), (Integer) jsp.getValue(), (Integer) jsp2.getValue());
                    DefaultTableModel tmodel = (DefaultTableModel) getCurrentTable().getModel();
                    //  tmodel.setRowCount(0);

                    for (int k = 0; k < (Integer) jsp.getValue(); k++) {
                        int a=(Integer) jsp2.getValue()-1;
                        tmodel.removeRow(a);
                    }
                    updateHeaders();
                }
            }
        });

        colsDelete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SpinnerNumberModel snm= new SpinnerNumberModel(1,1,getCurrentTable().getModel().getColumnCount(),1);
                SpinnerNumberModel snm2= new SpinnerNumberModel(1,1,getCurrentTable().getModel().getColumnCount(),1);
                JSpinner jsp=new JSpinner(snm);
                JSpinner jsp2=new JSpinner(snm2);
                //FALTA PREGUNTAR ON
                Object [] spinners= {
                        "Insert the number of columns to delete",jsp,
                        "Insert the position from where you want to delete the columns",jsp2
                };
                int DelR = JOptionPane.showConfirmDialog(
                        null,
                        spinners,
                        "Delete columns",
                        JOptionPane.OK_CANCEL_OPTION,
                        JOptionPane.PLAIN_MESSAGE,
                        null

                );
                if (DelR==JOptionPane.OK_OPTION) {
                    PresentationController.delCols(currentSheetName(), (Integer) jsp.getValue(), (Integer) jsp2.getValue());
                    DefaultTableModel tmodel = (DefaultTableModel) getCurrentTable().getModel();


                    for (int k = 0; k < (Integer) jsp.getValue(); k++) {
                        int a=(Integer) jsp2.getValue()-1;
                        TableColumn t=getCurrentTable().getColumnModel().getColumn(a);
                        getCurrentTable().removeColumn(t);
                        getCurrentTable().revalidate();
                        //FALTA UPDATE HEADER
                    }

                }
            }
        });

        delete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PresentationController.delSheet(currentSheetName());
                sheets.remove(sheets.getSelectedIndex());
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

    public static String currentSheetName(){
        int a= sheets.getSelectedIndex();
        return sheets.getTitleAt(a);
    }

    public static JTable getCurrentTable(){
        int a= sheets.getSelectedIndex();
        JScrollPane sp;
        if (a==0){ sp=(JScrollPane) sheets.getComponent(a);}
        else {sp= (JScrollPane) sheets.getComponent(a+1);}
        return (JTable) sp.getViewport().getComponent(0);
    }

    public static JScrollPane getCurrentScroll(){
        int a= sheets.getSelectedIndex();
        JScrollPane sp;
        if (a==0){ sp=(JScrollPane) sheets.getComponent(a);}
        else {sp= (JScrollPane) sheets.getComponent(a+1);}
        return sp;
    }
    public static void updateHeaders(){
        JTable current= getCurrentTable();
        DefaultTableModel tmodel= (DefaultTableModel) current.getModel();
        int numfil=tmodel.getRowCount();
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
        rowHeader.setFixedCellHeight(current.getRowHeight());
        Color c = new Color(70, 130, 180);
        rowHeader.setBackground(c);
        rowHeader.setBorder(BorderFactory.createLineBorder(Color.WHITE));
        rowHeader.setForeground(Color.WHITE);

        current.getTableHeader().setBorder(BorderFactory.createLineBorder(Color.WHITE));
        current.getTableHeader().setBackground(c);
        current.getTableHeader().setForeground(Color.WHITE);
        rowHeader.setCellRenderer(getRenderer());
        getCurrentScroll().setRowHeaderView(rowHeader);


    }

}
