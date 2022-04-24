package com.pomc.classes;

import java.time.LocalDate;
import java.util.*;

public class Block {
    Cell [][] block;
    Cell ul;
    Cell dr;
    int size_r;
    int size_c;

    public Block (Cell [][] b, Cell ul, Cell dr) {
        this.block = b;

        this.ul = ul;
        this.dr = dr;

        this.size_r = this.block.length;
        this.size_c = this.block[0].length;
    }

    public Cell getCell (int i, int j) {
        return this.block[i][j];
    }

    public int number_rows() {
        return this.size_r;
    }

    public int number_cols() {
        return this.size_c;
    }

    public void setCell(int i, int j, Cell c) {
        this.block[i][j] = c;
    }

    public boolean allDouble() {
        for (int i = 0; i < this.block[0].length; ++i) {
            for (int j = 0; j < this.block[0].length; ++j)
                if (!this.block[i][j].isNum()) return false;
        }

        return true;
    }

    public boolean allText() {
        for (int i = 0; i < this.block[0].length; ++i) {
            for (int j = 0; j < this.block[0].length; ++j)
                if (!this.block[i][j].isText()) return false;
        }

        return true;
    }

    public boolean allDate() {
        LocalDate d = null;
        for (int i = 0; i < this.block[0].length; ++i) {
            for (int j = 0; j < this.block[0].length; ++j)
                if (!this.block[i][j].isDate()) return false;
        }
        return true;
    }

    public boolean isEqual(Block b) {

        if (b.number_cols() != this.block[0].length || b.number_rows() != this.block.length) return false;

        for (int i = 0; i < b.number_rows(); ++i) {
            for (int j = 0; j < b.number_cols(); ++j) {
                if (!b.getCell(i,j).getInfo().equals(this.block[i][j].getInfo())) return false;
            }
        }
        return true;
    }

    public void CopyB () {
        // copy to clipboard
    }

    //copy values from one block to another
    public void ref(Block b, Boolean ref) {
        for (int i = 0; i < this.block.length; ++i) {
            for (int j = 0; j < this.block[0].length; ++j) {

                if (this.block[i][j].isNum()) b.getCell(i,j).changeValue(this.block[i][j].getInfo());
                else {
                    Cell n = (Cell) b.getCell(i, j).changeValue(this.block[i][j].getInfo());
                    this.block[i][j] = n;
                }

                if (ref) {
                    Vector<Cell> s = new Vector<>(1);
                    s.add(this.block[i][j]);

                    Map.Entry<String, Vector<Cell>> r = new AbstractMap.SimpleEntry<>("EQUAL", s);
                    b.getCell(i, j).setRefInfo(r);
                }
            }
        }
    }

    public void ModifyBlock(Object n) {
        System.out.println(n.getClass());
        for (Cell[] cells : block) {
            for (int j = 0; j < block[0].length; ++j) {

                if (n.getClass() == cells[j].getInfo().getClass()) {
                    cells[j].changeValue(n);
                }
                else {
                    System.out.println(cells[j].getInfo().getClass());
                    Cell nw = (Cell) cells[j].changeValue(n);
                    cells[j] = nw;
                }
            }
        }
    }

    public void SortBlock (Block col, int n_col, String criteria) {
        if (Objects.equals(criteria, "<")) {
            if (col.allDouble()) {
                Arrays.sort(block, (a, b) -> Double.compare((double) a[n_col].getInfo(), (double) b[n_col].getInfo()));
            }
            else if (col.allText()) {
                Arrays.sort(block, (a, b) -> ((String) a[0].getInfo()).compareTo((String) b[0].getInfo()));
            }
        }

        // >
        else {
            if (col.allDouble()) {
                Arrays.sort(block, (a, b) -> Double.compare((double) b[n_col].getInfo(), (double) a[n_col].getInfo()));
            }
            else if (col.allText()) {
                Arrays.sort(block, (a, b) -> ((String) b[0].getInfo()).compareTo((String) a[0].getInfo()));
            }
        }
    }

    public Cell find (Object n) {
        for (Cell[] cells : block) {
            for (int j = 0; j < block[0].length; ++j) {
                if (Objects.equals(cells[j].getInfo(), n)) return cells[j];
            }
        }
        return null;
    }

    public void findAndReplace (Object n, Object change) {
        for (Cell[] cells : block) {
            for (int j = 0; j < block[0].length; ++j) {
                if (Objects.equals(cells[j].getInfo(), n)) {
                    Cell nw = (Cell) cells[j].changeValue(cells[j].getInfo());
                    cells[j] = nw;
                }
            }
        }
    }

    public void floor (Block b, Boolean ref) {
        for (int i = 0; i < this.block.length; ++i) {
            for (int j = 0; j < this.block[0].length; ++j) {

                Cell n = (Cell) b.getCell(i,j).changeValue(this.block[i][j].getInfo());
                b.setCell(i,j,n);

                if (ref) {
                    Vector<Cell> s = new Vector<>(1);
                    s.add(this.block[i][j]);

                    Map.Entry<String, Vector<Cell>> r = new AbstractMap.SimpleEntry<>("FLOOR", s);

                    b.getCell(i,j).setRefInfo(r);
                }
            }
        }
    }

