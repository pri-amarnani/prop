package com.pomc.view;

import com.pomc.classes.Sheet;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.time.temporal.JulianFields;

import static javax.swing.JOptionPane.*;

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



    public static Integer[] addAOp(String func) {
        Integer[] b1 = {-1,-1,-1,-1}; //segundo operando
        Integer[] b2 = {-1,-1,-1,-1,0};//donde se imprime

        if(func.equals("arit")) {
            Object[] selectionValues = {"Addition", "Substraction", "Multiplication", "Division"};
            String initialSelection = "Addition";
            Object selection = JOptionPane.showInputDialog(null, "Choose the type of arithmetic operation",
                    "Arithmetic operations", JOptionPane.QUESTION_MESSAGE, null, selectionValues, initialSelection);
            if (selection != null) {
                b2 = Operation(b1, b2, selection.toString());
            }
        }
        else{
            b2=Operation(b1,b2,func);
        }
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
                op,
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE,
                null

        );
        if (result == JOptionPane.OK_OPTION) {
            int trows = SheetView.getCurrentTable().getModel().getRowCount();
            int tcols = SheetView.getCurrentTable().getModel().getColumnCount();
            String [] alphCols=numtoAlphabetCols(getCols(tcols));

            SpinnerNumberModel ulrm = new SpinnerNumberModel(1, 1, trows, 1);
            SpinnerListModel ulcm= new SpinnerListModel(alphCols);
            JSpinner ulr = new JSpinner(ulrm);
            JSpinner ulc = new JSpinner(ulcm);

            SpinnerNumberModel drrm = new SpinnerNumberModel(trows, 1, trows, 1);
            SpinnerListModel drcm= new SpinnerListModel(alphCols);
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
                    "Select the second block ",
                    JOptionPane.OK_CANCEL_OPTION,
                    JOptionPane.PLAIN_MESSAGE,
                    null

            );
            if (result2 == JOptionPane.OK_OPTION) {
                b1[0] = (Integer) ulr.getValue();
                b1[1] = SheetView.alphabetToNum((String) ulc.getValue())+1;
                b1[2] = (Integer) drr.getValue();
                b1[3] = SheetView.alphabetToNum((String) drc.getValue())+1;

                if (printb.isSelected()) {


                        SpinnerNumberModel ulrm2 = new SpinnerNumberModel(1, 1, trows, 1);
                        SpinnerListModel ulcm2 = new SpinnerListModel(alphCols);
                        JSpinner ulr2 = new JSpinner(ulrm2);
                        JSpinner ulc2 = new JSpinner(ulcm2);

                        SpinnerNumberModel drrm2 = new SpinnerNumberModel(trows, 1, trows, 1);
                        SpinnerListModel drcm2 =  new SpinnerListModel(alphCols);
                        JSpinner drr2 = new JSpinner(drrm2);
                        JSpinner drc2 = new JSpinner(drcm2);

                        Object[] fields22 = new Object[]{
                                "Select the upper left cell's row", ulr2,
                                "Select the upper left cell's column", ulc2,
                                "Select the down right cell's row", drr2,
                                "Select the down right cell's column", drc2,
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
                            int ul=(Integer) ulr2.getValue();
                            int uc=SheetView.alphabetToNum((String) ulc2.getValue())+1;
                            int dl= (Integer) drr2.getValue();
                            int dc=SheetView.alphabetToNum((String) drc2.getValue())+1;
                            int x=PresentationController.blockWRefs(ul,uc,dl,dc,SheetView.currentSheetName());
                            int confirm=-1;
                            if(x!=-1) {
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
                                b2[0] = (Integer) ulr2.getValue();
                                b2[1] = SheetView.alphabetToNum((String) ulc2.getValue())+1;
                                b2[2] = (Integer) drr2.getValue();
                                b2[3] = SheetView.alphabetToNum((String) drc2.getValue())+1;
                            }
                                else return null;
                            b2[4] = -1;
                        }
                        else return null;

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
                    case "Concatenate":
                        PresentationController.blockConcat(b1,b2,b,SheetView.currentSheetName());
                        break;
                    default:
                        PresentationController.blockDiv(b1, b2, b, SheetView.currentSheetName());
                        break;
                }

            }
        }
        return b2;
    }

    public static Integer[] addSOp(String func) {
        Integer[] a = {-1,-1};
        if(func.equals("stat")) {
            Object[] selectionValues = {"Mean", "Median", "Variance", "Covariance", "Standard Deviation", "Pearson correlation coefficient"};
            String initialSelection = "Mean";
            Object selection = JOptionPane.showInputDialog(null, "Choose the type of statistic operation",
                    "Statistic operations", JOptionPane.QUESTION_MESSAGE, null, selectionValues, initialSelection);
            if (selection != null) {
                if (selection.toString().equals("Covariance") || selection.toString().equals("Pearson correlation coefficient")) {
                    a = operationS2(a, selection.toString());
                } else a = operationS1(selection.toString(),"",-1);
            }
        }
        else if (func.equals("maxmin")){
            Object[] selectionValues = {"Max", "Min"};
            String initialSelection = "Max";
            Object selection = JOptionPane.showInputDialog(null, "Choose the operation",
                    "Max/Min", JOptionPane.QUESTION_MESSAGE, null, selectionValues, initialSelection);
            if (selection != null) {
                 a = operationS1(selection.toString(),"",-1);
            }
        }
        else{
            Object[] selectionValues = {"==", ">","<",">=","<="};
            String initialSelection = "==";
            Object selection = JOptionPane.showInputDialog(null, "Choose the criteria",
                    "Criteria", JOptionPane.QUESTION_MESSAGE, null, selectionValues, initialSelection);
            JTextField eq= new JTextField();
            Object[] fields= new Object[]{
                    "The choosen criteria is: ",selection,
                    "Choose the number to compare",eq,
            };

            int countiff = JOptionPane.showConfirmDialog(
                    null,
                    fields,
                    "Count if...",
                    JOptionPane.OK_CANCEL_OPTION,
                    JOptionPane.PLAIN_MESSAGE,
                    null

            );

            if (countiff==JOptionPane.OK_OPTION) {
                a = operationS1("Count if",selection.toString(),Double.parseDouble(eq.getText()));
            }
            else return null;
        }
        return a;

    }
    public static Integer[] operationS1(String op, String crit,double eq) {
        Integer[] b1 = {-1,-1};
        JCheckBox ref = new JCheckBox("Reference the result ?");
        Object[] fields = new Object[]{
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
                int trows = SheetView.getCurrentTable().getModel().getRowCount();
                int tcols = SheetView.getCurrentTable().getModel().getColumnCount();
                String [] alphCols=numtoAlphabetCols(getCols(tcols));
                SpinnerNumberModel ulrm = new SpinnerNumberModel(1, 1, trows, 1);
                SpinnerListModel ulcm = new SpinnerListModel(alphCols);
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
                    b1[0] = (Integer) cr.getValue();
                    b1[1] = SheetView.alphabetToNum((String) cc.getValue()) + 1;
                    int x = PresentationController.blockWRefs(b1[0], b1[1], b1[0], b1[1], SheetView.currentSheetName());
                    int confirm = -1;
                    switch (x) {
                        case 0:
                            confirm = showConfirmDialog(null, "Watch out! There are references in the selected block, the content and references will be lost after the print. \n Are you sure?", "References!", JOptionPane.YES_NO_OPTION);
                            break;
                        case 1:
                            confirm = showConfirmDialog(null, "Watch out! There is information in the selected block, the content  will be lost after the print. \n Are you sure?", "Alert", YES_NO_OPTION);
                            break;
                        case -1:
                            confirm = YES_OPTION;
                            break;
                    }
                    if (confirm == JOptionPane.YES_OPTION) {
                        boolean b = ref.isSelected();
                        switch (op) {
                            case "Mean":
                                PresentationController.blockMean(b1, b, SheetView.currentSheetName());
                                break;
                            case "Median":
                                PresentationController.blockMedian(b1, b, SheetView.currentSheetName());
                                break;
                            case "Variance":
                                PresentationController.blockVariance(b1, b, SheetView.currentSheetName());
                                break;
                            case "Max":
                                PresentationController.blockMax(b1, b, SheetView.currentSheetName());
                                break;
                            case "Min":
                                PresentationController.blockMin(b1, b, SheetView.currentSheetName());
                                break;
                            case "Count if":
                                PresentationController.blockCountIf(b1, b, SheetView.currentSheetName(), eq, crit);
                                break;
                            default:
                                PresentationController.blockSTD(b1, b, SheetView.currentSheetName());
                                break;
                        }
                        return b1;
                    }else return null;
                }else return null;
            }else return null;
        }


    public static Integer[] operationS2(Integer[] a,String op) {
        Integer[] b1 = {-1, -1, -1, -1, 0};//donde se imprime
        int trows = SheetView.getCurrentTable().getModel().getRowCount();
        int tcols = SheetView.getCurrentTable().getModel().getColumnCount();
        String[] alphCols = numtoAlphabetCols(getCols(tcols));
        SpinnerNumberModel ulrm = new SpinnerNumberModel(1, 1, trows, 1);
        SpinnerListModel ulcm = new SpinnerListModel(alphCols);
        JSpinner ulr = new JSpinner(ulrm);
        JSpinner ulc = new JSpinner(ulcm);

        SpinnerNumberModel drrm = new SpinnerNumberModel(trows, 1, trows, 1);
        SpinnerListModel drcm = new SpinnerListModel(alphCols);
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
            b1[1] = SheetView.alphabetToNum((String) ulc.getValue()) + 1;
            b1[2] = (Integer) drr.getValue();
            b1[3] = SheetView.alphabetToNum((String) drc.getValue()) + 1;

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
                    a[0] = (Integer) cr.getValue();
                    a[1] = SheetView.alphabetToNum((String) cc.getValue()) + 1;
                    int x = PresentationController.blockWRefs(a[0], a[1], a[0], a[1], SheetView.currentSheetName());
                    int confirm = -1;
                        switch (x) {
                            case 0:
                                confirm = showConfirmDialog(null, "Watch out! There are references in the selected block, the content and references will be lost after the print. \n Are you sure?", "References!", JOptionPane.YES_NO_OPTION);
                                break;
                            case 1:
                                confirm = showConfirmDialog(null, "Watch out! There is information in the selected block, the content  will be lost after the print. \n Are you sure?", "Alert", YES_NO_OPTION);
                                break;
                            case -1:
                                confirm = YES_OPTION;
                                break;
                        }
                        if (confirm == JOptionPane.YES_OPTION) {
                            boolean b = ref.isSelected();
                            if ("Covariance".equals(op)) {
                                PresentationController.blockCovar(a, b1, b, SheetView.currentSheetName());
                            } else {
                                PresentationController.blockCPearson(a, b1, b, SheetView.currentSheetName());
                            }
                            return a;
                        } else return null;
                } else return null;
            } else return null;
        }else return null;
    }

    public static Integer[] addSingleOp(String op) {
        Integer[] ids = {-1,-1,-1,-1,0};
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

            if (printb.isSelected()) {
                int trows = SheetView.getCurrentTable().getModel().getRowCount();
                int tcols = SheetView.getCurrentTable().getModel().getColumnCount();
                String[] alphCols = numtoAlphabetCols(getCols(tcols));
                SpinnerNumberModel ulrm = new SpinnerNumberModel(1, 1, trows, 1);
                SpinnerListModel ulcm = new SpinnerListModel(alphCols);
                JSpinner ulr = new JSpinner(ulrm);
                JSpinner ulc = new JSpinner(ulcm);

                SpinnerNumberModel drrm = new SpinnerNumberModel(trows, 1, trows, 1);
                SpinnerListModel drcm = new SpinnerListModel(alphCols);
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
                    int ul = (Integer) ulr.getValue();
                    int uc = SheetView.alphabetToNum((String) ulc.getValue()) + 1;
                    int dl = (Integer) drr.getValue();
                    int dc = SheetView.alphabetToNum((String) drc.getValue()) + 1;
                    int x = PresentationController.blockWRefs(ul, uc, dl, dc, SheetView.currentSheetName());
                    int confirm = -1;
                    if (x != -1) {
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
                            ulrow = (Integer) ulr.getValue();
                            ulcol = SheetView.alphabetToNum((String) ulc.getValue()) + 1;
                            drrow = (Integer) drr.getValue();
                            drcol = SheetView.alphabetToNum((String) drc.getValue()) + 1;
                        } else return null;
                        ids[4] = -1;
                } else return null;
            }
            else ref.setSelected(false);
            boolean b = false;
            if (ref.isSelected()) b = true;
            switch (op) {
                case "floor": PresentationController.blockFloor(ulrow, ulcol, drrow, drcol,b,SheetView.currentSheetName());
                break;
                case "Day of the week": PresentationController.blockDOTW(ulrow, ulcol, drrow, drcol,b,SheetView.currentSheetName());
                break;
                case "ceil": PresentationController.blockCeil(ulrow,ulcol,drrow,drcol,b,SheetView.currentSheetName());
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


    public static Integer[] moveBlock() {
        Integer[] ids = {-1, -1, -1, -1};
        JCheckBox ref = new JCheckBox("Reference the result");
        Object[] fields = new Object[]{
                ref,
        };
        int result = JOptionPane.showConfirmDialog(
                null,
                fields,
                "Move block",
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

            int trows = SheetView.getCurrentTable().getModel().getRowCount();
            int tcols = SheetView.getCurrentTable().getModel().getColumnCount();
            String[] alphCols = numtoAlphabetCols(getCols(tcols));

            SpinnerNumberModel ulrm = new SpinnerNumberModel(1, 1, trows, 1);
            SpinnerListModel ulcm = new SpinnerListModel(alphCols);
            JSpinner ulr = new JSpinner(ulrm);
            JSpinner ulc = new JSpinner(ulcm);

            SpinnerNumberModel drrm = new SpinnerNumberModel(trows, 1, trows, 1);
            SpinnerListModel drcm = new SpinnerListModel(alphCols);
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
                    "Move block to: ",
                    JOptionPane.OK_CANCEL_OPTION,
                    JOptionPane.PLAIN_MESSAGE,
                    null
            );
            if (result2 == JOptionPane.OK_OPTION) {
                ulrow = (Integer) ulr.getValue();
                ulcol = SheetView.alphabetToNum((String) ulc.getValue())+1;
                drrow = (Integer) drr.getValue();
                drcol = SheetView.alphabetToNum((String) drc.getValue())+1;
                int x = PresentationController.blockWRefs(ulrow, ulcol, drrow, drcol, SheetView.currentSheetName());
                int confirm = -1;
                if (x != -1) {
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
                    boolean b = false;
                    if (ref.isSelected()) b = true;
                    PresentationController.moveBlock(ulrow, ulcol, drrow, drcol, b, SheetView.currentSheetName());
                    ids[0] = ulrow - 1;
                    ids[1] = ulcol - 1;
                    ids[2] = drrow - 1;
                    ids[3] = drcol - 1;
                    return ids;
                } else return null;
            } else return null;
        } else return null;
    }





    public static Integer[] addSingleOpCrit(String op) {
        Integer[] ids = {-1,-1,-1,-1,0};
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
            Object selection = "-1";
            String s;
            String from = "";
            String to = "";
            if (op.equals("Extract")) {
                Object[] selectionValues = {"Day", "Month", "Year"};
                String initialSelection = "Day";
                selection = JOptionPane.showInputDialog(null, "Select the criteria",
                        "Extraction", JOptionPane.QUESTION_MESSAGE, null, selectionValues, initialSelection);
            } else if (op.equals("Convert")) {
                Object[] selectionValues = {"mTOcm", "mTOkm", "mTOinches", "inchesTOm", "kmTOm", "kmTOcm", "cmTOm", "cmTOkm"};
                String initialSelection = "mTOcm";
                selection = JOptionPane.showInputDialog(null, "Select the criteria",
                        "Convert", JOptionPane.QUESTION_MESSAGE, null, selectionValues, initialSelection);
                if (selection != null) {
                    s = selection.toString();
                    String[] sconvert = s.split("TO");
                    from = sconvert[0];
                    to = sconvert[1];
                }
            } else if (op.equals("Length")) {
                Object[] selectionValues = {"Words", "Letters", "Characters"};
                String initialSelection = "Words";
                selection = JOptionPane.showInputDialog(null, "Select the criteria",
                        "Text length", JOptionPane.QUESTION_MESSAGE, null, selectionValues, initialSelection);
            }

            int ulrowaux = PresentationController.blockFirstRow(SheetView.currentSheetName());
            int ulcolaux = PresentationController.blockFirstCol(SheetView.currentSheetName());
            int drrow = ulrowaux + SheetView.getCurrentTable().getSelectedRowCount();
            int drcol = ulcolaux + SheetView.getCurrentTable().getSelectedColumnCount();
            int ulrow = ulrowaux + 1;
            int ulcol = ulcolaux + 1;

//              System.out.println("ul: "+ulrow+" , "+ulcol);
//              System.out.println("dr: "+drrow+" , "+drcol);

            if (selection != null) {
                if (printb.isSelected()) {
                    int trows = SheetView.getCurrentTable().getModel().getRowCount();
                    int tcols = SheetView.getCurrentTable().getModel().getColumnCount();
                    String[] alphCols = numtoAlphabetCols(getCols(tcols));
                    SpinnerNumberModel ulrm = new SpinnerNumberModel(1, 1, trows, 1);
                    SpinnerListModel ulcm = new SpinnerListModel(alphCols);
                    JSpinner ulr = new JSpinner(ulrm);
                    JSpinner ulc = new JSpinner(ulcm);

                    SpinnerNumberModel drrm = new SpinnerNumberModel(trows, 1, trows, 1);
                    SpinnerListModel drcm = new SpinnerListModel(alphCols);
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
                        int ul = (Integer) ulr.getValue();
                        int uc = SheetView.alphabetToNum((String) ulc.getValue()) + 1;
                        int dl = (Integer) drr.getValue();
                        int dc = SheetView.alphabetToNum((String) drc.getValue()) + 1;
                        int x = PresentationController.blockWRefs(ul, uc, dl, dc, SheetView.currentSheetName());
                        int confirm = -1;
                        if (x != -1) {
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
                                ulrow = (Integer) ulr.getValue();
                                ulcol = SheetView.alphabetToNum((String) ulc.getValue()) + 1;
                                drrow = (Integer) drr.getValue();
                                drcol = SheetView.alphabetToNum((String) drc.getValue()) + 1;
                            } else return null;
                            ids[4] = -1;
                    } else return null;
                } else ref.setSelected(false);
                boolean b = false;
                if (ref.isSelected()) b = true;
                if (selection != null) {
                    switch (op) {
                        case "Extract":
                            PresentationController.blockExtract(ulrow, ulcol, drrow, drcol, b, selection.toString(), SheetView.currentSheetName());
                            break;
                        case "Convert":
                            PresentationController.blockConvert(ulrow, ulcol, drrow, drcol, b, from, to, SheetView.currentSheetName());
                            break;
                        case "Length":
                            PresentationController.blockLength(ulrow, ulcol, drrow, drcol, b, SheetView.currentSheetName(), selection.toString());
                            break;
                        default:
                            break;
                    }

                    ids[0] = ulrow - 1;
                    ids[1] = ulcol - 1;
                    ids[2] = drrow - 1;
                    ids[3] = drcol - 1;
                }
            }
        }
        return ids;
    }

    public static void addReplaceWC(){
        Object[] selectionValues = {"All caps","All lowercase","Cap first letter"};
        String initialSelection = "All caps";
        Object selection = JOptionPane.showInputDialog(null, "Select the criteria",
                "Replace with criteria", JOptionPane.QUESTION_MESSAGE, null, selectionValues, initialSelection);
        if(selection!=null) PresentationController.blockReplaceWC(SheetView.currentSheetName(),selection.toString());
    }

    public static void unitOp(){
        Object[] selectionValues = {"Addition","Substraction","Multiplication","Division","Modulus","Power"};
        JComboBox selection= new JComboBox<>(selectionValues);
        JTextField num= new JTextField();
        Object[] fields = new Object[]{
                "Select the operation: ", selection,
                "Enter a number: ",num,
        };
        int result=showConfirmDialog(
                null,
                fields,
                "Unitary operations",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE,
                null
        );
        if(result==JOptionPane.OK_OPTION){
            PresentationController.blockOpBlock((String) selection.getSelectedItem(),Double.parseDouble(num.getText()),SheetView.currentSheetName());
        }
    }

    public static void modifyB(){
        if (SheetView.emptyBlock()) {
            PresentationController.createBlock(0, 0, SheetView.getCurrentTable().getRowCount() - 1, SheetView.getCurrentTable().getColumnCount() - 1, SheetView.currentSheetName());
        }
        JTextField o= new JTextField();
        int pop= JOptionPane.showConfirmDialog(
                null,
                o,
                "Modify block",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE,
                null

        );
        if(pop==OK_OPTION) PresentationController.blockModify(o.getText(),SheetView.currentSheetName());
    }



    public static String[] numtoAlphabetCols(Integer[] cols){
        String[] result= new String[cols.length];
        for (int i = 0; i < cols.length; i++) {
            result[i]=numToAlphabet(cols[i]);
        }
        return result;
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

    public static Integer[] getCols(int n){
        Integer[] result= new Integer[n];
        for (int i = 0; i < n; i++) {
            result[i]=i;
        }
        return result;
    }

}


