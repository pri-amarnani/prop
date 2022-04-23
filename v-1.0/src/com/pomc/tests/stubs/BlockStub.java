package com.pomc.tests.stubs;

import com.pomc.classes.Block;
import com.pomc.classes.Cell;

import java.time.LocalDate;
import java.util.*;

//import java.util.Vector;

public class BlockStub extends Block {

    public BlockStub(Cell[][] b, Cell ul, Cell dr) {
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

