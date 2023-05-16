package jfxsistemaequiposcomputo.pojo;

public class SolicitudConUsuarioYEquipo {
    private int codigoRespuesta;
    private Usuario usuario;
    private Solicitud solicitud;
    private EquipoComputo equipo;
    
    public SolicitudConUsuarioYEquipo() {
    }

    public SolicitudConUsuarioYEquipo(int codigoRespuesta, Usuario usuario, Solicitud solicitud, EquipoComputo equipo) {
        this.codigoRespuesta = codigoRespuesta;
        this.usuario = usuario;
        this.solicitud = solicitud;
        this.equipo = equipo;
    }

    public int getCodigoRespuesta() {
        return codigoRespuesta;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public Solicitud getSolicitud() {
        return solicitud;
    }

    public EquipoComputo getEquipo() {
        return equipo;
    }

    public void setCodigoRespuesta(int codigoRespuesta) {
        this.codigoRespuesta = codigoRespuesta;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public void setSolicitud(Solicitud solicitud) {
        this.solicitud = solicitud;
    }

    public void setEquipo(EquipoComputo equipo) {
        this.equipo = equipo;
    }
    
    
}