    public void convert (Block b, Boolean ref) {

    }

    public void sum (Block b1, Block b2, Boolean ref) {
        for (int i = 0; i < this.block.length; ++i) {
            for (int j = 0; j < this.block[0].length; ++j) {

                Cell n = (Cell) b2.getCell(i, j).changeValue((double) this.block[i][j].getInfo() + (double) b1.getCell(i, j).getInfo());
                b2.setCell(i,j,n);

                if (ref) {

                    Vector<Cell> s = new Vector<>(2);
                    s.add(this.block[i][j]);
                    s.add(b1.getCell(i, j));

                    Map.Entry<String, Vector<Cell>> r = new AbstractMap.SimpleEntry<>("SUM", s);


                    b2.getCell(i, j).setRefInfo(r);
                }
            }
        }
    }

    public void mult (Block b1, Block b2, Boolean ref) {
        for (int i = 0; i < this.block.length; ++i) {
            for (int j = 0; j < this.block[0].length; ++j) {
                Cell n = (Cell) b2.getCell(i, j).changeValue((double) this.block[i][j].getInfo() + (double) b1.getCell(i, j).getInfo());
                b2.setCell(i,j,n);

                if (ref) {

                    Vector<Cell> s = new Vector<>(2);
                    s.add(this.block[i][j]);
                    s.add(b1.getCell(i, j));

                    Map.Entry<String, Vector<Cell>> r = new AbstractMap.SimpleEntry<>("MULT", s);


                    b2.getCell(i, j).setRefInfo(r);
                }
            }
        }
    }

    public void div (Block b1, Block b2, Boolean ref) {
        for (int i = 0; i < this.block.length; ++i) {
            for (int j = 0; j < this.block[0].length; ++j) {
                Cell n = (Cell) b2.getCell(i, j).changeValue((double) this.block[i][j].getInfo() + (double) b1.getCell(i, j).getInfo());
                b2.setCell(i,j,n);

                if (ref) {

                    Vector<Cell> s = new Vector<>(2);
                    s.add(this.block[i][j]);
                    s.add(b1.getCell(i, j));

                    Map.Entry<String, Vector<Cell>> r = new AbstractMap.SimpleEntry<>("DIV", s);


                    b2.getCell(i, j).setRefInfo(r);
                }
            }
        }
    }

    public void substract (Block b1, Block b2, Boolean ref) {
        for (int i = 0; i < this.block.length; ++i) {
            for (int j = 0; j < this.block[0].length; ++j) {
                Cell n = (Cell) b2.getCell(i, j).changeValue((double) this.block[i][j].getInfo() + (double) b1.getCell(i, j).getInfo());
                b2.setCell(i,j,n);

                if (ref) {

                    Vector<Cell> s = new Vector<>(2);
                    s.add(this.block[i][j]);
                    s.add(b1.getCell(i, j));

                    Map.Entry<String, Vector<Cell>> r = new AbstractMap.SimpleEntry<>("SUB", s);


                    b2.getCell(i, j).setRefInfo(r);
                }
            }
        }
    }

    //FINISH
    public void extract (Block b, Boolean ref) {

    }

    //FINISH
    public void dayOfTheWeek (Block b, Boolean ref) {
        for (int i = 0; i < this.block.length; ++i) {
            for (int j = 0; j < this.block[0].length; ++j) {
                LocalDate aux = (LocalDate) getCell(i,j).getInfo();

            }
        }

    }

    // criteria == mayus or criteria == minus
    public void replaceWithCriteriaText (String criteria) {
        for (Cell[] cells : this.block) {
            for (int j = 0; j < this.block[0].length; ++j) {
                String s = (String) cells[j].getInfo();
                if (Objects.equals(criteria, "ALL CAPS")) {
                    Cell n = (Cell) cells[j].changeValue(s.toUpperCase(Locale.ROOT));
                    cells[j] = n;
                }

                else if (Objects.equals(criteria, "ALL NOT CAPS")) {
                    Cell n = (Cell) cells[j].changeValue(s.toLowerCase(Locale.ROOT));
                    cells[j] = n;
                }

                else if (Objects.equals(criteria, "CAP_FIRST_LETTER")) {
                    Cell n = (Cell) cells[j].changeValue(s.substring(0, 1).toUpperCase(Locale.ROOT)
                            + s.substring(1));
                    cells[j] = n;
                }
            }
        }
    }

    // val = true means we want to put value in cell, else just show value.
    public Cell mean (Cell c, Boolean ref) {
        double sum = 0;
        Vector<Cell> s = new Vector<>();
        for (Cell[] cells : this.block) {
            for (int j = 0; j < this.block[0].length; ++j) {
                sum += (double) cells[j].getInfo();
                s.add(cells[j]);
            }
        }

        if (c.isNum()) c.changeValue(sum/s.size());
        else c = (Cell) c.changeValue(sum/s.size());

        if (ref) {
            Map.Entry<String, Vector<Cell>> r = new AbstractMap.SimpleEntry<>("MEAN", s);
            c.setRefInfo(r);
        }

        return c;
    }

