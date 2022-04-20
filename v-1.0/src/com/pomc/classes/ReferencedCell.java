package com.pomc.classes;

import java.util.*;

public class ReferencedCell extends Cell {
    private String info;
    private Object value;
    private final String type="R";
    private Map.Entry<String,Vector<Cell>> refInfo; //celdas y operaciones (la información que yo guardo).


    public ReferencedCell (int row, int col,String info){
        super(row,col);
        this.info= info;
    }

    @Override
    public String getType() {
        return type;
    }

    @Override
    public Object getInfo() {
        return info;
    }


    public Map.Entry<String, Vector<Cell>> getRefInfo() {
        return refInfo;
    }

    public Object getValue(){
        return value;
    }

    public void setRefInfo(Map.Entry<String, Vector<Cell>> refInfo) {
        this.refInfo = refInfo;
    }

    public void setInfo(String s){
        this.info=s;
    }

    public void changeValue(Object o) {this.value=o;}

}
