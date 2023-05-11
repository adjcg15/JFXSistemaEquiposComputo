package jfxsistemaequiposcomputo.pojo;

public class SolicitudMantenimiento {
    private int idUsuario;
    private String contraeniaSO;
    private String fechaRegistro;
    private int idEquipoComputo;
    private byte[] imagen;
    private boolean incluyeCargador;
    private String marca;
    private String memoriaRAM;
    private String modelo;
    private String observaciones;
    private String procesador;
    private String sistemaOperativo;
    private String tamanioPantalla;
    private String tipo;
    private String usuarioSO;

    public SolicitudMantenimiento() {
    }

    public SolicitudMantenimiento(int idUsuario, String contraeniaSO, String fechaRegistro, int idEquipoComputo, byte[] imagen, boolean incluyeCargador, String marca, String memoriaRAM, String modelo, String observaciones, String procesador, String sistemaOperativo, String tamanioPantalla, String tipo, String usuarioSO) {
        this.contraeniaSO = contraeniaSO;
        this.fechaRegistro = fechaRegistro;
        this.idEquipoComputo = idEquipoComputo;
        this.imagen = imagen;
        this.incluyeCargador = incluyeCargador;
        this.marca = marca;
        this.memoriaRAM = memoriaRAM;
        this.modelo = modelo;
        this.observaciones = observaciones;
        this.procesador = procesador;
        this.sistemaOperativo = sistemaOperativo;
        this.tamanioPantalla = tamanioPantalla;
        this.tipo = tipo;
        this.usuarioSO = usuarioSO;
        this.idUsuario = idUsuario;
    }
    
    public int getIdUsuario() {
        return idUsuario;
    }

    public String getContraeniaSO() {
        return contraeniaSO;
    }

    public void setContraeniaSO(String contraeniaSO) {
        this.contraeniaSO = contraeniaSO;
    }

    public String getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(String fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public int getIdEquipoComputo() {
        return idEquipoComputo;
    }

    public void setIdEquipoComputo(int idEquipoComputo) {
        this.idEquipoComputo = idEquipoComputo;
    }

    public byte[] getImagen() {
        return imagen;
    }
    
    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public void setImagen(byte[] imagen) {
        this.imagen = imagen;
    }

    public boolean isIncluyeCargador() {
        return incluyeCargador;
    }

    public void setIncluyeCargador(boolean incluyeCargador) {
        this.incluyeCargador = incluyeCargador;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getMemoriaRAM() {
        return memoriaRAM;
    }

    public void setMemoriaRAM(String memoriaRAM) {
        this.memoriaRAM = memoriaRAM;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public String getProcesador() {
        return procesador;
    }

    public void setProcesador(String procesador) {
        this.procesador = procesador;
    }

    public String getSistemaOperativo() {
        return sistemaOperativo;
    }

    public void setSistemaOperativo(String sistemaOperativo) {
        this.sistemaOperativo = sistemaOperativo;
    }

    public String getTamanioPantalla() {
        return tamanioPantalla;
    }

    public void setTamanioPantalla(String tamanioPantalla) {
        this.tamanioPantalla = tamanioPantalla;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getUsuarioSO() {
        return usuarioSO;
    }

    public void setUsuarioSO(String usuarioSO) {
        this.usuarioSO = usuarioSO;
    }
}
