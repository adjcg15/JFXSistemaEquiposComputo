package jfxsistemaequiposcomputo.pojo;

public class SolicitudConUsuarioYEquipo {
    private Usuario usuario;
    private Solicitud solicitud;
    private EquipoComputo equipo;
    
    public SolicitudConUsuarioYEquipo() {
    }

    public SolicitudConUsuarioYEquipo(Usuario usuario, Solicitud solicitud, EquipoComputo equipo) {
        this.usuario = usuario;
        this.solicitud = solicitud;
        this.equipo = equipo;
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

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public void setSolicitud(Solicitud solicitud) {
        this.solicitud = solicitud;
    }

    public void setEquipo(EquipoComputo equipo) {
        this.equipo = equipo;
    }
    
    @Override
    public String toString() {
        if(equipo != null && !equipo.getModelo().equals("")) {
            return "Solicitud de equipo " + equipo.getModelo().toUpperCase();
        }
        
        return "Solicitud vacía";
    }
}
