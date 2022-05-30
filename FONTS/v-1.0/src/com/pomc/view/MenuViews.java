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
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;

import static com.pomc.view.SheetView.*;
import static javax.swing.JOptionPane.*;

public class MenuViews {

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
        int tcols = SheetView.getCurrentTable().getModel().getColumnCount();
        String[] alphCols = FuncView.numtoAlphabetCols(FuncView.getCols(tcols));
        SpinnerListModel ulcm = new SpinnerListModel(alphCols);

        SpinnerNumberModel snm= new SpinnerNumberModel(1,1,100000,1);
        //SpinnerNumberModel snm2= new SpinnerNumberModel(getCurrentTable().getModel().getColumnCount(),1,getCurrentTable().getModel().getColumnCount(),1);
        JSpinner jsp=new JSpinner(snm);
        JSpinner jsp2=new JSpinner(ulcm);
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
            int jsp2val=  SheetView.alphabetToNum((String) jsp2.getValue())+1;
            PresentationController.addCols(currentSheetName(), (Integer) jsp.getValue(), jsp2val);
            DefaultTableModel tmodel = (DefaultTableModel) getCurrentTable().getModel();

            for (int k = 0; k < (Integer) jsp.getValue(); k++) {
                TableColumn col= new TableColumn(tmodel.getColumnCount());
                getCurrentTable().setAutoCreateColumnsFromModel(false);
                getCurrentTable().addColumn(col);
                tmodel.addColumn( getCurrentTable().getColumnModel().getColumn(jsp2val).getHeaderValue());//NO SE ACTUALIZA BIEN SI NO SE AÑADE AL FINAL
                int b = getCurrentTable().getColumnCount()-1;
                getCurrentTable().moveColumn(b,jsp2val+k);
            }
            SheetView.updateColHeaders();
        }
    }

    public static void rowsDelete() {
        if(getCurrentTable().getModel().getRowCount() <= 1) {
            showMessageDialog(null, "Can't delete a single row ", "Error!", JOptionPane.ERROR_MESSAGE);
        }
        else {
            SpinnerNumberModel snm = new SpinnerNumberModel(1, 1, getCurrentTable().getModel().getRowCount()-1, 1);
            SpinnerNumberModel snm2 = new SpinnerNumberModel(1, 1, getCurrentTable().getModel().getRowCount(), 1);
            JSpinner jsp = new JSpinner(snm);
            JSpinner jsp2 = new JSpinner(snm2);
            Object[] spinners = {
                    "Insert the number of rows to delete", jsp,
                    "Insert the position from where you want to delete the rows", jsp2
            };
            int DelR = JOptionPane.showConfirmDialog(
                    null,
                    spinners,
                    "Delete rows",
                    JOptionPane.OK_CANCEL_OPTION,
                    JOptionPane.PLAIN_MESSAGE,
                    null
            );
            if (DelR == JOptionPane.OK_OPTION) {
                int a2 = (int) jsp.getValue();
                int b = (int) jsp2.getValue() - 1;
                if (a2 +b > (getCurrentTable().getRowCount())) {
                    showMessageDialog(null, "Can't delete rows from here ", "Error!", JOptionPane.ERROR_MESSAGE);
                }
                else {
                    PresentationController.delRow(currentSheetName(), a2, b);
                    DefaultTableModel tmodel = (DefaultTableModel) getCurrentTable().getModel();
                    for (int k = 0; k < (int) jsp.getValue(); ++k) {
                        tmodel.removeRow(b);
                    }
                    SheetView.updateRowHeaders();
                }
            }
        }
    }

    public static void colsDelete() {
        int tcols = SheetView.getCurrentTable().getModel().getColumnCount();
        if (tcols <= 1) {
            showMessageDialog(null, "Can't delete a single column ", "Error!", JOptionPane.ERROR_MESSAGE);
        }
        else {
            String[] alphCols = FuncView.numtoAlphabetCols(FuncView.getCols(tcols));
            SpinnerListModel ulcm = new SpinnerListModel(alphCols);

            SpinnerNumberModel snm = new SpinnerNumberModel(1, 1, getCurrentTable().getModel().getColumnCount()-1, 1);
            JSpinner jsp = new JSpinner(snm);
            JSpinner jsp2 = new JSpinner(ulcm);
            //FALTA PREGUNTAR ON
            Object[] spinners = {
                    "Insert the number of columns to delete", jsp,
                    "Insert the position from where you want to delete the columns", jsp2
            };
            int DelC = JOptionPane.showConfirmDialog(
                    null,
                    spinners,
                    "Delete columns",
                    JOptionPane.OK_CANCEL_OPTION,
                    JOptionPane.PLAIN_MESSAGE,
                    null
            );
            if (DelC == JOptionPane.OK_OPTION) {
                int jsp2val = SheetView.alphabetToNum((String) jsp2.getValue());
                int jspval = (int) jsp.getValue();
                if (jsp2val + jspval > getCurrentTable().getColumnCount()) {
                    showMessageDialog(null, "Can't delete columns from here ", "Error!", JOptionPane.ERROR_MESSAGE);
                } else {
                    int x = PresentationController.blockWRefs(1,jsp2val+1,getCurrentTable().getRowCount(), jsp2val+jspval, SheetView.currentSheetName());
                    int confirm=-1;
                    if(x!=-1){
                        switch (x) {
                            case 0:
                                confirm = showConfirmDialog(null, "Watch out! There are references in the selected block, the content and references will be lost after the print. \n Are you sure?", "References!", JOptionPane.YES_NO_OPTION);
                                break;
                            case 1:
                                confirm = showConfirmDialog(null, "Watch out! There is information in the selected block, the content  will be lost after the print. \n Are you sure?", "Alert", YES_NO_OPTION);
                                break;
                            default:
                                break;
                        }
                    }
                    if (confirm == JOptionPane.YES_OPTION || x == -1) {
                        PresentationController.delCols(currentSheetName(), (Integer) jsp.getValue(), jsp2val);
                        for (int k = 0; k < (int) jsp.getValue(); k++) {
                            getCurrentTable().setAutoCreateColumnsFromModel(true);
                            TableColumn t = getCurrentTable().getColumnModel().getColumn(jsp2val);
                            getCurrentTable().removeColumn(t);
                            getCurrentTable().revalidate();
                        }
                        SheetView.updateColHeaders();
                        SheetView.rewriteModel(jsp2val);
                    }
                }
            }
        }
    }

    public static void deleteSheet() {
        int confirm=showConfirmDialog(null, "Look out! The current sheet will be deleted. \n Are you sure?", "Delete Sheet", JOptionPane.YES_NO_OPTION);
        if(confirm==JOptionPane.OK_OPTION) {
            PresentationController.delSheet(currentSheetName());
        }}

    public static void save() {
        if (PresentationController.isFileSaved())  {
            PresentationController.save();
            showMessageDialog(null, "Saved correctly !", "Saved", JOptionPane.INFORMATION_MESSAGE);
        }
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
            showMessageDialog(null, "Saved correctly !", "Saved", JOptionPane.INFORMATION_MESSAGE);
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
        selectFile.setApproveButtonText("Export");
        int result = selectFile.showSaveDialog(null);
        if(result == JFileChooser.APPROVE_OPTION) {
            System.out.println(selectFile.getCurrentDirectory());
            int desLength = selectFile.getFileFilter().getDescription().length();
            String ext =selectFile.getFileFilter().getDescription().substring(desLength-4,desLength-1);
            PresentationController.export(selectFile.getSelectedFile(), ext);
            showMessageDialog(null, "Exported correctly !", "Exported", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    public static void importFiles() {
        JFileChooser selectFile = new JFileChooser();
        FileFilter csv = new FileNameExtensionFilter("CSV Files (*.csv)", "csv");
        selectFile.addChoosableFileFilter(csv);
        selectFile.addChoosableFileFilter(new FileNameExtensionFilter("Text Files (*.txt)", "txt"));
        selectFile.setDialogTitle("Select file to import");
        selectFile.setFileFilter(csv);
        selectFile.setApproveButtonText("Import");
        int result = selectFile.showOpenDialog(null);
        if(result == JFileChooser.APPROVE_OPTION) {
            if (!MainMenu.menuUpdated) {
            System.out.println(selectFile.getCurrentDirectory());
            PresentationController.Import(selectFile.getSelectedFile());
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
            else {
                int confirm=showConfirmDialog(null, "Beware! Not saved changes will be lost. \n Are you sure?", "Unsaved changes!", JOptionPane.YES_NO_OPTION);
                if(confirm==JOptionPane.OK_OPTION) {
                    PresentationController.Import(selectFile.getSelectedFile());
                    BorderLayout blayout = (BorderLayout) MainMenu.frame.getContentPane().getLayout();
                    MainMenu.frame.getContentPane().remove(blayout.getLayoutComponent(BorderLayout.CENTER));
                    MainMenu.frame.repaint();
                    MainMenu.frame.getContentPane().add(BorderLayout.CENTER, SheetView.cambio());
                    MainMenu.frame.setTitle(PresentationController.getTitle() + " - POMC WORKSHEETS");
                    MainMenu.frame.setVisible(true);
                    SheetView.rewriteModel(0);
                }

            }
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
            else {
                int confirm=showConfirmDialog(null, "Beware! Not saved changes will be lost. \n Are you sure?", "Unsaved changes!", JOptionPane.YES_NO_OPTION);
                if(confirm==JOptionPane.OK_OPTION) {
                    PresentationController.open(selectFile.getSelectedFile());
                    BorderLayout blayout = (BorderLayout) MainMenu.frame.getContentPane().getLayout();
                    MainMenu.frame.getContentPane().remove(blayout.getLayoutComponent(BorderLayout.CENTER));
                    MainMenu.frame.repaint();
                    MainMenu.frame.getContentPane().add(BorderLayout.CENTER, SheetView.cambio());
                    MainMenu.frame.setTitle(PresentationController.getTitle() + " - POMC WORKSHEETS");
                    MainMenu.frame.setVisible(true);
                    SheetView.rewriteModel(0);
                }
            }
        }
    }

    public static void propertiesB() {
        String properties = "TITLE: " + PresentationController.getTitle() + "\n";
        properties += "SHEETS: " + PresentationController.getNumberofSheets() + "\n";
        if (PresentationController.isFileSaved()) {
            File fi = new File(PresentationController.getPath());
            try {
                BasicFileAttributes attr = Files.readAttributes(Path.of(PresentationController.getPath()), BasicFileAttributes.class);
                properties += "PATH: " + fi.getPath() + "\n";
                properties += "CREATED AT: " + attr.creationTime()+ "\n";
                properties += "TOTAL SPACE:" + fi.length() + "B \n";
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        showMessageDialog(null,properties, "Properties of the document", JOptionPane.PLAIN_MESSAGE);
    }
}
