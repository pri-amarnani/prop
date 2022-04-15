package com.pomc.classes;


import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Calendar;

public class DateCell extends Cell {
    private LocalDate info;

    public DateCell (int row,int column, String type, LocalDate info){
        super(row,column,type);
        this.info= info;
        super.infod=info;
    }

    public LocalDate getInfoDate(){
        return info;
    }

    public void changeValueD(LocalDate d){
        this.info=d;
        super.infod=d;
    }

    public int extract(String criteria){ //tendría que devolver localdate???????
        if (criteria=="DAY") return info.getDayOfMonth();
        else if (criteria=="MONTH") return info.getMonthValue();
        else if (criteria=="YEAR") return info.getYear();
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

}
