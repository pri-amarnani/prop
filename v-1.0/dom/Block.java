import Cell;
import java.util.Arrays;
import java.time.LocalDate; // this will need to be included in Cell
import java.math.RoundingMode;

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

    public void ModifyBlock(float n) {
        for (int i = 0; i < block.length; ++i) {
            for (int j = 0; j < block[0].length; ++j) {
                if (block[i][j].isFloat()) block[i][j].changeValue(n);
            }
        }
    }

    public void ModifyBlock(String n) {
        for (int i = 0; i < block.length; ++i) {
            for (int j = 0; j < block[0].length; ++j) {
                if (block[i][j].isString()) block[i][j].changeValue(n);
            }
        }
    }

    public void ModifyBlock(LocalDate ld) {
        for (int i = 0; i < block.length; ++i) {
            for (int j = 0; j < block[0].length; ++j) {
                if (block[i][j].isDate()) block[i][j].changeValue(ld);
            }
        }
    }

    public void SortBlock (Block b, String criteria) {
        // b is a column

    }

    public Cell find (int n) {
        for (int i = 0; i < block.length; ++i) {
            for (int j = 0; j < block[0].length; ++j) {
                if (block[i][j].value() == n) return block[i][j];
            }
        }
        return null;
    }

    public Cell find (String n) {
        for (int i = 0; i < block.length; ++i) {
            for (int j = 0; j < block[0].length; ++j) {
                if (block[i][j].value() == n) return block[i][j];
            }
        }
        return null;
    }

    public Cell find (LocalDate ld) {
        for (int i = 0; i < block.length; ++i) {
            for (int j = 0; j < block[0].length; ++j) {
                if (block[i][j].value() == ld) return block[i][j];
            }
        }
        return null;
    }

    public void findAndReplace (int n) {
        for (int i = 0; i < block.length; ++i) {
            for (int j = 0; j < block[0].length; ++j) {
                if (block[i][j].value() == n) this.block[i][j].changeValue(n);
            }
        }
    }

    public void findAndReplace (String n) {
        for (int i = 0; i < block.length; ++i) {
            for (int j = 0; j < block[0].length; ++j) {
                if (block[i][j].value() == n) this.block[i][j].changeValue(n);
            }
        }
    }

    public void findAndReplace (LocalDate ld) {
        for (int i = 0; i < block.length; ++i) {
            for (int j = 0; j < block[0].length; ++j) {
                if (block[i][j].value() == ld) this.block[i][j].changeValue(ld);
            }
        }
    }

    public void floor (Block b, Boolean ref) {
        for (int i = 0; i < this.block.length; ++i) {
            for (int j = 0; j < this.block[0].length; ++j) {
                b.getCell(i,j).changeValue((float) Math.floor((double) block[i][j].value()));
            }
        }
    }

    public void convert (Block b, Boolean ref) {

    }

    public void sum (Block b1, Block b2, Boolean ref) {
        for (int i = 0; i < this.block.length; ++i) {
            for (int j = 0; j < this.block[0].length; ++j) {
                b2.getCell(i,j).changeValue(this.block[i][j].value() + b1.getCell(i,j).value());
            }
        }
    }

    public void mult (Block b1, Block b2, Boolean ref) {
        for (int i = 0; i < this.block.length; ++i) {
            for (int j = 0; j < this.block[0].length; ++j) {
                b2.getCell(i,j).changeValue(this.block[i][j].value() * b1.getCell(i,j).value());
            }
        }
    }

    public void div (Block b1, Block b2, Boolean ref) {
        for (int i = 0; i < this.block.length; ++i) {
            for (int j = 0; j < this.block[0].length; ++j) {
                b2.getCell(i,j).changeValue(this.block[i][j].value() / b1.getCell(i,j).value());
            }
        }
    }

    public void substract (Block b1, Block b2, Boolean ref) {
        for (int i = 0; i < this.block.length; ++i) {
            for (int j = 0; j < this.block[0].length; ++j) {
                b2.getCell(i,j).changeValue(this.block[i][j].value() - b1.getCell(i,j).value());
            }
        }
    }

    public void extract (Block b, Boolean ref) {

    }

    public void dayOfTheWeek (Block b, Boolean ref) {

    }

    public void replace (Block b, String criteria) {

    }
    public float mean (Boolean ref) {
        float sum = 0;
        for (int i = 0; i < this.block.length; ++i) {
            for (int j = 0; j < this.block[0].length; ++j) {
                sum += this.block[i][j].value();
            }
        }
        return sum/(this.block.length*this.block[0].length);
    }

    // left to right and then to down
    public float median (Boolean ref) {
        float [] s = new float[this.block.length*this.block[0].length];

        for (int i = 0; i < this.block.length; ++i) {
            for (int j = 0; j < this.block[0].length; ++j) {
                s[i+j] = this.block[i][j].getValue();
            }
        }

        Arrays.sort(s);
        return s[s.length/2];
    }

    public float var (Boolean ref) {
        return (float)Math.pow(this.std(false), 2);
    }

    public float covar (Block b, Boolean ref) {
        return -1;
    }

    public float std (Boolean ref) {
        float sum = 0, std = 0;
        float [] s = new float[this.block.length*this.block[0].length];

        for (int i = 0; i < this.block.length; ++i) {
            for (int j = 0; j < this.block[0].length; ++j) {
                s[i+j] = this.block[i][j].getValue();
                sum += this.block[i][j].getValue();
            }
        }

        float mean = sum/s.length;

        for(float num: s) {
            std += Math.pow(num - mean, 2);
        }

        return (float)Math.sqrt(std/s.length);
    }

    public float CPearson (Block b, Boolean ref) {
        return this.covar(b, false)/(b.std(false)*this.std(false));
    }

    public static void main(String[] args) {

    }
}
