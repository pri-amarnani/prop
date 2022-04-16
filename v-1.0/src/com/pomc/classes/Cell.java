package com.pomc.classes;

import java.util.Objects;

public abstract class Cell {
    private int row;
    private int column;
    private String type;

    /**
     * Creadora
     * @param row
     * @param column
     * @param type
     */
    public Cell(int row,int column,String type){
        this.row=row;
        this.column=column;
        this.type=type;
        //faltan el array de punteros para las referencias!!!
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

    /**
     * Consultora del tipo
     * @return tipo de celda
     */
    public String getType(){
        return type;
    }

    /**
     * Modificadora de columna, la columna de la celda pasa a ser c.
     * @param c
     */
    public void setColumn(int c){
        this.column=c;
    }

    /**
     * Modificadora de la fila, la fila de la celda pasa a ser r.
     * @param r
     */
    public void setRow(int r){
        this.row=r;
    }

    /**
     * Modificadora del type, el tipo de celda pasa a ser t.
     * @param t
     */
    public void setType(String t){
        this.type=t;
    }

    /**
     * Consultora
     * @return true si es una celda númerica, false en caso contrario.
     */
    public boolean isNum(){
        return  (Objects.equals(type, "N"));
    }

    /**
     * Consultora
     * @return true si es una celda de texto, false en caso contrario.
     */
    public boolean isText(){
        return (Objects.equals(type, "T"));
    }

    /**
     * Consultora
     * @return true si es una celda de fecha, false en caso contrario.
     */
    public boolean isDate(){
        return (Objects.equals(type, "D"));
    }

    /**
     * Consultora
     * @return la información guardada en la celda según el tipo de celda
     */
    public abstract Object getInfo();

    /**
     * Modificadora, cambia el valor de la celda
     * @param o
     */
    public abstract void changeValue(Object o);


}

