package com.pomc.controller;

import com.itextpdf.text.DocumentException;
import com.pomc.classes.*;


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

    private final gestorCSV Csv;
    private final gestorTXT Txt;
    private final ExportPdf Pdf;

    private static gestorPOMC Pomc = new gestorPOMC();

    private final Path dataDirectory = Paths.get("..", "..", "..", "..", "EXE", "dades");

    public PersistenceController(gestorCSV csv, gestorTXT txt, ExportPdf pdf, gestorPOMC pomc) {
        Csv = csv;
        Txt = txt;
        Pdf = pdf;
        Pomc = pomc;
    }


    //Save Files
    public static void save(String path_doc, Document doc) throws IOException {
        Vector<Sheet> sh = doc.getDocSheets();
        Pomc.writePOMC(path_doc,sh);
    }



    public void export(String path_doc, Document doc, String type) throws IOException, DocumentException {
        Vector<Sheet> sh = doc.getDocSheets();

        if(type == "csv"){
            Csv.writeCSV(path_doc, sh.elementAt(0));
        }
        else if(type == "txt"){
            Txt.writeTXT(path_doc, sh.elementAt(0));
        }
        else if(type == "pdf"){
            Vector<String> v = new Vector<>();
            v.addAll(Arrays.asList(path_doc.split(".")));
            Csv.writeCSV(v.elementAt(0)+".csv", sh.elementAt(0));
            Pdf.exportPDF(path_doc, v.elementAt(0)+".csv");
            Csv.deleteCSV(v.elementAt(0)+".csv");
        }

        else System.out.println("Extension not valid, try again.");
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

    public Document imports(String path, String type) throws IOException {
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

        if(type == "csv"){
            sh.add(Csv.readCSV(path));
        }
        else if(type == "txt"){
            sh.add(Txt.readTXT(path));
        }

        Document doc = new Document(title);
        doc.createDocWithSheet(sh);
        return doc;

    }


    public boolean exists(String path) {
        return new File(path).canRead();
    }


}