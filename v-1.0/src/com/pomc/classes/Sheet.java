package com.pomc.classes;

//import java.time.LocalDate;
import java.util.Date;
import java.util.Vector;

public class Sheet {
    Vector<Vector<Cell>> celdas;
    String title;
    int num_rows;
    int num_cols;

    public Sheet(String title) {
        this.title = title;
        celdas = null;
        num_cols = 0;
        num_rows = 0;
    }

    public Sheet(Vector<Vector<Cell>> celdas, String title) {  //not sure
        this.title = title;
        this.celdas = celdas;
        num_rows = celdas.size();
        num_cols = celdas.elementAt(0).size();
    }

    public Sheet(int rows, int columns, String title){
        this.num_rows = rows;
        this.num_cols = columns;
        this.title = title;
        cells(rows, columns);
    }

    public void cells(int rows, int cols){ //las celdas por defecto son numericas
        title = "titulo por defecto"; //poner titulo por defecto
        String n = "N";
        for(int i = 0; i < rows; ++i){
            celdas.add(new Vector<Cell>(rows));
            for(int j = 0; j < cols; ++j){
                //celdas.elementAt(i).elementAt(j) = new Cell(i, j, n);     //creadora de cell?
                //-> Cell' is abstract; cannot be instantiated
            }
        }
    }

    /*
    public void NewRow(int pos){  //docSheets.removeElement(i);
        ++num_rows;
        if (pos != celdas.size()) celdas.add(new ArrayList<Cell>()); //celdas.add(celdas[celdas.size()-1]);
        for(int i = celdas.size()-2; i >= pos; --i){      //necesito funcion para cambiar el tipo
            celdas[i+1] = celdas[i];
        }
        celdas[pos] =new  Cell[][]; //vaciar la info
    }

    public void NewColumn(int pos){
        ++num_cols;
        //NO MOVER LAS CELDAS, SOLO LA INFO QUE TIENEN!!!
        for(int i = 0; i < celdas.size(); ++i){
            for(int j = celdas[0].size(); j >= pos; --j ) {
                if(j == celdas[0].size()) celdas[i].add(celdas[i][j-1]);
                else if(j == pos) celdas[i][j] = new Cell;  //vaciar la info
                else celdas[i][j] = celdas[i][j-1];
            }
        }
    }

    public void DeleteRow(int pos){
        --num_rows; //salta si solo hay una fila
        for(int i = pos; i < celdas.size()-1; ++i){      //necesito funcion para cambiar el tipo
            celdas[i] = celdas[i+1];
        }
        celdas.remove(celdas[celdas.size()-1]); //pop?
    }
    public void DeleteColumn(int pos){
        --num_cols; //salta si solo hay una columna
        for(int i = 0; i < celdas.size(); ++i){
            for(int j = pos; j < celdas[0].size()-1; ++j ) {
                celdas[i][j] = celdas[i][j+1];
            }
            celdas[i].remove(celdas[i][celdas[0].size()-1]);
        }
    }

*/

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

    public void ModifyBlock(Block b, Date ld){
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
    public Cell find(Date ld, Block b){
        return b.find(ld);
    }


    public void findAndReplace(double n, double r, Block b){
        b.findAndReplace(n); //(n,r)
    }
    public void findAndReplace(String s, Block b){
        b.findAndReplace(s);
    }
    public void findAndReplace(Date ld, Block b){
        b.findAndReplace(ld);
    }



    public void floor(Block b1,Block b2, Cell c, Boolean ref){
        b1.floor(b2, ref);
    }

    public void convert(Block b1, Cell c, Boolean ref){
        b1.convert(b1, ref);
    }

    public void sum(Block b1, Block b2, Block b3, Boolean ref){  //lo guardamos en cell?
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

    public void extract(Block b1,Cell c, Boolean ref){
        b1.extract(b1, ref);
    }

    public void dayOfTheWeek (Block b1,Cell c, Boolean ref){
        b1.dayOfTheWeek(b1, ref);
    }

    public void replaceWithCriteriaText(Block b1,Cell c, Boolean ref, String criteria){
        b1.replaceWithCriteriaText(criteria);
    }

    public int length(TextCell c, String criteria){
        return c.length(criteria);
    }

    public void mean(Block b1,Cell c, Boolean ref){
        b1.mean(ref);
    }

    public void median(Block b1, Cell c, Boolean ref){
        b1.median(ref);
    }

    public void var(Block b1,Cell c, Boolean ref){
        if(c.isNum()) c.changeValueN(b1.var(ref));
        else if(c.isText()){
            //cambiar el tipo de la celda a numerica
            //c.changeValueT(b1.var(ref));
        }
        else if(c.isDate()){
            //cambiar el tipo de la celda a numerica
            //c.changeValueD(b1.var(ref));
        }
    }

    public void covar(Block b1, Block b2, Cell c, Boolean ref){
        if(c.isNum()) c.changeValueN(b1.covar(b1,ref));
        else if(c.isText()){
            //cambiar el tipo de la celda a numerica
            //c.changeValueT(b1.covar(b1,ref));    //siempre devuelve un float!!
        }
        else if(c.isDate()){
            //cambiar el tipo de la celda a numerica
            //c.changeValueD(b1.covar(b1,ref));
        }
        //else (); //control de errores
    }

    public void std(Block b1,Cell c, Boolean ref){  //mirar en todas de que tipo es el bloque (numerico)
        b1.std(ref);
    }

    public void CPearson(Block b1, Cell c, Boolean ref){ //crear bloque desde la celda c
        Block b2 = new Block();
        if(c.isNum()) c.changeValueN(b1.CPearson(b2, ref));
        else if(c.isText()){
            //cambiar el tipo de la celda a numerica
            //c.changeValueN(b1.CPearson(b2, ref));
        }
        else if(c.isDate()){
            //cambiar el tipo de la celda a numerica
            //c.changeValueN(b1.CPearson(b2, ref));
        }
    }
}