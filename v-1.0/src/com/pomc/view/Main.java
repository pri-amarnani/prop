package com.pomc.view;

import com.pomc.controller.DomainController;

import java.util.Scanner;

public class Main {

    static Scanner userInput = new Scanner(System.in);


    public static void menuTextFunctions(String docSheet){
        boolean menu = true;
        while(menu) {
            System.out.println("In the block of Sheet " + docSheet);
            System.out.println("Select an action:");
            System.out.println("(1) Replace with Criteria");
            System.out.println("(2) Length");
            System.out.println("(0) Go back");
            System.out.println("\n \n Enter number:");
            Integer num = numberInsertion(2,0);
            if (num != null) {
                System.out.println("\n");
                switch (num) {
                    case 0:
                        menu = false;
                        break;
                    case 1:
                        System.out.println("(4) Delete a Sheet");
                        break;
                    case 2:
                        System.out.println("(4)2t");
                        break;
                    case 3:
                        System.out.println("e a Sheet");
                        break;
                    default:
                        System.out.println("\n");
                        break;
                }
                System.out.println("\n");
            }

        }
    }

    public static void menuDateFunctions(String docSheet){
        boolean menu = true;
        while(menu) {
            System.out.println("In the block of Sheet " + docSheet);
            System.out.println("Select an action:");
            System.out.println("(1) Extract");
            System.out.println("(2) Day of the week");
            System.out.println("(0) Go back");
            System.out.println("\n \n Enter number:");
            Integer num = numberInsertion(2,0);
            if (num != null) {
                System.out.println("\n");
                switch (num) {
                    case 0:
                        menu = false;
                        break;
                    case 1:
                        System.out.println("(4) Delete a Sheet");
                        break;
                    case 2:
                        System.out.println("(4)2t");
                        break;
                    case 3:
                        System.out.println("e a Sheet");
                        break;
                    default:
                        System.out.println("\n");
                        break;
                }
                System.out.println("\n");
            }

        }
    }

    public static void menuMathematicFunctions(String docSheet){
        boolean menu = true;
        while(menu) {
            System.out.println("In the block of Sheet " + docSheet);
            System.out.println("Select an action:");
            System.out.println("(1) Addition");
            System.out.println("(2) Substraction");
            System.out.println("(3) Multiplication");
            System.out.println("(4) Division");
            System.out.println("(5) Mean");
            System.out.println("(6) Median");
            System.out.println("(7) Variance");
            System.out.println("(8) Covariance");
            System.out.println("(9) Standard Deviation");
            System.out.println("(10) Pearson Coefficient");
            System.out.println("(0) Go back");
            System.out.println("\n \n Enter number:");
            Integer num = numberInsertion(4,0);
            if (num != null) {
                System.out.println("\n");
                switch (num) {
                    case 0:
                        menu = false;
                        break;
                    case 1:
                        System.out.println("(4) Delete a Sheet");
                        break;
                    case 2:
                        System.out.println("(4)2t");
                        break;
                    case 3:
                        System.out.println("e a Sheet");
                        break;
                    case 4:
                        System.out.println("e a Sheet");
                        break;
                    case 5:
                        System.out.println("e a Sheet");
                        break;
                    case 6:
                        System.out.println("e a Sheet");
                        break;
                    case 7:
                        System.out.println("e a Sheet");
                        break;
                    case 8:
                        System.out.println("e a Sheet");
                        break;
                    case 9:
                        System.out.println("e a Sheet");
                        break;
                    case 10:
                        System.out.println("e a Sheet");
                        break;
                    default:
                        System.out.println("\n");
                        break;
                }
                System.out.println("\n");
            }

        }
    }

