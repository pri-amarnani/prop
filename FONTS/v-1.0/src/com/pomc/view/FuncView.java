package com.pomc.view;

import com.pomc.classes.Sheet;

import javax.swing.*;
import java.awt.event.ActionEvent;

import static javax.swing.JOptionPane.showMessageDialog;

public class FuncView {


      public static void addSort() {
          if (SheetView.emptyBlock()) {
              System.out.println("NO BLOCK :///");
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
                  System.out.println("NO BLOCK :///");
                  PresentationController.createBlock(0,0,SheetView.getCurrentTable().getRowCount()-1,SheetView.getCurrentTable().getColumnCount()-1,SheetView.currentSheetName());
              }
              String[] cellFound = PresentationController.blockFind(value,SheetView.currentSheetName());
              if (cellFound.length>1) {
                  System.out.println("FOUNDD");
                  int row = Integer.parseInt(cellFound[0]);
                  int col = Integer.parseInt(cellFound[1]);
                  SheetView.getCurrentTable().changeSelection(row-1,col-1,false,false);
                  System.out.println("SLECTEDDD");
              }
              else{
                  System.out.println("WTFFFF");
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

      public static void addFloor(){

      }



}
