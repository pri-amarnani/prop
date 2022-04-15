package com.pomc.classes;

import java.time.LocalDate;
import java.util.Objects;
import java.util.Date;

public class Cell {
    private int row; //que es final??
    private int column;
    private String type;
    protected double infon;
    protected String infot;
    protected LocalDate infod;

    public Cell(int row,int column,String type){
        this.row=row;
        this.column=column;
        this.type=type;
    }

    /**
     * Consultora de la fila
     * @return fila de la celda
     */

    public int getRow(){
        return row;
    }

    /**
     * Consultora de la columna
     * @return columna de la celda
     */

    public int getColumn(){
        return column;
    }

    public String getType(){
        return type;
    }

    public void setColumn(int c){
        this.column=c;
    }

    public void setRow(int r){
        this.row=r;
    }

    public void setType(String t){
        this.type=t;
    }

    public boolean isNum(){
        return  (Objects.equals(type, "N")); //??????????????
    }

    public boolean isText(){
        return (Objects.equals(type, "T"));
    }

    public boolean isDate(){
        return (Objects.equals(type, "D"));
    }

    public double getInfoNum(){return infon;}
    public String getInfoText() {return infot;}
    public LocalDate  getInfoDate(){return infod;}

    public void changeValueN(double n){
        this.infon=n;
    }
    public void changeValueT (String t){
        this.infot=t;
    }
    public void changeValueD (LocalDate d){
        this.infod=d;
    }


}

