package jfxsistemaequiposcomputo.pojo;

import java.util.ArrayList;

public class ListaEstadosRespuesta {
    private ArrayList<Estado> estados;
    private int codigoRespuesta;

    public ListaEstadosRespuesta() {
    }

    public ListaEstadosRespuesta(ArrayList<Estado> estados, int codigoRespuesta) {
        this.estados = estados;
        this.codigoRespuesta = codigoRespuesta;
    }

    public ArrayList<Estado> getEstados() {
        return estados;
    }

    public int getCodigoRespuesta() {
        return codigoRespuesta;
    }

    public void setEstados(ArrayList<Estado> estados) {
        this.estados = estados;
    }

    public void setCodigoRespuesta(int codigoRespuesta) {
        this.codigoRespuesta = codigoRespuesta;
    }
}
