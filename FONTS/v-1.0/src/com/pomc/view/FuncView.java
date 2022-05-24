package com.pomc.view;

import com.pomc.classes.Sheet;

import javax.swing.*;
import java.awt.event.ActionEvent;

import static javax.swing.JOptionPane.showConfirmDialog;
import static javax.swing.JOptionPane.showMessageDialog;

public class FuncView {


    public static void addSort() {
        if (SheetView.emptyBlock()) {
            PresentationController.createBlock(0, 0, SheetView.getCurrentTable().getRowCount() - 1, SheetView.getCurrentTable().getColumnCount() - 1, SheetView.currentSheetName());
        }
        SpinnerNumberModel snm = new SpinnerNumberModel(1, 1, PresentationController.blockCols(SheetView.currentSheetName()), 1);
        JSpinner jsp = new JSpinner(snm);
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
            if (optionList.getSelectedItem().equals("Ascending")) criteria = ">";
            int col = (int) jsp.getValue() - 1;
            //int ncol = PresentationController.blockFirstCol(currentSheetName()) + col - 1;
            Boolean sort = PresentationController.blockSort(col, criteria, SheetView.currentSheetName());
            if (!sort) {
                showMessageDialog(null, "Couldn't sort \nTry again", "Error!", JOptionPane.ERROR_MESSAGE);
            } else {
                SheetView.rewriteBlock();
            }
        }
    }


    public static void addFind() {
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
            if (SheetView.emptyBlock()) {

                PresentationController.createBlock(0, 0, SheetView.getCurrentTable().getRowCount() - 1, SheetView.getCurrentTable().getColumnCount() - 1, SheetView.currentSheetName());
            }
            String[] cellFound = PresentationController.blockFind(value, SheetView.currentSheetName());
            if (cellFound.length > 1) {
                int row = Integer.parseInt(cellFound[0]);
                int col = Integer.parseInt(cellFound[1]);
                SheetView.getCurrentTable().changeSelection(row - 1, col - 1, false, false);

            } else {
                showMessageDialog(null, "Value not found! :(\nTry again", "Error!", JOptionPane.ERROR_MESSAGE);
            }
        }

    }

    public static void addFindR() {
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
        if (result == JOptionPane.OK_OPTION) {
            if (SheetView.emptyBlock()) {
                PresentationController.createBlock(0, 0, SheetView.getCurrentTable().getRowCount() - 1, SheetView.getCurrentTable().getColumnCount() - 1, SheetView.currentSheetName());
            }
            String[] cellFound = PresentationController.blockFind(value.getText(), SheetView.currentSheetName());
            if (cellFound.length > 1 && !value.getText().equals("")) {
                Object[] replaces = PresentationController.blockFindAndReplace(value.getText(), replace.getText(), SheetView.currentSheetName());
                if (replaces != null) {
                    for (int i = 0; i < replaces.length; i += 2) {
                        SheetView.getCurrentTable().getModel().setValueAt(replace.getText(), (int) replaces[i], (int) replaces[i + 1]);
                    }
                }
            } else {
                showMessageDialog(null, "Value not found! :(\nTry again", "Error!", JOptionPane.ERROR_MESSAGE);
            }
        }
    }



    public static Integer[] addAOp() {
        Integer[] b1 = new Integer[4]; //segundo operando
        Integer[] b2 = new Integer[5];//donde se imprime
        b2[4] = 0;

        Object[] selectionValues = {"Addition", "Substraction", "Multiplication", "Division"};
        String initialSelection = "Addition";
        Object selection = JOptionPane.showInputDialog(null, "Choose the type of arithmetic operation",
                "Arithmetic operations", JOptionPane.QUESTION_MESSAGE, null, selectionValues, initialSelection);

        b2 = Operation(b1,b2,selection.toString());

        return b2;
    }

    public static Integer[] Operation(Integer[] b1, Integer[] b2, String op) {
        JCheckBox printb = new JCheckBox("Print the result in another block");
        JCheckBox ref = new JCheckBox("Reference the result");
        Object[] fields = new Object[]{
                printb,
                ref,
        };
        int result = JOptionPane.showConfirmDialog(
                null,
                fields,
                "Arithmetic operations : " + op,
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE,
                null

        );
        if (result == JOptionPane.OK_OPTION) {
            int trows = SheetView.getCurrentTable().getModel().getRowCount();
            int tcols = SheetView.getCurrentTable().getModel().getColumnCount();
            SpinnerNumberModel ulrm = new SpinnerNumberModel(1, 1, trows, 1);
            SpinnerNumberModel ulcm = new SpinnerNumberModel(1, 1, tcols, 1);
            JSpinner ulr = new JSpinner(ulrm);
            JSpinner ulc = new JSpinner(ulcm);

            SpinnerNumberModel drrm = new SpinnerNumberModel(trows, 1, trows, 1);
            SpinnerNumberModel drcm = new SpinnerNumberModel(tcols, 1, tcols, 1);
            JSpinner drr = new JSpinner(drrm);
            JSpinner drc = new JSpinner(drcm);

            Object[] fields2 = new Object[]{
                    "Select the upper left cell's row", ulr,
                    "Select the upper left cell's column", ulc,
                    "Select the down right cell's row", drr,
                    "Select the down right cell's column", drc,
            };
            int result2 = JOptionPane.showConfirmDialog(
                    null,
                    fields2,
                    "Select the second block for the addition",
                    JOptionPane.OK_CANCEL_OPTION,
                    JOptionPane.PLAIN_MESSAGE,
                    null

            );
            if (result2 == JOptionPane.OK_OPTION) {
                b1[0] = (Integer) ulr.getValue();
                b1[1] = (Integer) ulc.getValue();
                b1[2] = (Integer) drr.getValue();
                b1[3] = (Integer) drc.getValue();

                if (printb.isSelected()) {
                    int confirm = showConfirmDialog(null, "The information from the cells will be lost.\n Are you sure", "Alert!", JOptionPane.YES_NO_OPTION);
                    if (confirm == JOptionPane.YES_OPTION) {

                        SpinnerNumberModel ulrm2 = new SpinnerNumberModel(1, 1, trows, 1);
                        SpinnerNumberModel ulcm2 = new SpinnerNumberModel(1, 1, tcols, 1);
                        JSpinner ulr2 = new JSpinner(ulrm);
                        JSpinner ulc2 = new JSpinner(ulcm);

                        SpinnerNumberModel drrm2 = new SpinnerNumberModel(trows, 1, trows, 1);
                        SpinnerNumberModel drcm2 = new SpinnerNumberModel(tcols, 1, tcols, 1);
                        JSpinner drr2 = new JSpinner(drrm);
                        JSpinner drc2 = new JSpinner(drcm);

                        Object[] fields22 = new Object[]{
                                "Select the upper left cell's row", ulr,
                                "Select the upper left cell's column", ulc,
                                "Select the down right cell's row", drr,
                                "Select the down right cell's column", drc,
                        };
                        int result22 = JOptionPane.showConfirmDialog(
                                null,
                                fields22,
                                "Select the block where you want to print the result",
                                JOptionPane.OK_CANCEL_OPTION,
                                JOptionPane.PLAIN_MESSAGE,
                                null

                        );
                        if (result22 == JOptionPane.OK_OPTION) {
                            b2[0] = (Integer) ulr.getValue();
                            b2[1] = (Integer) ulc.getValue();
                            b2[2] = (Integer) drr.getValue();
                            b2[3] = (Integer) drc.getValue();
                        }
                        else return null;
                    }
                    b2[4] = -1;
                } else {
                    ref.setSelected(false);
                    b2[0] = b1[0];
                    b2[1] = b1[1];
                    b2[2] = b1[2];
                    b2[3] = b1[3];
                }
                boolean b = false;
                if (ref.isSelected()) b = true;
                switch(op) {
                    case "Addition":
                        PresentationController.blockAdd(b1, b2, b, SheetView.currentSheetName());
                        break;
                    case "Substraction":
                        PresentationController.blockSubs(b1, b2, b, SheetView.currentSheetName());
                        break;
                    case "Multiplication":
                        PresentationController.blockMult(b1, b2, b, SheetView.currentSheetName());
                        break;
                    default:
                        PresentationController.blockDiv(b1, b2, b, SheetView.currentSheetName());
                        break;
                }
                return b2;
            }
        }
        return null;
    }

    public static Integer[] addSOp() {
        Integer[] a = new Integer[2];
        Object[] selectionValues = {"Mean", "Median", "Variance", "Covariance","Standard Deviation", "Pearson correlation coefficient"};
        String initialSelection = "Mean";
        Object selection = JOptionPane.showInputDialog(null, "Choose the type of statistic operation",
                "Statistic operations", JOptionPane.QUESTION_MESSAGE, null, selectionValues, initialSelection);

        if (selection.toString().equals("Covariance") || selection.toString().equals("Pearson correlation coefficient") ) {
            a =operationS2(a,selection.toString());
        }
        else a =operationS1(selection.toString());
        return a;

    }
    public static Integer[] operationS1(String op) {
        Integer[] b1 = new Integer[2];
        JCheckBox ref = new JCheckBox("Reference the result ?");
        Object[] fields = new Object[]{
                ref,
        };
        int result = JOptionPane.showConfirmDialog(
                null,
                fields,
                "Statistic operations : " + op,
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE,
                null

        );
        if (result == JOptionPane.OK_OPTION) {
            int confirm = showConfirmDialog(null, "The information from the cell will be lost.\n Are you sure ?", "Alert!", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                int trows = SheetView.getCurrentTable().getModel().getRowCount();
                int tcols = SheetView.getCurrentTable().getModel().getColumnCount();
                SpinnerNumberModel ulrm = new SpinnerNumberModel(1, 1, trows, 1);
                SpinnerNumberModel ulcm = new SpinnerNumberModel(1, 1, tcols, 1);
                JSpinner cr = new JSpinner(ulrm);
                JSpinner cc = new JSpinner(ulcm);

                Object[] fields2 = new Object[]{
                        "Select the cell's row", cr,
                        "Select the cell's column", cc
                };
                int result2 = JOptionPane.showConfirmDialog(
                        null,
                        fields2,
                        "Select the cell to print the result",
                        JOptionPane.OK_CANCEL_OPTION,
                        JOptionPane.PLAIN_MESSAGE,
                        null

                );
                if (result2 == JOptionPane.OK_OPTION) {
                    b1[0] = (Integer) cr.getValue()-1;
                    b1[1] = (Integer) cc.getValue()-1;
                    boolean b = ref.isSelected();
                    switch(op) {
                        case "Mean":
                            PresentationController.blockMean(b1, b, SheetView.currentSheetName());
                            break;
                        case "Median":
                            PresentationController.blockMedian(b1, b, SheetView.currentSheetName());
                            break;
                        case "Variance":
                            PresentationController.blockVariance(b1, b, SheetView.currentSheetName());
                            break;
                        default:
                            PresentationController.blockSTD(b1, b, SheetView.currentSheetName());
                            break;
                    }
                    return b1;
                }
            }
        }
        return null;
    }


    public static Integer[] operationS2(Integer[] a,String op) {
        Integer[] b1 = new Integer[5];//donde se imprime
        b1[4] = 0;
        int trows = SheetView.getCurrentTable().getModel().getRowCount();
        int tcols = SheetView.getCurrentTable().getModel().getColumnCount();
        SpinnerNumberModel ulrm = new SpinnerNumberModel(1, 1, trows, 1);
        SpinnerNumberModel ulcm = new SpinnerNumberModel(1, 1, tcols, 1);
        JSpinner ulr = new JSpinner(ulrm);
        JSpinner ulc = new JSpinner(ulcm);

        SpinnerNumberModel drrm = new SpinnerNumberModel(trows, 1, trows, 1);
        SpinnerNumberModel drcm = new SpinnerNumberModel(tcols, 1, tcols, 1);
        JSpinner drr = new JSpinner(drrm);
        JSpinner drc = new JSpinner(drcm);

        Object[] fields2 = new Object[]{
                "Select the upper left cell's row", ulr,
                "Select the upper left cell's column", ulc,
                "Select the down right cell's row", drr,
                "Select the down right cell's column", drc,
        };
        int result2 = JOptionPane.showConfirmDialog(
                null,
                fields2,
                "Select the second block for " + op,
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE,
                null

        );
        if (result2 == JOptionPane.OK_OPTION) {
            b1[0] = (Integer) ulr.getValue();
            b1[1] = (Integer) ulc.getValue();
            b1[2] = (Integer) drr.getValue();
            b1[3] = (Integer) drc.getValue();

            JCheckBox ref = new JCheckBox("Reference the result ?");
            Object[] fields = new Object[]{
                    ref,
            };
            int result = JOptionPane.showConfirmDialog(
                    null,
                    fields,
                    "Statistic operations : " + op,
                    JOptionPane.OK_CANCEL_OPTION,
                    JOptionPane.PLAIN_MESSAGE,
                    null

            );
            if (result == JOptionPane.OK_OPTION) {
                int confirm = showConfirmDialog(null, "The information from the cell will be lost.\n Are you sure ?", "Alert!", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    JSpinner cr = new JSpinner(ulrm);
                    JSpinner cc = new JSpinner(ulcm);
                    Object[] fields3 = new Object[]{
                            "Select the cell's row", cr,
                            "Select the cell's column", cc
                    };
                    int result3 = JOptionPane.showConfirmDialog(
                            null,
                            fields3,
                            "Select the cell to print the result",
                            JOptionPane.OK_CANCEL_OPTION,
                            JOptionPane.PLAIN_MESSAGE,
                            null

                    );
                    if (result3 == JOptionPane.OK_OPTION) {
                        a[0] = (Integer) cr.getValue()-1;
                        a[1] = (Integer) cc.getValue()-1;
                        boolean b = ref.isSelected();
                        if ("Covariance".equals(op)) {
                            PresentationController.blockCovar(a,b1, b, SheetView.currentSheetName());
                        } else {
                            PresentationController.blockCPearson(a,b1, b, SheetView.currentSheetName());
                        }
                        return a;
                    }
                }
            }
        }
        return null;
    }

    public static Integer[] addSingleOp(String op) {
        Integer[] ids = new Integer[5];
        ids[4] = 0;
        JCheckBox printb = new JCheckBox("Print the result in another block");
        JCheckBox ref = new JCheckBox("Reference the result");
        Object[] fields = new Object[]{
                printb,
                ref,
        };
        int result = JOptionPane.showConfirmDialog(
                null,
                fields,
                op,
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE,
                null

        );
        if (result == JOptionPane.OK_OPTION) {

            int ulrowaux = PresentationController.blockFirstRow(SheetView.currentSheetName());
            int ulcolaux = PresentationController.blockFirstCol(SheetView.currentSheetName());
            int drrow = ulrowaux + SheetView.getCurrentTable().getSelectedRowCount();
            int drcol = ulcolaux + SheetView.getCurrentTable().getSelectedColumnCount();
            int ulrow = ulrowaux + 1;
            int ulcol = ulcolaux + 1;

//              System.out.println("ul: "+ulrow+" , "+ulcol);
//              System.out.println("dr: "+drrow+" , "+drcol);

            if (printb.isSelected()) {
                int confirm = showConfirmDialog(null, "The information from the cells will be lost.\n Are you sure", "Alert!", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    int trows = SheetView.getCurrentTable().getModel().getRowCount();
                    int tcols = SheetView.getCurrentTable().getModel().getColumnCount();
                    SpinnerNumberModel ulrm = new SpinnerNumberModel(1, 1, trows, 1);
                    SpinnerNumberModel ulcm = new SpinnerNumberModel(1, 1, tcols, 1);
                    JSpinner ulr = new JSpinner(ulrm);
                    JSpinner ulc = new JSpinner(ulcm);

                    SpinnerNumberModel drrm = new SpinnerNumberModel(trows, 1, trows, 1);
                    SpinnerNumberModel drcm = new SpinnerNumberModel(tcols, 1, tcols, 1);
                    JSpinner drr = new JSpinner(drrm);
                    JSpinner drc = new JSpinner(drcm);

                    Object[] fields2 = new Object[]{
                            "Select the upper left cell's row", ulr,
                            "Select the upper left cell's column", ulc,
                            "Select the down right cell's row", drr,
                            "Select the down right cell's column", drc,
                    };
                    int result2 = JOptionPane.showConfirmDialog(
                            null,
                            fields2,
                            op,
                            JOptionPane.OK_CANCEL_OPTION,
                            JOptionPane.PLAIN_MESSAGE,
                            null

                    );
                    if (result2 == JOptionPane.OK_OPTION) {
                        ulrow = (Integer) ulr.getValue();
                        ulcol = (Integer) ulc.getValue();
                        drrow = (Integer) drr.getValue();
                        drcol = (Integer) drc.getValue();
                    }
                }
                ids[4] = -1;
            } else ref.setSelected(false);
            boolean b = false;
            if (ref.isSelected()) b = true;
            switch (op) {
                case "floor": PresentationController.blockFloor(ulrow, ulcol, drrow, drcol,b,SheetView.currentSheetName());
                break;
                case "Day of the week": PresentationController.blockDOTW(ulrow, ulcol, drrow, drcol,b,SheetView.currentSheetName());
                break;
                default:
                    break;
            }
            ids[0] = ulrow - 1;
            ids[1] = ulcol - 1;
            ids[2] = drrow - 1;
            ids[3] = drcol - 1;
        }
        return ids;
    }



    public static Integer[] addSingleOpCrit(String op) {
        Integer[] ids = new Integer[5];
        ids[4] = 0;
        JCheckBox printb = new JCheckBox("Print the result in another block");
        JCheckBox ref = new JCheckBox("Reference the result");
        Object[] fields = new Object[]{
                printb,
                ref,
        };
        int result = JOptionPane.showConfirmDialog(
                null,
                fields,
                op,
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE,
                null

        );
        if (result == JOptionPane.OK_OPTION) {

            Object[] selectionValues = {"Day", "Month", "Year"};
            String initialSelection = "Day";
            Object selection = JOptionPane.showInputDialog(null, "Select the criteria",
                    "Extraction", JOptionPane.QUESTION_MESSAGE, null, selectionValues, initialSelection);


            int ulrowaux = PresentationController.blockFirstRow(SheetView.currentSheetName());
            int ulcolaux = PresentationController.blockFirstCol(SheetView.currentSheetName());
            int drrow = ulrowaux + SheetView.getCurrentTable().getSelectedRowCount();
            int drcol = ulcolaux + SheetView.getCurrentTable().getSelectedColumnCount();
            int ulrow = ulrowaux + 1;
            int ulcol = ulcolaux + 1;

//              System.out.println("ul: "+ulrow+" , "+ulcol);
//              System.out.println("dr: "+drrow+" , "+drcol);

            if (printb.isSelected()) {
                int confirm = showConfirmDialog(null, "The information from the cells will be lost.\n Are you sure", "Alert!", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    int trows = SheetView.getCurrentTable().getModel().getRowCount();
                    int tcols = SheetView.getCurrentTable().getModel().getColumnCount();
                    SpinnerNumberModel ulrm = new SpinnerNumberModel(1, 1, trows, 1);
                    SpinnerNumberModel ulcm = new SpinnerNumberModel(1, 1, tcols, 1);
                    JSpinner ulr = new JSpinner(ulrm);
                    JSpinner ulc = new JSpinner(ulcm);

                    SpinnerNumberModel drrm = new SpinnerNumberModel(trows, 1, trows, 1);
                    SpinnerNumberModel drcm = new SpinnerNumberModel(tcols, 1, tcols, 1);
                    JSpinner drr = new JSpinner(drrm);
                    JSpinner drc = new JSpinner(drcm);

                    Object[] fields2 = new Object[]{
                            "Select the upper left cell's row", ulr,
                            "Select the upper left cell's column", ulc,
                            "Select the down right cell's row", drr,
                            "Select the down right cell's column", drc,
                    };
                    int result2 = JOptionPane.showConfirmDialog(
                            null,
                            fields2,
                            op,
                            JOptionPane.OK_CANCEL_OPTION,
                            JOptionPane.PLAIN_MESSAGE,
                            null

                    );
                    if (result2 == JOptionPane.OK_OPTION) {
                        ulrow = (Integer) ulr.getValue();
                        ulcol = (Integer) ulc.getValue();
                        drrow = (Integer) drr.getValue();
                        drcol = (Integer) drc.getValue();
                    }
                }
                ids[4] = -1;
            } else ref.setSelected(false);
            boolean b = false;
            if (ref.isSelected()) b = true;
            switch (op) {
                case "Extract": PresentationController.blockExtract(ulrow, ulcol, drrow, drcol,b,selection.toString(),SheetView.currentSheetName());
                    break;
                default:
                    break;
            }
            ids[0] = ulrow - 1;
            ids[1] = ulcol - 1;
            ids[2] = drrow - 1;
            ids[3] = drcol - 1;
        }
        return ids;
    }

}


