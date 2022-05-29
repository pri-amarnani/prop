package com.pomc.classes;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.Objects;
import java.util.Vector;

public class gestorTXT {

    public void writeTXT(String path, Sheet sh) throws IOException {
        BufferedWriter file = new BufferedWriter(new FileWriter(path));
        file.write('"' + sh.getTitle() + '"' + ';' + sh.getNumRows() + ";" + sh.getNumCols() + "\n");

        for (int i = 0; i < sh.getNumRows(); ++i) {
            boolean first = true;
            for (int j = 0; j < sh.getNumCols(); ++j) {
                if (!first) file.write(";");

                if (sh.getCell(i,j).getClass() == ReferencedCell.class) {
                    file.write(sh.getCell(i,j).getContent().toString());
                }

                else if (sh.getCell(i,j).getClass() == TextCell.class) {
                    file.write('"' + (String) sh.getCell(i,j).getInfo() + '"');
                }

                else {
                    if (sh.getCell(i,j).getInfo() == null || Objects.equals(sh.getCell(i, j).getInfo(), "")) file.write("null");
                    else file.write(sh.getCell(i,j).getInfo().toString());
                }

                if (first) first = false;
            }
            file.write("\n");
        }

        file.close();
    }

    public Sheet readTXT(String location) throws IOException {   //not sure
        Vector<Vector<String>> sheet = new Vector<>();

        BufferedReader file = new BufferedReader(new FileReader(location));
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

        Vector<String> row_doc = new Vector<>();
        Cell [][] sh = new Cell[rows][cols];
        int i_aux = 0;

        while ((row = file.readLine()) != null) {

            boolean quote_mark_o = false;
            boolean quote_mark_c = false;

            String new_row = "";

            for (int i = 0; i < row.length(); ++i) {
                if (row.charAt(i) == '"' && quote_mark_c == quote_mark_o) {quote_mark_o = true; quote_mark_c = false; new_row += (row.charAt(i));}
                else if (quote_mark_o && !quote_mark_c && row.charAt(i) == ';') new_row += ('~');
                else if (row.charAt(i) == '"' && quote_mark_o && !quote_mark_c) {quote_mark_c = true; new_row += (row.charAt(i));}
                else new_row += (row.charAt(i));

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
                if (i == row_doc.size() && row_doc.size() < cols) {
                    sh[i_aux][i] = new NumCell(i_aux,i,null);
                }

                else if (!Objects.equals(row_doc.elementAt(i), "null")) {
                    Object aux = Parse(row_doc.elementAt(i));
                    if (aux.getClass() == Double.class) {
                        sh[i_aux][i] = new NumCell(i_aux,i,(Double) aux);
                    }

                    else if (aux.getClass() == LocalDate.class) {
                        sh[i_aux][i] = new DateCell(i_aux,i,(LocalDate) aux);
                    }

                    else {
                        sh[i_aux][i] = new TextCell(i_aux,i,(String) aux);
                    }
                }

                else {
                    sh[i_aux][i] = new NumCell(i_aux,i,null);
                }
            }
            row_doc = new Vector<>();
            ++i_aux;
        }

        Sheet s = new Sheet(sh,title);

        atributs.clear();
        file.close();

        return s;
    }

    public boolean exists(String path) {
        return new File(path).canRead();
    }

    public boolean deleteTXT(String path) {
        if (exists(path)) {
            return new File(path).delete();
        } else {
            return false;
        }
    }

    public static Object Parse(String input) {
        try{
            Double isDouble = Double.parseDouble(input);
            return isDouble;
        } catch (NumberFormatException e) {
            try {
                LocalDate isDate = LocalDate.parse(input);
                return isDate;
            }
            catch (DateTimeParseException d) {
                return input;
            }
        }
    }
    public static String AntiParse(Object o) {
        System.out.println(o);
        if (o.getClass() == Double.class) return String.valueOf(o);
        else if (o.getClass()== LocalDate.class) return o.toString();
        else return (String) o;
    }
}

