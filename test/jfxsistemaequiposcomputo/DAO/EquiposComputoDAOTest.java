package jfxsistemaequiposcomputo.DAO;

import jfxsistemaequiposcomputo.pojo.EquipoComputo;
import jfxsistemaequiposcomputo.utils.Constantes;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author rodri
 */
public class EquiposComputoDAOTest {
    @Test
    public void testAsociarSolicitudEquipo() {
        System.out.println("asociarSolicitudEquipo");
        
        EquipoComputo equipo = new EquipoComputo();
        
        byte[] imagen = {1, 2, 3, 4};
        
        equipo.setIdUsuario(2); //Debe ser un ID existente en la BD
        equipo.setIdSolicitud(2); //Debe ser un ID existente en la BD
        equipo.setTipo("Prueba");
        equipo.setIncluyeCargador(true);
        equipo.setMarca("Prueba");
        equipo.setModelo("Prueba");
        equipo.setTamanioPantalla(12.6F);
        equipo.setProcesador("Prueba");
        equipo.setMemoriaRAM(8);
        equipo.setSistemaOperativo("Prueba");
        equipo.setUsuarioSO("Prueba");
        equipo.setContraseniaSO("Prueba");
        equipo.setImagen(imagen);
        
        int respuestaEspereda = Constantes.OPERACION_EXITOSA;
        int respuestaRecibida = EquiposComputoDAO.asociarSolicitudEquipo(equipo);
        assertEquals(respuestaEspereda, respuestaRecibida);
    }

    @Test
    public void testCrearEquipoComputo() {
        System.out.println("crearEquipoComputo");
        byte[] imagen = {1, 2, 3, 4};
        EquipoComputo nuevoEquipo = new EquipoComputo();
        nuevoEquipo.setIdUsuario(2);
        nuevoEquipo.setContraseniaSO("XXXX");
        nuevoEquipo.setFechaRegistro("2023-02-12");
        nuevoEquipo.setTipo("PC Escritorio");
        nuevoEquipo.setIncluyeCargador(true);
        nuevoEquipo.setModelo("XXX");
        nuevoEquipo.setSistemaOperativo("XXX");
        nuevoEquipo.setTamanioPantalla(12.5f);
        nuevoEquipo.setContraseniaSO("XXXX");
        nuevoEquipo.setProcesador("XXXX");
        nuevoEquipo.setMemoriaRAM(15);
        nuevoEquipo.setMarca("XXX");
        nuevoEquipo.setFechaRegistro("2023-10-12");
        nuevoEquipo.setImagen (imagen);
        nuevoEquipo.setUsuarioSO("XXX");
        
        int idUsuario = 2; //deben de existir en la base de datos
        int idSolicitudDiagnostico = 2;
        int respuesta = Constantes.OPERACION_EXITOSA;
        int respuestaObtenida = EquiposComputoDAO.crearEquipoComputo(nuevoEquipo, idUsuario, idSolicitudDiagnostico);
        
        assertEquals(respuesta, respuestaObtenida);
    }
    
}
