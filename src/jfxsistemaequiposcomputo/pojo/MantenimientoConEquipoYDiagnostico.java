package jfxsistemaequiposcomputo.pojo;

public class MantenimientoConEquipoYDiagnostico {
    private Mantenimiento mantenimiento;
    private EquipoComputo equipo;
    private Diagnostico diagnostico;

    public MantenimientoConEquipoYDiagnostico() {
    }

    public MantenimientoConEquipoYDiagnostico(Mantenimiento mantenimiento, EquipoComputo equipo, Diagnostico diagnostico) {
        this.mantenimiento = mantenimiento;
        this.equipo = equipo;
        this.diagnostico = diagnostico;
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

    public void setMantenimiento(Mantenimiento mantenimiento) {
        this.mantenimiento = mantenimiento;
    }

    public void setEquipo(EquipoComputo equipo) {
        this.equipo = equipo;
    }

    public void setDiagnostico(Diagnostico diagnostico) {
        this.diagnostico = diagnostico;
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
