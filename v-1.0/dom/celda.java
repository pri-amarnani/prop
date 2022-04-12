package projProp;

public class celda {
    private final int fila;
    private final int columna;
    private final String id; //hace falta?????



    public celda(int fila,int columna,String id){
        this.fila=fila;
        this.columna=columna;
        this.id=id;
    }

    /**
     * Consultora de la fila
     * @return fila de la celda
     */

    public int getfila(){
        return fila;
    }

    /**
     * Consultora de la columna
     * @return columna de la celda
     */

    public int getColumna(){
        return columna;
    }
}

