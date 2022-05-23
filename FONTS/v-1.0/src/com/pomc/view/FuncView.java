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


      public static void addFind(){
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

                  PresentationController.createBlock(0,0,SheetView.getCurrentTable().getRowCount()-1,SheetView.getCurrentTable().getColumnCount()-1,SheetView.currentSheetName());
              }
              String[] cellFound = PresentationController.blockFind(value,SheetView.currentSheetName());
              if (cellFound.length>1) {
                  int row = Integer.parseInt(cellFound[0]);
                  int col = Integer.parseInt(cellFound[1]);
                  SheetView.getCurrentTable().changeSelection(row-1,col-1,false,false);

              }
              else{
                  showMessageDialog(null, "Value not found! :(\nTry again", "Error!", JOptionPane.ERROR_MESSAGE);
              }
          }

      }

      public static void addFindR(){
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
              if (SheetView.emptyBlock()) {
                  PresentationController.createBlock(0,0,SheetView.getCurrentTable().getRowCount()-1,SheetView.getCurrentTable().getColumnCount()-1,SheetView.currentSheetName());
              }
              String[] cellFound = PresentationController.blockFind(value.getText(),SheetView.currentSheetName());
              if (cellFound.length>1 && !value.getText().equals("")) {
                  Object[] replaces = PresentationController.blockFindAndReplace(value.getText(),replace.getText(),SheetView.currentSheetName());
                  if(replaces != null) {
                      for (int i = 0; i < replaces.length; i+=2) {
                          SheetView.getCurrentTable().getModel().setValueAt(replace.getText(),(int)replaces[i],(int)replaces[i+1]);
                      }
                  }
              }
              else{
                  showMessageDialog(null, "Value not found! :(\nTry again", "Error!", JOptionPane.ERROR_MESSAGE);
              }
          }
      }

      public static Integer[] addFloor(){
          Integer[] ids=new Integer[5];
          ids[4]=0;
//          JRadioButton yesPrint= new JRadioButton("yes",false);
//          JRadioButton noPrint= new JRadioButton("no",true);
//          JRadioButton yesRef= new JRadioButton("yes",false);
//          JRadioButton noRef= new JRadioButton("no",true);
            JCheckBox printb= new JCheckBox("Print the result in another block");
            JCheckBox ref= new JCheckBox("Reference the result");
          Object[] fields = new Object[]{
                  printb,
                  ref,
          };
          int result = JOptionPane.showConfirmDialog(
                  null,
                  fields,
                  "Floor",
                  JOptionPane.OK_CANCEL_OPTION,
                  JOptionPane.PLAIN_MESSAGE,
                  null

          );
          if (result == JOptionPane.OK_OPTION){

              int ulrowaux=PresentationController.blockFirstRow(SheetView.currentSheetName());
              int ulcolaux=PresentationController.blockFirstCol(SheetView.currentSheetName());
              int drrow=ulrowaux+SheetView.getCurrentTable().getSelectedRowCount();
              int drcol=ulcolaux+SheetView.getCurrentTable().getSelectedColumnCount();
              int ulrow=ulrowaux+1;
              int ulcol=ulcolaux+1;

//              System.out.println("ul: "+ulrow+" , "+ulcol);
//              System.out.println("dr: "+drrow+" , "+drcol);

                if(printb.isSelected()) {
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
                                "Floor",
                                JOptionPane.OK_CANCEL_OPTION,
                                JOptionPane.PLAIN_MESSAGE,
                                null

                        );
                        if (result2 == JOptionPane.OK_OPTION) {
                            ulrow = (Integer) ulr.getValue() ;
                            ulcol = (Integer) ulc.getValue() ;
                            drrow = (Integer) drr.getValue();
                            drcol = (Integer) drc.getValue();
                        }
                    }
                    ids[4]=-1;
                }
                boolean b=false;
                if(ref.isSelected()) b=true;
                PresentationController.blockFloor(ulrow,ulcol,drrow,drcol,b,SheetView.currentSheetName());
                ids[0]=ulrow-1;
                ids[1]=ulcol-1;
                ids[2]=drrow-1;
                ids[3]=drcol-1;
          }
          return ids;
      }



}
