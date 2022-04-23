package com.pomc.view;

import com.pomc.controller.DomainController;

import java.util.Scanner;

public class Main {

    static Scanner userInput = new Scanner(System.in);


    public static void menuTextFunctions(String docSheet, String sheetBlock){
        while(true) {
            System.out.println("In Sheet " + docSheet);
            System.out.println("Select an action:");
            System.out.println("(1) Replace with Criteria");
            System.out.println("(2) Length");
            System.out.println("(0) Go back");
            System.out.println("\n \n Enter number:");
            Integer num = numberInsertion(2,0);
            if (num != null) {
                System.out.println("\n");
                switch (num) {
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
                        System.out.println("2222Sheet");
                        break;
                }
                System.out.println("\n");
            }

        }
    }

    public static void menuDateFunctions(String docSheet, String sheetBlock){
        while(true) {
            System.out.println("In Sheet " + docSheet);
            System.out.println("Select an action:");
            System.out.println("(1) Extract");
            System.out.println("(2) Day of the week");
            System.out.println("(0) Go back");
            System.out.println("\n \n Enter number:");
            Integer num = numberInsertion(2,0);
            if (num != null) {
                System.out.println("\n");
                switch (num) {
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
                        System.out.println("2222Sheet");
                        break;
                }
                System.out.println("\n");
            }

        }
    }

    public static void menuMathematicFunctions(String docSheet, String sheetBlock){
        while(true) {
            System.out.println("In Sheet " + docSheet);
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
                        System.out.println("2222Sheet");
                        break;
                }
                System.out.println("\n");
            }

        }
    }

    public static void menuFunctions(String docSheet, String sheetBlock){
        while(true) {
            System.out.println("In Sheet " + docSheet);
            System.out.println("Select an action:");
            System.out.println("(1) Find Cell");
            System.out.println("(2) Find and Replace");
            System.out.println("(3) Sort Block");
            System.out.println("(4) Floor");
            System.out.println("(5) Convert");
            System.out.println("(6) Mathematic Operations");
            System.out.println("(7) Date Operations");
            System.out.println("(8) Text Operations");
            System.out.println("(0) Go back");
            System.out.println("\n \n Enter number:");
            Integer num = numberInsertion(8,0);
            if (num != null) {
                System.out.println("\n");
                switch (num) {
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
                    default:
                        System.out.println("2222Sheet");
                        break;
                }
                System.out.println("\n");
            }
        }
    }

    public static void menuSheet(String docSheet){
        while(true) {
            System.out.println("In Sheet " + docSheet);
            System.out.println("Select an action:");
            System.out.println("(1) Show Cells");
            System.out.println("(2) Create Row");
            System.out.println("(3) Create Column");
            System.out.println("(4) Delete Row");
            System.out.println("(5) Delete Column");
            System.out.println("(6) Select Block"); //Te lleva al menu de block
            System.out.println("(0) Go back");
            System.out.println("\n \n Enter number:");
            Integer num = numberInsertion(6,0);
            if (num != null) {
                System.out.println("\n");
                switch (num) {
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
                    default:
                        System.out.println("2222Sheet");
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
            System.out.println("\n \n Enter number:");
            Integer num = numberInsertion(4,1);
            if (num != null) {
                System.out.println("\n");
                switch (num) {
                    case 1:
                        for(String name : sheetNames){
                            System.out.println(name);
                        }
                        break;
                    case 2:
                        System.out.println("(4)2t");
                        break;
                    case 3:
                        System.out.println("Input Sheet Name");
                        String title = userInput.nextLine();
                        System.out.println("Input Sheet Rows");
                        Integer nRows = numberInsertion(30000,0);
                        System.out.println("Input Sheet Columns");
                        Integer nCols = numberInsertion(30000,0);
                        String newTitle = DomainController.addSheet(title,nRows,nCols);
                        System.out.println( newTitle + " sheet added to your Document");
                        break;
                    default:
                        if (sheetNames.length < 2) System.out.println("The document has no sheets.");
                        else {
                            System.out.println("Input Sheet Name");
                            String sheet = userInput.nextLine();
                            while (!foundIn(sheet,sheetNames)) {
                                System.out.println("Sheet name not found. Try again:");
                                sheet = userInput.nextLine();
                            }
                            DomainController.deleteSheet(sheet);
                            System.out.println("Sheet deleted");
                        }
                        break;
                }
                System.out.println("\n");
            }
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
            }
            else {
                return num;
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid character, enter a number. \n");
        }
        return null;
    }

    public static void main(String[] args) {
        System.out.println("\n POMC WORKSHEETS");
        System.out.println("------------------------------");
        System.out.println("Enter title for document:");
        String title = userInput.nextLine();
        DomainController.initializeDoc(title,"out/production/v-1.0/com/pomc");
        menuDocument();
    }
}
