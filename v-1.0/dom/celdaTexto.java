package projProp;

import java.util.Date;

public class celdaTexto extends celda{

    private String info;

    public celdaTexto (int fila,int columna, String id, String info){
        super(fila,columna,id);
        this.info= info;
    }
}
