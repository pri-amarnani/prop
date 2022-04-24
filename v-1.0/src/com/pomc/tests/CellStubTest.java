package com.pomc.tests;

import com.pomc.classes.Cell;
import com.pomc.classes.ReferencedCell;
import com.pomc.tests.stubs.CellStub;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.Month;
import java.util.Map;
import java.util.Vector;

import static org.junit.jupiter.api.Assertions.*;


public class CellStubTest {

    private CellStub cell;

    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = BigDecimal.valueOf(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

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
        cell.SinglecellRefDate("day");
        Vector<ReferencedCell> refstest=cell.getRefs();
        assertEquals(refstest.size(),1); //estaria bien así?
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
        cell.SinglecellRefNum("sub");
        ReferencedCell r3= new ReferencedCell(3,4,"=c1+c4");
        cell.AddRef(r3);
        Vector<ReferencedCell> refstest= cell.getRefs();
        assertEquals(refstest.size(),2);
    }

    @Test
    @DisplayName("EliminateRef should work")
    public void testEliminateRef(){ //?????????
        cell.SinglecellRefNum("mult");
        Vector<ReferencedCell> refstest= cell.getRefs();
        ReferencedCell rtest= refstest.elementAt(0);
        cell.EliminateRef(rtest);
        assertEquals(refstest.size(),0);
    }

    @Test
    @DisplayName("hasRefs should work")
    public void testHasRefs(){
        cell.SinglecellRefNum("sum");
        assertTrue(cell.hasRefs());
    }


    @Test
    @DisplayName("updateRefsSum should work")
    public void testUpdateRefssum(){
        cell.TwoCellRef("sum");

        Vector<ReferencedCell> vrefest= cell.getRefs();
        ReferencedCell rtest= vrefest.elementAt(0);

        Map.Entry<String, Vector<Cell>> testmap= rtest.getRefInfo();
        Vector<Cell> vcelltest= testmap.getValue();

        Cell ctest= vcelltest.elementAt(0);
        Cell ctest2= vcelltest.elementAt(1);
        Double oper= (Double)ctest.getInfo()+ (Double) ctest2.getInfo();
        rtest.setContent(oper);
        Object o2= ctest.changeValue(10.00);
        assertEquals(rtest.getContent(),12.0);
    }

    @Test
    @DisplayName("updateRefsSub should work")
    public void testUpdateRefssub(){
        cell.TwoCellRef("sub");

        Vector<ReferencedCell> vrefest= cell.getRefs();
        ReferencedCell rtest= vrefest.elementAt(0);

        Map.Entry<String, Vector<Cell>> testmap= rtest.getRefInfo();
        Vector<Cell> vcelltest= testmap.getValue();

        Cell ctest= vcelltest.elementAt(0);
        Cell ctest2= vcelltest.elementAt(1);
        Double oper= (Double)ctest.getInfo()- (Double) ctest2.getInfo();
        rtest.setContent(oper);
        Object o2= ctest.changeValue(10.00);
        assertEquals(rtest.getContent(),8.0);
    }

    @Test
    @DisplayName("updateRefsMult should work")
    public void testUpdateRefsmult(){
        cell.TwoCellRef("mult");

        Vector<ReferencedCell> vrefest= cell.getRefs();
        ReferencedCell rtest= vrefest.elementAt(0);

        Map.Entry<String, Vector<Cell>> testmap= rtest.getRefInfo();
        Vector<Cell> vcelltest= testmap.getValue();

        Cell ctest= vcelltest.elementAt(0);
        Cell ctest2= vcelltest.elementAt(1);
        Double oper= (Double)ctest.getInfo()* (Double) ctest2.getInfo();
        rtest.setContent(oper);
        Object o2= ctest.changeValue(10.00);
        assertEquals(rtest.getContent(),20.0);
    }

    @Test
    @DisplayName("updateRefsDiv should work")
    public void testUpdateRefsdiv(){
        cell.TwoCellRef("div");

        Vector<ReferencedCell> vrefest= cell.getRefs();
        ReferencedCell rtest= vrefest.elementAt(0);

        Map.Entry<String, Vector<Cell>> testmap= rtest.getRefInfo();
        Vector<Cell> vcelltest= testmap.getValue();

        Cell ctest= vcelltest.elementAt(0);
        Cell ctest2= vcelltest.elementAt(1);
        Double oper= (Double)ctest.getInfo()/ (Double) ctest2.getInfo();
        rtest.setContent(oper);
        Object o2= ctest.changeValue(10.00);
        assertEquals(rtest.getContent(),5.0);
    }

