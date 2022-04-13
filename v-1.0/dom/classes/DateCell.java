package classes;

import classes.Cell;

import java.util.Date;

public class DateCell extends Cell {
    private Date info;

    public DateCell (int row,int column, String type, Date info){
        super(row,column,type);
        this.info= info;
    }

}
