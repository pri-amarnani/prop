package com.pomc.classes;

import java.util.*;

public class ReferencedCell extends Cell {
    private String info;
    private Object content;
    private final String type="R";
    private Map.Entry<String,Vector<Cell>> refInfo; //celdas y operaciones (la información que yo guardo).


    public ReferencedCell (int row, int col,String info){
        super(row,col);
        this.info= info;
    }

    @Override
    public String getType() {
        return this.type;
    }

    @Override
    public Object getInfo() {
        return this.info;
    }

    @Override
    public Object changeValue(Object o) {
        return null;
    }


    public Map.Entry<String, Vector<Cell>> getRefInfo() {
        return this.refInfo;
    }

    public Object getContent(){
        return this.content;
    }

    public void setRefInfo(Map.Entry<String, Vector<Cell>> refInfo) {
        this.refInfo = refInfo;
    }

    public void setInfo(String s){
        this.info=s;
    }

    public void setContent(Object o) {this.content=o;}


}
