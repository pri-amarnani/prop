package com.pomc.controller;
import com.pomc.classes.*;
import org.knowm.xchart.BitmapEncoder;
import org.knowm.xchart.PieChart;
import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.XYChart;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Vector;

import static java.lang.String.valueOf;

public class DomainController {
    static Document doc ;



    //------------------------------------------------DOCS FUNCTS--------------------------------------

    public static void initializeDoc(String title) {
        doc = new Document(title);
    }

    //retorna el nom del doc
    public static String getDocName() {
        return doc.getTitle();
    }

    public static Document getDoc() {
        return doc;
    }

    public static void setDoc(Document doc) {
        DomainController.doc = doc;
    }

    public static int getNumSheet(){return doc.getNumSheet();}

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


    public static void setSheetTitle(String title,String newtitle){
        doc.getSheet(title).setTitle(newtitle);
    }

    //gets current sheet number of rows
    public static Integer sheetRows(String name){
        if (doc.getSheet(name)!=null) return doc.getSheet(name).getNumRows();
        else return -1;
    }

    //gets current sheet number of columns
    public static Integer sheetCols(String name){
        if (doc.getSheet(name)!=null) return doc.getSheet(name).getNumCols();
        else return -1;
    }

    //gets an array with all the contents of the cells of the current sheet
    public static String[][] currentSheetCells(String name){
        Vector<Vector<String>> Contents = new Vector<Vector<String>>();
        for(int i = 0; i < doc.getSheet(name).getNumRows(); ++i) {
            Contents.add(i,new Vector<String>());
            for (int j = 0; j < doc.getSheet(name).getNumCols(); ++j) {
                String content;
                Cell cell = doc.getSheet(name).getCell(i,j);
                if (cell.getInfo() == null) { content = " - "; }
                else if (cell.getType().equals("R")) content = AntiParse(cell.getContent());
                else content = AntiParse(cell.getInfo());
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
    public static void sheetAddRow(Integer num, String name){
        if (doc.getSheet(name)!=null) {
            doc.getSheet(name).NewRow(num - 1);
        }
    }

    public static void sheetAddRows(int num,int pos,String name){
        for(int i=0;i<num;i++){
            sheetAddRow(pos,name);
        }
    }
    //adds an empty col to the specified position
    public static void sheetAddCol(Integer num, String name){
        if (doc.getSheet(name)!=null) doc.getSheet(name).NewColumn(num );
    }

    public static void sheetAddCols(int num,int pos,String name){
        for(int i=0;i<num;i++){
            sheetAddCol(pos,name);
        }
    }
    //deletes a row in the specified position
    public static void sheetDeleteRow(Integer num, String name){
        if (doc.getSheet(name)!=null) doc.getSheet(name).DeleteRow(num );

    }

    public static void sheetDelRows(int num,int pos,String name){
        for(int i=0;i<num;i++){

            sheetDeleteRow(pos,name);

        }
    }
    //deletes a column in the specified position
    public static void sheetDeleteCol(Integer num, String name){
        if (doc.getSheet(name)!=null) doc.getSheet(name).DeleteColumn(num);

    }
    public static void sheetDeleteCols(int num,int pos,String name){
        for(int i=0;i<num;i++){
            sheetDeleteCol(pos,name);
        }
    }

    public static void editCell(int i, int j, String value, String name) {
        Sheet s;
        if (name != null) {
            s = doc.getSheet(name);
            Object parsedValue = Parse(value);
            Cell cellToEdit = s.getCell(i , j );
            s.change_value(cellToEdit, parsedValue);
        }
    }


    //-----------------------------------BLOCK FUNCTIONS------------------------------

    //Current block initialized, as it's minimum is 1
    public static Boolean initializeBlock(Integer[] blockCells,String sheetname) {
        if (blockCells[0] <= blockCells[2]  && blockCells[1]  <= blockCells[3] ) {
            Cell c1 = doc.getSheet(sheetname).getCell(blockCells[0] , blockCells[1] );
            Cell c2 = doc.getSheet(sheetname).getCell(blockCells[2] , blockCells[3] );
            doc.getSheet(sheetname).SelectBlock(c1,c2);
            return true;
        }
        return false;
    }




    public static Block createBlock(Integer[] blockCells, String name) {
        if (name != null && blockCells[0]!=-1) {
            Sheet s=doc.getSheet(name);
            if (blockCells[0] <= blockCells[2] && blockCells[1] <= blockCells[3]) {
                Cell c1 = s.getCell(blockCells[0] - 1, blockCells[1] - 1);
                Cell c2 = s.getCell(blockCells[2] - 1, blockCells[3] - 1);
                return s.create_block(c1, c2);
            }
        }
        return null;
    }

    public static int selectedBlockCols (String sheetName){
        if(sheetName!=null){
            return doc.getSheet(sheetName).getSelectedBlock().number_cols();
        }
        else return -1;
    }

    public static int selectedBlockRows(String sheetName){
        if(sheetName!=null){
            return doc.getSheet(sheetName).getSelectedBlock().number_rows();
        }
        else return -1;
    }




    public static String[][] currentBlockCells(String name){
        if (name != null) {
            Vector<Vector<String>> Contents = new Vector<Vector<String>>();
            for (int i = 0; i < doc.getSheet(name).getSelectedBlock().number_rows(); ++i) {
                Contents.add(i, new Vector<String>());
                for (int j = 0; j < doc.getSheet(name).getSelectedBlock().number_cols(); ++j) {
                    String content;
                    Cell cell = doc.getSheet(name).getSelectedBlock().getCell(i, j);
                    if (cell.getInfo() == null) {
                        content = " - ";
                    } else if (cell.getType().equals("R")) content = AntiParse(cell.getContent());
                    else content = AntiParse(cell.getInfo());
                    Contents.get(i).add(j, content);
                }
            }
            Vector<String[]> cellsContents = new Vector<String[]>();
            for (int k = 0; k < Contents.size(); ++k) {
                cellsContents.add(Contents.get(k).toArray(new String[Contents.get(k).size()]));
            }

            return cellsContents.toArray(new String[Contents.size()][]);
        }
        return null;
    }

    public static String blockType(String sheetname){
        if(sheetname!=null){
            if(doc.getSheet(sheetname).getSelectedBlock().allDouble()) return "N";
            else if (doc.getSheet(sheetname).getSelectedBlock().allDate()) return "D";
            else if (doc.getSheet(sheetname).getSelectedBlock().allText()) return "T";
            else return "M";
        }
        return null;
    }


    public static Integer[] blockFind(String value,String sheetName) {
        Object parsedValue = Parse(value);
        if (sheetName != null) {
            Vector<Cell> v = doc.getSheet(sheetName).find(parsedValue);
            Integer[] res = new Integer[v.size()];
            doc.getSheet(sheetName);
            if (doc.getSheet(sheetName).find(parsedValue) != null) {
                for (int i = 0; i < v.size(); i++) {
                    res[i] = v.elementAt(i).getColumn();
                }
                return res;
            }
            else return null;
        }
        return null;
    }

    public static Object[] blockFindAndReplace(String find,String replace, String sheetName) {
        if (sheetName != null) {
            Object parsedFind = Parse(find);
            Object parsedReplace = Parse(replace);
            return doc.getSheet(sheetName).findAndReplace(parsedFind,parsedReplace);
        }
        return null;
    }

    public static boolean blockSort(int nCol, String Criteria, String sheetName){
        return (doc.getSheet(sheetName).SortBlock(nCol,Criteria));
    }

    public static void blockFloor(Integer[] blockCells, boolean ref,String name){
        Block block = createBlock(blockCells,name);
        doc.getSheet(name).floor(block,ref);
    }

    public static void blockConvert(Integer[] blockCells, boolean ref, String from, String to, String sheetname){
        Block block = createBlock(blockCells,sheetname);
        doc.getSheet(sheetname).convert(block, ref, from, to);
    }

    public static int blockColumns(String sheetName) {
        return doc.getSheet(sheetName).getSelectedBlock().number_cols();
    }
    public static int blockRows(String sheetName) {
        return doc.getSheet(sheetName).getSelectedBlock().number_rows();
    }

    public static int blockFirstRow(String sheetName) {
        return doc.getSheet(sheetName).blockFirstRow();
    }
    public static int blockFirstCol(String sheetName) {
        return doc.getSheet(sheetName).blockFirstCol();
    }
    //-----------------------------FUNCTIONS--------------------------------

    public static void funcAddition(Integer[] Block1, Integer[] Block2, boolean ref, String sheetname) {
        Block b1 = createBlock(Block1, sheetname);
        Block b2 = createBlock(Block2,sheetname);
        doc.getSheet(sheetname).sum(b1,b2,ref);
    }

    public static void funcSubstraction(Integer[] Block1, Integer[] Block2, boolean ref, String sheetname) {
        Block b1 = createBlock(Block1,sheetname);
        Block b2 = createBlock(Block2,sheetname);
        doc.getSheet(sheetname).substract(b1,b2,ref);
    }

    public static void funcMultiply(Integer[] Block1, Integer[] Block2, boolean ref, String sheetname) {
        Block b1 = createBlock(Block1,sheetname);
        Block b2 = createBlock(Block2,sheetname);
        doc.getSheet(sheetname).mult(b1,b2,ref);
    }

    public static void funcDivide(Integer[] Block1, Integer[] Block2, boolean ref,String sheetname) {
        Block b1 = createBlock(Block1,sheetname);
        Block b2 = createBlock(Block2,sheetname);
        doc.getSheet(sheetname).div(b1,b2,ref);
    }

    public static Double funcMean(Integer i, Integer j, boolean ref, String sheetname){
        Cell cell = doc.getSheet(sheetname).getCells().get(i).get(j);
        return   doc.getSheet(sheetname).mean(cell,ref);
    }
    public static Double funcMedian(Integer i, Integer j, boolean ref,String sheetname ){
        Cell cell = doc.getSheet(sheetname).getCells().get(i).get(j);
        return doc.getSheet(sheetname).median(cell,ref);
    }
    public static Double funcVariance(Integer i, Integer j, boolean ref ,String sheetname){
        Cell cell = doc.getSheet(sheetname).getCells().get(i).get(j);
        return doc.getSheet(sheetname).var(cell,ref);
    }

    public static Double funcCovariance(Integer[] block,Integer i, Integer j, boolean ref,String sheetname ){
        Block b1 = createBlock(block,sheetname);
        Cell cell = doc.getSheet(sheetname).getCells().get(i).get(j);
        return doc.getSheet(sheetname).covar(b1,cell,ref);
    }

    public static Double funcStandardDeviation(Integer i, Integer j, boolean ref,String sheetname ){
        Cell cell = doc.getSheet(sheetname).getCells().get(i).get(j);
        return doc.getSheet(sheetname).std(cell,ref);
    }

    public static Double funcCPearson(Integer[] block,Integer i, Integer j, boolean ref,String sheetname ){
        Block b1 = createBlock(block,sheetname);
        Cell cell = doc.getSheet(sheetname).getCells().get(i).get(j);
        return doc.getSheet(sheetname).CPearson(b1,cell,ref);
    }


    public static void funcExtract(Integer[] block,boolean ref, String ex,String sheetname){
        Block b1 = createBlock(block,sheetname);
        doc.getSheet(sheetname).extract(b1,ref,ex);
    }

    public static void funcDayoftheWeek(Integer[] block,boolean ref,String sheetname){
        Block b1 = createBlock(block,sheetname);
        doc.getSheet(sheetname).dayOfTheWeek(b1,ref);
    }

    public static boolean moveBlock(Integer[] block,boolean ref,String sheetname){
        Block b1 = createBlock(block,sheetname);
        return doc.getSheet(sheetname).MoveBlock(b1,ref);
    }

    public static void funcReplaceCriteria(String criteria,String sheetname){
        doc.getSheet(sheetname).replaceWithCriteriaText(criteria);
    }

    public static void funcLength(Integer[] block, String criteria,boolean ref,String sheetname) {
        Block b1 = createBlock(block, sheetname);
        doc.getSheet(sheetname).lengthBlock(b1,ref, criteria);
    }

    public static String getCellInfo(int r, int c, String sheetname){
        if(sheetname!=null) {
            Sheet s = doc.getSheet(sheetname);
            if(s.getCell(r, c).getInfo()!=null) return AntiParse(s.getCell(r, c).getInfo());
            else return "";
        }
        return null;
    }

    public static String getCellContent(int r, int c, String sheetname){
        if(sheetname!=null) {
            Sheet s = doc.getSheet(sheetname);
            if(s.getCell(r, c).getContent()!=null){
                return AntiParse(s.getCell(r, c).getContent());
            }
            else return "";
        }
        return null;
    }

    public static String getCellType(int r,int c, String sheetname){
        if(sheetname!=null){
            Sheet s=doc.getSheet(sheetname);
            return s.getCell(r,c).getType();
        }
        return null;
    }

    public static boolean hasRefs(int r,int c,String sheetName){
        if(sheetName!=null){
            Sheet s=doc.getSheet(sheetName);
            return s.getCell(r,c).hasRefs();
        }
        return false;
    }

    public static Object[] getRefsIds (int r, int c, String sheetName){
        Vector<Integer> rids= new Vector<>();
        if(sheetName!=null){
            Sheet s=doc.getSheet(sheetName);
            int rsize=s.getCell(r,c).getRefs().size();
            for (int i = 0; i <rsize; i++) {
                int row=s.getCell(r,c).getRefs().elementAt(i).getRow();
                int col=s.getCell(r,c).getRefs().elementAt(i).getColumn();
                rids.add(row);
                rids.add(col);
            }

        }
        return rids.toArray();
    }

    public static int isRef (int ulr, int ulc, int drr, int drc, String sheetName){
        Block block;
        Integer [] ints={ulr,ulc,drr,drc};
        Cell c1= doc.getSheet(sheetName).getCell(ulr,ulc);
        Cell c2= doc.getSheet(sheetName).getCell(drr,drc);
        block= doc.getSheet(sheetName).create_block(c1,c2);


        for (int i = 0; i < block.number_rows(); i++) {
            for (int j = 0; j < block.number_cols(); j++) {
                Cell c= block.getCell(i,j);
                if (c.getType().equals("R")) return 0; //el bloque tiene una o + celdas con ref
                else if(c.getInfo()!=null) return 1;
            }

        }

        return -1;
    }

    public static Object[] getSBlockids(String sheetName){
        Block b=doc.getSheet(sheetName).getSelectedBlock();
        Vector<Integer> result=new Vector<Integer>();
//       int sbrows=doc.getSheet(sheetName).getSelectedBlock().number_rows();
//       int sbcols= doc.getSheet(sheetName).getSelectedBlock().number_cols();
//       Integer[] result= new Integer[(sbrows*sbcols)*2];
//        for (int k = 0; k < (sbcols*sbrows)*2 ; k+=2) {
//                    result[k]=k+blockFirstRow(sheetName);
//                    result[k+1]=k+blockFirstCol(sheetName);
//        }
        for (int i = 0; i < b.number_rows() ; i++) {
            for (int j = 0; j < b.number_cols(); j++) {
                result.add(b.getCell(i,j).getRow());
                result.add(b.getCell(i,j).getColumn());
            }
        }
        return result.toArray();
    }

    //-----------------------------------OPCIONALES-----------------------------------

    public static XYChart graficXY(String title, String x, String y, String func, String sheetName){
        if(doc.getSheet(sheetName).getSelectedBlock() != null){
            XYChart chart = doc.getSheet(sheetName).getSelectedBlock().graficXY(title, x, y, func);
            // Show it
            //noinspection rawtypes
            //new SwingWrapper(chart).displayChart();

            try {
                BitmapEncoder.saveBitmap(chart, "./LinearChart", BitmapEncoder.BitmapFormat.PNG);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            return chart;
        }

        return null;
    }

    public static PieChart graficPie(String sheetName){
        PieChart ch = new PieChart(5, 5);
        if(doc.getSheet(sheetName).getSelectedBlock() != null){
            Block b1 = doc.getSheet(sheetName).getSelectedBlock();
            boolean correct = true;
            if(b1.number_cols() != 2) correct = false;
            Vector<String> v = new Vector<>();

            for(int i = 0; i < b1.number_rows() && correct; ++i){
                if(!b1.getCell(i,0).getType().equals("T")) correct = false;
                if(!b1.getCell(i,1).getType().equals("N")) correct = false;
                if(b1.getCell(i,0).getType().equals("T")) v.add((String) b1.getCell(i,0).getInfo());
            }
            for (int i = 0; i < v.size() && correct; i++) {
                for (int j = i + 1; j < v.size() && correct; j++) {
                    if(v.elementAt(i).equals(v.elementAt(j))) correct = false;
                }
            }


            if(!correct){
                return ch;
            }
            else{
                PieChart chart = doc.getSheet(sheetName).getSelectedBlock().graficPie();

                try {
                    BitmapEncoder.saveBitmap(chart, "./PieChart", BitmapEncoder.BitmapFormat.PNG);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                return chart;
            }

        }
        return ch;
    }
    public static void blockCeil(Integer[] blockCells, boolean ref,String sheetName){
        Block block = createBlock(blockCells, sheetName);
        doc.getSheet(sheetName).ceil(block,ref);
    }

    public static void funcConcatenate(Integer[] Block1, Integer[] Block2, boolean ref,String sheetName) {
        Block b1 = createBlock(Block1, sheetName);
        Block b2 = createBlock(Block2, sheetName);
        doc.getSheet(sheetName).concatenate(b1,b2,ref);
    }

    public static Double funcMax(Integer i, Integer j, boolean ref,String sheetName ){
        Cell cell =doc.getSheet(sheetName).getCells().get(i).get(j);
        return doc.getSheet(sheetName).max(cell,ref);
    }
    public static Double funcMin(Integer i, Integer j, boolean ref ,String sheetName){
        Cell cell = doc.getSheet(sheetName).getCells().get(i).get(j);
        return doc.getSheet(sheetName).min(cell,ref);
    }


    public static Double funcCountIf(Integer i, Integer j,boolean ref, double eq, String criteria,String sheetName){
        Cell cell = doc.getSheet(sheetName).getCells().get(i).get(j);
        return doc.getSheet(sheetName).countIf(cell,ref,eq,criteria);
    }

//    public static Double funcSumAll(Integer i, Integer j, boolean val, boolean ref ,String sheetName){
//        Cell cell = null;
//        if (val) cell = doc.getSheet(sheetName).getCells().get(i).get(j);
//        return doc.getSheet(sheetName).sumAll(cell,ref,val);
//    }
//    public static Double funcSubAll(Integer i, Integer j, boolean val, boolean ref,String sheetName ){
//        Cell cell = null;
//        if (val) cell = doc.getSheet(sheetName).getCells().get(i).get(j);
//        return doc.getSheet(sheetName).subAll(cell,ref,val);
//    }
//    public static Double funcMultAll(Integer i, Integer j, boolean val, boolean ref,String sheetName ){
//        Cell cell = null;
//        if (val) cell = doc.getSheet(sheetName).getCells().get(i).get(j);
//        return doc.getSheet(sheetName).multAll(cell,ref,val);
//    }
//    public static Double funcDivAll(Integer i, Integer j, boolean val, boolean ref,String sheetName ){
//        Cell cell = null;
//        if (val) cell = doc.getSheet(sheetName).getCells().get(i).get(j);
//        return doc.getSheet(sheetName).divAll(cell,ref,val);
//    }
    public static void opBlock(String op, double x,String sheetName){
        doc.getSheet(sheetName).opBlock(op, x);
    }

    public static void modifyBlock(String o,String sheetName){
        Object ob= Parse(o);
        doc.getSheet(sheetName).ModifyBlock(ob);
    }

    public static void funcTrim(String sheetName){
        doc.getSheet(sheetName).trim();
    }


    //-----------------------------------AUXILIAR-----------------------------
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
    public static String AntiParse(Object o) {
        if (o.getClass() == Double.class) return String.valueOf(o);
        else if (o.getClass()== LocalDate.class) return o.toString();
        else return (String) o;
    }



}
