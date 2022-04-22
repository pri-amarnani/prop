package com.pomc.classes;

import java.time.LocalDate;
import java.util.Vector;

public class Sheet {

    Vector<Vector<Cell>> cells = new Vector<>();
    String title;
    int num_rows;
    int num_cols;
    Block b_selected;


    public int getNumRows () {
        return this.num_rows;
    }

    public int getNumCols () {
        return this.num_cols;
    }

    public String getTitle () {
        return this.title;
    }

    public Block getSelectedBlock () {
        return this.b_selected;
    }

    public void setTitle (String title) {
        this.title = title;
    }

    public Sheet() {
        this.title = "titulo por defecto"; // valor default depende del num de sheet
        for(int i = 0; i < 60; ++i){  //default 60x60
            Vector<Cell> row = new Vector<>();
            for(int j = 0; j < 60; ++j){
                row.add(new NumCell(i, j, null));
            }
            cells.add(row);
        }
        num_cols = 60;
        num_rows = 60;
    }

    public Sheet(String title) {
        if(title != null) this.title = title;
        else this.title = "titulo por defecto"; // valor default depende del num de sheet
        for(int i = 0; i < 60; ++i){  //default 60x60
            Vector<Cell> row = new Vector<>();
            for(int j = 0; j < 60; ++j){
                row.add(new NumCell(i, j, null));
            }
            cells.add(row);
        }
        num_cols = 60;
        num_rows = 60;
    }

    public Sheet(Vector<Vector<Cell>> cells, String title) {  //not sure
        if(title != null) this.title = title;
        else this.title = "titulo por defecto";
        this.cells = cells;
        num_rows = cells.size();
        num_cols = cells.elementAt(0).size();
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
        if(!b_selected.allText() || !b_selected.allText() || b_selected.allDate()) System.out.println("Error. Whole Block has to be of type number or type text.");
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

    public void findAndReplace(double n, double r){  //(n,r)
        if(b_selected.allDouble()) b_selected.findAndReplace(n);
        else System.out.println("Error. Not all cells are of type Number.");
    }

    public void findAndReplace(String s){ //pasar dos doubles
        if(b_selected.allText()) b_selected.findAndReplace(s);
        else System.out.println("Error. Not all cells are of type String.");
    }

    public void findAndReplace(LocalDate ld){ //pasar dos doubles
        if(b_selected.allDate()) b_selected.findAndReplace(ld);
        else System.out.println("Error. Not all cells are of type Date.");

    }

    public void floor(Block b, Cell c, Boolean ref){    //no se superponen     que hace exactamente????
        if (b_selected.allDouble() && b.allDouble()){    //la celda para seleccionar bloque?
            if(b_selected.dr.getColumn() > b.ul.getColumn() && b_selected.dr.getRow() > b.ul.getRow()) System.out.println("Error. The blocks selected collide.");
            else b_selected.floor(b, ref);
        }
        else System.out.println("Error. Not all cells are of type Number.");
    }

    public void convert(Cell c, Boolean ref){ //falta acabar
        b_selected.convert(b_selected, ref);
    }

    //hacer funcion para comprobar q no se superponen
    public void sum(Block b1, Block b2, Boolean ref){     //no se superponen
        if (b_selected.allDouble() && b1.allDouble()){
            // esto no se tiene q comprobar//if(b_selected.dr.getColumn() > b1.ul.getColumn() && b_selected.dr.getRow() > b1.ul.getRow() || b_selected.ul.getColumn() < b1.dr.getColumn() && b_selected.ul.getRow() < b1.dr.getRow()) System.out.println("Error. The blocks selected collide.");
            //esto si //else if(b_selected.dr.getColumn() > b2.ul.getColumn() && b_selected.dr.getRow() > b2.ul.getRow() || b_selected.ul.getColumn() < b2.dr.getColumn() && b_selected.ul.getRow() < b2.dr.getRow()) System.out.println("Error. The blocks selected collide.");
            //else if(b1.dr.getColumn() > b2.ul.getColumn() && b1.dr.getRow() > b2.ul.getRow()) System.out.println("Error. The blocks selected collide.");
            b_selected.sum(b1, b2, ref);
        }
        else System.out.println("Error. Not all cells are of type Number.");
    }

    public void mult(Block b1, Block b2, Boolean ref){  //no se superponen
        if (b_selected.allDouble() && b1.allDouble()) b_selected.mult(b1, b2, ref);
        else System.out.println("Error. Not all cells are of type Number.");
    }

    public void div(Block b1, Block b2, Boolean ref){  //no se superponen
        if (b_selected.allDouble() && b1.allDouble()) b_selected.div(b1, b2, ref);
        else System.out.println("Error. Not all cells are of type Number.");
    }

    public void substract(Block b1, Block b2, Boolean ref){ //no se superponen (solo con b2)!!!
        if (b_selected.allDouble() && b1.allDouble() ) b_selected.substract(b1, b2, ref);
        else System.out.println("Error. Not all cells are of type Number.");
    }

    public void extract(Block b1,Cell c, Boolean ref){      //falta acabar
        b_selected.extract(b1, ref);
    }

    public void dayOfTheWeek (Block b1,Cell c, Boolean ref){ //no se superponen?
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

    public void covar(Block b, Cell c, Boolean ref, Boolean val){
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

    public void CPearson(Block b, Cell c, Boolean ref, Boolean val){   //no se superponen??
        if(b_selected.allDouble() && b.allDouble()){
            b_selected.CPearson(b, c, ref, val);
        }
        else System.out.println("Error. Not all cells are of type Number.");
    }

}