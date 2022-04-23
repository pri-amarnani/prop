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
        for (int i = 0; i < this.block[0].length; ++i) {
            for (int j = 0; j < this.block[0].length; ++j)
                if (!this.block[i][j].isDate()) return false;
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
                b.getCell(i,j).changeValue(this.block[i][j].getInfo());

                if (ref) {
                    Vector<Cell> s = new Vector<>(1);
                    s.add(this.block[i][j]);

                    Map.Entry<String, Vector<Cell>> r = new AbstractMap.SimpleEntry<>("EQUAL", s);
                    b.getCell(i, j).setRefInfo(r);
                }
            }
        }
    }

    public void ModifyBlock(double n) {
        for (Cell[] cells : block) {
            for (int j = 0; j < block[0].length; ++j) {
                if (cells[j].isNum()) cells[j].changeValue(n);
            }
        }
    }

    public void ModifyBlock(String n) {
        for (Cell[] cells : block) {
            for (int j = 0; j < block[0].length; ++j) {
                if (cells[j].isText()) cells[j].changeValue(n);
            }
        }
    }

    public void ModifyBlock(LocalDate ld) {
        for (Cell[] cells : block) {
            for (int j = 0; j < block[0].length; ++j) {
                if (cells[j].isDate()) cells[j].changeValue(ld);
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

    public Cell find (double n) {
        for (Cell[] cells : block) {
            for (int j = 0; j < block[0].length; ++j) {
                if (Objects.equals(cells[j].getInfo(), n)) return cells[j];
            }
        }
        return null;
    }

    public Cell find (String n) {
        for (Cell[] cells : block) {
            for (int j = 0; j < block[0].length; ++j) {
                if (Objects.equals(cells[j].getInfo(), n)) return cells[j];
            }
        }
        return null;
    }

    public Cell find (LocalDate ld) {
        for (Cell[] cells : block) {
            for (int j = 0; j < block[0].length; ++j) {
                if (cells[j].getInfo() == ld) return cells[j];
            }
        }
        return null;
    }

    public void findAndReplace (double n) {
        for (Cell[] cells : block) {
            for (int j = 0; j < block[0].length; ++j) {
                if (Objects.equals(cells[j].getInfo(), n)) cells[j].changeValue(n);
            }
        }
    }

    public void findAndReplace (String n) {
        for (Cell[] cells : block) {
            for (int j = 0; j < block[0].length; ++j) {
                if (Objects.equals(cells[j].getInfo(), n)) cells[j].changeValue(n);
            }
        }
    }

    public void findAndReplace (LocalDate ld) {
        for (Cell[] cells : block) {
            for (int j = 0; j < block[0].length; ++j) {
                if (cells[j].getInfo() == ld) cells[j].changeValue(ld);
            }
        }
    }

    public void floor (Block b, Boolean ref) {
        for (int i = 0; i < this.block.length; ++i) {
            for (int j = 0; j < this.block[0].length; ++j) {
                b.getCell(i,j).changeValue(Math.floor((double) block[i][j].getInfo()));

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
                b2.getCell(i, j).changeValue((double) this.block[i][j].getInfo() + (double) b1.getCell(i, j).getInfo());

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
                b2.getCell(i, j).changeValue((double) this.block[i][j].getInfo() * (double) b1.getCell(i, j).getInfo());

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
                b2.getCell(i,j).changeValue((double) this.block[i][j].getInfo() / (double) b1.getCell(i,j).getInfo());

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
                b2.getCell(i,j).changeValue((double) this.block[i][j].getInfo() - (double) b1.getCell(i,j).getInfo());

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
                    cells[j].changeValue(s.toUpperCase(Locale.ROOT));
                }

                else if (Objects.equals(criteria, "ALL NOT CAPS")) {
                    cells[j].changeValue(s.toLowerCase(Locale.ROOT));
                }

                else if (Objects.equals(criteria, "CAP_FIRST_LETTER")) {
                    cells[j].changeValue(s.substring(0, 1).toUpperCase(Locale.ROOT)
                            + s.substring(1));
                }
            }
        }
    }

    // val = true means we want to put value in cell, else just show value.
    public double mean (Cell c, Boolean ref, Boolean val) {
        double sum = 0;
        Vector<Cell> s = new Vector<>();
        for (Cell[] cells : this.block) {
            for (int j = 0; j < this.block[0].length; ++j) {
                sum += (double) cells[j].getInfo();
                s.add(cells[j]);
            }
        }

        if (val) {
            c.changeValue(sum/s.size());
        }

        if (ref && val) {
            Map.Entry<String, Vector<Cell>> r = new AbstractMap.SimpleEntry<>("MEAN", s);
            c.setRefInfo(r);
        }

        return sum/(this.block.length*this.block[0].length);
    }

    public double median (Cell c, Boolean ref, Boolean val) {
        double [] arr = new double[this.block.length*this.block[0].length];
        Vector<Cell> s = new Vector<>();

        for (int i = 0; i < this.block.length; ++i) {
            for (int j = 0; j < this.block[0].length; ++j) {
                arr[i+j] = (double) this.block[i][j].getInfo();
                s.add(this.block[i][j]);
            }
        }

        Arrays.sort(arr);

        if (val) {
            c.changeValue(arr[arr.length/2]);
        }

        if (ref && val) {
            Map.Entry<String, Vector<Cell>> r = new AbstractMap.SimpleEntry<>("MEDIAN", s);
            c.setRefInfo(r);
        }

        return arr[arr.length/2];
    }

    public double var (Cell c, Boolean ref, Boolean val) {
        double d = Math.pow(this.std(c, false, false), 2);
        if (val) {
            c.changeValue(d);
        }

        if (val && ref) {
            Vector<Cell> s = new Vector<>();

            for (Cell[] cells : this.block) {
                s.addAll(Arrays.asList(cells).subList(0, this.block[0].length));
            }

            Map.Entry<String, Vector<Cell>> r = new AbstractMap.SimpleEntry<>("VAR", s);
            c.setRefInfo(r);
        }
        return d;
    }

    public double covar (Block b, Cell c, Boolean ref, Boolean val) {
        double mean1 = this.mean(c,false,false);
        double mean2 = b.mean(c,false,false);

        double [] x = new double[this.block.length*this.block[0].length];
        double [] y = new double[this.block.length*this.block[0].length];
        Vector<Cell> s = new Vector<>();

        for (int i = 0; i < this.block.length; ++i) {
            for (int j = 0; j < this.block[0].length; ++j) {
                x[i+j] = (double) this.block[i][j].getInfo();
                s.add(this.block[i][j]);
                y[i+j] = (double) b.getCell(i,j).getInfo();
                s.add(b.getCell(i,j));
            }
        }

        double sum = 0;

        for (int i = 0; i < x.length; ++i) {
            sum += (x[i] - mean1)*(y[i] - mean2);
        }

        double cov = sum/x.length;

        if (val) {
            c.changeValue(cov);
        }

        if (val && ref) {
            Map.Entry<String, Vector<Cell>> r = new AbstractMap.SimpleEntry<>("COVAR", s);
            c.setRefInfo(r);
        }

        return cov;
    }

    public double std (Cell c, Boolean ref, Boolean val) {
        double sum = 0, std = 0;
        double [] arr = new double[this.block.length*this.block[0].length];
        Vector<Cell> s = new Vector<>();
        for (int i = 0; i < this.block.length; ++i) {
            for (int j = 0; j < this.block[0].length; ++j) {
                arr[i+j] = (double) this.block[i][j].getInfo();
                s.add(this.block[i][j]);
                sum += (double) this.block[i][j].getInfo();
            }
        }

        double mean = sum/arr.length;

        for (double num: arr) {
            std += Math.pow(num - mean, 2);
        }
        double stdd = Math.sqrt(std/arr.length);

        if (val) {
            c.changeValue(stdd);
        }

        if (val && ref) {
            Map.Entry<String, Vector<Cell>> r = new AbstractMap.SimpleEntry<>("VAR", s);
            c.setRefInfo(r);
        }

        return stdd;
    }

    public double CPearson (Block b, Cell c, Boolean ref, Boolean val) {
        double cp = this.covar(b,c,false, false)/(b.std(c,false,false)*this.std(c,false,false));

        if (val) {
            c.changeValue(cp);
        }

        if (val && ref) {

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

        return cp;
    }

    public static void main(String[] args) {

    }
}