    public static void menuFunctions(String docSheet){
        boolean menu = true;
        while(menu) {
            System.out.println("In the block of Sheet " + docSheet);
            System.out.println("Select an action:");
            System.out.println("(1) Show Block Cells");
            System.out.println("(2) Find Cell");
            System.out.println("(3) Find and Replace");
            System.out.println("(4) Sort Block");
            System.out.println("(5) Floor");
            System.out.println("(6) Convert");
            System.out.println("(7) Mathematic Operations");
            System.out.println("(8) Date Operations");
            System.out.println("(9) Text Operations");
            System.out.println("(0) Go back");
            System.out.println("\n \n Enter number:");
            Integer num = numberInsertion(9,0);
            if (num != null) {
                System.out.println("\n");
                switch (num) {
                    case 0:
                        menu = false;
                        break;
                    case 1:
                        String[][] blockContents = DomainController.currentBlockCells();
                        for(int i = 0; i < blockContents.length ; ++i) {
                            for (int j = 0; j < blockContents[0].length ; ++j) {
                                System.out.print(" | " + blockContents[i][j]);
                            }
                            System.out.print(" | \n");
                        }
                        break;
                    case 2:
                        System.out.println("Input value to search:");
                        String value = userInput.nextLine();
                        String[] cellPos= DomainController.currentBlockFind(value);
                        if(cellPos[0] == "Not Found") System.out.println("Value not found");
                        else {
                            System.out.println("The cell is located at the row " + cellPos[0] + " and the column " + cellPos[1] );
                        }
                        break;
                    case 3:
                        System.out.println("Input value to search:");
                        String valueI = userInput.nextLine();
                        System.out.println("Input value to replace:");
                        String valueR = userInput.nextLine();
                        if (valueI.equals("") || valueR.equals("")) System.out.println("Values missing");
                        else {
                            DomainController.currentBlockFindAndReplace(valueI,valueR);
                            System.out.println("Values replaced!");
                        }
                        break;
                    case 4:
                        System.out.println("Input reference column to order");
                        Integer nCol = numberInsertion(DomainController.currentBlockColumns() , 0);
                        while (true) {
                            System.out.println("Input order criteria (</>)");
                            String criteria = userInput.nextLine();
                            if (!criteria.equals("<") && !criteria.equals(">")) {
                                System.out.println("Invalid criteria.");
                            }
                            else {
                                if (DomainController.currentBlockSort(nCol,criteria)) {
                                    System.out.println("Block sorted.");
                                }
                                else {
                                    System.out.println("Couldn't sort block (Invalid values in reference column)");
                                }
                                break;
                            }
                        }
                        break;
                    case 5:
                        System.out.println("First define the block.");
                        Integer[] blockCells = requireBlock();
                        if (blockCells.length == 0) break;
                        else {
                            System.out.println("Do you want the result to be a reference to the operation? (y/n)");
                            String refFloor = userInput.nextLine();
                            if (refFloor.equals("y") || refFloor.equals("yes") ) {
                                DomainController.currentBlockFloor(blockCells, true);
                                System.out.println("Result floored!");
                            }
                            else if (refFloor.equals("n") || refFloor.equals("no")) {
                                DomainController.currentBlockFloor(blockCells, false);
                            }
                            else {
                                System.out.println("Invalid character.");
                            }
                        }
                        break;
                    case 6:
                        System.out.println("First define the block.");
                        Integer[] blockCells1 = requireBlock();
                        if (blockCells1.length == 0) break;

                        break;
                    case 7:
                        menuMathematicFunctions(docSheet);
                        break;
                    case 8:
                        menuDateFunctions(docSheet);
                        break;
                    case 9:
                        menuTextFunctions(docSheet);
                        break;
                    default:
                        System.out.println("\n");
                        break;
                }
                System.out.println("\n");
            }
        }
    }

