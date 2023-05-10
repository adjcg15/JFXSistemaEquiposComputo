package jfxsistemaequiposcomputo.pojo;

public class UsuarioRespuesta {
    private int codigoRespuesta;
    private Usuario usuario;

    public UsuarioRespuesta() {
    }

    public UsuarioRespuesta(int codigoRespuesta, Usuario usuario) {
        this.codigoRespuesta = codigoRespuesta;
        this.usuario = usuario;
    }
    
    public int getCodigoRespuesta() {
        return codigoRespuesta;
    }

    public void setCodigoRespuesta(int codigoRespuesta) {
        this.codigoRespuesta = codigoRespuesta;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}
