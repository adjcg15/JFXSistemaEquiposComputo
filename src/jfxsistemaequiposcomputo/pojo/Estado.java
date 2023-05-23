package jfxsistemaequiposcomputo.pojo;

public class Estado {
    private boolean activo;
    private String fechaInicio;
    private String fechaFin;
    private int idEstado;
    private String nombre;

    public Estado() {
    }

    public Estado(boolean activo, String fechaInicio, String fechaFin, int idEstado, String nombre) {
        this.activo = activo;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.idEstado = idEstado;
        this.nombre = nombre;
    }

    public boolean isActivo() {
        return activo;
    }

    public String getFechaInicio() {
        return fechaInicio;
    }

    public String getFechaFin() {
        return fechaFin;
    }

    public int getIdEstado() {
        return idEstado;
    }

    public String getNombre() {
        return nombre;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    public void setFechaInicio(String fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public void setFechaFin(String fechaFin) {
        this.fechaFin = fechaFin;
    }

    public void setIdEstado(int idEstado) {
        this.idEstado = idEstado;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