    @Test
    @DisplayName("updateRefsfloor should work")
    public void testUpdateRefsfloor(){
        cell.SinglecellRefNum("floor");

        Vector<ReferencedCell> vrefest= cell.getRefs();
        ReferencedCell rtest= vrefest.elementAt(0);

        Map.Entry<String, Vector<Cell>> testmap= rtest.getRefInfo();
        Vector<Cell> vcelltest= testmap.getValue();

        Cell ctest= vcelltest.elementAt(0);
        Object o2= ctest.changeValue(10.5432);

        assertEquals(rtest.getContent(),10.0);
    }

    @Test
    @DisplayName("updateRefsmTocm should work")
    public void testUpdateRefsmTocm(){
        cell.SinglecellRefNum("mTOcm");

        Vector<ReferencedCell> vrefest= cell.getRefs();
        ReferencedCell rtest= vrefest.elementAt(0);

        Map.Entry<String, Vector<Cell>> testmap= rtest.getRefInfo();
        Vector<Cell> vcelltest= testmap.getValue();

        Cell ctest= vcelltest.elementAt(0);
        Object o2= ctest.changeValue(12.2);
        assertEquals(rtest.getContent(),1220.0);
    }
    @Test
    @DisplayName("updateRefsmTokm should work")
    public void testUpdateRefsmTokm(){
        cell.SinglecellRefNum("mTOkm");

        Vector<ReferencedCell> vrefest= cell.getRefs();
        ReferencedCell rtest= vrefest.elementAt(0);

        Map.Entry<String, Vector<Cell>> testmap= rtest.getRefInfo();
        Vector<Cell> vcelltest= testmap.getValue();

        Cell ctest= vcelltest.elementAt(0);
        Object o2= ctest.changeValue(12.2);
        assertEquals(round((double) rtest.getContent(),4),0.0122);
    }

    @Test
    @DisplayName("updateRefsmToinches should work")
    public void testUpdateRefsmToinches(){
        cell.SinglecellRefNum("mTOinches");

        Vector<ReferencedCell> vrefest= cell.getRefs();
        ReferencedCell rtest= vrefest.elementAt(0);

        Map.Entry<String, Vector<Cell>> testmap= rtest.getRefInfo();
        Vector<Cell> vcelltest= testmap.getValue();

        Cell ctest= vcelltest.elementAt(0);

        Object o2= ctest.changeValue(12.2);

        assertEquals(round((double) rtest.getContent(),2),480.31);
    }

    @Test
    @DisplayName("updateRefsmTokm should work")
    public void testUpdateRefsinchesTom(){
        cell.SinglecellRefNum("inchesTOm");

        Vector<ReferencedCell> vrefest= cell.getRefs();
        ReferencedCell rtest= vrefest.elementAt(0);

        Map.Entry<String, Vector<Cell>> testmap= rtest.getRefInfo();
        Vector<Cell> vcelltest= testmap.getValue();

        Cell ctest= vcelltest.elementAt(0);
        Object o2= ctest.changeValue(12.2);
        assertEquals(round((double) rtest.getContent(),5),0.30988);
    }

    @Test
    @DisplayName("updateRefscmTom should work")
    public void testUpdateRefscmTom(){
        cell.SinglecellRefNum("cmTOm");

        Vector<ReferencedCell> vrefest= cell.getRefs();
        ReferencedCell rtest= vrefest.elementAt(0);

        Map.Entry<String, Vector<Cell>> testmap= rtest.getRefInfo();
        Vector<Cell> vcelltest= testmap.getValue();

        Cell ctest= vcelltest.elementAt(0);
        Object o2= ctest.changeValue(12.2);
        assertEquals(round((double) rtest.getContent(),3),0.122);
    }

