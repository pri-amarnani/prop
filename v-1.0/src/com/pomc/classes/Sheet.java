package com.pomc.classes;

import java.time.LocalDate;
import java.util.Vector;
//import java.util.Arrays;
//import java.util.Objects;

public class Sheet {
    Vector<Vector<Cell>> cells = new Vector<>();
    String title;
    int num_rows;
    int num_cols;
    Block b_selected;

    public Sheet(String title) {
        if(title != null) this.title = title;
        else this.title = "titulo por defecto"; // valor default depende del num de sheet
        cells = null;
        num_cols = 0;
        num_rows = 0;
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
                row.add(new NumCell(i, j, "N", null));
            }
            cells.add(row);
        }
    }

    public void NewRow(int pos){
        ++num_rows;
        Vector<Cell> pos_row = new Vector<>();
        for(int i = 0; i < num_cols; ++i){
            Cell c = new NumCell(pos, i,"N", null);
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
            row.insertElementAt(new NumCell(i,pos,"N", null), pos);
            for(int j = pos + 1; j < num_cols; ++j ) {
                Cell c = row.elementAt(j);
                c.setColumn(j);
                row.setElementAt(c,j);
            }
            cells.setElementAt(row,i);
        }
    }

    public void DeleteRow(int pos){
        if(num_rows <= 0) System.out.println("Error. Not enough rows.");
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
        if(num_cols <= 0) System.out.println("Error. Not enough columns.");
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
        Vector<Vector<Cell>> vec_block = new Vector<Vector<Cell>>();
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
        Cell[][] arr_block;
        arr_block = b.toArray(new Cell[b.size()][]);
        Block block = new Block(arr_block);
        return block;
    }

    public Block SelectBlock(Cell c1, Cell c2){   //no ha de devolver un bloque
        Block b = create_block(c1,c2);
        b_selected = b;
        return b;
    }

    public void CopyB(Block b){ //falta acabar
        b.CopyB();
    }


    public void MoveBlock(Block b){  //del bloque seleccionado al bloque b

    }

    public void ModifyBlock(Block b, double n){
        if(b.allDate()) b.ModifyBlock(n);
        else System.out.println("Error. Not all cells are of type Number.");
    }

    public void ModifyBlock(Block b, String s){
        if(b.allDate()) b.ModifyBlock(s);
        else System.out.println("Error. Not all cells are of type String.");
    }

    public void ModifyBlock(Block b, LocalDate ld){
        if(b.allDate()) b.ModifyBlock(ld);
        else System.out.println("Error. Not all cells are of type Date.");
    }




    public void ReferenceBlocks(Block b1, Block b2){}  //se puede hacer desde referenceCell
    public void ReferenceCellBlock(Cell c, Block b){}  //se puede hacer desde referenceCell

    public void ReferenceCells(Cell c, Cell c2){

    }




    //ACABAR CONTROL DE ERRORES


    public void SortBlock(Block b, String Criteria){ //falta acabar
        b.SortBlock(b,Criteria);
    }

    public Cell find(double n, Block b){
        if(b.allDate()) return b.find(n);
        else System.out.println("Error. Not all cells are of type Number.");
        return null;
    }

    public Cell find(String s, Block b){
        if(b.allDate()) return b.find(s);
        else System.out.println("Error. Not all cells are of type String.");
        return null;
    }

    public Cell find(LocalDate ld, Block b){
        if(b.allDate()) return b.find(ld);
        else System.out.println("Error. Not all cells are of type Date.");
        return null;
    }

    public void findAndReplace(double n, double r, Block b){
        if(b.allDouble()) b.findAndReplace(n); //(n,r)
        else System.out.println("Error. Not all cells are of type Number.");
    }

    public void findAndReplace(String s, Block b){ //pasar dos doubles
        if(b.allText()) b.findAndReplace(s);
        else System.out.println("Error. Not all cells are of type String.");
    }

    public void findAndReplace(LocalDate ld, Block b){ //pasar dos doubles
        if(b.allDate()) b.findAndReplace(ld);
        else System.out.println("Error. Not all cells are of type Date.");

    }

    public void floor(Block b1, Cell c, Boolean ref){           //la celda para seleccionar bloque?
        if (b_selected.allDouble() && b1.allDouble()) b_selected.floor(b1, ref);
        else System.out.println("Error. Not all cells are of type Number.");
    }

    //1 bloque seleccionado + crear otro en las aritmeticas?


    public void convert(Block b1, Cell c, Boolean ref){ //falta acabar
        b1.convert(b1, ref);
    }

    public void sum(Block b1, Block b2, Block b3, Boolean ref){
        if (b_selected.allDouble() && b1.allDouble()) b1.sum(b2, b3, ref);
        else System.out.println("Error. Not all cells are of type Number.");
    }

    public void mult(Block b1, Block b2, Boolean ref){
        if (b_selected.allDouble() && b1.allDouble()) b_selected.mult(b1, b2, ref);
        else System.out.println("Error. Not all cells are of type Number.");
    }

    public void div(Block b1, Block b2, Boolean ref){
        if (b_selected.allDouble() && b1.allDouble()) b_selected.div(b1, b2, ref);
        else System.out.println("Error. Not all cells are of type Number.");
    }

    public void substract(Block b1, Block b2, Boolean ref){
        if (b_selected.allDouble() && b1.allDouble() ) b_selected.substract(b1, b2, ref);
        else System.out.println("Error. Not all cells are of type Number.");
    }

    public void extract(Block b1,Cell c, Boolean ref){      //falta acabar
        b_selected.extract(b1, ref);
    }

    public void dayOfTheWeek (Block b1,Cell c, Boolean ref){ //q hace exactamente?
        if(b_selected.allDate()) b_selected.dayOfTheWeek(b1, ref);
        else System.out.println("Error. Not all cells are of type Date.");
    }

    public void replaceWithCriteriaText(Block b1,Cell c, Boolean ref, String criteria){
        if (b_selected.allText()) b1.replaceWithCriteriaText(criteria);
        else System.out.println("Error. Not all cells are of type Number.");
    }

    public int length(TextCell c, String criteria){
        if(c.getType() == "T") return c.length(criteria);
        else System.out.println("Error. Not all cells are of type Number.");
        return -1;
    }

    public void mean(Block b1,Cell c, Boolean ref){
        if(b1.allDouble()){
            c.setType("N");
            c.changeValue(b1.mean(ref));
        }
        else System.out.println("Error. Not all cells are of type Number.");
    }

    public void median(Block b1, Cell c, Boolean ref){
        if(b1.allDouble()){
            c.setType("N");
            c.changeValue(b1.median(ref));
        }
        else System.out.println("Error. Not all cells are of type Number.");

    }

    public void var(Block b1,Cell c, Boolean ref){
        if(b1.allDouble()){
            c.setType("N");
            c.changeValue(b1.var(ref));
        }
        else System.out.println("Error. Not all cells are of type Number.");
    }

    public void covar(Block b1, Block b2, Cell c, Boolean ref){
        if(b1.allDouble() && b2.allDouble()){
            c.setType("N");
            c.changeValue(b1.covar(b1,ref));
        }
        else System.out.println("Error. Not all cells are of type Number.");
    }

    public void std(Block b1,Cell c, Boolean ref){
        if(b1.allDouble()){
            c.setType("N");
            c.changeValue(b1.std(ref));
        }
        else System.out.println("Error. Not all cells are of type Number.");

    }

    public void CPearson(Block b1, Block b2, Cell c, Boolean ref){ //crear bloque desde la celda c
        if(b1.allDouble() && b2.allDouble()){
            c.setType("N");
            c.changeValue(b1.CPearson(b2, ref));
        }
        else System.out.println("Error. Not all cells are of type Number.");
    }

    public static void main(String[] args) {

    }

}