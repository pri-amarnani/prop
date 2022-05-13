package com.pomc.view;

import com.pomc.classes.Document;

public class PresentationController {
    static Document doc;
    public static void newDoc(String s){
        if (s.isEmpty()) doc= new Document("untitled_doc");
        else doc=new Document(s);
    }
}
