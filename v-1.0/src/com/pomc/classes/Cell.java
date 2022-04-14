package com.pomc.classes;

import java.util.Objects;
import java.util.Date;

public abstract class Cell {
    private final int row; //que es final??
    private final int column;
    private final String type;


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

    public boolean isNum(){
        return  (Objects.equals(type, "N")); //??????????????
    }

    public boolean isText(){
        return (Objects.equals(type, "T"));
    }

    public boolean isDate(){
        return (Objects.equals(type, "D"));
    }

    public abstract double getInfoNum();
    public abstract String getInfoText();
    public abstract Date getInfoDate();

    public void getInfo(){
       if (Objects.equals(type, "N"))  getInfoNum();
       else if (Objects.equals(type, "T")) getInfoText();
       else if (Objects.equals(type, "D")) getInfoDate();
    }

    public double getInfoN(){
        return getInfoNum();
    }
    public String getInfoT(){
        return getInfoText();
    }
    public Date getInfoD(){
        return getInfoDate();
    }

    public abstract void changeValueN(double n);
    public abstract void changeValueT (String t);
    public abstract void changeValueD (Date d);


}