    @Test
    @DisplayName("updateRefscmTokm should work")
    public void testUpdateRefscmTokm(){
        cell.SinglecellRefNum("cmTOkm");

        Vector<ReferencedCell> vrefest= cell.getRefs();
        ReferencedCell rtest= vrefest.elementAt(0);

        Map.Entry<String, Vector<Cell>> testmap= rtest.getRefInfo();
        Vector<Cell> vcelltest= testmap.getValue();

        Cell ctest= vcelltest.elementAt(0);
        Object o2= ctest.changeValue(12.2);
        assertEquals(round((double) rtest.getContent(),6),0.000122);
    }

    @Test
    @DisplayName("updateRefskmTocm should work")
    public void testUpdateRefskmTocm(){
        cell.SinglecellRefNum("kmTOcm");

        Vector<ReferencedCell> vrefest= cell.getRefs();
        ReferencedCell rtest= vrefest.elementAt(0);

        Map.Entry<String, Vector<Cell>> testmap= rtest.getRefInfo();
        Vector<Cell> vcelltest= testmap.getValue();

        Cell ctest= vcelltest.elementAt(0);
        Object o2= ctest.changeValue(12.2);
        assertEquals(round((double) rtest.getContent(),1),1220000);
    }

    @Test
    @DisplayName("updateRefskmTom should work")
    public void testUpdateRefskmTom(){
        cell.SinglecellRefNum("kmTOm");

        Vector<ReferencedCell> vrefest= cell.getRefs();
        ReferencedCell rtest= vrefest.elementAt(0);

        Map.Entry<String, Vector<Cell>> testmap= rtest.getRefInfo();
        Vector<Cell> vcelltest= testmap.getValue();

        Cell ctest= vcelltest.elementAt(0);
        Object o2= ctest.changeValue(12.2);
        assertEquals(round((double) rtest.getContent(),1),12200);
    }


    @Test
    @DisplayName("updateRefsmean should work")
    public void testUpdateRefsmean(){
        cell.MoreCellRefs("mean");

        Vector<ReferencedCell> vrefest= cell.getRefs();
        ReferencedCell rtest= vrefest.elementAt(0);

        Map.Entry<String, Vector<Cell>> testmap= rtest.getRefInfo();
        Vector<Cell> vcelltest= testmap.getValue();

        Cell ctest= vcelltest.elementAt(0);

        Object o2= ctest.changeValue(12.2);

        assertEquals(rtest.getContent(),5.3);
    }

    @Test
    @DisplayName("updateRefsmean should work")
    public void testUpdateRefsmedian(){
        cell.MoreCellRefs("median");

        Vector<ReferencedCell> vrefest= cell.getRefs();
        ReferencedCell rtest= vrefest.elementAt(0);

        Map.Entry<String, Vector<Cell>> testmap= rtest.getRefInfo();
        Vector<Cell> vcelltest= testmap.getValue();

        Cell ctest= vcelltest.elementAt(0);

        Object o2= ctest.changeValue(12.2);

        assertEquals(rtest.getContent(),4.0);
    }

    @Test
    @DisplayName("updateRefsmean should work")
    public void testUpdateRefsvar(){
        cell.MoreCellRefs("var");

        Vector<ReferencedCell> vrefest= cell.getRefs();
        ReferencedCell rtest= vrefest.elementAt(0);

        Map.Entry<String, Vector<Cell>> testmap= rtest.getRefInfo();
        Vector<Cell> vcelltest= testmap.getValue();

        Cell ctest= vcelltest.elementAt(0);

        Object o2= ctest.changeValue(12.2);

        assertEquals(rtest.getContent(),16.369999999999994);
    }

    @Test
    @DisplayName("updateRefsCovar should work")
    public void testUpdateRefscovar(){
        cell.MoreCellRefs("covar");

        Vector<ReferencedCell> vrefest= cell.getRefs();
        ReferencedCell rtest= vrefest.elementAt(0);

        Map.Entry<String, Vector<Cell>> testmap= rtest.getRefInfo();
        Vector<Cell> vcelltest= testmap.getValue();

        Cell ctest= vcelltest.elementAt(0);

        Object o2= ctest.changeValue(12.2);

        assertEquals(rtest.getContent(),-5.1);
    }

    @Test
    @DisplayName("updateRefsStd should work")
    public void testUpdateRefsStd(){
        cell.MoreCellRefs("std");

        Vector<ReferencedCell> vrefest= cell.getRefs();
        ReferencedCell rtest= vrefest.elementAt(0);

        Map.Entry<String, Vector<Cell>> testmap= rtest.getRefInfo();
        Vector<Cell> vcelltest= testmap.getValue();

        Cell ctest= vcelltest.elementAt(0);

        Object o2= ctest.changeValue(12.2);
        assertEquals(rtest.getContent(),4.045985664828781);
    }

