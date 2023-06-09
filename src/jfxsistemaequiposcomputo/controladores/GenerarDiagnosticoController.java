package jfxsistemaequiposcomputo.controladores;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.converter.LocalDateStringConverter;
import javax.imageio.ImageIO;
import jfxsistemaequiposcomputo.DAO.DiagnosticoDAO;
import jfxsistemaequiposcomputo.DAO.EquiposComputoDAO;
import jfxsistemaequiposcomputo.DAO.SolicitudesDAO;
import jfxsistemaequiposcomputo.pojo.Diagnostico;
import jfxsistemaequiposcomputo.pojo.EquipoComputo;
import jfxsistemaequiposcomputo.pojo.ListaSolicitudesRespuesta;
import jfxsistemaequiposcomputo.pojo.Solicitud;
import jfxsistemaequiposcomputo.pojo.SolicitudConUsuarioYEquipo;
import jfxsistemaequiposcomputo.pojo.Usuario;
import jfxsistemaequiposcomputo.utils.Constantes;
import jfxsistemaequiposcomputo.utils.Utilidades;

public class GenerarDiagnosticoController implements Initializable {
    @FXML
    private Pane paneDetalles;
    @FXML
    private ImageView ivFoto;
    @FXML
    private ListView<SolicitudConUsuarioYEquipo> lvSolicitudes;
    @FXML
    private TextField tfContraseniaSO;
    @FXML
    private TextField tfUsuarioSO;
    @FXML
    private TextField tfSO;
    @FXML
    private TextField tfMemoriaRAM;
    @FXML
    private TextField tfProcesador;
    @FXML
    private TextField tfTamanioPantalla;
    @FXML
    private TextField tfModelo;
    @FXML
    private TextField tfMarca;
    @FXML
    private Label lbModeloEquipo;
    @FXML
    private Label lbFechaSolicitud;
    @FXML
    private Label lbObervaciones;
    @FXML
    private Label lbNombre;
    @FXML
    private Label lbDireccion;
    @FXML
    private Label lbCorreo;
    @FXML
    private Label lbTelefono;
    @FXML
    private TextField tfDiagnostico;
    @FXML
    private TextField tfPropuesta;
    @FXML
    private TextField tfCosto;
    @FXML
    private RadioButton rbPreventivo;
    @FXML
    private RadioButton rbCorrectivo;
    @FXML
    private ToggleGroup tgTipoMantenimiento;
    @FXML
    private DatePicker dpFechaAtencion;

    private ObservableList<SolicitudConUsuarioYEquipo> listaSolicitudes;
    private Diagnostico diagnostico = new Diagnostico();
    private EquipoComputo nuevoEquipoComputo = new EquipoComputo();
    
    private String tipoMantenimiento;
    private int idSolicitud;
    private String fechaSolicitud;
    private float costoEstimado;
    private float tamanioPantalla;
    private int memoriaRAM;
    
