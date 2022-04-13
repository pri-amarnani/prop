package classes;

import java.util.Objects;

public class Cell {
    private final int row; //que es final??
    private final int column;
    private final String type;


    public Cell(int row,int column,String type){
        this.row=row;
        this.column=column;
        this.type=type;
    }

    /**
     * Consultora de la fila
     * @return fila de la celda
     */

    public int getRow(){
        return row;
    }

    /**
     * Consultora de la columna
     * @return columna de la celda
     */

    public int getColumn(){
        return column;
    }

    public boolean isNum(){
        return  (Objects.equals(type, "N")); //??????????????
    }

    public boolean isText(){
        return (Objects.equals(type, "T"));
    }

    public boolean isDate(){
        return (Objects.equals(type, "D"));
    }
}

