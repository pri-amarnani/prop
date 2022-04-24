package com.pomc.tests;

import com.pomc.classes.Block;
import com.pomc.tests.stubs.CellStub;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;


public class BlockTest {

    Block bN;
    Block bD;
    Block bT;

    Block bMIX;

    Block testing;
    Block testing2;
    Block testingStadistics;

    Block n_t;

    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = BigDecimal.valueOf(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    @BeforeEach
    void setUp() {
        CellStub blockN[][] = new CellStub[5][5];
        CellStub blockD[][] = new CellStub[5][5];
        CellStub blockT[][] = new CellStub[5][5];
        CellStub blockMIX[][] = new CellStub[5][5];
        CellStub blockS[][] = new CellStub[5][5];
        CellStub blockNT[][] = new CellStub[5][5];

        int in = 0;
        double c = 0.3;

        for (int i = 0; i < 5; ++i) {
            for (int j = 0; j < 5; ++j) {
                CellStub cN = new CellStub(i,j,(i+j)*1.0,"N");
                CellStub cS = new CellStub(i,j,c+0.6,"N");
                CellStub cD = new CellStub(i,j,LocalDate.of(2001,8,in+1),"D");
                CellStub cT = new CellStub(i,j,"Celda:"+in,"T");

                blockN[i][j] = cN;
                blockD[i][j] = cD;
                blockT[i][j] = cT;
                blockS[i][j] = cS;
                if ((i+j)%3 == 0) blockMIX[i][j] = new CellStub(i,j,(i+j)*1.0,"N");
                else if ((i+j)%3 == 1) blockMIX[i][j] = new CellStub(i,j,LocalDate.of(2001,8,in+1),"D");
                else blockMIX[i][j] = new CellStub(i,j,"Celda:"+in,"T");

                //new CellStub(i,j,"Celda:"+in,"T");
                if ((i+j)%2 == 0 || j == 1) blockNT[i][j] = new CellStub(i,j,"Celda:"+in,"T");
                else blockNT[i][j] = new CellStub(i,j,(i+j)*1.0,"N");

                ++in;
                c += 0.6;
            }
        }

        bN = new Block(blockN, blockN[0][0], blockN[4][4]);
        bD = new Block(blockD, blockD[0][0], blockD[4][4]);
        bT = new Block(blockT, blockT[0][0], blockT[4][4]);
        bMIX = new Block(blockMIX, blockMIX[0][0], blockMIX[4][4]);
        testing = new Block(blockMIX, blockMIX[0][0], blockMIX[4][4]);
        testing2 = new Block(blockN, blockN[0][0], blockN[4][4]);
        testingStadistics = new Block(blockS, blockN[0][0], blockN[4][4]);
        n_t = new Block(blockNT, blockNT[0][0], blockNT[4][4]);
    }


    @Test
    @DisplayName("Get certain cell")
    void getCellTest() {
        CellStub c = (CellStub) bN.getCell(2,2);

        assertEquals(4.0, c.getVal());
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
        assertTrue(bD.allDate());
    }


    @Test
    @DisplayName("None is date")
    void allDataTest2() {
        assertFalse(bN.allDate());
    }

    @Test
    @DisplayName("Data test mixed values")
    void allDataTest3() {
        assertFalse(bMIX.allDate());
    }

    @Test
    @DisplayName("All are numeric")
    void allDoubleTest1() {
        assertTrue(bN.allDouble());
    }

    @Test
    @DisplayName("None is numeric")
    void allDoubleTest2() {
        assertFalse(bT.allDouble());
    }

    @Test
    @DisplayName("Numeric test mixed values")
    void allDoubleTest3() {
        assertFalse(bMIX.allDouble());
    }

    @Test
    @DisplayName("All are text")
    void allTextTest1() {
        assertTrue(bT.allText());
    }

    @Test
    @DisplayName("None is text")
    void allTextTest2() {
        assertFalse(bD.allText());
    }

    @Test
    @DisplayName("Text test mixed values")
    void allTextTest3() {
        assertFalse(bMIX.allText());
    }


    @Test
    @DisplayName("Test if two blocks are the same")
    void testIsEqualT() {
        assertTrue(bN.isEqual(bN));
    }

    @Test
    @DisplayName("Test if two blocks aren't the same")
    void testIsEqualFalse() {
        assertFalse(bN.isEqual(bMIX));
    }

    @Test
    @DisplayName("Test if the values are copied correctly from one block to another, without references")
    void testRef() {
        bN.ref(testing, false);

        CellStub c1 = (CellStub) testing.getCell(2,2);
        CellStub c = (CellStub) bN.getCell(2,2);

        assertTrue(bN.isEqual(testing));
    }

    @Test
    @DisplayName("Test modify block num, true")
    void testModifyBlockNumT() {
        testing2.ModifyBlock(10.0);
        testing.ModifyBlock(10.0);
        //System.out.println(testing.getCell(2,2));
        assertTrue(testing2.isEqual(testing));
    }

    @Test
    @DisplayName("Test modify block num, false")
    void testModifyBlockNumF() {
        testing.ModifyBlock(20.0);
        testing2.ModifyBlock("ststst");

        assertFalse(testing2.isEqual(testing));
    }

    @Test
    @DisplayName("Test modify block text, true")
    void testModifyBlockTextT() {
        testing2.ModifyBlock("hola");
        testing.ModifyBlock("hola");
        assertTrue(testing2.isEqual(testing));
    }

    @Test
    @DisplayName("Test modify block text, false")
    void testModifyBlockTextF() {
        testing2.ModifyBlock("hola");
        testing.ModifyBlock("adeu");
        assertFalse(testing2.isEqual(testing));
    }

    @Test
    @DisplayName("Test modify block date, true")
    void testModifyBlockDateT() {
        testing2.ModifyBlock(LocalDate.of(2001,8,8));
        testing.ModifyBlock(LocalDate.of(2001,8,8));

        CellStub c = (CellStub) testing.getCell(3,2); //val = 4
        CellStub c1 = (CellStub) testing2.getCell(2,2); //val = 4

        assertTrue(testing2.isEqual(testing));
    }

    @Test
    @DisplayName("Test modify block date, false")
    void testModifyBlockDateF() {
        testing2.ModifyBlock(LocalDate.now());
        testing.ModifyBlock(LocalDate.of(2001,8,8));

        assertFalse(testing2.isEqual(testing));
    }

    @Test
    @DisplayName("Test sort")
    void testSort() {

        for (int i = 0; i < bN.number_rows(); ++i) {
            for (int j = 0; j < bN.number_cols(); ++j) {
                System.out.print(n_t.getCell(i,j).getInfo() + " ");
            }
            System.out.println("\n");
        }
        System.out.println("----------");
        n_t.SortBlock(1,">", "T");
        for (int i = 0; i < bN.number_rows(); ++i) {
            for (int j = 0; j < bN.number_cols(); ++j) {
                System.out.print(n_t.getCell(i,j).getInfo() + " ");
            }
            System.out.println("\n");
        }



    }

    @Test
    @DisplayName("Test find num")
    void testFindN() {
        CellStub c = (CellStub) bN.getCell(3,4);
        assertEquals(c,bN.find(7.0));
    }

    @Test
    @DisplayName("Test find text")
    void testFindT() {
        CellStub c = (CellStub) bT.getCell(3,4);
        assertEquals(c,bT.find("Celda:19"));
    }

    @Test
    @DisplayName("Test find date")
    void testFindD() {
        CellStub c = (CellStub) bD.getCell(0,3);
        assertEquals(c,bD.find(LocalDate.of(2001,8,4)));
    }

    @Test
    @DisplayName("Test find and replace num")
    void testFindReplaceN() {
        bN.findAndReplace(7.0,1000.0);
        CellStub c = (CellStub) bN.getCell(3,4);
        System.out.println(c);
        assertEquals(1000.0, c.getInfo());
    }

    @Test
    @DisplayName("Test find and replace text")
    void testFindReplaceT() {
        bT.findAndReplace("Celda:7",1000.0);
        CellStub c = (CellStub) bT.getCell(1,2);
        assertEquals(1000.0, c.getInfo());
    }

    @Test
    @DisplayName("Test find and replace date")
    void testFindReplaceD() {
        bD.findAndReplace(LocalDate.of(2001,8,4),LocalDate.of(2001,8,12));
        CellStub c = (CellStub) bD.getCell(0,3);

        assertEquals(LocalDate.of(2001,8,12), c.getInfo());
    }

    @Test
    @DisplayName("Test floor, without references")
    void testFloor() {
        testingStadistics.floor(testing, false);

        assertEquals(5.0, testing.getCell(1,2).getInfo());
    }

    @Test
    @DisplayName("Test sum, without references")
    void testSumB() {
        testing.ModifyBlock(10.0);
        bN.ModifyBlock(20.0);

        testing.sum(testing,testing2,false);

        assertTrue(bN.isEqual(testing2));
    }

    @Test
    @DisplayName("Test sub, without references")
    void testSubB() {
        testing.ModifyBlock(10.0);
        bN.ModifyBlock(0.0);

        testing.sum(testing,testing2,false);

        assertTrue(bN.isEqual(testing2));
    }

    @Test
    @DisplayName("Test mult, without references")
    void testMultB() {
        testing.ModifyBlock(10.0);
        bN.ModifyBlock(100.0);

        testing.sum(testing,testing2,false);

        assertTrue(bN.isEqual(testing2));
    }

    @Test
    @DisplayName("Test div, without references")
    void testDivB() {
        testing.ModifyBlock(10.0);
        bN.ModifyBlock(1.0);

        testing.sum(testing,testing2,false);

        assertTrue(bN.isEqual(testing2));
    }


    @Test
    @DisplayName("Test extract")
    void testExtract() {
        assertTrue(false);
    }

    @Test
    @DisplayName("Test day of the week")
    void testDOW() {
        assertTrue(false);
    }

    @Test
    @DisplayName("Test replace with criteria")
    void testReplaceWithCriteria() {
        assertTrue(false);
    }

    @Test
    @DisplayName("Test mean with value")
    void testMeanVal() {
        CellStub c2 = (CellStub) testing2.getCell(4,4);
        System.out.println(c2.getInfo());
        CellStub c = (CellStub) bN.mean(c2,false);

        assertEquals(4.0, c.getInfo());
    }

    @Test
    @DisplayName("Test median with value")
    void testMedianVal() {
        CellStub c2 = (CellStub) testing2.getCell(2,2);
        CellStub c = (CellStub) bN.median(c2,false);

        assertEquals(4.0, c.getInfo());
    }

    @Test
    @DisplayName("Test std with value")
    void testStdVal() {
        CellStub c2 = (CellStub) testing2.getCell(2,2);
        CellStub c = (CellStub) bN.std(c2,false);

        assertEquals(2.0, c.getInfo());
    }

    @Test
    @DisplayName("Test var with value")
    void testVarVal() {
        CellStub c2 = (CellStub) testing2.getCell(2,2);
        CellStub c = (CellStub) bN.var(c2,false);

        assertEquals(4.0, c.getInfo());
    }

    @Test
    @DisplayName("Test covar with value")
    void testCovarVal() {

        bMIX.ModifyBlock(10.0);
        bN.ModifyBlock(10.0);

        CellStub c = (CellStub) bMIX.getCell(4,4);
        CellStub c2 = (CellStub) bN.getCell(4,4);

        c = (CellStub) c.changeValue(190.0);
        c2 = (CellStub) c2.changeValue(1.0);

        CellStub c3 = (CellStub) bMIX.getCell(0,0);
        CellStub c4 = (CellStub) bN.getCell(0,0);

        c3 = (CellStub) c3.changeValue(230.0);
        c4 = (CellStub) c4.changeValue(2.0);

        assertEquals(-129.50, round((double) bN.covar(bMIX, bT.getCell(0,0), false).getInfo(),2));
    }

    @Test
    @DisplayName("Test cpearson with std equals to 0")
    void testCPValZ() {
        bMIX.ModifyBlock(10.0);
        bN.ModifyBlock(10.0);

        assertEquals(1.0, bN.CPearson(bMIX, bT.getCell(0,0), false).getInfo());
    }

    @Test
    @DisplayName("Test cpearson with std")
    void testCPVal() {
        bMIX.ModifyBlock(10.0);
        bN.ModifyBlock(10.0);

        CellStub c = (CellStub) bMIX.getCell(4,4);
        CellStub c2 = (CellStub) bN.getCell(4,4);

        c = (CellStub) c.changeValue(190.0);
        c2 = (CellStub) c2.changeValue(1.0);

        CellStub c3 = (CellStub) bMIX.getCell(0,0);
        CellStub c4 = (CellStub) bN.getCell(0,0);

        c3 = (CellStub) c3.changeValue(230.0);
        c4 = (CellStub) c4.changeValue(2.0);

        assertEquals(-1.0, round( (double) bN.CPearson(bMIX, bT.getCell(0,0), false).getInfo(),4));
    }
}
