package jfxsistemaequiposcomputo.controladores;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import jfxsistemaequiposcomputo.pojo.Usuario;
import jfxsistemaequiposcomputo.utils.Utilidades;

public class MenuPrincipalController implements Initializable {
    @FXML
    private Pane paneModuloPrivilegiado;
    private Usuario usuario;
    
    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
    
    public void inicializarOpcionesAdmin(boolean esAdministrador) {
        paneModuloPrivilegiado.setVisible(esAdministrador);
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    @FXML
    private void clicCerrarSesion(MouseEvent event) {
        Stage escenarioPrincipal = 
            (Stage) paneModuloPrivilegiado.getScene().getWindow();
        escenarioPrincipal.close();
    }

    @FXML
    private void clicRedirigirSolicitarMantenimiento(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(
                getClass()
                    .getResource("/jfxsistemaequiposcomputo/vistas/FXMLRegistrarEquipo.fxml")
            );
            Parent root = loader.load();
            RegistrarEquipoController registrarEquipoController 
                = loader.getController();

            registrarEquipoController.setUsuario(usuario);
            
            Stage nuevoEscenario = new Stage();
            Scene nuevaEscena = new Scene(root);
            nuevoEscenario.setScene(nuevaEscena);
            nuevoEscenario.setTitle("Solicitar mantenimiento");
            nuevoEscenario.initModality(Modality.APPLICATION_MODAL);
            nuevoEscenario.showAndWait();

        } catch (IOException e) {
            Utilidades.mostrarDialogoSimple(
                "Error en la redirección",
                "Por el momento no se puede ingresar al módulo"
                + ", intente más tarde",
                Alert.AlertType.ERROR
            );
        }
    }

    @FXML
    private void clicRedirigirGenerarDiagnostico(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(
                getClass()
                    .getResource("/jfxsistemaequiposcomputo/vistas/FXMLGenerarDiagnostico.fxml")
            );
            Parent root = loader.load();
            GenerarDiagnosticoController generarDiagnosticoController 
                = loader.getController();

            generarDiagnosticoController.setUsuario(usuario);
            
            Stage nuevoEscenario = new Stage();
            Scene nuevaEscena = new Scene(root);
            nuevoEscenario.setScene(nuevaEscena);
            nuevoEscenario.setTitle("Generar diagnóstico");
            nuevoEscenario.initModality(Modality.APPLICATION_MODAL);
            nuevoEscenario.showAndWait();
        } catch (IOException e) {
            Utilidades.mostrarDialogoSimple(
                "Error en la redirección",
                "Por el momento no se puede ingresar al módulo"
                + ", intente más tarde",
                Alert.AlertType.ERROR
            );
        }
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
