package com.pomc.classes;

import java.time.LocalDate;
import java.util.Objects;
import java.util.Vector;

public class TextCell extends Cell {

    private String info;


    public TextCell (int row,int column, String type, String info){
        super(row,column,type);
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
    public Object getInfo() {
        return info;
    }

    @Override
    public void changeValue(Object o) {
        if (o.getClass()==String.class) {
            this.info = (String) o;
        }
        else if (o.getClass()==Double.class){
            info= null;
            new NumCell(getRow(),getColumn(),"N", (Double) o);
        }
        else if(o.getClass()== LocalDate.class){
            info= null;
            new DateCell(getRow(),getColumn(),"D",(LocalDate)o);
        }

        if (hasRefs()){
            Vector<Cell> v= getRefs();
            for (int i=0; i<v.size();i++){
                Cell c= v.elementAt(i);
                String s= c.getRefInfo();//falta func per interpretar aquest String
                //Tornar a fer l'operació amb el nou valor
            }
        }
    }

}
