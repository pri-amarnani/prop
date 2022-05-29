package com.pomc.classes;

import org.knowm.xchart.PieChart;
import org.knowm.xchart.QuickChart;
import org.knowm.xchart.XYChart;

import java.time.LocalDate;
import java.util.*;

import static java.lang.Math.abs;

public class Block {
    Cell [][] block;
    Cell ul;
    Cell dr;
    int size_r;
    int size_c;

    public Block (Cell [][] b, Cell ul, Cell dr) {
        this.block = b;

        this.ul = ul;
        this.dr = dr;

        this.size_r = this.block.length;
        this.size_c = this.block[0].length;
    }

    public Cell getCell (int i, int j) {
        return this.block[i][j];
    }

    public int number_rows() {
        return this.size_r;
    }

    public int number_cols() {
        return this.size_c;
    }

    public void setCell(int i, int j, Cell c) {
        this.block[i][j] = c;
    }

    public boolean allDouble() {
        for (int i = 0; i < this.block.length; ++i) {
            for (int j = 0; j < this.block[0].length; ++j) {
                //System.out.println(this.block[i][j].getRow() +" "+this.block[i][j].getColumn());
                if (this.block[i][j].getInfo() == null){
                    //System.out.println(this.block[i][j].getRow() +" ola "+this.block[i][j].getColumn() + " " + this.block[i][j].getType());
                    return false;
                }
                else if (!this.block[i][j].isNum()) {
                    //System.out.println(this.block[i][j].getRow() +" qw1 "+this.block[i][j].getColumn());
                    return false;
                }
            }

        }

        return true;
    }

    public boolean allText() {
        for (int i = 0; i < this.block.length; ++i) {
            for (int j = 0; j < this.block[0].length; ++j)
                if (!this.block[i][j].isText()) return false;
        }

        return true;
    }

    public boolean allDate() {
        for (int i = 0; i < this.block.length; ++i) {
            for (int j = 0; j < this.block[0].length; ++j) {
                if (!this.block[i][j].isDate()) return false;
            }
        }
        return true;
    }

    public boolean isEqual(Block b) {

        if (b.number_cols() != this.block[0].length || b.number_rows() != this.block.length) return false;

        for (int i = 0; i < b.number_rows(); ++i) {
            for (int j = 0; j < b.number_cols(); ++j) {
                if (!b.getCell(i,j).getInfo().equals(this.block[i][j].getInfo())) return false;
            }
        }
        return true;
    }

    public XYChart graficXY(String title, String x, String y, String func){
        Cell [][] aux = new Cell[this.block.length][this.block[0].length];

        for (int i = 0; i < this.block.length; ++i) {
            for (int j = 0; j < this.block[0].length; ++j) {
                aux[i][j] = block[i][j];
            }
        }

        Arrays.sort(aux, (a, b) -> Double.compare((double) a[0].getInfo(), (double) b[0].getInfo()));

        double[] xData = new double[block.length];
        double[] yData = new double[block.length];

        for (int i = 0; i < block.length; i++) {
            xData[i] = (double) aux[i][0].getInfo();
        }
        for (int i = 0; i < block.length; i++) {
            yData[i] = (double) aux[i][1].getInfo();
        }

        // Create Chart
        XYChart chart = QuickChart.getChart(title, x, y, func, xData, yData);
        return chart;
    }

    public PieChart graficPie(){
        PieChart ch = new PieChart(500, 500);

        String[] labels = new String[this.block.length];
        double[] values = new double[this.block.length];

        for (int i = 0; i < this.block.length; ++i) {
            labels[i] = (String) this.block[i][0].getInfo();
        }

        for (int i = 0; i < this.block.length; ++i) {
            values[i] = (double) this.block[i][1].getInfo();
        }

        for (int i = 0; i < this.block.length; ++i) {
            ch.addSeries(labels[i], values[i]);
        }

        return ch;
    }

    public void CopyB () {
        // copy to clipboard
    }

