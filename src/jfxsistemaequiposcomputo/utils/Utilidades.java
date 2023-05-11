package jfxsistemaequiposcomputo.utils;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import jfxsistemaequiposcomputo.JFXSistemaEquiposComputo;

public class Utilidades {
    public static void mostrarDialogoSimple(String titulo, String mensaje, Alert.AlertType tipo){
        Alert mostrarAlerta = new Alert(tipo);
        mostrarAlerta.setTitle(titulo);
        mostrarAlerta.setContentText(mensaje);
        mostrarAlerta.setHeaderText(null);
        mostrarAlerta.showAndWait();
    }
    
    public static Scene inicializarEscena(String ruta){
        Scene escena = null;
        try{
            Parent vista = FXMLLoader.load(JFXSistemaEquiposComputo.class.getResource(ruta));
            escena = new Scene(vista);
        }catch(IOException ex){
            System.err.println("ERROR"+ex.getMessage());
        }
        return escena;
    }
    
    public static String fechaActualFormatoMySQL() {
        LocalDate fechaActual = LocalDate.now();
        String fechaConFormato = fechaActual.format(
            DateTimeFormatter.ofPattern("yyyy-MM-dd")
        );
        return fechaConFormato;
    }
}
