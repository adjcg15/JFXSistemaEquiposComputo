package jfxsistemaequiposcomputo.controladores;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

/**
 * FXML Controller class
 *
 * @author rodri
 */
public class AdministrarMantenimientoController implements Initializable {

    @FXML
    private Pane paneDetalles;
    @FXML
    private ImageView ivFoto;
    @FXML
    private Label lbModeloEquipo;
    @FXML
    private Label lbDiagnosticoPreliminar;
    @FXML
    private Label lbTipoMantenimiento;
    @FXML
    private Label lbPropuestaSolucion;
    @FXML
    private TextField tfComentarios;
    @FXML
    private ListView<?> lvSolicitudes;
    @FXML
    private Label lbTituloSeccion;
    @FXML
    private Label lbDiagnostico;
    @FXML
    private Label lbMantenimiento;
    @FXML
    private Label lbRevision;
    @FXML
    private Label lbFinalizado;
    @FXML
    private Label lbFechaInicioDiagnostico;
    @FXML
    private Label lbFechaInicioMantenimiento;
    @FXML
    private Label lbFechaInicioRevision;
    @FXML
    private Label lbFechaFinDiagnostico1;
    @FXML
    private Label lbFechaFinMantenimiento;
    @FXML
    private Label lbFechaFinRevision;
    @FXML
    private Label lbFechaFinFinalizado;
    @FXML
    private Button btnPasarEstado;
    @FXML
    private ComboBox<?> cbTipoRefaccion;
    @FXML
    private ComboBox<?> cbNombreRefaccion;
    @FXML
    private TableView<?> tvListaRefacciones;
    @FXML
    private TableColumn<?, ?> colNombreRefaccion;
    @FXML
    private TableColumn<?, ?> colTipoRefaccion;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }    

    @FXML
    private void clicSeleccionarSolicitud(MouseEvent event) {
    }

    @FXML
    private void clicBtnRegresar(MouseEvent event) {
    }

    @FXML
    private void clicBtnGuardarComentario(ActionEvent event) {
    }

    @FXML
    private void clicBtnPasarEstado(ActionEvent event) {
    }

    @FXML
    private void clicBtnAgregar(ActionEvent event) {
    }
    
}