    private SolicitudConUsuarioYEquipo solicitudCompletaSeleccionada 
            = new SolicitudConUsuarioYEquipo();
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cargarListaSolicitudes();
        cambioTipoMantenimiento();
        paneDetalles.setVisible(false);
        lvSolicitudes.setOnMouseClicked(this::clicSeleccionarSolicitud);
    }
    
    private void cargarListaSolicitudes() {
        listaSolicitudes = FXCollections.observableArrayList();
        
        ListaSolicitudesRespuesta respuestaBD 
            = SolicitudesDAO.recuperarSolicitudesConUsuarioYEquipo();
        switch(respuestaBD.getCodigoRespuesta()) {
            case Constantes.ERROR_CONEXION:
                Utilidades.mostrarDialogoSimple(
                    "Sin conexión", 
                    "Lo sentimos, por el momento "
                    + "no tiene conexión para acceder a la información", 
                        Alert.AlertType.ERROR
                );
                break;
            case Constantes.ERROR_CONSULTA:
                Utilidades.mostrarDialogoSimple(
                    "No fue posible cargar los datos", 
                    "Hubo un error al cargar la información"
                    + ", por favor intente más tarde", 
                    Alert.AlertType.ERROR
                );
                break;
            case Constantes.OPERACION_EXITOSA:
                listaSolicitudes.addAll(respuestaBD.getSolicitudesCompletas());
                lvSolicitudes.setItems(listaSolicitudes);
                break;
        }
    }

    @FXML
    private void clicBtnRegresar(MouseEvent event) {
        Stage escenarioPrincipal = (Stage) tfContraseniaSO.getScene().getWindow();
        escenarioPrincipal.close();
    }

    @FXML
    private void clicBtnGuardarCambios(ActionEvent event) {
       int idSolicitudDiagnostico = 
                solicitudCompletaSeleccionada.getSolicitud().getIdSolicitud();
        int idUsuario = 
                solicitudCompletaSeleccionada.getUsuario().getIdUsuario();
        
        boolean camposValidos = validarCamposGuardarCambios();
        if(camposValidos) {
            obtenerInformacionEquipos();
            int respuestaCreación = EquiposComputoDAO.crearEquipoComputo
        (nuevoEquipoComputo, idUsuario, idSolicitudDiagnostico);
            switch(respuestaCreación) {
                case Constantes.ERROR_CONEXION:
                    Utilidades.mostrarDialogoSimple(
                        "Error de conexión",
                        "Ocurrió un error al guardar los datos, intente más tarde", 
                        Alert.AlertType.ERROR
                        );
                    break;
                case Constantes.ERROR_CONSULTA:
                    Utilidades.mostrarDialogoSimple(
                        "Error en la creación",
                        "Ocurrió un error al guardar los datos, intente más tarde", 
                        Alert.AlertType.WARNING
                    );
                    break;
                case Constantes.OPERACION_EXITOSA:
                    Utilidades.mostrarDialogoSimple(
                        "Información actualizada",
                        "La información del equipo se ha actualizado correctamente", 
                        Alert.AlertType.INFORMATION
                    );
                    break;
            }
        } else {
            Utilidades.mostrarDialogoSimple(
                "Verificar información", 
                "Por favor complete los campos y verifique que los valores "
                 + "ingresados en tamaño pantalla y memoria RAM sean numéricos",
                Alert.AlertType.WARNING
            );
        }
        actualizarListView();
       
    }
    
    @FXML
    private void clicBtnRechazar(ActionEvent event) {
        int respuestaRechazo = 
                SolicitudesDAO.actualizarEstadoSolicitud(Constantes.ESTADO_SOLICITUD_RECHAZADA, 
                            idSolicitud);
        switch(respuestaRechazo) {
            case Constantes.ERROR_CONEXION:
                Utilidades.mostrarDialogoSimple(
                    "Error de conexión",
                    "Ocurrió un error al rechazar la solicitud, intente más tarde", 
                    Alert.AlertType.ERROR
                    );
                break;
            case Constantes.ERROR_CONSULTA:
                Utilidades.mostrarDialogoSimple(
                    "Error en la consulta",
                    "Ocurrió un error al rechazar la solicitud, intente más tarde", 
                    Alert.AlertType.WARNING
                );
                break;
            case Constantes.OPERACION_EXITOSA:
                Utilidades.mostrarDialogoSimple(
                    "Solicitud rechazada",
                    "La solicitud se ha rechazado correctamente", 
                    Alert.AlertType.INFORMATION
                );
                limpiarPaneDetalles();
                actualizarListView();
                break;
        }       
    }

    @FXML
    private void clicBtnFinalizar(ActionEvent event) {
        boolean camposValidos = validarCampos();
        if(camposValidos) {
            establecerDiagnostico();
            int respuestaCreación = DiagnosticoDAO.crearDiagnostico(diagnostico);
            switch(respuestaCreación) {
                case Constantes.ERROR_CONEXION:
                    Utilidades.mostrarDialogoSimple(
                        "Error de conexión",
                        "Ocurrió un error al guardar los datos, intente más tarde", 
                        Alert.AlertType.ERROR
                        );
                    break;
                case Constantes.ERROR_CONSULTA:
                    Utilidades.mostrarDialogoSimple(
                        "Error en la creación",
                        "Ocurrió un error al guardar los datos, intente más tarde", 
                        Alert.AlertType.WARNING
                    );
                    break;
                case Constantes.OPERACION_EXITOSA:
                    Utilidades.mostrarDialogoSimple(
                        "Diagnostico guardado",
                        "El diagnóstico se ha guardado correctamente", 
                        Alert.AlertType.INFORMATION
                    );
                    limpiarPaneDetalles();
                    actualizarListView();
                    break;
            }
        } else {
            Utilidades.mostrarDialogoSimple(
                "Campos inválidos u obligatorios", 
                "Por favor complete los campos obligatorios y escriba información válida", 
                Alert.AlertType.WARNING
            );
        } 
    }

    @FXML
    private void clicSeleccionarSolicitud(MouseEvent event) {
        int posicion = lvSolicitudes.getSelectionModel().getSelectedIndex();
        if(posicion != -1){
            mostrarSolicitud(posicion);
        }else{
            paneDetalles.setVisible(false);
        }
    }
    
    private void mostrarSolicitud(int posicion){
        paneDetalles.setVisible(true);
        dpFechaAtencion.setEditable(false);
        DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        dpFechaAtencion.setConverter(new LocalDateStringConverter(formatter1, null));
        
        EquipoComputo equipo = listaSolicitudes.get(posicion).getEquipo();
        Usuario usuario = listaSolicitudes.get(posicion).getUsuario();
        Solicitud solicitud = listaSolicitudes.get(posicion).getSolicitud();
        
        idSolicitud = solicitud.getIdSolicitud();
        fechaSolicitud = solicitud.getFechaInicio();
        solicitudCompletaSeleccionada.setEquipo(equipo);
        solicitudCompletaSeleccionada.setSolicitud(solicitud);
        solicitudCompletaSeleccionada.setUsuario(usuario);
        
        tfMarca.setText(equipo.getMarca());
        tfModelo.setText(equipo.getModelo()); 
        tfTamanioPantalla.setText(String.valueOf(equipo.getTamanioPantalla())); 
        tfProcesador.setText(equipo.getProcesador());
        tfMemoriaRAM.setText(String.valueOf(equipo.getMemoriaRAM()));
        tfSO.setText(equipo.getSistemaOperativo());
        tfUsuarioSO.setText(equipo.getUsuarioSO());
        tfUsuarioSO.setEditable(true);
        tfContraseniaSO.setText(equipo.getContraseniaSO());
        tfContraseniaSO.setEditable(true);
        lbModeloEquipo.setText(equipo.getModelo());
        lbObervaciones.setText(solicitud.getObservaciones());
        lbFechaSolicitud.setText(solicitud.getFechaInicio());
        
        lbNombre.setText(usuario.getNombre()+" "+usuario.getApellidoPaterno()
                +" "+usuario.getApellidoMaterno());
        lbDireccion.setText(usuario.getDirección());
        lbCorreo.setText(usuario.getCorreo());
        lbTelefono.setText(usuario.getTelefono());
        mostrarImagenPantalla(equipo);
    }
    
    private void mostrarImagenPantalla(EquipoComputo equipo){
        if(equipo.getImagen() != null){
           try{
                byte[] data = equipo.getImagen();
                BufferedImage img = ImageIO.read(new ByteArrayInputStream(data));
                Image imagenDecodificada = SwingFXUtils.toFXImage(img, null);
                ivFoto.setImage(imagenDecodificada);
           }catch(IOException ex){
               System.out.println(ex.getMessage());
           }
        }else{
            Utilidades.mostrarDialogoSimple(
                "Error",
                "No fue posible cargar la imagen, intente más tarde",
                Alert.AlertType.ERROR
            );
            ivFoto.setImage(null);
        }
    }
    
    private void establecerDiagnostico(){
       diagnostico.setDiagnosticoPreliminar(tfDiagnostico.getText());
       diagnostico.setPropuestaSolucion(tfPropuesta.getText());
       diagnostico.setCostoEstimado(costoEstimado);
       diagnostico.setFechaAtencion(obtenerFechaAtencion());
       diagnostico.setTipoDeMantenimiento(tipoMantenimiento);
       diagnostico.setFechaSolicitud(fechaSolicitud);
       diagnostico.setIdSolicitudDiagnostico(idSolicitud);
    }
    
    private void cambioTipoMantenimiento(){
        tgTipoMantenimiento.selectedToggleProperty().addListener(new ChangeListener<Toggle>(){
            @Override
            public void changed(ObservableValue<? extends Toggle> observable, Toggle oldValue, Toggle newValue) {
                if(rbCorrectivo.isSelected()){
                    tipoMantenimiento = "Correctivo";
                }else if (rbPreventivo.isSelected()){
                    tipoMantenimiento = "Preventivo";
                }
            }
        });
    }
    private void obtenerInformacionEquipos(){
        EquipoComputo equipoRecuperado = solicitudCompletaSeleccionada.getEquipo();
        
        nuevoEquipoComputo.setTipo(equipoRecuperado.getTipo());
        nuevoEquipoComputo.setIncluyeCargador(equipoRecuperado.isIncluyeCargador());
        nuevoEquipoComputo.setModelo(tfModelo.getText());
        nuevoEquipoComputo.setSistemaOperativo(tfSO.getText());
        nuevoEquipoComputo.setTamanioPantalla(tamanioPantalla);
        nuevoEquipoComputo.setContraseniaSO(tfContraseniaSO.getText());
        nuevoEquipoComputo.setProcesador(tfProcesador.getText());
        nuevoEquipoComputo.setMemoriaRAM(memoriaRAM);
        nuevoEquipoComputo.setMarca(tfMarca.getText());
        nuevoEquipoComputo.setFechaRegistro(equipoRecuperado.getFechaRegistro());
        nuevoEquipoComputo.setImagen(equipoRecuperado.getImagen());
        nuevoEquipoComputo.setUsuarioSO(tfUsuarioSO.getText());
        
    }
    
    private boolean validarCampos(){
        boolean camposValidos;
        boolean campoCosto = true;
        boolean tipoMantenimiento = rbCorrectivo.isSelected() || rbPreventivo.isSelected();
        
        try{
            costoEstimado = Float.parseFloat(tfCosto.getText());
            if(costoEstimado < 0){
                tfCosto.setText("");
                campoCosto = false;
            }
        }catch(NumberFormatException nfe){
            tfCosto.setText("");
            Utilidades.mostrarDialogoSimple("Error de entrada", 
                "Debe colocar un numero en el Costo Estimado", 
                Alert.AlertType.WARNING);
            campoCosto = false;
        }
        
        camposValidos = 
            !tfDiagnostico.getText().isEmpty()
            && !tfPropuesta.getText().isEmpty()
            && dpFechaAtencion.getValue() != null;
        
        return camposValidos && campoCosto && tipoMantenimiento;
    }
    
    private void actualizarListView() {
    listaSolicitudes.clear(); 
    cargarListaSolicitudes(); 
        if (!listaSolicitudes.isEmpty()) {
            lvSolicitudes.getSelectionModel().selectFirst();
        }
    }
    
    private void limpiarPaneDetalles(){
        paneDetalles.setVisible(false);
        tfDiagnostico.setText("");
        tfPropuesta.setText("");
        tfCosto.setText("");
        dpFechaAtencion.setValue(null);
        rbCorrectivo.setSelected(false);
        rbPreventivo.setSelected(false);
    }
    
    private boolean validarCamposGuardarCambios() {
        boolean campoTamañoPantalla = true;
        boolean camposMemoriaRAM = true;
        TextField[] camposRequeridos = {tfMarca, tfTamanioPantalla, tfModelo,
            tfProcesador, tfMemoriaRAM, tfSO, tfUsuarioSO, tfContraseniaSO};
        for (TextField campo : camposRequeridos) {
            if (campo.getText().isEmpty()) {
                return false;
            }
        }
        try {
            tamanioPantalla = Float.parseFloat(tfTamanioPantalla.getText());
        } catch(NumberFormatException nfe) {
            tfTamanioPantalla.setText("");
            campoTamañoPantalla = false;
        }
        try {
            memoriaRAM = Integer.parseInt(tfMemoriaRAM.getText());
        } catch(NumberFormatException nfe) {
            tfMemoriaRAM.setText("");
            camposMemoriaRAM = false;
        }
        return campoTamañoPantalla && camposMemoriaRAM;
    }

    private String obtenerFechaAtencion(){
        String fechaAtencion = null;
        
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate fecha = dpFechaAtencion.getValue();
        fechaAtencion = fecha.format(formatter);
        
        return fechaAtencion;
    }
}