    public Cell median (Cell c, Boolean ref) {
        double [] arr = new double[this.block.length*this.block[0].length];
        Vector<Cell> s = new Vector<>();
        int ii = 0;
        for (int i = 0; i < this.block.length; ++i) {
            for (int j = 0; j < this.block[0].length; ++j) {
                arr[ii] = (Double) this.block[i][j].getInfo();
                s.add(this.block[i][j]);

                ii += 1;
            }
        }

        Arrays.sort(arr);

        if (c.isNum()) c.changeValue(arr[arr.length/2]);
        else c = (Cell) c.changeValue(arr[arr.length/2]);

        if (ref) {
            Map.Entry<String, Vector<Cell>> r = new AbstractMap.SimpleEntry<>("MEDIAN", s);
            c.setRefInfo(r);
        }

        return c;
    }

    public Cell var (Cell c, Boolean ref) {
        double d = Math.pow((double) this.std(c, false).getInfo(), 2);

        if (c.isNum()) c.changeValue(d);
        else c = (Cell) c.changeValue(d);

        if (ref) {
            Vector<Cell> s = new Vector<>();

            for (Cell[] cells : this.block) {
                s.addAll(Arrays.asList(cells).subList(0, this.block[0].length));
            }

            Map.Entry<String, Vector<Cell>> r = new AbstractMap.SimpleEntry<>("VAR", s);
            c.setRefInfo(r);
        }
        return c;
    }

    public Cell covar (Block b, Cell c, Boolean ref) {
        double mean1 = (double) this.mean(c,false).getInfo();
        double mean2 = (double) b.mean(c,false).getInfo();

        double [] x = new double[this.block.length*this.block[0].length];
        double [] y = new double[this.block.length*this.block[0].length];

        int ii = 0;
        int jj = 0;

        Vector<Cell> s = new Vector<>();

        for (int i = 0; i < this.block.length; ++i) {
            for (int j = 0; j < this.block[0].length; ++j) {
                System.out.println((double) b.getCell(i,j).getInfo());


                x[ii] = (double) this.block[i][j].getInfo();
                s.add(this.block[i][j]);
                y[jj] = (double) b.getCell(i,j).getInfo();
                s.add(b.getCell(i,j));

                ii += 1;
                jj += 1;
            }
        }

        double sum = 0;

        for (int i = 0; i < x.length; ++i) {
            sum += (x[i] - mean1)*(y[i] - mean2);
        }

        double cov = sum/(x.length-1);

        if (c.isNum()) c.changeValue(cov);
        else c = (Cell) c.changeValue(cov);

        if (ref) {
            Map.Entry<String, Vector<Cell>> r = new AbstractMap.SimpleEntry<>("COVAR", s);
            c.setRefInfo(r);
        }

        return c;
    }

    public Cell std (Cell c, Boolean ref) {
        double sum = 0, std = 0;
        double [] arr = new double[this.block.length*this.block[0].length];
        Vector<Cell> s = new Vector<>();
        int ii = 0;
        for (int i = 0; i < this.block.length; ++i) {
            for (int j = 0; j < this.block[0].length; ++j) {
                arr[ii] = (double) this.block[i][j].getInfo();
                s.add(this.block[i][j]);
                sum += (double) this.block[i][j].getInfo();

                ii += 1;
            }
        }

        double mean = sum/arr.length;

        for (double num: arr) {
            std += Math.pow(num - mean, 2);
        }
        double stdd = Math.sqrt(std/arr.length);

        if (c.isNum()) c.changeValue(stdd);
        else c = (Cell) c.changeValue(stdd);

        if (ref) {
            Map.Entry<String, Vector<Cell>> r = new AbstractMap.SimpleEntry<>("VAR", s);
            c.setRefInfo(r);
        }

        return c;
    }

    public Cell CPearson (Block b, Cell c, Boolean ref, Boolean val) {
        double d1 = (double) b.std(c,false).getInfo();
        double d2 = (double) this.std(c, false).getInfo();

        double cp;
        if (d1 == 0 || d2 == 0) cp = 1;

        else cp = (double) this.covar(b,c,false).getInfo()/(d1*d2);

        if (c.isNum()) c.changeValue(cp);
        else c = (Cell) c.changeValue(cp);

        if (ref) {

            Vector<Cell> s = new Vector<>();

            for (int i = 0; i < this.block.length; ++i) {
                for (int j = 0; j < this.block[0].length; ++j) {
                    s.add(this.block[i][j]);
                    s.add(b.getCell(i,j));
                }
            }

            Map.Entry<String, Vector<Cell>> r = new AbstractMap.SimpleEntry<>("CP", s);
            c.setRefInfo(r);
        }

        return c;
    }
}