    public void opBlock (String op, double x) {

        for (int i = 0; i < this.block.length; ++i) {
            for (int j = 0; j < this.block[0].length; ++j) {

                if (this.block[i][j].isNum() && this.block[i][j].getInfo() != null ) {
                    if(op == "Addition") {
                        Cell n = this.block[i][j];
                        this.block[i][j].changeValue((Double) this.block[i][j].getInfo() + x);
                        setCell(i, j, n);
                    }
                    if(op == "Substraction") {
                        Cell n = this.block[i][j];
                        this.block[i][j].changeValue((Double) this.block[i][j].getInfo() - x);
                        setCell(i, j, n);
                    }
                    if(op == "Division") {
                        Cell n = this.block[i][j];
                        this.block[i][j].changeValue((Double) this.block[i][j].getInfo() / (Double) x);
                        setCell(i, j, n);
                    }
                    if(op == "Multiplication") {
                        Cell n = this.block[i][j];
                        this.block[i][j].changeValue((Double) this.block[i][j].getInfo() * x);
                        setCell(i, j, n);
                    }
                    if(op == "Modulus") {
                        Cell n = this.block[i][j];
                        this.block[i][j].changeValue((Double) this.block[i][j].getInfo() % x);
                        setCell(i, j, n);
                    }
                    if(op == "Power") {
                        Cell n = this.block[i][j];
                        this.block[i][j].changeValue( Math.pow((Double) this.block[i][j].getInfo(), x));
                        setCell(i, j, n);
                    }
                }
            }
        }
        ul= getCell(0,0);
        dr= getCell(size_r-1,size_c-1);
    }

    public void trim () {
        for (int i = 0; i < this.block.length; ++i) {
            for (int j = 0; j < this.block[0].length; ++j) {
                TextCell tc = (TextCell) this.block[i][j];

                this.block[i][j].changeValue(((String) tc.getInfo()).trim());
            }
        }
        ul= getCell(0,0);
        dr= getCell(size_r-1,size_c-1);
    }

    public void ceiling (Block b, Boolean ref) {
        for (int i = 0; i < this.block.length; ++i) {
            for (int j = 0; j < this.block[0].length; ++j) {

                if (this.block[i][j].isNum()) {
                    Cell n = (Cell) b.getCell(i, j).changeValue(Math.ceil((double) this.block[i][j].getInfo()));
                    b.setCell(i,j,n);
                    if (ref) {
                        ReferencedCell rc = new ReferencedCell(n.getRow(),n.getColumn(),"=ceil");
                        rc.setContent(n.getInfo());
                        b.setCell(i, j, rc);
                        Vector<Cell> s = new Vector<>(1);
                        s.add(this.block[i][j]);
                        this.block[i][j].AddRef((ReferencedCell) b.getCell(i, j));
                        Map.Entry<String, Vector<Cell>> r = new AbstractMap.SimpleEntry<>("ceil", s);

                        b.getCell(i, j).setRefInfo(r);

                        n = null;
                    }
                }
            }
        }
        b.ul=b.getCell(0,0);
        b.dr=b.getCell(b.size_r-1,b.size_c-1);
    }

    public Cell max (Cell c, Boolean ref) {
        boolean first = true;
        double max = 0;
        Vector<Cell> s = new Vector<>();

        for (Cell[] cells : this.block) {
            for (int j = 0; j < this.block[0].length; ++j) {
                if (cells[j] != null) {
                    if (first) max = (double) cells[j].getInfo();
                    else max = Math.max(max, (double) cells[j].getInfo());
                    first = false;
                    s.add(cells[j]);
                }
            }
        }

        c = (Cell) c.changeValue(max);

        if (ref) {
            //System.out.println("entra1");
            ReferencedCell rc = new ReferencedCell(c.getRow(),c.getColumn(),"=max");
            rc.setContent(c.getInfo());
            c = rc;
            Map.Entry<String, Vector<Cell>> r = new AbstractMap.SimpleEntry<>("max", s);
            c.setRefInfo(r);
            for (int x=0;x<s.size();x++){
                Cell a= s.elementAt(x);
                a.AddRef(rc);
            }
        }

        return c;
    }

    // criteria == <, >, ==, >=, <=
    public Cell countIf (Cell c, Boolean ref, double val, String criteria) {
        double count = 0;
        Vector<Cell> s = new Vector<>();

        for (Cell[] cells : this.block) {
            for (int j = 0; j < this.block[0].length; ++j) {
                if (cells[j] != null) {
                    if (Objects.equals(criteria, "==")) {
                        if ((double) cells[j].getInfo() == val) ++count;
                    }
                    else if (Objects.equals(criteria, "<")) {
                        if ((double) cells[j].getInfo() < val) ++count;
                    }
                    else if (Objects.equals(criteria, ">")) {
                        if ((double) cells[j].getInfo() > val) ++count;
                    }
                    else if (Objects.equals(criteria, "<=")) {
                        if ((double) cells[j].getInfo() <= val) ++count;
                    }
                    else if (Objects.equals(criteria, ">=")) {
                        if ((double) cells[j].getInfo() >= val) ++count;
                    }
                    s.add(cells[j]);
                }
            }
        }

        c = (Cell) c.changeValue(count);

        if (ref) {
            ReferencedCell rc = new ReferencedCell(c.getRow(),c.getColumn(),"=countIf"+ criteria + val);
            rc.setContent(c.getInfo());
            c = rc;
            Map.Entry<String, Vector<Cell>> r = new AbstractMap.SimpleEntry<>("countIf"+ criteria + val, s);
            c.setRefInfo(r);
            for (int x=0;x<s.size();x++){
                Cell a= s.elementAt(x);
                a.AddRef(rc);
            }
        }

        return c;
    }

