package com.pomc.view;

import com.digidemic.unitof.I;
import com.pomc.classes.Block;
import com.pomc.classes.Cell;
import com.pomc.classes.Document;
import com.pomc.classes.Sheet;
import com.pomc.controller.DomainController;

import java.util.Vector;

public class PresentationController {
//    static Document doc;
//    static Block block;
    public static void newDoc(String d){
        DomainController.initializeDoc(d);
    }

    public static void newSheet(String s, int r,int c){
        DomainController.addSheet(s,r,c);
    }

    public static int getNumberofSheets(){ return DomainController.getNumSheet();}

    public static String[] getSheets(){
        return DomainController.showSheets();
    }

    public static int getSheetRows(int i,String name){
        return DomainController.sheetRows(name);
    }

    public static int getSheetCols(int i,String name){
        return DomainController.sheetCols(name);
    }

    public static boolean createBlock(int ulr,int ulc,int drr,int drc, String name){
        //FALTA id de la hoja en la que estamos
        Integer[] cells= new Integer[]{ulr,ulc,drr,drc};
        return DomainController.initializeBlock(cells,name);
    }

//    public static String[] showBlock(String sheetname){
//        return DomainController.currentBlockCells(sheetname);
//    }


    public static void changeSheetName(String name,String newname){
        DomainController.setSheetTitle(name,newname);
    }

    public static void addRow(String name, int num, int pos){
        DomainController.sheetAddRows(num,pos,name);
    }

    public static void delRow(String name, int num,int pos) {DomainController.sheetDelRows(num,pos,name);}

    public static void addCols(String name, int num, int pos){DomainController.sheetAddCols(num,pos,name);}

    public static void delCols(String name, int num,int pos){DomainController.sheetDeleteCols(num,pos,name);}

    public static void delSheet(String name){DomainController.deleteSheet(name);}

    public static void editedCell(int r,int c, String newValue, String SheetName){DomainController.editCell(r,c,newValue,SheetName);}

    public static void showTcells(String name){
        String[][] cellsContents = DomainController.currentBlockCells(name);
        for(int i = 0; i < cellsContents.length ; ++i) {
            for (int j = 0; j < cellsContents[0].length ; ++j) {
                System.out.print(" | " + cellsContents[i][j]);
            }
            System.out.print(" | \n");
        }
    }

//    public static String[][] currentBlock(String sheetname){
//      //  DomainController.getCurrentBlock(sheetname);
//
//    }

    public static String cellInfo(int r, int c, String name){
        String t= DomainController.getCellType(r,c,name);
        if(t.equals("N")) t="Number";
        else if (t.equals("T")) t="Text";
        else if (t.equals("D")) t= "Date";
        else if (t.equals("R")) t="Referenced Cell";
        String i= DomainController.getCellInfo(r,c,name);
        if (i.equals("")) return "";
        return " Type: "+t+" | Content: "+i;

    }

}
