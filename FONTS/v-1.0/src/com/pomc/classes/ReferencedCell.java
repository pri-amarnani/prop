package com.pomc.classes;

import java.time.LocalDate;
import java.util.*;

public class ReferencedCell extends Cell {
    private String info;
    private Object content;
    private final String type="R";
    private Map.Entry<String,Vector<Cell>> refInfo; //celdas y operaciones (la información que yo guardo).


    public ReferencedCell (int row, int col, String info){
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
        int myrow=getRow();
        int mycol=getColumn();
        this.info= null;
        this.setRow(null);
        this.setColumn(null);
        System.gc();

        if (o.getClass()==String.class){
            TextCell t=new TextCell(myrow,mycol, (String) o);
            return t;
        }
        else if(o.getClass()== LocalDate.class){
            DateCell d=new DateCell(myrow,mycol,(LocalDate)o);
            return d;
        }
        else if (o.getClass()==Double.class){
            NumCell n=new NumCell(myrow,mycol, (Double) o);
            return n;
        }

        return this;
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

    public void setRCell(Cell c){
        Vector<Cell> rcell= refInfo.getValue();
        for (int i=0;i<rcell.size();i++){
            Cell c2=rcell.elementAt(i);
            if (c.getRow()==c2.getRow() && c.getColumn()==c2.getColumn()){
                rcell.setElementAt(c,i);
                refInfo.setValue(rcell);
            }
        }

    }


}
