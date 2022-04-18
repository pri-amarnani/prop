package com.pomc.classes;

import com.digidemic.unitof.UnitOf;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Map;
import java.util.Objects;
import java.util.Vector;
public abstract class Cell {
    private int row;
    private int column;
    private String type;
    private Vector<Cell> refs; //vector de las celdas que "dependen" de esta celda
    private Map.Entry<String,Vector<Cell>> refInfo; //celdas y operaciones (la información que yo guardo).


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
    public Map.Entry<String,Vector<Cell>> getRefInfo(){
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
    public void setRefInfo(Map.Entry<String,Vector<Cell>> s){
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
        this.refInfo=null;
    }
    public void EliminateRef(Cell c){
        refs.removeElement(c);
    }

    public boolean hasRefs(){
        return !refs.isEmpty();
    }

    public void addRefInfo(Map.Entry<String,Vector<Cell>> s){
        this.refInfo=s;
        this.refs=null;
    }

    public void updateRefs(){
        for (int i=0; i<refs.size();i++){
            Cell c= refs.elementAt(i);
            Map.Entry<String,Vector<Cell>> s= c.getRefInfo();
            //Volver a hacer esa operación con los nuevos valores
                String op= s.getKey();
                Vector<Cell> cellsref= s.getValue();
//sum(c1,c2.c3)
                if (Objects.equals(op, "sum") && Objects.equals(type, "N")) {
                    double newvalue = 0;
                    for (int j=0;j<cellsref.size();j++){
                        newvalue= newvalue+ (double) (cellsref.elementAt(j)).getInfo();
                    }
                    c.changeValue(newvalue);
                }

                else if (Objects.equals(op, "sub") && Objects.equals(type, "N")) {
                    double newvalue = (double) cellsref.elementAt(0).getInfo();
                    for (int j=1;j<cellsref.size();j++){
                        newvalue= newvalue- (double) (cellsref.elementAt(j)).getInfo();
                    }
                    c.changeValue(newvalue);
                }

                else if (Objects.equals(op, "*")&& Objects.equals(type, "N")) {
                    double newvalue = (double) cellsref.elementAt(0).getInfo();
                    for (int j=1;j<cellsref.size();j++){
                        newvalue= newvalue* (double) (cellsref.elementAt(j)).getInfo();
                    }
                    c.changeValue(newvalue);
                }
                else if (Objects.equals(op, "/")&& Objects.equals(type, "N")) {
                    double newvalue = (double) cellsref.elementAt(0).getInfo();
                    for (int j=1;j<cellsref.size();j++){
                        newvalue= newvalue/ (double) (cellsref.elementAt(j)).getInfo();
                    }
                    c.changeValue(newvalue);
                }
                else if(Objects.equals(op, "floor")&& Objects.equals(type, "N")){
                    double newvalue = (double) cellsref.elementAt(0).getInfo();
                    newvalue= Math.floor(newvalue*10)/100;
                    c.changeValue(newvalue);
                }

                else if(Objects.equals(op, "mTOcm")&& Objects.equals(type, "N")){
                    UnitOf.Length length = new UnitOf.Length();
                    double newvalue= (double) cellsref.elementAt(0).getInfo();
                    newvalue= length.fromMeters(newvalue).toCentimeters();
                    c.changeValue(newvalue);
                }
                else if(Objects.equals(op, "mTOkm")&& Objects.equals(type, "N")){
                    UnitOf.Length length = new UnitOf.Length();
                    double newvalue= (double) cellsref.elementAt(0).getInfo();
                    newvalue= length.fromMeters(newvalue).toKilometers();
                    c.changeValue(newvalue);
                }
                else if(Objects.equals(op, "mTOinchess")&& Objects.equals(type, "N")){
                    UnitOf.Length length = new UnitOf.Length();
                    double newvalue= (double) cellsref.elementAt(0).getInfo();
                    newvalue= length.fromMeters(newvalue).toInches();
                    c.changeValue(newvalue);
                }
                else if(Objects.equals(op, "inchesTOm")&& Objects.equals(type, "N")){
                    UnitOf.Length length = new UnitOf.Length();
                    double newvalue= (double) cellsref.elementAt(0).getInfo();
                    newvalue= length.fromInches(newvalue).toMeters();
                    c.changeValue(newvalue);
                }
                else if(Objects.equals(op, "cmTOm")&& Objects.equals(type, "N")){
                    UnitOf.Length length = new UnitOf.Length();
                    double newvalue= (double) cellsref.elementAt(0).getInfo();
                    newvalue= length.fromCentimeters(newvalue).toMeters();
                    c.changeValue(newvalue);
                }
                else if(Objects.equals(op, "cmTOkm")&& Objects.equals(type, "N")){
                    UnitOf.Length length = new UnitOf.Length();
                    double newvalue= (double) cellsref.elementAt(0).getInfo();
                    newvalue= length.fromCentimeters(newvalue).toKilometers();
                    c.changeValue(newvalue);
                }

                else if(Objects.equals(op, "kmTOcm")&& Objects.equals(type, "N")){
                    UnitOf.Length length = new UnitOf.Length();
                    double newvalue= (double) cellsref.elementAt(0).getInfo();
                    newvalue= length.fromKilometers(newvalue).toCentimeters();
                    c.changeValue(newvalue);
                }
                else if(Objects.equals(op, "kmTOm")&& Objects.equals(type, "N")){
                    UnitOf.Length length = new UnitOf.Length();
                    double newvalue= (double) cellsref.elementAt(0).getInfo();
                    newvalue= length.fromKilometers(newvalue).toMeters();
                    c.changeValue(newvalue);
                }

                else if(Objects.equals(op, "day")&& Objects.equals(type, "D")){
                    LocalDate newvaluedate= (LocalDate) cellsref.elementAt((0)).getInfo();
                    int newvalue=0;
                    newvalue= newvaluedate.getDayOfMonth();
                    c.changeValue(newvalue);
                }
                else if(Objects.equals(op, "month")&& Objects.equals(type, "D")){
                    LocalDate newvaluedate= (LocalDate) cellsref.elementAt((0)).getInfo();
                    int newvalue=0;
                    newvalue= newvaluedate.getMonthValue();
                    c.changeValue(newvalue);
                }
                else if(Objects.equals(op, "year")&& Objects.equals(type, "D")){
                    LocalDate newvaluedate= (LocalDate) cellsref.elementAt(0).getInfo();
                    int newvalue=0;
                    newvalue= newvaluedate.getYear();
                    c.changeValue(newvalue);
                }
                else if(Objects.equals(op, "dayoftheWeek")&& Objects.equals(type, "D")){
                    LocalDate newvaluedate= (LocalDate) cellsref.elementAt(0).getInfo();
                    String newvalue="";
                    DayOfWeek d= newvaluedate.getDayOfWeek();
                    int day= d.getValue();
                    if(day==1) newvalue= "Monday";
                    else if (day==2) newvalue= "Tuesday";
                    else if (day==3) newvalue= "Wednesday";
                    else if (day==4) newvalue= "Thursday";
                    else if (day==5) newvalue= "Friday";
                    else if (day==6) newvalue= "Saturday";
                    else if (day==7) newvalue= "Sunday";
                    c.changeValue(newvalue);
                }
        }
    }




}

