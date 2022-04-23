package com.pomc.controller;
import com.pomc.classes.*;

import java.util.Vector;

public class DomainController {
    static Document doc ;
    static Sheet docSheet;

    static Block block;


    //------------------------------------------------DOCS FUNCTS--------------------------------------

    public static void initializeDoc(String title, String path) {
        doc = new Document(title,path);
    }

    //retorna el nom del doc
    public static String getDocName() {
        return doc.getTitle();
    }

    //returns an array of the sheets names and No sheets if there are no sheets
    public static String[] showSheets() {
        Vector<String> sheetsNames = new Vector<String>();
        if(doc.getDocSheets().size() == 0) return new String[]{"No Sheets"};
        else {
            for (int i = 0; i < doc.getDocSheets().size(); ++i) {
                sheetsNames.add(doc.getDocSheets().get(i).getTitle());
            }
            return sheetsNames.toArray(new String[0]);
        }
    }

    //adds Sheet with specified rows and columns to the doc.
    public static String addSheet(String title, Integer rows, Integer cols) {

        String newSheetTitle = title;
        Integer newSheetRows = rows;
        Integer newSheetCols = cols;
        if (title.equals("")) {
            newSheetTitle = "Sheet " + (doc.getDocSheets().size()+1);
        }
        if (rows == 0) {
            newSheetRows = 64;
        }
        if (cols == 0) {
            newSheetCols = 64;
        }
        doc.createSheet(newSheetRows, newSheetCols, newSheetTitle);
        return newSheetTitle;
    }

    //deletes a specified Sheet of the doc.
    public static void deleteSheet(String title) {
        doc.deleteSheet(title);
    }


        //----------------------------------SHEETS FUNCTS--------------------------------------------

    //Initializes the current Sheet
    public static void setDocSheet(String title){
        for (Sheet sheet : doc.getDocSheets()) {
            if (title.equals(sheet.getTitle())) {
                docSheet = sheet;
                return;
            }
        }
    }

    //gets current sheet number of rows
    public static Integer currentSheetRows(){
        return docSheet.getNumRows();
    }

    //gets current sheet number of columns
    public static Integer currentSheetCols(){
        return docSheet.getNumCols();
    }

    //gets an array with all the contents of the cells of the current sheet
    public static String[][] currentSheetCells(){
        Vector<Vector<String>> Contents = new Vector<Vector<String>>();
        for(int i = 0; i < docSheet.getNumRows(); ++i) {
                Contents.add(i,new Vector<String>());
            for (int j = 0; j < docSheet.getNumCols(); ++j) {
                String content = (String) docSheet.getCells().get(i).get(j).getInfo();
                if (docSheet.getCells().get(i).get(j).getInfo() == null) { content = " - "; }
                Contents.get(i).add(j,content);
            }
        }
        Vector<String[]> cellsContents = new Vector<String[]>();
        for (int k = 0; k < Contents.size(); ++k) {
            cellsContents.add(Contents.get(k).toArray(new String[Contents.get(k).size()]));
        }
        return cellsContents.toArray(new String[Contents.size()][]);
    }

    //adds an empty row to the specified position
    public static void currentSheetAddRow(Integer num){
        docSheet.NewRow(num);
    }

    //adds an empty col to the specified position
    public static void currentSheetAddCol(Integer num){
        docSheet.NewColumn(num);
    }

    //deletes a row in the specified position
    public static void currentSheetDeleteRow(Integer num){
        docSheet.DeleteRow(num);
    }

    //deletes a column in the specified position
    public static void currentSheetDeleteCol(Integer num){
        docSheet.DeleteColumn(num);
    }

    //-----------------------------------BLOCK FUNCTIONS------------------------------
    public static void initializeBlock() {

    }

}
