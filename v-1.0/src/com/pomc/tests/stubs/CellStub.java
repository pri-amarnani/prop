package com.pomc.tests.stubs;

import com.pomc.classes.Cell;
import com.pomc.classes.ReferencedCell;

import java.util.Map;
import java.util.Vector;

public class CellStub extends Cell {
    /**
     * Creadora
     *
     * @param row
     * @param column
     */
    public CellStub(int row, int column) {
        super(row, column);
        ReferencedCell r= new ReferencedCell(3,3,"=c21+c21");
        ReferencedCell r2= new ReferencedCell(3,2,"=c1+c2");
        this.AddRef(r);
    }

    @Override
    public String getType() {
        return null;
    }

    @Override
    public Object getInfo() {
        return null;
    }

    @Override
    public void changeValue(Object o) {

    }

    @Override
    public void setRefInfo(Map.Entry<String, Vector<Cell>> refInfo) {

    }
}
