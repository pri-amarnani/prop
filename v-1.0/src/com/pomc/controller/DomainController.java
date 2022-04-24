package com.pomc.controller;
import com.pomc.classes.*;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Vector;

import static java.lang.String.valueOf;

public class DomainController {
    static Document doc ;
    static Sheet docSheet;

    static Block block;


    //------------------------------------------------DOCS FUNCTS--------------------------------------

    public static void initializeDoc(String title) {
        doc = new Document(title);
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
        Integer newSheetRows = rows;        //TODO comprobar sheet existente
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
                String content;
                Cell cell = docSheet.getCells().get(i).get(j);
                if (cell.getInfo() == null) { content = " - "; }
                else if (cell.getInfo().getClass() == Double.class) content = String.valueOf(cell.getInfo());
                else if (cell.getInfo().getClass()== LocalDate.class) content = cell.getInfo().toString();
                else content = (String) cell.getInfo();
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
        docSheet.NewRow(num -1);
    }

    //adds an empty col to the specified position
    public static void currentSheetAddCol(Integer num){
        docSheet.NewColumn(num -1);
    }

    //deletes a row in the specified position
    public static void currentSheetDeleteRow(Integer num){
        docSheet.DeleteRow(num -1);
    }

    //deletes a column in the specified position
    public static void currentSheetDeleteCol(Integer num){
        docSheet.DeleteColumn(num -1);

    }

    public static void editCell(int i, int j, String value) {
        Object parsedValue = Parse(value);
        Cell cellToEdit = docSheet.getCell(i-1,j-1);
        docSheet.change_value(cellToEdit,parsedValue);
    }
    //-----------------------------------BLOCK FUNCTIONS------------------------------

    //Current block initialized, as it's minimum is 1
    public static Boolean initializeBlock(Integer[] blockCells) {
        if (blockCells[0] <= blockCells[2]  && blockCells[1]  <= blockCells[3] ) {
            Cell c1 = docSheet.getCell(blockCells[0] -1, blockCells[1] -1);
            Cell c2 = docSheet.getCell(blockCells[2] -1, blockCells[3] -1);
            block = docSheet.SelectBlock(c1,c2);
            return true;
        }
            return false;
    }
    public static Block createBlock(Integer[] blockCells) {
        if (blockCells[0] <= blockCells[2]  && blockCells[1]  <= blockCells[3] ) {
            Cell c1 = docSheet.getCell(blockCells[0] -1, blockCells[1] -1);
            Cell c2 = docSheet.getCell(blockCells[2] -1, blockCells[3] -1);
            return docSheet.create_block(c1,c2);
        }
            return null;
    }

    public static String[][] currentBlockCells(){
        Vector<Vector<String>> Contents = new Vector<Vector<String>>();
        for(int i = 0; i < docSheet.getSelectedBlock().number_rows(); ++i) {
            Contents.add(i,new Vector<String>());
            for (int j = 0; j < docSheet.getSelectedBlock().number_cols(); ++j) {
                String content;
                Cell cell = docSheet.getSelectedBlock().getCell(i,j);
                if (cell.getInfo() == null) { content = " - "; }
                else if (cell.getInfo().getClass() == Double.class) content = String.valueOf(cell.getInfo());
                else if (cell.getInfo().getClass()== LocalDate.class) content = cell.getInfo().toString();
                else content = (String) cell.getInfo();
                Contents.get(i).add(j,content);
            }
        }
        Vector<String[]> cellsContents = new Vector<String[]>();
        for (int k = 0; k < Contents.size(); ++k) {
            cellsContents.add(Contents.get(k).toArray(new String[Contents.get(k).size()]));
        }
        return cellsContents.toArray(new String[Contents.size()][]);
    }

    public static String[] currentBlockFind(String value) {
        Object parsedValue = Parse(value);
        if (docSheet.find(parsedValue) != null) {
            Cell searchedCell = docSheet.find(parsedValue);
            return new String[]{String.valueOf(searchedCell.getRow() +1), String.valueOf(searchedCell.getColumn() +1)};
        }
        else return new String[]{"Not Found"};
    }

    public static void currentBlockFindAndReplace(String find,String replace) {
        Object parsedFind = Parse(find);
        Object parsedReplace = Parse(replace);
        docSheet.findAndReplace(parsedFind,parsedReplace);
    }

    public static boolean currentBlockSort(int nCol, String Criteria){
        return (docSheet.SortBlock(nCol-1,Criteria));
    }

    public static void currentBlockFloor(Integer[] blockCells, boolean ref){
        Block block = createBlock(blockCells);
        docSheet.floor(block,ref);
    }

    public static void currentBlockConvert(Integer[] blockCells, boolean ref, String from, String to){
        Block block = createBlock(blockCells);
        docSheet.convert(block, ref, from, to);
    }

    public static int currentBlockColumns() {
        return docSheet.getSelectedBlock().number_cols();
    }
    public static int currentBlockRows() {
        return docSheet.getSelectedBlock().number_rows();
    }
    //-----------------------------FUNCTIONS--------------------------------

    public static Object Parse(String input) {
       try{
           Double isDouble = Double.parseDouble(input);
           return isDouble;
       } catch (NumberFormatException e) {
          try {
              LocalDate isDate = LocalDate.parse(input);
              return isDate;
          }
          catch (DateTimeParseException d) {
              return input;
          }
       }


    }


}
