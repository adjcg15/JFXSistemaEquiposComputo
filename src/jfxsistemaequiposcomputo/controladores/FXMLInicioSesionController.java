/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package jfxsistemaequiposcomputo.controladores;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import jfxsistemaequiposcomputo.DAO.SesionDAO;
import jfxsistemaequiposcomputo.utils.Constantes;
import jfxsistemaequiposcomputo.utils.Utilidades;

/**
 * FXML Controller class
 *
 * @author dnava
 */
public class FXMLInicioSesionController implements Initializable {

    @FXML
    private TextField tfCorreo;
    @FXML
    private PasswordField pswContraseña;
    @FXML
    private Label lbErrorCorreo;
    @FXML
    private Label lbErrorContrasenia;

    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void clicIngresar(ActionEvent event)throws IOException{
        lbErrorCorreo.setText(" ");
        lbErrorContrasenia.setText(" ");
        validarInformacionCampos();
    }
    
    private void validarInformacionCampos()throws IOException{
        String usuarioCorreo = tfCorreo.getText();
        String password = pswContraseña.getText();
        
        boolean camposValidos = true;
        if(usuarioCorreo.isEmpty()){
            lbErrorCorreo.setText("Ingresa tu correo.");
        }
        if (password.length()== 0){
            camposValidos=false;
            lbErrorContrasenia.setText("Ingresa tu contraseña.");
        }
        if(camposValidos){
           validarInformacionUsuario(usuarioCorreo, password);
        }
    }
    
    private void validarInformacionUsuario(String usuario, String password){
        usuario usuarioRespuesta = SesionDAO.verificarUsuarioSesion(usuario, password);
        System.out.println("Respuesta: " + usuarioRespuesta.getCodigoRespuesta());
        
        switch (usuarioRespuesta.getCodigoRespuesta()){
            case Constantes.ERROR_CONEXION:
               Utilidades.mostrarDialogoSimple("Error de conexión",
                       "Por el momento no hay conexión a la base de datos,"
                               + " por favor intente mas tarde",
                       Alert.AlertType.ERROR);
            break;
            case Constantes.ERROR_CONSULTA:
                Utilidades.mostrarDialogoSimple("Error en la solicitud",
                        "Por el momento no se puede procesar la solicitud"
                                + " de verificación",
                        Alert.AlertType.ERROR);
            break;
            case Constantes.OPERACION_EXITOSA:
                if(usuarioRespuesta.getIdUsuario()>0){
                    redirigirMenuPrincipal(); 
                }else{
                    Utilidades.mostrarDialogoSimple("Informacion incorrecta",
                        "El usuario y/o contraseña no son correctos, "
                                + "por favor verifica tu información",
                        Alert.AlertType.WARNING);
                }
            break;
            default:
                Utilidades.mostrarDialogoSimple("Error de petición",
                        "El sistema no se encuentra disponible por el momento",
                        Alert.AlertType.NONE);
        }
    }
    
    private void redirigirMenuPrincipal(){
        Stage escenarioBase = (Stage) tfCorreo.getScene().getWindow();
       escenarioBase.setScene(Utilidades.inicializarEscena("vistas/FXMLPrincipal.fxml"));
       escenarioBase.setTitle("Menú Principal");
       escenarioBase.show();
    }
    
    
}
