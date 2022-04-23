package com.pomc.classes;

import java.time.LocalDate;
import java.util.Vector;

public class Sheet {

    Vector<Vector<Cell>> cells = new Vector<>();
    String title;
    int num_rows;
    int num_cols;
    Block b_selected;

    public boolean isEqual(Block b1, Block b2){
        return b1.isEqual(b2);
    }

    public int getNumRows () {
        return this.num_rows;
    }

    public int getNumCols () {
        return this.num_cols;
    }

    public String getTitle () {
        return this.title;
    }

    public Cell getCell (int i, int j) {
        Vector<Cell> row = cells.elementAt(i);
        return row.elementAt(j);
    }

    public Block getSelectedBlock () {
        return this.b_selected;
    }

    public Vector<Vector<Cell>> getCells() {
        return cells;
    }
/*
    public Cell[][] getArray_cells() {
        Cell[][] b2 = new Cell[cells.size()][cells.elementAt(0).size()];

        for(int i = 0; i < 11; ++i){
            Vector<Cell> row = cells.elementAt(i);

            for(int j = 0; j < 10; ++j){
                b2[i][j] = row.elementAt(j);
            }
        }
        return b2;
    }

 */

    public void setTitle (String title) {
        this.title = title;
    }

    public Sheet(String title) {
        this.title = title;
        for(int i = 0; i < 64; ++i){
            Vector<Cell> row = new Vector<>();
            for(int j = 0; j < 64; ++j){
                row.add(new NumCell(i, j, null));
            }
            cells.add(row);
        }
        num_cols = 64;
        num_rows = 64;
    }

    public Sheet(Cell[][] cells, String title) {  //not sure //arrray to vector

        Vector<Vector<Cell>> v_cells = new Vector<>();

        for(int i = 0; i < cells.length; ++ i ){
            Vector<Cell> row = new Vector<>();
            for(int j = 0; j < cells[0].length; ++j){
                row.add(cells[i][j]);   //cells[i][j]  quizas mejor paso a paso
            }
            v_cells.add(row);
        }

        this.title = title;
        this.cells = v_cells;
        num_rows = v_cells.size();
        num_cols = v_cells.elementAt(0).size();
    }

    public Sheet(int rows, int columns, String title){
        this.num_rows = rows;
        this.num_cols = columns;
        this.title = title;
        for(int i = 0; i < rows; ++i){
            Vector<Cell> row = new Vector<>();
            for(int j = 0; j < columns; ++j){
                row.add(new NumCell(i, j, null));
            }
            cells.add(row);
        }
    }

    public boolean isEqual(Sheet sh) {

        if (sh.getNumCols() != this.getNumCols() || sh.getNumRows() != this.getNumRows()) return false;

        for (int i = 0; i < sh.getNumRows(); ++i) {
            for (int j = 0; j < sh.getNumCols(); ++j) {
                if (this.getCell(i,j).getInfo() != sh.getCell(i,j).getInfo()) return false;
            }
        }
        return true;
    }

    public void change_value(Cell c, Object o){   //cambiar el valor de las celdas
        Integer id = -1, id2 = -1;
        for(int i = 0; i < cells.size(); ++i){
            id2 = cells.elementAt(i).indexOf(c);
            if(id2 != null){
                id = id2;
                id2 = i;
                break;
            }
        }
        Object o1 = c.changeValue(o);
        if(o1 != null && id != -1 && id2 != -1){
            cells.elementAt(id2).setElementAt((Cell) o1, id);
        }
        else System.out.println("Cell not found");
    }

    public void NewRow(int pos){
        ++num_rows;
        Vector<Cell> pos_row = new Vector<>();
        for(int i = 0; i < num_cols; ++i){
            Cell c = new NumCell(pos, i, null);
            pos_row.add(c);
        }
        cells.insertElementAt(pos_row, pos);
        for(int i = pos + 1; i < num_rows; ++i){
            Vector<Cell> row = cells.elementAt(i);
            for(int j = 0; j < num_cols; ++j){
                Cell c = row.elementAt(j);
                c.setRow(i);
                row.setElementAt(c,j);
            }
            cells.setElementAt(row,i);
        }
    }

    public void NewColumn(int pos){
        ++num_cols;
        for(int i = 0; i < num_rows; ++i){
            Vector<Cell> row = cells.elementAt(i);
            row.insertElementAt(new NumCell(i,pos,null), pos);
            for(int j = pos + 1; j < num_cols; ++j ) {
                Cell c = row.elementAt(j);
                c.setColumn(j);
                row.setElementAt(c,j);
            }
            cells.setElementAt(row,i);
        }
    }

