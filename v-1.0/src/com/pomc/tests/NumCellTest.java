package com.pomc.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import com.pomc.classes.NumCell;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class NumCellTest {

    private NumCell ncell;

    @BeforeEach
    void setUp(){
        ncell= new NumCell(1,1,2.5432);
    }


    @Test
    @DisplayName("truncar should work")
    public void test_truncar(){
        assertEquals( ncell.truncar(),2.0);
    }

    @Test
    @DisplayName("conversion should work")
    public void test_conversion(){
        assertEquals(ncell.conversion("cm","m"),0.025432);
    }

    @Test
    @DisplayName("type getter should work")
    public void test_getType(){
        assertEquals(ncell.getType(),"N");
    }

    @Test
    @DisplayName("info getter should work")
    public void test_getInfo(){
        assertEquals(ncell.getInfo(),2.5432);
    }

    @Test
    @DisplayName("changeValue should work")
    public void test_changeValue(){
        Object a=ncell.changeValue(1.25);
        assertEquals(ncell.getInfo(),1.25); //se hace así???
    }


}
