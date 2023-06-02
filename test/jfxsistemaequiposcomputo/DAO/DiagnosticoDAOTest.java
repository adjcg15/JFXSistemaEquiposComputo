/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package jfxsistemaequiposcomputo.DAO;

import jfxsistemaequiposcomputo.pojo.Diagnostico;
import jfxsistemaequiposcomputo.utils.Constantes;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author dnava
 */
public class DiagnosticoDAOTest {
    
    public DiagnosticoDAOTest() {
    }

    @Test

    public void testCrearDiagnostico() {
        System.out.println("Operacion fallida");

        Diagnostico diagnostico = new Diagnostico();
        diagnostico.setTipoDeMantenimiento("Correctivo");
        diagnostico.setFechaAtencion("2023-10-12");
        diagnostico.setDiagnosticoPreliminar("No sirve");
        diagnostico.setFechaSolicitud("2023-02-15");
        diagnostico.setCostoEstimado(12);
        diagnostico.setPropuestaSolucion("CCC");
        diagnostico.setIdSolicitudDiagnostico(1); // ID existente

        int respuestaEsperada = 501;
        int respuestaObtenida = DiagnosticoDAO.crearDiagnostico(diagnostico);

        assertEquals(respuestaEsperada, respuestaObtenida);
    }
}

