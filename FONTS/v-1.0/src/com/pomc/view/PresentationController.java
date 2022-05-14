package com.pomc.view;

import com.pomc.classes.Block;
import com.pomc.classes.Cell;
import com.pomc.classes.Document;
import com.pomc.classes.Sheet;

import java.util.Vector;

public class PresentationController {
    static Document doc;
    static Block block;
    public static void newDoc(String d){
        doc=null;
        doc=new Document(d);

    }

    public static void newSheet(String s, int r,int c){
        doc.createSheet(r,c,s);
    }

    public static int getNumberofSheets(){ return doc.getNumSheet();}

    public static String[] getSheets(){
        Vector<Sheet> aux= doc.getDocSheets();
        String [] names= new String[aux.size()];
        for (int i=0;i<aux.size();i++){
            Sheet s= aux.elementAt(i);
            names[i]=s.getTitle();
        }
        return names;
    }

    public static int getSheetRows(int i){
        return doc.getDocSheets().elementAt(i).getNumRows();
    }

    public static int getSheetCols(int i){
        return doc.getDocSheets().elementAt(i).getNumCols();
    }

    public static void createBlock(int ulr,int ulc,int drr,int drc, int i){
        //FALTA id de la hoja en la que estamos
        Sheet sheet=doc.getDocSheets().elementAt(i);
        Cell ul= sheet.getCell(ulr,ulc);
        Cell dr= sheet.getCell(drr,drc);
        block=sheet.SelectBlock(ul,dr);
    }

}
