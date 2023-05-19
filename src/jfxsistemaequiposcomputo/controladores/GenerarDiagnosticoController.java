package jfxsistemaequiposcomputo.controladores;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URL;
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
    private TextField tfTamañoPantalla;
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
    private TextField tfFecha;
    @FXML
    private RadioButton rbPreventivo;
    @FXML
    private RadioButton rbCorrectivo;
    @FXML
    private Label lbTituloSeccion;
    @FXML
    private ToggleGroup tgTipoMantenimiento;

    private Usuario usuario;
    private ObservableList<SolicitudConUsuarioYEquipo> listaSolicitudes;
    private Diagnostico diagnostico = new Diagnostico();
    private EquipoComputo equipo = new EquipoComputo();
    EquipoComputo nuevoEquipoComputo = new EquipoComputo();
    
    private String tipoMantenimiento;
    private int idSolicitud;
    private String fechaSolicitud;
    private float costoEstimado;
    private float tamanioPantalla;
    private int memoriaRAM;
    
    private SolicitudConUsuarioYEquipo solicitudCompletaSeleccionada 
            = new SolicitudConUsuarioYEquipo();
    
    
    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
    
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
                        "Diagnostico guardado",
                        "El diagnóstico se ha guardado correctamente", 
                        Alert.AlertType.INFORMATION
                    );
                    break;
            }
        } else {
            System.out.println("valores incorrectos");
        }
        actualizarListView();
       
    }
    
    @FXML
    private void clicBtnCancelar(ActionEvent event) {
        paneDetalles.setVisible(false);
        tfDiagnostico.setText("");
        tfPropuesta.setText("");
        tfCosto.setText("");
        tfFecha.setText("");
        rbCorrectivo.setSelected(false);
        rbPreventivo.setSelected(false);
    }

    @FXML
    private void clicBtnGuardar(ActionEvent event) {
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
                    Stage escenarioPrincipal = (Stage) tfDiagnostico.getScene().getWindow();
                    escenarioPrincipal.close();
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
        tfTamañoPantalla.setText(String.valueOf(equipo.getTamanioPantalla())); 
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
       diagnostico.setFechaAtencion(tfFecha.getText());
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
        //EquipoComputo nuevoEquipoComputo = new EquipoComputo();
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
            && !tfFecha.getText().isEmpty();
        
        return camposValidos && campoCosto && tipoMantenimiento;
    }
    
    private void actualizarListView() {
    listaSolicitudes.clear(); 
    cargarListaSolicitudes(); 
    
        if (!listaSolicitudes.isEmpty()) {
            lvSolicitudes.getSelectionModel().selectFirst();
        }
    }
    
    private boolean validarCamposGuardarCambios(){
        boolean campoTamañoPantalla = true;
        boolean camposMemoriaRAM = true;
        try{
            tamanioPantalla = Float.parseFloat(tfTamañoPantalla.getText());           
        }catch(NumberFormatException nfe){
            tfTamañoPantalla.setText("");
            Utilidades.mostrarDialogoSimple
        ("Error", "Debe ingresar un valor numerico para el tamaño de pantalla", 
                Alert.AlertType.WARNING);
            campoTamañoPantalla=false;
          }
        try{
            memoriaRAM = Integer.parseInt(tfMemoriaRAM.getText());          
        }catch(NumberFormatException nfe){
            tfMemoriaRAM.setText("");
            Utilidades.mostrarDialogoSimple
        ("Error", "Debe ingresar un valor numerico para la memoria RAM", 
                Alert.AlertType.WARNING);
            camposMemoriaRAM = false;
  
        }
        return campoTamañoPantalla && camposMemoriaRAM;
    }
    
    
 }