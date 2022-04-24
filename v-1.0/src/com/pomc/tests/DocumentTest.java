package com.pomc.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.pomc.classes.Document;
import com.pomc.classes.Sheet;
import com.pomc.tests.stubs.SheetStub;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Vector;


public class DocumentTest {

    private Document doc;

    @BeforeEach
    public void initialize() throws Exception {
        doc = new Document("testDoc");
    }

    @Test
    @DisplayName("Should get the title of the document")
    public void testEqualTitle()
    {
        assertEquals("testDoc",doc.getTitle());
    }
    @Test
    @DisplayName("Should change the title of the document")
    public void testSetTitle()
    {
        doc.setTitle("testDoc2");
        assertEquals("testDoc2",doc.getTitle());
    }
    @Test
    @DisplayName("Should add a new Sheet to the Document")
    public void testAddSheet()
    {
       doc.createSheet("Sheet1");
       assertEquals("Sheet1",doc.getDocSheets().lastElement().getTitle());
    }



}
