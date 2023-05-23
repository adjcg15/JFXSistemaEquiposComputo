package jfxsistemaequiposcomputo.pojo;

import java.util.ArrayList;

public class ListaRefaccionesRespuesta {
    private ArrayList<Refaccion> refacciones;
    private int codigoRespuesta;

    public ListaRefaccionesRespuesta() {
    }

    public ListaRefaccionesRespuesta(ArrayList<Refaccion> refacciones, int codigoRespuesta) {
        this.refacciones = refacciones;
        this.codigoRespuesta = codigoRespuesta;
    }

    public ArrayList<Refaccion> getRefacciones() {
        return refacciones;
    }

    public int getCodigoRespuesta() {
        return codigoRespuesta;
    }

    public void setRefacciones(ArrayList<Refaccion> refacciones) {
        this.refacciones = refacciones;
    }

    public void setCodigoRespuesta(int codigoRespuesta) {
        this.codigoRespuesta = codigoRespuesta;
    }
}
