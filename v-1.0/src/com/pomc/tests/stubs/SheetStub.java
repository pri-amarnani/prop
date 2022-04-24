package com.pomc.tests.stubs;

import com.pomc.classes.Sheet;

public class SheetStub extends Sheet {
    private String title;

    public SheetStub (String t) {
        super(t);
        this.title = t;
    }

    public String getTitle() {
        return title;
    }
}
