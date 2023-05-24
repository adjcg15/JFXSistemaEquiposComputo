package jfxsistemaequiposcomputo.controladores;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javax.imageio.ImageIO;
import jfxsistemaequiposcomputo.DAO.MantenimientosDAO;
import jfxsistemaequiposcomputo.DAO.SolicitudesDAO;
import jfxsistemaequiposcomputo.pojo.Estado;
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
    private Button BtnGuardarComentario;
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
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private List<Label> labelsFechaInicio = new ArrayList<>();
    private List<Label> labelsFechaFin = new ArrayList<>();
    MantenimientoConEquipoYDiagnostico mantenimiento = null;
    String estadoActual = null;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cargarListaMantenimientos();
        paneDetalles.setVisible(false);
        lvMantenimientos.setOnMouseClicked(this::clicSeleccionarSolicitud);
     
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
        int posicion = lvMantenimientos.getSelectionModel().getSelectedIndex();
        if(posicion != -1){
            mostrarSolicitud(posicion);
        }else{
            paneDetalles.setVisible(false);
        }
    }
    
    private void mostrarSolicitud(int posicion){
        lbFechaInicioDiagnostico.setText(null);
        lbFechaFinDiagnostico1.setText(null);
        lbFechaInicioMantenimiento.setText(null);
        lbFechaFinMantenimiento.setText(null);
        lbFechaInicioRevision.setText(null);
        lbFechaFinRevision.setText(null);
        lbFechaFinFinalizado.setText(null);
        btnPasarEstado.setDisable(false);    
        btnPasarEstado.setText("Pasar a mantenimiento");
        
        paneDetalles.setVisible(true);
        mantenimiento = listaMantenimientos.get(posicion);
        lbDiagnosticoPreliminar.setText(mantenimiento.getDiagnostico().getDiagnosticoPreliminar());
        lbPropuestaSolucion.setText(mantenimiento.getDiagnostico().getPropuestaSolucion()); 
        lbTipoMantenimiento.setText(mantenimiento.getDiagnostico().getTipoDeMantenimiento());
        lbModeloEquipo.setText(mantenimiento.getEquipo().getModelo());
        tfComentarios.setText(mantenimiento.getMantenimiento().getComentario());
        mostrarImagenEquipo(mantenimiento.getEquipo().getImagen());
        
        ArrayList<Estado> estados = mantenimiento.getEstados();
        
        estadoActual = Constantes.ESTADO_SOLICITUD_DIAGNOSTICO;
        
        if(estados.size() >= 3) {
            Estado diagnostico = estados.get(2);
            lbFechaInicioDiagnostico.setText(diagnostico.getFechaInicio());
            if(!diagnostico.getFechaFin().isEmpty()) {
                lbFechaFinDiagnostico1.setText(diagnostico.getFechaFin());
                estadoActual = Constantes.ESTADO_SOLICITUD_MANTENIMIENTO;
            }
            
            if(estados.size() >= 4){
                Estado estadoMantenimiento = estados.get(3);
                lbFechaInicioMantenimiento.setText(estadoMantenimiento.getFechaInicio());
                if(!estadoMantenimiento.getFechaFin().isEmpty()) {
                    lbFechaFinMantenimiento.setText(estadoMantenimiento.getFechaFin());
                    estadoActual = Constantes.ESTADO_SOLICITUD_REVISION;
                }
            }
            
            if(estados.size() >= 5){
                Estado revisión = estados.get(4);
                lbFechaInicioRevision.setText(revisión.getFechaInicio());
                if(!revisión.getFechaFin().isEmpty()) {
                    lbFechaFinRevision.setText(revisión.getFechaFin());
                    lbFechaFinFinalizado.setText(revisión.getFechaFin());
                    estadoActual = Constantes.ESTADO_SOLICITUD_FINALIZADO;
                    btnPasarEstado.setDisable(true);
                }
            }
            
            btnPasarEstado.setDisable(estadoActual.equals(Constantes.ESTADO_SOLICITUD_FINALIZADO));
            
        }
        
        btnPasarEstado.setText("Pasar a " + estadoActual);
         
    }
    
    private void mostrarImagenEquipo(byte[] fotoEquipo) {
        if (fotoEquipo != null) {
            try {
                ByteArrayInputStream bis = new ByteArrayInputStream(fotoEquipo);
                BufferedImage bufferedImage = ImageIO.read(bis);
                Image image = SwingFXUtils.toFXImage(bufferedImage, null);
                ivFoto.setImage(image);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            Utilidades.mostrarDialogoSimple(
                "Error",
                "No fue posible cargar la imagen, intente más tarde",
                Alert.AlertType.ERROR
            );
            ivFoto.setImage(null);
        }
    
    }
    
    @FXML
    private void clicBtnRegresar(MouseEvent event) {
        Stage escenarioPrincipal = (Stage) tfComentarios.getScene().getWindow();
        escenarioPrincipal.close();
    }

    @FXML
    private void clicBtnGuardarComentario(ActionEvent event) {
        int posicion = lvMantenimientos.getSelectionModel().getSelectedIndex();
        if (posicion != -1) {
        MantenimientoConEquipoYDiagnostico mantenimientos = listaMantenimientos.get(posicion);
        int idDiagnostico = mantenimientos.getDiagnostico().getIdDiagnostico();
        String comentario = tfComentarios.getText();

        if (comentario != null && !comentario.isEmpty()) {
            int resultado = MantenimientosDAO.guardarComentario(idDiagnostico, comentario);

            switch (resultado) {
                case Constantes.OPERACION_EXITOSA:
                    Utilidades.mostrarDialogoSimple("Comentario guardado", 
                            "El comentario se ha guardado correctamente.", 
                            Alert.AlertType.INFORMATION);
                    BtnGuardarComentario.setDisable(true);
                    break;

                case Constantes.ERROR_CONEXION:
                    Utilidades.mostrarDialogoSimple("Error de conexión", 
                            "No se puede establecer conexión con la base de datos.", 
                            Alert.AlertType.ERROR);
                    break;

                default:
                    Utilidades.mostrarDialogoSimple("Error al guardar", 
                            "Ha ocurrido un error al guardar el comentario.",
                            Alert.AlertType.ERROR);
                    break;
            }
        } else {
            Utilidades.mostrarDialogoSimple("Comentario vacío",
                    "Debes ingresar un comentario.", 
                    Alert.AlertType.WARNING);
        }
        } else {
            System.out.println("Error: No se ha seleccionado un elemento en lvMantenimientos.");
        }
    }

    
    @FXML
    private void clicBtnPasarEstado(ActionEvent event) {
        String nombreEstado;
        switch(estadoActual) {
                case Constantes.ESTADO_SOLICITUD_DIAGNOSTICO:
                    nombreEstado = Constantes.ESTADO_SOLICITUD_MANTENIMIENTO;
                    break;
                case Constantes.ESTADO_SOLICITUD_MANTENIMIENTO:
                    nombreEstado = Constantes.ESTADO_SOLICITUD_REVISION;
                    break;
                case Constantes.ESTADO_SOLICITUD_REVISION:
                    nombreEstado = Constantes.ESTADO_SOLICITUD_FINALIZADO;
                    break;
                default:
                    return;
        }
        
        
        int respuestaActualizacion = 
                SolicitudesDAO.actualizarEstadoSolicitud(nombreEstado, 
                        mantenimiento.getDiagnostico().getIdSolicitudDiagnostico());
        
        if(respuestaActualizacion != Constantes.OPERACION_EXITOSA) {
            
        } else {
            System.out.println("error");
            
            LocalDate fechaActual = LocalDate.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            String fechaActualFormateada = fechaActual.format(formatter);

            if(estadoActual.equals(Constantes.ESTADO_SOLICITUD_DIAGNOSTICO)) {
                estadoActual = Constantes.ESTADO_SOLICITUD_MANTENIMIENTO;
                lbFechaFinDiagnostico1.setText(fechaActualFormateada);
                lbFechaInicioMantenimiento.setText(fechaActualFormateada);
            } else if(estadoActual.equals(Constantes.ESTADO_SOLICITUD_MANTENIMIENTO)) {
                estadoActual = Constantes.ESTADO_SOLICITUD_REVISION;
                lbFechaFinMantenimiento.setText(fechaActualFormateada);
                lbFechaInicioRevision.setText(fechaActualFormateada);
            } else if(estadoActual.equals(Constantes.ESTADO_SOLICITUD_REVISION)) {
                estadoActual = Constantes.ESTADO_SOLICITUD_FINALIZADO;
                lbFechaFinRevision.setText(fechaActualFormateada);
                lbFechaFinFinalizado.setText(fechaActualFormateada);
                btnPasarEstado.setDisable(true);
            } 
            
            switch(estadoActual) {
                case Constantes.ESTADO_SOLICITUD_MANTENIMIENTO:
                    btnPasarEstado.setText("Pasar a Revision");
                    break;
                case Constantes.ESTADO_SOLICITUD_REVISION:
                    btnPasarEstado.setText("Pasar a Finalizar");
                    break;
            }

            
        }
        
        actualizarListView();
        
    }  
    private void actualizarListView() {
    listaMantenimientos.clear(); 
    cargarListaMantenimientos(); 
    
        if (!listaMantenimientos.isEmpty()) {
            lvMantenimientos.getSelectionModel().selectFirst();
        }
    }
       
    @FXML
    private void clicBtnAgregar(ActionEvent event) {
    }
    
  }
