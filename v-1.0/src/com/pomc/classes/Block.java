package com.pomc.classes;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Locale;
import java.util.Objects;

public class Block {
    Cell [][] block;
    int size_r;
    int size_c;

    public Block () {
        block = null;
    }

    public Block (Cell [][] b) {
        block = new Cell[b.length][b[0].length];
        block = b;
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

    public void SortBlock (Block b, String criteria) {
        // b is a column


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
            }
        }
    }

    public void convert (Block b, Boolean ref) {

    }

    public void sum (Block b1, Block b2, Boolean ref) {
        for (int i = 0; i < this.block.length; ++i) {
            for (int j = 0; j < this.block[0].length; ++j) {
                b2.getCell(i,j).changeValue((double) this.block[i][j].getInfo() + (double) b1.getCell(i,j).getInfo());
            }
        }
    }

    public void mult (Block b1, Block b2, Boolean ref) {
        for (int i = 0; i < this.block.length; ++i) {
            for (int j = 0; j < this.block[0].length; ++j) {
                b2.getCell(i,j).changeValue((double) this.block[i][j].getInfo() * (double) b1.getCell(i,j).getInfo());
            }
        }
    }

    public void div (Block b1, Block b2, Boolean ref) {
        for (int i = 0; i < this.block.length; ++i) {
            for (int j = 0; j < this.block[0].length; ++j) {
                b2.getCell(i,j).changeValue((double) this.block[i][j].getInfo() / (double) b1.getCell(i,j).getInfo());
            }
        }
    }

    public void substract (Block b1, Block b2, Boolean ref) {
        for (int i = 0; i < this.block.length; ++i) {
            for (int j = 0; j < this.block[0].length; ++j) {
                b2.getCell(i,j).changeValue((double) this.block[i][j].getInfo() - (double) b1.getCell(i,j).getInfo());
            }
        }
    }

    public void extract (Block b, Boolean ref) {

    }

    public void dayOfTheWeek (Block b, Boolean ref) {
        for (int i = 0; i < this.block.length; ++i) {
            for (int j = 0; j < this.block[0].length; ++j) {
                LocalDate aux = (LocalDate) getCell(i,j).getInfo();

                //need cell to have DayOfTheWeek method coded
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

    public double mean (Boolean ref) {
        double sum = 0;

        for (Cell[] cells : this.block) {
            for (int j = 0; j < this.block[0].length; ++j) {
                sum += (double) cells[j].getInfo();
            }
        }
        return sum/(this.block.length*this.block[0].length);
    }

    // left to right and then to down
    public double median (Boolean ref) {
        double [] s = new double[this.block.length*this.block[0].length];

        for (int i = 0; i < this.block.length; ++i) {
            for (int j = 0; j < this.block[0].length; ++j) {
                s[i+j] = (double) this.block[i][j].getInfo();
            }
        }

        Arrays.sort(s);
        return s[s.length/2];
    }

    public double var (Boolean ref) {
        return Math.pow(this.std(false), 2);
    }

    public double covar (Block b, Boolean ref) {
        double mean1 = this.mean(false);
        double mean2 = b.mean(false);

        double [] x = new double[this.block.length*this.block[0].length];
        double [] y = new double[this.block.length*this.block[0].length];

        for (int i = 0; i < this.block.length; ++i) {
            for (int j = 0; j < this.block[0].length; ++j) {
                x[i+j] = (double) this.block[i][j].getInfo();
                y[i+j] = (double) b.getCell(i,j).getInfo();
            }
        }

        double sum = 0;

        for (int i = 0; i < x.length; ++i) {
            sum += (x[i] - mean1)*(y[i] - mean2);
        }

        return sum/x.length;
    }

    public double std (Boolean ref) {
        double sum = 0, std = 0;
        double [] s = new double[this.block.length*this.block[0].length];

        for (int i = 0; i < this.block.length; ++i) {
            for (int j = 0; j < this.block[0].length; ++j) {
                s[i+j] = (double) this.block[i][j].getInfo();
                sum += (double) this.block[i][j].getInfo();
            }
        }

        double mean = sum/s.length;

        for (double num: s) {
            std += Math.pow(num - mean, 2);
        }

        return (Math.sqrt(std/s.length));
    }

    public double CPearson (Block b, Boolean ref) {
        return this.covar(b, false)/(b.std(false)*this.std(false));
    }

    public static void main(String[] args) {

    }
}
