package jfxsistemaequiposcomputo.pojo;

import java.util.ArrayList;

public class ListaMantenimientosRespuesta {
    private int codigoRespuesta;
    private ArrayList<MantenimientoConEquipoYDiagnostico> mantenimientosCompletos;

    public ListaMantenimientosRespuesta() {
    }

    public ListaMantenimientosRespuesta(int codigoRespuesta, ArrayList<MantenimientoConEquipoYDiagnostico> mantenimientosCompletos) {
        this.codigoRespuesta = codigoRespuesta;
        this.mantenimientosCompletos = mantenimientosCompletos;
    }

    public int getCodigoRespuesta() {
        return codigoRespuesta;
    }

    public ArrayList<MantenimientoConEquipoYDiagnostico> getMantenimientosCompletos() {
        return mantenimientosCompletos;
    }

    public void setCodigoRespuesta(int codigoRespuesta) {
        this.codigoRespuesta = codigoRespuesta;
    }

    public void setMantenimientosCompletos(ArrayList<MantenimientoConEquipoYDiagnostico> mantenimientosCompletos) {
        this.mantenimientosCompletos = mantenimientosCompletos;
    }
}
