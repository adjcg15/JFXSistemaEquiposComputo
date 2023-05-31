package jfxsistemaequiposcomputo.pojo;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Estado {
    private boolean activo;
    private String fechaInicio;
    private String fechaFin;
    private int idEstado;
    private String nombre;
    
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    
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
        LocalDate fechaLocalDate = LocalDate.parse(fechaInicio);
        String fechaConvertida = fechaLocalDate.format(formatter);
        this.fechaInicio = fechaConvertida;
    }

    public void setFechaFin(String fechaFin) {
        if(fechaFin != null){
            LocalDate fechaLocalDate = LocalDate.parse(fechaFin);
            String fechaFinConvertida = fechaLocalDate.format(formatter);
            this.fechaFin = fechaFinConvertida;
        }else{
            this.fechaFin = fechaFin;
        }
    }

    public void setIdEstado(int idEstado) {
        this.idEstado = idEstado;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
