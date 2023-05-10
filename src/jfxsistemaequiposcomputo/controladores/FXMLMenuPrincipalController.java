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

/**
 *
 * @author ANGEL
 */
public class FXMLMenuPrincipalController implements Initializable {
    @FXML
    private Pane paneModuloPrivilegiado;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void clicCerrarSesion(MouseEvent event) {
    }

    @FXML
    private void clicRedirigirSolicitarMantenimiento(ActionEvent event) {
    }

    @FXML
    private void clicRedirigirGenerarDiagnostico(ActionEvent event) {
    }

    @FXML
    private void clicRedirigirGestionarMantenimiento(ActionEvent event) {
    }
    
}
