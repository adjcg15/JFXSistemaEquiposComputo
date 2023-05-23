package jfxsistemaequiposcomputo.pojo;

public class Refaccion {
    private int idRefaccion;
    private int idTipoRefaccion;
    private String nombre;
    private String tipoRefaccion;

    public Refaccion() {
    }

    public Refaccion(int idRefaccion, int idTipoRefaccion, String nombre, String tipoRefaccion) {
        this.idRefaccion = idRefaccion;
        this.idTipoRefaccion = idTipoRefaccion;
        this.nombre = nombre;
        this.tipoRefaccion = tipoRefaccion;
    }

    public int getIdRefaccion() {
        return idRefaccion;
    }

    public int getIdTipoRefaccion() {
        return idTipoRefaccion;
    }

    public String getNombre() {
        return nombre;
    }

    public String getTipoRefaccion() {
        return tipoRefaccion;
    }

    public void setIdRefaccion(int idRefaccion) {
        this.idRefaccion = idRefaccion;
    }

    public void setIdTipoRefaccion(int idTipoRefaccion) {
        this.idTipoRefaccion = idTipoRefaccion;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setTipoRefaccion(String tipoRefaccion) {
        this.tipoRefaccion = tipoRefaccion;
    }
}
