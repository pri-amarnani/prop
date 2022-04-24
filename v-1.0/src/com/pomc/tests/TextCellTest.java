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
    @DisplayName("replaceCFL should work")
    public void test_replaceCFL(){
        tcell.replace("cap_first_letter");
        assertEquals( tcell.getInfo(),"Test of subclass TextCell");
    }
    @Test
    @DisplayName("replaceAC should work")
    public void test_replaceAL(){
        tcell.replace("all caps");
        assertEquals( tcell.getInfo(),"TEST OF SUBCLASS TEXTCELL");
    }
    @Test
    @DisplayName("replaceANC should work")
    public void test_replaceANC(){
        tcell.replace("all not caps");
        assertEquals( tcell.getInfo(),"test of subclass textcell");
    }

    @Test
    @DisplayName("length1 should work")
    public void test_length1(){
        assertEquals( tcell.length("words"),4);
    }

    @Test
    @DisplayName("length2 should work")
    public void test_length2(){
        assertEquals( tcell.length("letters"),22);
    }

    @Test
    @DisplayName("length3 should work")
    public void test_length3(){
        assertEquals( tcell.length("characters"),25);
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
