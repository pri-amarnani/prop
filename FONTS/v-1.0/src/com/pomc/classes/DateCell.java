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

    public Double extract(String criteria){ //tendría que devolver localdate???????
        if (Objects.equals(criteria, "Day")) return Double.valueOf((info.getDayOfMonth()));
        else if (Objects.equals(criteria, "Month")) return Double.valueOf(info.getMonthValue());
        else if (Objects.equals(criteria, "Year")) return Double.valueOf(info.getYear());
        return Double.valueOf(-1);
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
        return type;
    }

    @Override
    public Object getInfo() {
        return info;
    }

    @Override
    public Object changeValue(Object o) {
        if (o.getClass()==LocalDate.class) {
            this.info = (LocalDate) o;
        }
        else if (o.getClass()==String.class){
            TextCell t=new TextCell(getRow(),getColumn(), (String) o);
            if (hasRefs()){
                Vector<ReferencedCell> myrefs=this.getRefs();
                for (int i=0;i<myrefs.size();i++){
                    ReferencedCell r=myrefs.elementAt(i);
                    r.setRCell(t);
                    t.AddRef(r);
                    r.setContent(o);
                }

            }
            if (hasRefs())
            this.info= null;
            this.setRow(null);
            this.setColumn(null);
            System.gc();
            return t;
        }
        else if(o.getClass()== Double.class){
            NumCell n=new NumCell(getRow(),getColumn(),(Double) o);
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
