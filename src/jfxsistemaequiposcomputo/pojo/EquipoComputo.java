package jfxsistemaequiposcomputo.pojo;

public class EquipoComputo {
    private String contraseniaSO;
    private String fechaRegistro;
    private int idEquipoComputo;
    private int idSolicitud;
    private int idUsuario;
    private byte[] imagen;
    private boolean incluyeCargador;
    private String marca;
    private Integer memoriaRAM;
    private String modelo;
    private String procesador;
    private String sistemaOperativo;
    private Float tamanioPantalla;
    private String tipo;
    private String usuarioSO;

    public EquipoComputo() {
    }

    public EquipoComputo(String contraseniaSO, String fechaRegistro, int idEquipoComputo, int idSolicitud, int idUsuario, byte[] imagen, boolean incluyeCargador, String marca, Integer memoriaRAM, String modelo, String procesador, String sistemaOperativo, Float tamanioPantalla, String tipo, String usuarioSO) {
        this.contraseniaSO = contraseniaSO;
        this.fechaRegistro = fechaRegistro;
        this.idEquipoComputo = idEquipoComputo;
        this.idSolicitud = idSolicitud;
        this.idUsuario = idUsuario;
        this.imagen = imagen;
        this.incluyeCargador = incluyeCargador;
        this.marca = marca;
        this.memoriaRAM = memoriaRAM;
        this.modelo = modelo;
        this.procesador = procesador;
        this.sistemaOperativo = sistemaOperativo;
        this.tamanioPantalla = tamanioPantalla;
        this.tipo = tipo;
        this.usuarioSO = usuarioSO;
    }

    public String getContraseniaSO() {
        return contraseniaSO;
    }

    public String getFechaRegistro() {
        return fechaRegistro;
    }

    public int getIdEquipoComputo() {
        return idEquipoComputo;
    }

    public int getIdSolicitud() {
        return idSolicitud;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public byte[] getImagen() {
        return imagen;
    }

    public boolean isIncluyeCargador() {
        return incluyeCargador;
    }

    public String getMarca() {
        return marca;
    }

    public Integer getMemoriaRAM() {
        return memoriaRAM;
    }

    public String getModelo() {
        return modelo;
    }

    public String getProcesador() {
        return procesador;
    }

    public String getSistemaOperativo() {
        return sistemaOperativo;
    }

    public Float getTamanioPantalla() {
        return tamanioPantalla;
    }

    public String getTipo() {
        return tipo;
    }

    public String getUsuarioSO() {
        return usuarioSO;
    }

    public void setContraseniaSO(String contraseniaSO) {
        this.contraseniaSO = contraseniaSO;
    }

    public void setFechaRegistro(String fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public void setIdEquipoComputo(int idEquipoComputo) {
        this.idEquipoComputo = idEquipoComputo;
    }

    public void setIdSolicitud(int idSolicitud) {
        this.idSolicitud = idSolicitud;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public void setImagen(byte[] imagen) {
        this.imagen = imagen;
    }

    public void setIncluyeCargador(boolean incluyeCargador) {
        this.incluyeCargador = incluyeCargador;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public void setMemoriaRAM(Integer memoriaRAM) {
        this.memoriaRAM = memoriaRAM;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public void setProcesador(String procesador) {
        this.procesador = procesador;
    }

    public void setSistemaOperativo(String sistemaOperativo) {
        this.sistemaOperativo = sistemaOperativo;
    }

    public void setTamanioPantalla(Float tamanioPantalla) {
        this.tamanioPantalla = tamanioPantalla;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public void setUsuarioSO(String usuarioSO) {
        this.usuarioSO = usuarioSO;
    }
}
