package com.pomc.controller;

import com.itextpdf.text.DocumentException;
import com.pomc.classes.*;


import java.awt.*;
import java.io.*;
import java.util.Arrays;
import java.util.Scanner;
import java.util.Vector;
import java.nio.file.Path;
import java.nio.file.Paths;

public class PersistenceController {
    File docFile;
    FileWriter docWriter;
    static Scanner userInput = new Scanner(System.in);

    String path = "";

    private static gestorCSV Csv = new gestorCSV();
    private static gestorTXT Txt = new gestorTXT();
    private static ExportPdf Pdf = new ExportPdf();

    private static gestorPOMC Pomc = new gestorPOMC();

    private final Path dataDirectory = Paths.get("..", "..", "..", "..", "EXE", "dades");

    //Save Files
    public static void save(String path_doc, Document doc) throws IOException {
        Vector<Sheet> sh = doc.getDocSheets();
        Pomc.writePOMC(path_doc,sh);
    }



    public static void export(String path_doc, Document doc, String type) throws IOException, DocumentException {
        Vector<Sheet> sh = doc.getDocSheets();

        if(type.equals("csv")){
            Csv.writeCSV(path_doc, sh.elementAt(0));
        }
        else if(type.equals("txt")){
            Txt.writeTXT(path_doc, sh.elementAt(0));
        }
        else if(type.equals("pdf")){
            Vector<String> v2 = new Vector<>();
            v2.addAll(Arrays.asList(path_doc.split("/")));
            Vector<String> v = new Vector<>();
            v.addAll(Arrays.asList(v2.elementAt(v2.size()-1).split("\\.(?=[^\\.]+$)")));
            Csv.writeCSV(v.elementAt(0)+".csv", sh.elementAt(0));
            Pdf.exportPDF(v.elementAt(0)+".csv",path_doc);
            Csv.deleteCSV(v.elementAt(0)+".csv");
        }
    }


    //Read Files
    public static Document open(String path) throws IOException {
        Vector<String> path_split = new Vector<>();
        path_split.addAll(Arrays.asList(path.split("/")));

        String title = "";

        int i = 0;
        char aux = path_split.elementAt(path_split.size()-1).charAt(0);

        while(aux != '.'){
            title += aux;
            ++i;
            aux = path_split.elementAt(path_split.size()-1).charAt(i);
        }

        Vector<Sheet> sh = Pomc.readPOMC(path);
        for (int j = 0; j < sh.size(); ++j) {
            sh.elementAt(j).setTitle(sh.elementAt(j).getTitle().substring(1,sh.elementAt(j).getTitle().length()-1));
        }
        Document doc = new Document(title);
        doc.createDocWithSheet(sh);
        return doc;
    }

    public static Document imports(String path, String type) throws IOException {
        Vector<String> path_split = new Vector<>();
        path_split.addAll(Arrays.asList(path.split("/")));

        String title = "";

        int i = 0;
        char aux = path_split.elementAt(path_split.size()-1).charAt(0);

        while(aux != '.'){
            title += aux;
            ++i;
            aux = path_split.elementAt(path_split.size()-1).charAt(i);
        }

        Vector<Sheet> sh = new Vector<>();

        if(type.equals("csv")){
            sh.add(Csv.readCSV(path));
        }
        else if(type.equals("txt")){
            sh.add(Txt.readTXT(path));
        }
        for (int j = 0; j < sh.size(); ++j) {
            sh.elementAt(j).setTitle(sh.elementAt(j).getTitle().substring(1,sh.elementAt(j).getTitle().length()-1));
        }
        Document doc = new Document(title);
        doc.createDocWithSheet(sh);
        return doc;

    }


    public boolean exists(String path) {
        return new File(path).canRead();
    }

    public static void openUG() {
        Path path = Paths.get("DOCS", "UsersGuide.pdf");
        File myFile = path.toFile();
        try {
            Desktop.getDesktop().open(myFile);
        } catch (IOException e) {

        }
    }

    public static void openChart(String path) {
        File myFile = new File(path);
        try {
            Desktop.getDesktop().open(myFile);
        } catch (IOException e) {

        }
    }
}