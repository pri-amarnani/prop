package com.pomc.view;

import javax.sql.RowSetReader;
import javax.swing.*;
import javax.swing.event.CellEditorListener;
import javax.swing.event.ChangeEvent;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.EventObject;
import java.util.List;
import java.util.Vector;

import static java.lang.Math.max;
import static java.lang.Math.min;
import static javax.swing.JOptionPane.showMessageDialog;
import static javax.swing.JOptionPane.showOptionDialog;

public class SheetView {
    static JTabbedPane sheets = new JTabbedPane();
    static JLabel bar=new JLabel();

    public static JPanel cambio() {
        sheets.removeAll();
        // el panel con barras de scroll automáticas
        JPanel frame = new JPanel(new BorderLayout());
        String[] sheets_names = PresentationController.getSheets();

        for (int i = 0; i < sheets_names.length; i++) {
            int numfil=PresentationController.getSheetRows(i,sheets_names[i]);
            int numcol= PresentationController.getSheetCols(i,sheets_names[i]);
            //TABLE
            JTable table = new JTable();
            table.putClientProperty("terminateEditOnFocusLost", true);
            DefaultTableModel model=new DefaultTableModel(numfil,numcol);
            table.setModel(model);
            //table.setColumnModel(columnsModel);
            ArrayList<String> rowheaders = new ArrayList<>(numfil);
            for (int j = 1; j <= numfil; j++) {
                rowheaders.add("" + j);

            }
            ListModel l = new AbstractListModel() {
                @Override
                public int getSize() {
                    return rowheaders.size();
                }

                @Override
                public Object getElementAt(int index) {
                    return rowheaders.get(index);
                }
            };


            for (int j=0;j<numcol;j++){
                table.getColumnModel().getColumn(j).setHeaderValue(numToAlphabet(j));
            }

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

            bar.setBackground(Color.LIGHT_GRAY);
            bar.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
            bar.setText("    ");
            bar.setMinimumSize(new Dimension(table.getWidth(),25));
            bar.setPreferredSize(new Dimension(table.getWidth(),25));
            bar.setMaximumSize(new Dimension(table.getWidth(),25));
            frame.add(BorderLayout.NORTH,bar);

            sheets.addTab(sheets_names[i], null, scrollPane);



            //Cell listeners

            model.addTableModelListener(new TableModelListener() {
                @Override
                public void tableChanged(TableModelEvent e) {
                    if (e.getType() == 0) {
                        int rowChanged = e.getFirstRow();
                        int colChanged = e.getColumn();


                        if (rowChanged >= 0 && rowChanged < getCurrentTable().getRowCount() && colChanged >= 0 && colChanged < getCurrentTable().getColumnCount()) {
//                       String colName = getCurrentTable().getModel().getColumnName(colChanged);
//
//                        int colValue= alphabetToNum(colName);
                            int colIndex = getCurrentTable().convertColumnIndexToView(colChanged);


                            TableModel tm = getCurrentTable().getModel();

                            String newValue = (String) table.getValueAt(rowChanged, colIndex);
                            PresentationController.editedCell(rowChanged, colIndex, newValue, currentSheetName());
                            //System.out.println(newValue);
                            //PresentationController.showTcells(currentSheetName());


                        }

                    }
                }
            });

            table.addMouseListener(new MouseAdapter() {
                int row1,col1,row2,col2;
                @Override
                public void mousePressed(MouseEvent e) {
                    table.clearSelection();
                    int row=table.rowAtPoint(e.getPoint());
                    int col=table.columnAtPoint(e.getPoint());
                    if(row<table.getRowCount() && row>=0 && col>=0 && col<table.getColumnCount()){
                        row1=row;
                        col1=col;
                        bar.setText(PresentationController.cellInfo(row,col,currentSheetName()));
                    }

                }

                @Override
                public void mouseReleased(MouseEvent e) {

                    int row=table.rowAtPoint(e.getPoint());
                    int col=table.columnAtPoint(e.getPoint());
                    if(row<table.getRowCount() && row>=0 && col>=0 && col<table.getColumnCount() ){
                        if (row1 != row || col1 != col ) {
                            bar.setText(PresentationController.cellInfo(row,col,currentSheetName()));
                            col2=col;
                            row2=row;
                            PresentationController.createBlock(min(row1,row2),min(col1,col2),max(row1,row2),max(col1,col2),currentSheetName());
                            PresentationController.showTcells(currentSheetName());
                            System.out.println();
                        }
                    }
                }

            });

            table.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            table.setColumnSelectionAllowed(false);
            table.setRowSelectionAllowed(false);
            table.setCellSelectionEnabled(true);
            PresentationController.createBlock(0,0,numfil-1,numcol-1,sheets_names[i]);



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
                    JTextField newsheet_title;
                    if (existsSheetName("sheet " + aux)) ++aux;
                    newsheet_title = new JTextField("sheet " + aux);
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
                    if (newsheetpop == JOptionPane.OK_OPTION && nrows.getText()!=null &&ncolumns.getText() !=null && isNumeric(nrows.getText()) && isNumeric(ncolumns.getText())) {
                        Integer numfil = Integer.parseInt(nrows.getText());
                        Integer numcol = Integer.parseInt(ncolumns.getText());
                        if (numfil > 0 && numcol > 0) {
                            String title=newsheet_title.getText();
                            if (existsSheetName(title)) title=title+"_v2";
                            sheets.insertTab(title, null, addSheet(title, numfil, numcol), null, sheets.getTabCount() - 1);
                            frame.setVisible(true);
                            sheets.setSelectedIndex(sheets.getTabCount() - 2);
                            ok=true;
                        }
                    }

                    if (newsheetpop == JOptionPane.CANCEL_OPTION || newsheetpop==JOptionPane.CLOSED_OPTION) ok = true;

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

        JMenuBar jmbar_sheet= new JMenuBar();
        jmbar_sheet.add(Box.createHorizontalGlue());
        // TODO: 18/5/22 persistencia
        ImageIcon findIcon= new ImageIcon("res/find.png");
        Image fIcon=findIcon.getImage();
        Image fi=fIcon.getScaledInstance(40,40,Image.SCALE_DEFAULT);
        findIcon.setImage(fi);
        JButton find= new JButton(findIcon);
        Color c = new Color(70, 130, 180);
        find.setBackground(jmbar_sheet.getBackground());
        find.setBorder(BorderFactory.createLineBorder(c,1));
        jmbar_sheet.add(find);
        find.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String value = (String) JOptionPane.showInputDialog(
                        null,
                        "Find in Sheet",
                        "Insert value to Find:",
                        JOptionPane.PLAIN_MESSAGE,
                        null,
                        null,
                        ""
                );
                if (value != null) {
                    if (emptyBlock()) {
                        System.out.println("NO BLOCK :///");
                        PresentationController.createBlock(0,0,getCurrentTable().getRowCount()-1,getCurrentTable().getColumnCount()-1,currentSheetName());
                    }
                    String[] cellFound = PresentationController.blockFind(value,currentSheetName());
                    if (cellFound.length>1) {
                        System.out.println("FOUNDD");
                        int row = Integer.parseInt(cellFound[0]);
                        int col = Integer.parseInt(cellFound[1]);
                        getCurrentTable().changeSelection(row-1,col-1,false,false);
                        System.out.println("SLECTEDDD");
                    }
                    else{
                        System.out.println("WTFFFF");
                        showMessageDialog(null, "Value not found! :(\nTry again", "Error!", JOptionPane.ERROR_MESSAGE);
                    }
                }

            }
        });

        ImageIcon findRIcon= new ImageIcon("res/findR.png");
        Image fRcon=findRIcon.getImage();
        Image fr=fRcon.getScaledInstance(40,40,Image.SCALE_DEFAULT);
        findRIcon.setImage(fr);
        JButton findR= new JButton(findRIcon);
        findR.setBackground(jmbar_sheet.getBackground());
        findR.setBorder(BorderFactory.createLineBorder(c,1));
        jmbar_sheet.add(BorderLayout.CENTER,findR);
        findR.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JTextField value = new JTextField();
                JTextField replace = new JTextField();
                Object[] fields = new Object[]{
                    "Value to find :", value,
                    "Value to replace : ", replace
                };
                int result = JOptionPane.showConfirmDialog(
                        null,
                        fields,
                        "Find and Replace",
                        JOptionPane.OK_CANCEL_OPTION,
                        JOptionPane.PLAIN_MESSAGE,
                        null

                );
                if (result == JOptionPane.OK_OPTION){
                    if (emptyBlock()) {
                        PresentationController.createBlock(0,0,getCurrentTable().getRowCount()-1,getCurrentTable().getColumnCount()-1,currentSheetName());
                    }
                    String[] cellFound = PresentationController.blockFind(value.getText(),currentSheetName());
                    if (cellFound.length>1 && !value.getText().equals("")) {
                        Integer[] replaces = PresentationController.blockFindAndReplace(value.getText(),replace.getText(),currentSheetName());
                        if(replaces != null) {
                            for (int i = 0; i < replaces.length; i+=2) {
                                getCurrentTable().getModel().setValueAt(replace.getText(),replaces[i],replaces[i+1]);
                            }
                        }
                    }
                    else{

                        showMessageDialog(null, "Value not found! :(\nTry again", "Error!", JOptionPane.ERROR_MESSAGE);
                    }
                }

            }
        });


        ImageIcon sort= new ImageIcon("res/sort.png");
        Image sortI=sort.getImage();
        Image s=sortI.getScaledInstance(40,40,Image.SCALE_DEFAULT);
        sort.setImage(s);
        JButton sortB= new JButton(sort);
        sortB.setBackground(jmbar_sheet.getBackground());
        sortB.setBorder(BorderFactory.createLineBorder(c,1));
        jmbar_sheet.add(BorderLayout.CENTER,sortB);



        sortB.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (emptyBlock()) {
                    System.out.println("NO BLOCK :///");
                    PresentationController.createBlock(0,0,getCurrentTable().getRowCount()-1,getCurrentTable().getColumnCount()-1,currentSheetName());
                }
                SpinnerNumberModel snm= new SpinnerNumberModel(1,1,PresentationController.blockCols(currentSheetName()),1);
                JSpinner jsp=new JSpinner(snm);
                Object[] options = {
                        "Descending",
                        "Ascending",
                };
                JComboBox optionList = new JComboBox(options);
                optionList.setSelectedIndex(0);
                Object[] components = {
                        "Insert main column :", jsp,
                        "Select sort criteria :", optionList
                };
                int result = JOptionPane.showConfirmDialog(
                        null,
                        components,
                        "Sort",
                        JOptionPane.OK_CANCEL_OPTION,
                        JOptionPane.PLAIN_MESSAGE,
                        null
                );
                if (result == JOptionPane.OK_OPTION) {
                    String criteria = "<";
                    if(optionList.getSelectedItem().equals("Ascending")) criteria = ">";
                    int col = (int) jsp.getValue()-1;
                    //int ncol = PresentationController.blockFirstCol(currentSheetName()) + col - 1;
                    Boolean sort =PresentationController.blockSort(col,criteria ,currentSheetName());
                    if (!sort) {
                        showMessageDialog(null, "Couldn't sort \nTry again", "Error!", JOptionPane.ERROR_MESSAGE);
                    }
                    else {
                        rewriteBlock();
                    }
                }


            }
        });




        jmbar_sheet.add(Box.createHorizontalGlue());

        JPanel mp= (JPanel) MainMenu.getCurrentFrame().getContentPane().getComponent(0);
        mp.add(BorderLayout.CENTER,jmbar_sheet);
        /*JMenu sheets= new JMenu("Change sheet");
        JMenuItem changesheets= new JRadioButtonMenuItem();
        sheets.add(changesheets);
        mb.add(sheets,2);*/
        mb.add(block,1);
        mb.add(sheet,2);

        create.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
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
                SpinnerNumberModel snm2= new SpinnerNumberModel(getCurrentTable().getModel().getRowCount()+1,1,getCurrentTable().getModel().getRowCount()+1,1);
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
                    updateRowHeaders();
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
                        getCurrentTable().setAutoCreateColumnsFromModel(false);
                        getCurrentTable().addColumn(col);
                        int a=(Integer)jsp2.getValue();
                        tmodel.addColumn( getCurrentTable().getColumnModel().getColumn(a).getHeaderValue());//NO SE ACTUALIZA BIEN SI NO SE AÑADE AL FINAL
                        int b = getCurrentTable().getColumnCount()-1;
                        getCurrentTable().moveColumn(b,a+k);
                    }
                    updateColHeaders();
                    ((DefaultTableModel) getCurrentTable().getModel()).fireTableDataChanged();


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
                    int a2=(int) jsp.getValue();
                    int b=(int) jsp2.getValue()-1;

                    PresentationController.delRow(currentSheetName(), a2, b);
                    System.out.println();
                    //PresentationController.showTcells(currentSheetName());
                    DefaultTableModel tmodel = (DefaultTableModel) getCurrentTable().getModel();
                    //  tmodel.setRowCount(0);

                    for (int k = 0; k < (int) jsp.getValue(); ++k) {
                        tmodel.removeRow(b);
                    }
                    updateRowHeaders();
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
                int DelC = JOptionPane.showConfirmDialog(
                        null,
                        spinners,
                        "Delete columns",
                        JOptionPane.OK_CANCEL_OPTION,
                        JOptionPane.PLAIN_MESSAGE,
                        null

                );
                if (DelC==JOptionPane.OK_OPTION) {
                    int a=(int) jsp2.getValue()-1;

                    PresentationController.delCols(currentSheetName(), (Integer) jsp.getValue(), a);
                    DefaultTableModel tmodel = (DefaultTableModel) getCurrentTable().getModel();
                    for (int k = 0; k < (int) jsp.getValue(); k++) {
                        getCurrentTable().setAutoCreateColumnsFromModel(true);
                        //getCurrentTable().setAutoCreateColumnsFromModel(false);
                        TableColumn t= getCurrentTable().getColumnModel().getColumn(a);
                        System.out.println("HEADERRR:..............."+t.getHeaderValue());
                        getCurrentTable().removeColumn(t);
                        getCurrentTable().revalidate();
                        //FALTA UPDATE HEADER
                    }
                    updateColHeaders();
                    rewriteModel(a);
                    //((DefaultTableModel) getCurrentTable().getModel()).fireTableDataChanged();
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
        model.addTableModelListener(new TableModelListener() {
            @Override
            public void tableChanged(TableModelEvent e) {
                if (e.getType() == TableModelEvent.UPDATE) {
                    int rowChanged = e.getFirstRow();
                    int colChanged = e.getColumn();


                    if (rowChanged >= 0 && rowChanged < getCurrentTable().getRowCount() && colChanged >= 0 && colChanged < getCurrentTable().getColumnCount()) {
//                       String colName = getCurrentTable().getModel().getColumnName(colChanged);
//
//                        int colValue= alphabetToNum(colName);
                        int colIndex = getCurrentTable().convertColumnIndexToView(colChanged);

                        TableModel tm = getCurrentTable().getModel();
                        String newValue = (String) table.getValueAt(rowChanged, colIndex);
                        PresentationController.editedCell(rowChanged, colIndex, newValue, currentSheetName());
                        //System.out.println(newValue);
                        //PresentationController.showTcells(currentSheetName());
                    }

                }
            }
        });

        table.addMouseListener(new MouseAdapter() {
            int row1,col1,row2,col2;
            @Override
            public void mousePressed(MouseEvent e) {
                table.clearSelection();
                int row=table.rowAtPoint(e.getPoint());
                int col=table.columnAtPoint(e.getPoint());
                if(row<table.getRowCount() && row>=0 && col>=0 && col<table.getColumnCount()){
                    row1=row;
                    col1=col;
                    bar.setText(PresentationController.cellInfo(row,col,currentSheetName()));
                }

            }

            @Override
            public void mouseReleased(MouseEvent e) {

                int row=table.rowAtPoint(e.getPoint());
                int col=table.columnAtPoint(e.getPoint());
                if(row<table.getRowCount() && row>=0 && col>=0 && col<table.getColumnCount() ){
                    if (row1 != row || col1 != col ) {
                        bar.setText(PresentationController.cellInfo(row,col,currentSheetName()));
                        col2=col;
                        row2=row;
                        PresentationController.createBlock(min(row1,row2),min(col1,col2),max(row1,row2),max(col1,col2),currentSheetName());

                        System.out.println();
                    }
                }
            }

        });

        table.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        table.setColumnSelectionAllowed(false);
        table.setRowSelectionAllowed(false);
        table.setCellSelectionEnabled(true);

        return scrollPane;

    }
