package com.pomc.classes;

import java.util.Map;
import java.util.Objects;
import java.util.Vector;
public abstract class Cell {
    private int row;
    private int column;
    private String type;
    private Vector<Cell> refs; //vector de las celdas que "dependen" de esta celda
    private Vector<Map.Entry<String,Map.Entry<Cell,Cell>>> refInfo; //celdas y operaciones (la información que yo guardo).


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
    public Vector<Map.Entry<String,Map.Entry<Cell,Cell>>> getRefInfo(){
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
    public void setRefInfo(Vector<Map.Entry<String,Map.Entry<Cell,Cell>>> s){
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

    public void addRefInfo(Map.Entry<String,Map.Entry<Cell,Cell>> s){
        this.refInfo.add(s);
    }
    public void updateRefs(){
        Vector<Cell> v= getRefs();
        for (int i=0; i<v.size();i++){
            Cell c= v.elementAt(i);
            Vector<Map.Entry<String,Map.Entry<Cell,Cell>>> r= c.getRefInfo();
            //Volver a hacer esa operación con los nuevos valores
            for (int j=0; j<r.size();j++){
                Map.Entry<String,Map.Entry<Cell,Cell>> s= r.elementAt(j);
                String op= s.getKey();
                Map.Entry<Cell,Cell> cellsref= s.getValue();
                Cell cr1=cellsref.getKey();
                Cell cr2=cellsref.getValue();
                if (Objects.equals(op, "+") && Objects.equals(type, "N")) {
                    double myinfo= (double) getInfo();
                    c.changeValue(myinfo+(double)c.getInfo());
                }
                else if (Objects.equals(op, "-") && Objects.equals(type, "N")) {
                    double myinfo= (double) getInfo();
                    c.changeValue(myinfo-(double) c.getInfo());
                }
                else if (Objects.equals(op, "*")&& Objects.equals(type, "N")) {
                    double myinfo= (double) getInfo();
                    c.changeValue(myinfo*(double) c.getInfo());
                }
                else if (Objects.equals(op, "/")&& Objects.equals(type, "N")) {
                    double myinfo= (double) getInfo();
                    c.changeValue(myinfo/(double) c.getInfo());
                }
                else if(Objects.equals(op, "floor")&& Objects.equals(type, "N")){
                    double myinfo= (double) getInfo();
                    c.changeValue((double)Math.floor(myinfo*10)/100);
                }
                else if(Objects.equals(op, "convert")&& Objects.equals(type, "N")){

                }
                else if(Objects.equals(op, "extract")&& Objects.equals(type, "T")){

                }
                else if(Objects.equals(op, "dayoftheWeek")&& Objects.equals(type, "T")){

                }

            }
        }
    }




}

