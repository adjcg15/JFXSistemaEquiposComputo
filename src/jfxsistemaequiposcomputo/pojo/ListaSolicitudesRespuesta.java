package jfxsistemaequiposcomputo.pojo;

import java.util.ArrayList;

public class ListaSolicitudesRespuesta {
    private int codigoRespuesta;
    private ArrayList<SolicitudConUsuarioYEquipo> solicitudesCompletas;
    
    public ListaSolicitudesRespuesta() {
    }

    public ListaSolicitudesRespuesta(
        int codigoRespuesta, 
        ArrayList<SolicitudConUsuarioYEquipo> solicitudesCompletas
    ) {
        this.codigoRespuesta = codigoRespuesta;
        this.solicitudesCompletas = solicitudesCompletas;
    }

    public int getCodigoRespuesta() {
        return codigoRespuesta;
    }

    public ArrayList<SolicitudConUsuarioYEquipo> getSolicitudesCompletas() {
        return solicitudesCompletas;
    }

    public void setCodigoRespuesta(int codigoRespuesta) {
        this.codigoRespuesta = codigoRespuesta;
    }

    public void setSolicitudesCompletas(ArrayList<SolicitudConUsuarioYEquipo> solicitudesCompletas) {
        this.solicitudesCompletas = solicitudesCompletas;
    }
}
