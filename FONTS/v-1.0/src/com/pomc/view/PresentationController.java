package com.pomc.view;

import com.digidemic.unitof.I;
import com.pomc.classes.Block;
import com.pomc.classes.Cell;
import com.pomc.classes.Document;
import com.pomc.classes.Sheet;
import com.pomc.controller.DomainController;

import java.io.File;
import java.util.Vector;

public class PresentationController {

    private static String path = null;


    //------------------------------DOC FUNCTIONS -------------------------------------

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




    //_-------------------------SHEET FUNCTIONS -------------------------------

    public static int getSheetRows(int i,String name){
        return DomainController.sheetRows(name);
    }

    public static int getSheetCols(int i,String name){
        return DomainController.sheetCols(name);
    }
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



    //------------------------BLOCK FUNCTIONS-----------------------------------

    public static boolean createBlock(int ulr,int ulc,int drr,int drc, String name){
        //FALTA id de la hoja en la que estamos

        Integer[] cells= new Integer[]{ulr,ulc,drr,drc};
        return DomainController.initializeBlock(cells,name);
    }

    public static int blockRows(String sheetName) {return DomainController.blockRows(sheetName);};
    public static int blockCols(String sheetName) {return DomainController.blockColumns(sheetName);};

    public static int blockFirstRow(String sheetName) {return DomainController.blockFirstRow(sheetName);};
    public static int blockFirstCol(String sheetName) {return DomainController.blockFirstCol(sheetName);};

    public static String[] blockFind(String value, String sheetName){ return DomainController.blockFind(value,sheetName);}

    public static Object[] blockFindAndReplace(String find, String replace, String sheetName){ return DomainController.blockFindAndReplace(find,replace,sheetName);}

    public static boolean blockSort(int nCol, String criteria, String sheetName) {return DomainController.blockSort(nCol,criteria,sheetName); }

    public static String blockType(String sheetName) {return DomainController.blockType(sheetName);}

    public static void blockFloor(int ulr, int ulc, int drr, int drc, boolean ref,String Sheetname){
        Integer[] b_ids= {ulr,ulc,drr,drc};
        DomainController.blockFloor(b_ids,ref,Sheetname);
    }


    //-----------------------CELL FUNCTIONS--------------------------------
    public static void editedCell(int r,int c, String newValue, String SheetName){DomainController.editCell(r,c,newValue,SheetName);}

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

    public static String getCellInfo(int r, int c, String name){
        String t= DomainController.getCellType(r,c,name);
        String i;
        if (t.equals("R")) i =DomainController.getCellContent(r,c,name);
        else {
            i= DomainController.getCellInfo(r,c,name);
        }
        return i;
    }

    public static void showTcells(String name){
        String[][] cellsContents = DomainController.currentBlockCells(name);
        for(int i = 0; i < cellsContents.length ; ++i) {
            for (int j = 0; j < cellsContents[0].length ; ++j) {
                System.out.print(" | " + cellsContents[i][j]);
            }
            System.out.print(" | \n");
        }
    }


    //-------------------------FILE MANAGEMENT-----------------------------

    public static boolean isFileSaved() {
        return (path != null);
    }

    public static void save() {
        //TODO enviar path a persistencia
    }

    public static void saveAs(File selectedFile) {
        //TODO enviar path a persistencia y guardar current path
        String[] extension = selectedFile.getName().split("\\.(?=[^\\.]+$)");
        if (extension.length> 1){
            if(extension[1].equals("pomc")) path= selectedFile.getName();
            else {
                path = selectedFile.getName()+".pomc";
            }
        }
        else path = selectedFile.getName()+".pomc";
        System.out.println(path);
    }

    public static void export(File selectedFile) {
        //TODO enviar path a persistencia y formato
    }
}
