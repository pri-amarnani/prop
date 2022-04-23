package com.pomc.classes;


import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Map;
import java.util.Objects;
import java.util.Vector;

public class DateCell extends Cell {
    private LocalDate info;
    private final String type="D";

    public DateCell (int row,int column, LocalDate info){
        super(row,column);
        this.info= info;
    }

    public int extract(String criteria){ //tendría que devolver localdate???????
        if (Objects.equals(criteria, "DAY")) return (info.getDayOfMonth());
        else if (Objects.equals(criteria, "MONTH")) return info.getMonthValue();
        else if (Objects.equals(criteria, "YEAR")) return info.getYear();
        return -1;
    }

    public String getDayofTheWeek(){
        DayOfWeek d= info.getDayOfWeek();
        int day= d.getValue();
        if(day==1) return "Monday";
        else if (day==2) return "Tuesday";
        else if (day==3) return "Wednesday";
        else if (day==4) return "Thursday";
        else if (day==5) return "Friday";
        else if (day==6) return "Saturday";
        else if (day==7) return "Sunday";
        return "-";
    }

    @Override
    public String getType() {
        return null;
    }

    @Override
    public Object getInfo() {
        return info;
    }

    @Override
    public void changeValue(Object o) {
        if (o.getClass()==LocalDate.class) {
            this.info = (LocalDate) o;
        }
        else if (o.getClass()==String.class){
            info= null;
            new TextCell(getRow(),getColumn(), (String) o);
        }
        else if(o.getClass()== Double.class){
            info= null;
            new NumCell(getRow(),getColumn(),(Double) o);
        }
        if (hasRefs()) updateRefs();

    }

    @Override
    public void setRefInfo(Map.Entry<String, Vector<Cell>> refInfo) {

    }
}
