package com.pomc.tests;

import com.pomc.classes.Block;
import com.pomc.classes.Cell;
import com.pomc.classes.NumCell;
import com.pomc.classes.Sheet;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Vector;

import static org.junit.jupiter.api.Assertions.*;

import com.pomc.tests.stubs.BlockStub;


public class SheetTest {

    private Sheet sheet;

    private Cell[][] b = new Cell[][];
    for (Cell[] bl : b) {
        for (int j = 0; j < b[0].length; ++j) {
            //if (b[j].isNum()) b[j].changeValue(n);
        }
    }

    private BlockStub bs = new BlockStub();

    @BeforeEach
    void setUp(){
        sheet = new Sheet("hoja");
    }
    /*
    sheet = new Sheet();
    assertEquals(60, sheet.getNumRows());   //comprobar creadora?
    assertEquals(60, sheet.getNumCols());
     */

    @Test
    @DisplayName("NewRow should increase in one unit the numer of rows")
    public void testNewRow() {
        sheet = new Sheet("hoja");
        sheet.NewRow(0);
        assertEquals(65,sheet.getNumRows());
        assertEquals(64,sheet.getNumCols());
    }

    @Test
    @DisplayName("The value on the new row has to be null, the others stay the same")
    public void testNewRow2() {
        Vector<Vector<Cell>> cells = new Vector<>();
        for(int i = 0; i < 10; ++i){
            Vector<Cell> row = new Vector<>();
            for(int j = 0; j < 10; ++j){
                row.add(new NumCell(i, j, 3.0));
            }
            cells.add(row);
        }
        Vector<Vector<Cell>> cells2 = new Vector<>();
        for(int i = 0; i < 11; ++i){
            Vector<Cell> row = new Vector<>();
            for(int j = 0; j < 10; ++j){
                if(i == 2) row.add(new NumCell(i, j, null));
                else row.add(new NumCell(i, j, 3.0));
            }
            cells2.add(row);
        }
        sheet = new Sheet(cells, "hoja 1");
        sheet.NewRow(2);
        //assertEquals(61,sheet.getNumRows());
        //assertEquals(60,sheet.getNumCols());
        assertEquals(cells, cells2);
    }

    @Test
    @DisplayName("NewColumn should increase in one unit the numer of columns")
    public void testNewColumn() {
        sheet.NewColumn(0);
        assertEquals(65,sheet.getNumCols());
        assertEquals(64,sheet.getNumRows());
    }

    @Test
    @DisplayName("The value on the new column has to be null, the others stay the same")
    public void testNewColumn2() {
        Vector<Vector<Cell>> cells = new Vector<>();
        for(int i = 0; i < 10; ++i){
            Vector<Cell> row = new Vector<>();
            for(int j = 0; j < 10; ++j){
                row.add(new NumCell(i, j, 3.0));
            }
            cells.add(row);
        }
        Vector<Vector<Cell>> cells2 = new Vector<>();
        for(int i = 0; i < 10; ++i){
            Vector<Cell> row = new Vector<>();
            for(int j = 0; j < 11; ++j){
                if(j == 2) row.add(new NumCell(i, j, null));
                else row.add(new NumCell(i, j, 3.0));
            }
            cells2.add(row);
        }
        sheet = new Sheet(cells, "hoja 1");
        sheet.NewColumn(2);
        //assertEquals(65,sheet.getNumCols());
        //assertEquals(64,sheet.getNumRows());
        assertEquals(cells, cells2);
    }

    @Test
    @DisplayName("DeleteRow should decrease in one unit the numer of rows")
    public void testDeleteRow() {
        sheet.DeleteRow(0);
        assertEquals(63,sheet.getNumRows());
        assertEquals(64,sheet.getNumCols());
    }

    @Test
    @DisplayName("The value on the new row has to be null, the others stay the same")
    public void testDeleteRow2() {
        Vector<Vector<Cell>> cells = new Vector<>();
        for(int i = 0; i < 10; ++i){
            Vector<Cell> row = new Vector<>();
            for(int j = 0; j < 10; ++j){
                row.add(new NumCell(i, j, 3.0));
            }
            cells.add(row);
        }
        Vector<Vector<Cell>> cells2 = new Vector<>();
        for(int i = 0; i < 9; ++i){
            Vector<Cell> row = new Vector<>();
            for(int j = 0; j < 10; ++j){
                row.add(new NumCell(i, j, 3.0));
            }
            cells2.add(row);
        }
        sheet = new Sheet(cells, "hoja 1");
        sheet.DeleteRow(2);
        //assertEquals(65,sheet.getNumRows());
        //assertEquals(64,sheet.getNumCols());
        assertEquals(cells, cells2);
    }

    @Test
    @DisplayName("DeleteColumn should decrease in one unit the numer of columns")
    public void testDeleteColumn() {
        sheet.DeleteColumn(0);
        assertEquals(63,sheet.getNumCols());
        assertEquals(64,sheet.getNumRows()); //for para ver si todos los valores son null
    }

    @Test
    @DisplayName("The value after deleting a column should change (the position of each cell)")
    public void testDeleteColumn2() {
        Vector<Vector<Cell>> cells = new Vector<>();
        for(int i = 0; i < 10; ++i){
            Vector<Cell> row = new Vector<>();
            for(int j = 0; j < 10; ++j){
                row.add(new NumCell(i, j, 3.0));
            }
            cells.add(row);
        }
        Vector<Vector<Cell>> cells2 = new Vector<>();
        for(int i = 0; i < 10; ++i){
            Vector<Cell> row = new Vector<>();
            for(int j = 0; j < 9; ++j){
                row.add(new NumCell(i, j, 3.0));
            }
            cells2.add(row);
        }
        sheet = new Sheet(cells, "hoja 1");
        sheet.NewColumn(2);
        //assertEquals(65,sheet.getNumCols());
        //assertEquals(64,sheet.getNumRows());
        assertEquals(cells, cells2);
    }

    @Test
    @DisplayName("create_block")
    public void testCreate_block() { //llamar a cell

    }

    @Test
    @DisplayName("SelectBlock")
    public void testSelectBlock() { //llamar cell

    }

    @Test
    @DisplayName("find")
    public void testFind() {

    }

    @Test
    @DisplayName("overlapping")
    public Boolean testOverlapping(){
        return true; //nope
    }

    @Test
    @DisplayName("length")
    public void testLength() {

    }

}
