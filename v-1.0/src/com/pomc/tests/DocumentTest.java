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
        doc = new Document("test","out/production/v-1.0/pomcs");
    }

    @Test
    @DisplayName("Should add a new Sheet to the Document")
    public void testAddSheet()
    {
       doc.createSheet("Sheet1");
       assertEquals(doc.getDocSheets().lastElement(),"Sheet1");
    }
}
