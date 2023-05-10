package jfxsistemaequiposcomputo.pojo;

public class Usuario {
    private int idUsuario;
    private String nombre;
    private String telefono;
    private String dirección;
    private String correo;
    private String contrasenia;
    private boolean privilegiado;

    public Usuario() {
    }

    public Usuario(int idUsuario, String nombre, String telefono, String dirección, String correo, String contrasenia, boolean privilegiado) {
        this.idUsuario = idUsuario;
        this.nombre = nombre;
        this.telefono = telefono;
        this.dirección = dirección;
        this.correo = correo;
        this.contrasenia = contrasenia;
        this.privilegiado = privilegiado;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getDirección() {
        return dirección;
    }

    public void setDirección(String dirección) {
        this.dirección = dirección;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getContrasenia() {
        return contrasenia;
    }

    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }

    public boolean isPrivilegiado() {
        return privilegiado;
    }

    public void setPrivilegiado(boolean privilegiado) {
        this.privilegiado = privilegiado;
    }
}
