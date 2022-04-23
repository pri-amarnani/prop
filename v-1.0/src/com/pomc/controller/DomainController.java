package com.pomc.controller;
import com.pomc.classes.*;

import java.util.Vector;

public class DomainController {
    static Document doc ;

    public static void initializeDoc(String title, String path) {
        doc = new Document(title,path);
    }

    //retorna el nom del doc
    public static String getDocName() {
        return doc.getTitle();
    }

    //returns an array of the sheets names and No sheets if there are no sheets
    public static String[] showSheets() {
        Vector<String> sheetsNames = new Vector<String>();
        if(doc.getDocSheets().size() == 0) return new String[]{"No Sheets"};
        else {
            for (int i = 0; i < doc.getDocSheets().size(); ++i) {
                sheetsNames.add((i+1)+". " + doc.getDocSheets().get(i).getTitle());
            }
            return (String[]) sheetsNames.toArray();
        }
    }
    public static String addSheet(String title, Integer rows, Integer cols) {
        String newSheetTitle = title;
        Integer newSheetRows = rows;
        Integer newSheetCols = cols;
        if (title.equals("")) {
            newSheetTitle = "Sheet " + (doc.getDocSheets().size()+1);
        }
        if (rows == 0) {
            newSheetRows = 64;
        }
        if (cols == 0) {
            newSheetCols = 64;
        }
        doc.createSheet(newSheetRows, newSheetCols, newSheetTitle);
        return newSheetTitle;
    }
    public static void deleteSheet(String title) {
        doc.deleteSheet(title);
    }

}