    public Cell min (Cell c, Boolean ref) {
        boolean first = true;
        double min = 0;
        Vector<Cell> s = new Vector<>();

        for (Cell[] cells : this.block) {
            for (int j = 0; j < this.block[0].length; ++j) {
                if (cells[j] != null) {
                    if (first) min = (double) cells[j].getInfo();
                    else min = Math.min(min, (double) cells[j].getInfo());
                    first = false;
                    s.add(cells[j]);
                }
            }
        }

        c = (Cell) c.changeValue(min);

        if (ref) {
            //System.out.println("entra1");
            ReferencedCell rc = new ReferencedCell(c.getRow(),c.getColumn(),"=min");
            rc.setContent(c.getInfo());
            c = rc;
            Map.Entry<String, Vector<Cell>> r = new AbstractMap.SimpleEntry<>("min", s);
            c.setRefInfo(r);
            for (int x=0;x<s.size();x++){
                Cell a= s.elementAt(x);
                a.AddRef(rc);
            }
        }

        return c;
    }

    public void length(Block b, Boolean ref, String criteria){
        for (int i = 0; i < this.block.length; ++i) {
            for (int j = 0; j < this.block[0].length; ++j) {
                if (this.block[i][j].getInfo() != null) {
                    TextCell c = (TextCell)  this.block[i][j];
                    b.getCell(i,j).changeValue((Double) c.length(criteria));
                    Cell n = (Cell) b.getCell(i,j);
                    b.setCell(i,j,n);
                    if (ref) {
                        ReferencedCell rc = new ReferencedCell(n.getRow(),n.getColumn(),"length" + criteria);
                        rc.setContent(n.getInfo());
                        b.setCell(i, j, rc);
                        Vector<Cell> s = new Vector<>(1);
                        s.add(this.block[i][j]);
                        this.block[i][j].AddRef((ReferencedCell) b.getCell(i, j));
                        Map.Entry<String, Vector<Cell>> r = new AbstractMap.SimpleEntry<>("length" + criteria, s);
                        b.getCell(i, j).setRefInfo(r);
                        n = null;
                    }
                }
            }
        }
        b.ul = b.getCell(0,0);
        b.dr = b.getCell(b.size_r -1,b.size_c -1);
    }

    //copy values from one block to another
    public void ref(Block b, Boolean ref) {
        for (int i = 0; i < this.block.length; ++i) {
            for (int j = 0; j < this.block[0].length; ++j) {
                Cell n =  b.getCell(i,j);
                if (this.block[i][j].getInfo() != null) {

                    n = (Cell) b.getCell(i,j).changeValue(this.block[i][j].getInfo());
                    b.setCell(i,j,n);

                }
                if (ref) {
                    ReferencedCell rc = new ReferencedCell(n.getRow(),n.getColumn(),"=equal");
                    rc.setContent(n.getInfo());
                    b.setCell(i, j, rc);
                    Vector<Cell> s = new Vector<>(1);
                    s.add(this.block[i][j]);
                    this.block[i][j].AddRef((ReferencedCell) b.getCell(i, j));
                    Map.Entry<String, Vector<Cell>> r = new AbstractMap.SimpleEntry<>("equal", s);
                    b.getCell(i, j).setRefInfo(r);
                    n = null;
                }
            }
        }
        b.ul = b.getCell(0,0);
        b.dr = b.getCell(b.size_r -1,b.size_c -1);
    }


    public void ModifyBlock(Object n) {
        for (int i=0; i<this.block.length;++i) {
            for (int j = 0; j < this.block[0].length; ++j) {
                Cell old = getCell(i,j);
                Cell nw= (Cell) old.changeValue(n);
                this.setCell(i,j,nw);
            }
        }
        ul = this.block[0][0];
        dr = this.block[size_r-1][size_c-1];
    }

