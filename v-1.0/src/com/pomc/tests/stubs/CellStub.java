package com.pomc.tests.stubs;

import com.digidemic.unitof.S;
import com.pomc.classes.*;

import java.time.LocalDate;
import java.time.Month;
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
    }

    public void SinglecellRefDate(String s){
        ReferencedCell r= new ReferencedCell(3,3," ");//la info depende de la op en el test----> func con 2 celdas
        LocalDate l= LocalDate.of(2022, Month.APRIL,24);
        DateCell d= new DateCell(1,1,l);
        Vector<Cell> rv = new Vector<>();
        rv.add(d);
        d.AddRef(r);
        this.AddRef(r);
        Map.Entry<String,Vector<Cell>> rinfo= new AbstractMap.SimpleEntry<>(s,rv);
        r.setRefInfo(rinfo);
        r.setContent(d.getInfo());
    }

    public void SinglecellRefNum(String s){
        ReferencedCell r= new ReferencedCell(3,3," ");//la info depende de la op en el test----> func con 2 celdas
        NumCell n= new NumCell(1,1,1.20);
        Vector<Cell> rv = new Vector<>();
        rv.add(n);
        n.AddRef(r);
        this.AddRef(r);
        Map.Entry<String,Vector<Cell>> rinfo= new AbstractMap.SimpleEntry<>(s,rv);
        r.setRefInfo(rinfo);
        r.setContent(n.getInfo());
    }

    public void TwoCellRef(String s){
        ReferencedCell r= new ReferencedCell(3,3," ");//la info depende de la op en el test----> func con 2 celdas
        LocalDate l= LocalDate.of(2022, Month.APRIL,24);
        NumCell n1= new NumCell(1,1,1.0);
        NumCell n2= new NumCell(2,2,2.0);
        Vector<Cell> rv = new Vector<>();
        rv.add(n1);
        rv.add(n2);
        n1.AddRef(r);
        n2.AddRef(r);
        this.AddRef(r);
        Map.Entry<String,Vector<Cell>> rinfo= new AbstractMap.SimpleEntry<>(s,rv);
        r.setRefInfo(rinfo);
    }

    public void MoreCellRefs(String s){
        ReferencedCell r= new ReferencedCell(3,3," ");//la info depende de la op en el test----> func con 2 celdas
        LocalDate l= LocalDate.of(2022, Month.APRIL,24);
        NumCell n1= new NumCell(1,1,1.0);
        NumCell n2= new NumCell(2,2,2.0);
        NumCell n3= new NumCell(3,3,3.0);
        NumCell n4= new NumCell(4,4,4.0);
        Vector<Cell> rv = new Vector<>();
        rv.add(n1);
        rv.add(n2);
        rv.add(n3);
        rv.add(n4);
        n1.AddRef(r);
        n2.AddRef(r);
        n3.AddRef(r);
        n4.AddRef(r);
        this.AddRef(r);
        Map.Entry<String,Vector<Cell>> rinfo= new AbstractMap.SimpleEntry<>(s,rv);
        r.setRefInfo(rinfo);
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