    public void DeleteRow(int pos){
        if(num_rows <= 1) System.out.println("Error. Not enough rows.");
        else{
            --num_rows;
            cells.removeElement(pos);
            for(int i = pos; i < num_rows; ++i){
                Vector<Cell> row = cells.elementAt(i);
                for(int j = 0; j < num_cols; ++j){
                    Cell c = row.elementAt(j);
                    c.setRow(i);
                    row.setElementAt(c,j);
                }
                cells.setElementAt(row,i);
            }
        }
    }

    public void DeleteColumn(int pos){
        if(num_cols <= 1) System.out.println("Error. Not enough columns.");
        else{
            --num_cols;
            for(int i = 0; i < num_rows; ++i){
                Vector<Cell> row = cells.elementAt(i);
                row.removeElementAt(pos);
                for(int j = pos; j < num_cols; ++j ) {
                    Cell c = row.elementAt(j);
                    c.setColumn(j);
                    row.setElementAt(c,j);
                }
                cells.setElementAt(row,i);
            }
        }
    }


    public Block create_block(Cell c1, Cell c2){
        Vector<Vector<Cell>> vec_block = new Vector<>();

        Cell ul;
        Cell dr;

        for(int i = c1.getRow(); i <= c2.getRow(); ++i){
            Vector<Cell> row = cells.elementAt(i);
            Vector<Cell> row2 = new Vector<>();
            for(int j = c1.getColumn(); j <= c2.getColumn(); ++j){
                Cell c = row.elementAt(j);
                row2.add(c);
            }
            vec_block.add(row2);
        }

        Vector<Cell[]> b = new Vector<>();

        for(int i = 0 ; i < vec_block.size(); ++i){
            Vector<Cell> row_i = vec_block.elementAt(i);
            Cell[] r = row_i.toArray(new Cell[row_i.size()]);
            b.add(r);
        }

        Vector<Cell> first_row = cells.elementAt(0);
        ul = first_row.firstElement();
        Vector<Cell> last_row = cells.lastElement();
        dr = last_row.lastElement();

        Cell[][] arr_block;
        arr_block = b.toArray(new Cell[b.size()][]);

        Block block = new Block(arr_block, ul, dr);
        return block;
    }

    public Block SelectBlock(Cell c1, Cell c2){
        Block b = create_block(c1,c2);
        b_selected = b;
        return b;
    }

    public void CopyB(){ //falta acabar
        b_selected.CopyB();
    }

    public void MoveBlock(Block b, Boolean ref){
        if(b.number_cols() == b_selected.number_cols() && b.number_rows() == b_selected.number_rows()) {
            if(b_selected.dr.getColumn() > b.ul.getColumn() && b_selected.dr.getRow() > b.ul.getRow()) System.out.println("Error. The blocks selected collide.");
            else b_selected.ref(b, ref);
        }
        else System.out.println("Error. The blocks selected have different sizes.");
    }

    public void ModifyBlock(double n){
        if(b_selected.allDate()) b_selected.ModifyBlock(n);
        else System.out.println("Error. Not all cells are of type Number.");
    }

    public void ModifyBlock(String s){
        if(b_selected.allDate()) b_selected.ModifyBlock(s);
        else System.out.println("Error. Not all cells are of type String.");
    }

    public void ModifyBlock(LocalDate ld){
        if(b_selected.allDate()) b_selected.ModifyBlock(ld);
        else System.out.println("Error. Not all cells are of type Date.");
    }

    public void SortBlock(int n_col, String Criteria){
        if(!b_selected.allText() || !b_selected.allDouble() || b_selected.allDate()) System.out.println("Error. Whole Block has to be of type number or type text.");
        else b_selected.SortBlock(b_selected ,n_col, Criteria);
    }

    public Cell find(double n){
        if(b_selected.allDate()) return b_selected.find(n);
        else System.out.println("Error. Not all cells are of type Number.");
        return null;
    }

    public Cell find(String s){
        if(b_selected.allDate()) return b_selected.find(s);
        else System.out.println("Error. Not all cells are of type String.");
        return null;
    }

    public Cell find(LocalDate ld){
        if(b_selected.allDate()) return b_selected.find(ld);
        else System.out.println("Error. Not all cells are of type Date.");
        return null;
    }

    public void findAndReplace(double n, double r){
        if(b_selected.allDouble()) b_selected.findAndReplace(n, r);
        else System.out.println("Error. Not all cells are of type Number.");
    }

    public void findAndReplace(String s, String r){
        if(b_selected.allText()) b_selected.findAndReplace(s, r);
        else System.out.println("Error. Not all cells are of type String.");
    }

    public void findAndReplace(LocalDate ld, LocalDate r){
        if(b_selected.allDate()) b_selected.findAndReplace(ld, r);
        else System.out.println("Error. Not all cells are of type Date.");

    }

