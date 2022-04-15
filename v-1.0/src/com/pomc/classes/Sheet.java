package com.pomc.classes;

import java.time.LocalDate;
import java.util.Vector;

public class Sheet {
    Vector<Vector<Cell>> cells;
    String title;
    int num_rows;
    int num_cols;
    Block b_selected;

    public Sheet(String title) {
        if(title != null) this.title = title;
        else this.title = "titulo por defecto";
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
        String n = "N";
        for(int i = 0; i < rows; ++i){
            Vector<Cell> row = new Vector<>();
            for(int j = 0; j < columns; ++j){
                row.add(new Cell(i, j, n));
            }
            cells.add(row);
        }
    }

    public void NewRow(int pos){
        ++num_rows;
        Vector<Cell> pos_row = new Vector<Cell>();
        for(int i = 0; i < num_cols; ++i){
            Cell c = new Cell(pos, i,"N");
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
            row.insertElementAt(new Cell(i,pos,"N"), pos);
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

    public Block SelectBloc(Cell c1, Cell c2){ //1 bloque seleccionado + crear otro en las aritmeticas
        //crear bloque y devolverlo
        return new Block(); //crear uno desde c1 a c2
    }

    public void CopyB(Block b){
        b.CopyB();
    }


    public void MoveBlock(Block b){  //del bloque seleccionado al bloque b

    }

    public void ModifyBlock(Block b, double n){
        b.ModifyBlock(n);
    }

    public void ModifyBlock(Block b, String s){
        b.ModifyBlock(s);
    }

    public void ModifyBlock(Block b, LocalDate ld){
        b.ModifyBlock(ld);
    }




    public void ReferenciarBloc(Block b1, Block b2){}
    public void ReferenciarCela(Cell c, Block b){}

    public void SortBlock(Block b, String Criteria){
        b.SortBlock(b,Criteria);
    }


    public Cell find(double n, Block b){
        return b.find(n);
    }
    public Cell find(String s, Block b){
        return b.find(s);
    }
    public Cell find(LocalDate ld, Block b){
        return b.find(ld);
    }


    public void findAndReplace(double n, double r, Block b){
        b.findAndReplace(n); //(n,r)
    }
    public void findAndReplace(String s, Block b){ //pasar dos doubles
        b.findAndReplace(s);
    }
    public void findAndReplace(LocalDate ld, Block b){ //pasar dos doubles
        b.findAndReplace(ld);
    }



    public void floor(Block b1,Block b2, Cell c, Boolean ref){
        b1.floor(b2, ref);
    }

    public void convert(Block b1, Cell c, Boolean ref){ //falta acabar
        b1.convert(b1, ref);
    }

    public void sum(Block b1, Block b2, Block b3, Boolean ref){
        b1.sum(b2, b3, ref);
    }

    public void mult(Block b1, Block b2, Block b3, Boolean ref){
        b1.mult(b2, b3, ref);
    }

    public void div(Block b1, Block b2, Block b3, Boolean ref){
        b1.div(b2, b3, ref);
    }

    public void substract(Block b1, Block b2, Block b3, Boolean ref){
        b1.substract(b2, b3, ref);
    }

    public void extract(Block b1,Cell c, Boolean ref){ //falta acabar
        b1.extract(b1, ref);
    }

    public void dayOfTheWeek (Block b1,Cell c, Boolean ref){ //q hace exactamente?
        b1.dayOfTheWeek(b1, ref);
    }

    public void replaceWithCriteriaText(Block b1,Cell c, Boolean ref, String criteria){
        b1.replaceWithCriteriaText(criteria);
    }

    public int length(TextCell c, String criteria){
        return c.length(criteria);
    }

    public void mean(Block b1,Cell c, Boolean ref){
        c.setType("N");
        c.changeValueN(b1.mean(ref));
    }

    public void median(Block b1, Cell c, Boolean ref){
        c.setType("N");
        c.changeValueN(b1.median(ref));
    }

    public void var(Block b1,Cell c, Boolean ref){
        c.setType("N");
        c.changeValueN(b1.var(ref));
    }

    public void covar(Block b1, Block b2, Cell c, Boolean ref){
        c.setType("N");
        c.changeValueN(b1.covar(b1,ref));
        //else (); //control de errores
    }

    public void std(Block b1,Cell c, Boolean ref){
        c.setType("N");
        c.changeValueN(b1.std(ref));
    }

    public void CPearson(Block b1, Cell c, Boolean ref){ //crear bloque desde la celda c
        Block b2 = new Block();
        c.setType("N");
        c.changeValueN(b1.CPearson(b2, ref));
    }
}