    public void SortBlock (int n_col, String criteria, String type) {
        int firstCol=ul.getColumn();
        int firstRow=ul.getRow();

        if (Objects.equals(criteria, "<")) {
            if (Objects.equals(type, "N")) {
                Arrays.sort(block, (a, b) -> Double.compare((double) b[n_col].getInfo(), (double) a[n_col].getInfo()));
            }
            else if (Objects.equals(type, "T")) {
                Arrays.sort(block, (a, b) -> ((String) b[n_col].getInfo()).compareTo((String) a[n_col].getInfo()));
            }

            else if (Objects.equals(type, "D")) {
                Arrays.sort(block, (a, b) -> ((LocalDate) b[n_col].getInfo()).compareTo((LocalDate) a[n_col].getInfo()));
            }
        }

        // >
        else {
            if (Objects.equals(type, "N")) {
                Arrays.sort(block, (a, b) -> Double.compare((double) a[n_col].getInfo(), (double) b[n_col].getInfo()));
            }
            else if (Objects.equals(type, "T")) {
                Arrays.sort(block, (a, b) -> ((String) a[n_col].getInfo()).compareTo((String) b[n_col].getInfo()));
            }

            else if (Objects.equals(type, "D")) {
                Arrays.sort(block, (a, b) -> ((LocalDate) a[n_col].getInfo()).compareTo((LocalDate) b[n_col].getInfo()));
            }
        }


        for (int i = 0; i < size_r; ++i) {
            for (int j = 0; j < size_c; ++j) {
                this.block[i][j].setRow(firstRow+i);
                this.block[i][j].setColumn(firstCol+j);
            }
        }
        ul = this.block[0][0];
        dr = this.block[size_r-1][size_c-1];
    }

    public Cell find (Object n) {
        for (Cell[] cells : block) {
            for (int j = 0; j < block[0].length; ++j) {
                if (Objects.equals(cells[j].getInfo(), n)) return cells[j];
            }
        }
        return null;
    }

    public Object[] findAndReplace (Object n, Object change) {
        Vector<Integer> results = new Vector<>();
        for (Cell[] cells : block) {
            for (int j = 0; j < block[0].length; ++j) {
                if (Objects.equals(cells[j].getInfo(), n)) {
                    results.add(cells[j].getRow());
                    results.add(cells[j].getColumn());
                    Cell nw = (Cell) cells[j].changeValue(change);
                    cells[j] = nw;
                }
            }
        }

        return  results.toArray();
    }

    public void floor (Block b, Boolean ref) {
        for (int i = 0; i < this.block.length; ++i) {
            for (int j = 0; j < this.block[0].length; ++j) {

                if (this.block[i][j].isNum()) {
                    Cell n = (Cell) b.getCell(i, j).changeValue(Math.floor((double) this.block[i][j].getInfo()));
                    b.setCell(i,j,n);
                    if (ref) {
                        ReferencedCell rc = new ReferencedCell(n.getRow(),n.getColumn(),"=floor");
                        rc.setContent(n.getInfo());
                        b.setCell(i, j, rc);
                        Vector<Cell> s = new Vector<>(1);
                        s.add(this.block[i][j]);
                        this.block[i][j].AddRef((ReferencedCell) b.getCell(i, j));
                        Map.Entry<String, Vector<Cell>> r = new AbstractMap.SimpleEntry<>("floor", s);

                        b.getCell(i, j).setRefInfo(r);

                        n = null;
                    }
                }
            }
        }
        b.ul=b.getCell(0,0);
        b.dr=b.getCell(b.size_r-1,b.size_c-1);
    }

    public void convert (Block b, Boolean ref, String from, String to) {
        for (int i = 0; i < this.block.length; ++i) {
            for (int j = 0; j < this.block[0].length; ++j) {
                NumCell N = (NumCell) this.block[i][j];

                Cell n = (Cell) b.getCell(i, j).changeValue(N.conversion(from,to));
                b.setCell(i,j,n);
                if (ref) {
                    ReferencedCell rc = new ReferencedCell(n.getRow(),n.getColumn(),"=convert");
                    rc.setContent(n.getInfo());
                    b.setCell(i,j,rc);
                    Vector<Cell> s = new Vector<>(1);
                    s.add(N);
                    N.AddRef((ReferencedCell) b.getCell(i,j));
                     Map.Entry<String, Vector<Cell>> r = new AbstractMap.SimpleEntry<>(from + "TO" + to, s);
                     b.getCell(i, j).setRefInfo(r);

                    n=null;
                }
            }
        }
        b.ul=b.getCell(0,0);
        b.dr=b.getCell(b.size_r-1,b.size_c-1);
    }

