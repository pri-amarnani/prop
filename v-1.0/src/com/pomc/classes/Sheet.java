package com.pomc.classes;

import java.time.LocalDate;
import java.util.Vector;

public class Sheet {

    Vector<Vector<Cell>> cells = new Vector<>();
    String title;
    int num_rows;
    int num_cols;
    Block b_selected;


    public void update(Block b){

        Cell ul = b.ul;
        Cell dr = b.dr;
        for(int i = 0; i < b.number_rows(); ++ i){
            for(int j = 0; j < b.number_cols(); ++j){
                cells.elementAt(ul.getRow() + i).setElementAt(b.getCell(i,j),ul.getColumn() + j); //seguro?
            }
        }
    }

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
                if (this.getCell(i,j).getInfo() == null && sh.getCell(i,j).getInfo() != null) return false;
                if (sh.getCell(i,j).getInfo() == null && this.getCell(i,j).getInfo() != null) return false;

                if (!(this.getCell(i,j).getInfo() == null) && !(sh.getCell(i,j).getInfo() == null) && !this.getCell(i,j).getInfo().equals(sh.getCell(i,j).getInfo())) return false;
            }
        }
        return true;
    }

    public void change_value(Cell c, Object o){
        Integer id = -1, id2 = -1;
        for(int i = 0; i < num_rows; ++i) {
            id = cells.elementAt(i).indexOf(c);
            if (id != null && id != -1) {
                id2 = i;
                break;
            }
        }
        Object o1 = c.changeValue(o);
        if(id != null && id != -1) cells.elementAt(id2).setElementAt((Cell) o1, id);
        else System.out.println("Cell not found");
        System.out.println(cells.elementAt(id2).elementAt(id).getType());
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
        Cell [][] arr_block = new Cell[c2.getRow()-c1.getRow()+1][c2.getColumn()-c1.getColumn()+1];

        for(int i =0 ; i <= c2.getRow()-c1.getRow(); ++i){
            for(int j = 0; j <= c2.getColumn()-c1.getColumn(); ++j){
                arr_block[i][j] = cells.elementAt(c1.getRow()+ i).elementAt(c1.getRow()+j);
            }
        }
        Block block = new Block(arr_block, c1, c2);
        return block;
    }

    public Block SelectBlock(Cell c1, Cell c2){
        System.out.println(c1.getRow() + " " + c2.getRow());
        System.out.println(c1.getInfo() + " " + c2.getInfo());
        b_selected = null;
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
        update(b);
    }

    public void ModifyBlock(Object o){
        b_selected.ModifyBlock(o);
        update(b_selected);
    }

    public boolean SortBlock(int n_col, String Criteria){
        Cell c = b_selected.getCell(0,n_col);
        Cell c2 = b_selected.getCell(b_selected.number_rows()-1,n_col);

        Block b = create_block(c, c2);

        if(!b.allText() && !b.allDouble()){
            System.out.println("Error. Whole column has to be of type number or type text.");
            return false;
        }
        else b_selected.SortBlock(n_col, Criteria, c.getType());
        update(b_selected);
        return true;
    }

    public Cell find(Object o){
        return b_selected.find(o);
    }

    public void findAndReplace(Object n, Object r){
        b_selected.findAndReplace(n, r);
        update(b_selected);
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
            if(ref && overlapping(b_selected, b)) System.out.println("Error. The blocks selected are overlapped.");
            else b_selected.floor(b, ref);
        }
        else System.out.println("Error. Not all cells are of type Number.");
        update(b);
    }

    public void convert(Block b, Boolean ref, String from, String to){ //falta acabar
        b_selected.convert(b, ref, from, to);
        update(b_selected);
    }

    public void sum(Block b1, Block b2, Boolean ref){
        if (b_selected.allDouble() && b1.allDouble()){
            if(ref && overlapping(b_selected, b2)) System.out.println("Error. The blocks selected are overlapped.");
            else if(ref && overlapping(b1, b2)) System.out.println("Error. The blocks selected are overlapped.");
            else b_selected.sum(b1, b2, ref);
        }
        else System.out.println("Error. Not all cells are of type Number.");
        update(b2);
    }

    public void mult(Block b1, Block b2, Boolean ref){
        if (b_selected.allDouble() && b1.allDouble()){
            if(ref && overlapping(b_selected, b2)) System.out.println("Error. The blocks selected are overlapped.");
            else if(ref && overlapping(b1, b2)) System.out.println("Error. The blocks selected are overlapped.");
            else b_selected.mult(b1, b2, ref);
        }
        else System.out.println("Error. Not all cells are of type Number.");
        update(b2);
    }

    public void div(Block b1, Block b2, Boolean ref){
        if (b_selected.allDouble() && b1.allDouble()) {
            if(ref && overlapping(b_selected, b2)) System.out.println("Error. The blocks selected are overlapped.");
            else if(ref && overlapping(b1, b2)) System.out.println("Error. The blocks selected are overlapped.");
            else b_selected.div(b1, b2, ref);
        }
        else System.out.println("Error. Not all cells are of type Number.");
        update(b2);
    }

    public void substract(Block b1, Block b2, Boolean ref){
        if (b_selected.allDouble() && b1.allDouble() ){
            if(ref && overlapping(b_selected, b2)) System.out.println("Error. The blocks selected are overlapped.");
            else if(ref && overlapping(b1, b2)) System.out.println("Error. The blocks selected are overlapped.");
            else b_selected.substract(b1, b2, ref);
        }
        else System.out.println("Error. Not all cells are of type Number.");
        update(b2);
    }

    public void extract(Block b1,Boolean ref, String ex){      //falta acabar
        b_selected.extract(b1, ref, ex);
    }

    public void dayOfTheWeek (Block b, Boolean ref, String d){
        if(b_selected.allDate()) b_selected.dayOfTheWeek(b, ref, d);
        else System.out.println("Error. Not all cells are of type Date.");
        update(b);
    }

    public void replaceWithCriteriaText(String criteria){
        if (b_selected.allText()) b_selected.replaceWithCriteriaText(criteria);
        else System.out.println("Error. Not all cells are of type Text.");
        update(b_selected);
    }

    public int length(TextCell c, String criteria){
        if(c.getType() == "T") return c.length(criteria);
        else System.out.println("Error. Cell is not of type Text.");
        return -1;
    }

    public Double mean(Cell c, Boolean ref, Boolean val){
        if(b_selected.allDouble()){
            if(ref && overlapping(b_selected, create_block(c,c))) System.out.println("Error. Cell contained in the block.");
            else{
                Cell m = b_selected.mean(c, ref);
                if(val) cells.elementAt(m.getRow()).setElementAt(m, m.getColumn());
                return (Double) m.getInfo();
            }
        }
        else System.out.println("Error. Not all cells are of type Number.");

        return null;
    }

    public Double median(Cell c, Boolean ref, Boolean val){
        if(b_selected.allDouble()){
            if(ref && overlapping(b_selected, create_block(c,c))) System.out.println("Error. Cell contained in the block.");
            else{
                Cell m = b_selected.median(c, ref);
                if(val) cells.elementAt(m.getRow()).setElementAt(m, m.getColumn());
                return (Double) m.getInfo();
            }
        }
        else System.out.println("Error. Not all cells are of type Number.");
        return null;
    }

    public Double var(Cell c, Boolean ref, Boolean val){
        if(b_selected.allDouble()){
            if(ref && overlapping(b_selected, create_block(c,c))) System.out.println("Error. Cell contained in the block.");
            else{
                Cell m = b_selected.var(c, ref);
                if(val) cells.elementAt(m.getRow()).setElementAt(m, m.getColumn());
                return (Double) m.getInfo();
            }
        }
        else System.out.println("Error. Not all cells are of type Number.");
        return null;
    }

    public Double covar(Block b, Cell c, Boolean ref, Boolean val){ //sii
        if(b_selected.allDouble() && b.allDouble()){
            if(ref && overlapping(b_selected, create_block(c,c))) System.out.println("Error. Cell contained in the block.");
            else{
                Cell m = b_selected.covar(b, c, ref);
                if(val) cells.elementAt(m.getRow()).setElementAt(m, m.getColumn());
                return (Double) m.getInfo();
            }
        }
        else System.out.println("Error. Not all cells are of type Number.");
        return null;
    }

    public Double std(Cell c, Boolean ref, Boolean val){
        if(b_selected.allDouble()){
            if(ref && overlapping(b_selected, create_block(c,c))) System.out.println("Error. Cell contained in the block.");
            else{
                Cell m = b_selected.std(c, ref);
                if(val) cells.elementAt(m.getRow()).setElementAt(m, m.getColumn());
                return (Double) m.getInfo();
            }
        }
        else System.out.println("Error. Not all cells are of type Number.");
        return null;
    }

    public Double CPearson(Block b, Cell c, Boolean ref, Boolean val){
        if(b_selected.allDouble() && b.allDouble()){
            if(ref && overlapping(b_selected, create_block(c,c))) System.out.println("Error. Cell contained in the block.");
            else{
                Cell m = b_selected.CPearson(b, c, ref);
                if(val) cells.elementAt(m.getRow()).setElementAt(m, m.getColumn());
                return (Double) m.getInfo();
            }
        }
        else System.out.println("Error. Not all cells are of type Number.");
        return null;
    }

}