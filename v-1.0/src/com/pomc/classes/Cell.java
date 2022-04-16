package com.pomc.classes;

import java.util.Objects;
import java.util.Vector;

public abstract class Cell {
    private int row;
    private int column;
    private String type;
    Vector<Cell> refs; //vector de las celdas que "dependen" de esta celda
    private String refInfo; //celdas y operaciones

    /**
     * Creadora
     * @param row
     * @param column
     * @param type
     */
    public Cell(int row,int column,String type){ //debería quitar la creadora??????
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
     * Consultora de la info que la celda referencia
     * @return refinfo
     */
    public String getRefInfo(){
        return refInfo;
    }

    /**
     * Consultora del vector ref
     * @return vector refs
     */
    public Vector<Cell> getRefs(){
        return refs;
    }

    /**
     * Modificadora de RefInfo
     * @param s
     */
    public void setRefInfo(String s){
        this.refInfo=s;
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

    public void AddRef (Cell c){
        refs.add(c);
    }
    public void EliminateRef(Cell c){
        refs.removeElement(c);
    }
    public void EliminateRefInfo(){
        this.refInfo=null;
    }

    public boolean hasRefs(){
        return refs.isEmpty();
    }


}