    public void concatenate (Block b1, Block b2, Boolean ref) {
        for (int i = 0; i < this.block.length; ++i) {
            for (int j = 0; j < this.block[0].length; ++j) {
                //System.out.println("concatenate");
                Cell n = (Cell) b2.getCell(i, j).changeValue((String) this.block[i][j].getInfo() + (String) b1.getCell(i, j).getInfo());
                b2.setCell(i,j,n);
                if (ref) {
                    ReferencedCell rc = new ReferencedCell(n.getRow(),n.getColumn(),"=concatenate");
                    rc.setContent(n.getInfo());
                    b2.setCell(i,j,rc);
                    Vector<Cell> s = new Vector<>(2);
                    s.add(this.block[i][j]);
                    s.add(b1.getCell(i, j));
                    this.block[i][j].AddRef(rc);
                    b1.getCell(i, j).AddRef(rc);
                    Map.Entry<String, Vector<Cell>> r = new AbstractMap.SimpleEntry<>("concatenate", s);

                    b2.getCell(i, j).setRefInfo(r);

                    n=null;
                }
            }
        }
        b2.ul=b2.getCell(0,0);
        b2.dr=b2.getCell(b2.size_r-1,b2.size_c-1);
    }

    public void sum (Block b1, Block b2, Boolean ref) {
        for (int i = 0; i < this.block.length; ++i) {
            for (int j = 0; j < this.block[0].length; ++j) {
                //System.out.println("suma");
                Cell n = (Cell) b2.getCell(i, j).changeValue((double) this.block[i][j].getInfo() + (double) b1.getCell(i, j).getInfo());
                b2.setCell(i,j,n);
                if (ref) {
                    ReferencedCell rc = new ReferencedCell(n.getRow(),n.getColumn(),"=sum");
                    rc.setContent(n.getInfo());
                    b2.setCell(i,j,rc);
                    Vector<Cell> s = new Vector<>(2);
                    s.add(this.block[i][j]);
                    s.add(b1.getCell(i, j));
                    this.block[i][j].AddRef(rc);
                    b1.getCell(i, j).AddRef(rc);
                    Map.Entry<String, Vector<Cell>> r = new AbstractMap.SimpleEntry<>("sum", s);

                    b2.getCell(i, j).setRefInfo(r);

                    n=null;
                }
            }
        }
        b2.ul=b2.getCell(0,0);
        b2.dr=b2.getCell(b2.size_r-1,b2.size_c-1);
    }

    public void mult (Block b1, Block b2, Boolean ref) {
        for (int i = 0; i < this.block.length; ++i) {
            for (int j = 0; j < this.block[0].length; ++j) {

                Cell n = (Cell) b2.getCell(i, j).changeValue((double) this.block[i][j].getInfo() * (double) b1.getCell(i, j).getInfo());
                b2.setCell(i,j,n);
                if (ref) {
                    ReferencedCell rc = new ReferencedCell(n.getRow(),n.getColumn(),"=mult");
                    rc.setContent(n.getInfo());
                    b2.setCell(i,j,rc);
                    Vector<Cell> s = new Vector<>(2);
                    s.add(this.block[i][j]);
                    s.add(b1.getCell(i, j));
                    this.block[i][j].AddRef(rc);
                    b1.getCell(i, j).AddRef(rc);
                    Map.Entry<String, Vector<Cell>> r = new AbstractMap.SimpleEntry<>("mult", s);

                    b2.getCell(i, j).setRefInfo(r);

                    n=null;
                }
            }
        }
        b2.ul=b2.getCell(0,0);
        b2.dr=b2.getCell(b2.size_r-1,b2.size_c-1);
    }

    public void div (Block b1, Block b2, Boolean ref) {
        for (int i = 0; i < this.block.length; ++i) {
            for (int j = 0; j < this.block[0].length; ++j) {

                Cell n = (Cell) b2.getCell(i, j).changeValue((double) this.block[i][j].getInfo() / (double) b1.getCell(i, j).getInfo());
                b2.setCell(i,j,n);
                if (ref) {
                    ReferencedCell rc = new ReferencedCell(n.getRow(),n.getColumn(),"=div");
                    rc.setContent(n.getInfo());
                    b2.setCell(i,j,rc);
                    Vector<Cell> s = new Vector<>(2);
                    s.add(this.block[i][j]);
                    s.add(b1.getCell(i, j));
                    this.block[i][j].AddRef(rc);
                    b1.getCell(i, j).AddRef(rc);
                    Map.Entry<String, Vector<Cell>> r = new AbstractMap.SimpleEntry<>("div", s);

                    b2.getCell(i, j).setRefInfo(r);

                    n=null;
                }
            }
        }
        b2.ul=b2.getCell(0,0);
        b2.dr=b2.getCell(b2.size_r-1,b2.size_c-1);
    }

