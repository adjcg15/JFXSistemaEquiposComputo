package jfxsistemaequiposcomputo.controladores;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
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
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javax.imageio.ImageIO;
import jfxsistemaequiposcomputo.DAO.SolicitudesDAO;
import jfxsistemaequiposcomputo.pojo.EquipoComputo;
import jfxsistemaequiposcomputo.pojo.Solicitud;
import jfxsistemaequiposcomputo.pojo.SolicitudConUsuarioYEquipo;
import jfxsistemaequiposcomputo.pojo.Usuario;
import jfxsistemaequiposcomputo.utils.Constantes;
import jfxsistemaequiposcomputo.utils.Utilidades;

/**
 * FXML Controller class
 *
 * @author dnava
 */
public class RegistrarEquipoController implements Initializable {

    @FXML
    private ComboBox<String> cbTipoEquipo;
    ObservableList<String> listaTiposEquipos;
    @FXML
    private TextField tfMarca;
    @FXML
    private TextField tfModelo;
    @FXML
    private TextField tfTamanioPantalla;
    @FXML
    private TextField tfProcesador;
    @FXML
    private TextField tfMemoriaRAM;
    @FXML
    private TextField tfSO;
    @FXML
    private TextField tfUsuarioSO;
    @FXML
    private TextField tfContraseniaSO;
    @FXML
    private TextField tfDescripcionProblema;
    @FXML
    private ImageView ivImagenEquipo;
    @FXML
    private CheckBox cboxIncluyeCargador;
    
