package jfxsistemaequiposcomputo.pojo;

public class Solicitud {
    private int idSolicitud;
    private int idEstado;
    private int idUsuario;
    private String observaciones;

    public Solicitud() {
    }

    public Solicitud(int idSolicitud, int idEstado, String observaciones) {
        this.idSolicitud = idSolicitud;
        this.idEstado = idEstado;
        this.observaciones = observaciones;
    }

    public int getIdSolicitud() {
        return idSolicitud;
    }

    public int getIdEstado() {
        return idEstado;
    }
    
    public int getIdUsuario() {
        return idUsuario;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setIdSolicitud(int idSolicitud) {
        this.idSolicitud = idSolicitud;
    }

    public void setIdEstado(int idEstado) {
        this.idEstado = idEstado;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }
    
    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }
}
