package com.pomc.tests.stubs;

import com.pomc.classes.Block;
import com.pomc.classes.Cell;
import com.pomc.tests.stubs.CellStub;

import java.time.LocalDate;
import java.util.*;

//import java.util.Vector;

public class BlockStub extends Block {
    CellStub [][] block;
    CellStub ul;
    CellStub dr;
    int size_r;
    int size_c;

    public BlockStub(CellStub[][] b, CellStub ul, CellStub dr) {
        super(b, ul, dr);
    }


    @Override
    public boolean allDouble(){
        return true;
    }

    @Override
    public boolean allText()  {
        return true;
    }

    @Override
    public boolean allDate() {
        return true;
    }


}

