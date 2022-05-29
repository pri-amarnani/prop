package com.pomc.classes;

import com.digidemic.unitof.D;
import com.digidemic.unitof.UnitOf;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.*;

public abstract class Cell {
    private Integer row;
    private Integer column;
    private Vector<ReferencedCell> refs= new Vector<>();
  //  private Map.Entry<String,Vector<Cell>> refInfo; //celdas y operaciones (la información que yo guardo).


    /**
     * Creadora
     * @param row
     * @param column
     */
    public Cell(int row,int column){ //debería quitar la creadora??????
        this.row=row;
        this.column=column;
        this.refs = new Vector<>();
    }

    /**
     * Consultora de la fila
     * @return fila de la celda
     */

    public int getRow(){
        return this.row;
    }

    /**
     * Consultora de la columna
     * @return columna de la celda
     */

    public int getColumn(){
        return this.column;
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
        return this.refs;
    }


    /**
     * Modificadora de columna, la columna de la celda pasa a ser c.
     * @param c
     */
    public void setColumn(Integer c){
        this.column=c;
    }

    /**
     * Modificadora de la fila, la fila de la celda pasa a ser r.
     * @param r
     */
    public void setRow(Integer r){
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
    public abstract Object changeValue(Object o);

    public void AddRef (ReferencedCell c){this.refs.add(c);}

    public void EliminateRef(ReferencedCell c){
        refs.removeElement(c);
    }

    public boolean hasRefs(){
        return !refs.isEmpty();
    }

    public void updateRefs(){

        for (int i=0; i<this.refs.size();i++){
            ReferencedCell c= this.refs.elementAt(i);
            Map.Entry<String,Vector<Cell>> s= c.getRefInfo();

            String op= s.getKey();
            Vector<Cell> cellsref= s.getValue();

            if (Objects.equals(op, "sum")) {
                Double newvalue = 0.0;
                for (int j=0;j<cellsref.size();j++){
                    newvalue= newvalue+ (Double) ((cellsref.elementAt(j)).getInfo());
                }
                c.setContent(newvalue);
            }

            else if (Objects.equals(op, "sub")) {
                Double newvalue = (Double) cellsref.elementAt(0).getInfo();
                for (int j=1;j<cellsref.size();j++){
                    newvalue= newvalue- (Double) (cellsref.elementAt(j)).getInfo();
                }
                c.setContent(newvalue);
            }

            else if (Objects.equals(op, "mult")) {
                Double newvalue = (Double) cellsref.elementAt(0).getInfo();
                for (int j=1;j<cellsref.size();j++){
                    newvalue= newvalue* (Double) (cellsref.elementAt(j)).getInfo();
                }
                c.setContent(newvalue);
            }
            else if (Objects.equals(op, "div")) {
                Double newvalue = (Double) cellsref.elementAt(0).getInfo();
                for (int j=1;j<cellsref.size();j++){
                    newvalue= newvalue/ (Double) (cellsref.elementAt(j)).getInfo();
                }
                c.setContent(newvalue);
            }

            if (Objects.equals(op, "concatenate")) {
                String newvalue = "";
                for (int j=0;j<cellsref.size();j++){
                    newvalue= newvalue + (String) ((cellsref.elementAt(j)).getInfo());
                }
                c.setContent(newvalue);
            }

            else if(Objects.equals(op, "floor")){
                Double newvalue = (Double) cellsref.elementAt(0).getInfo();
                newvalue= Math.floor(newvalue);
                c.setContent(newvalue);
            }

            else if(Objects.equals(op, "ceil")){
                Double newvalue = (Double) cellsref.elementAt(0).getInfo();
                newvalue= Math.ceil(newvalue);
                c.setContent(newvalue);
            }

            else if(Objects.equals(op, "mTOcm")){
                UnitOf.Length length = new UnitOf.Length();
                Double newvalue= (Double) cellsref.elementAt(0).getInfo();
                newvalue= length.fromMeters(newvalue).toCentimeters();
                c.setContent(newvalue);
            }
            else if(Objects.equals(op, "mTOkm")){
                UnitOf.Length length = new UnitOf.Length();
                Double newvalue= (Double) cellsref.elementAt(0).getInfo();
                newvalue= length.fromMeters(newvalue).toKilometers();
                c.setContent(newvalue);
            }
            else if(Objects.equals(op, "mTOinches")){
                UnitOf.Length length = new UnitOf.Length();
                Double newvalue= (Double) cellsref.elementAt(0).getInfo();
                newvalue= length.fromMeters(newvalue).toInches();
                c.setContent(newvalue);
            }
            else if(Objects.equals(op, "inchesTOm")){
                UnitOf.Length length = new UnitOf.Length();
                Double newvalue= (Double) cellsref.elementAt(0).getInfo();
                newvalue= length.fromInches(newvalue).toMeters();
                c.setContent(newvalue);
            }
            else if(Objects.equals(op, "cmTOm")){
                UnitOf.Length length = new UnitOf.Length();
                Double newvalue= (Double) cellsref.elementAt(0).getInfo();
                newvalue= length.fromCentimeters(newvalue).toMeters();
                c.setContent(newvalue);
            }
            else if(Objects.equals(op, "cmTOkm")){
                UnitOf.Length length = new UnitOf.Length();
                Double newvalue= (Double) cellsref.elementAt(0).getInfo();
                newvalue= length.fromCentimeters(newvalue).toKilometers();
                c.setContent(newvalue);
            }

            else if(Objects.equals(op, "kmTOcm")){
                UnitOf.Length length = new UnitOf.Length();
                Double newvalue= (Double) cellsref.elementAt(0).getInfo();
                newvalue= length.fromKilometers(newvalue).toCentimeters();
                c.setContent(newvalue);
            }
            else if(Objects.equals(op, "kmTOm")){
                UnitOf.Length length = new UnitOf.Length();
                double newvalue= (double) cellsref.elementAt(0).getInfo();
                newvalue= length.fromKilometers(newvalue).toMeters();
                c.setContent(newvalue);
            }
            else if(Objects.equals(op, "mean")){
                double auxsum=0;
                double newvalue= 0;
                for(int j=0; j<cellsref.size(); j++){
                    auxsum += (Double) cellsref.elementAt(j).getInfo();
                }
                newvalue=auxsum/cellsref.size();
                c.setContent(newvalue);
            }
            else if(Objects.equals(op, "max")){
                double auxsum=0;
                double newvalue;
                boolean first = true;
                for(int j=0; j<cellsref.size(); j++){
                    if (first) auxsum = (Double) cellsref.elementAt(j).getInfo();
                    else auxsum = Math.max(auxsum, (Double) cellsref.elementAt(j).getInfo());
                    first = false;
                }
                newvalue=auxsum;
                c.setContent(newvalue);
            }
            else if(Objects.equals(op, "min")){
                double auxsum=0;
                double newvalue;
                boolean first = true;
                for(int j=0; j<cellsref.size(); j++){
                    if (first) auxsum = (Double) cellsref.elementAt(j).getInfo();
                    else auxsum = Math.min(auxsum, (Double) cellsref.elementAt(j).getInfo());
                    first = false;
                }
                newvalue=auxsum;
                c.setContent(newvalue);
            }
            else if(Objects.equals(op, "sumAll")){
                double auxsum=0;
                double newvalue;
                for(int j=0; j<cellsref.size(); j++){
                    auxsum += (Double) cellsref.elementAt(j).getInfo();
                }
                newvalue=auxsum;
                c.setContent(newvalue);
            }
            else if(Objects.equals(op, "subAll")){
                double auxsum=0;
                double newvalue;
                for(int j=0; j<cellsref.size(); j++){
                    Double val = (Double) cellsref.elementAt(j).getInfo();
                    auxsum -= val;
                }
                newvalue=auxsum;
                c.setContent(newvalue);
            }
            else if(Objects.equals(op, "multAll")){
                double auxsum=1;
                double newvalue;
                for(int j=0; j<cellsref.size(); j++){
                    auxsum *= (Double) cellsref.elementAt(j).getInfo();
                }
                newvalue=auxsum;
                c.setContent(newvalue);
            }
            else if(Objects.equals(op, "divAll")){
                double auxsum=1;
                double newvalue;
                for(int j=0; j<cellsref.size(); j++){
                    auxsum /= (Double) cellsref.elementAt(j).getInfo();
                }
                newvalue=auxsum;
                c.setContent(newvalue);
            }
            else if(Objects.equals(op, "median")){
                Double [] arr= new Double [cellsref.size()];
                int i2=0;
                for (int j=0; j< cellsref.size();j++){
                    arr[i2]= (Double) cellsref.elementAt(j).getInfo();
                    i2 += 1;
                }
                Arrays.sort(arr);
                c.setContent(arr[arr.length/2]);
            }
            else if(Objects.equals(op, "var")){
                Double d= calcstd(cellsref);
                Double newvalue= Math.pow(d,2);
                c.setContent(newvalue);
            }

            else if(Objects.equals(op, "covar")){
                int size=cellsref.size();
                Vector <Cell> v1= new Vector<>();
                Vector <Cell> v2= new Vector<>();
                Double auxsum1=0.0,auxsum2=0.0;

                for (int k=0; k<size/2;k++){
                    v1.add(cellsref.elementAt(k));
                }

                for (int j=size/2; j<size;j++){
                    v2.add(cellsref.elementAt(j));
                }

                Double cov= calccovar(v1,v2);
                System.out.print(cov);
                c.setContent(cov);
            }

            else if (Objects.equals(op,"std")){
                c.setContent(calcstd(cellsref));
            }
            else if (Objects.equals(op,"cp")){
                int size=cellsref.size();
                Vector <Cell> v1= new Vector<>();
                Vector <Cell> v2= new Vector<>();
                Double auxsum1=0.0,auxsum2=0.0;

                for (int k=0; k<size/2-1;k++){
                    v1.add(cellsref.elementAt(k));
                }

                for (int j=size/2; j<size;j++){
                    v2.add(cellsref.elementAt(j));
                }

                Double d1= (Double) calcstd(v1);
                Double d2= (Double) calcstd(v2);

                Double cp;
                if (d1==0 || d2==0) cp=1.0;
                else cp=(Double) calccovar(v1,v2)/(d1*d2);

                if (cp>1.0) cp=1.0;
                if (cp<-1.0) cp=-1.0;
                c.setContent(cp);
            }
            else if(Objects.equals(op, "day")){
                System.out.println("ENTRA DAY!");
                LocalDate newvaluedate= (LocalDate) cellsref.elementAt((0)).getInfo();
                System.out.println(newvaluedate);
                Double newvalue=0.0;
                newvalue= Double.valueOf(newvaluedate.getDayOfMonth());
                c.setContent(newvalue);
                System.out.println(newvalue);
                System.out.println(this.getType());
            }
            else if(Objects.equals(op, "month")){
                LocalDate newvaluedate= (LocalDate) cellsref.elementAt((0)).getInfo();
                Double newvalue=0.0;
                newvalue= Double.valueOf(newvaluedate.getMonthValue());
                c.setContent(newvalue);
            }
            else if(Objects.equals(op, "year")){
                LocalDate newvaluedate= (LocalDate) cellsref.elementAt(0).getInfo();
                Double newvalue=0.0;
                newvalue= Double.valueOf(newvaluedate.getYear());
                c.setContent(newvalue);
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
                c.setContent(newvalue);
            }
            else if (Objects.equals(op,"equal")){
                c.setContent(cellsref.elementAt(i).getInfo());
            }
            else if(Objects.equals(op, "lengthwords")){
                TextCell tc = (TextCell) cellsref.elementAt(0);
                c.setContent(tc.length("words"));
            }
            else if(Objects.equals(op, "lengthletters")){
                TextCell tc = (TextCell) cellsref.elementAt(0);
                c.setContent(tc.length("letters"));
            }
            else if(Objects.equals(op, "lengthcharacters")){
                TextCell tc = (TextCell) cellsref.elementAt(0);
                c.setContent(tc.length("characters"));
            }
        }
    }

    public abstract void setRefInfo(Map.Entry<String, Vector<Cell>> refInfo);

    public abstract Object getContent();
    public abstract void setContent(Object o);

    public Double calcstd (Vector<Cell> vcells){

        Double sum=0.0;
        Double std=0.0;
        int size= vcells.size();
        Double [] arr= new Double[size];
        int i2=0;
        for (int i=0;i<size;i++){
            arr[i2] =(Double) vcells.elementAt(i).getInfo();
            sum += (Double) vcells.elementAt(i).getInfo();
            i2 += 1;
        }
        Double mean = sum/arr.length;
        for (Double num:arr){
            std += Math.pow(num-mean,2);
        }
        Double stdd= Math.sqrt(std/ arr.length);

        return stdd;
    }

    public Double calccovar(Vector<Cell> v1, Vector<Cell> v2){
        Double auxsum1=0.0,auxsum2=0.0;
        for(int q=0; q<v1.size(); q++){
            auxsum1 += (Double) v1.elementAt(q).getInfo();
        }
        Double mean1= auxsum1/v1.size();

        for(int r=0; r<v2.size(); r++){
            auxsum2 += (Double) v2.elementAt(r).getInfo();
        }
        Double mean2= auxsum2/ v2.size();

        int i2=0,j2=0;

        Double[] x= new Double[v1.size()];
        Double[] y= new Double[v2.size()];

        for (int z=0; z<v1.size();z++){
            x[i2]= (Double) v1.elementAt(z).getInfo();
            y[j2]= (Double) v2.elementAt(z).getInfo();

            i2 += 1;
            j2 += 1;
        }

        Double sum=0.0;
        for (int w=0;w<x.length;w++){
            sum += (x[w]-mean1) *(y[w]-mean2);
        }
        Double cov= sum/(x.length-1);
        return cov;
    }


}

