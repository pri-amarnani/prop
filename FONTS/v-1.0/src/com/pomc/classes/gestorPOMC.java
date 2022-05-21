package com.pomc.classes;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.*;

public class gestorPOMC {

    public void writePOMC(String location, Vector<Sheet> sh_t, String tempName) throws IOException {
        File check = new File(location);
        if (check.isDirectory()) {
            location = location + "/" + tempName + ".pomc";
        }
        BufferedWriter file = new BufferedWriter(new FileWriter(location));
        file.write(sh_t.size() + "\n");
        int u = 0;

        while (u < sh_t.size()) {
            Sheet sh = sh_t.elementAt(u);
            file.write('"' + sh.getTitle() + '"' + ';' + sh.getNumRows() + ";" + sh.getNumCols() + "\n");

            for (int i = 0; i < sh.getNumRows(); ++i) {
                boolean first = true;
                for (int j = 0; j < sh.getNumCols(); ++j) {
                    if (!first) file.write(";");

                    if (sh.getCell(i, j).getClass() == ReferencedCell.class) {
                        file.write(sh.getCell(i, j).getInfo().toString());

                        ReferencedCell c = (ReferencedCell) sh.getCell(i, j);
                        Map.Entry<String, Vector<Cell>> ref = c.getRefInfo();
                        Vector<Cell> v = ref.getValue();

                        for (int k = 0; k < v.size(); ++k) {
                            file.write("(" + v.elementAt(k).getRow() + "," + v.elementAt(k).getColumn() + ")");
                        }
                    } else if (sh.getCell(i, j).getClass() == TextCell.class) {
                        file.write('"' + (String) sh.getCell(i, j).getInfo() + '"');
                    } else {
                        if (sh.getCell(i, j).getInfo() == null) file.write("null");
                        else file.write(sh.getCell(i, j).getInfo().toString());
                    }

                    if (first) first = false;
                }
                file.write("\n");
            }
            file.write("\n");
            ++u;
        }

        file.close();
    }

    public Vector<Sheet> readPOMC(String location) throws IOException {
        Vector<Sheet> res = new Vector<>();

        BufferedReader file = new BufferedReader(new FileReader(location));
        String row;
        int n_sh = 0;

        if ((row = file.readLine()) != null) {
            n_sh = Integer.parseInt(row);
        }

        int curr = 0;
        while (curr < n_sh) {
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

            Cell[][] sh = new Cell[rows][cols];
            int i_aux = 0;

            while ((row = file.readLine()) != null && i_aux < rows) {

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
                    if (i == row_doc.size() && row_doc.size() < cols) {
                        sh[i_aux][i] = new NumCell(i_aux, i, null);
                    } else if (!Objects.equals(row_doc.elementAt(i), "")) {
                        Object aux = Parse(row_doc.elementAt(i));
                        if (aux.getClass() == Double.class) {
                            sh[i_aux][i] = new NumCell(i_aux, i, (Double) aux);
                        } else if (aux.getClass() == LocalDate.class) {
                            sh[i_aux][i] = new DateCell(i_aux, i, (LocalDate) aux);
                        } else {
                            sh[i_aux][i] = new TextCell(i_aux, i, (String) aux);
                        }
                    } else {
                        sh[i_aux][i] = new NumCell(i_aux, i, null);
                    }
                }
                row_doc = new Vector<>();
                ++i_aux;
            }

            for (int i = 0; i < sh.length; ++i) {
                for (int j = 0; j < sh[0].length; ++j) {
                    if (sh[i][j].getClass() == TextCell.class) {
                        String info = (String) sh[i][j].getInfo();

                        if (info.charAt(0) == '=') {
                            String op = "";
                            int k = 1;

                            while (k < info.length() && info.charAt(k) != '(') {
                                op += info.charAt(k);
                                ++k;
                            }

                            Vector<Cell> r = new Vector<>();

                            for (k = k + 1; k < info.length(); k += 2) {
                                String x = "";

                                while (k < info.length() && info.charAt(k) != ',') {
                                    x += info.charAt(k);
                                    ++k;
                                }
                                ++k;

                                String y = "";
                                while (k < info.length() && info.charAt(k) != ')') {
                                    y += info.charAt(k);
                                    ++k;
                                }
                                r.add(sh[Integer.parseInt(x)][Integer.parseInt(y)]);
                            }


                            ReferencedCell rc = new ReferencedCell(i, j, op);

                            // random value
                            rc.setContent(4.0);

                            Map.Entry<String, Vector<Cell>> ent = new AbstractMap.SimpleEntry<>(op, r);
                            rc.setRefInfo(ent);

                            for (int u = 0; u < r.size(); ++u) {
                                sh[r.elementAt(u).getRow()][r.elementAt(u).getColumn()].AddRef(rc);
                            }

                            sh[i][j] = rc;
                        }
                    }
                }
            }

            for (int i = 0; i < sh.length; ++i) {
                for (int j = 0; j < sh[0].length; ++j) {
                    sh[i][j].updateRefs();
                }
            }

            Sheet s = new Sheet(sh, title);
            res.add(s);

            atributs.clear();
            ++curr;
        }

        file.close();

        return res;
    }

    public boolean exists(String path) {
        return new File(path).canRead();
    }

    public boolean deletePOMC(String path) {
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

    // testing purpose
    public static void main(String[] args) throws IOException {
        gestorPOMC g = new gestorPOMC();

        Cell [][] b = new Cell[10][10];
        for(int i = 0; i < 10; ++i){
            for(int j = 0; j < 10; ++j){
                if (i+j == 0) b[i][j] = new NumCell(i, j, null);
                else if (i + j >= 5)  {
                    ReferencedCell r = new ReferencedCell(1,1,"=sum");

                    NumCell n= new NumCell(2,2,3.0);
                    NumCell n2= new NumCell(1,2,3.0);

                    Vector<Cell> v= new Vector<>();
                    v.add(n);
                    v.add(n2);

                    Map.Entry<String, Vector<Cell>> re = new AbstractMap.SimpleEntry<>("sum", v);
                    r.setRefInfo(re);

                    b[i][j] = r;
                }

                else if (i + j < 5) {
                    b[i][j] = new NumCell(i, j, 3.0);
                }
            }
        }

        Vector<Sheet> aux = new Vector<>();
        Sheet sheet = new Sheet( b ,"hoja");
        aux.add(sheet);
        aux.add(sheet);
        g.writePOMC("/home/mark/Desktop", aux, "test_write");

        Vector<Sheet> s_t = g.readPOMC("/home/mark/Desktop/test_write.pomc");

        for (int u = 0; u < s_t.size(); ++u) {
            Sheet s = s_t.elementAt(u);

            for (int i = 0; i < s.getNumRows(); ++i) {
                for (int j = 0; j < s.getNumCols(); ++j) {
                    if (s.getCell(i, j).getClass() == ReferencedCell.class)
                        System.out.print(s.getCell(i, j).getContent() + " ");
                    else System.out.print(s.getCell(i, j).getInfo() + " ");
                }
                System.out.println();
            }

            System.out.println();
            System.out.println();
        }
    }

}