    @Test
    @DisplayName("updateRefsStd should work")
    public void testUpdateRefsCP(){
        cell.MoreCellRefs("cp");

        Vector<ReferencedCell> vrefest= cell.getRefs();
        ReferencedCell rtest= vrefest.elementAt(0);

        Map.Entry<String, Vector<Cell>> testmap= rtest.getRefInfo();
        Vector<Cell> vcelltest= testmap.getValue();

        Cell ctest= vcelltest.elementAt(0);

        Object o2= ctest.changeValue(12.2);
        assertEquals(rtest.getContent(),1.0);
    }

    @Test
    @DisplayName("updateRefsDay should work")
    public void testUpdateRefsDay(){
        cell.SinglecellRefDate("day");

        Vector<ReferencedCell> vrefest= cell.getRefs();
        ReferencedCell rtest= vrefest.elementAt(0);

        Map.Entry<String, Vector<Cell>> testmap= rtest.getRefInfo();
        Vector<Cell> vcelltest= testmap.getValue();

        Cell ctest= vcelltest.elementAt(0);
        LocalDate l2= LocalDate.of(2022, Month.FEBRUARY,16);
        Object o2= ctest.changeValue(l2);

        assertEquals(rtest.getContent(),16);
    }

    @Test
    @DisplayName("updateRefsDay should work")
    public void testUpdateRefsMonth(){
        cell.SinglecellRefDate("month");

        Vector<ReferencedCell> vrefest= cell.getRefs();
        ReferencedCell rtest= vrefest.elementAt(0);

        Map.Entry<String, Vector<Cell>> testmap= rtest.getRefInfo();
        Vector<Cell> vcelltest= testmap.getValue();

        Cell ctest= vcelltest.elementAt(0);
        LocalDate l2= LocalDate.of(2022, Month.FEBRUARY,16);
        Object o2= ctest.changeValue(l2);

        assertEquals(rtest.getContent(),2);
    }

    @Test
    @DisplayName("updateRefsYear should work")
    public void testUpdateRefsYear(){
        cell.SinglecellRefDate("year");

        Vector<ReferencedCell> vrefest= cell.getRefs();
        ReferencedCell rtest= vrefest.elementAt(0);

        Map.Entry<String, Vector<Cell>> testmap= rtest.getRefInfo();
        Vector<Cell> vcelltest= testmap.getValue();

        Cell ctest= vcelltest.elementAt(0);
        LocalDate l2= LocalDate.of(2022, Month.FEBRUARY,16);
        Object o2= ctest.changeValue(l2);

        assertEquals(rtest.getContent(),2022);
    }

    @Test
    @DisplayName("updateRefsDOTW should work")
    public void testUpdateRefsDOTW(){
        cell.SinglecellRefDate("dayoftheWeek");

        Vector<ReferencedCell> vrefest= cell.getRefs();
        ReferencedCell rtest= vrefest.elementAt(0);

        Map.Entry<String, Vector<Cell>> testmap= rtest.getRefInfo();
        Vector<Cell> vcelltest= testmap.getValue();

        Cell ctest= vcelltest.elementAt(0);
        LocalDate l2= LocalDate.of(2022, Month.FEBRUARY,16);
        Object o2= ctest.changeValue(l2);

        assertEquals(rtest.getContent(),"Wednesday");
    }

    @Test
    @DisplayName("updateRefsDay should work")
    public void testUpdateRefsequal(){
        cell.SinglecellRefDate("equal");

        Vector<ReferencedCell> vrefest= cell.getRefs();
        ReferencedCell rtest= vrefest.elementAt(0);

        Map.Entry<String, Vector<Cell>> testmap= rtest.getRefInfo();
        Vector<Cell> vcelltest= testmap.getValue();

        Cell ctest= vcelltest.elementAt(0);
        LocalDate l2= LocalDate.of(2022, Month.FEBRUARY,16);

        Object o2= ctest.changeValue(l2);

        assertEquals(rtest.getContent(),l2);
    }

}