    public void substract (Block b1, Block b2, Boolean ref) {
        for (int i = 0; i < this.block.length; ++i) {
            for (int j = 0; j < this.block[0].length; ++j) {
                Cell n = (Cell) b2.getCell(i, j).changeValue((double) this.block[i][j].getInfo() - (double) b1.getCell(i, j).getInfo());
                b2.setCell(i,j,n);
                if (ref) {
                    ReferencedCell rc = new ReferencedCell(n.getRow(),n.getColumn(),"=sub");
                    rc.setContent(n.getInfo());
                    b2.setCell(i,j,rc);
                    Vector<Cell> s = new Vector<>(2);
                    s.add(this.block[i][j]);
                    s.add(b1.getCell(i, j));
                    this.block[i][j].AddRef(rc);
                    b1.getCell(i, j).AddRef(rc);
                    Map.Entry<String, Vector<Cell>> r = new AbstractMap.SimpleEntry<>("sub", s);
                    b2.getCell(i, j).setRefInfo(r);
                    n=null;
                }
            }
        }
        b2.ul=b2.getCell(0,0);
        b2.dr=b2.getCell(b2.size_r-1,b2.size_c-1);
    }

    public void extract (Block b, Boolean ref, String ex) {
        for (int i = 0; i < b.number_rows(); ++i) {
            for (int j = 0; j < b.number_cols(); ++j) {
                DateCell N = (DateCell) this.block[i][j];

                Cell n = (Cell) b.getCell(i, j).changeValue(N.extract(ex));
                b.setCell(i,j,n);
                if (ref) {
                    ReferencedCell rc = new ReferencedCell(n.getRow(),n.getColumn(),ex);
                    rc.setContent(n.getInfo());
                    b.setCell(i,j,rc);
                    Vector<Cell> s = new Vector<>(1);
                    s.add(N);
                    N.AddRef(rc);
                    Map.Entry<String, Vector<Cell>> r = new AbstractMap.SimpleEntry<>(ex, s);
                    b.getCell(i, j).setRefInfo(r);
                    n=null;
                }
            }
        }
        b.ul=b.getCell(0,0);
        b.dr=b.getCell(b.size_r-1,b.size_c-1);
    }

    //FINISH
    public void dayOfTheWeek (Block b, Boolean ref) {
        for (int i = 0; i < b.number_rows(); ++i) {
            for (int j = 0; j < b.number_cols(); ++j) {
                DateCell N = (DateCell) this.block[i][j];

                Cell n = (Cell) b.getCell(i, j).changeValue(N.getDayofTheWeek());
                b.setCell(i,j,n);
                if (ref) {
                    ReferencedCell rc = new ReferencedCell(n.getRow(),n.getColumn(),"=dayoftheWeek");
                    rc.setContent(n.getInfo());
                    b.setCell(i,j,rc);
                    Vector<Cell> s = new Vector<>(1);
                    s.add(N);
                    N.AddRef(rc);
                    Map.Entry<String, Vector<Cell>> r = new AbstractMap.SimpleEntry<>("dayoftheWeek", s);
                    b.getCell(i, j).setRefInfo(r);
                    n=null;
                }
            }
        }
        b.ul=b.getCell(0,0);
        b.dr=b.getCell(b.size_r-1,b.size_c-1);
      //  System.out.println(b.getCell(0,0).getType());
    }

    // criteria == mayus or criteria == minus
    public void replaceWithCriteriaText (String criteria) {
        for (Cell[] cells : this.block) {
            for (int j = 0; j < this.block[0].length; ++j) {
                String s = (String) cells[j].getInfo();
                if (Objects.equals(criteria, "All caps")) {
                    Cell n = (Cell) cells[j].changeValue(s.toUpperCase(Locale.ROOT));
                    cells[j] = n;
                }

                else if (Objects.equals(criteria, "All lowercase")) {
                    Cell n = (Cell) cells[j].changeValue(s.toLowerCase(Locale.ROOT));
                    cells[j] = n;
                }

                else if (Objects.equals(criteria, "Cap first letter")) {
                    Cell n = (Cell) cells[j].changeValue(s.substring(0, 1).toUpperCase(Locale.ROOT)
                            + s.substring(1));
                    cells[j] = n;
                }
            }
        }
    }

    public Cell sumAll (Cell c, Boolean ref) {

        double sum = 0;
        Vector<Cell> s = new Vector<>();
        for (Cell[] cells : this.block) {
            for (int j = 0; j < this.block[0].length; ++j) {
                sum += (double) cells[j].getInfo();
                s.add(cells[j]);
            }
        }

        c = (Cell) c.changeValue(sum);

        if (ref) {
            //System.out.println("entra1");
            ReferencedCell rc = new ReferencedCell(c.getRow(),c.getColumn(),"=sumAll");
            rc.setContent(c.getInfo());
            c = rc;
            Map.Entry<String, Vector<Cell>> r = new AbstractMap.SimpleEntry<>("sumAll", s);
            c.setRefInfo(r);
            for (int x=0;x<s.size();x++){
                Cell a= s.elementAt(x);
                a.AddRef(rc);
            }
        }

        return c;
    }

