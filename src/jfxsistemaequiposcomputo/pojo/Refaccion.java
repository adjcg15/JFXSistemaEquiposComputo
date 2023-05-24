package jfxsistemaequiposcomputo.pojo;

public class Refaccion {
    private int idRefaccion;
    private int idTipoRefaccion;
    private String nombre;
    private String tipoRefaccion;
    private int stock;

    public Refaccion() {
    }

    public Refaccion(int idRefaccion, int idTipoRefaccion, String nombre, String tipoRefaccion, int stock) {
        this.idRefaccion = idRefaccion;
        this.idTipoRefaccion = idTipoRefaccion;
        this.nombre = nombre;
        this.tipoRefaccion = tipoRefaccion;
        this.stock = stock;
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

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    @Override
    public String toString() {
        return nombre + ", stock: " + stock;
    }
}
