package com.pomc.view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.io.IOException;
import java.util.ArrayList;

import static com.pomc.view.SheetView.currentSheetName;
import static com.pomc.view.SheetView.getCurrentTable;

public class MenuViews {
    public static void close() {
        MainMenu.frame.removeAll();
        try {
            MainMenu.main(new String[] {""});
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    public static String changeName() {
        String newname = (String) JOptionPane.showInputDialog(
                null,
                "New Name",
                "Insert a new title for this sheet",
                JOptionPane.PLAIN_MESSAGE,
                null,
                null,
                currentSheetName()

        );
        if(newname!=null && !newname.equals("")){
            PresentationController.changeSheetName(currentSheetName(),newname);
           return newname;
        }
        else {
            return null;
        }
    }

    public static void rowsInsert() {
        SpinnerNumberModel snm= new SpinnerNumberModel(1,1,100000,1);
        SpinnerNumberModel snm2= new SpinnerNumberModel(getCurrentTable().getModel().getRowCount()+1,1,getCurrentTable().getModel().getRowCount()+1,1);
        JSpinner jsp=new JSpinner(snm);
        JSpinner jsp2=new JSpinner(snm2);
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
            ArrayList<Object> newRow = new ArrayList<Object>();
            //fill with empty data this row
            for (int j = 0; j < tmodel.getColumnCount(); j++) {
                newRow.add("");
            }
            for (int k = 0; k < (Integer) jsp.getValue(); k++) {
                tmodel.insertRow((Integer) jsp2.getValue()-1, newRow.toArray());
            }
            SheetView.updateRowHeaders();
        }
    }

    public static void colsInsert() {

        SpinnerNumberModel snm= new SpinnerNumberModel(1,1,100000,1);
        SpinnerNumberModel snm2= new SpinnerNumberModel(getCurrentTable().getModel().getColumnCount(),1,getCurrentTable().getModel().getColumnCount(),1);
        JSpinner jsp=new JSpinner(snm);
        JSpinner jsp2=new JSpinner(snm2);
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
            SheetView.updateColHeaders();
        }
    }

    public static void rowsDelete() {

        SpinnerNumberModel snm= new SpinnerNumberModel(1,1,getCurrentTable().getModel().getRowCount(),1);
        SpinnerNumberModel snm2= new SpinnerNumberModel(1,1,getCurrentTable().getModel().getRowCount(),1);
        JSpinner jsp=new JSpinner(snm);
        JSpinner jsp2=new JSpinner(snm2);
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
            DefaultTableModel tmodel = (DefaultTableModel) getCurrentTable().getModel();
            for (int k = 0; k < (int) jsp.getValue(); ++k) {
                tmodel.removeRow(b);
            }
            SheetView.updateRowHeaders();
        }
    }

    public static void colsDelete() {

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
            for (int k = 0; k < (int) jsp.getValue(); k++) {
                getCurrentTable().setAutoCreateColumnsFromModel(true);
                TableColumn t= getCurrentTable().getColumnModel().getColumn(a);
                System.out.println("HEADERRR:..............."+t.getHeaderValue());
                getCurrentTable().removeColumn(t);
                getCurrentTable().revalidate();
            }
            SheetView.updateColHeaders();
            SheetView.rewriteModel(a);
        }
    }

    public static void deleteSheet() {PresentationController.delSheet(currentSheetName());}

    public static void save() {
    }

    public static void saveAs() {
    }

    public static void export() {
    }
}
