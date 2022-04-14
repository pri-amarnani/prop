package com.pomc.classes;

import java.time.LocalDate;
import java.util.ArrayList;

public class Sheet {
    //Cell[][] celdas;
    ArrayList<ArrayList<Cell>> celdas;
    int num_sheet;
    String title;
    int num_rows;
    int num_cols;

    //add = push      remove = pop

    public void AfegirFila(int pos){
        if (pos != celdas.size()) celdas.add(new ArrayList<Cell>()); //celdas.add(celdas[celdas.size()-1]);
        for(int i = celdas.size()-2; i >= pos; --i){      //necesito funcion para cambiar el tipo
            celdas[i+1] = celdas[i];
        }
        celdas[pos] = new Cell[][]; //vaciar la info
    }

    public void AfegirColumna(int pos){
        //NO MOVER LAS CELDAS, SOLO LA INFO QUE TIENEN!!!
        for(int i = 0; i < celdas.size(); ++i){
            for(int j = celdas[0].size(); j >= pos; --j ) {
                if(j == celdas[0].size()) celdas[i].add(celdas[i][j-1]);
                else if(j == pos) celdas[i][j] = new Cell;  //vaciar la info
                else celdas[i][j] = celdas[i][j-1];
            }
        }
    }

    public void EliminarFila(int pos){
        for(int i = pos; i < celdas.size()-1; ++i){      //necesito funcion para cambiar el tipo
            celdas[i] = celdas[i+1];
        }
        celdas.remove(celdas[celdas.size()-1]); //pop?
    }
    public void EliminarColumna(int pos){
        for(int i = 0; i < celdas.size(); ++i){
            for(int j = pos; j < celdas[0].size()-1; ++j ) {
                celdas[i][j] = celdas[i][j+1];
            }
            celdas[i].remove(celdas[i][celdas[0].size()-1]);
        }
    }



    public void SeleccionarBloc(Cell c1, Cell c2){} //pasar mat
    public void seleccionarCela(int f, int c){} //necesito un getCell

    public void CopyB(Block b){
        b.CopyB();
    }


    public void MoveBlock(Block b, Cell c){}

    public void ModifyBlock(Block b, int n){ //float/double en vez de int
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


    public Cell find(int n,Block b){
        return b.find(n);
    }
    public Cell find(String s,Block b){
        return b.find(s);
    }
    public Cell find(LocalDate ld,Block b){
        return b.find(ld);
    }


    public void findAndReplace(int n, Block b){ //en vez de int tendria q ser double/float
        b.findAndReplace(n);
    }
    public void findAndReplace(String s, Block b){
        b.findAndReplace(s);
    }
    public void findAndReplace(LocalDate ld, Block b){  //que hace exactamente esta funcion? sustituye por q valor?
        b.findAndReplace(ld);
    }





    public void FuncioTruncament(Block b1, Cell c, Boolean ref){

    }



    public void convert(Block b1, Cell c, Boolean ref){
        b1.convert(b1, ref);
    }

    // public void FuncioOpAritmetiques(Block b1, Block b2, Cell c, Boolean ref){}

    public void sum(Block b1, Block b2, Block b3, Cell c, Boolean ref){
        b1.sum(b2, b3, ref);
    }

    public void mult(Block b1, Block b2, Block b3, Cell c, Boolean ref){
        b1.mult(b2, b3, ref);
    }

    public void div(Block b1, Block b2, Block b3, Cell c, Boolean ref){
        b1.div(b2, b3, ref);
    }

    public void substract(Block b1, Block b2, Block b3, Cell c, Boolean ref){
        b1.substract(b2, b3, ref);
    }

    public void extract(Block b1,Cell c, Boolean ref){
        b1.extract(b1, ref);
    }

    public void dayOfTheWeek (Block b1,Cell c, Boolean ref){
        b1.dayOfTheWeek(b1, ref);
    }

    public void replace(Block b1,Cell c, Boolean ref, String criteria){
        b1.replace(b1, criteria);
    }

    public int length(TextCell c, String criteria){
        return c.length(criteria);   //solo en textCell?
    }

    public void mean(Block b1,Cell c, Boolean ref){
        b1.mean(ref);
    }

    public void median(Block b1, Cell c, Boolean ref){
        b1.median(ref);
    }

    public void var(Block b1,Cell c, Boolean ref){
        if(c.isNum()) c.changeValueN(b1.var(ref));
        else if(c.isText()) c.changeValueT(b1.var(ref));    //siempre devuelve un float!!
        else if(c.isDate()) c.changeValueD(b1.var(ref));    //siempre devuelve un float!!
    }

    public void covar(Block b1,Cell c, Boolean ref){
        if(c.isNum()) c.changeValueN(b1.covar(b1,ref));
        else if(c.isText()) c.changeValueT(b1.covar(b1,ref));
        else if(c.isDate()) c.changeValueD(b1.covar(b1,ref)); //primero cambiar el tipo a int/float/DOUBLE
    }

    public void std(Block b1,Cell c, Boolean ref){ //que funcion??
        b1.std(ref);
    }

    public void CPearson(Block b1, Cell c, Boolean ref){
        if(c.isNum()) c.changeValueN(b1.CPearson(ref));
        else if(c.isText()) c.changeValueT(b1.CPearson(ref));
        else if(c.isDate()) c.changeValueD(b1.CPearson(ref));
    }
}