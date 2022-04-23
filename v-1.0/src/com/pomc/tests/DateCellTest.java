package com.pomc.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import com.pomc.classes.DateCell;
import com.pomc.classes.NumCell;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.Month;

public class DateCellTest {
    private DateCell dcell;
    @BeforeEach
    void setUp(){
        LocalDate l= LocalDate.of(2001, Month.FEBRUARY,16);
        dcell= new DateCell(7,9,l);
    }

    @Test
    @DisplayName("extract should work")
    public void test_extract(){
        assertEquals( dcell.extract("DAY"),16);
    }

    @Test
    @DisplayName("getDayofTheWeek should work")
    public void test_getDayfTheWeek(){
        assertEquals( dcell.getDayofTheWeek(),"Friday");
    }

    @Test
    @DisplayName("getType should work")
    public void test_getType(){
        assertEquals( dcell.getType(),"D");
    }
    @Test
    @DisplayName("getInfo should work")
    public void test_getInfo(){
        LocalDate l2= LocalDate.of(2001, Month.FEBRUARY,16);
        assertEquals( dcell.getInfo(),l2);
    }

    @Test
    @DisplayName("changeValue should work")
    public void test_changeValue(){
        LocalDate l2= LocalDate.of(2001, Month.FEBRUARY,25);
        dcell.changeValue(l2);
        assertEquals( dcell.getInfo(),l2);
    }


}
