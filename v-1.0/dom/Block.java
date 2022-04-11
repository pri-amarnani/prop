import Cell;
import java.time.LocalDate; // this will need to be included in Cell

public class Block {
    Cell [][] block;

    public Block () {
        block = null;
    }

    public Block (Cell [][] b) {
        block = new Cell[bottom_r.row() - top_left.row()][bottom_r.column() - top_left.column()];
        block = b;
    }

    public void CopyB () {
        // copy to clipboard
    }

    public void ModifyBlock(int n) {
        for (int i = 0; i < block.length; ++i) {
            for (int j = 0; j < block[0].length; ++j) {
                if (block[i][j].isInt()) block[i][j].changeValue(n);
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

    }

    public void findAndReplace (String n) {

    }

    public void findAndReplace (LocalDate ld) {

    }

    public void floor (Block b, Boolean ref) {

    }

    public void convert (Block b, Boolean ref) {

    }

    public void sum (Block b1, Block b2, Boolean ref) {

    }

    public void mult (Block b1, Block b2, Boolean ref) {

    }

    public void div (Block b1, Block b2, Boolean ref) {

    }

    public void substract (Block b1, Block b2, Boolean ref) {

    }

    public void extract (Block b, Boolean ref) {

    }

    public String dayOfTheWeek (Block b, Boolean ref) {
        return null;
    }

    public void replace (Block b, String criteria) {

    }

    public float mean (Block b, Boolean ref) {
        return -1;
    }

    public int median (Block b, Boolean ref) {
        return -1;
    }

    public float var (Block b, Boolean ref) {
        return -1;
    }

    public float covar (Block b, Boolean ref) {
        return -1;
    }

    public float std (Block b, Boolean ref) {
        return -1;
    }

    public float CPearson (Block b, Boolean ref) {
        return -1;
    }
}
