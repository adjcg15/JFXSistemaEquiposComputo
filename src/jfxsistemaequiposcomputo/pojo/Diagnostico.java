package jfxsistemaequiposcomputo.pojo;

/**
 *
 * @author rodri
 */
public class Diagnostico {
    private int idDiagnostico;
    private String tipoDeMantenimiento;
    private String fechaAtencion;
    private String diagnosticoPreliminar;
    private String fechaSolicitud;
    private float costoEstimado;
    private String propuestaSolucion;
    private int idSolicitudDiagnostico;

    public Diagnostico() {
    }

    public Diagnostico(int idDiagnostico, String tipoDeMantenimiento, String fechaAtencion, String diagnosticoPreliminar, String fechaSolicitud, float costoEstimado, String propuestaSolucion, int idSolicitudDiagnostico) {
        this.idDiagnostico = idDiagnostico;
        this.tipoDeMantenimiento = tipoDeMantenimiento;
        this.fechaAtencion = fechaAtencion;
        this.diagnosticoPreliminar = diagnosticoPreliminar;
        this.fechaSolicitud = fechaSolicitud;
        this.costoEstimado = costoEstimado;
        this.propuestaSolucion = propuestaSolucion;
        this.idSolicitudDiagnostico = idSolicitudDiagnostico;
    }

    public int getIdDiagnostico() {
        return idDiagnostico;
    }

    public void setIdDiagnostico(int idDiagnostico) {
        this.idDiagnostico = idDiagnostico;
    }

    public String getTipoDeMantenimiento() {
        return tipoDeMantenimiento;
    }

    public void setTipoDeMantenimiento(String tipoDeMantenimiento) {
        this.tipoDeMantenimiento = tipoDeMantenimiento;
    }

    public String getFechaAtencion() {
        return fechaAtencion;
    }

    public void setFechaAtencion(String fechaAtencion) {
        this.fechaAtencion = fechaAtencion;
    }

    public String getDiagnosticoPreliminar() {
        return diagnosticoPreliminar;
    }

    public void setDiagnosticoPreliminar(String diagnosticoPreliminar) {
        this.diagnosticoPreliminar = diagnosticoPreliminar;
    }

    public String getFechaSolicitud() {
        return fechaSolicitud;
    }

    public void setFechaSolicitud(String fechaSolicitud) {
        this.fechaSolicitud = fechaSolicitud;
    }

    public float getCostoEstimado() {
        return costoEstimado;
    }

    public void setCostoEstimado(float costoEstimado) {
        this.costoEstimado = costoEstimado;
    }

    public String getPropuestaSolucion() {
        return propuestaSolucion;
    }

    public void setPropuestaSolucion(String propuestaSolucion) {
        this.propuestaSolucion = propuestaSolucion;
    }

    public int getIdSolicitudDiagnostico() {
        return idSolicitudDiagnostico;
    }

    public void setIdSolicitudDiagnostico(int idSolicitudDiagnostico) {
        this.idSolicitudDiagnostico = idSolicitudDiagnostico;
    }
} 
