package com.pomc.persistence;

import com.pomc.classes.Document;

import java.io.*;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PersistenceController {
    File docFile;
    FileWriter docWriter;
    static Scanner userInput = new Scanner(System.in);


    dooc(){
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

    //Save Files
    public void save() {
        try {
            docWriter = new FileWriter(docFile.getAbsoluteFile());
            docWriter.write(String.valueOf(docSheets));  //CAMBIAR CUANDO TODO ESTE HECHO
            docWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveAs() {
        try {
            String path = validPath();
            System.out.println("Enter New Title:");
            String Title = userInput.nextLine();
            Document docN = new Document(Title,path);
            docWriter = new FileWriter(docN.docFile.getAbsoluteFile());
            docWriter.write(String.valueOf(this.docSheets));  //CAMBIAR CUANDO TODO ESTE HECHO
            docWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //Read Files
    public static void open(){
        String path = validPath();
        File tempDirectory = new File(path);
        while (true) {
            System.out.println("Enter document title to open:");
            String title = userInput.nextLine();
            File doc = new File(tempDirectory.getAbsolutePath() + "/" + title + ".pomc");
            try {
                Scanner myReader = new Scanner(doc);
                while (myReader.hasNextLine()) {
                    String data = myReader.nextLine();                              //TODO Cambiar manera de llegir
                    System.out.println(data);
                }
                myReader.close();
                break;
            } catch (FileNotFoundException e) {
                System.out.println("File does not exist, try again.");
            }
        }

    }

    //Export Files
    public String escapeSpecialCharacters(String data) {
        String escapedData = data.replaceAll("\\R", " ");
        if (data.contains(",") || data.contains("\"") || data.contains("'")) {
            data = data.replace("\"", "\"\"");
            escapedData = "\"" + data + "\"";
        }
        return escapedData;
    }

    public String convertToCSV(String[] data) {
        return Stream.of(data)
                .map(this::escapeSpecialCharacters)
                .collect(Collectors.joining(","));
    }

    public void export(){
        while (true) {
            System.out.println("Enter extension to export :");
            String ext = userInput.nextLine();
            if (ext.equalsIgnoreCase("csv")) {                          //TODO implementar mas extensiones cuando se defina el formato
                File csvOutputFile = new File(Title +".csv");
                try (PrintWriter pw = new PrintWriter(csvOutputFile)) {
                    docSheets.stream()
                            .map((String data) -> convertToCSV(new String[]{data}))
                            .forEach(pw::println);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                break;
            }
            else {
                System.out.println("Extension not valid, try again.");
            }
        }
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

}
