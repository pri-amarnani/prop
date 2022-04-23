package com.pomc.tests;

import com.pomc.classes.ReferencedCell;
import com.pomc.tests.stubs.CellStub;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Vector;

import static org.junit.jupiter.api.Assertions.*;


public class CellStubTest {

    private CellStub cell;

    @BeforeEach
    void setUp(){
        cell= new CellStub(5,2);
    }


    @Test
    @DisplayName("getRow should work")
    public void testgetRow(){
        assertEquals(cell.getRow(),5);
    }

    @Test
    @DisplayName("getColumn should work")
    public void testgetColumn(){
        assertEquals(cell.getColumn(),2);
    }

    @Test
    @DisplayName("getRefs should work")
    public void testgetRefs(){
        Vector<ReferencedCell> refstest=cell.getRefs();
        assertEquals(refstest.size(),2); //estaria bien así?
    }

    @Test
    @DisplayName("setColumn should work")
    public void testsetColumn(){
        cell.setColumn(11);
        assertEquals(cell.getColumn(),11);
    }

    @Test
    @DisplayName("setRow should work")
    public void testsetRow(){
        cell.setRow(20);
        assertEquals(cell.getRow(),20);
    }

    @Test
    @DisplayName("isNum should work")
    public void testisNum(){
        assertFalse(cell.isNum());
    }

    @Test
    @DisplayName("isText should work")
    public void testisText(){
        assertFalse(cell.isText());
    }

    @Test
    @DisplayName("isDate should work")
    public void testisDate(){
        assertFalse(cell.isDate());
    }

    @Test
    @DisplayName("addRef should work")
    public void testAddRef(){
        ReferencedCell r3= new ReferencedCell(3,4,"=c1+c4");
        cell.AddRef(r3);
        Vector<ReferencedCell> refstest= cell.getRefs();
        assertEquals(refstest.size(),3);
    }

    @Test
    @DisplayName("EliminateRef should work")
    public void testEliminateRef(){ //?????????
        Vector<ReferencedCell> refstest= cell.getRefs();
        ReferencedCell rtest= refstest.elementAt(0);
        cell.EliminateRef(rtest);
        Vector<ReferencedCell> refstest2= cell.getRefs();
        assertEquals(refstest2.size(),1);
    }

    @Test
    @DisplayName("hasRefs should work")
    public void testHasRefs(){
        assertTrue(cell.hasRefs());
    }

    //updateRefs?????


}