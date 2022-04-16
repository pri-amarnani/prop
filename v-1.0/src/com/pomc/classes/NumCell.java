package com.pomc.classes;

import com.digidemic.unitof.UnitOf;
import java.util.Vector;
import java.time.LocalDate;

public class NumCell extends Cell {

    //truncament
    //conversio
    //aritmetiques *falta*
    private double info;
    Cell[] refs;

    public NumCell(int row, int column, String type, Double info){
        super(row,column,type);
        this.info= info;
    }

    public double truncar(){
        return Math.floor(info*10)/100;
    }

    //metros kilometros centimetros
    //metro a inch
    public double conversion(String convFrom, String convTo) {
        UnitOf.Length length = new UnitOf.Length();
        double result=-1;
        if (convFrom == "m") {
            if (convTo == "cm") result= length.fromMeters(info).toCentimeters();
            if (convTo == "km") result=length.fromMeters(info).toKilometers();
            if (convTo == "inches") result=length.fromMeters(info).toInches();
        }

        if (convFrom == "inches") result=length.fromInches(info).toMeters();

        if (convFrom == "km") {
            if (convTo == "m") result=length.fromKilometers(info).toMeters();
            if (convTo == "cm") result=length.fromKilometers(info).toCentimeters();
        }

        if (convFrom == "cm") {
            if (convTo == "m") result=length.fromCentimeters(info).toMeters();
            if (convTo == "km") result=length.fromMeters(info).toKilometers();
        }
        return result; //si devuelve -1 falla la conv
    }


    @Override
    public Object getInfo() {
        return info;
    }

    @Override
    public void changeValue(Object o) {
        if (o.getClass()==Double.class) {
            this.info = (double) o;
        }
        else if (o.getClass()==String.class){
            info= Double.parseDouble(null);
            new TextCell(getRow(),getColumn(),"T", (String) o);
        }
        else if(o.getClass()== LocalDate.class){
            info= Double.parseDouble(null);
            new DateCell(getRow(),getColumn(),"D",(LocalDate)o);
        }

        if (hasRefs()){
            Vector<Cell> v= getRefs();
            for (int i=0; i<v.size();i++){
                Cell c= v.elementAt(i);
                String s= c.getRefInfo();//falta func para interpretar este String
                //Volver a hacer esa operación con los nuevos valores
            }
        }
    }


}
