package com.pomc.tests;

import com.pomc.classes.Block;
import com.pomc.classes.Cell;
import com.pomc.classes.Sheet;
import com.pomc.tests.stubs.CellStub;
import com.pomc.tests.stubs.BlockStub;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Vector;

import static org.junit.jupiter.api.Assertions.*;



public class SheetTest {

    private Sheet sheet;
    private CellStub[][] b ; //

    @BeforeEach
    void setUp(){

        b = new CellStub[10][10];
        for(int i = 0; i < 10; ++i){
            Vector<CellStub> row = new Vector<>();
            for(int j = 0; j < 10; ++j){
                b[i][j] = new CellStub(i, j, 3.0, "N");
            }
        }

        sheet = new Sheet( b ,"hoja");
        //array to vector, cambiar constructora


    }

    @Test
    @DisplayName("getNumRows should get the number of rows")
    public void getNumRowsTest () {

        assertEquals(sheet.getNumRows(), 10);
    }

    @Test
    @DisplayName("getNumCols should get the number of columns")
    public void getNumColsTest () {
        assertEquals(sheet.getNumCols(), 10);
    }

    @Test
    @DisplayName("getTitle should get the title of the sheet")
    public void getTitleTest () {
        assertEquals(sheet.getTitle(), "hoja");
    }

    @Test
    @DisplayName("getCell should get de cell att the position indicated")
    public void getCellTest() {
        CellStub c = (CellStub) sheet.getCell(2,2);
        assertEquals(3.0, c.getVal());
    }

    @Test
    @DisplayName("If there's no block selectet, its value is null")
    public void getSelectedBlockTest() {
        BlockStub b = (BlockStub) sheet.getSelectedBlock();
        BlockStub b2 = null;
        assertEquals(b2 , b);
    }

    @Test
    @DisplayName("If there's a selected block, getSelectedBlock should get its value")
    public void getSelectedBlock2Test() {
        //falta acabar
        //selectBlock..
        //BlockStub b = (BlockStub) sheet.getSelectedBlock();

        //BlockStub b2 = null;
        //assertNotEquals(b2 , b);
    }

    @Test
    @DisplayName("Test if getCells returns all the cells of the sheet")  //falta hacerla
    public void getCellsTest() {

    }

    @Test
    @DisplayName("Tests if the title setted is the title of the sheet")
    public void setTitleTest () {
        sheet.setTitle("hoja 1");
        assertEquals(sheet.getTitle(),"hoja 1");
    }

    @Test
    @DisplayName("NewRow should increase in one unit the numer of rows")
    public void testNewRow() {
        sheet.NewRow(0);
        assertEquals(11,sheet.getNumRows());
        assertEquals(10,sheet.getNumCols());
    }

    @Test
    @DisplayName("The value on the new row has to be null, the others stay the same") //isEqual
    public void testNewRow2() {
        //Vector<Vector<CellStub>> cells = sheet.getCells();

        /*
        for(int i = 0; i < 10; ++i){
            Vector<CellStub> row = new Vector<>();
            for(int j = 0; j < 10; ++j){
                b[i][j] = new CellStub(i, j, 3.0, "N");
            }
        }

        sheet = new Sheet( b ,"hoja");

         */

        sheet.NewRow(2);

        //cells por alguna razon esta vacio
        /*
        CellStub[][] b1 = new CellStub[cells.size()][(cells.firstElement()).size()];

        for(int i = 0; i < cells.size(); ++i){
            Vector<CellStub> row = cells.elementAt(i);

            for(int j = 0; j < cells.elementAt(0).size(); ++j){
                b1[i][j] = row.elementAt(j);
            }
        }

         */


        CellStub[][] b2 = new CellStub[11][10];

        for(int i = 0; i < 11; ++i){
            for(int j = 0; j < 10; ++j){
                if(i == 2) b2[i][j] = new CellStub(i, j, null, "N");
                else b2[i][j] = new CellStub(i, j, 3.0, "N");
            }
        }

        assertEquals(b, b2);



        /*
        for(int i = 0; i < 11; ++i){
            for(int j = 0; j < 10; ++j){

                assertEquals(b1, b2);
            }
        }

         */
    }










    @Test
    @DisplayName("create_block")
    public void testCreate_block() { //llamar a cell  ES CREADORA!!! se comprueba?
        //Cell c = sheet.getCell();
    }

    @Test
    @DisplayName("SelectBlock saves in b_selected the selected bloc") //depende de celda
    public void testSelectBlock() { //llamar cell
        //CellStub c1, c2;
        //c1 = new CellStub(0,0);
        //c2 = new CellStub();
        //assertEquals(sheet.SelectBlock(c1,c1),sheet.getSelectedBlock());
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