    public static void menuSheet(String docSheet){
        boolean menu = true;
        while(menu) {
            System.out.println("In Sheet " + docSheet);
            System.out.println("Select an action:");
            System.out.println("(1) Show Cells");
            System.out.println("(2) Edit Cells");
            System.out.println("(3) Create Row");
            System.out.println("(4) Create Column");
            System.out.println("(5) Delete Row");
            System.out.println("(6) Delete Column");
            System.out.println("(7) Select Block"); //Te lleva al menu de block
            System.out.println("(0) Go back");
            System.out.println("\n \n Enter number:");
            Integer num = numberInsertion(7,0);
            if (num != null) {
                System.out.println("\n");
                switch (num) {
                    case 0:
                        menu = false;
                        break;
                    case 1:
                        String[][] cellsContents = DomainController.currentSheetCells();
                        for(int i = 0; i < cellsContents.length ; ++i) {
                            for (int j = 0; j < cellsContents[0].length ; ++j) {
                                System.out.print(" | " + cellsContents[i][j]);
                            }
                            System.out.print(" | \n");
                        }
                        break;
                    case 2:
                        System.out.println("Input the row of the Cell to edit:");
                        Integer cellI = numberInsertion(DomainController.currentSheetRows(), 1);
                        System.out.println("Input the column of the Cell to edit:");                     //TODO no va con strings ni dates
                        Integer cellJ = numberInsertion(DomainController.currentSheetCols(), 1);
                        if (cellI > 0 && cellJ > 0) {
                            System.out.println("Input the value:");
                            String value = userInput.nextLine();
                            DomainController.editCell(cellI,cellJ,value);
                            System.out.println("Cell edited!");
                        }
                        else System.out.println("Cell not found");
                        break;
                    case 3:
                        System.out.println("Input where to add the row:");
                        Integer newRowPos = numberInsertion(DomainController.currentSheetRows(), 1);
                        if (newRowPos == 0) newRowPos = DomainController.currentSheetRows();
                        DomainController.currentSheetAddRow(newRowPos);
                        System.out.println("Row in position " + newRowPos + " was added to the Sheet");
                        break;
                    case 4:
                        System.out.println("Input where to add the column:");
                        Integer newColPos = numberInsertion(DomainController.currentSheetCols(), 1);
                        if (newColPos == 0) newColPos = DomainController.currentSheetCols();
                        DomainController.currentSheetAddCol(newColPos);
                        System.out.println("Column in position " + newColPos + " was added to the Sheet");
                        break;
                    case 5:
                        System.out.println("Input the number of row to delete:");
                        Integer rowPos = numberInsertion(DomainController.currentSheetRows(), 1);
                        if (rowPos >0 && isUserSure()) {
                            DomainController.currentSheetDeleteRow(rowPos);
                            System.out.println("Row in position " + rowPos + " was deleted");
                        }
                        else System.out.println("No row was deleted.");
                        break;
                    case 6:
                        System.out.println("Input the number of column to delete:");
                        Integer colPos = numberInsertion(DomainController.currentSheetCols(), 1);
                        if (colPos >0 && isUserSure()) {
                            DomainController.currentSheetDeleteCol(colPos);
                            System.out.println("Column in position " + colPos + " was deleted");
                        }
                        else System.out.println("No column was deleted.");
                        break;
                    case 7:
                            Integer[] blockCells = requireBlock();
                            if (blockCells.length == 0) break;
                            else {
                            if (!DomainController.initializeBlock(blockCells))System.out.println("Couldn't create block");
                            else {
                                System.out.println("Block created");
                                System.out.println("\n");
                                menuFunctions(docSheet);
                            }
                                break;
                            }
                    default:
                    System.out.println("\n");
                        break;
                }
                System.out.println("\n");
            }
        }
    }
    public static void menuDocument(){
        while(true) {
            String[] sheetNames = DomainController.showSheets();
            System.out.println("In document " + DomainController.getDocName());
            System.out.println("Select an action:");
            System.out.println("(1) Show Sheets");
            System.out.println("(2) Select a Sheet");
            System.out.println("(3) Create a Sheet");
            System.out.println("(4) Delete a Sheet");
            System.out.println("(5) Help!");            //TODO ola
            System.out.println("\n \n Enter number:");
            Integer num = numberInsertion(5,1);
            if (num != null) {
                switch (num) {
                    case 1:
                        for(int i = 0; i < sheetNames.length; ++i ){
                            System.out.println((i+1)+". " + sheetNames[i]);
                        }
                        break;
                    case 2:
                        if (sheetNames[0].equals("No Sheets")) System.out.println("The document has no sheets.");
                        else {
                            System.out.println("Input Sheet Name");
                            String sheetName = userInput.nextLine();
                            while (!foundIn(sheetName,sheetNames)) {
                                System.out.println("Sheet name not found. Try again:");
                                sheetName = userInput.nextLine();
                            }
                            DomainController.setDocSheet(sheetName);
                            System.out.println("\n");
                            menuSheet(sheetName);
                        }
                        break;
                    case 3:
                        System.out.println("Input Sheet Name");
                        String title = userInput.nextLine();
                        while (foundIn(title,sheetNames)) {
                            System.out.println("Sheet already exits. Try again:");
                            title = userInput.nextLine();
                        }
                        System.out.println("Input Sheet Rows");
                        Integer nRows = numberInsertion(30000,0);
                        System.out.println("Input Sheet Columns");
                        Integer nCols = numberInsertion(30000,0);
                        String newTitle = DomainController.addSheet(title,nRows,nCols);
                        System.out.println( newTitle + " sheet added to your Document");
                        break;
                    case 4:
                        if (sheetNames[0].equals("No Sheets")) System.out.println("The document has no sheets.");
                        else {
                            System.out.println("Input Sheet Name");
                            String sheet = userInput.nextLine();
                            while (!foundIn(sheet,sheetNames)) {
                                System.out.println("Sheet name not found. Try again:");
                                sheet = userInput.nextLine();
                            }
                            if (isUserSure()){
                                DomainController.deleteSheet(sheet);
                                System.out.println("The "+ sheet + " sheet was deleted");
                            }
                            else System.out.println("No sheets deleted");
                        }
                        break;
                    case 5:
                        System.out.println("Press buttons");
                    default:
                        System.out.println("\n");
                        break;
                }
            }
            System.out.println("\n");
        }
    }

