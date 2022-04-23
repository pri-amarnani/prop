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
    private Vector<ReferencedCell> refs; //vector de las celdas que "dependen" de esta celda
  //  private Map.Entry<String,Vector<Cell>> refInfo; //celdas y operaciones (la información que yo guardo).


    /**
     * Creadora
     * @param row
     * @param column
     */
    public Cell(int row,int column){ //debería quitar la creadora??????
        this.row=row;
        this.column=column;
        this.refs=null;
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
    public abstract String getType();



    /**
     * Consultora del vector ref
     * @return vector refs
     */
    public Vector<ReferencedCell> getRefs(){
        return refs;
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
     * Consultora
     * @return true si es una celda númerica, false en caso contrario.
     */
    public boolean isNum(){
        String t=getType();
        return  (Objects.equals(t, "N"));
    }

    /**
     * Consultora
     * @return true si es una celda de texto, false en caso contrario.
     */
    public boolean isText(){
        String t=getType();
        return (Objects.equals(t, "T"));
    }

    /**
     * Consultora
     * @return true si es una celda de fecha, false en caso contrario.
     */
    public boolean isDate(){
        String t=getType();
        return (Objects.equals(t, "D"));
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

    public void AddRef (ReferencedCell c){refs.add(c);}

    public void EliminateRef(ReferencedCell c){
        refs.removeElement(c);
    }

    public boolean hasRefs(){
        return !refs.isEmpty();
    }

    public void updateRefs(){
        for (int i=0; i<refs.size();i++){
            ReferencedCell c= refs.elementAt(i);
            Map.Entry<String,Vector<Cell>> s= c.getRefInfo();
            //Volver a hacer esa operación con los nuevos valores
            String op= s.getKey();
            Vector<Cell> cellsref= s.getValue();
//sum(c1,c2.c3)
            if (Objects.equals(op, "sum")) {
                double newvalue = 0;
                for (int j=0;j<cellsref.size();j++){
                    newvalue= newvalue+ (double) (cellsref.elementAt(j)).getInfo();
                }
                c.changeValue(newvalue);
            }

            else if (Objects.equals(op, "sub")) {
                double newvalue = (double) cellsref.elementAt(0).getInfo();
                for (int j=1;j<cellsref.size();j++){
                    newvalue= newvalue- (double) (cellsref.elementAt(j)).getInfo();
                }
                c.changeValue(newvalue);
            }

            else if (Objects.equals(op, "*")) {
                double newvalue = (double) cellsref.elementAt(0).getInfo();
                for (int j=1;j<cellsref.size();j++){
                    newvalue= newvalue* (double) (cellsref.elementAt(j)).getInfo();
                }
                c.changeValue(newvalue);
            }
            else if (Objects.equals(op, "/")) {
                double newvalue = (double) cellsref.elementAt(0).getInfo();
                for (int j=1;j<cellsref.size();j++){
                    newvalue= newvalue/ (double) (cellsref.elementAt(j)).getInfo();
                }
                c.changeValue(newvalue);
            }
            else if(Objects.equals(op, "floor")){
                double newvalue = (double) cellsref.elementAt(0).getInfo();
                newvalue= Math.floor(newvalue*10)/100;
                c.changeValue(newvalue);
            }

            else if(Objects.equals(op, "mTOcm")){
                UnitOf.Length length = new UnitOf.Length();
                double newvalue= (double) cellsref.elementAt(0).getInfo();
                newvalue= length.fromMeters(newvalue).toCentimeters();
                c.changeValue(newvalue);
            }
            else if(Objects.equals(op, "mTOkm")){
                UnitOf.Length length = new UnitOf.Length();
                double newvalue= (double) cellsref.elementAt(0).getInfo();
                newvalue= length.fromMeters(newvalue).toKilometers();
                c.changeValue(newvalue);
            }
            else if(Objects.equals(op, "mTOinchess")){
                UnitOf.Length length = new UnitOf.Length();
                double newvalue= (double) cellsref.elementAt(0).getInfo();
                newvalue= length.fromMeters(newvalue).toInches();
                c.changeValue(newvalue);
            }
            else if(Objects.equals(op, "inchesTOm")){
                UnitOf.Length length = new UnitOf.Length();
                double newvalue= (double) cellsref.elementAt(0).getInfo();
                newvalue= length.fromInches(newvalue).toMeters();
                c.changeValue(newvalue);
            }
            else if(Objects.equals(op, "cmTOm")){
                UnitOf.Length length = new UnitOf.Length();
                double newvalue= (double) cellsref.elementAt(0).getInfo();
                newvalue= length.fromCentimeters(newvalue).toMeters();
                c.changeValue(newvalue);
            }
            else if(Objects.equals(op, "cmTOkm")){
                UnitOf.Length length = new UnitOf.Length();
                double newvalue= (double) cellsref.elementAt(0).getInfo();
                newvalue= length.fromCentimeters(newvalue).toKilometers();
                c.changeValue(newvalue);
            }

            else if(Objects.equals(op, "kmTOcm")){
                UnitOf.Length length = new UnitOf.Length();
                double newvalue= (double) cellsref.elementAt(0).getInfo();
                newvalue= length.fromKilometers(newvalue).toCentimeters();
                c.changeValue(newvalue);
            }
            else if(Objects.equals(op, "kmTOm")){
                UnitOf.Length length = new UnitOf.Length();
                double newvalue= (double) cellsref.elementAt(0).getInfo();
                newvalue= length.fromKilometers(newvalue).toMeters();
                c.changeValue(newvalue);
            }

            else if(Objects.equals(op, "day")){
                LocalDate newvaluedate= (LocalDate) cellsref.elementAt((0)).getInfo();
                int newvalue=0;
                newvalue= newvaluedate.getDayOfMonth();
                c.changeValue(newvalue);
            }
            else if(Objects.equals(op, "month")){
                LocalDate newvaluedate= (LocalDate) cellsref.elementAt((0)).getInfo();
                int newvalue=0;
                newvalue= newvaluedate.getMonthValue();
                c.changeValue(newvalue);
            }
            else if(Objects.equals(op, "year")){
                LocalDate newvaluedate= (LocalDate) cellsref.elementAt(0).getInfo();
                int newvalue=0;
                newvalue= newvaluedate.getYear();
                c.changeValue(newvalue);
            }
            else if(Objects.equals(op, "dayoftheWeek")){
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

    public abstract void setRefInfo(Map.Entry<String, Vector<Cell>> refInfo);

}

