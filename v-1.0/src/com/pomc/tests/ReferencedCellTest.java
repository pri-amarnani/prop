package com.pomc.tests;


import static org.junit.jupiter.api.Assertions.assertEquals;

import com.pomc.classes.Cell;
import com.pomc.classes.NumCell;
import com.pomc.classes.ReferencedCell;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.AbstractMap;
import java.util.Map;
import java.util.Vector;

public class ReferencedCellTest {
    private ReferencedCell rcell;

    @BeforeEach
    void setUp(){
        rcell= new ReferencedCell(22,22,"=c1+c2");
        NumCell n= new NumCell(2,2,6.0);
        Vector<Cell> v= new Vector<>();
        v.add(n);
        Map.Entry<String, Vector<Cell>> r = new AbstractMap.SimpleEntry<>("SUM", v);
        rcell.setRefInfo(r);
    }


    @Test
    @DisplayName("getType should work")
    public void test_getType(){
        assertEquals( rcell.getType(),"R");
    }

    @Test
    @DisplayName("getInfo should work")
    public void test_getInfo(){
        assertEquals( rcell.getInfo(),"=c1+c2");
    }

    @Test
    @DisplayName("getType should work")
    public void test_getRefInfo(){
        Map.Entry<String,Vector<Cell>> refinfotest= rcell.getRefInfo();
        assertEquals(refinfotest.getKey(),"SUM");
        assertEquals(refinfotest.getValue().elementAt(0).getInfo(),6.0);
    }
    @Test
    @DisplayName("getValue should work")
    public void test_setRefInfo(){
        NumCell n= new NumCell(1,2,4.0);
        Vector<Cell> v= new Vector<>();
        v.add(n);
        Map.Entry<String, Vector<Cell>> r = new AbstractMap.SimpleEntry<>("DIV", v);
        rcell.setRefInfo(r);
        Map.Entry<String,Vector<Cell>> refinfotest= rcell.getRefInfo();
        assertEquals(refinfotest.getKey(),"DIV");
        assertEquals(refinfotest.getValue().elementAt(0).getInfo(),4.0);
    }

    @Test
    @DisplayName("setInfo should work")
    public void test_setInfo(){
        rcell.setInfo("=c2/c3");
        assertEquals(rcell.getInfo(),"=c2/c3");
    }

    @Test
    @DisplayName("getType should work")
    public void test_changeValue(){
       rcell.changeValue(2);
        assertEquals(rcell.getValue(),2);
    }





}
