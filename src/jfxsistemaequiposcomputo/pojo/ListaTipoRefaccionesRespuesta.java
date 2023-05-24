package jfxsistemaequiposcomputo.pojo;

import java.util.ArrayList;

/**
 *
 * @author rodri
 */
public class ListaTipoRefaccionesRespuesta {
    private ArrayList<TipoRefaccion> tipoRefacciones;
    private int codigoRespuesta;

    public ListaTipoRefaccionesRespuesta() {
    }

    public ListaTipoRefaccionesRespuesta(ArrayList<TipoRefaccion> tipoRefacciones, int codigoRespuesta) {
        this.tipoRefacciones = tipoRefacciones;
        this.codigoRespuesta = codigoRespuesta;
    }

    public ArrayList<TipoRefaccion> getTipoRefacciones() {
        return tipoRefacciones;
    }

    public void setTipoRefacciones(ArrayList<TipoRefaccion> tipoRefacciones) {
        this.tipoRefacciones = tipoRefacciones;
    }

    public int getCodigoRespuesta() {
        return codigoRespuesta;
    }

    public void setCodigoRespuesta(int codigoRespuesta) {
        this.codigoRespuesta = codigoRespuesta;
    }
}
