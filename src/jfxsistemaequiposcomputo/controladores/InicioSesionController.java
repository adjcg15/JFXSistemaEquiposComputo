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
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import jfxsistemaequiposcomputo.DAO.SesionDAO;
import jfxsistemaequiposcomputo.pojo.Usuario;
import jfxsistemaequiposcomputo.pojo.UsuarioRespuesta;
import jfxsistemaequiposcomputo.utils.Constantes;
import jfxsistemaequiposcomputo.utils.Utilidades;

/**
 * FXML Controller class
 *
 * @author dnava
 */
public class InicioSesionController implements Initializable {

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
        lbErrorCorreo.setText("");
        lbErrorContrasenia.setText("");
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
    
    private void validarInformacionUsuario(String correo, String password){
        UsuarioRespuesta usuarioRespuesta = SesionDAO.verificarUsuarioSesion(correo, password);
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
                if(usuarioRespuesta.getUsuario().getIdUsuario()>0){
                    Utilidades.mostrarDialogoSimple("Bienvenido(a)", 
                            "Bienvenido(a) "+usuarioRespuesta.getUsuario().toString()+ "al sistema",
                    Alert.AlertType.INFORMATION);
                    redirigirMenuPrincipal(usuarioRespuesta.getUsuario()); 
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
                        Alert.AlertType.WARNING);
        }
    }
    
    private void redirigirMenuPrincipal(Usuario usuarioRespuesta){
        try {
            FXMLLoader loader = new FXMLLoader(
                getClass()
                    .getResource("/jfxsistemaequiposcomputo/vistas/FXMLMenuPrincipal.fxml")
            );
            Parent root = loader.load();
            MenuPrincipalController menuController = loader.getController();

            menuController.setUsuario(usuarioRespuesta);
            menuController.inicializarOpcionesAdmin(
                usuarioRespuesta.isPrivilegiado()
            );

            Stage escenarioBase = (Stage) tfCorreo.getScene().getWindow();
            Scene currentScene = escenarioBase.getScene();
            currentScene.setRoot(root);
        } catch (IOException e) {
            Utilidades.mostrarDialogoSimple(
                "Error en la redirección",
                "Por el momento no se puede ingresar al sistema"
                + ", intente más tarde",
                Alert.AlertType.ERROR
            );
        }
    }
}