    public static boolean foundIn (String element, String[] array) {
        for(String i : array){
            if (i.equals(element))  return true;
        }
        return false;
    }

    public static Integer numberInsertion(int max,int min) {
        try {
            String userIn = userInput.nextLine();
            if (userIn.equals("")) return 0;
            int num = Integer.parseInt(userIn);
            if (num > max || num < min) {
                System.out.println("Invalid number, try again. \n");
                return numberInsertion(max,min);
            }
            else {
                return num;
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid character, enter a number:");
            return numberInsertion(max,min);
        }
    }

    public static boolean isUserSure(){
        System.out.println("Are you sure? (y/n)");
        while(true) {
            String userIn = userInput.nextLine();
            if (userIn.equals("y") || userIn.equals("yes") ) return true;
            else if (userIn.equals("n") || userIn.equals("no")) return false;
            else {
                System.out.println("Invalid answer. Are you sure ? (y/n)");
            }
        }
    }

    public static Integer[] requireBlock(){
        System.out.println("Input the row of the upper left cell of the block:");
        Integer ulCelli = numberInsertion(DomainController.currentSheetRows(), 1);
        System.out.println("Input the column of the upper left cell of the block:");
        Integer ulCellj = numberInsertion(DomainController.currentSheetCols(), 1);
        System.out.println("Input the row of the lower right cell of the block:");
        Integer lrCelli = numberInsertion(DomainController.currentSheetRows(), 1);
        System.out.println("Input the column of the lower right cell of the block:");
        Integer lrCellj = numberInsertion(DomainController.currentSheetCols(), 1);
        if (ulCelli > 0 && ulCellj > 0 && lrCelli > 0 && lrCellj > 0 ) return new Integer[]{ulCelli, ulCellj, lrCelli, lrCellj};
        else {
            System.out.println("No Block created");
            return new Integer[]{};
        }
    }

    public static void main(String[] args) {
        System.out.println("\n POMC WORKSHEETS");
        System.out.println("------------------------------");
        System.out.println("Enter title for document:");                //TODO nombre default y unico
        String title = userInput.nextLine();
        System.out.println("\n");
        DomainController.initializeDoc(title);
        menuDocument();
    }
}
