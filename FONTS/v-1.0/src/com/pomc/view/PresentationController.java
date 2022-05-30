package com.pomc.view;

import com.itextpdf.text.DocumentException;
import com.pomc.controller.DomainController;
import com.pomc.controller.PersistenceController;

import java.io.File;
import java.io.IOException;

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

    public static String getTitle() { return DomainController.getDocName();}


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

        Integer[] cells= {ulr,ulc,drr,drc};
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

    public static void blockTrim(String Sheetname){
        DomainController.funcTrim(Sheetname);
    }

    public static void blockAdd(Integer[] block1, Integer[] block2, boolean ref, String sheetName){DomainController.funcAddition(block1,block2,ref,sheetName);}

    public static void blockSubs(Integer[] block1, Integer[] block2, boolean ref, String sheetName){DomainController.funcSubstraction(block1,block2,ref,sheetName);}

    public static void blockMult(Integer[] block1, Integer[] block2, boolean ref, String sheetName){DomainController.funcMultiply(block1,block2,ref,sheetName);}

    public static void blockDiv(Integer[] block1, Integer[] block2, boolean ref, String sheetName){DomainController.funcDivide(block1,block2,ref,sheetName);}

    public static void blockMean(Integer[] b1, boolean ref, String sheetName) {DomainController.funcMean(b1[0]-1,b1[1]-1,ref,sheetName);}

    public static void blockMedian(Integer[] b1, boolean ref, String sheetName) {DomainController.funcMedian(b1[0]-1,b1[1]-1,ref,sheetName);}

    public static void blockVariance(Integer[] b1, boolean ref, String sheetName) {DomainController.funcVariance(b1[0]-1,b1[1]-1,ref,sheetName); }

    public static void blockSTD(Integer[] b1, boolean ref, String sheetName) {DomainController.funcStandardDeviation(b1[0]-1,b1[1]-1,ref,sheetName);}

    public static void blockCovar(Integer[] a, Integer[] b1, boolean ref, String sheetName) {DomainController.funcCovariance(b1,a[0],a[1],ref,sheetName);}

    public static void blockCPearson(Integer[] a, Integer[] b1, boolean ref, String sheetName) {DomainController.funcCPearson(b1,a[0],a[1],ref,sheetName);}

