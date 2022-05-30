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

    /**
     * Crea una hoja con el titulo pasado por parámetro y las celdas vacías
     * @param title
     */
    public void createSheet(String title) {
        Sheet newSheet = new Sheet(title);
        docSheets.add(newSheet);
    }

    /**
     * Crea un documento con una sola hoja (a)
     * @param a
     */
    public void createDocWithSheet(Vector<Sheet> a) {
        for(int i = 0; i < a.size(); ++i) {
            docSheets.add(a.elementAt(i));
        }
    }

    /**
     * Crea una hoja con el número de filas y columnas y el título indicado, y añade la hoja al documento
     * @param rows
     * @param columns
     * @param title
     */
    public void createSheet(int rows, int columns, String title) {
        Sheet newSheet = new Sheet(rows,columns,title);
        docSheets.add(newSheet);
    }

    /**
     * Elimina la hoja con el titulo indicado(title) del documento
     * @param title
     */
    public void deleteSheet(String title) {
        for (int i = 0; i < docSheets.size(); ++i) {
            if (docSheets.get(i).title.equals(title) ) {
                docSheets.remove(i);
                return;
            }
        }
        return;
    }

    public int getNumSheet(){
        return docSheets.size();
    }

    /**
     * Encuentra en las hojas del documento la hoja con el nombre indicado (name)
     * @param name
     * @return devuelve la hoja
     */
    public Sheet getSheet(String name){
        int i=0;
        boolean found=false;
        while (i<docSheets.size() && !found) {
            if (docSheets.elementAt(i).getTitle()==name) {
                found = true;
                return docSheets.elementAt(i);
            }
            else i++;
        }
        return null;
        }

}
