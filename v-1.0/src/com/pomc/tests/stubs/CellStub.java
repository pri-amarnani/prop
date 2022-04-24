package com.pomc.tests.stubs;

import com.pomc.classes.*;

import java.time.LocalDate;
import java.util.AbstractMap;
import java.util.Map;
import java.util.Vector;

public class CellStub extends Cell {

    private Object info;
    private String type = "";
    private Map.Entry<String,Vector<Cell>> refInfo;

    /**
     * Creadora
     *
     * @param row
     * @param column
     */
    public CellStub(int row, int column) {
        super(row, column);
        ReferencedCell r= new ReferencedCell(3,3,"=c21+c12");
        ReferencedCell r2= new ReferencedCell(3,2,"=c1+c2");

        Vector<Cell> v = new Vector<>(2);

        NumCell n1 = new NumCell(2,1, 5.0);
        NumCell n2 = new NumCell(1,2, 7.5);

        v.add(n1);
        v.add(n2);

        Map.Entry<String, Vector<Cell>> s = new AbstractMap.SimpleEntry<>("sum", v);
        r.setRefInfo(s);

        this.AddRef(r);
    }

    public CellStub(int row, int column, Object value, String t) {
        super(row, column);
        this.info = value;
        this.type = t;
    }

    public Object getVal() {
        return this.info;
    }

    @Override
    public String getType() {
        return type;
    }

    @Override
    public Object getInfo() {
        return this.info;
    }

    @Override
    public Object changeValue(Object o) {
        if (o.getClass() == Double.class) return changeValueN(o);
        if (o.getClass() == String.class) return changeValueT(o);
        if (o.getClass() == LocalDate.class) return changeValueD(o);

        return null;
    }

    public Object changeValueN(Object o) {
        if (o.getClass()==Double.class) {
            this.info = (Double) o;
            return this;
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
            this.info= null;
            this.setRow(null);
            this.setColumn(null);
            System.gc();
            return d;
        }
        //System.out.println("HHH");
        return null;
    }

    public Object changeValueT(Object o) {
        if (o.getClass()==String.class && !((String) o).startsWith("=")) {
            this.info = (String) o;
            return this;
        }
        else if (o.getClass()==Double.class){
            NumCell n=new NumCell(getRow(),getColumn(), (Double) o);
            this.info= null;
            this.setRow(null);
            this.setColumn(null);
            System.gc();
            return n;
        }
        else if(o.getClass()== LocalDate.class){
            DateCell d=new DateCell(getRow(),getColumn(),(LocalDate)o);
            this.info= null;
            this.setRow(null);
            this.setColumn(null);
            System.gc();
            return d;
        }
        if (hasRefs()) updateRefs();
        return null;
    }

    public Object changeValueD(Object o) {
        if (o.getClass()==LocalDate.class) {
            this.info = (LocalDate) o;
            return this;
        }
        else if (o.getClass()==String.class){
            TextCell t=new TextCell(getRow(),getColumn(), (String) o);
            this.info= null;
            this.setRow(null);
            this.setColumn(null);
            System.gc();
            return t;
        }
        else if(o.getClass()== Double.class){
            NumCell n=new NumCell(getRow(),getColumn(),(Double) o);
            this.info= null;
            this.setRow(null);
            this.setColumn(null);
            System.gc();
            return n;
        }
        return null;
    }



    @Override
    public void setRefInfo(Map.Entry<String, Vector<Cell>> refInfo) {
        this.refInfo = refInfo;
    }
}
