package jfxsistemaequiposcomputo.controladores;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import jfxsistemaequiposcomputo.pojo.Usuario;

/**
 * FXML Controller class
 *
 * @author ANGEL
 */
public class GenerarDiagnosticoController implements Initializable {
    @FXML
    private Pane paneDetalles;
    @FXML
    private ImageView ivFoto;
    @FXML
    private ListView<?> lvSolicitudes;
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
    
    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        paneDetalles.setVisible(true);
    }    

    @FXML
    private void clicBtnRegresar(MouseEvent event) {
    }

    @FXML
    private void clicBtnGuardarCambios(ActionEvent event) {
    }

    @FXML
    private void clicBtnCancelar(ActionEvent event) {
    }

    @FXML
    private void clicBtnGuardar(ActionEvent event) {
    }
    
}
