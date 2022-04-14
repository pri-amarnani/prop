package classes;

public class Sheet {
    Cell[][] celdas; //vectores?
    int num_full;
    String titol;
    int num_filas;
    int num_col;


    /*
       FUNCIONES BLOC
    CopiarContingutBloc()
    ModificarContingutBloc(Type T)
    OrdenarBloc(Bloc b,String Criteri)
    Cerca(Tipus T)
    CercaIReemplaçament(Tipus T)
     */


    public void AfegirFila(Int pos){
        if (pos != celdas[0].size()) celdas.push(celdas[celdas.size()-1]);
        for(int i = celdas.size()-2; i >= pos; --i){
            celdas[i+1] = celdas[i];
        }
        celdas[pos] = new ArrayList<Cela>>; //vectores!!!
    }

    public void AfegirColumna(Int pos){
        //NO MOVER LAS CELDAS, SOLO LA INFO QUE TIENEN!!!
        for(int i = 0, i < celdas.size(); ++i){
            for(int j = celdas[0].size(); j >= pos; --j ) {
                if(j == celdas[0].size()) celdas[i].push(celdas[i][j-1]);
                else if(j == pos) celdas[i][j] = new Cela;          //SE TENDRIA QUE HACER EN LA CLLASE CELDA!!!
                else celdas[i][j] = celdas[i][j-1];
            }
        }
    }

    EliminarFila(Int pos){}
    EliminarColumna(Int pos){}
    SeleccionarBloc(Cela c1, Cela c2){} //pasar mat
    SeleccionarCela(Int f, Int c){}
    CopiarContingutBloc(Bloc b){}
    MoureContingutBloc(Bloc b, Cela c){}
    ModificarContingutBloc(Block b, Tipus t){}
    ReferenciarBloc(Block b1, Block b2){}
    ReferenciarCela(Cela c, Bloc b){}

    void OrdenarBloc(Block b, String Criteri){
        b.OrdenarBloc(b,Criteri);
    }

    Cerca(Tipus T,Block b){}
    CercaIReemplaçament(Tipus T, Block b){}







    public void FuncioTruncament(Block b1, Cell c, Boolean ref){}
    public void FuncioConversio(Block b1, Cell c, Boolean ref){}
    public void FuncioOpAritmetiques(Block b1, Block b2, Cell c, Boolean ref){}
    public void FuncioExtraccio(Block b1,Cell c, Boolean ref){}
    public void FuncioDiaSetmana(Block b1,Cell c, Boolean ref){}

    public void replace(Block b1,Cell c, Boolean ref, String criteria){
        b1.replace(b1, criteria);
    }

    public void length(TextCell c, String criteria){
        c.length(criteria);   //depende del tipo de celda? o solo textcell
    }

    public void mean(Block b1,Cell c, Boolean ref){
        b1.mean(ref);
    }

    public void median(Block b1, Cell c, Boolean ref){
        b1.median(ref);
    }

    public void var(Block b1,Cell c, Boolean ref){
        b1.var(ref);
    }

    public void covar(Block b1,Cell c, Boolean ref){
        b1.covar(b1,ref);
    }

    public void std(Block b1,Cell c, Boolean ref){ //que funcion??
        b1.std(ref);
    }

    public void CPearson(Block b1, Cell c, Boolean ref){
        c.info(b1.CPearson(ref)) ; //c.info = funcion de cell para cambiar la info de la celda
    }
}