//    public static String convertToNumberingScheme(int number) {
//        var letters  = "";
//        do {
//            number -= 1;
//            char letra = (char) (65 + (number % 26));
//            letters = letra + letters;
//            number = (number / 26); // quick `floor`
//        } while(number > 0);
//
//        return letters;
//    }

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
    public static void updateRowHeaders(){
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

    public static void updateColHeaders(){
        int colcount=getCurrentTable().getColumnCount();
        String[] newh= new String[colcount];
        for (int j=0;j<colcount;j++){
            getCurrentTable().getColumnModel().getColumn(j).setHeaderValue(numToAlphabet(j));
            newh[j]=numToAlphabet(j);
        }
        DefaultTableModel tm= (DefaultTableModel) getCurrentTable().getModel();
        tm.setColumnIdentifiers(newh);
    }


    public static boolean isNumeric(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch(NumberFormatException e){
            return false;
        }
    }

    public static boolean existsSheetName(String name){
        for (int i=0;i<sheets.getTabCount();i++){
            if (sheets.getTitleAt(i).equals(name)) return true;
        }
        return false;
    }

    public static String numToAlphabet(int i) {
        if( i<0 ) {
            return "-"+numToAlphabet(-i-1);
        }

        int quot = i/26;
        int rem = i%26;
        char letter = (char)((int)'A' + rem);
        if( quot == 0 ) {
            return ""+letter;
        } else {
            return numToAlphabet(quot-1) + letter;
        }
    }

    public static void rewriteModel(int col){
        TableModel tm = getCurrentTable().getModel();
        for (int i = 0; i < tm.getRowCount(); i++) {
            for (int j = col; j <tm.getColumnCount() ; j++) {
                tm.setValueAt(PresentationController.getCellInfo(i,j,currentSheetName()),i,j);
            }

        }

    }

//    public static int[] getIds(String s){
//        int found[] = new int[2];
//        int modelids[]=getIdsModel(s);
//        if(s!=null) {
//            for (int i = 0; i < getCurrentTable().getRowCount(); i++) {
//                for (int j = 0; j < getCurrentTable().getColumnCount(); j++) {
//                    if (getCurrentTable().getValueAt(i, j)!= null && getCurrentTable().getValueAt(i, j).equals(s)) {
//                            found[0] = i;
//                            found[1] = j;
//                    }
//                }
//            }
//        }
//        return found;
//    }
//

    public static int alphabetToNum(String i) {
        int result = -1;
        for (int j = 0; j < i.length(); j++) {
            result += Math.pow(26,j)* (i.charAt(j) - 'A' + 1);

        }
        return result;
    }

    public static boolean emptyBlock() {
       return (getCurrentTable().getSelectedColumnCount() <= 0) && (getCurrentTable().getSelectedRowCount() <=0);
    }

    public static void rewriteBlock(){
        TableModel tm = getCurrentTable().getModel();
        String cs = currentSheetName();
        int firstrow = PresentationController.blockFirstRow(cs);
        int firstcol = PresentationController.blockFirstCol(cs);
        System.out.println("firstcol ====" + firstcol);
        for (int i = 0; i < PresentationController.blockRows(cs); i++) {
            for (int j = 0; j < PresentationController.blockCols(cs); j++) {
                  tm.setValueAt(PresentationController.getCellInfo(firstrow+i,firstcol+j,cs),firstrow+i,firstcol+j);
            }
        }
    }




}
