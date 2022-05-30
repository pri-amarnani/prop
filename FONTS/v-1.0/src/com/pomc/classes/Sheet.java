package com.pomc.classes;

import org.knowm.xchart.PieChart;
import org.knowm.xchart.XYChart;

import java.util.Map;
import java.util.Vector;

public class Sheet {

    Vector<Vector<Cell>> cells = new Vector<>();
    String title;
    int num_rows;
    int num_cols;
    Block b_selected;

    /**
     * Actualiza en la hoja el valor de las celdas de un bloque modificado
     * @param b
     */
    public void update(Block b){
        if (b != null) {
            Cell ul = b.ul;
            for (int i = 0; i < b.number_rows(); ++i) {
                for (int j = 0; j < b.number_cols(); ++j) {
                    cells.elementAt(ul.getRow() + i).setElementAt(b.getCell(i, j), ul.getColumn() + j);
                    //  System.out.println("SHEET PRINT: "+getCell(ul.getRow()+i,ul.getColumn()+j).getInfo());
                }
            }
        }
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

    public void setTitle (String title) {
        this.title = title;
    }

    /**
     * Crea una hoja 64x64 vacía con el título proporcionado
     * @param title
     */
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

    /**
     * Crea una hoja con el título y valor de las celdas proporcionado
     * @param cells
     * @param title
     */
    public Sheet(Cell[][] cells, String title) {

        Vector<Vector<Cell>> v_cells = new Vector<>();

        for(int i = 0; i < cells.length; ++ i ){
            Vector<Cell> row = new Vector<>();
            for(int j = 0; j < cells[0].length; ++j){
                row.add(cells[i][j]);
            }
            v_cells.add(row);
        }

        this.title = title;
        this.cells = v_cells;
        num_rows = v_cells.size();
        num_cols = v_cells.elementAt(0).size();
    }

    /**
     * Crea una hoja con el titulo proporcionado y número de filas y columnas indicado
     * @param rows
     * @param columns
     * @param title
     */
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

    /**
     * Compruba si dos hojas son iguales
     * @param sh
     * @return true si la hoja actual es igual a la hoja pasada por parámetro, false si son diferentes
     */
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

    /**
     * Cambia el valor de la celda c por el valor o
     * @param c
     * @param o
     */
    public void change_value(Cell c, Object o){
        Integer id = -1, id2 = -1;
        for(int i = 0; i < num_rows; ++i) {
            id = cells.elementAt(i).indexOf(c);
            if (id != -1) {
                id2 = i;
                break;
            }
        }
        if(c.getType()=="R"){
           ReferencedCell rcell= (ReferencedCell) c;
            Map.Entry<String,Vector<Cell>> rcellRefs=rcell.getRefInfo();
            Vector<Cell> rcellCells= rcellRefs.getValue();
            for (int i = 0; i < rcellCells.size(); i++) {
                Cell delRef=rcellCells.elementAt(i);
                System.out.println("DEPENDIA DE: "+delRef.getRow()+", "+delRef.getColumn());
                delRef.deleteRefCell(rcell);
            }
        }
        Object o1 = c.changeValue(o);
        if(id != -1) cells.elementAt(id2).setElementAt((Cell) o1, id);
        else System.out.println("Cell not found");

    }

    /**
     * Crea una grafica lineal con los parámetros indicados
     * @param title
     * @param x
     * @param y
     * @param func
     * @return Devuelve la grafica creada
     */
    public XYChart graficXY(String title, String x, String y, String func){
        return b_selected.graficXY(title, x, y, func);
    }

    /**
     * Crea una grafica Pie
     * @return devuelve la gráfica creada
     */
    public PieChart graficPie(){
        return b_selected.graficPie();
    }

    /**
     * Añade una fila en la posición indicada
     * @param pos
     */
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

    /**
     * Añade una columna en la posición indicada
     * @param pos
     */
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

    /**
     * Elimina la fila indicada
     * @param pos
     */
    public void DeleteRow(int pos){
        --num_rows;
        cells.removeElementAt(pos);
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

    /**
     * Elimina la columna indicada
     * @param pos
     */
    public void DeleteColumn(int pos){
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


    /**
     * Crea un bloque a partir de dos celdas
     * @param c1
     * @param c2
     * @return
     */
    public Block create_block(Cell c1, Cell c2){
        Cell [][] arr_block = new Cell[c2.getRow()-c1.getRow()+1][c2.getColumn()-c1.getColumn()+1];
        for(int i =0 ; i <= c2.getRow()-c1.getRow(); ++i){
            for(int j = 0; j <= c2.getColumn()-c1.getColumn(); ++j){
                arr_block[i][j] = cells.elementAt(c1.getRow()+ i).elementAt(c1.getColumn()+j);
            }
        }
        Block block = new Block(arr_block, c1, c2);
        return block;
    }

    /**
     * Selecciona un bloque a partir de dos celdas y lo guarda en el parámetro privado
     * @param c1
     * @param c2
     * @return Devuelve el bloque seleccionado
     */
    public Block SelectBlock(Cell c1, Cell c2){
        b_selected = null;
        Block b = create_block(c1,c2);
        b_selected = b;
        return b;
    }


    public int blockFirstRow(){
        return b_selected.ul.getRow();
    }
    public int blockFirstCol(){
       return b_selected.ul.getColumn();
    }

    /**
     * Mueve un bloque a otra posición de la hoja con posibilidad de referencia
     * @param b
     * @param ref
     */
    public void MoveBlock(Block b, Boolean ref){
        b_selected.ref(b, ref);
        update(b);
    }

    /**
     * Calcula la longitud de las celdas de un bloque y las guarda en otro bloque, puede tener referencia
     * @param b
     * @param ref
     * @param criteria
     */
    public void lengthBlock(Block b, Boolean ref, String criteria){
        b_selected.length(b, ref, criteria);
        update(b);
    }

    /**
     * Cambia el valor de las celdas de un bloque por el valor pasado por parámetro
     * @param o
     */
    public void ModifyBlock(Object o){

        b_selected.ModifyBlock(o);
        update(b_selected);
    }

    /**
     * Ordena un bloque en función de la columna indicada y siguiendo el criterio indicado
     * @param n_col
     * @param Criteria
     */
    public boolean SortBlock(int n_col, String Criteria){
        Cell c = b_selected.getCell(0,n_col);
        Cell c2 = b_selected.getCell(b_selected.number_rows()-1,n_col);

        Block b = create_block(c, c2);

        b_selected.SortBlock(n_col, Criteria, c.getType());
        update(b_selected);
        return true;
    }

    /**
     * Busca las celdas con valor igual al indicado
     * @param o
     * @return Devuelve un vector con las celdas
     */
    public Vector<Cell> find(Object o){
        return b_selected.find(o);
    }

    /**
     * Busca las celdas con valor n y modifica su valor poniendo r
     * @param n
     * @param r
     * @return devuelve un array con las posiciones de las celdas modificadas
     */
    public Object[] findAndReplace(Object n, Object r){
        Object[] result = b_selected.findAndReplace(n, r);
        update(b_selected);
        return result;
    }

    /**
     * Comprueba si dos bloques coinciden en alguna celda
     * @param b1
     * @param b2
     * @return true si comparten celdas, false si no comparten
     */
    public Boolean overlapping(Block b1, Block b2){
        if(b1.dr.getColumn() < b2.ul.getColumn()) return false;
        if(b1.dr.getRow() < b2.ul.getRow()) return false;
        if(b2.dr.getColumn() < b1.ul.getColumn()) return false;
        if(b2.dr.getRow() < b1.ul.getRow()) return false;
        return true;
    }

    /**
     * Llama a la funcion de bloque que hace las operaciones unitarias
     * @param op
     * @param x
     */
    public void opBlock(String op, double x){
        b_selected.opBlock(op, x);
        update(b_selected);
    }

    /**
     * Llama a la función de bloque que edita la celda dejando solo su parte entera
     * @param b
     * @param ref
     */
    public void floor(Block b, Boolean ref){
        b_selected.floor(b, ref);
        update(b);
    }

    /**
     * Llama a la función de bloque que redondea el valor (numérico) de la celda hacia el entero superior
     * @param b
     * @param ref
     */
    public void ceil(Block b, Boolean ref){
        b_selected.ceiling(b, ref);
        update(b);
    }

    /**
     * Convierte el valor de las celdas de una unidad métrica a otra
     * @param b
     * @param ref
     * @param from
     * @param to
     */
    public void convert(Block b, Boolean ref, String from, String to){
        b_selected.convert(b, ref, from, to);
        update(b);
    }

    /**
     * Hace la suma de dos bloques y escribe el resultado en un tercer bloque, puede tener referencias
     * @param b1
     * @param b2
     * @param ref
     */
    public void sum(Block b1, Block b2, Boolean ref){
        b_selected.sum(b1, b2, ref);
        update(b2);
    }

    /**
     * Hace la multiplicación de dos bloques y escribe el resultado en otro bloque, puede tener referencias
     * @param b1
     * @param b2
     * @param ref
     */
    public void mult(Block b1, Block b2, Boolean ref){
        b_selected.mult(b1, b2, ref);
        update(b2);
    }

    /**
     * Hace la división de dos bloques y escribe el resultado en un tercer bloque, puede tener referencia
     * @param b1
     * @param b2
     * @param ref
     */
    public void div(Block b1, Block b2, Boolean ref){
        b_selected.div(b1, b2, ref);
        update(b2);
    }

    /**
     * Hace la resta de dos bloques y escribe el resultado en un tercer bloque, puede tener referencia
     * @param b1
     * @param b2
     * @param ref
     */
    public void substract(Block b1, Block b2, Boolean ref){
        b_selected.substract(b1, b2, ref);
        update(b2);
    }


    /**
     * Hace la concatenacion de las celdas de dos bloques y la escribe en un tercer bloque, puede tener referencia
     * @param b1
     * @param b2
     * @param ref
     */
    public void concatenate(Block b1, Block b2, Boolean ref){
        b_selected.concatenate(b1, b2, ref);
        update(b2);
    }

    /**
     * Obtiene el día/mes/año de las fechas y lo escribe en otro bloque, puede tener referencia
     * @param b1
     * @param ref
     * @param ex
     */
    public void extract(Block b1,Boolean ref, String ex){
        b_selected.extract(b1, ref, ex);
        update(b1);
    }

    /**
     * Obtiene los días de la semana de unas fechas y los escribe en otro bloque, puede tener referencia
     * @param b
     * @param ref
     */
    public void dayOfTheWeek (Block b, Boolean ref){
        b_selected.dayOfTheWeek(b, ref);
        update(b);
    }

    /**
     * Cambia a mayúsculas/minúsculas o la primera letra a mayúscula de las celdas tipo texto
     * @param criteria
     */
    public void replaceWithCriteriaText(String criteria){
        b_selected.replaceWithCriteriaText(criteria);
        update(b_selected);
    }

    /**
     * Quita los espacios al inicio y final de una celda de tipo texto
     */
    public void trim(){
        b_selected.trim();
        update(b_selected);
    }

    /**
     * Cuenta el numero de celdas que cumplen la condicion indicada (==,<=,>=,<, >)
     * Escribe el número en la celda indicada, puede tener referencia
     * @param c
     * @param ref
     * @param eq
     * @param criteria
     * @return devuelve el numero de celdas que cumplen
     */
    public Double countIf(Cell c, Boolean ref, double eq, String criteria){
        Cell m = b_selected.countIf(c, ref, eq, criteria);
        cells.elementAt(m.getRow()).setElementAt(m, m.getColumn());
        if (ref) return (Double) m.getContent();
        else return (Double) m.getInfo();
    }

    /**
     * Encuntra la celda con valor máximo en un bloque, puede tener referencia
     * @param c
     * @param ref
     * @return devuelve el valor de la celda
     */
    public Double max(Cell c, Boolean ref){
        Cell m = b_selected.max(c, ref);
        cells.elementAt(m.getRow()).setElementAt(m, m.getColumn());
        if (ref) return (Double) m.getContent();
        else return (Double) m.getInfo();
    }

    /**
     * Encuentra la celda con valor mínimo en un bloque, puede tener referencia
     * @param c
     * @param ref
     * @return devuelve el valor de la celda
     */
    public Double min(Cell c, Boolean ref){
        Cell m = b_selected.min(c, ref);
        cells.elementAt(m.getRow()).setElementAt(m, m.getColumn());
        if (ref) return (Double) m.getContent();
        else return (Double) m.getInfo();
    }

    /**
     * Suma todos los valores de un bloque y escribe la suma en una celda, puede tener referencia
     * @param c
     * @param ref
     * @return devuelve la suma
     */
    public Double sumAll(Cell c, Boolean ref){
        Cell m = b_selected.sumAll(c, ref);
        cells.elementAt(m.getRow()).setElementAt(m, m.getColumn());
        if (ref) return (Double) m.getContent();
        else return (Double) m.getInfo();
    }

    /**
     * Resta al primer valor del bloque el resto y escribe la resta en una celda, puede tener referencia
     * @param c
     * @param ref
     * @return devuelve el valor de la resta
     */
    public Double subAll(Cell c, Boolean ref){
        Cell m = b_selected.subAll(c, ref);
        cells.elementAt(m.getRow()).setElementAt(m, m.getColumn());
        if (ref) return (Double) m.getContent();
        else return (Double) m.getInfo();
    }

    /**
     * Multiplica todos los valores de un bloque y escribe la multiplicación en una celda, puede tener referencia
     * @param c
     * @param ref
     * @param val
     * @return devuelve el valor de la división
     */
    public Double multAll(Cell c, Boolean ref, Boolean val){
        Cell m = b_selected.multAll(c, ref);
        cells.elementAt(m.getRow()).setElementAt(m, m.getColumn());
        if (ref) return (Double) m.getContent();
        else return (Double) m.getInfo();
    }

    /**
     * Divide al primer valor del bloque el resto y escribe la división en una celda, puede tener referencia
     * @param c
     * @param ref
     * @param val
     * @return devuelve el valor de la multiplicación
     */
    public Double divAll(Cell c, Boolean ref, Boolean val){
        Cell m = b_selected.divAll(c, ref);
        cells.elementAt(m.getRow()).setElementAt(m, m.getColumn());
        if (ref) return (Double) m.getContent();
        else return (Double) m.getInfo();
    }

    /**
     * Hace la media de los valores de un bloque y escribe el resultado en una celda
     * Puede tener referencias
     * @param c
     * @param ref
     * @return devuelve el valor de la media
     */
    public Double mean(Cell c, Boolean ref){
        Cell m = b_selected.mean(c, ref);
        cells.elementAt(m.getRow()).setElementAt(m, m.getColumn());
        if (ref) return (Double) m.getContent();
        else return (Double) m.getInfo();
    }

    /**
     * Encuentra la mediana de los valores de un bloque y escribe el resultado en una celda
     * @param c
     * @param ref
     * @return devuelve el valor de la mediana
     */
    public Double median(Cell c, Boolean ref){
        Cell m = b_selected.median(c, ref);
        cells.elementAt(m.getRow()).setElementAt(m, m.getColumn());
        if (ref) return (Double) m.getContent();
        else return (Double) m.getInfo();
    }

    /**
     * Hace la varianza de un bloque y escribe el resultado en una celda
     * Puede tener referencia
     * @param c
     * @param ref
     * @return devuelve el valor de la varianza
     */
    public Double var(Cell c, Boolean ref){
        Cell m = b_selected.var(c, ref);
        cells.elementAt(m.getRow()).setElementAt(m, m.getColumn());
        if (ref) return (Double) m.getContent();
        else return (Double) m.getInfo();
    }

    /**
     * Hace la covarianza de dos bloques y escribe el valor en una celda
     * Puede tener referencias
     * @param b
     * @param c
     * @param ref
     * @return devuelve el valor de la covarianza
     */
    public Double covar(Block b, Cell c, Boolean ref){
        Cell m = b_selected.covar(b, c, ref);
        cells.elementAt(m.getRow()).setElementAt(m, m.getColumn());
        if (ref) return (Double) m.getContent();
        else return (Double) m.getInfo();
    }

    /**
     * Calcula la desviación estándard de un bloque y guarda el valor en una celda
     * Puede tener referencia
     * @param c
     * @param ref
     * @return devuelve el valor de la desviación estandard
     */
    public Double std(Cell c, Boolean ref){
        Cell m = b_selected.std(c, ref);
        cells.elementAt(m.getRow()).setElementAt(m, m.getColumn());
        if (ref) return (Double) m.getContent();
        else return (Double) m.getInfo();
    }

    /**
     * Calcula el coeficiente de pearson entre dos bloques y escribe el resultado en una celda
     * Puede tener referencia
     * @param b
     * @param c
     * @param ref
     * @return devuelve el valor del coeficiente de pearson
     */
    public Double CPearson(Block b, Cell c, Boolean ref){
        Cell m = b_selected.CPearson(b, c, ref);
        cells.elementAt(m.getRow()).setElementAt(m, m.getColumn());
        if (ref) return (Double) m.getContent();
        else return (Double) m.getInfo();
    }

}