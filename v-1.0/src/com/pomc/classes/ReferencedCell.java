package com.pomc.classes;

import com.digidemic.unitof.UnitOf;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.*;

public class ReferencedCell extends Cell {
    String info;
    Object value;
    private Map.Entry<String,Vector<Cell>> refInfo; //celdas y operaciones (la información que yo guardo).

    public ReferencedCell (int row, int col,String type,String info){
        super(row,col,type);
        this.info= info;
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

    public void setValue(Object value) {
        this.value = value;
    }

    public void changeValue(Object o) {this.value=o;}

}
