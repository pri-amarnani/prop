package com.pomc.classes;
import java.io.*;
import java.util.Scanner;
import java.util.Vector;

public class Document {
    String Title;
    Vector<Sheet> docSheets = new Vector<Sheet>();

    //constructor
    public Document(String Title) {
        this.Title = Title;
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

    public void createDocWithSheet(Sheet a) {
        Title = a.getTitle();
        docSheets.add(a);
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
    }
}
