package com.pomc.classes;

import java.util.Scanner;

public class Main {

    static Scanner userInput = new Scanner(System.in);
    static Document doc  = new Document("Document1","out/production/v-1.0");

    public static void menuTextFunctions(Sheet docSheet, Block sheetBlock){
        while(true) {
            System.out.println("In Sheet " + docSheet.title);
            System.out.println("Select an action:");
            System.out.println("(1) Replace with Criteria");
            System.out.println("(2) Length");
            System.out.println("(0) Go back");
            System.out.println("/n /n Enter number:");
            Integer num = Integer.valueOf(userInput.nextLine());
            if (num > 4) {
                System.out.println("/n Invalid number, try again.");
            }

        }
    }

    public static void menuDateFunctions(Sheet docSheet, Block sheetBlock){
        while(true) {
            System.out.println("In Sheet " + docSheet.title);
            System.out.println("Select an action:");
            System.out.println("(1) Extract");
            System.out.println("(2) Day of the week");
            System.out.println("(0) Go back");
            System.out.println("/n /n Enter number:");
            Integer num = Integer.valueOf(userInput.nextLine());
            if (num > 4) {
                System.out.println("/n Invalid number, try again.");
            }

        }
    }

    public static void menuMathematicFunctions(Sheet docSheet, Block sheetBlock){
        while(true) {
            System.out.println("In Sheet " + docSheet.title);
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
            System.out.println("/n /n Enter number:");
            Integer num = Integer.valueOf(userInput.nextLine());
            if (num > 4) {
                System.out.println("/n Invalid number, try again.");
            }

        }
    }

    public static void menuFunctions(Sheet docSheet, Block sheetBlock){
        while(true) {
            System.out.println("In Sheet " + docSheet.title);
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
            System.out.println("/n /n Enter number:");
            Integer num = Integer.valueOf(userInput.nextLine());
            if (num > 4) {
                System.out.println("/n Invalid number, try again.");
            }

        }
    }

    public static void menuSheet(Sheet docSheet){
        while(true) {
            System.out.println("In Sheet " + docSheet.title);
            System.out.println("Select an action:");
            System.out.println("(1) Show Cells");
            System.out.println("(2) Create Row");
            System.out.println("(3) Create Column");
            System.out.println("(4) Delete Row");
            System.out.println("(5) Delete Column");
            System.out.println("(6) Select Block"); //Te lleva al menu de block
            System.out.println("(0) Go back");
            System.out.println("/n /n Enter number:");
            Integer num = Integer.valueOf(userInput.nextLine());
            if (num > 4) {
                System.out.println("/n Invalid number, try again.");
            }
        }
    }
    public static void menuDocument(){
        while(true) {
            System.out.println("In document " + doc.Title);
            System.out.println("Select an action:");
            System.out.println("(1) Show Sheets");
            System.out.println("(2) Select a Sheet");
            System.out.println("(3) Create a Sheet");
            System.out.println("(4) Delete a Sheet");
            System.out.println("/n /n Enter number:");
            Integer num = Integer.valueOf(userInput.nextLine());
            if (num > 4) {
                System.out.println("/n Invalid number, try again.");
            }
        }
    }


    public static void main(String[] args) {

        System.out.println("POMC WORKSHEETS");
        menuDocument();
    }
}
