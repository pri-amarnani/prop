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

    public void replace(String criteria){ //return String??
        if (Objects.equals(criteria, "majTomin")) {
            info= info.toUpperCase();
        }
        else if (Objects.equals(criteria, "minTomaj")){
            info=info.toLowerCase();
        }
        else if (Objects.equals(criteria, "firstMaj")){
            String aux;
            aux=info.substring(0,1).toUpperCase()+info.substring(1);
            info=aux;
        }
        //not cientifica a num??
    }

    public int length(String criteria){
        if  (Objects.equals(criteria, "words")){ //falta comprovar!!!!!!
            String [] aux;
            aux= info.split("\\s+");
            return aux.length;
        }
        else if (Objects.equals(criteria, "letters")){
            int count=0;
            for (int i=0;i<info.length();i++){
                if(info.charAt(i)!=' ') count++;
            }
            return count;
        }
        else if (Objects.equals(criteria, "characters")){
            return info.length();
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

    @Override
    public void changeValue(Object o) {
        if (o.getClass()==String.class && !((String) o).startsWith("=")) {
            this.info = (String) o;
        }
        else if (o.getClass()==Double.class){
            info= null;
            new NumCell(getRow(),getColumn(), (Double) o);
        }
        else if(o.getClass()== LocalDate.class){
            info= null;
            new DateCell(getRow(),getColumn(),(LocalDate)o);
        }

        if (hasRefs()) updateRefs();
    }

    @Override
    public void setRefInfo(Map.Entry<String, Vector<Cell>> refInfo) {

    }

}
