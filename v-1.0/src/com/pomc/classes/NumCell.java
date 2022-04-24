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
    private Double info;
    private final String type="N";

    public NumCell(int row, int column, Double info){
        super(row,column);
        this.info= info;
    }

    public double truncar(){
        this.info= Math.floor(info);
        return info;
    }

    //metros kilometros centimetros
    //metro a inch
    public double conversion(String convFrom, String convTo) {
        UnitOf.Length length = new UnitOf.Length();
        if (Objects.equals(convFrom, "m")) {
            if (Objects.equals(convTo, "cm")) this.info= length.fromMeters(info).toCentimeters();
            if (Objects.equals(convTo, "km")) this.info=length.fromMeters(info).toKilometers();
            if (Objects.equals(convTo, "inches")) this.info=length.fromMeters(info).toInches();
        }

        if (Objects.equals(convFrom, "inches")) this.info=length.fromInches(info).toMeters();

        if (Objects.equals(convFrom, "km")) {
            if (Objects.equals(convTo, "m")) this.info=length.fromKilometers(info).toMeters();
            if (Objects.equals(convTo, "cm")) this.info=length.fromKilometers(info).toCentimeters();
        }

        if (Objects.equals(convFrom, "cm")) {
            if (Objects.equals(convTo, "m")) this.info=length.fromCentimeters(info).toMeters();
            if (Objects.equals(convTo, "km")) this.info=length.fromMeters(info).toKilometers();
        }
        return info; //si devuelve -1 falla la conv
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
    public Object changeValue(Object o) {
        if (o.getClass()==Double.class) {
            this.info = (Double) o;
        }
        else if (o.getClass()==String.class){
            TextCell t=new TextCell(getRow(),getColumn(), (String) o);
            this.info= null;
            this.setRow(null);
            this.setColumn(null);
            System.gc();
            return t;
        }
        else if(o.getClass()== LocalDate.class){
            DateCell d=new DateCell(getRow(),getColumn(),(LocalDate)o);
            this.info=null;
            this.setRow(null);
            this.setColumn(null);
            System.gc();
            return d;
        }

        if (hasRefs()) updateRefs();
        return this;
    }

    @Override
    public void setRefInfo(Map.Entry<String, Vector<Cell>> refInfo) {

    }


}
