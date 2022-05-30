package com.pomc.classes;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Vector;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

public class ExportPdf {

    public static void exportPDF(String p, String p2) throws IOException, DocumentException {

        BufferedReader file = new BufferedReader(new FileReader(p));
        String row;
        int rows = 0;
        int cols = 0;
        String title = null;

        Vector<String> atributs = new Vector<>();

        if ((row = file.readLine()) != null) {

            atributs.addAll(Arrays.asList(row.split(";")));

            rows = Integer.parseInt(atributs.elementAt(1));
            cols = Integer.parseInt(atributs.elementAt(2));
            title = atributs.elementAt(0);
        }
        String auxx = "";
        for (int i = 1; i < title.length()-1; i++) auxx += title.charAt(i);
        Document document = new Document(PageSize.A1, 25, 10, 25, 25);
        PdfWriter pdfWriter = PdfWriter.getInstance(document, new FileOutputStream(p2));

        document.open();

        Paragraph heading = new Paragraph(auxx,
                FontFactory.getFont(FontFactory.HELVETICA, 20, Font.BOLD, new BaseColor(0, 150, 150)));

        document.add(heading);

        PdfPTable t = new PdfPTable(cols);
        t.setSpacingBefore(25);
        t.setSpacingAfter(25);


        Vector<String> row_doc = new Vector<>();

        while ((row = file.readLine()) != null) {

            boolean quote_mark_o = false;
            boolean quote_mark_c = false;

            String new_row = "";

            for (int i = 0; i < row.length(); ++i) {
                if (row.charAt(i) == '"' && quote_mark_c == quote_mark_o) {
                    quote_mark_o = true;
                    quote_mark_c = false;
                    new_row += (row.charAt(i));
                } else if (quote_mark_o && !quote_mark_c && row.charAt(i) == ';') new_row += ('~');
                else if (row.charAt(i) == '"' && quote_mark_o && !quote_mark_c) {
                    quote_mark_c = true;
                    new_row += (row.charAt(i));
                } else new_row += (row.charAt(i));

            }

            row = new_row;
            row_doc.addAll(Arrays.asList(row.split(";")));

            for (int i = 0; i < row_doc.size(); ++i) {
                if (!Objects.equals(row_doc.elementAt(i), "") && row_doc.elementAt(i).charAt(0) == '"') {
                    String aux = "";
                    for (int j = 0; j < row_doc.elementAt(i).length(); ++j) {
                        if (row_doc.elementAt(i).charAt(j) == '~') aux += ';';
                        else if (row_doc.elementAt(i).charAt(j) != '"') aux += row_doc.elementAt(i).charAt(j);
                    }

                    row_doc.setElementAt(aux, i);
                }
            }



            for (int i = 0; i < cols; ++i) {
                if (row_doc.elementAt(i).equals("null")) {
                    PdfPCell c1 = new PdfPCell(new Phrase(""));
                    t.addCell(c1);
                }
                else {
                    PdfPCell c1 = new PdfPCell(new Phrase(row_doc.elementAt(i)));
                    t.addCell(c1);
                }
            }
            row_doc = new Vector<>();
        }


        atributs.clear();
        file.close();

        document.add(t);

        document.close();
        pdfWriter.close();

    }
}