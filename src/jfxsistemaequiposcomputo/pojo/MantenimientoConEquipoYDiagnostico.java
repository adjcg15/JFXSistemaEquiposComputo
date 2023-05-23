package jfxsistemaequiposcomputo.pojo;

import java.util.ArrayList;

public class MantenimientoConEquipoYDiagnostico {
    private Mantenimiento mantenimiento;
    private EquipoComputo equipo;
    private Diagnostico diagnostico;
    private ArrayList<Estado> estados;

    public MantenimientoConEquipoYDiagnostico() {
    }

    public MantenimientoConEquipoYDiagnostico(Mantenimiento mantenimiento, EquipoComputo equipo, Diagnostico diagnostico, ArrayList<Estado> estados) {
        this.mantenimiento = mantenimiento;
        this.equipo = equipo;
        this.diagnostico = diagnostico;
        this.estados = estados;
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
    
    @Override
    public String toString() {
        if(equipo != null && !equipo.getModelo().equals("")) {
            System.out.println(equipo.getModelo().toUpperCase());
            return "Mantenimiento de equipo " + equipo.getModelo().toUpperCase();
        }
        
        return "Mantenimiento vacío";
    }
}