//    public static void blockLength(int ulr, int ulc, boolean ref,String Sheetname){
//        DomainController.blockFloor(b_ids,ref,Sheetname);
//    }
    public static void blockDOTW(int ulr,int ulc, int drr, int drc, boolean ref, String sheetName){
        Integer[] ints={ulr,ulc,drr,drc};
        DomainController.funcDayoftheWeek(ints,ref,sheetName);
    }

    public static void blockExtract(int ulr, int ulc, int drr, int drc, boolean ref,String criteria, String sheetName){
        Integer[] ints= {ulr,ulc,drr,drc};
        if(!criteria.equals("-1")) DomainController.funcExtract(ints,ref,criteria,sheetName);
    }

    public static void blockConvert(int ulr, int ulc,int drr, int drc, boolean ref,String from,String to, String sheetName){
        Integer[] ints= {ulr,ulc,drr,drc};
        if(!from.equals("") && !to.equals(""))DomainController.blockConvert(ints,ref,from,to,sheetName);
    }

    public static void moveBlock(int ulr, int ulc,int drr, int drc, boolean ref, String sheetName){
        Integer[] ints= {ulr,ulc,drr,drc};
        DomainController.moveBlock(ints,ref,sheetName);
    }

    public static void blockLength(int ulr, int ulc,int drr, int drc, boolean ref, String sheetName,String criteria){
        Integer[] ints= {ulr,ulc,drr,drc};
        DomainController.funcLength(ints,criteria,ref,sheetName);
    }

    public static void blockReplaceWC(String sheetName,String criteria){
        DomainController.funcReplaceCriteria(criteria,sheetName);
    }




    //-----------------------CELL FUNCTIONS--------------------------------
    public static void editedCell(int r,int c, String newValue, String SheetName){DomainController.editCell(r,c,newValue,SheetName);}

    public static boolean isReferenced(int r,int c,String Sheetname){
        String t= DomainController.getCellType(r,c,Sheetname);
        return t.equals("R");
    }
    public static boolean hasRefs (int r,int c, String sheetName){return DomainController.hasRefs(r,c,sheetName);}

    public static Object[] getRefsIds(int r, int c, String sheetName){
        return DomainController.getRefsIds(r,c,sheetName);
    }

    public static int blockWRefs(int ulr, int ulc, int drr, int drc, String sheetName){return DomainController.isRef(ulr-1,ulc,drr-1,drc,sheetName);}

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
        if (t.equals("R")){
            i =DomainController.getCellContent(r,c,name);
        }
        else {
            i= DomainController.getCellInfo(r,c,name);
        }
        return i;
    }

    public static void showTcells(String name){
        String[][] cellsContents = DomainController.currentSheetCells(name);
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
        try {
            PersistenceController.save(path,DomainController.getDoc());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void saveAs(File selectedFile) {
        String[] extension = selectedFile.getName().split("\\.(?=[^\\.]+$)");
        if (extension.length> 1){
            if(extension[1].equals("pomc")) path= selectedFile.getAbsolutePath();
            else {
                path = selectedFile.getAbsolutePath()+".pomc";
            }
        }
        else path = selectedFile.getAbsolutePath()+".pomc";
        save();
    }

    public static void export(File selectedFile, String ext) {
        String[] extension = selectedFile.getName().split("\\.(?=[^\\.]+$)");
        if (extension.length> 1){
            if(extension[1].equals("csv") ||extension[1].equals("txt") || extension[1].equals("pdf")) {
                try {
                    PersistenceController.export(selectedFile.getAbsolutePath(),DomainController.getDoc(),extension[1]);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                } catch (DocumentException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        else {
            try {
            switch (ext) {
                    case "csv":
                        PersistenceController.export(selectedFile.getAbsolutePath() + ".csv", DomainController.getDoc(), "csv");
                        break;
                    case "txt":
                        PersistenceController.export(selectedFile.getAbsolutePath() + ".txt", DomainController.getDoc(), "txt");
                        break;
                    case "pdf":
                        PersistenceController.export(selectedFile.getAbsolutePath() + ".pdf", DomainController.getDoc(), "pdf");
                        break;
                }
            }catch (IOException e) {
                throw new RuntimeException(e);
            } catch (DocumentException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static boolean open(File selectedFile) {
        String[] extension = selectedFile.getName().split("\\.(?=[^\\.]+$)");
        if (extension.length > 1) {
            if (extension[1].equals("pomc")) {
                path = selectedFile.getAbsolutePath();
                try {
                    DomainController.setDoc(PersistenceController.open(path));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                return true;
            }
        }
        return false;
    }

    public static boolean Import(File selectedFile) {
        String[] extension = selectedFile.getName().split("\\.(?=[^\\.]+$)");
        if (extension.length > 1) {
            if (extension[1].equals("csv") || extension[1].equals("txt")) {
                path = selectedFile.getAbsolutePath();
                try {
                    DomainController.setDoc(PersistenceController.imports(path,extension[1]));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                return true;
            }
        }
        return false;
    }
    //-------------------------OPCIONALES-----------------------------
    public static void blockCeil(int ulr, int ulc, int drr, int drc, boolean ref, String sheetName){
        Integer[] ints= {ulr,ulc,drr,drc};
        DomainController.blockCeil(ints,ref,sheetName);
    }

    public static void blockConcat (Integer[] b1,Integer[] b2, boolean ref, String sheetName){
        DomainController.funcConcatenate(b1,b2,ref,sheetName);
        showTcells(sheetName);
    }

    public static void blockMax(Integer[] a, boolean ref, String sheetName){
        DomainController.funcMax(a[0]-1,a[1]-1,ref,sheetName);
    }

    public static void blockMin(Integer[] a, boolean ref, String sheetName){
        DomainController.funcMin(a[0]-1,a[1]-1,ref,sheetName);
    }

    public static void blockCountIf(Integer[]a,boolean ref,String sheetName, double eq, String crit){ DomainController.funcCountIf(a[0]-1,a[1]-1,ref,eq,crit,sheetName);}

    public static void blockOpBlock(String op, double x,String sheetName ){DomainController.opBlock(op, x,sheetName);
    }

    public static void blockModify(String o, String sheetName){DomainController.modifyBlock(o,sheetName);}




}
