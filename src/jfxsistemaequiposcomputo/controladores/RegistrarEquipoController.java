package jfxsistemaequiposcomputo.controladores;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javax.imageio.ImageIO;
import jfxsistemaequiposcomputo.DAO.SolicitudesDAO;
import jfxsistemaequiposcomputo.pojo.SolicitudMantenimiento;
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
    @FXML
    ObservableList<String> listaTiposEquipos;
    @FXML
    private RadioButton rbSi;
    @FXML
    private RadioButton rbNo;
    @FXML
    private ToggleGroup tgCargador;
    @FXML
    private TextField tfMarca;
    @FXML
    private TextField tfModelo;
    @FXML
    private TextField tfTamañoPantalla;
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
    
    private boolean cargadorIncluido;
    private SolicitudMantenimiento solicitud = new SolicitudMantenimiento();
    private Usuario usuario;
    
    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        System.out.println("Inicializando vista");
        listaTiposEquipos = FXCollections.observableArrayList(Constantes.TIPOS_EQUIPOS);
        System.out.println("Lista de equipos seteada");
        configurarComboBox();
        System.out.println("Combo configurado");
        configurarCambioCargador();
        System.out.println("Cargador configurado");
    }    
    
    private void configurarComboBox (){
        cbTipoEquipo.setItems(listaTiposEquipos);
    }
    
    private void configurarCambioCargador(){
        tgCargador
            .selectedToggleProperty()
            .addListener(new ChangeListener<Toggle>(){
                @Override
                public void changed(ObservableValue<? extends Toggle> observable, Toggle oldValue, Toggle newValue) {
                    if(rbSi.isSelected()){
                       cargadorIncluido = true;
                    }else if(rbNo.isSelected()){
                       cargadorIncluido = false;
                    }
                }
            });
    }
    
    @FXML
    private void clicBtnSeleccionarImagen(ActionEvent event) {
        FileChooser seleccionImagen = new FileChooser();
        FileChooser.ExtensionFilter filtroDialogo = 
                new FileChooser.ExtensionFilter("Archivos PNG (*.png)", "*.PNG");
        
        seleccionImagen.setTitle("Selecciona una imagen");
        seleccionImagen.getExtensionFilters().add(filtroDialogo);
        
        Stage escenarioPrincipal = 
            (Stage) tfDescripcionProblema.getScene().getWindow();
        File archivoSeleccionado = 
            seleccionImagen.showOpenDialog(escenarioPrincipal);
        visualizarImagen(archivoSeleccionado);
    }
    
    private void visualizarImagen(File imgSeleccionada){
        if(imgSeleccionada != null){
            try {
                BufferedImage bufferImg = ImageIO.read(imgSeleccionada);
                Image imagenDecodificada = 
                    SwingFXUtils.toFXImage(bufferImg, null);
                
                ivImagenEquipo.setImage(imagenDecodificada);
                
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                ImageIO.write(bufferImg, "jpg", outputStream);
                byte[] bytesImagen = outputStream.toByteArray();
                solicitud.setImagen(bytesImagen);
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
       establecerSolicitud();
       int respuestaCreación = SolicitudesDAO.crearSolicitud(solicitud);
       System.out.println(respuestaCreación);
    }
    
    private void establecerSolicitud() {
        solicitud.setTipo(cbTipoEquipo.getValue());
        solicitud.setIncluyeCargador(cargadorIncluido);
        solicitud.setMarca(tfMarca.getText());
        solicitud.setModelo(tfModelo.getText());
        solicitud.setTamanioPantalla(tfTamañoPantalla.getText());
        solicitud.setProcesador(tfProcesador.getText());
        solicitud.setMemoriaRAM(tfMemoriaRAM.getText());
        solicitud.setSistemaOperativo(tfSO.getText());
        solicitud.setUsuarioSO(tfUsuarioSO.getText());
        solicitud.setContraeniaSO(tfContraseniaSO.getText());
        solicitud.setObservaciones(tfDescripcionProblema.getText());
        solicitud.setIdUsuario(usuario.getIdUsuario());
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
    
}
