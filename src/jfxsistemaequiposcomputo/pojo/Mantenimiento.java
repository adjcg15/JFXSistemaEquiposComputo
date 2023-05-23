package jfxsistemaequiposcomputo.pojo;

public class Mantenimiento {
    private int idMantenimiento;
    private int idDiagnostico;
    private String comentario;

    public Mantenimiento() {
    }

    public Mantenimiento(int idMantenimiento, int idDiagnostico, String comentario) {
        this.idMantenimiento = idMantenimiento;
        this.idDiagnostico = idDiagnostico;
        this.comentario = comentario;
    }

    public int getIdMantenimiento() {
        return idMantenimiento;
    }

    public int getIdDiagnostico() {
        return idDiagnostico;
    }

    public String getComentario() {
        return comentario;
    }

    public void setIdMantenimiento(int idMantenimiento) {
        this.idMantenimiento = idMantenimiento;
    }

    public void setIdDiagnostico(int idDiagnostico) {
        this.idDiagnostico = idDiagnostico;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }
}