    public Boolean overlapping(Block b1, Block b2){
        if(b1.dr.getColumn() < b2.ul.getColumn()) return false;
        if(b1.dr.getRow() < b2.ul.getRow()) return false;
        if(b2.dr.getColumn() < b1.ul.getColumn()) return false;
        if(b2.dr.getRow() < b1.ul.getRow()) return false;
        return true;
    }

    public void floor(Block b, Boolean ref){
        if (b_selected.allDouble() && b.allDouble()){
            if(overlapping(b_selected, b)) System.out.println("Error. The blocks selected are overlapped.");
            else b_selected.floor(b, ref);
        }
        else System.out.println("Error. Not all cells are of type Number.");
    }

    public void convert(Cell c, Boolean ref){ //falta acabar
        b_selected.convert(b_selected, ref);
    }

    public void sum(Block b1, Block b2, Boolean ref){
        if (b_selected.allDouble() && b1.allDouble()){
            if(overlapping(b_selected, b2)) System.out.println("Error. The blocks selected are overlapped.");
            else if(overlapping(b1, b2)) System.out.println("Error. The blocks selected are overlapped.");
            else b_selected.sum(b1, b2, ref);
        }
        else System.out.println("Error. Not all cells are of type Number.");
    }

    public void mult(Block b1, Block b2, Boolean ref){
        if (b_selected.allDouble() && b1.allDouble()){
            if(overlapping(b_selected, b2)) System.out.println("Error. The blocks selected are overlapped.");
            else if(overlapping(b1, b2)) System.out.println("Error. The blocks selected are overlapped.");
            else b_selected.mult(b1, b2, ref);
        }
        else System.out.println("Error. Not all cells are of type Number.");
    }

    public void div(Block b1, Block b2, Boolean ref){
        if (b_selected.allDouble() && b1.allDouble()) {
            if(overlapping(b_selected, b2)) System.out.println("Error. The blocks selected are overlapped.");
            else if(overlapping(b1, b2)) System.out.println("Error. The blocks selected are overlapped.");
            else b_selected.div(b1, b2, ref);
        }
        else System.out.println("Error. Not all cells are of type Number.");
    }

    public void substract(Block b1, Block b2, Boolean ref){
        if (b_selected.allDouble() && b1.allDouble() ){
            if(overlapping(b_selected, b2)) System.out.println("Error. The blocks selected are overlapped.");
            else if(overlapping(b1, b2)) System.out.println("Error. The blocks selected are overlapped.");
            else b_selected.substract(b1, b2, ref);
        }
        else System.out.println("Error. Not all cells are of type Number.");
    }

    public void extract(Block b1,Cell c, Boolean ref){      //falta acabar
        b_selected.extract(b1, ref);
    }

    public void dayOfTheWeek (Block b1, Cell c, Boolean ref){
        if(b_selected.allDate()) b_selected.dayOfTheWeek(b1, ref);
        else System.out.println("Error. Not all cells are of type Date.");
    }

    public void replaceWithCriteriaText(String criteria){
        if (b_selected.allText()) b_selected.replaceWithCriteriaText(criteria);
        else System.out.println("Error. Not all cells are of type Text.");
    }

    public int length(TextCell c, String criteria){
        if(c.getType() == "T") return c.length(criteria);
        else System.out.println("Error. Cell is not of type Text.");
        return -1;
    }

    public void mean(Cell c, Boolean ref, Boolean val){
        if(b_selected.allDouble()){
            b_selected.mean(c, ref, val);
        }
        else System.out.println("Error. Not all cells are of type Number.");
    }

    public void median(Cell c, Boolean ref, Boolean val){
        if(b_selected.allDouble()){
            b_selected.median(c, ref, val);
        }
        else System.out.println("Error. Not all cells are of type Number.");
    }

    public void var(Cell c, Boolean ref, Boolean val){
        if(b_selected.allDouble()){
            b_selected.var(c, ref, val);
        }
        else System.out.println("Error. Not all cells are of type Number.");
    }

    public void covar(Block b, Cell c, Boolean ref, Boolean val){ //sii
        if(b_selected.allDouble() && b.allDouble()){
            b_selected.covar(b, c, ref, val);
        }
        else System.out.println("Error. Not all cells are of type Number.");
    }

    public void std(Cell c, Boolean ref, Boolean val){
        if(b_selected.allDouble()){
            b_selected.std(c, ref, val);
        }
        else System.out.println("Error. Not all cells are of type Number.");
    }

    public void CPearson(Block b, Cell c, Boolean ref, Boolean val){
        if(b_selected.allDouble() && b.allDouble()){
            System.out.println(b_selected.CPearson(b, c, ref, val));
        }
        else System.out.println("Error. Not all cells are of type Number.");
    }

}