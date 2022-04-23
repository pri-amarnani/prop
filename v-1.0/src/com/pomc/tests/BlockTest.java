package com.pomc.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.pomc.classes.Block;
import com.pomc.tests.stubs.CellStub;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.AbstractMap;
import java.util.Map;
import java.util.Vector;


public class BlockTest {

    Block bN;
    Block bD;
    Block bT;

    Block bMIX;

    Block testing;

    @BeforeEach
    void setUp() {
        CellStub blockN[][] = new CellStub[5][5];
        CellStub blockD[][] = new CellStub[5][5];
        CellStub blockT[][] = new CellStub[5][5];
        CellStub blockMIX[][] = new CellStub[5][5];

        for (int i = 0; i < 5; ++i) {
            for (int j = 0; j < 5; ++j) {

                LocalDate d = LocalDate.now();

                CellStub cN = new CellStub(i,j,i+j,"N");
                CellStub cD = new CellStub(i,j,d,"D");
                CellStub cT = new CellStub(i,j,"Celda:","T");

                blockN[i][j] = cN;
                blockD[i][j] = cD;
                blockT[i][j] = cT;

                if ((i+j)%3 == 0) blockMIX[i][j] = cN;
                else if ((i+j)%3 == 1) blockMIX[i][j] = cD;
                else blockMIX[i][j] = cT;
            }
        }

        bN = new Block(blockN, blockN[0][0], blockN[4][4]);
        bD = new Block(blockD, blockD[0][0], blockD[4][4]);
        bT = new Block(blockT, blockT[0][0], blockT[4][4]);
        bMIX = new Block(blockMIX, blockMIX[0][0], blockMIX[4][4]);
        testing = new Block(blockMIX, blockMIX[0][0], blockMIX[4][4]);
    }


    @Test
    @DisplayName("Get certain cell")
    void getCellTest() {
        CellStub c = (CellStub) bN.getCell(2,2);
        assertEquals(4, c.getVal());
    }


    @Test
    @DisplayName("Get number of rows")
    void getRowsTest() {
        assertEquals(5, bN.number_rows());
    }

    @Test
    @DisplayName("Get number of columns")
    void getColsTest() {
        assertEquals(5, bN.number_cols());
    }

    @Test
    @DisplayName("All are dates")
    void allDataTest1() {
        assertEquals(true, bD.allDate());
    }


    @Test
    @DisplayName("None is date")
    void allDataTest2() {
        assertEquals(false, bN.allDate());
    }

    @Test
    @DisplayName("Data test mixed values")
    void allDataTest3() {
        assertEquals(false, bMIX.allDate());
    }

    @Test
    @DisplayName("All are numeric")
    void allDoubleTest1() {
        assertEquals(true, bN.allDouble());
    }

    @Test
    @DisplayName("None is numeric")
    void allDoubleTest2() {
        assertEquals(false, bT.allDouble());
    }

    @Test
    @DisplayName("Numeric test mixed values")
    void allDoubleTest3() {
        assertEquals(false, bMIX.allDouble());
    }

    @Test
    @DisplayName("All are text")
    void allTextTest1() {
        assertEquals(true, bT.allText());
    }

    @Test
    @DisplayName("None is text")
    void allTextTest2() {
        assertEquals(false, bD.allText());
    }

    @Test
    @DisplayName("Text test mixed values")
    void allTextTest3() {
        assertEquals(false, bMIX.allText());
    }


    @Test
    @DisplayName("Test if two blocks are the same")
    void testIsEqual() {
        assertEquals(true, bN.isEqual(bN));
    }

    @Test
    @DisplayName("Test if the values are copied correctly from one block to another, without references")
    void testRef() {
        bN.ref(testing, false);
        assertEquals(true, bN.isEqual(testing));
    }

    @Test
    @DisplayName("Test if the values are copied correctly from one block to another, with references")
    void testRef2() {

    }

    /*
    @Test
    @DisplayName("Test modify block num")
    void testModifyBlockNum() {

    }

    @Test
    @DisplayName("Test modify block test")
    void testModifyBlockText() {

    }

    @Test
    @DisplayName("Test modify block test")
    void testModifyBlockDate() {

    }

    @Test
    @DisplayName("Test sort")
    void testSort() {

    }

    @Test
    @DisplayName("Test find num")
    void testFindNum() {

    }

    @Test
    @DisplayName("Test find text")
    void testFindText() {

    }

    @Test
    @DisplayName("Test find date")
    void testFindDate() {

    }

    @Test
    @DisplayName("Test find and replace num")
    void testFindReplaceNum() {

    }

    @Test
    @DisplayName("Test find and replace text")
    void testFindReplaceText() {

    }

    @Test
    @DisplayName("Test find and replace date")
    void testFindReplaceDate() {

    }

    @Test
    @DisplayName("Test floor, without references")
    void testFloor() {

    }

    @Test
    @DisplayName("Test floor, with references")
    void testFloor2() {

    }

    @Test
    @DisplayName("Test convert")
    void testConvert() {

    }

    @Test
    @DisplayName("Test sum, without references")
    void testSumB() {

    }

    @Test
    @DisplayName("Test sum, with references")
    void testSumB2() {

    }

    @Test
    @DisplayName("Test sub, without references")
    void testSubB() {

    }

    @Test
    @DisplayName("Test sub, with references")
    void testSubB2() {

    }

    @Test
    @DisplayName("Test mult, without references")
    void testMultB() {

    }

    @Test
    @DisplayName("Test mult, with references")
    void testMultB2() {

    }

    @Test
    @DisplayName("Test div, without references")
    void testDivB() {

    }

    @Test
    @DisplayName("Test div, with references")
    void testDivB2() {

    }

    @Test
    @DisplayName("Test extract")
    void testExtract() {

    }

    @Test
    @DisplayName("Test day of the week")
    void testDOW() {

    }

    @Test
    @DisplayName("Test replace with criteria")
    void testReplaceWithCriteria() {

    }

    @Test
    @DisplayName("Test mean with value")
    void testMeanVal() {

    }

    @Test
    @DisplayName("Test mean with value and ref")
    void testMeanValRef() {

    }

    @Test
    @DisplayName("Test median with value")
    void testMedianVal() {

    }

    @Test
    @DisplayName("Test median with value and ref")
    void testMedianValRef() {

    }

    @Test
    @DisplayName("Test var with value")
    void testVarVal() {

    }

    @Test
    @DisplayName("Test var with value and ref")
    void testVarValRef() {

    }

    @Test
    @DisplayName("Test covar with value")
    void testCovarVal() {

    }

    @Test
    @DisplayName("Test covar with value and ref")
    void testCovarValRef() {

    }

    @Test
    @DisplayName("Test std with value")
    void testStdVal() {

    }

    @Test
    @DisplayName("Test std with value and ref")
    void testStdValRef() {

    }

    @Test
    @DisplayName("Test cpearson with value")
    void testCPVal() {

    }

    @Test
    @DisplayName("Test cpearson with value and ref")
    void testCPValRef() {

    }*/

}
