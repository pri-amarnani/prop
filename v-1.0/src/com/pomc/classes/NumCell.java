package com.pomc.classes;

import com.digidemic.unitof.UnitOf;

public class NumCell extends Cell {

    //truncament
    //conversio
    //aritmetiques *falta*
    private double info;

    public NumCell(int row,int column, String type, float info){
        super(row,column,type);
        this.info= info;
        super.infon=info;
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

    public double getInfoNum(){
        return info;
    }

    public void changeValueN(double n){
        this.info=n;
        super.infon=n;
    }



}
