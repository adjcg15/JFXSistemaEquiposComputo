/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXML2.java to edit this template
 */
package jfxsistemaequiposcomputo.controladores;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import jfxsistemaequiposcomputo.pojo.Usuario;
import jfxsistemaequiposcomputo.utils.Utilidades;

/**
 *
 * @author ANGEL
 */
public class FXMLMenuPrincipalController implements Initializable {
    @FXML
    private Pane paneModuloPrivilegiado;
    private Usuario usuario;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }
    
    private void cargarOpcionesUsuario() {
        
    }

    @FXML
    private void clicCerrarSesion(MouseEvent event) {
        
    }

    @FXML
    private void clicRedirigirSolicitarMantenimiento(ActionEvent event) {
        Stage escenarioSolicitudMantenimiento = new Stage();
        escenarioSolicitudMantenimiento.setScene(
            Utilidades.inicializarEscena("vistas/FXMLRegistrarEquipos.fxml")
        );
        escenarioSolicitudMantenimiento.setTitle("Solicitar mantenimiento");
        escenarioSolicitudMantenimiento.initModality(Modality.APPLICATION_MODAL);
        escenarioSolicitudMantenimiento.showAndWait();
    }

    @FXML
    private void clicRedirigirGenerarDiagnostico(ActionEvent event) {
        Stage escenarioDiagnostico = new Stage();
        escenarioDiagnostico.setScene(
            Utilidades.inicializarEscena("vistas/FXMLGenerarDiagnostico.fxml")
        );
        escenarioDiagnostico.setTitle("Generar diagnóstico");
        escenarioDiagnostico.initModality(Modality.APPLICATION_MODAL);
        escenarioDiagnostico.showAndWait();
    }

    @FXML
    private void clicRedirigirGestionarMantenimiento(ActionEvent event) {
        Stage escenarioMantenimiento = new Stage();
        escenarioMantenimiento.setScene(
            Utilidades.inicializarEscena("vistas/FXMLAdministrarMantenimiento.fxml")
        );
        escenarioMantenimiento.setTitle("Administrar mantenimiento");
        escenarioMantenimiento.initModality(Modality.APPLICATION_MODAL);
        escenarioMantenimiento.showAndWait();
    }
    
}
