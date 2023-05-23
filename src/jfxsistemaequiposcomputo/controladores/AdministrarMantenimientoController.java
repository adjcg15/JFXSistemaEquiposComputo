package jfxsistemaequiposcomputo.controladores;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
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
import jfxsistemaequiposcomputo.DAO.MantenimientosDAO;
import jfxsistemaequiposcomputo.pojo.ListaMantenimientosRespuesta;
import jfxsistemaequiposcomputo.pojo.MantenimientoConEquipoYDiagnostico;
import jfxsistemaequiposcomputo.utils.Constantes;
import jfxsistemaequiposcomputo.utils.Utilidades;

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
    private ListView<MantenimientoConEquipoYDiagnostico> lvMantenimientos;
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
    
    ObservableList<MantenimientoConEquipoYDiagnostico> listaMantenimientos;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cargarListaMantenimientos();
    }    
    
    private void cargarListaMantenimientos() {
        listaMantenimientos = FXCollections.observableArrayList();
        
        ListaMantenimientosRespuesta respuestaBD 
            = MantenimientosDAO.recuperarMantenimientosConEquipoYDiagnostico();
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
                listaMantenimientos.addAll(respuestaBD.getMantenimientosCompletos());
                lvMantenimientos.setItems(listaMantenimientos);
                break;
        }
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
