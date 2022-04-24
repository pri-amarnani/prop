package com.pomc.tests;


import com.pomc.classes.Sheet;
import com.pomc.tests.stubs.CellStub;
import com.pomc.tests.stubs.BlockStub;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Vector;

import static org.junit.jupiter.api.Assertions.*;



public class SheetTest {
    /*
    private Sheet sheet;
    private CellStub[][] b ;

    @BeforeEach
    void setUp(){

        b = new CellStub[10][10];
        for(int i = 0; i < 10; ++i){
            for(int j = 0; j < 10; ++j){
                b[i][j] = new CellStub(i, j, 3.0, "N");
            }
        }

        sheet = new Sheet( b ,"hoja");

    }

    @Test
    @DisplayName("getNumRows should get the number of rows")
    public void getNumRowsTest (){
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
    @DisplayName("create_block")
    public void create_blockTest() {

        CellStub[][] b2 = new CellStub[3][3];

        for(int i = 0; i < 3; ++i){
            for(int j = 0; j < 3; ++j){
                b2[i][j] = new CellStub(i, j, 3.0, "N");
            }
        }

        BlockStub b1 = new BlockStub(b2, b2[0][0], b2[2][2]);
        assertTrue(b1.isEqual(sheet.create_block(sheet.getCell(0,0), sheet.getCell(2,2))));
    }


    @Test
    @DisplayName("SelectBlock saves in b_selected the selected bloc")
    public void SelectBlockTest() {
        CellStub c1;
        c1 = (CellStub) sheet.getCell(0,0);

        CellStub[][] b1 = new CellStub[1][1];

        for(int i = 0; i < 1; ++i){
            for(int j = 0; j < 1; ++j){
                b1[i][j] = new CellStub(i, j, 3.0, "N");
            }
        }

        BlockStub b2 = new BlockStub(b1, b1[0][0],b1[0][0]);
        assertTrue(b2.isEqual(sheet.SelectBlock(c1,c1)));
    }

    @Test
    @DisplayName("SelectBlock saves in b_selected the selected bloc")
    public void SelectBlock2Test() {
        CellStub c1;
        c1 = (CellStub) sheet.getCell(0,0);

        CellStub[][] b1 = new CellStub[3][3];

        for(int i = 0; i < 3; ++i){
            for(int j = 0; j < 3; ++j){
                b1[i][j] = new CellStub(i, j, 3.0, "N");
            }
        }

        BlockStub b2 = new BlockStub(b1, b1[0][0],b1[0][0]);
        assertFalse(b2.isEqual(sheet.SelectBlock(c1,c1)));
    }

    @Test
    @DisplayName("SelectBlock saves in b_selected the selected bloc")
    public void getSelectBlockTest() {
        CellStub c1 = (CellStub) sheet.getCell(0,0);
        assertEquals(sheet.SelectBlock(c1,c1), sheet.getSelectedBlock());
    }

    @Test
    @DisplayName("If there's no block selectet, its value is null")
    public void getSelectedBlock2Test() {
        BlockStub b = (BlockStub) sheet.getSelectedBlock();
        BlockStub b2 = null;
        assertEquals(b2 , b);
    }

    @Test
    @DisplayName("Test if getCells returns all the cells of the sheet")  //falta hacerla
    public void getCellsTest() {

        for(int i = 0; i < 10; ++i){
            for(int j = 0; j < 10; ++j){
                CellStub c = (CellStub) sheet.getCells().elementAt(i).elementAt(j);
                assertEquals(3.0, c.getVal());
            }
        }
    }

    @Test
    @DisplayName("Tests if the title setted is the title of the sheet")
    public void setTitleTest () {
        sheet.setTitle("hoja 1");
        assertEquals(sheet.getTitle(),"hoja 1");
    }

    @Test
    @DisplayName("isEqual should tell either or not two sheets contain the same information")
    public void isEqualTest (){

        CellStub[][] b2 = new CellStub[10][10];

        for(int i = 0; i < 10; ++i){
            for(int j = 0; j < 10; ++j){
                b2[i][j] = new CellStub(i, j, 3.0, "N");
            }
        }

        Sheet sh = new Sheet( b2 ,"hoja");

        CellStub[][] b3 = new CellStub[10][10];

        for(int i = 0; i < 10; ++i){
            for(int j = 0; j < 10; ++j){
                b3[i][j] = new CellStub(i, j, 3.0, "N");
            }
        }

        Sheet sh2 = new Sheet( b3 ,"hoja 2");
        assert(sh.isEqual(sh2));
    }

    @Test
    @DisplayName("isEqual should tell either or not two sheets contain the same information")
    public void isEqual2Test(){

        CellStub[][] b2 = new CellStub[10][10];

        for(int i = 0; i < 10; ++i){
            for(int j = 0; j < 10; ++j){
                b2[i][j] = new CellStub(i, j, 3.0, "N");
            }
        }

        Sheet sh = new Sheet( b2 ,"hoja");

        CellStub[][] b3 = new CellStub[10][10];

        for(int i = 0; i < 10; ++i){
            for(int j = 0; j < 10; ++j){
                b3[i][j] = new CellStub(i, j, "c", "T");
            }
        }

        Sheet sh2 = new Sheet( b3 ,"hoja 2");

        assertEquals(false, sh.isEqual(sh2));
    }

    @Test
    @DisplayName("isEqual should tell either or not two sheets contain the same information")
    public void isEqual3Test(){

        CellStub[][] b2 = new CellStub[10][10];

        for(int i = 0; i < 10; ++i){
            for(int j = 0; j < 10; ++j){
                b2[i][j] = new CellStub(i, j, 3.0, "N");
            }
        }

        Sheet sh = new Sheet( b2 ,"hoja");

        CellStub[][] b3 = new CellStub[5][5];

        for(int i = 0; i < 5; ++i){
            for(int j = 0; j < 5; ++j){
                b3[i][j] = new CellStub(i, j, 3.0, "N");
            }
        }

        Sheet sh2 = new Sheet( b3 ,"hoja 2");


        assertEquals(false, sh.isEqual(sh2));
    }

    @Test
    @DisplayName("isEqual should tell either or not two sheets contain the same information")
    public void isEqual4Test(){

        CellStub[][] b2 = new CellStub[10][10];

        for(int i = 0; i < 10; ++i){
            for(int j = 0; j < 10; ++j){
                b2[i][j] = new CellStub(i, j, 3.0, "N");
            }
        }

        Sheet sh = new Sheet( b2 ,"hoja");

        CellStub[][] b3 = new CellStub[10][10];

        for(int i = 0; i < 5; ++i){
            for(int j = 0; j < 5; ++j){
                b3[i][j] = new CellStub(i, j, 2.0, "N");
            }
        }

        Sheet sh2 = new Sheet( b3 ,"hoja 2");


        assertEquals(false, sh.isEqual(sh2));
    }

    @Test
    @DisplayName("NewRow should increase in one unit the numer of rows")
    public void NewRowTest() {
        sheet.NewRow(0);
        assertEquals(11,sheet.getNumRows());
        assertEquals(10,sheet.getNumCols());
    }

    @Test
    @DisplayName("The value on the new row has to be null, the others stay the same") //isEqual
    public void NewRow2Test() {

        CellStub[][] b2 = new CellStub[11][10];

        for(int i = 0; i < 11; ++i){
            for(int j = 0; j < 10; ++j){
                if(i == 2) b2[i][j] = new CellStub(i, j, null, "N");
                else b2[i][j] = new CellStub(i, j, 3.0, "N");
            }
        }
        Sheet sh = new Sheet( b2 ,"hoja 2");
        sheet.NewRow(2);
        assertTrue(sheet.isEqual(sh));
    }

    @Test
    @DisplayName("NewColumn should increase in one unit the numer of columns")
    public void NewColumnTest() {
        sheet.NewColumn(0);
        assertEquals(11,sheet.getNumCols());
        assertEquals(10,sheet.getNumRows());
    }

    @Test
    @DisplayName("The value on the new column has to be null, the others stay the same")
    public void NewColumn2Test() {

        CellStub[][] b2 = new CellStub[10][11];

        for(int i = 0; i < 10; ++i){
            Vector<CellStub> row = new Vector<>();
            for(int j = 0; j < 11; ++j){
                if(j == 2) b2[i][j] = new CellStub(i, j, null, "N");
                else b2[i][j] = new CellStub(i, j, 3.0, "N");
            }
        }

        Sheet sh = new Sheet(b2, "hoja 1");
        sheet.NewColumn(2);

        assertTrue(sheet.isEqual(sh));
    }

    @Test
    @DisplayName("DeleteRow should decrease in one unit the numer of rows")
    public void DeleteRowTest() {
        sheet.DeleteRow(0);
        assertEquals(9,sheet.getNumRows());
        assertEquals(10,sheet.getNumCols());
    }

    @Test
    @DisplayName("The value on the new row has to be null, the others stay the same")
    public void DeleteRow2Test() {

        CellStub[][] b2 = new CellStub[9][10];

        for(int i = 0; i < 9; ++i){
            Vector<CellStub> row = new Vector<>();
            for(int j = 0; j < 10; ++j){
                b2[i][j] = new CellStub(i, j, 3.0, "N");
            }
        }

        Sheet sh = new Sheet(b2, "hoja 1");
        sheet.DeleteRow(2);
        assertTrue(sheet.isEqual(sh));
    }

    @Test
    @DisplayName("DeleteColumn should decrease in one unit the numer of columns")
    public void DeleteColumnTest() {
        sheet.DeleteColumn(0);
        assertEquals(9,sheet.getNumCols());
        assertEquals(10,sheet.getNumRows()); //for para ver si todos los valores son null
    }

    @Test
    @DisplayName("The value after deleting a column should change (the position of each cell)")
    public void DeleteColumn2Test() {
        CellStub[][] b2 = new CellStub[10][9];

        for(int i = 0; i < 10; ++i){
            Vector<CellStub> row = new Vector<>();
            for(int j = 0; j < 9; ++j){
                b2[i][j] = new CellStub(i, j, 3.0, "N");
            }
        }

        Sheet sh = new Sheet(b2, "hoja 1");
        sheet.DeleteColumn(2);
        assert(sheet.isEqual(sh));
    }

    @Test
    @DisplayName("The cell value (number) is replaced by a text")
    public void change_valueTest(){
        CellStub c = (CellStub) sheet.getCell(0,0);
        sheet.change_value(c, "hola");
        assertEquals("hola", ((CellStub) sheet.getCell(0,0)).getVal());
    }

    @Test
    @DisplayName("The cell value (Text) is replaced by a number")
    public void change_value2Test(){
        CellStub[][] b2 = new CellStub[10][10];

        for(int i = 0; i < 10; ++i){
            for(int j = 0; j < 10; ++j){
                b2[i][j] = new CellStub(i, j, "hola", "T");
            }
        }

        sheet = new Sheet( b2 ,"hoja");

        CellStub c = (CellStub) sheet.getCell(0,0);
        sheet.change_value(c, 2.0);
        assertEquals(2.0, ((CellStub) sheet.getCell(0,0)).getVal());
    }

    @Test
    @DisplayName("The cell value (doouble) is replaced by a Date")
    public void change_value3Test(){

        CellStub c = (CellStub) sheet.getCell(0,0);
        sheet.change_value(c, LocalDate.of(2022,04,24));
        assertEquals(LocalDate.of(2022,04,24), ((CellStub) sheet.getCell(0,0)).getVal());
    }

    @Test
    @DisplayName("overlapping should tell either or not two blocks collide")
    public void OverlappingTest(){

        CellStub[][] b1 = new CellStub[5][5];

        for(int i = 0; i < 5; ++i){
            Vector<CellStub> row = new Vector<>();
            for(int j = 0; j < 5; ++j){
                b1[i][j] = (CellStub) sheet.getCell(i, j);
            }
        }

        CellStub[][] b2 = new CellStub[4][4];

        for(int i = 0; i < 4; ++i){
            Vector<CellStub> row = new Vector<>();
            for(int j = 0; j < 4; ++j){
                b2[i][j] = (CellStub) sheet.getCell(i+4, j+4);
            }
        }

        BlockStub bs1 = new BlockStub(b1, b1[0][0], b1[4][4]);
        BlockStub bs2 = new BlockStub(b2, b2[0][0], b2[3][3]);

        assert(sheet.overlapping(bs1,bs2));
    }

    @Test
    @DisplayName("overlapping should tell either or not two blocks collide")
    public void Overlapping2Test(){

        CellStub[][] b1 = new CellStub[3][3];

        for(int i = 0; i < 3; ++i){
            Vector<CellStub> row = new Vector<>();
            for(int j = 0; j < 3; ++j){
                b1[i][j] = (CellStub) sheet.getCell(i, j);
            }
        }

        CellStub[][] b2 = new CellStub[4][4];

        for(int i = 0; i < 4; ++i){
            Vector<CellStub> row = new Vector<>();
            for(int j = 0; j < 4; ++j){
                b2[i][j] = (CellStub) sheet.getCell(i+4, j+4);
            }
        }

        BlockStub bs1 = new BlockStub(b1, b1[0][0], b1[2][2]);
        BlockStub bs2 = new BlockStub(b2, b2[0][0], b2[3][3]);

        assertFalse(sheet.overlapping(bs1,bs2));
    }*/

}
