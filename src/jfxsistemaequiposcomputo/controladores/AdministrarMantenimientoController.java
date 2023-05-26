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
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javax.imageio.ImageIO;
import jfxsistemaequiposcomputo.DAO.MantenimientosDAO;
import jfxsistemaequiposcomputo.DAO.RefaccionesDAO;
import jfxsistemaequiposcomputo.DAO.TipoRefaccionesDAO;
import jfxsistemaequiposcomputo.DAO.SolicitudesDAO;
import jfxsistemaequiposcomputo.pojo.Estado;
import jfxsistemaequiposcomputo.pojo.ListaMantenimientosRespuesta;
import jfxsistemaequiposcomputo.pojo.ListaRefaccionesRespuesta;
import jfxsistemaequiposcomputo.pojo.ListaTipoRefaccionesRespuesta;
import jfxsistemaequiposcomputo.pojo.MantenimientoConEquipoYDiagnostico;
import jfxsistemaequiposcomputo.pojo.Refaccion;
import jfxsistemaequiposcomputo.pojo.TipoRefaccion;
import jfxsistemaequiposcomputo.utils.Constantes;
import jfxsistemaequiposcomputo.utils.Utilidades;

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
    private ComboBox<TipoRefaccion> cbTipoRefaccion;
    @FXML
    private ComboBox<Refaccion> cbNombreRefaccion;
    @FXML
    private TableView<Refaccion> tvListaRefacciones;
    @FXML
    private TableColumn colNombreRefaccion;
    @FXML
    private TableColumn colTipoRefaccion;
    
    ObservableList<MantenimientoConEquipoYDiagnostico> listaMantenimientos;
    private ObservableList<TipoRefaccion> tiporefacciones;
    private ObservableList<Refaccion> refacciones;
    private ObservableList<Refaccion> refaccionesTabla;
    private MantenimientoConEquipoYDiagnostico mantenimiento = new MantenimientoConEquipoYDiagnostico();
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private List<Label> labelsFechaInicio = new ArrayList<>();
    private List<Label> labelsFechaFin = new ArrayList<>();
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
        cargarInformacionTipoRefacciones();
        cbTipoRefaccion.valueProperty().addListener(new ChangeListener<TipoRefaccion>(){
            @Override
            public void changed(ObservableValue<? extends TipoRefaccion> observable, TipoRefaccion oldValue, TipoRefaccion newValue) {
                if(newValue != null){
                    cargarInformacionRefacciones(newValue.getIdTipoRefaccion());
                }
            }
        });
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
            if(diagnostico.getFechaFin() != null && !diagnostico.getFechaFin().isEmpty()) {
                lbFechaFinDiagnostico1.setText(diagnostico.getFechaFin());
                estadoActual = Constantes.ESTADO_SOLICITUD_MANTENIMIENTO;
            }
            if(estados.size() >= 4){
                Estado estadoMantenimiento = estados.get(3);
                lbFechaInicioMantenimiento.setText(estadoMantenimiento.getFechaInicio());
                if(estadoMantenimiento.getFechaFin() != null && !estadoMantenimiento.getFechaFin().isEmpty()) {
                    lbFechaFinMantenimiento.setText(estadoMantenimiento.getFechaFin());
                    estadoActual = Constantes.ESTADO_SOLICITUD_REVISION;
                }
            }
            if(estados.size() >= 5){
                Estado revision = estados.get(4);
                lbFechaInicioRevision.setText(revision.getFechaInicio());
                if(revision.getFechaFin() != null && !revision.getFechaFin().isEmpty()) {
                    lbFechaFinRevision.setText(revision.getFechaFin());
                    lbFechaFinFinalizado.setText(revision.getFechaFin());
                    estadoActual = Constantes.ESTADO_SOLICITUD_FINALIZADO;
                    btnPasarEstado.setDisable(true);
                }
            }
            btnPasarEstado.setDisable(estadoActual.equals
            (Constantes.ESTADO_SOLICITUD_FINALIZADO));  
        }
        btnPasarEstado.setText("Pasar a " + estadoActual);
        configurarTabla();
        cargarInformacionTabla();
    }
    
    private void mostrarImagenEquipo(byte[] fotoEquipo) {
        if (fotoEquipo != null) {
            try{
                ByteArrayInputStream bis = new ByteArrayInputStream(fotoEquipo);
                BufferedImage bufferedImage = ImageIO.read(bis);
                Image image = SwingFXUtils.toFXImage(bufferedImage, null);
                ivFoto.setImage(image);
            }catch (IOException e) {
                e.printStackTrace();
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
    
    @FXML
    private void clicBtnRegresar(MouseEvent event) {
        Stage escenarioPrincipal = (Stage) tfComentarios.getScene().getWindow();
        escenarioPrincipal.close();
    }

    @FXML
    private void clicBtnGuardarComentario(ActionEvent event) {
        int posicion = lvMantenimientos.getSelectionModel().getSelectedIndex();
        if (posicion != -1) {
            MantenimientoConEquipoYDiagnostico mantenimiento = listaMantenimientos.get(posicion);
            int idDiagnostico = mantenimiento.getDiagnostico().getIdDiagnostico();
            String comentario = tfComentarios.getText();
        if (comentario != null && !comentario.isEmpty()) {
            int resultado = MantenimientosDAO.guardarComentario(idDiagnostico, comentario);
            switch (resultado) {
                case Constantes.OPERACION_EXITOSA:
                    Utilidades.mostrarDialogoSimple("Comentario guardado",
                            "El comentario se ha guardado correctamente.",
                            Alert.AlertType.INFORMATION);
                    tfComentarios.setText(comentario);
                    actualizarListView();
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
        }else{
            Utilidades.mostrarDialogoSimple("Comentario vacío", 
                    "Debes ingresar un comentario.", Alert.AlertType.WARNING);
            }
        }else{
            System.out.println("Error: No se ha seleccionado un elemento en lvMantenimientos.");
        }
        actualizarListView();
    }
    
    @FXML
    private void clicBtnPasarEstado(ActionEvent event) { 
        String nombreEstado;
        actualizarListView();
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
        if(respuestaActualizacion == Constantes.OPERACION_EXITOSA) {
            System.out.println("realizado");
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
                Utilidades.mostrarDialogoSimple("Mantenimiento finalizado",
                        "El mantenimiento ha concluído correctamente.",
                        Alert.AlertType.INFORMATION);
                paneDetalles.setVisible(false);   
            }  
        switch(estadoActual) {
            case Constantes.ESTADO_SOLICITUD_MANTENIMIENTO:
                btnPasarEstado.setText("Pasar a Revision");
                break;
            case Constantes.ESTADO_SOLICITUD_REVISION:
                btnPasarEstado.setText("Pasar a Finalizar");
                break;
            case Constantes.ESTADO_SOLICITUD_FINALIZADO:
                btnPasarEstado.setText("Pasar a finalizado");
                break;
            }
        }
        actualizarListView();
    }
    
    private void actualizarListView() {
        listaMantenimientos.clear(); 
        cargarListaMantenimientos(); 
        if(!listaMantenimientos.isEmpty()) {
            lvMantenimientos.getSelectionModel().selectFirst();
        }
    }
        
    @FXML
    private void clicBtnAgregar(ActionEvent event) {
        boolean esValido = validarSeleccionRefaccion();
        if(esValido == true){
            int idRefaccion = cbNombreRefaccion.getValue().getIdRefaccion();
            int idMantenimiento = mantenimiento.getMantenimiento().getIdMantenimiento();
            int respuestaAsociacion = RefaccionesDAO.asociarRefaccionMantenimiento(idMantenimiento, idRefaccion);
            switch(respuestaAsociacion) {
                case Constantes.ERROR_CONEXION:
                    Utilidades.mostrarDialogoSimple(
                        "Error de conexión",
                        "Ocurrió un error al asociar los datos, intente más tarde", 
                        Alert.AlertType.ERROR
                        );
                    break;
                case Constantes.ERROR_CONSULTA:
                    Utilidades.mostrarDialogoSimple(
                        "Error en la creación",
                        "Ocurrió un error al asociar los datos, intente más tarde", 
                        Alert.AlertType.WARNING
                    );
                    break;
                case Constantes.OPERACION_EXITOSA:
                    Utilidades.mostrarDialogoSimple(
                        "Refaccion agregada",
                        "La refacción se ha guardado correctamente", 
                        Alert.AlertType.INFORMATION
                    );
                    cargarInformacionTabla();
                    cbTipoRefaccion.setValue(null);
                    cbNombreRefaccion.setValue(null);
                    break;
            }    
        }else{
            Utilidades.mostrarDialogoSimple(
                        "Error al agregar la refacción",
                        "Debe seleccionar el tipo de refacción y la refacción", 
                        Alert.AlertType.WARNING
                    );
            cbTipoRefaccion.setValue(null);
            cbNombreRefaccion.setValue(null);
        }
    }
    
    private void cargarInformacionTipoRefacciones(){
        tiporefacciones = FXCollections.observableArrayList();
        ListaTipoRefaccionesRespuesta tipoRefaccionesRespuesta = TipoRefaccionesDAO.obtenerTipoRefacciones();
        switch(tipoRefaccionesRespuesta.getCodigoRespuesta()){
            case Constantes.ERROR_CONEXION:
                Utilidades.mostrarDialogoSimple("Sin conexión", "Por el momento no hay conexión", Alert.AlertType.ERROR);
                break;
            case Constantes.ERROR_CONSULTA:
                Utilidades.mostrarDialogoSimple("Error al cargar los datos", "Hubo un error al cargar la información", Alert.AlertType.WARNING);
                break;
            case Constantes.OPERACION_EXITOSA:
                tiporefacciones.addAll(tipoRefaccionesRespuesta.getTipoRefacciones());
                cbTipoRefaccion.setItems(tiporefacciones);
                break;
        }        
    }
    
    private void cargarInformacionRefacciones(int idTipoRefaccion){
        refacciones = FXCollections.observableArrayList();
        ListaRefaccionesRespuesta refaccionesRespuesta = RefaccionesDAO.obtenerRefacciones(idTipoRefaccion);
        switch(refaccionesRespuesta.getCodigoRespuesta()){
            case Constantes.ERROR_CONEXION:
                Utilidades.mostrarDialogoSimple("Sin conexión", "Por el momento no hay conexión", Alert.AlertType.ERROR);
                break;
            case Constantes.ERROR_CONSULTA:
                Utilidades.mostrarDialogoSimple("Error al cargar los datos", "Hubo un error al cargar la información", Alert.AlertType.WARNING);
                break;
            case Constantes.OPERACION_EXITOSA:
                refacciones.addAll(refaccionesRespuesta.getRefacciones());
                cbNombreRefaccion.setItems(refacciones);
                break;
        }        
    }
    
      private void configurarTabla(){
        colNombreRefaccion.setCellValueFactory(new PropertyValueFactory("nombre"));
        colTipoRefaccion.setCellValueFactory(new PropertyValueFactory("tipoRefaccion"));
    }
    
    private void cargarInformacionTabla(){
        int idMantenimiento = mantenimiento.getMantenimiento().getIdMantenimiento();
        refaccionesTabla = FXCollections.observableArrayList();
        ListaRefaccionesRespuesta respuestaBD = RefaccionesDAO.recuperarRefaccionesMantenimiento(idMantenimiento);
        switch(respuestaBD.getCodigoRespuesta()){
            case Constantes.ERROR_CONEXION:
                Utilidades.mostrarDialogoSimple("Sin conexión", "Por el momento no hay conexión", Alert.AlertType.ERROR);
                break;
            case Constantes.ERROR_CONSULTA:
                Utilidades.mostrarDialogoSimple("Error al cargar los datos", "Hubo un error al cargar la información", Alert.AlertType.WARNING);
                break;
            case Constantes.OPERACION_EXITOSA:
                refaccionesTabla.addAll(respuestaBD.getRefacciones());
                tvListaRefacciones.setItems(refaccionesTabla);
                break;    
        }
    }
    
    private boolean validarSeleccionRefaccion(){
        boolean esValido = true;
        if(cbNombreRefaccion.getValue() == null || cbTipoRefaccion.getValue() == null){
            esValido = false;
        }
        return esValido;
    }
}
