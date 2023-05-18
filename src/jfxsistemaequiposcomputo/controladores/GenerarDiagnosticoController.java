package jfxsistemaequiposcomputo.controladores;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javax.imageio.ImageIO;
import jfxsistemaequiposcomputo.DAO.EquiposComputoDAO;
import jfxsistemaequiposcomputo.DAO.SolicitudesDAO;
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

    private Usuario usuario;
    private ObservableList<SolicitudConUsuarioYEquipo> listaSolicitudes;
    private EquipoComputo equipo = new EquipoComputo();
    private SolicitudConUsuarioYEquipo solicitudCompletaSeleccionada 
            = new SolicitudConUsuarioYEquipo();
    
    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cargarListaSolicitudes();
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
       obtenerInformacionEquipos();
       Utilidades.mostrarDialogoSimple("Informacion Actualizada",
               "La información se ha modificado correctamente", 
               Alert.AlertType.INFORMATION);
        
    }
    
    @FXML
    private void clicBtnCancelar(ActionEvent event) {
        paneDetalles.setVisible(false);
    }

    @FXML
    private void clicBtnGuardar(ActionEvent event) {
       
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
        solicitudCompletaSeleccionada.setEquipo(equipo);
        solicitudCompletaSeleccionada.setSolicitud(solicitud);
        solicitudCompletaSeleccionada.setUsuario(usuario);
        
        tfMarca.setText(equipo.getMarca());
        tfModelo.setText(equipo.getModelo()); 
        tfTamañoPantalla.setText(equipo.getTamanioPantalla()); 
        tfProcesador.setText(equipo.getProcesador());
        tfMemoriaRAM.setText(equipo.getMemoriaRAM());
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
    
    private void obtenerInformacionEquipos(){
        EquipoComputo nuevoEquipoComputo = new EquipoComputo();
        EquipoComputo equipoRecuperado = solicitudCompletaSeleccionada.getEquipo();
        
        nuevoEquipoComputo.setTipo(equipoRecuperado.getTipo());
        nuevoEquipoComputo.setIncluyeCargador(equipoRecuperado.isIncluyeCargador());
        nuevoEquipoComputo.setModelo(tfModelo.getText());
        nuevoEquipoComputo.setSistemaOperativo(tfSO.getText());
        nuevoEquipoComputo.setTamanioPantalla(tfTamañoPantalla.getText());
        nuevoEquipoComputo.setContraseniaSO(tfContraseniaSO.getText());
        nuevoEquipoComputo.setProcesador(tfProcesador.getText());
        nuevoEquipoComputo.setMemoriaRAM(tfMemoriaRAM.getText());
        nuevoEquipoComputo.setMarca(tfMarca.getText());
        nuevoEquipoComputo.setFechaRegistro(equipoRecuperado.getFechaRegistro());
        nuevoEquipoComputo.setImagen(equipoRecuperado.getImagen());
        nuevoEquipoComputo.setUsuarioSO(tfUsuarioSO.getText());
        int idSolicitudDiagnostico = 
                solicitudCompletaSeleccionada.getSolicitud().getIdSolicitud();
        int idUsuario = 
                solicitudCompletaSeleccionada.getUsuario().getIdUsuario();
        EquiposComputoDAO.crearEquipoComputo(nuevoEquipoComputo, idUsuario, idSolicitudDiagnostico);
    
        }
    
    }
    