    private Usuario usuario = new Usuario();
    private Solicitud solicitud = new Solicitud();
    private EquipoComputo equipo = new EquipoComputo();
    
    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        listaTiposEquipos = FXCollections.observableArrayList(Constantes.TIPOS_EQUIPOS);
        configurarComboBox();
    }    
    
    private void configurarComboBox (){
        cbTipoEquipo.setItems(listaTiposEquipos);
    }
    
    @FXML
    private void clicBtnSeleccionarImagen(ActionEvent event) {
        FileChooser seleccionImagen = new FileChooser();
        FileChooser.ExtensionFilter filtroDialogo = 
                new FileChooser.ExtensionFilter("Imagen .jpg o .png", "*.PNG", "*.JPG");
        
        seleccionImagen.setTitle("Selecciona una imagen");
        seleccionImagen.getExtensionFilters().add(filtroDialogo);
        
        Stage escenarioPrincipal = 
            (Stage) tfDescripcionProblema.getScene().getWindow();
        File imagenSeleccionada = 
            seleccionImagen.showOpenDialog(escenarioPrincipal);
        mostrarImagenSeleccionada(imagenSeleccionada);
    }
    
    private void mostrarImagenSeleccionada (File imagenSeleccionada){
        if(imagenSeleccionada != null){
            try {
                BufferedImage bufferImg = ImageIO.read(imagenSeleccionada);
                Image imagenDecodificada = 
                    SwingFXUtils.toFXImage(bufferImg, null);
                
                ivImagenEquipo.setImage(imagenDecodificada);
                
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                ImageIO.write(bufferImg, "jpg", outputStream);
                byte[] bytesImagen = outputStream.toByteArray();
                equipo.setImagen(bytesImagen);
           } catch (IOException ex) {
                Utilidades.mostrarDialogoSimple(
                    "Error", 
                    "No fue posible cargar la imagen, intente más tarde", 
                    Alert.AlertType.ERROR
                );
            }
        }
    }

    @FXML
    private void clicBtnGuardar(ActionEvent event) {
        establecerSolicitudCompleta();
        
        SolicitudConUsuarioYEquipo solicitudCompleta = new SolicitudConUsuarioYEquipo();
        solicitudCompleta.setUsuario(usuario);
        solicitudCompleta.setSolicitud(solicitud);
        solicitudCompleta.setEquipo(equipo);
        
        boolean camposValidos = validarSolicitud(solicitudCompleta);
        if (camposValidos) {
            solicitudCompleta.getEquipo().setMemoriaRAM(Integer.parseInt(tfMemoriaRAM.getText()));
            solicitudCompleta.getEquipo().setTamanioPantalla(Float.parseFloat(tfTamanioPantalla.getText()));
            
            int respuestaCreacion 
                = SolicitudesDAO.crearSolicitudConUsuarioYEquipo(solicitudCompleta);
            switch(respuestaCreacion) {
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
                        "Solicitud enviada",
                        "Su solicitud se ha enviado correctamente", 
                        Alert.AlertType.INFORMATION
                    );
                    Stage escenarioPrincipal = 
                        (Stage) tfDescripcionProblema.getScene().getWindow();
                    escenarioPrincipal.close();
                    break;
            }
        } else {
            Utilidades.mostrarDialogoSimple(
                "Campos obligatorios", 
                "Por favor complete los campos obligatorios. La RAM debe ser un número "
                + " y en caso de agregar un tamaño de pantalla, también debe ser "
                + " un número",
                Alert.AlertType.WARNING
            );
        } 
    }
    
    private void establecerSolicitudCompleta() {
        solicitud.setObservaciones(tfDescripcionProblema.getText());
        solicitud.setIdUsuario(usuario.getIdUsuario());
        
        equipo.setTipo(cbTipoEquipo.getValue());
        equipo.setIncluyeCargador(cboxIncluyeCargador.isSelected());
        equipo.setMarca(tfMarca.getText());
        equipo.setModelo(tfModelo.getText());
        equipo.setProcesador(tfProcesador.getText());
        
        equipo.setSistemaOperativo(tfSO.getText());
        equipo.setUsuarioSO(tfUsuarioSO.getText());
        equipo.setContraseniaSO(tfContraseniaSO.getText());
    }

    @FXML
    private void clicBtnCancelar(ActionEvent event) {
        Stage escenarioPrincipal = (Stage) tfDescripcionProblema.getScene().getWindow();
        escenarioPrincipal.close();
    }

    @FXML
    private void clicBtnRegresar(MouseEvent event) {
        Stage escenarioPrincipal = (Stage) tfDescripcionProblema.getScene().getWindow();
        escenarioPrincipal.close();
    }
    
    public boolean validarSolicitud(SolicitudConUsuarioYEquipo solicitudCompleta) {
        Solicitud solicitud = solicitudCompleta.getSolicitud();
        EquipoComputo equipoSolicitud = solicitudCompleta.getEquipo();
        
        String tipoEquipo = equipoSolicitud.getTipo();
        String marca = equipoSolicitud.getMarca();
        String modelo = equipoSolicitud.getModelo();
        String sistemaOperativo = equipoSolicitud.getSistemaOperativo();
        String usuarioSO = equipoSolicitud.getUsuarioSO();
        String contraseniaSO = equipoSolicitud.getContraseniaSO();
        byte[] imagen = equipoSolicitud.getImagen();
        
        boolean memoriaValida = true, 
                tamanioPantallaValido = true;
        try{
            if(!tfTamanioPantalla.getText().isEmpty()) {
                float tamanioPantalla = Float.parseFloat(tfTamanioPantalla.getText());
            }
        }catch(NumberFormatException nfe){
            tfTamanioPantalla.setText("");
            tamanioPantallaValido = false;
        }
        
        try{
            int memoriaRAM = Integer.parseInt(tfMemoriaRAM.getText());
        }catch(NumberFormatException nfe){
            tfMemoriaRAM.setText("");
            memoriaValida = false;
        }
        
        String observaciones = solicitud.getObservaciones();
        
        boolean camposTecnicosValidos =
            tipoEquipo != null 
            && marca != null 
            && modelo != null 
            && sistemaOperativo != null
            && usuarioSO != null
            && contraseniaSO != null
            && imagen != null
            && memoriaValida
            && tamanioPantallaValido;

        boolean camposSolicitudValidos =
            observaciones != null && !observaciones.isEmpty();
        
        return camposTecnicosValidos && camposSolicitudValidos;
    }
}
