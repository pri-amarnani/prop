package com.pomc.classes;

import java.time.LocalDate;
import java.util.Map;
import java.util.Objects;
import java.util.Vector;

public class TextCell extends Cell {

    private String info;
    private final String type="T";


    public TextCell (int row,int column, String info){
        super(row,column);
        this.info= info;
    }

    /**
     * Calcula el tamaño en palabras/letras/caracteres de una celda
     * @param criteria
     * @return devuelve el tamaño
     */

    public double length(String criteria){
        if  (Objects.equals(criteria, "Words")){ //falta comprovar!!!!!!
            String [] aux;
            aux= info.split("\\s+");
            return ((double) aux.length);
        }
        else if (Objects.equals(criteria, "Letters")){
            int count=0;
            for (int i=0;i<info.length();i++){
                if(info.charAt(i)!=' ') count++;
            }
            return (double) count;
        }
        else if (Objects.equals(criteria, "Characters")){
            return (double) info.length();
        }
        return -1;

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
        if (o.getClass()==String.class && !((String) o).startsWith("=")) {
            this.info = (String) o;
        }
        else if (o.getClass()==Double.class){
            NumCell n=new NumCell(getRow(),getColumn(), (Double) o);
            if (hasRefs()){
                Vector<ReferencedCell> myrefs=this.getRefs();
                for (int i=0;i<myrefs.size();i++){
                    ReferencedCell r=myrefs.elementAt(i);
                    r.setRCell(n);
                    n.AddRef(r);
                    r.setContent(o);
                }

            }
            this.info= null;
            this.setRow(null);
            this.setColumn(null);
            System.gc();
            return n;
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
            this.info= null;
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

    @Override
    public Object getContent() {
        return null;
    }

    @Override
    public void setContent(Object o) {

    }

}
