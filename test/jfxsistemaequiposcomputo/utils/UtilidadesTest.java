package jfxsistemaequiposcomputo.utils;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import jfxsistemaequiposcomputo.JFXSistemaEquiposComputo;
import org.junit.Test;
import static org.junit.Assert.*;

public class UtilidadesTest {

    @Test
    public void testFechaActualFormatoMySQL() {
        System.out.println("fechaActualFormatoMySQL");
        String fechaEsperada = "2023-05-11";
        String fechaRespuesta = Utilidades.fechaActualFormatoMySQL();
        assertEquals(fechaEsperada, fechaRespuesta);
    }
    
}
