package jfxsistemaequiposcomputo.pojo;

/**
 *
 * @author rodri
 */
public class TipoRefaccion {
    private int idTipoRefaccion;
    private String nombre;

    public TipoRefaccion() {
    }

    public TipoRefaccion(int idTipoRefaccion, String nombre) {
        this.idTipoRefaccion = idTipoRefaccion;
        this.nombre = nombre;
    }

    public int getIdTipoRefaccion() {
        return idTipoRefaccion;
    }

    public void setIdTipoRefaccion(int idTipoRefaccion) {
        this.idTipoRefaccion = idTipoRefaccion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public String toString() {
        return nombre;
    }
}
