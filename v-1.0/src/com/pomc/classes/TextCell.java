package com.pomc.classes;

import java.util.Objects;

public class TextCell extends Cell {

    private String info;

    public TextCell (int row,int column, String type, String info){
        super(row,column,type);
        this.info= info;
        super.infot=info;
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

    public void changeValueT(String t){

        this.info=t;
        super.infot=t;
    }
}
