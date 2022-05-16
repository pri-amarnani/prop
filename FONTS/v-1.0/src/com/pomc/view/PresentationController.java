package com.pomc.view;

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

//    public static void createBlock(int ulr,int ulc,int drr,int drc, int i){
//        //FALTA id de la hoja en la que estamos
//        Sheet sheet=doc.getDocSheets().elementAt(i);
//        Cell ul= sheet.getCell(ulr,ulc);
//        Cell dr= sheet.getCell(drr,drc);
//        block=sheet.SelectBlock(ul,dr);
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

}
