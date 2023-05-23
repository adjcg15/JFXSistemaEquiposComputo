package jfxsistemaequiposcomputo.pojo;

import java.util.ArrayList;

public class MantenimientoConEquipoYDiagnostico {
    private Mantenimiento mantenimiento;
    private EquipoComputo equipo;
    private Diagnostico diagnostico;
    private ArrayList<Estado> estados;
    private ArrayList<Refaccion> refacciones;

    public MantenimientoConEquipoYDiagnostico() {
    }

    public MantenimientoConEquipoYDiagnostico(Mantenimiento mantenimiento, EquipoComputo equipo, Diagnostico diagnostico, ArrayList<Estado> estados, ArrayList<Refaccion> refacciones) {
        this.mantenimiento = mantenimiento;
        this.equipo = equipo;
        this.diagnostico = diagnostico;
        this.estados = estados;
        this.refacciones = refacciones;
    }

    public Mantenimiento getMantenimiento() {
        return mantenimiento;
    }

    public EquipoComputo getEquipo() {
        return equipo;
    }

    public Diagnostico getDiagnostico() {
        return diagnostico;
    }
    
    public ArrayList<Estado> getEstados() {
        return estados;
    }
    
    public ArrayList<Refaccion> getRefacciones() {
        return refacciones;
    }

    public void setMantenimiento(Mantenimiento mantenimiento) {
        this.mantenimiento = mantenimiento;
    }

    public void setEquipo(EquipoComputo equipo) {
        this.equipo = equipo;
    }

    public void setDiagnostico(Diagnostico diagnostico) {
        this.diagnostico = diagnostico;
    }
    
    public void setEstados(ArrayList<Estado> estados) {
        this.estados = estados;
    }
    
    public void setRefacciones(ArrayList<Refaccion> refacciones) {
        this.refacciones = refacciones;
    }
    
    @Override
    public String toString() {
        if(equipo != null && !equipo.getModelo().equals("")) {
            return "Mantenimiento de equipo " + equipo.getModelo().toUpperCase();
        }
        
        return "Mantenimiento vacío";
    }
}
