package com.pomc.view;

import com.pomc.controller.DomainController;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import static com.pomc.view.SheetView.*;

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
        if (PresentationController.isFileSaved()) PresentationController.save();
        else saveAs();
    }

    public static void saveAs() {
        JFileChooser selectFile = new JFileChooser();
        FileFilter pomc = new FileNameExtensionFilter("POMC Worksheet Files (*.pomc)", "pomc");
        selectFile.addChoosableFileFilter(pomc);
        selectFile.setFileFilter(pomc);
        selectFile.setDialogTitle("Select location to save the file");
        int result = selectFile.showSaveDialog(null);
        if(result == JFileChooser.APPROVE_OPTION) {
            System.out.println(selectFile.getCurrentDirectory());
            PresentationController.saveAs(selectFile.getSelectedFile());
        }
    }

    public static void export() {
        JFileChooser selectFile = new JFileChooser();
        FileFilter pdf =new FileNameExtensionFilter("PDF Documents (*.pdf)", "pdf");
        selectFile.addChoosableFileFilter(pdf);
        selectFile.addChoosableFileFilter(new FileNameExtensionFilter("Text Files (*.txt)", "txt"));
        selectFile.addChoosableFileFilter(new FileNameExtensionFilter("CSV Files (*.csv)", "csv"));
        selectFile.setFileFilter(pdf);
        selectFile.setDialogTitle("Select location to export the file");
        int result = selectFile.showSaveDialog(null);
        if(result == JFileChooser.APPROVE_OPTION) {
            System.out.println(selectFile.getCurrentDirectory());
            int desLength = selectFile.getFileFilter().getDescription().length();
            String ext =selectFile.getFileFilter().getDescription().substring(desLength-4,desLength-1);
            PresentationController.export(selectFile.getSelectedFile(), ext);
        }
    }

    public static void importFiles() {
        JFileChooser selectFile = new JFileChooser();
        FileFilter csv = new FileNameExtensionFilter("CSV Files (*.csv)", "csv");
        selectFile.addChoosableFileFilter(csv);
        selectFile.addChoosableFileFilter(new FileNameExtensionFilter("Text Files (*.txt)", "txt"));
        selectFile.setDialogTitle("Select file to import");
        selectFile.setFileFilter(csv);
        int result = selectFile.showOpenDialog(null);
        if(result == JFileChooser.APPROVE_OPTION) {
            System.out.println(selectFile.getCurrentDirectory());
            PresentationController.Import(selectFile.getSelectedFile());
            BorderLayout blayout = (BorderLayout) MainMenu.frame.getContentPane().getLayout();
            MainMenu.frame.getContentPane().remove(blayout.getLayoutComponent(BorderLayout.CENTER));
            MainMenu.frame.repaint();
            MainMenu.frame.getContentPane().add(BorderLayout.CENTER, SheetView.cambio());
            rewriteModel(0);
            if (!MainMenu.menuUpdated) {
                MainMenu.menuUpdated = true;
                SheetView.updateMenu(MainMenu.mb);
            }
            MainMenu.frame.setTitle(PresentationController.getTitle() + " - POMC WORKSHEETS");
            MainMenu.frame.setVisible(true);
        }
    }
    public static void open() {
        JFileChooser selectFile = new JFileChooser();
        FileFilter pomc = new FileNameExtensionFilter("POMC Worksheet Files (*.pomc)", "pomc");
        selectFile.addChoosableFileFilter(pomc);
        selectFile.setDialogTitle("Select file to open");
        selectFile.setFileFilter(pomc);
        int result = selectFile.showOpenDialog(null);
        if(result == JFileChooser.APPROVE_OPTION) {
            if (!MainMenu.menuUpdated) {
                PresentationController.open(selectFile.getSelectedFile());
                BorderLayout blayout = (BorderLayout) MainMenu.frame.getContentPane().getLayout();
                MainMenu.frame.getContentPane().remove(blayout.getLayoutComponent(BorderLayout.CENTER));
                MainMenu.frame.repaint();
                MainMenu.frame.getContentPane().add(BorderLayout.CENTER, SheetView.cambio());
                MainMenu.menuUpdated = true;
                SheetView.updateMenu(MainMenu.mb);
                MainMenu.frame.setTitle(PresentationController.getTitle() + " - POMC WORKSHEETS");
                MainMenu.frame.setVisible(true);
                SheetView.rewriteModel(0);

            }
            else {//TODO preguntar si seguro
                PresentationController.open(selectFile.getSelectedFile());
                BorderLayout blayout = (BorderLayout) MainMenu.frame.getContentPane().getLayout();
                MainMenu.frame.getContentPane().remove(blayout.getLayoutComponent(BorderLayout.CENTER));
                MainMenu.frame.repaint();
                MainMenu.frame.getContentPane().add(BorderLayout.CENTER, SheetView.cambio());
                MainMenu.menuUpdated = true;
                SheetView.updateMenu(MainMenu.mb);
                MainMenu.frame.setTitle(PresentationController.getTitle() + " - POMC WORKSHEETS");
                MainMenu.frame.setVisible(true);
                SheetView.rewriteModel(0);
            }
        }
    }
}
