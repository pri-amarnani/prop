package com.pomc.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import com.pomc.classes.TextCell;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class TextCellTest {
    private TextCell tcell;

    @BeforeEach
    void setUp(){ tcell= new TextCell(2,2,"test of subclass TextCell");}

    @Test
    @DisplayName("replace should work")
    public void test_replace(){
        tcell.replace("firstMaj");
        assertEquals( tcell.getInfo(),"Test of subclass TextCell");
    }

    @Test
    @DisplayName("length should work")
    public void test_length(){
        assertEquals( tcell.length("words"),4);
    }


    @Test
    @DisplayName("getType should work")
    public void test_getType(){
        assertEquals( tcell.getType(),"T");
    }

    @Test
    @DisplayName("getInfo should work")
    public void test_getInfo(){
        assertEquals( tcell.getInfo(),"test of subclass TextCell");
    }

    @Test
    @DisplayName("changeValue should work")
    public void test_changeValue(){
        tcell.changeValue("hola");
        assertEquals( tcell.getInfo(),"hola");
    }

}
