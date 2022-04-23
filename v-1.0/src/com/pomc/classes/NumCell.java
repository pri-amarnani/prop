package com.pomc.classes;

import com.digidemic.unitof.UnitOf;

import java.util.Map;
import java.util.Objects;
import java.util.Vector;
import java.time.LocalDate;

public class NumCell extends Cell {

    //truncament
    //conversio
    //aritmetiques *falta*
    private double info;
    private final String type="N";

    public NumCell(int row, int column, Double info){
        super(row,column);
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
        if (Objects.equals(convFrom, "m")) {
            if (Objects.equals(convTo, "cm")) result= length.fromMeters(info).toCentimeters();
            if (Objects.equals(convTo, "km")) result=length.fromMeters(info).toKilometers();
            if (Objects.equals(convTo, "inches")) result=length.fromMeters(info).toInches();
        }

        if (Objects.equals(convFrom, "inches")) result=length.fromInches(info).toMeters();

        if (Objects.equals(convFrom, "km")) {
            if (Objects.equals(convTo, "m")) result=length.fromKilometers(info).toMeters();
            if (Objects.equals(convTo, "cm")) result=length.fromKilometers(info).toCentimeters();
        }

        if (Objects.equals(convFrom, "cm")) {
            if (Objects.equals(convTo, "m")) result=length.fromCentimeters(info).toMeters();
            if (Objects.equals(convTo, "km")) result=length.fromMeters(info).toKilometers();
        }
        return result; //si devuelve -1 falla la conv
    }


    @Override
    public String getType() {
        return type;
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
            new TextCell(getRow(),getColumn(), (String) o);
        }
        else if(o.getClass()== LocalDate.class){
            info= Double.parseDouble(null);
            new DateCell(getRow(),getColumn(),(LocalDate)o);
        }

        if (hasRefs()) updateRefs();
    }

    @Override
    public void setRefInfo(Map.Entry<String, Vector<Cell>> refInfo) {

    }


}
