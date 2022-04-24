package com.pomc.classes;

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

                    Map.Entry<String, Vector<Cell>> r = new AbstractMap.SimpleEntry<>("=", s);
                    b.getCell(i, j).setRefInfo(r);
                }
            }
        }
    }

    public void ModifyBlock(Object n) {
        for (Cell[] cells : block) {
            for (int j = 0; j < block[0].length; ++j) {
                Cell nw = (Cell) cells[j].changeValue(n);
                cells[j] = nw;
            }
        }
    }

    public void SortBlock (int n_col, String criteria, String type) {

        if (Objects.equals(criteria, "<")) {
            if (Objects.equals(type, "N")) {
                Arrays.sort(block, (a, b) -> Double.compare((double) a[n_col].getInfo(), (double) b[n_col].getInfo()));
            }
            else if (Objects.equals(type, "T")) {
                Arrays.sort(block, (a, b) -> ((String) a[n_col].getInfo()).compareTo((String) b[n_col].getInfo()));
            }
        }

        // >
        else {
            if (Objects.equals(type, "N")) {
                Arrays.sort(block, (a, b) -> Double.compare((double) b[n_col].getInfo(), (double) a[n_col].getInfo()));
            }
            else if (Objects.equals(type, "T")) {
                Arrays.sort(block, (a, b) -> ((String) b[n_col].getInfo()).compareTo((String) a[n_col].getInfo()));
            }
        }

        for (int i = 0; i < size_r; ++i) {
            for (int j = 0; j < size_c; ++j) {
                this.block[i][j].setRow(i);
                this.block[i][j].setColumn(j);
            }
        }
        ul = this.block[0][0];
        dr = this.block[size_r-1][size_c-1];
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
                    Cell nw = (Cell) cells[j].changeValue(change);
                    cells[j] = nw;
                }
            }
        }
    }

    public void floor (Block b, Boolean ref) {
        for (int i = 0; i < this.block.length; ++i) {
            for (int j = 0; j < this.block[0].length; ++j) {

                if (this.block[i][j].isNum()) {
                    Cell n = (Cell) b.getCell(i, j).changeValue(Math.floor((double) this.block[i][j].getInfo()));
                    ReferencedCell rc = new ReferencedCell(n.getRow(),n.getColumn(),"=floor1");
                    rc.setContent(n.getInfo());
                    b.setCell(i, j, rc);
                    n = null;
                    if (ref) {
                        System.out.println("REF!");
                        Vector<Cell> s = new Vector<>(1);
                        s.add(this.block[i][j]);

                        Map.Entry<String, Vector<Cell>> r = new AbstractMap.SimpleEntry<>("floor", s);

                        b.getCell(i, j).setRefInfo(r);
                    }
                }
            }
        }
    }

    public void convert (Block b, Boolean ref, String from, String to) {
        for (int i = 0; i < b.number_rows(); ++i) {
            for (int j = 0; j < b.number_cols(); ++j) {
                NumCell N = (NumCell) this.block[i][j];

                Cell n = (Cell) b.getCell(i, j).changeValue(N.conversion(from,to));
                b.setCell(i,j,n);

                if (ref) {

                    Vector<Cell> s = new Vector<>(1);
                    s.add(n);
                     Map.Entry<String, Vector<Cell>> r = new AbstractMap.SimpleEntry<>(from + "TO" + to, s);
                     b.getCell(i, j).setRefInfo(r);
                }
            }
        }
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

                    Map.Entry<String, Vector<Cell>> r = new AbstractMap.SimpleEntry<>("sum", s);

                    b2.getCell(i, j).setRefInfo(r);
                }
            }
        }
    }

    public void mult (Block b1, Block b2, Boolean ref) {
        for (int i = 0; i < this.block.length; ++i) {
            for (int j = 0; j < this.block[0].length; ++j) {

                Cell n = (Cell) b2.getCell(i, j).changeValue((double) this.block[i][j].getInfo()*(double) b1.getCell(i, j).getInfo());
                b2.setCell(i,j,n);

                if (ref) {

                    Vector<Cell> s = new Vector<>(2);
                    s.add(this.block[i][j]);
                    s.add(b1.getCell(i, j));

                    Map.Entry<String, Vector<Cell>> r = new AbstractMap.SimpleEntry<>("mult", s);

                    b2.getCell(i, j).setRefInfo(r);
                }
            }
        }
    }

    public void div (Block b1, Block b2, Boolean ref) {
        for (int i = 0; i < this.block.length; ++i) {
            for (int j = 0; j < this.block[0].length; ++j) {
                Cell n = (Cell) b2.getCell(i, j).changeValue((double) this.block[i][j].getInfo() / (double) b1.getCell(i, j).getInfo());
                b2.setCell(i,j,n);

                if (ref) {

                    Vector<Cell> s = new Vector<>(2);
                    s.add(this.block[i][j]);
                    s.add(b1.getCell(i, j));

                    Map.Entry<String, Vector<Cell>> r = new AbstractMap.SimpleEntry<>("div", s);


                    b2.getCell(i, j).setRefInfo(r);
                }
            }
        }
    }

    public void substract (Block b1, Block b2, Boolean ref) {
        for (int i = 0; i < this.block.length; ++i) {
            for (int j = 0; j < this.block[0].length; ++j) {
                Cell n = (Cell) b2.getCell(i, j).changeValue((double) this.block[i][j].getInfo() - (double) b1.getCell(i, j).getInfo());
                b2.setCell(i,j,n);

                if (ref) {
                    Vector<Cell> s = new Vector<>(2);
                    s.add(this.block[i][j]);
                    s.add(b1.getCell(i, j));

                    Map.Entry<String, Vector<Cell>> r = new AbstractMap.SimpleEntry<>("sub", s);
                    b2.getCell(i, j).setRefInfo(r);
                }
            }
        }
    }

    public void extract (Block b, Boolean ref, String ex) {
        for (int i = 0; i < b.number_rows(); ++i) {
            for (int j = 0; j < b.number_cols(); ++j) {
                DateCell N = (DateCell) this.block[i][j];

                Cell n = (Cell) b.getCell(i, j).changeValue(N.extract(ex));
                b.setCell(i,j,n);

                if (ref) {
                    Vector<Cell> s = new Vector<>(1);
                    s.add(n);
                    Map.Entry<String, Vector<Cell>> r = new AbstractMap.SimpleEntry<>(ex, s);
                    b.getCell(i, j).setRefInfo(r);
                }
            }
        }
    }

    //FINISH
    public void dayOfTheWeek (Block b, Boolean ref, String d) {
        for (int i = 0; i < b.number_rows(); ++i) {
            for (int j = 0; j < b.number_cols(); ++j) {
                DateCell N = (DateCell) this.block[i][j];

                Cell n = (Cell) b.getCell(i, j).changeValue(N.extract(d));
                b.setCell(i,j,n);

                if (ref) {
                    Vector<Cell> s = new Vector<>(1);
                    s.add(n);
                    Map.Entry<String, Vector<Cell>> r = new AbstractMap.SimpleEntry<>("dayoftheWeek", s);
                    b.getCell(i, j).setRefInfo(r);
                }
            }
        }
    }

    // criteria == mayus or criteria == minus
    public void replaceWithCriteriaText (String criteria) {
        for (Cell[] cells : this.block) {
            for (int j = 0; j < this.block[0].length; ++j) {
                String s = (String) cells[j].getInfo();
                if (Objects.equals(criteria, "all caps")) {
                    Cell n = (Cell) cells[j].changeValue(s.toUpperCase(Locale.ROOT));
                    cells[j] = n;
                }

                else if (Objects.equals(criteria, "all not caps")) {
                    Cell n = (Cell) cells[j].changeValue(s.toLowerCase(Locale.ROOT));
                    cells[j] = n;
                }

                else if (Objects.equals(criteria, "cap_first_letter")) {
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

        c = (Cell) c.changeValue(sum/s.size());

        if (ref) {
            Map.Entry<String, Vector<Cell>> r = new AbstractMap.SimpleEntry<>("mean", s);
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

        c = (Cell) c.changeValue(arr[arr.length/2]);

        if (ref) {
            Map.Entry<String, Vector<Cell>> r = new AbstractMap.SimpleEntry<>("median", s);
            c.setRefInfo(r);
        }

        return c;
    }

    public Cell var (Cell c, Boolean ref) {
        double d = Math.pow((double) this.std(c, false).getInfo(), 2);

        c = (Cell) c.changeValue(d);

        if (ref) {
            Vector<Cell> s = new Vector<>();

            for (Cell[] cells : this.block) {
                s.addAll(Arrays.asList(cells).subList(0, this.block[0].length));
            }

            Map.Entry<String, Vector<Cell>> r = new AbstractMap.SimpleEntry<>("var", s);
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

        c = (Cell) c.changeValue(cov);

        if (ref) {
            Map.Entry<String, Vector<Cell>> r = new AbstractMap.SimpleEntry<>("covar", s);
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

        c = (Cell) c.changeValue(stdd);

        if (ref) {
            Map.Entry<String, Vector<Cell>> r = new AbstractMap.SimpleEntry<>("std", s);
            c.setRefInfo(r);
        }

        return c;
    }

    public Cell CPearson (Block b, Cell c, Boolean ref) {
        double d1 = (double) b.std(c,false).getInfo();
        double d2 = (double) this.std(c, false).getInfo();

        double cp;
        double covv = (double) this.covar(b,c,false).getInfo();

        if (d1 == 0 || d2 == 0) cp = 1;
        else cp = covv/(d1*d2);

        if (cp > 1.0) cp = 1.0;
        if (cp < -1) cp = -1.0;

        c = (Cell) c.changeValue(cp);

        if (ref) {

            Vector<Cell> s = new Vector<>();

            for (int i = 0; i < this.block.length; ++i) {
                for (int j = 0; j < this.block[0].length; ++j) {
                    s.add(this.block[i][j]);
                    s.add(b.getCell(i,j));
                }
            }

            Map.Entry<String, Vector<Cell>> r = new AbstractMap.SimpleEntry<>("cp", s);
            c.setRefInfo(r);
        }

        return c;
    }
}
