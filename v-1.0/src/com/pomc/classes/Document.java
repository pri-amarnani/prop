package com.pomc.classes;
import java.io.*;
import java.util.Scanner;
import java.util.Vector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Document {
    String Title;
    Vector<Sheet> docSheets = new Vector<Sheet>();
    private File docFile;
    private static Scanner userInput = new Scanner(System.in);

    //constructor
    public Document(String Title, String path) {
        this.Title = Title;
        File tempDirectory; //defines where the file is created
        tempDirectory = new File(path);
        this.docFile = new File(tempDirectory.getAbsolutePath() +"/" + Title +".pomc");
            try {
                if (docFile.createNewFile()) {
                    System.out.println("File created: "+ docFile.getName());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
    }

    //getters


    public String getTitle() {
        return Title;
    }

    public Vector<Sheet> getDocSheets() {
        return docSheets;
    }

    //setters
    public void setTitle(String title) {
        Title = title;
    }

    //Sheet management
    public void createSheet(String title) {
        Sheet newSheet = new Sheet(title);
        docSheets.add(newSheet);
    }

    public void createSheet(int rows, int columns, String title) {
        Sheet newSheet = new Sheet(rows,columns,title);
        docSheets.add(newSheet);
    }

    public void deleteSheet(String title) {
        for (int i = 0; i < docSheets.size(); ++i) {
            if (docSheets.get(i).title.equals(title) ) {
                docSheets.remove(i);
                return;
            }
        }
        System.out.println("Unexistent Sheet");
        return;
        //searches by object
    }


    //Auxiliar methods
    public static String validPath() {
        while(true) {
            System.out.println("Enter Document Path :");
            String path = userInput.nextLine();
            File tempDirectory = new File(path);
            if (tempDirectory.isDirectory()) {
                return path;
            }
            else {
                System.out.println("Path not valid, try again.");
            }
        }
    }
    //testing
    public static void main(String[] args) {

        String path = validPath();
        System.out.println("Enter Document Title:");
        String title = userInput.nextLine();
        Document doc = new Document(title,path);
        for (int i = 0; i < 5; i++) {
            doc.createSheet("Hoja"+(5-i));
            doc.createSheet("Hojaa"+(i));
        }

    }
}
