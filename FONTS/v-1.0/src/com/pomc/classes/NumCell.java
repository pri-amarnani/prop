package com.pomc.classes;

import com.digidemic.unitof.UnitOf;

import java.util.Map;
import java.util.Objects;
import java.util.Vector;
import java.time.LocalDate;

public class NumCell extends Cell {

    private Double info;
    private final String type="N";

    public NumCell(int row, int column, Double info){
        super(row,column);
        this.info= info;
    }



    /**
     * Convierte el valor de la celda de una unidad métrica a otra
     * @param convFrom
     * @param convTo
     * @return devuelve el valor modificado
     */
    public double conversion(String convFrom, String convTo) {
        UnitOf.Length length = new UnitOf.Length();
        if (Objects.equals(convFrom, "m")) {
            if (Objects.equals(convTo, "cm")) return length.fromMeters(info).toCentimeters();
            if (Objects.equals(convTo, "km")) return length.fromMeters(info).toKilometers();
            if (Objects.equals(convTo, "inches")) return length.fromMeters(info).toInches();
        }

        if (Objects.equals(convFrom, "inches")) return length.fromInches(info).toMeters();

        if (Objects.equals(convFrom, "km")) {
            if (Objects.equals(convTo, "m")) return length.fromKilometers(info).toMeters();
            if (Objects.equals(convTo, "cm"))return length.fromKilometers(info).toCentimeters();
        }

        if (Objects.equals(convFrom, "cm")) {
            if (Objects.equals(convTo, "m")) return length.fromCentimeters(info).toMeters();
            if (Objects.equals(convTo, "km")) return length.fromMeters(info).toKilometers();
        }
        return -1; //si devuelve -1 falla la conv
    }


    @Override
    public String getType() {
        return type;
    }

    @Override
    public Object getInfo() {
        return info;
    }

    /**
     * Cambia el valor de una celda por el indicado (o)
     * @param o
     * @return devuelve la celda modificada
     */
    @Override
    public Object changeValue(Object o) {
        if (o.getClass()==Double.class) {
            this.info = (Double) o;
        }
        else if (o.getClass()==String.class){
            TextCell t=new TextCell(getRow(),getColumn(), (String) o);
            if (hasRefs()){
                Vector<ReferencedCell> myrefs=this.getRefs();
                for (int i=0;i<myrefs.size();i++){
                    ReferencedCell r=myrefs.elementAt(i);
                    r.setRCell(t);
                    t.AddRef(r);
                    r.setContent(o);
                }

            }
            this.info= null;
            this.setRow(null);
            this.setColumn(null);
            System.gc();
            return t;
        }
        else if(o.getClass()== LocalDate.class){
            DateCell d=new DateCell(getRow(),getColumn(),(LocalDate)o);
            if (hasRefs()){
                Vector<ReferencedCell> myrefs=this.getRefs();
                for (int i=0;i<myrefs.size();i++){
                    ReferencedCell r=myrefs.elementAt(i);
                    r.setRCell(d);
                    d.AddRef(r);
                    r.setContent(o);
                }

            }
            this.info=null;
            this.setRow(null);
            this.setColumn(null);
            System.gc();
            return d;
        }

        if (hasRefs()) { System.out.println("HOLA");updateRefs();}
        return this;
    }

    @Override
    public void setRefInfo(Map.Entry<String, Vector<Cell>> refInfo) {

    }

    @Override
    public Object getContent() {
        return null;
    }

    @Override
    public void setContent(Object o) {

    }


}