    public Cell subAll (Cell c, Boolean ref) {

        double sum = 0;
        Vector<Cell> s = new Vector<>();
        for (Cell[] cells : this.block) {
            for (int j = 0; j < this.block[0].length; ++j) {
                sum -= (double) cells[j].getInfo();
                s.add(cells[j]);
            }
        }

        c = (Cell) c.changeValue(-sum);

        if (ref) {
            //System.out.println("entra1");
            ReferencedCell rc = new ReferencedCell(c.getRow(),c.getColumn(),"=subAll");
            rc.setContent(c.getInfo());
            c = rc;
            Map.Entry<String, Vector<Cell>> r = new AbstractMap.SimpleEntry<>("subAll", s);
            c.setRefInfo(r);
            for (int x=0;x<s.size();x++){
                Cell a= s.elementAt(x);
                a.AddRef(rc);
            }
        }

        return c;
    }

    public Cell multAll (Cell c, Boolean ref) {

        double sum = 1;
        Vector<Cell> s = new Vector<>();
        for (Cell[] cells : this.block) {
            for (int j = 0; j < this.block[0].length; ++j) {
                sum *= (double) cells[j].getInfo();
                s.add(cells[j]);
            }
        }

        c = (Cell) c.changeValue(sum);

        if (ref) {
            //System.out.println("entra1");
            ReferencedCell rc = new ReferencedCell(c.getRow(),c.getColumn(),"=multAll");
            rc.setContent(c.getInfo());
            c = rc;
            Map.Entry<String, Vector<Cell>> r = new AbstractMap.SimpleEntry<>("multAll", s);
            c.setRefInfo(r);
            for (int x=0;x<s.size();x++){
                Cell a= s.elementAt(x);
                a.AddRef(rc);
            }
        }

        return c;
    }

    public Cell divAll (Cell c, Boolean ref) {

        double sum = 1;
        Vector<Cell> s = new Vector<>();
        for (Cell[] cells : this.block) {
            for (int j = 0; j < this.block[0].length; ++j) {
                sum /= (double) cells[j].getInfo();
                s.add(cells[j]);
            }
        }

        c = (Cell) c.changeValue(sum);

        if (ref) {
            //System.out.println("entra1");
            ReferencedCell rc = new ReferencedCell(c.getRow(),c.getColumn(),"=divAll");
            rc.setContent(c.getInfo());
            c = rc;
            Map.Entry<String, Vector<Cell>> r = new AbstractMap.SimpleEntry<>("divAll", s);
            c.setRefInfo(r);
            for (int x=0;x<s.size();x++){
                Cell a= s.elementAt(x);
                a.AddRef(rc);
            }
        }

        return c;
    }

    public Cell mean (Cell c, Boolean ref) {

        double sum = 0;
        Vector<Cell> s = new Vector<>();
        for (Cell[] cells : this.block) {
            for (int j = 0; j < this.block[0].length; ++j) {
                sum += (double) cells[j].getInfo();
                s.add(cells[j]);
            }
        }

        c = (Cell) c.changeValue(sum/s.size());

        if (ref) {
            //System.out.println("entra1");
            ReferencedCell rc = new ReferencedCell(c.getRow(),c.getColumn(),"=mean");
            rc.setContent(c.getInfo());
            c = rc;
            Map.Entry<String, Vector<Cell>> r = new AbstractMap.SimpleEntry<>("mean", s);
            c.setRefInfo(r);
            for (int x=0;x<s.size();x++){
                Cell a= s.elementAt(x);
                a.AddRef(rc);
            }
        }

        return c;
    }

    public Cell median (Cell c, Boolean ref) {
        double [] arr = new double[this.block.length*this.block[0].length];
        Vector<Cell> s = new Vector<>();
        int ii = 0;
        for (int i = 0; i < this.block.length; ++i) {
            for (int j = 0; j < this.block[0].length; ++j) {
                arr[ii] = (Double) this.block[i][j].getInfo();
                s.add(this.block[i][j]);

                ii += 1;
            }
        }

        Arrays.sort(arr);

        c = (Cell) c.changeValue(arr[arr.length/2]);

        if (ref) {
            ReferencedCell rc = new ReferencedCell(c.getRow(),c.getColumn(),"=median");
            rc.setContent(c.getInfo());
            c = rc;
            Map.Entry<String, Vector<Cell>> r = new AbstractMap.SimpleEntry<>("median", s);
            c.setRefInfo(r);
            for (int x=0;x<s.size();x++){
                Cell a= s.elementAt(x);
                a.AddRef(rc);
            }
        }

        return c;
    }

