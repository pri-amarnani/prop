package com.pomc.classes;

import java.util.Date;
import java.util.Calendar;

public abstract class DateCell extends Cell {
    private Date info;

    public DateCell (int row,int column, String type, Date info){
        super(row,column,type);
        this.info= info;
    }

    public Date getInfoDate(){
        return info;
    }

    public void changeValueD(Date d){
        this.info=d;
    }

    public String getDayofTheWeek(){
        Calendar c= Calendar.getInstance();
        c.setTime(info);
        int day= c.get(Calendar.DAY_OF_WEEK);
        if(day==1) return "Monday";
        else if (day==2) return "Tuesday";
        else if (day==3) return "Wednesday";
        else if (day==4) return "Thursday";
        else if (day==5) return "Friday";
        else if (day==6) return "Saturday";
        else if (day==7) return "Sunday";
    }

}
