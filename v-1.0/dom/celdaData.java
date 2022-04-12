package projProp;

import java.util.Date;

public class celdaData extends celda {
    private Date info;

    public celdaData (int fila,int columna, String id, Date info){
        super(fila,columna,id);
        this.info= info;
    }

}
