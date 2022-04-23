package com.pomc.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.pomc.classes.Document;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


public class DocumentTest {

    private Document doc;

    @BeforeEach
    public void initialize() throws Exception {
        doc = new Document("testDoc");
    }

    @Test
    @DisplayName("Should get the title of the document")
    public void testAddSheet()
    {
        assertEquals(doc.getTitle(),"testDoc");
    }
    @Test
    @DisplayName("Should change the title of the document")
    public void testAddSheet()
    {
        doc.setTitle("testDoc2");
        assertEquals(doc.getTitle(),"testDoc2");
    }
    @Test
    @DisplayName("Should add a new Sheet to the Document")
    public void testAddSheet()
    {
       doc.createSheet("Sheet1");
       assertEquals(doc.getDocSheets().lastElement(),"Sheet1");
    }
}
