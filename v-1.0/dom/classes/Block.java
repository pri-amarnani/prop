package classes;

import java.util.Arrays;
import java.time.LocalDate; // this will need to be included in classes.Cell
import java.util.Date;


public class Block {
    Cell [][] block;

    public Block () {
        block = null;
    }

    public Block (Cell [][] b) {
        block = new Cell[][];
        block = b;
    }

    public Cell getCell (int i, int j) {
        return this.block[i][j];
    }

    public void CopyB () {
        // copy to clipboard
    }

    public void ModifyBlock(double n) {
        for (int i = 0; i < block.length; ++i) {
            for (int j = 0; j < block[0].length; ++j) {
                if (block[i][j].isNum()) block[i][j].changeValueN(n);
            }
        }
    }

    public void ModifyBlock(String n) {
        for (int i = 0; i < block.length; ++i) {
            for (int j = 0; j < block[0].length; ++j) {
                if (block[i][j].isText()) block[i][j].changeValueT(n);
            }
        }
    }

    public void ModifyBlock(LocalDate ld) {
        for (int i = 0; i < block.length; ++i) {
            for (int j = 0; j < block[0].length; ++j) {
                if (block[i][j].isDate()) block[i][j].changeValueD(ld);
            }
        }
    }

    public void SortBlock (Block b, String criteria) {
        // b is a column

    }

    public Cell find (double n) {
        for (int i = 0; i < block.length; ++i) {
            for (int j = 0; j < block[0].length; ++j) {
                if (block[i][j].getInfoNum() == n) return block[i][j];
            }
        }
        return null;
    }

    public Cell find (String n) {
        for (int i = 0; i < block.length; ++i) {
            for (int j = 0; j < block[0].length; ++j) {
                if (block[i][j].getInfoText() == n) return block[i][j];
            }
        }
        return null;
    }

    public Cell find (Date ld) {
        for (int i = 0; i < block.length; ++i) {
            for (int j = 0; j < block[0].length; ++j) {
                if (block[i][j].getInfoDate() == ld) return block[i][j];
            }
        }
        return null;
    }

    public void findAndReplace (double n) {
        for (int i = 0; i < block.length; ++i) {
            for (int j = 0; j < block[0].length; ++j) {
                if (block[i][j].getInfoNum() == n) this.block[i][j].changeValueN(n);
            }
        }
    }

    public void findAndReplace (String n) {
        for (int i = 0; i < block.length; ++i) {
            for (int j = 0; j < block[0].length; ++j) {
                if (block[i][j].getInfoText() == n) this.block[i][j].changeValueT(n);
            }
        }
    }

    public void findAndReplace (Date ld) {
        for (int i = 0; i < block.length; ++i) {
            for (int j = 0; j < block[0].length; ++j) {
                if (block[i][j].getInfoDate() == ld) this.block[i][j].changeValueD(ld);
            }
        }
    }

    public void floor (Block b, Boolean ref) {
        for (int i = 0; i < this.block.length; ++i) {
            for (int j = 0; j < this.block[0].length; ++j) {
                b.getCell(i,j).changeValueN(Math.floor(block[i][j].getInfoNum()));
            }
        }
    }

    public void convert (Block b, Boolean ref) {

    }

    public void sum (Block b1, Block b2, Boolean ref) {
        for (int i = 0; i < this.block.length; ++i) {
            for (int j = 0; j < this.block[0].length; ++j) {
                b2.getCell(i,j).changeValueN(this.block[i][j].getInfoNum() + b1.getCell(i,j).getInfoNum());
            }
        }
    }

    public void mult (Block b1, Block b2, Boolean ref) {
        for (int i = 0; i < this.block.length; ++i) {
            for (int j = 0; j < this.block[0].length; ++j) {
                b2.getCell(i,j).changeValueN(this.block[i][j].getInfoNum() * b1.getCell(i,j).getInfoNum());
            }
        }
    }

    public void div (Block b1, Block b2, Boolean ref) {
        for (int i = 0; i < this.block.length; ++i) {
            for (int j = 0; j < this.block[0].length; ++j) {
                b2.getCell(i,j).changeValueN(this.block[i][j].getInfoNum() / b1.getCell(i,j).getInfoNum());
            }
        }
    }

    public void substract (Block b1, Block b2, Boolean ref) {
        for (int i = 0; i < this.block.length; ++i) {
            for (int j = 0; j < this.block[0].length; ++j) {
                b2.getCell(i,j).changeValueN(this.block[i][j].getInfoNum() - b1.getCell(i,j).getInfoNum());
            }
        }
    }

    public void extract (Block b, Boolean ref) {

    }

    public void dayOfTheWeek (Block b, Boolean ref) {

    }

    public void replace (Block b, String criteria) {

    }
    public double mean (Boolean ref) {
        double sum = 0;

        for (int i = 0; i < this.block.length; ++i) {
            for (int j = 0; j < this.block[0].length; ++j) {
                sum += this.block[i][j].getInfoNum();
            }
        }
        return sum/(this.block.length*this.block[0].length);
    }

    // left to right and then to down
    public double median (Boolean ref) {
        double [] s = new double[this.block.length*this.block[0].length];

        for (int i = 0; i < this.block.length; ++i) {
            for (int j = 0; j < this.block[0].length; ++j) {
                s[i+j] = this.block[i][j].getInfoNum();
            }
        }

        Arrays.sort(s);
        return s[s.length/2];
    }

    public double var (Boolean ref) {
        return Math.pow(this.std(false), 2);
    }

    public double covar (Block b, Boolean ref) {
        return -1;
    }

    public double std (Boolean ref) {
        double sum = 0, std = 0;
        double [] s = new double[this.block.length*this.block[0].length];

        for (int i = 0; i < this.block.length; ++i) {
            for (int j = 0; j < this.block[0].length; ++j) {
                s[i+j] = this.block[i][j].getInfoNum();
                sum += this.block[i][j].getInfoNum();
            }
        }

        double mean = sum/s.length;

        for (double num: s) {
            std += Math.pow(num - mean, 2);
        }

        return (Math.sqrt(std/s.length);
    }

    public double CPearson (Block b, Boolean ref) {
        return this.covar(b, false)/(b.std(false)*this.std(false));
    }

    public static void main(String[] args) {

    }
}