    public Cell var (Cell c, Boolean ref) {
        double d = Math.pow((double) this.std(c, false).getInfo(), 2);

        c = (Cell) c.changeValue(d);

        if (ref) {
            ReferencedCell rc = new ReferencedCell(c.getRow(),c.getColumn(),"=var");
            rc.setContent(c.getInfo());
            c = rc;
            Vector<Cell> s = new Vector<>();

            for (Cell[] cells : this.block) {
                s.addAll(Arrays.asList(cells).subList(0, this.block[0].length));
            }

            Map.Entry<String, Vector<Cell>> r = new AbstractMap.SimpleEntry<>("var", s);
            c.setRefInfo(r);
            for (int x=0;x<s.size();x++){
                Cell a= s.elementAt(x);
                a.AddRef(rc);
            }
        }
        return c;
    }

    public Cell covar (Block b, Cell c, Boolean ref) {
        double mean1 = (double) this.mean(c,false).getInfo();
        double mean2 = (double) b.mean(c,false).getInfo();

        double [] x = new double[this.block.length*this.block[0].length];
        double [] y = new double[this.block.length*this.block[0].length];

        int ii = 0;
        int jj = 0;

        Vector<Cell> s = new Vector<>();

        for (int i = 0; i < this.block.length; ++i) {
            for (int j = 0; j < this.block[0].length; ++j) {
                x[ii] = (double) this.block[i][j].getInfo();
                s.add(this.block[i][j]);
                y[jj] = (double) b.getCell(i,j).getInfo();
                s.add(b.getCell(i,j));

                ii += 1;
                jj += 1;
            }
        }

        double sum = 0;

        for (int i = 0; i < x.length; ++i) {
            sum += (x[i] - mean1)*(y[i] - mean2);
        }

        double cov = sum/(x.length-1);

        c = (Cell) c.changeValue(cov);

        if (ref) {
            ReferencedCell rc = new ReferencedCell(c.getRow(),c.getColumn(),"=covar");
            rc.setContent(c.getInfo());
            c = rc;
            Map.Entry<String, Vector<Cell>> r = new AbstractMap.SimpleEntry<>("covar", s);
            c.setRefInfo(r);
            for (int t=0;t<s.size();t++){
                Cell a= s.elementAt(t);
                a.AddRef(rc);
            }
        }

        return c;
    }

    public Cell std (Cell c, Boolean ref) {
        double sum = 0, std = 0;
        double [] arr = new double[this.block.length*this.block[0].length];
        Vector<Cell> s = new Vector<>();
        int ii = 0;
        for (int i = 0; i < this.block.length; ++i) {
            for (int j = 0; j < this.block[0].length; ++j) {
                arr[ii] = (double) this.block[i][j].getInfo();
                s.add(this.block[i][j]);
                sum += (double) this.block[i][j].getInfo();

                ii += 1;
            }
        }

        double mean = sum/arr.length;

        for (double num: arr) {
            std += Math.pow(num - mean, 2);
        }
        double stdd = Math.sqrt(std/arr.length);

        c = (Cell) c.changeValue(stdd);

        if (ref) {
            ReferencedCell rc = new ReferencedCell(c.getRow(),c.getColumn(),"=std");
            rc.setContent(c.getInfo());
            c = rc;
            Map.Entry<String, Vector<Cell>> r = new AbstractMap.SimpleEntry<>("std", s);
            c.setRefInfo(r);
            for (int t=0;t<s.size();t++){
                Cell a= s.elementAt(t);
                a.AddRef(rc);
            }
        }

        return c;
    }

    public Cell CPearson (Block b, Cell c, Boolean ref) {
        double d1 = (double) b.std(c,false).getInfo();
        double d2 = (double) this.std(c, false).getInfo();

        double cp;
        double covv = (double) this.covar(b,c,false).getInfo();

        if (d1 == 0 || d2 == 0) cp = 1;
        else cp = covv/(d1*d2);

        if (cp > 1.0) cp = 1.0;
        if (cp < -1) cp = -1.0;

        c = (Cell) c.changeValue(cp);

        if (ref) {

            Vector<Cell> s = new Vector<>();

            for (int i = 0; i < this.block.length; ++i) {
                for (int j = 0; j < this.block[0].length; ++j) {
                    s.add(this.block[i][j]);
                    s.add(b.getCell(i,j));
                }
            }
            ReferencedCell rc = new ReferencedCell(c.getRow(),c.getColumn(),"=cp");
            rc.setContent(c.getInfo());
            c = rc;
            Map.Entry<String, Vector<Cell>> r = new AbstractMap.SimpleEntry<>("cp", s);
            c.setRefInfo(r);
            for (int t=0;t<s.size();t++){
                Cell a= s.elementAt(t);
                a.AddRef(rc);
            }
        }

        return c;
    }
}
